 
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


@Data
public class OpenapiInterfaceGroup implements Serializable{


	//========== properties ==========

	@Id
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("接口组名")
    @Column( name="name")
    private String name;

    @ApiModelProperty("接口组编码")
    @Column( name="code")
    private String code;

    @ApiModelProperty("1检查数据2检查数据3缴费数据")
    @Column( name="business_type")
    private Integer businessType;

    @ApiModelProperty("1测试环境2正式环境")
    @Column( name="interface_environment")
    private Integer interfaceEnvironment;

    @ApiModelProperty("0未删除1已删除")
    @Column( name="is_delete")
    private Integer isDelete;

    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("")
    @Column( name="modify_time")
    private Date modifyTime;




}
