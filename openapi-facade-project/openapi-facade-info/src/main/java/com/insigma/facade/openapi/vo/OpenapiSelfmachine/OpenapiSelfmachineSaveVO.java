package com.insigma.facade.openapi.vo.OpenapiSelfmachine;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiSelfmachineSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("自助机地址id")
    @Column( name="machine_address_id")
    private Long machineAddressId;

    @ApiModelProperty("自助机实际地址")
    @Column( name="machine_address")
    private String machineAddress;

    @ApiModelProperty("备注")
    @Column( name="remark")
    private String remark;

    @ApiModelProperty("自助机类型id")
    @Column( name="machine_type_id")
    private Long machineTypeId;
}
