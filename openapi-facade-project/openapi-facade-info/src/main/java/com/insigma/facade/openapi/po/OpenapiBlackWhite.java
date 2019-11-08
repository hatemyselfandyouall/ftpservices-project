 
package com.insigma.facade.openapi.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain = true)
public class OpenapiBlackWhite implements Serializable{


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("IP地址")
    @Column( name="ip_address")
    private String ipAddress;

    @ApiModelProperty("应用id")
    @Column( name="application_id")
    private String applicationId;

    @ApiModelProperty("应用名称")
    @Column( name="application_name")
    private String applicationName;

    @ApiModelProperty("添加类型，1白名单，2黑名单")
    @Column( name="add_type")
    private Integer addType;

    @ApiModelProperty("添加原因")
    @Column( name="reason")
    private String reason;

    @ApiModelProperty("0未删除1已删除")
    @Column( name="is_delete")
    private Integer isDelete;

    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("")
    @Column( name="modify_time")
    private Date modifyTime;

    @ApiModelProperty("")
    @Column( name="create_by")
    private Long createBy;

    @ApiModelProperty("")
    @Column( name="modify_by")
    private Long modifyBy;

}
