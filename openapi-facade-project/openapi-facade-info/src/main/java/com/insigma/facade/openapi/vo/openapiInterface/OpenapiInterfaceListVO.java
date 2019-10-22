package com.insigma.facade.openapi.vo.openapiInterface;

import com.insigma.facade.openapi.vo.root.PageVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiInterfaceListVO extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("")
    @Column( name="type_id")
    private Long typeId;

    @ApiModelProperty("0未发布1已发布")
    @Column( name="is_public")
    private Integer isPublic;

    @ApiModelProperty("0未生效1已生效")
    @Column( name="is_avaliable")
    private Integer isAvaliable;

    @ApiModelProperty("name")
    @Column( name="name")
    private String name;


}
