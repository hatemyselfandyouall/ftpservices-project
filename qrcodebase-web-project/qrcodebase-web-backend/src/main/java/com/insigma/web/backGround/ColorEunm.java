package com.insigma.web.backGround;

public enum ColorEunm {

    GREEN(0x50B061),RED(0xFF4B1A),YELLOW(0xFFC216),GREY(0x8A8A8A);

    private Integer colerInt;

    ColorEunm(Integer colerInt) {
        this.colerInt = colerInt;
    }

    public Integer getColerInt() {
        return colerInt;
    }
}
