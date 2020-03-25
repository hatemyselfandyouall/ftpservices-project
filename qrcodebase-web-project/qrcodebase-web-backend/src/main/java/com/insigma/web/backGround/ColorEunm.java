package com.insigma.web.backGround;

public enum ColorEunm {

    GREEN(0xFF228B22),BLACK(0x11111111);

    private Integer colerInt;

    ColorEunm(Integer colerInt) {
        this.colerInt = colerInt;
    }

    public Integer getColerInt() {
        return colerInt;
    }
}
