package com.insigma.vo;

import com.insigma.facade.sysbase.vo.SysFunctionDTO;

import java.io.Serializable;

public class SysFunctionVO extends SysFunctionDTO implements Serializable {

    private static final transient long serialVersionUID = -1L;

    private String absoluteUrl;

    private Boolean hasPrivilege;

    public String getAbsoluteUrl() {
        return absoluteUrl;
    }

    public void setAbsoluteUrl(String absoluteUrl) {
        this.absoluteUrl = absoluteUrl;
    }


    public Boolean getHasPrivilege() {
        return hasPrivilege;
    }

    public void setHasPrivilege(Boolean hasPrivilege) {
        this.hasPrivilege = hasPrivilege;
    }
}
