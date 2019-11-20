package com.insigma.facade.openapi.vo.OpenapiSelfmachine;

import com.insigma.facade.openapi.po.SelfMachineEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiSelfmachineDeleteVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("自助机id")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("校验状态")
    @Column( name="statu")
    private SelfMachineEnum statu;

}
