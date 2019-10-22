 
package com.insigma.facade.openapi.po;

import java.io.Serializable;

import lombok.experimental.Accessors;
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
@Accessors(chain = true)
public class OpenapiInterface implements Serializable{


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("接口id")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("")
    @Column( name="code")
    private String code;

    @ApiModelProperty("接口名称")
    @Column( name="name")
    private String name;

    @ApiModelProperty("接口组id")
    @Column( name="group_id")
    private Long groupId;

    @ApiModelProperty("")
    @Column( name="type_id")
    private Long typeId;

    @ApiModelProperty("")
    @Column( name="type_name")
    private String typeName;

    @ApiModelProperty("数据提供单位编码")
    @Column( name="data_resource_code")
    private String dataResourceCode;

    @ApiModelProperty("接口摘要")
    @Column( name="summary")
    private String summary;

    @ApiModelProperty("版本号")
    @Column( name="version_number")
    private Integer versionNumber;

    @ApiModelProperty("接口内部url")
    @Column( name="inner_url")
    private String innerUrl;

    @ApiModelProperty("接口外部url")
    @Column( name="out_url")
    private String outUrl;

    @ApiModelProperty("接口请求类型0get1post")
    @Column( name="request_type")
    private Integer requestType;

    @ApiModelProperty("接收外部参数类型0json")
    @Column( name="out_param_type")
    private Integer outParamType;

    @ApiModelProperty("接收内部参数类型0json")
    @Column( name="inner_param_type")
    private Integer innerParamType;

    @ApiModelProperty("命令参数编码")
    @Column( name="command_code")
    private String commandCode;

    @ApiModelProperty("参数请求范例")
    @Column( name="request_example")
    private String requestExample;

    @ApiModelProperty("0未生效1已生效")
    @Column( name="is_avaliable")
    private Integer isAvaliable;

    @ApiModelProperty("0未发布1已发布")
    @Column( name="is_public")
    private Integer isPublic;

    @ApiModelProperty("提供者姓名")
    @Column( name="provider_name")
    private String providerName;

    @ApiModelProperty("提供者类型")
    @Column( name="provider_type")
    private String providerType;

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
