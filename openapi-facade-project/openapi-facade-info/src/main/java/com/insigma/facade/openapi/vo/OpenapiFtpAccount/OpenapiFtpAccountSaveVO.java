package com.insigma.facade.openapi.vo.OpenapiFtpAccount;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiFtpAccountSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("")
    @Column( name="user_id")
    private Long userId;

    @ApiModelProperty("账号")
    @Column( name="login_name")
    private String loginName;

    @ApiModelProperty("密码")
    @Column( name="pass_word")
    private String passWord;

}
