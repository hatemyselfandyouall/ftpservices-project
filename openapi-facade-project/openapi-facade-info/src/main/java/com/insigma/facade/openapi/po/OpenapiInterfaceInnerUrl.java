 
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
public class OpenapiInterfaceInnerUrl implements Serializable{


	//========== properties ==========

    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("")
    @Column( name="interface_id")
    private Long interfaceId;

    @ApiModelProperty("内部url")
    @Column( name="inner_url")
    private String innerUrl;




}
