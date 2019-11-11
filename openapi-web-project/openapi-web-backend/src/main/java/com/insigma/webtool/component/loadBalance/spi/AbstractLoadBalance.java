package com.insigma.webtool.component.loadBalance.spi;

import com.insigma.facade.openapi.po.OpenapiInterface;
import com.insigma.facade.openapi.po.OpenapiInterfaceInnerUrl;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * The type Abstract load balance.
 *
 * @author xiaoyu(Myth)
 */
public abstract class AbstractLoadBalance implements LoadBalance {

    /**
     * Do select divide upstream.
     *
     * @param upstreamList the upstream list
     * @param ip           the ip
     * @return the divide upstream
     */
    protected abstract OpenapiInterfaceInnerUrl doSelect(List<OpenapiInterfaceInnerUrl> upstreamList, String ip);

    @Override
    public OpenapiInterfaceInnerUrl select(final List<OpenapiInterfaceInnerUrl> upstreamList, final String ip) {
        if (CollectionUtils.isEmpty(upstreamList)) {
            return null;
        }
        if (upstreamList.size() == 1) {
            return upstreamList.get(0);
        }
        return doSelect(upstreamList, ip);
    }

}
