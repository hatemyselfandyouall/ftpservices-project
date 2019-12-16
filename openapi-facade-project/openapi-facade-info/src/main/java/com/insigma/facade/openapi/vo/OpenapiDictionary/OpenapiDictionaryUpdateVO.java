package com.insigma.facade.openapi.vo.OpenapiDictionary;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiDictionaryUpdateVO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("安全监控访问次数预警值")
    private String value;
}
