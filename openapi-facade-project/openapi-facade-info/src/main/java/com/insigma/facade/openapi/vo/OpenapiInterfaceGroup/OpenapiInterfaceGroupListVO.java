package com.insigma.facade.openapi.vo.OpenapiInterfaceGroup;

import com.insigma.facade.openapi.vo.root.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiInterfaceGroupListVO extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("1测试环境2正式环境")
    @Column( name="interface_environment")
    private Integer interfaceEnvironment;
}
