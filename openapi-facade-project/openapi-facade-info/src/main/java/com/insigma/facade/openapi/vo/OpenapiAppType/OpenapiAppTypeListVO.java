package com.insigma.facade.openapi.vo.OpenapiAppType;

import com.insigma.facade.openapi.vo.root.PageVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class OpenapiAppTypeListVO extends PageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String keyWord;
}
