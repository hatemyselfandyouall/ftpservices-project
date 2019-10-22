package com.insigma.facade.openapi.vo.OpenapiInterfaceHistory;

import com.insigma.facade.openapi.vo.root.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiInterfaceHistoryListVO extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("接口id")
    @Column( name="interface_id")
    private Long interfaceId;
}
