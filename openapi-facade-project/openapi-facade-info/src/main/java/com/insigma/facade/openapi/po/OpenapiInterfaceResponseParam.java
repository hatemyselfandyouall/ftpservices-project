 
package com.insigma.facade.openapi.po;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;

import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;


@Data
public class OpenapiInterfaceResponseParam implements Serializable{


	//========== properties ==========

    @Id
    @GeneratedValue(generator="JDBC")
    @ApiModelProperty("接口入参表")
    @Column( name="id")
    private String id;

    @ApiModelProperty("字段名")
    @Column( name="name")
    private String name;

    @ApiModelProperty("是否必填0不是1是")
    @Column( name="required")
    private Integer required;

    @ApiModelProperty("参数所属接口id")
    @Column( name="interface_id")
    private Long interfaceId;

    @ApiModelProperty("参数类型码")
    @Column( name="type")
    private String type;

    @ApiModelProperty("参数备注")
    @Column( name="remark")
    private String remark;

    @ApiModelProperty("0未删除1已删除")
    @Column( name="is_delete")
    private Integer isDelete;

    @ApiModelProperty("")
    @Column( name="parent_id")
    private String parentId;

    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("")
    @Column( name="modify_time")
    private Date modifyTime;

    List<OpenapiInterfaceResponseParam> children;


}
