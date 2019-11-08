package com.insigma.facade.openapi.vo.openapiInterface;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class OpenapiInterfaceStaaticsVO implements Serializable {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("总接口数")
    private Integer totaliInterfaceCount;

    @ApiModelProperty("近期发布接口数")
    private Integer newInterfaceCount;

    @ApiModelProperty("同比")
    private Double compareTotal;

    @ApiModelProperty("环比")
    private Double cmpareSomeTimesBeford;

    private List<OpenapiInterfaceStaaticsFieldsVO> openapiInterfaceStaaticsFieldsVOS;


}
