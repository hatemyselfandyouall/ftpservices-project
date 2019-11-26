 
package com.insigma.facade.openapi.po;

import java.io.Serializable;

import com.insigma.facade.openapi.enums.OpenapiSelfmachineEnum;
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
public class OpenapiSelfmachine implements Serializable{


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("唯一编码")
    @Column( name="unique_code")
    private String uniqueCode;

    @ApiModelProperty("")
    @Column( name="ip")
    private String ip;

    @ApiModelProperty("mac地址")
    @Column( name="mac_address")
    private String macAddress;

    @ApiModelProperty("操作系统编码")
    @Column( name="system_code")
    private String systemCode;

    @ApiModelProperty("机构名")
    @Column( name="org_name")
    private String orgName;

    @ApiModelProperty("机构id")
    @Column( name="org_id")
    private Long orgId;

    @ApiModelProperty("自助机类型id")
    @Column( name="machine_type_id")
    private Long machineTypeId;

    @ApiModelProperty("自助机地址id")
    @Column( name="machine_address_id")
    private Long machineAddressId;

    @ApiModelProperty("自助机实际地址")
    @Column( name="machine_address")
    private String machineAddress;

    @ApiModelProperty("备注")
    @Column( name="remark")
    private String remark;

    @ApiModelProperty("激活状态")
    @Column( name="active_statu")
    private OpenapiSelfmachineEnum activeStatu;

    @ApiModelProperty("创建时间")
    @Column( name="create_time")
    private Date createTime;

}
