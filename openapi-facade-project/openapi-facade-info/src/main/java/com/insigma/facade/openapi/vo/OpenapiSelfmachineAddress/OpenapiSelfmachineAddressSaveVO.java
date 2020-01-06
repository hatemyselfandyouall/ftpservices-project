package com.insigma.facade.openapi.vo.OpenapiSelfmachineAddress;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiSelfmachineAddressSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("")
    @Column( name="address")
    private String address;

    @ApiModelProperty("省")
    @Column( name="province")
    private String province;

    @ApiModelProperty("市")
    @Column( name="city")
    private String city;

    @ApiModelProperty("区")
    @Column( name="district")
    private String district;

    @ApiModelProperty("最近一次使用为1，其他0")
    @Column( name="is_last_used")
    private Integer isLastUsed;

}
