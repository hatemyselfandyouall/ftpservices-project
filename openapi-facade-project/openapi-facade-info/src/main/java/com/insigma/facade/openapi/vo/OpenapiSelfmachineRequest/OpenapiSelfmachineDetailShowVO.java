package com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest;

import com.insigma.facade.openapi.po.OpenapiSelfmachineRequest;
import com.insigma.facade.openapi.po.SelfMachineEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class OpenapiSelfmachineDetailShowVO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("机构编码")
    @Column( name="org_code")
    private String orgCode;

    @ApiModelProperty("机构名")
    @Column( name="org_name")
    private String orgName;

    @ApiModelProperty("地区id")
    @Column( name="area_id")
    private Long areaId;

    @ApiModelProperty("地区名")
    @Column( name="area_name")
    private String areaName;

    @ApiModelProperty("自助机类型id")
    @Column( name="machine_type_id")
    private Long machineTypeId;

    @ApiModelProperty("自助机类型id")
    @Column( name="machine_type_id")
    private String machineTypeName;

}
