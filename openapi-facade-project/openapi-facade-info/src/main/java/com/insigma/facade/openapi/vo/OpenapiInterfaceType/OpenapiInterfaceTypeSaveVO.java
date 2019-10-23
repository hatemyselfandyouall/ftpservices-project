package com.insigma.facade.openapi.vo.OpenapiInterfaceType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class OpenapiInterfaceTypeSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("")
    @Column( name="name")
    private String name;

    @ApiModelProperty("1业务分类2应用分类")
    @Column( name="type")
    private Integer type;

    @ApiModelProperty("使用的应用类型1医保2医院")
    @Column( name="app_type")
    private String appType;

    @ApiModelProperty("备注")
    @Column( name="remark")
    private String remark;

    @ApiModelProperty("")
    @Column( name="parent_id")
    private Long parentId;
}
