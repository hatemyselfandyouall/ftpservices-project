 
package com.insigma.facade.openapi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
public class OpenapiBlackWhiteDto implements Serializable{


	//========== properties ==========

    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("IP地址集合")
    private List<String> ipAddress;

    @ApiModelProperty("应用服务集合")
    private List<ApplicationServiceDto> applicationServiceDtos;

    @ApiModelProperty("添加类型，1白名单，2黑名单")
    private Integer addType;

    @ApiModelProperty("添加原因")
    private String reason;

    @ApiModelProperty("0未删除1已删除")
    private Integer isDelete;

    @ApiModelProperty("")
    private Date createTime;

    @ApiModelProperty("")
    private Date modifyTime;

    @ApiModelProperty("")
    private Long createBy;

    @ApiModelProperty("")
    private Long modifyBy;

}
