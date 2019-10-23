 
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
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class OpenapiApp implements Serializable{


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("应用名")
    @Column( name="name")
    private String name;

    @ApiModelProperty("应用类型id")
    @Column( name="typeId")
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
