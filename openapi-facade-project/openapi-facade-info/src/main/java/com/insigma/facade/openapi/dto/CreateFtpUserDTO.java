package com.insigma.facade.openapi.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

@Data
public class CreateFtpUserDTO  implements Serializable{

    private static final long serialVersionUID = -1L;

    private String userName;

    private String passWord;

    @ApiParam("是否生效0生效1失效")
    private Integer isWork;
}
