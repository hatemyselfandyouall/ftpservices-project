 
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
public class OpenapiUser implements Serializable{


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("用户ID")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("登录名")
    @Column( name="logon_name")
    private String logonName;

    @ApiModelProperty("登录密码")
    @Column( name="passwd")
    private String passwd;

    @ApiModelProperty("显示名")
    @Column( name="display_name")
    private String displayName;

    @ApiModelProperty("openId")
    @Column( name="open_id")
    private String openId;

    @ApiModelProperty("联系人名")
    @Column( name="contact_name")
    private String contactName;

    @ApiModelProperty("1超级管理员，2普通机构用户")
    @Column( name="user_type")
    private String userType;

    @ApiModelProperty("证件类型:1身份证、2军官证、3户口本、4护照、5其他")
    @Column( name="card_type")
    private String cardType;

    @ApiModelProperty("证件号码")
    @Column( name="card_id")
    private String cardId;

    @ApiModelProperty("联系电话")
    @Column( name="tel")
    private String tel;

    @ApiModelProperty("手机")
    @Column( name="mobile")
    private String mobile;

    @ApiModelProperty("电子邮箱")
    @Column( name="email")
    private String email;

    @ApiModelProperty("通讯地址")
    @Column( name="user_addr")
    private String userAddr;

    @ApiModelProperty("备注")
    @Column( name="remark")
    private String remark;

    @ApiModelProperty("创建人id")
    @Column( name="creator_id")
    private String creatorId;

    @ApiModelProperty("创建时ip")
    @Column( name="create_ip")
    private String createIp;

    @ApiModelProperty("用户锁定时间")
    @Column( name="lock_time")
    private Date lockTime;

    @ApiModelProperty("用户解锁时间")
    @Column( name="unlock_time")
    private Date unlockTime;

    @ApiModelProperty("用户锁定原因")
    @Column( name="lock_reason")
    private String lockReason;

    @ApiModelProperty("用户过期时间")
    @Column( name="user_expire_date")
    private Date userExpireDate;

    @ApiModelProperty("用户连续登录失败次数")
    @Column( name="fail_num")
    private Integer failNum;

    @ApiModelProperty("密码过期策略：1系统配置周期，2永不过期，3指定日期")
    @Column( name="pw_expire_type")
    private String pwExpireType;

    @ApiModelProperty("密码过期时间")
    @Column( name="pw_expire_date")
    private Date pwExpireDate;

    @ApiModelProperty("密码最近修改时间")
    @Column( name="pw_edit_date")
    private Date pwEditDate;

    @ApiModelProperty("用户签到状态 1到 2退")
    @Column( name="signs_tate")
    private String signsTate;

    @ApiModelProperty("0未删除1已删除")
    @Column( name="is_delete")
    private Integer isDelete;

    @ApiModelProperty("创建日期")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("修改时间")
    @Column( name="modify_time")
    private Date modifyTime;

    @ApiModelProperty("回退唯一字段")
    @Column( name="prseno")
    private String prseno;


    @ApiModelProperty("机构编码")
    @Column( name="org_no")
    private String orgNo;

}
