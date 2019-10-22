package com.insigma.facade.openapi.vo.openapiInterface;

import com.insigma.facade.openapi.po.OpenapiInterface;
import com.insigma.facade.openapi.po.OpenapiInterfaceRequestParam;
import com.insigma.facade.openapi.po.OpenapiInterfaceResponseParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OpenapiInterfaceShowVO implements Serializable {

    private static final long serialVersionUID = 1L;

    OpenapiInterface openapiInterface;

    List<OpenapiInterfaceRequestParam> openapiInterfaceRequestParams;

    List<OpenapiInterfaceResponseParam> openapiInterfaceResponseParams;

}
