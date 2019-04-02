package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;
import jp.sourceforge.qrcode.exception.DecodingFailedException;

public class DecodeQrCode {

	public String decoderQRCode(String imgPath) {

        // QRCode 二维码图片的文件
        File imageFile = new File(imgPath);

        BufferedImage bufImg = null;
        String decodedData = null;
        try {
            bufImg = ImageIO.read(imageFile);

            QRCodeDecoder decoder = new QRCodeDecoder();
            decodedData = new String(decoder.decode(new J2SEImage(bufImg)));

        } catch (IOException e) {
            System.out.println("1Error: " + e.getMessage());
            e.printStackTrace();
        } catch (DecodingFailedException dfe) {
            System.out.println("2Error: " + dfe.getMessage());
            dfe.printStackTrace();
        }
        return decodedData;
    }

    class J2SEImage implements QRCodeImage {
        BufferedImage bufImg;

        public J2SEImage(BufferedImage bufImg) {
            this.bufImg = bufImg;
        }

        public int getWidth() {
            return bufImg.getWidth();
        }

        public int getHeight() {
            return bufImg.getHeight();
        }

        public int getPixel(int x, int y) {
            return bufImg.getRGB(x, y);
        }
    }
    
    public static void main(String[] args) {
    	DecodeQrCode decodeQrCode = new DecodeQrCode();
        String qrcodePath = "D:/code.png";
        String result = decodeQrCode.decoderQRCode(qrcodePath);
        System.out.println("解析结果如下：");
        System.out.println(result);
    }
}
