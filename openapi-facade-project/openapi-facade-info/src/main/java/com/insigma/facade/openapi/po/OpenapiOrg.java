 
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
public class OpenapiOrg implements Serializable{


	//========== properties ==========
	
    @ApiModelProperty("机构id(系统生成,唯一关键字)")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("机构名称")
    @Column( name="org_name")
    private String orgName;

    @ApiModelProperty("机构代码(即机构的编号，输入的编号)")
    @Column( name="orgenter_code")
    private String orgenterCode;

    @ApiModelProperty("父机构id")
    @Column( name="parent_id")
    private Long parentId;

    @ApiModelProperty("机构简称")
    @Column( name="short_name")
    private String shortName;

    @ApiModelProperty("行政区划代码")
    @Column( name="region_code")
    private String regionCode;

    @ApiModelProperty("机构负责人")
    @Column( name="leader")
    private String leader;

    @ApiModelProperty("联系人")
    @Column( name="linkman")
    private String linkman;

    @ApiModelProperty("联系电话")
    @Column( name="tel")
    private String tel;

    @ApiModelProperty("机构地址")
    @Column( name="org_addr")
    private String orgAddr;

    @ApiModelProperty("机构描述")
    @Column( name="org_desc")
    private String orgDesc;

    @ApiModelProperty("在同一级机构中的序号")
    @Column( name="org_order")
    private Integer orgOrder;

    @ApiModelProperty("机构状态,即有效标志：1、有效，0、无效")
    @Column( name="org_state")
    private String orgState;

    @ApiModelProperty("主管部门")
    @Column( name="superdept")
    private String superdept;

    @ApiModelProperty("系统机构编码（自生成的编号）")
    @Column( name="orgauto_code")
    private String orgautoCode;

    @ApiModelProperty("邮编")
    @Column( name="zip")
    private String zip;

    @ApiModelProperty("路径")
    @Column( name="idpath")
    private String idpath;

    @ApiModelProperty("创建日期")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("修改时间")
    @Column( name="modify_time")
    private Date modifyTime;




}
