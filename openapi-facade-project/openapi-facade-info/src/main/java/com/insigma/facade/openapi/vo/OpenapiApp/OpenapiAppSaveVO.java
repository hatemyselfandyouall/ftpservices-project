package com.insigma.facade.openapi.vo.OpenapiApp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class OpenapiAppSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("应用名")
    @Column( name="name")
    private String name;

    @ApiModelProperty("应用类型id")
    @Column( name="type_id")
    private Long typeId;

    @ApiModelProperty("渠道来源1管理系统2开放平台")
    @Column( name="channel_source")
    private Integer channelSource;

    @ApiModelProperty("所属机构")
    @Column( name="org_name")
    private String orgName;

    @ApiModelProperty("应用所属的用户id")
    @Column( name="user_id")
    private Long userId;

    @ApiModelProperty("应用的app标识")
    @Column( name="app_key")
    private String appKey;

    @ApiModelProperty("应用的app秘钥")
    @Column( name="app_secret")
    private String appSecret;

    @ApiModelProperty("token")
    @Column( name="token")
    private String token;

    @ApiModelProperty("回调url")
    @Column( name="callback_url")
    private String callbackUrl;

    @ApiModelProperty("所属区域")
    @Column( name="area")
    private String area;

    @ApiModelProperty("应用描述")
    @Column( name="distribution")
    private String distribution;
}
