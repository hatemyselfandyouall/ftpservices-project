package com.insigma.facade.openapi.vo.openapiInterface;

import com.insigma.facade.openapi.po.OpenapiInterfaceHistory;
import com.insigma.facade.openapi.po.OpenapiInterfaceInnerUrl;
import com.insigma.facade.openapi.vo.OpenapiInterfaceHistory.OpenapiInterfaceHistorySaveVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceInnerUrl.OpenapiInterfaceInnerUrlSaveVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceRequestParam.OpenapiInterfaceRequestParamSaveVO;
import com.insigma.facade.openapi.vo.OpenapiInterfaceResponseParam.OpenapiInterfaceResponseParamSaveVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Data
public class OpenapiInterfaceSaveVO implements Serializable {

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

    @ApiModelProperty("0不是回调1是回调")
    @Column( name="is_callback")
    private Integer isCallback;

    @ApiModelProperty("接口描述")
    @Column( name="distribution")
    private String distribution;

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

    @ApiModelProperty("规则url")
    @Column( name="role_url")
    private String roleUrl;

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

    @ApiModelProperty("0未删除1已删除")
    @Column( name="is_delete")
    private Integer isDelete;

    @ApiModelProperty("提供者姓名")
    @Column( name="provider_name")
    private String providerName;

    @ApiModelProperty("提供者类型")
    @Column( name="provider_type")
    private String providerType;

    @ApiModelProperty("外部请求方式1GET2POST3POST/GET")
    @Column( name="out_request_way")
    private Integer outRequestWay;

    @ApiModelProperty("内部请求方式1GET2POST3POST/GET")
    @Column( name="inner_request_way")
    private Integer innerRequestWay;

    @ApiModelProperty("外部支持格式1JSON")
    @Column( name="out_request_format")
    private Integer outRequestFormat;

    @ApiModelProperty("内部支持格式1JSON")
    @Column( name="inner_request_format")
    private Integer innerRequestFormat;

    @ApiModelProperty("内部请求对象1政务服务2省数据中心3.自定义")
    @Column( name="inner_request_object")
    private Integer innerRequestObject;

    @ApiModelProperty("请求参数样例")
    @Column( name="request_param_example")
    private String requestParamExample;

    @ApiModelProperty("返回参数样例")
    @Column( name="response_param_example")
    private String responseParamExample;

    @ApiModelProperty("接口模式1透传")
    @Column( name="request_model")
    private Integer requestModel;

    @ApiModelProperty("创建者姓名")
    @Column( name="creator_name")
    private String creatorName;

    @ApiModelProperty("创建者id")
    @Column( name="creator_id")
    private Long creatorId;

    @ApiModelProperty("接口提供者类型")
    @Column( name="interface_producer_type")
    private String interfaceProducerType;

    @ApiModelProperty("开放权限1全部2白名单")
    @Column( name="open_access")
    private Integer openAccess;

    private List<OpenapiInterfaceRequestParamSaveVO> openapiInterfaceRequestParamSaveVOList;

    private List<OpenapiInterfaceResponseParamSaveVO> openapiInterfaceResponseParamSaveVOList;

    private List<OpenapiInterfaceInnerUrlSaveVO> openapiInterfaceInnerUrlSaveVOS;

    private OpenapiInterfaceHistorySaveVO openapiInterfaceHistorySaveVO;
    private static final long serialVersionUID = 1L;

}
