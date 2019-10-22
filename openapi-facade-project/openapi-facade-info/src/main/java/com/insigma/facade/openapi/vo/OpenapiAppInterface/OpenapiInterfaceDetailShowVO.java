package com.insigma.facade.openapi.vo.OpenapiAppInterface;

import com.insigma.facade.openapi.po.OpenapiInterface;
import com.insigma.facade.openapi.po.OpenapiInterfaceInnerUrl;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OpenapiInterfaceDetailShowVO extends OpenapiInterface implements Serializable {


    private List<OpenapiInterfaceInnerUrl> openapiInterfaceInnerUrls;
}
