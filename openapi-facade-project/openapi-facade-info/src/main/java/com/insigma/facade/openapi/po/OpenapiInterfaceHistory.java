 
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
public class OpenapiInterfaceHistory implements Serializable{


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("接口id")
    @Column( name="interface_id")
    private Long interfaceId;

    @ApiModelProperty("更新描述")
    @Column( name="update_description")
    private Long updateDescription;

    @ApiModelProperty("版本号")
    @Column( name="version_number")
    private Integer versionNumber;

    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("创建者id")
    @Column( name="creator_id")
    private Long creatorId;

    @ApiModelProperty("创建者姓名")
    @Column( name="creator_name")
    private String creatorName;




}
