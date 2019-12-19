package com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;

@Data
@Accessors(chain = true)
public class SelfMachineRequestResultVO {

    private String token;

    private String machineCode;

    @ApiModelProperty("自助机类型id")
    @Column( name="machine_type_id")
    private Long machineTypeId;

    private String orgCode;
}
