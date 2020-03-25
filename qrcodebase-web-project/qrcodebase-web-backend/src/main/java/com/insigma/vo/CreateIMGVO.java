package com.insigma.vo;

import com.insigma.web.backGround.ColorEunm;
import de.muntjak.tinylookandfeel.controlpanel.ColoredFont;
import lombok.Data;

import java.io.Serializable;

@Data
public class CreateIMGVO{

    private String  text;
    private String imgPath;
    private ColorEunm colorEunm;
}
