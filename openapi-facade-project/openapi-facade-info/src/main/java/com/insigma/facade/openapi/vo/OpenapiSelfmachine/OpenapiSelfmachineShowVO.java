package com.insigma.facade.openapi.vo.OpenapiSelfmachine;

import com.insigma.facade.openapi.po.OpenapiSelfmachine;
import com.insigma.facade.openapi.po.SelfMachineEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class OpenapiSelfmachineShowVO extends OpenapiSelfmachine implements Serializable {

    @ApiModelProperty("状态")
    @Column( name="statu")
    private SelfMachineEnum statu;

    @ApiModelProperty("客户端版本")
    @Column( name="client_version")
    private String clientVersion;

    @ApiModelProperty("机器型号")
    @Column( name="machine_type")
    private String machineType;

    @ApiModelProperty("自助机类型id")
    @Column( name="machine_type_id")
    private Long machineTypeId;

    @ApiModelProperty("机器编码")
    @Column( name="machine_code")
    private String machineCode;

    @ApiModelProperty("证书编号")
    @Column( name="certificate_code")
    private String certificateCode;
}
