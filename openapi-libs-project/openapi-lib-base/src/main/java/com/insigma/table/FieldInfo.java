package com.insigma.table;

import lombok.Data;

import java.io.Serializable;

@Data
public class FieldInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    //字段名
    private String name;
    //sql字段名
    private String sqlName;
    //字段类型
    private String type;

    private Integer typeLength;
    //字段注释
    private String remark;
    //属于主键
    private Boolean isPrimary;

    private String allasName;
}
