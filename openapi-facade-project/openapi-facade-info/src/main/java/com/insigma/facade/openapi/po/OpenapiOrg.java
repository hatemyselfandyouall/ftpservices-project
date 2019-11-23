 
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
public class OpenapiOrg implements Serializable{


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("机构id")
    @Column( name="org_id")
    private Long orgId;

    @ApiModelProperty("机构名")
    @Column( name="org_name")
    private String orgName;

    @ApiModelProperty("")
    @Column( name="org_code")
    private String orgCode;

    @ApiModelProperty("地区id")
    @Column( name="area_id")
    private Long areaId;

    @ApiModelProperty("地区名")
    @Column( name="area_name")
    private String areaName;

    @ApiModelProperty("ip网段")
    @Column( name="ip_segment")
    private String ipSegment;

    @ApiModelProperty("")
    @Column( name="app_key")
    private String appKey;

    @ApiModelProperty("")
    @Column( name="app_secret")
    private String appSecret;

    @ApiModelProperty("允许访问时间-起")
    @Column( name="access_time_start")
    private Date accessTimeStart;

    @ApiModelProperty("允许访问时间-止")
    @Column( name="access_time_final")
    private Date accessTimeFinal;

    @ApiModelProperty("限制数量")
    @Column( name="limit_count")
    private String limitCount;

    @ApiModelProperty("证书")
    @Column( name="certificate")
    private String certificate;

    @ApiModelProperty("证书文件key")
    @Column( name="certificate_key")
    private String certificateKey;

    @ApiModelProperty("0未删除1已删除")
    @Column( name="is_delete")
    private Integer isDelete;

    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("")
    @Column( name="creator_id")
    private Long creatorId;

    @ApiModelProperty("")
    @Column( name="creator_name")
    private String creatorName;

    @ApiModelProperty("")
    @Column( name="modify_time")
    private Date modifyTime;

    @ApiModelProperty("")
    @Column( name="modify_id")
    private Long modifyId;

    @ApiModelProperty("")
    @Column( name="modify_name")
    private String modifyName;

    @ApiModelProperty("描述")
    @Column( name="distribution")
    private String distribution;

    @ApiModelProperty("字母缩写")
    @Column( name="abbreviation")
    private String abbreviation;

}
