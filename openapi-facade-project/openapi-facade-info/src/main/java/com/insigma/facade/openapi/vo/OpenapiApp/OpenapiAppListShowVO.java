package com.insigma.facade.openapi.vo.OpenapiApp;

import com.insigma.facade.openapi.po.OpenapiApp;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiAppListShowVO extends OpenapiApp implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("接口数量")
    private Integer interfaceCount;

}
