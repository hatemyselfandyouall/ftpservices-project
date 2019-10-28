package com.insigma.facade.openapi.vo.OpenapiAudit;

import com.insigma.facade.openapi.vo.root.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
public class OpenapiAuditListVO extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("是否待处理0-待处理1-已处理")
    private  Integer pending;

    @ApiModelProperty("审核状态0驳回1通过2待审核")
    private Integer auditStatus;

    @ApiModelProperty("关键字查询：接口名称/申请人")
    private String keyWord;

    @ApiModelProperty("使用应用")
    private String application;

    @ApiModelProperty("申请机构")
    private String organize;

    @ApiModelProperty("开始时间")
    private Date startDate;

    @ApiModelProperty("结束时间")
    private Date endDate;
}
