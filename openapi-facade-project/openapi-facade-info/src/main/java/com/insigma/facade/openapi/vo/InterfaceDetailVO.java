package com.insigma.facade.openapi.vo;

import lombok.Data;

import java.util.Map;

@Data
public class InterfaceDetailVO {

     String pwd;
    String sign;
     Long pageNum;
    Long pageSize;
    Map<String,Object> whereWord;
    String orderByword;
}
