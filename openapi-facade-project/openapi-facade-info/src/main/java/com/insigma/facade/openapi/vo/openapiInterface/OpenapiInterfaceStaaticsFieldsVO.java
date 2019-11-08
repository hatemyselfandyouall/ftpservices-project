package com.insigma.facade.openapi.vo.openapiInterface;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class OpenapiInterfaceStaaticsFieldsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column( name="TIME")
    private String time;

    @Column(name="totalCount")
    private Integer totalCount;
}
