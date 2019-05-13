 
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
public class OpenapiInterface implements Serializable{


	//========== properties ==========

    @Id
    @ApiModelProperty("接口id")
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

    @ApiModelProperty("0未删除1已删除")
    @Column( name="is_delete")
    private Integer isDelete;

    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("")
    @Column( name="modify_time")
    private Date modifyTime;




}
