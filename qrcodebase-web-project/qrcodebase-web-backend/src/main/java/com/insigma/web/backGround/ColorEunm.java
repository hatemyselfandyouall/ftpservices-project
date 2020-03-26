package com.insigma.web.backGround;

public enum ColorEunm {

    GREEN(0x50B061),RED(0xFF4B1A);

    private Integer colerInt;

    ColorEunm(Integer colerInt) {
        this.colerInt = colerInt;
    }

    public Integer getColerInt() {
        return colerInt;
    }
}
