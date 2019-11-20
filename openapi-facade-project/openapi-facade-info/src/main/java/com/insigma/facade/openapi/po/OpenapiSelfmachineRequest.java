 
package com.insigma.facade.openapi.po;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;

import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class OpenapiSelfmachineRequest implements Serializable{


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("唯一编码")
    @Column( name="unique_code")
    private String uniqueCode;

    @Column( name="org_id")
    private Long orgId;

    @Column( name="number")
    private Integer number;

    @ApiModelProperty("机器编码")
    @Column( name="machine_code")
    private String machineCode;

    @ApiModelProperty("")
    @Column( name="ip")
    private String ip;

    @ApiModelProperty("mac地址")
    @Column( name="mac_address")
    private String macAddress;

    @ApiModelProperty("")
    @Column( name="app_key")
    private String appKey;

    @ApiModelProperty("ip段")
    @Column( name="ip_segment")
    private String ipSegment;

    @ApiModelProperty("机器型号")
    @Column( name="machine_type")
    private String machineType;

    @ApiModelProperty("请求时间")
    @Column( name="request_time")
    private Date requestTime;

    @ApiModelProperty("")
    @Column( name="token")
    private String token;

    @ApiModelProperty("状态")
    @Column( name="statu")
    private SelfMachineEnum statu;


    @ApiModelProperty("客户端版本")
    @Column( name="client_version")
    private String clientVersion;


    @ApiModelProperty("机构名")
    @Column( name="org_name")
    private String orgName;

    @ApiModelProperty("初步校验结果1正常0异常")
    @Column( name="preliminary_calibration")
    private Integer preliminaryCalibration;
}
