package com.insigma.facade.openapi.vo.openapiInterface;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class InterfaceStatisticsByAreaVO implements Serializable {

    @ApiModelProperty("接口id")
    private Long interfaceId;

    @ApiModelProperty("1今日2本周3本月4全年")
    private Integer staticType;

    @ApiModelProperty("1调用量2失败量3超时量")
    private Integer type;

}
