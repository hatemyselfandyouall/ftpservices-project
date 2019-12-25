package com.insigma.facade.openapi.vo;

import lombok.Data;

import java.util.Map;

@Data
public class InterfaceDetailVO {

    private String pwd;
    private String sign;
    private Long pageNum;
    private Long pageSize;
    private Map<String,Object> whereWord;
    private String orderByword;
}
