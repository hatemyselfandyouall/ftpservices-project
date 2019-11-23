package com.insigma.facade.openapi.vo.OpenapiOrgShortname;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiOrgShortnameDeleteVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("接口id")
    @Column( name="id")
    private Long id;



}
