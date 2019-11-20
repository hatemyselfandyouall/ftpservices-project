package com.insigma.facade.openapi.vo.OpenapiSelfmachineRequest;

import com.insigma.facade.openapi.po.SelfMachineEnum;
import com.insigma.facade.openapi.vo.root.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiSelfmachineRequestListVO extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String keyWord;

    @ApiModelProperty("初步校验结果1正常0异常")
    @Column( name="preliminary_calibration")
    private Integer preliminaryCalibration;

    @ApiModelProperty("机器型号")
    @Column( name="machine_type")
    private String machineType;
}
