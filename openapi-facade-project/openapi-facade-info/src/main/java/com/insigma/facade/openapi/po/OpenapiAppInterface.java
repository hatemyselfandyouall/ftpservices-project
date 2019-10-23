 
package com.insigma.facade.openapi.po;

import java.io.Serializable;

import lombok.experimental.Accessors;
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
@Accessors(chain = true)
public class OpenapiAppInterface implements Serializable{


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("应用id")
    @Column( name="app_id")
    private Long appId;

    @ApiModelProperty("接口id")
    @Column( name="interface_id")
    private Long interfaceId;

    @ApiModelProperty("0未删除1已删除")
    @Column( name="is_delete")
    private Integer isDelete;

    @ApiModelProperty("0审核未通过1审核已通过")
    @Column( name="is_audit")
    private Integer isAudit;

    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("")
    @Column( name="modify_time")
    private Date modifyTime;




}
