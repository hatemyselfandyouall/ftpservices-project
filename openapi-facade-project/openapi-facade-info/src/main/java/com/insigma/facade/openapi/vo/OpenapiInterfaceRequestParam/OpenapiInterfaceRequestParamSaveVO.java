package com.insigma.facade.openapi.vo.OpenapiInterfaceRequestParam;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Data
public class OpenapiInterfaceRequestParamSaveVO implements Serializable {

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

    @ApiModelProperty("前端分层字段")
    @Column( name="super_level")
    private Integer superLevel;

    List<OpenapiInterfaceRequestParamSaveVO> children;
}
