package com.insigma.web.backGround;


import com.insigma.util.BarCodeUtil;
import com.insigma.util.QRCodeUtil;
import com.insigma.vo.CreateBarCodeVO;
import com.insigma.vo.CreateIMGVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
    @RequestMapping(value = "/textToIMG",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public void textToIMG(@RequestBody CreateIMGVO createIMGVO,HttpServletResponse response){
//        ResultVo resultVo=new ResultVo();
        try(OutputStream os=response.getOutputStream()){
            String text=createIMGVO.getText();
            String imgPath=createIMGVO.getImgPath();
            Integer color=Integer.valueOf(createIMGVO.getColorEunm().getColerInt());
            BufferedImage bufferedImage=QRCodeUtil.encodeReturnBufferImg(text,imgPath,null,true,color);
            ImageIO.write(bufferedImage, "PNG",os);
        }catch (Exception e){
            log.error("二维码生成异常",e);
        }
//        return resultVo;
    }

    @ApiOperation(value = "生成条形码")
    @RequestMapping(value = "/textToBarCode",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public void textToBarCode(@RequestBody CreateBarCodeVO createBarCodeVO, HttpServletResponse response){
//        ResultVo resultVo=new ResultVo();
        try(OutputStream os=response.getOutputStream()){
            String text=createBarCodeVO.getText();
            Double moduleWidth=createBarCodeVO.getModuleWidth()!=null?createBarCodeVO.getModuleWidth():0.2;
            Integer resolution=createBarCodeVO.getResolution()!=null?createBarCodeVO.getResolution():300;
            BufferedImage bufferedImage= BarCodeUtil.getBarCode(moduleWidth,resolution,text);
            ImageIO.write(bufferedImage, "PNG",os);
        }catch (Exception e){
            log.error("二维码生成异常",e);
        }
//        return resultVo;
    }
}
