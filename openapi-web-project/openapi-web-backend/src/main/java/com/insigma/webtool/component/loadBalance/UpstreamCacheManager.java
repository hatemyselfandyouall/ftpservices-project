/*
 *   Licensed to the Apache Software Foundation (ASF) under one or more
 *   contributor license agreements.  See the NOTICE file distributed with
 *   this work for additional information regarding copyright ownership.
 *   The ASF licenses this file to You under the Apache License, Version 2.0
 *   (the "License"); you may not use this file except in compliance with
 *   the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.insigma.webtool.component.loadBalance;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.insigma.facade.openapi.facade.InterfaceFacade;
import com.insigma.facade.openapi.po.OpenapiInterface;
import com.insigma.facade.openapi.po.OpenapiInterfaceInnerUrl;
import com.insigma.facade.openapi.vo.openapiInterface.OpenapiInterfaceShowVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * this is divide  http url upstream.
 *
 * @author xiaoyu
 */
@Component
@Slf4j
public class UpstreamCacheManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpstreamCacheManager.class);

    private static final BlockingQueue<OpenapiInterfaceShowVO> BLOCKING_QUEUE = new LinkedBlockingQueue<>(1024);

    private static final Map<Long, List<OpenapiInterfaceInnerUrl>> UPSTREAM_MAP = Maps.newConcurrentMap();

    private static final Map<Long, List<OpenapiInterfaceInnerUrl>> SCHEDULED_MAP = Maps.newConcurrentMap();

    private static final Integer DELAY_INIT = 30;

    private static final Integer upstreamScheduledTime = 30;

    @Autowired
    InterfaceFacade interfaceFacade;
    /**
     * Find upstream list by selector id list.
     *
     * @param selectorId the selector id
     * @return the list
     */
    public List<OpenapiInterfaceInnerUrl> findUpstreamListBySelectorId(final Long selectorId) {
        return UPSTREAM_MAP.get(selectorId);
    }

    /**
     * Remove by key.
     *
     * @param key the key
     */
    public static void removeByKey(final Long key) {
        SCHEDULED_MAP.remove(key);
        UPSTREAM_MAP.remove(key);
    }

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        intiQueue();
        new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                SoulThreadFactory.create("save-upstream-task", false))
                .execute(new Worker());
        new ScheduledThreadPoolExecutor(1,
                SoulThreadFactory.create("scheduled-upstream-task", false))
                .scheduleWithFixedDelay(this::scheduled,
                        DELAY_INIT, upstreamScheduledTime,
                        TimeUnit.SECONDS);
    }

    private void intiQueue() {
        List<OpenapiInterfaceShowVO> openapiInterfaceShowVOS=interfaceFacade.intiQueue();
        openapiInterfaceShowVOS.forEach(UpstreamCacheManager::submit);
    }

    /**
     * Submit.
     *
     * @param selectorData the selector data
     */
    public static void submit(final OpenapiInterfaceShowVO selectorData) {
        try {
            BLOCKING_QUEUE.put(selectorData);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
    }


    /**
     * Clear.
     */
    static void clear() {
        SCHEDULED_MAP.clear();
        UPSTREAM_MAP.clear();
    }


    /**
     * Execute.
     *
     * @param selectorData the selector data
     */
    public void execute(final OpenapiInterfaceShowVO selectorData) {
        final List<OpenapiInterfaceInnerUrl> upstreamList =selectorData.getOpenapiInterfaceInnerUrls();
        if (CollectionUtils.isNotEmpty(upstreamList)) {
            SCHEDULED_MAP.put(selectorData.getOpenapiInterface().getId(), upstreamList);
            UPSTREAM_MAP.put(selectorData.getOpenapiInterface().getId(), check(upstreamList));
        }
    }

    private void scheduled() {
        if (SCHEDULED_MAP.size() > 0) {
            SCHEDULED_MAP.forEach((k, v) -> UPSTREAM_MAP.put(k, check(v)));
        }
    }

    private List<OpenapiInterfaceInnerUrl> check(final List<OpenapiInterfaceInnerUrl> upstreamList) {
        List<OpenapiInterfaceInnerUrl> resultList = Lists.newArrayListWithCapacity(upstreamList.size());
        for (OpenapiInterfaceInnerUrl divideUpstream : upstreamList) {
            final boolean pass = checkUrl(divideUpstream.getHeartUrl());
            if (pass) {
                resultList.add(divideUpstream);
            } else {
                log.error("check the url={} is fail ", divideUpstream.getHeartUrl());
            }
        }
        return resultList;
    }

    private boolean checkUrl(String heartUrl){
        boolean flag=false;
        try {
//            RestTemplate restTemplate=new RestTemplate();
//            ResponseEntity<String> responseEntity= restTemplate.getForEntity(heartUrl,String.class);
//            if (responseEntity.getStatusCode().is2xxSuccessful()){
                flag=true;
//            }
        }catch (Exception e){
            log.error("心跳检测异常"+e.getMessage());
        }
        return flag;
    }
    /**
     * The type Worker.
     */
    class Worker implements Runnable {

        @Override
        public void run() {
            runTask();
        }

        private void runTask() {
            for (;;) {
                try {
                    final OpenapiInterfaceShowVO selectorData = BLOCKING_QUEUE.take();
                    Optional.of(selectorData).ifPresent(UpstreamCacheManager.this::execute);
                } catch (InterruptedException e) {
                    LOGGER.warn("BLOCKING_QUEUE take operation was interrupted.", e);
                }
            }
        }
    }

}
