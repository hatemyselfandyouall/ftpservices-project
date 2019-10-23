package com.insigma.facade.openapi.vo.OpenapiInterfaceType;

import com.alibaba.fastjson.JSONObject;
import com.insigma.facade.openapi.po.OpenapiInterface;
import com.insigma.facade.openapi.po.OpenapiInterfaceType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class OpenapiInterfaceTypeTreeVO implements Serializable {

    @Id
    @GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("")
    @Column( name="name")
    private String name;

    @ApiModelProperty("1业务分类2应用分类")
    @Column( name="type")
    private Integer type;

    @ApiModelProperty("使用的应用类型1医保2医院")
    @Column( name="app_type")
    private String appType;

    @ApiModelProperty("备注")
    @Column( name="remark")
    private String remark;

    @ApiModelProperty("")
    @Column( name="parent_id")
    private Long parentId;

    @ApiModelProperty("0未删除1已删除")
    @Column( name="is_delete")
    private Integer isDelete;

    @ApiModelProperty("")
    @Column( name="create_time")
    private Date createTime;

    @ApiModelProperty("")
    @Column( name="modify_time")
    private Date modifyTime;

    List<OpenapiInterfaceTypeTreeVO> childrenTree;

    List<OpenapiInterface> childrenNode;

    List<JSONObject> tempChildren;
}
