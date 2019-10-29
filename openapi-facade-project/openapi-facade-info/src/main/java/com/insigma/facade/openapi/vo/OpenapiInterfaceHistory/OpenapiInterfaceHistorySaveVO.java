package com.insigma.facade.openapi.vo.OpenapiInterfaceHistory;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class OpenapiInterfaceHistorySaveVO implements Serializable {

    private static final long serialVersionUID = 1L;



    @ApiModelProperty("接口id")
    @Column( name="interface_id")
    private Long interfaceId;

    @ApiModelProperty("更新描述")
    @Column( name="update_description")
    private String updateDescription;

    @ApiModelProperty("版本号")
    @Column( name="version_number")
    private Integer versionNumber;

    @ApiModelProperty("历史版本描述json")
    @Column( name="history_json")
    private JSONObject historyJson;
}
