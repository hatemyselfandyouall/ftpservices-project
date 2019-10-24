package com.insigma.facade.openapi.vo.OpenapiInterfaceResponseParam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

@Data
public class OpenapiInterfaceResponseParamSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("字段名")
    @Column( name="name")
    private String name;

    @ApiModelProperty("是否必填0不是1是")
    @Column( name="required")
    private Integer required;

    @ApiModelProperty("参数类型码")
    @Column( name="type")
    private String type;

    @ApiModelProperty("参数备注")
    @Column( name="remark")
    private String remark;

    private List<OpenapiInterfaceResponseParamSaveVO> children;

}
