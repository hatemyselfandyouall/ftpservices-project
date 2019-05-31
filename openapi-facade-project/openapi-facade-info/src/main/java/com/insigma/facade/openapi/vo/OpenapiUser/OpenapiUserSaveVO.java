package com.insigma.facade.openapi.vo.OpenapiUser;

import com.insigma.facade.openapi.po.OpenapiFtpAccount;
import com.insigma.facade.openapi.vo.OpenapiFtpAccount.OpenapiFtpAccountSaveVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class OpenapiUserSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("登录名")
    @Column( name="logon_name")
    private String logonName;

    @ApiModelProperty("登录密码")
    @Column( name="passwd")
    private String passwd;

    @ApiModelProperty("重置密码标识，为1时重置密码")
    private String resetPasswdFlag;

    @ApiModelProperty("显示名")
    @Column( name="display_name")
    private String displayName;

    @ApiModelProperty("openId")
    @Column( name="open_id")
    private String openId;

    @ApiModelProperty("显示名")
    @Column( name="contact_name")
    private String contactName;

    @ApiModelProperty("1超级管理员，2普通机构用户")
    @Column( name="user_type")
    private String userType;


    @ApiModelProperty("创建时ip")
    @Column( name="create_ip")
    private String createIp;

    @ApiModelProperty("联系电话")
    @Column( name="tel")
    private String tel;


    @ApiModelProperty("电子邮箱")
    @Column( name="email")
    private String email;


    OpenapiFtpAccountSaveVO openapiFtpAccountSaveVO;

}
