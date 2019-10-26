package com.insigma.facade.openapi.vo.OpenapiAppInterface;

import com.insigma.facade.openapi.vo.root.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiAppInterfaceListVO extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("应用id")
    @Column( name="app_id")
    private Long appId;

    @ApiModelProperty("搜索名")
    private String keyword;

    @ApiModelProperty("1系统分配2自主申请")
    @Column( name="source_type")
    private Integer sourceType;
}
