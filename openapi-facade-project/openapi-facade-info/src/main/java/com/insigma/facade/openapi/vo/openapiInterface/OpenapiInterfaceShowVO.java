package com.insigma.facade.openapi.vo.openapiInterface;

import com.insigma.facade.openapi.po.OpenapiInterface;
import com.insigma.facade.openapi.po.OpenapiInterfaceInnerUrl;
import com.insigma.facade.openapi.po.OpenapiInterfaceRequestParam;
import com.insigma.facade.openapi.po.OpenapiInterfaceResponseParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OpenapiInterfaceShowVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private OpenapiInterface openapiInterface;

    private List<OpenapiInterfaceRequestParam> openapiInterfaceRequestParams;

    private List<OpenapiInterfaceResponseParam> openapiInterfaceResponseParams;
    private List<OpenapiInterfaceInnerUrl> openapiInterfaceInnerUrls;

}
