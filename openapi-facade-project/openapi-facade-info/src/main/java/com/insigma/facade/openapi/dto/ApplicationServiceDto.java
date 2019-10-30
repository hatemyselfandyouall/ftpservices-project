 
package com.insigma.facade.openapi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class ApplicationServiceDto implements Serializable{


	//========== properties ==========
    @ApiModelProperty("应用id")
    private String applicationId;

    @ApiModelProperty("应用名称")
    private String applicationName;
}
