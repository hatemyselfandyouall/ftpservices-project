package com.insigma.facade.openapi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.io.Serializable;

@Data
public class SelfMachineOrgDTO implements Serializable {

    @ApiModelProperty("机构编码")
    @Column( name="org_code")
    private String orgCode;

    @ApiModelProperty("机构名")
    @Column( name="org_name")
    private String orgName;

    @Transient
    private Integer count;
}
