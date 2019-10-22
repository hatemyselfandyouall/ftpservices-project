package com.insigma.facade.openapi.vo.openapiInterface;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

@Data
public class OpenapiInterfaceSetStatusVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("接口id")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("0未生效1已生效")
    @Column( name="is_avaliable")
    private Integer isAvaliable;
}

