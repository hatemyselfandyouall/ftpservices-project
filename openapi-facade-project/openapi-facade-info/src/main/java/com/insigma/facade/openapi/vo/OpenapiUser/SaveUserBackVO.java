package com.insigma.facade.openapi.vo.OpenapiUser;

import lombok.Data;

import java.io.Serializable;

@Data
public class SaveUserBackVO implements Serializable {

    private static final long serialVersionUID = 8829678930937250722L;

    private Integer flag;

    private String msg;
}
