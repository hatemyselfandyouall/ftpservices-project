package com.insigma.facade.openapi.vo.OpenapiInterfaceGroup;

import com.insigma.facade.openapi.po.OpenapiInterface;
import com.insigma.facade.openapi.po.OpenapiInterfaceGroup;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OpenapiInterfaceGroupDetailShowVO extends OpenapiInterfaceGroup implements Serializable {

    private static final long serialVersionUID = -4336524690205592711L;
    List<OpenapiInterface> interfaces;
}
