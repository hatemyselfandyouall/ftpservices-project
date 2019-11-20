package com.insigma.facade.openapi.vo.OpenapiSelfmachine;

import com.insigma.facade.openapi.vo.root.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiSelfmachineListVO extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String keyWord;

    @ApiModelProperty("所属机构")
    @Column( name="org_name")
    private String orgName;

    @ApiModelProperty("机器型号")
    @Column( name="machine_type")
    private String machineType;
}
