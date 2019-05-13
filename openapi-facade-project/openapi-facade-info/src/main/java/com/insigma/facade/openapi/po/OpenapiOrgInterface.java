 
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
public class OpenapiOrgInterface implements Serializable{


	//========== properties ==========
	
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("")
    @Column( name="org_code")
    private String orgCode;

    @ApiModelProperty("")
    @Column( name="interface_id")
    private Long interfaceId;


    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("")
    @Column( name="modify_time")
    private Date modifyTime;




}
