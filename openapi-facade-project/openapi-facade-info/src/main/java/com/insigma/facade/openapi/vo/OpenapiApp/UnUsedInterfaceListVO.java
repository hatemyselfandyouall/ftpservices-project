package com.insigma.facade.openapi.vo.OpenapiApp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class UnUsedInterfaceListVO implements Serializable {

    @ApiModelProperty("应用Id")
    private Long id;

    @ApiModelProperty("应用类型id")
    private Long typeId;
}
