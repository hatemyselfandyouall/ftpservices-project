package com.insigma.facade.employcode.vo.root;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
public class PageVO implements Serializable {
    private static final long serialVersionUID = 9068457090592963844L;
    private Long pageSize;
    private Long pageNum;
}
