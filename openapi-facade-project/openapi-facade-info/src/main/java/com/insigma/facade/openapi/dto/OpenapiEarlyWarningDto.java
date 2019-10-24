 
package com.insigma.facade.openapi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.io.Serializable;
import java.util.Date;


@Data
public class OpenapiEarlyWarningDto implements Serializable{

	//========== properties ==========

    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("预警人员姓名")
    private String name;

    @ApiModelProperty("联系人电话")
    private String phone;

    @ApiModelProperty("邮箱地址")
    private String mailbox;

    @ApiModelProperty("备注")
    private String remarks;

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
