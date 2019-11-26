package com.insigma.facade.openapi.vo.OpenapiSelfmachine;

import com.insigma.facade.openapi.enums.OpenapiSelfmachineEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Data
public class OpenapiSelfmachineSetOrgVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private List<Long> ids;

    @ApiModelProperty("备注")
    @Column( name="remark")
    private String remark;

    @ApiModelProperty("机构id")
    @Column( name="org_id")
    private Long orgId;


}
