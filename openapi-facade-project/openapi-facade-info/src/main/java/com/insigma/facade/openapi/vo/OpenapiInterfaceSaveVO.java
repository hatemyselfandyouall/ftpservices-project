package com.insigma.facade.openapi.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiInterfaceSaveVO implements Serializable {

    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("接口名")
    @Column( name="name")
    private String name;

    @ApiModelProperty("接口url")
    @Column( name="url")
    private String url;

    @ApiModelProperty("接口类型")
    @Column( name="type")
    private Integer type;

    private static final long serialVersionUID = 1L;

}
