 
package com.insigma.facade.openapi.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


@Data
public class OpenapiEarlyWarning implements Serializable{


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("预警人员姓名")
    @Column( name="name")
    private String name;

    @ApiModelProperty("联系人电话")
    @Column( name="phone")
    private String phone;

    @ApiModelProperty("邮箱地址")
    @Column( name="mailbox")
    private String mailbox;

    @ApiModelProperty("备注")
    @Column( name="remarks")
    private String remarks;

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
