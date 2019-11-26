package com.insigma.facade.openapi.vo.OpenapiOrg;

import com.insigma.facade.openapi.po.OpenapiOrg;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiOrgShowVO extends OpenapiOrg implements Serializable {

    @ApiModelProperty("自助机数量")
    private Integer machineCount;
}
