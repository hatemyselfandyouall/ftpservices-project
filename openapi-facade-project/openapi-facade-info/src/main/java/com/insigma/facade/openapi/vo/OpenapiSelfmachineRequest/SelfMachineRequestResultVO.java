package com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SelfMachineRequestResultVO {

    private String token;

    private String machineCode;
}
