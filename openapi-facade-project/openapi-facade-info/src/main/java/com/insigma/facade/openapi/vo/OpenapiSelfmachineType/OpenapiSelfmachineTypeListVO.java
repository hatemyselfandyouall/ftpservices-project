package com.insigma.facade.openapi.vo.OpenapiSelfmachineType;

import com.insigma.facade.openapi.vo.root.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiSelfmachineTypeListVO extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("")
    @Column( name="name")
    private String name;
}
