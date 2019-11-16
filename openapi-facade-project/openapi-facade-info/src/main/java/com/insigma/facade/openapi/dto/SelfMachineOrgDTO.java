package com.insigma.facade.openapi.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class SelfMachineOrgDTO implements Serializable {

    @ApiModelProperty("机构id")
    @Column( name="org_id")
    private Long orgId;

    @ApiModelProperty("机构名")
    @Column( name="org_name")
    private String orgName;
}
