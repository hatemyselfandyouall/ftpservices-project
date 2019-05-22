package com.insigma.facade.openapi.vo.OpenapiInterfaceResponseParam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiInterfaceResponseParamSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("参数所属接口id")
    @Column( name="interface_id")
    private Long interfaceId;

    @ApiModelProperty("参数类型码")
    @Column( name="type_code")
    private String typeCode;

    @ApiModelProperty("参数描述")
    @Column( name="description")
    private String description;

}
