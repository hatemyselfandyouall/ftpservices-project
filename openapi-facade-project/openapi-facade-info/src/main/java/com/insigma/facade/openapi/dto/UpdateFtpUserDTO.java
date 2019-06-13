package com.insigma.facade.openapi.dto;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateFtpUserDTO implements Serializable{

    private static final long serialVersionUID = -1L;

    private String userName;

    private String passWord;

}
