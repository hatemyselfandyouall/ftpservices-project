package com.insigma.facade.openapi.vo.OpenapiOrg;

import com.insigma.facade.openapi.vo.root.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiOrgListVO extends PageVO implements Serializable {

    @ApiModelProperty("机构id")
    @Column( name="org_id")
    private Long orgId;


    @ApiModelProperty("地区id")
    @Column( name="area_id")
    private Long areaId;

    private static final long serialVersionUID = 1L;

}
