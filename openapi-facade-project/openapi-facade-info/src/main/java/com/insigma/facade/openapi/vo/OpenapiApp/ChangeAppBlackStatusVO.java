package com.insigma.facade.openapi.vo.OpenapiApp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class ChangeAppBlackStatusVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("接口id")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("0未被黑名单1已被黑名单")
    @Column( name="is_blacked")
    private Integer isBlacked;
}
