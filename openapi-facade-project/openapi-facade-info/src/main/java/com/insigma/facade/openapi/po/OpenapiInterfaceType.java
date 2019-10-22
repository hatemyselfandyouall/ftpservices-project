 
package com.insigma.facade.openapi.po;

import java.io.Serializable;

import lombok.experimental.Accessors;
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
@Accessors(chain = true)
public class OpenapiInterfaceType implements Serializable{


	//========== properties ==========

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
    private Integer appType;

    @ApiModelProperty("备注")
    @Column( name="remark")
    private String remark;

    @ApiModelProperty("")
    @Column( name="parent_id")
    private Long parentId;

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
