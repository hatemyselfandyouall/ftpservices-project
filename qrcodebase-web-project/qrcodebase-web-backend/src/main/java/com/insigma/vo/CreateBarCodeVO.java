package com.insigma.vo;

import com.insigma.web.backGround.ColorEunm;
import lombok.Data;

@Data
public class CreateBarCodeVO {

    private Double moduleWidth;
    private Integer resolution;
    private String text;
}
