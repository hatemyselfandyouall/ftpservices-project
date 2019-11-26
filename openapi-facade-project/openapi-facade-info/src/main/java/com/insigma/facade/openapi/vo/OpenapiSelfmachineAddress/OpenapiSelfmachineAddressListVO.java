package com.insigma.facade.openapi.vo.OpenapiSelfmachineAddress;

import com.insigma.facade.openapi.vo.root.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiSelfmachineAddressListVO extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("")
    @Column( name="address")
    private String address;
}
