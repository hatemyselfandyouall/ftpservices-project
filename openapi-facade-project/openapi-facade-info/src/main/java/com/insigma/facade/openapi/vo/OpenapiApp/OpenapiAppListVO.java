package com.insigma.facade.openapi.vo.OpenapiApp;

import com.insigma.facade.openapi.vo.root.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiAppListVO extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("应用名")
    @Column( name="name")
    private String name;

    @ApiModelProperty("渠道来源1管理系统2开放平台")
    @Column( name="channel_source")
    private Integer channelSource;

    @ApiModelProperty("所属机构")
    @Column( name="org_name")
    private String orgName;

    @ApiModelProperty("应用类型id")
    @Column( name="typeId")
    private Long typeId;
}
