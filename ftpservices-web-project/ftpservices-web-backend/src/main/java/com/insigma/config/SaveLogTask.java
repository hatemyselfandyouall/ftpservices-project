package com.insigma.config;

import com.insigma.facade.facade.CdGatewayRequestDetailBdFacade;
import com.insigma.facade.vo.CdGatewayRequestDetailBd.CdGatewayRequestVO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SaveLogTask implements Runnable{

    private CdGatewayRequestDetailBdFacade cdGatewayRequestDetailBdFacade;

    private CdGatewayRequestVO cdGatewayRequestVO;

    @Override
    public void run() {
        cdGatewayRequestDetailBdFacade.saveCdGatewayRequest(cdGatewayRequestVO);
    }
}
