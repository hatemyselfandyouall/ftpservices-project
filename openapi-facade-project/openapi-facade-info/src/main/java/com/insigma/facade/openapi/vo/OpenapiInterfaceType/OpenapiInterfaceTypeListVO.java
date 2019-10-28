package com.insigma.facade.openapi.vo.OpenapiInterfaceType;

import com.insigma.facade.openapi.vo.root.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class OpenapiInterfaceTypeListVO  implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("1业务分类2应用分类")
    @Column( name="type")
    private Integer type;

    @ApiModelProperty("")
    @Column( name="parent_id")
    private Long parentId;

    private Long pageSize;

    private Long pageNum;
}
