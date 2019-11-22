package com.insigma.facade.openapi.vo.OpenapiAppInterface;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class OpenapiAppInterfaceSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("应用id")
    @Column( name="app_id")
    private Long appId;

    @ApiModelProperty("接口id")
    @Column( name="interface_id")
    private List<Long> interfaceIds;

    @ApiModelProperty("1系统分配2自主申请")
    @Column( name="source_type")
    private Integer sourceType;

    @ApiModelProperty("调用原因")
    @Column( name="use_reason")
    private String useReason;

    @ApiModelProperty("0未删除1已删除//前端不用传")
    @Column( name="is_delete")
    private Integer isDelete;

    @ApiModelProperty("创建时间//前端不用传")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("修改时间//前端不用传")
    @Column( name="modify_time")
    private Date modifyTime;

    @ApiModelProperty("开放权限1全部2白名单")
    @Column( name="open_access")
    private Integer openAccess;

    @ApiModelProperty("IP地址")
    private String ipAddress;
}
