package com.insigma.facade.openapi.vo.OpenapiOrg;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
public class OpenapiOrgSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("机构id")
    @Column( name="org_id")
    private Long orgId;

    @ApiModelProperty("机构名")
    @Column( name="org_name")
    private String orgName;

    @ApiModelProperty("地区id")
    @Column( name="area_id")
    private Long areaId;

    @ApiModelProperty("地区名")
    @Column( name="area_name")
    private String areaName;

    @ApiModelProperty("ip网段")
    @Column( name="ip_segment")
    private String ipSegment;

    @ApiModelProperty("限制数量")
    @Column( name="limit_count")
    private String limitCount;

    @ApiModelProperty("允许访问时间-起")
    @Column( name="access_time_start")
    private Date accessTimeStart;

    @ApiModelProperty("允许访问时间-止")
    @Column( name="access_time_final")
    private Date accessTimeFinal;

    @ApiModelProperty("描述")
    @Column( name="distribution")
    private String distribution;

}
