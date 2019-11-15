 
package com.insigma.facade.openapi.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


@Data
@Accessors(chain = true)
public class OpenapiMonitoringDataConfig implements Serializable{


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("接口id")
    @Column( name="interface_id")
    private Long interfaceId;

    @ApiModelProperty("平均调用时长大于")
    @Column( name="average_call_time")
    private Integer averageCallTime;

    @ApiModelProperty("单次调用时长")
    @Column( name="single_call_duration")
    private Integer singleCallDuration;

    @ApiModelProperty("预警时间间隔")
    @Column( name="warning_interval")
    private Integer warningInterval;

    @ApiModelProperty("报警时间间隔")
    @Column( name="alarm_interval")
    private Integer alarmInterval;

    @ApiModelProperty("一小时失败次数大于")
    @Column( name="number_of_failures")
    private Integer numberOfFailures;

    @ApiModelProperty("预警方式")
    @Column( name="early_warning_method")
    private Integer earlyWarningMethod;

    @ApiModelProperty(" 预警对象")
    @Column( name="early_warning_object")
    private String earlyWarningObject;

    @ApiModelProperty("0未删除1已删除")
    @Column( name="is_delete")
    private Integer isDelete;

    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("")
    @Column( name="modify_time")
    private Date modifyTime;

    @ApiModelProperty("")
    @Column( name="create_by")
    private Long createBy;

    @ApiModelProperty("")
    @Column( name="modify_by")
    private Long modifyBy;

}
