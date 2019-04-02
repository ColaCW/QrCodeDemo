package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class CreateQrCode {
	public void encoderQRCode(String content, String imgPath) {
		try {

			Qrcode qrcodeHandler = new Qrcode();
			qrcodeHandler.setQrcodeErrorCorrect('M');
			qrcodeHandler.setQrcodeEncodeMode('B');
			qrcodeHandler.setQrcodeVersion(7);//二维码对应版本，0-40，想二维码包含内容变长就要对应修改此版本号

			System.out.println(content);
			byte[] contentBytes = content.getBytes("gb2312");

			BufferedImage bufImg = new BufferedImage(140, 140, BufferedImage.TYPE_INT_RGB);//二维码长宽，根据上面的QrcodeVersion改变也要对应修改

			Graphics2D gs = bufImg.createGraphics();

			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, 140, 140);//二维码背景色长宽，根据二维码长度宽度以及QrcodeVersion对应修改，保证二维码显示完全

			// 设定图像颜色 &gt; BLACK
			gs.setColor(Color.BLACK);
			// 设置偏移量 不设置可能导致解析出错
			int pixoff = 2;
			// 输出内容 &gt; 二维码
			if (contentBytes.length > 0 && contentBytes.length < 320) {
				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
				for (int i = 0; i < codeOut.length; i++) {
					for (int j = 0; j < codeOut.length; j++) {
						if (codeOut[j][i]) {
							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
						}
					}
				}
				gs.dispose();
				bufImg.flush();
				File imgFile = new File(imgPath);
				// 生成二维码QRCode图片
				ImageIO.write(bufImg, "png", imgFile);
				System.out.println("生成二维码成功");
			} else {
				System.err.println("QRCode content bytes length = " + contentBytes.length + " not in [ 0,120 ]. ");
			}

			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("生成二维码失败");
		}

	}

	public static void main(String[] args) {
		String imgPath = "D:/code.png";
		String content = "https://www.baidu.com";
		CreateQrCode handler = new CreateQrCode();
		handler.encoderQRCode(content, imgPath);
	}
}
