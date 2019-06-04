package com.insigma.facade.openapi.vo.OpenapiInterfaceGroup;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiInterfaceGroupSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("接口组名")
    @Column( name="name")
    private String name;

    @ApiModelProperty("1检查数据2检查数据3缴费数据")
    @Column( name="business_type")
    private Integer businessType;

    @ApiModelProperty("1测试环境2正式环境")
    @Column( name="interface_environment")
    private Integer interfaceEnvironment;

}
