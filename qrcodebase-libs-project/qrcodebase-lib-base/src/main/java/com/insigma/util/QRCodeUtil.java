package com.insigma.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import io.nayuki.qrcodegen.*;

public class QRCodeUtil {
    public static void main(String[] args) throws IOException {
        QrCode qr0 = QrCode.encodeText("Hello, world!", QrCode.Ecc.MEDIUM);
        BufferedImage img = qr0.toImage(4, 10);
        ImageIO.write(img, "png", new File("qr-code.png"));

// Manual operation
        List<QrSegment> segs = QrSegment.makeSegments("3141592653589793238462643383");
//        QrCode qr1 = QrCode.encodeSegments(segs, QrCode.Ecc.HIGH, 5, 5, 2, false);
//        for (int y = 0; y < qr1.size; y++) {
//            for (int x = 0; x < qr1.size; x++) {
//                (... paint qr1.getModule(x, y) ...)
//            }
//        }
    }

    public static BufferedImage toQrImage(String text) throws IOException {
        QrCode qr0 = QrCode.encodeText(text, QrCode.Ecc.MEDIUM);
        BufferedImage img = qr0.toImage(4, 10);
//        ImageIO.write(img, "png", new File("qr-code.png"));
        return img;
    }
}
