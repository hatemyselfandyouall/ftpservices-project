package com.insigma.facade.openapi.vo.OpenapiUser;

import com.insigma.facade.openapi.vo.root.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiUserListVO extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("机构名称、编号、联系人模糊查询字段")
    private String name;

}
