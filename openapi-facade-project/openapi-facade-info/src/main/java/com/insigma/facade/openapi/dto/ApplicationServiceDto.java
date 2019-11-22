 
package com.insigma.facade.openapi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors(chain = true)
public class ApplicationServiceDto implements Serializable{


	//========== properties ==========
    @ApiModelProperty("应用id")
    private String applicationId;

    @ApiModelProperty("应用名称")
    private String applicationName;
}
