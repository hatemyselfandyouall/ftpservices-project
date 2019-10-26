package com.insigma.facade.openapi.vo.OpenapiApp;

import com.insigma.facade.openapi.po.OpenapiApp;
import com.insigma.facade.openapi.po.OpenapiAppInterface;
import com.insigma.facade.openapi.po.OpenapiInterface;
import lombok.Data;

@Data
public class OpenapiAppInterfaceShowVO extends OpenapiAppInterface {

    OpenapiInterface openapiInterface;

}
