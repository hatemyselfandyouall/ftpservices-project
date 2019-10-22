package com.insigma.facade.openapi.vo.openapiInterface;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

@Data
public class OpenapiInterfaceReleaseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("接口id")
    @Column( name="id")
    private List<Long> ids;

    @ApiModelProperty("0未发布1已发布")
    @Column( name="is_public")
    private Integer isPublic;
}
