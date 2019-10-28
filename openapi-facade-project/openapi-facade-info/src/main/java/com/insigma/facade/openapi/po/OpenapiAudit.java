 
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
public class OpenapiAudit implements Serializable{


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("id")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("申请接口名称")
    @Column( name="interface_name")
    private String interfaceName;

    @ApiModelProperty("使用应用")
    @Column( name="application")
    private String application;

    @ApiModelProperty("申请机构")
    @Column( name="organization")
    private String organization;

    @ApiModelProperty("机构类型")
    @Column( name="organize_type")
    private String organizeType;

    @ApiModelProperty("申请原因")
    @Column( name="a_reason")
    private String aReason;

    @ApiModelProperty("申请时间")
    @Column( name="a_time")
    private Date aTime;

    @ApiModelProperty("申请人")
    @Column( name="contact")
    private String contact;

    @ApiModelProperty("联系邮箱")
    @Column( name="email")
    private String email;

    @ApiModelProperty("联系人电话")
    @Column( name="phonenum")
    private String phonenum;

    @ApiModelProperty("审核人")
    @Column( name="auditor")
    private String auditor;

    @ApiModelProperty("审核意见")
    @Column( name="audit_opinion")
    private String auditOpinion;

    @ApiModelProperty("审核时间")
    @Column( name="audit_time")
    private Date auditTime;

    @ApiModelProperty("备注")
    @Column( name="remark")
    private String remark;

    @ApiModelProperty("审核人id")
    @Column( name="audit_id")
    private Long auditId;

    @ApiModelProperty("审核状态0驳回1通过2待审核")
    @Column( name="audit_status")
    private Integer auditStatus;

    @ApiModelProperty("审核人id")
    @Column( name="appId")
    private Long app_id;

    @ApiModelProperty("接口id")
    @Column( name="interfaceId")
    private Long interface_id;

    @ApiModelProperty("机构id")
    @Column( name="orgId")
    private Long org_id;
}
