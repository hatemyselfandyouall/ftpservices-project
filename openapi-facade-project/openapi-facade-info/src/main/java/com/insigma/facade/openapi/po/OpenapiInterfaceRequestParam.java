 
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
public class OpenapiInterfaceRequestParam implements Serializable{


	//========== properties ==========

    @ApiModelProperty("接口入参表")
    @Column( name="id")
    private String id;

    @ApiModelProperty("参数所属接口id")
    @Column( name="interface_id")
    private Long interfaceId;

    @ApiModelProperty("参数类型码")
    @Column( name="type_code")
    private String typeCode;

    @ApiModelProperty("参数描述")
    @Column( name="description")
    private String description;


    @ApiModelProperty("父参数id")
    @Column( name="parent_id")
    private String parentId;

    @ApiModelProperty("0未删除1已删除")
    @Column( name="is_delete")
    private Integer isDelete;

    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("")
    @Column( name="modify_time")
    private Date modifyTime;


    List<OpenapiInterfaceRequestParam> children;


}
