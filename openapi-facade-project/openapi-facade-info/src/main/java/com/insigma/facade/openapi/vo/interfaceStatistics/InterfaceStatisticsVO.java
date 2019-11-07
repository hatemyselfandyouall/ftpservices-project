package com.insigma.facade.openapi.vo.interfaceStatistics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class InterfaceStatisticsVO implements Serializable {

    @ApiModelProperty("接口id")
    private Long interfaceId;

    @ApiModelProperty("1今日2本周3本月4全年")
    private Integer staticType;

}

