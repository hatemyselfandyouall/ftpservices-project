package com.insigma.facade.openapi.vo.root;

import java.io.Serializable;

public class PageVO implements Serializable {
    private static final long serialVersionUID = 9068457090592963844L;
    private Long pageSize;
    private Long pageNum;

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getPageNum() {
        return pageNum;
    }

    public void setPageNum(Long pageNum) {
        this.pageNum = pageNum;
    }
}
