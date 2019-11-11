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

package com.insigma.webtool.component.loadBalance.spi;



import com.insigma.facade.openapi.po.OpenapiInterface;
import com.insigma.facade.openapi.po.OpenapiInterfaceInnerUrl;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * hash algorithm impl.
 *
 * @author xiaoyu(Myth)
 */
@Slf4j
public class HashLoadBalance extends AbstractLoadBalance {

    private static final int VIRTUAL_NODE_NUM = 5;

    @Override
    public OpenapiInterfaceInnerUrl doSelect(final List<OpenapiInterfaceInnerUrl> upstreamList, final String ip) {
        final TreeMap<Long, OpenapiInterfaceInnerUrl> treeMap = new TreeMap<>();
        for (OpenapiInterfaceInnerUrl address : upstreamList) {
            for (int i = 0; i < VIRTUAL_NODE_NUM; i++) {
                long addressHash = hash("SOUL-" + address.getInnerUrl() + "-HASH-" + i);
                treeMap.put(addressHash, address);
            }
        }
        long hash = hash(String.valueOf(ip));
        SortedMap<Long, OpenapiInterfaceInnerUrl> lastRing = treeMap.tailMap(hash);
        if (!lastRing.isEmpty()) {
            return lastRing.get(lastRing.firstKey());
        }
        return treeMap.firstEntry().getValue();
    }

    /**
     * get algorithm name.
     *
     * @return this is algorithm name.
     */
    @Override
    public String algorithm() {
        return LoadBalanceEnum.HASH.getName();
    }

    private static long hash(final String key) {
        // md5 byte
        MessageDigest md5=null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            log.error("MD5 not supported", e);
        }
        md5.reset();
        byte[] keyBytes=null;
        try {
            keyBytes = key.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("Unknown string :" + key, e);
        }

        md5.update(keyBytes);
        byte[] digest = md5.digest();

        // hash code, Truncate to 32-bits
        long hashCode = (long) (digest[3] & 0xFF) << 24
                | ((long) (digest[2] & 0xFF) << 16)
                | ((long) (digest[1] & 0xFF) << 8)
                | (digest[0] & 0xFF);
        return hashCode & 0xffffffffL;
    }

}
