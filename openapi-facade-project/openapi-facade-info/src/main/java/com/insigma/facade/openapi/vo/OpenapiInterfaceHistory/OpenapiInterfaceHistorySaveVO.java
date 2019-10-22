package com.insigma.facade.openapi.vo.OpenapiInterfaceHistory;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiInterfaceHistorySaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

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

}
