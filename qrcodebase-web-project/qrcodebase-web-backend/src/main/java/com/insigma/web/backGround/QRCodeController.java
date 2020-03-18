package com.insigma.web.backGround;


import com.insigma.util.QRCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import star.vo.result.ResultVo;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

@RestController
@RequestMapping("qrcode")
@Api(tags = "二维码模块")
@Slf4j
public class QRCodeController {

    @ApiOperation(value = "二维码模块")
    @RequestMapping(value = "/textToIMG",produces = {"application/json;charset=UTF-8"})
    public void textToIMG(String text, HttpServletResponse response){
//        ResultVo resultVo=new ResultVo();
        try(OutputStream os=response.getOutputStream()){
            BufferedImage bufferedImage=QRCodeUtil.toQrImage(text);
            ImageIO.write(bufferedImage, "PNG",os);
        }catch (Exception e){
            log.error("二维码生成异常",e);
        }
//        return resultVo;
    }
}
