package com.insigma.facade.openapi.vo.OpenapiAudit;

import com.insigma.facade.operatelog.vo.SysOperatelogRecord.SysOperatelogRecordSaveVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class OpenapiAuditSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator="JDBC")
    @ApiModelProperty("id//新增的时候不传，修改的时候要传")
    private List<Integer> ids;

    @ApiModelProperty("申请接口名称")
    private String interfaceName;

    @ApiModelProperty("使用应用")
    private String application;

    @ApiModelProperty("申请机构")
    private String organization;

    @ApiModelProperty("机构类型")
    private String organizeType;

    @ApiModelProperty("申请原因")
    private String aReason;

    @ApiModelProperty("申请时间//不用传")
    private Date aTime;

    @ApiModelProperty("申请人")
    private String contact;

    @ApiModelProperty("联系邮箱")
    private String email;

    @ApiModelProperty("联系人电话")
    private String phonenum;

    @ApiModelProperty("审核人")
    private String auditor;

    @ApiModelProperty("审核意见")
    private String auditOpinion;

    @ApiModelProperty("审核时间")
    private Date auditTime;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("审核人id//不用传")
    private Long auditId;

    @ApiModelProperty("审核状态0驳回1通过2待审核//不用传")
    private Integer auditStatus;

    @ApiModelProperty("应用id")
    private Long appId;

    @ApiModelProperty("接口id")
    private Long interfaceId;

    @ApiModelProperty("机构id")
    private Long orgId;

    @ApiModelProperty("用户Id,前端不用传")
    private Long userId;

    @ApiModelProperty("用户姓名,前端不用传")
    private String userName;
}
