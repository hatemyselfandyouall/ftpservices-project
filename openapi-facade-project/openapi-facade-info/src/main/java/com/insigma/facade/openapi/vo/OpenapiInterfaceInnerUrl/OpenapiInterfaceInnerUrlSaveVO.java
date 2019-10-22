package com.insigma.facade.openapi.vo.OpenapiInterfaceInnerUrl;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiInterfaceInnerUrlSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("")
    @Column( name="interface_id")
    private Long interfaceId;


}
