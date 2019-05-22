package com.insigma.facade.openapi.vo.OpenapiUser;

import com.insigma.facade.openapi.po.OpenapiFtpAccount;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiUserDetailShowVO implements Serializable {

    private static final long serialVersionUID = 4630227459132402039L;

    @ApiModelProperty("")
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

    @ApiModelProperty("1超级管理员，2普通机构用户")
    @Column( name="user_type")
    private String userType;

    OpenapiFtpAccount openapiFtpAccount;
}
