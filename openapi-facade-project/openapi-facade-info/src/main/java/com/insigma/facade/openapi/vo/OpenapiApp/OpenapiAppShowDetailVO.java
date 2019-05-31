package com.insigma.facade.openapi.vo.OpenapiApp;

import com.insigma.facade.openapi.po.OpenapiApp;
import com.insigma.facade.openapi.po.OpenapiAppInterface;
import com.insigma.facade.openapi.po.OpenapiInterface;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OpenapiAppShowDetailVO extends OpenapiApp implements Serializable {

    private static final long serialVersionUID = -7025150275716002592L;

    private List<OpenapiInterface> openapiInterfaces;
}
