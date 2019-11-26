package com.insigma.facade.openapi.vo.OpenapiSelfmachineType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiSelfmachineTypeDetailVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

}
