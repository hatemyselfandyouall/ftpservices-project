package com.insigma.util;

import org.apache.commons.lang3.StringUtils;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 一维条形码类
 * @data 2019-05-07
 * @author pillar
 */
public class BarCodeUtil {
    /**
     *具体实现
     * @param msg
//     * @param path
     */
    public static BufferedImage getBarCode(Double moduleWidth,Integer resolution,String msg){
        try {
            if(StringUtils.isEmpty(msg) )
                return null;
            //选择条形码类型(好多类型可供选择)
            Code128Bean bean=new Code128Bean();
            //设置长宽
//            final double moduleWidth=0.10;
//            final int resolution=300;

            bean.setModuleWidth(moduleWidth);
            bean.doQuietZone(false);
//            String format = "image/png";
            // 输出流
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                    resolution, BufferedImage.TYPE_BYTE_BINARY, false, 0);
            //生成条码
            bean.generateBarcode(canvas,msg);
            BufferedImage bufferedImage=canvas.getBufferedImage();
            return bufferedImage;
//            canvas.finish();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        String msg = "pillar666";
        String path = "D:\\pillar\\pilar666.jpg";
//        BarCodeUtil.getBarCode(msg,path);
    }
}