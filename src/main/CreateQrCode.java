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
			qrcodeHandler.setQrcodeVersion(7);//��ά���Ӧ�汾��0-40�����ά��������ݱ䳤��Ҫ��Ӧ�޸Ĵ˰汾��

			System.out.println(content);
			byte[] contentBytes = content.getBytes("gb2312");

			BufferedImage bufImg = new BufferedImage(140, 140, BufferedImage.TYPE_INT_RGB);//��ά�볤�����������QrcodeVersion�ı�ҲҪ��Ӧ�޸�

			Graphics2D gs = bufImg.createGraphics();

			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, 140, 140);//��ά�뱳��ɫ�������ݶ�ά�볤�ȿ���Լ�QrcodeVersion��Ӧ�޸ģ���֤��ά����ʾ��ȫ

			// �趨ͼ����ɫ &gt; BLACK
			gs.setColor(Color.BLACK);
			// ����ƫ���� �����ÿ��ܵ��½�������
			int pixoff = 2;
			// ������� &gt; ��ά��
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
				// ���ɶ�ά��QRCodeͼƬ
				ImageIO.write(bufImg, "png", imgFile);
				System.out.println("���ɶ�ά��ɹ�");
			} else {
				System.err.println("QRCode content bytes length = " + contentBytes.length + " not in [ 0,120 ]. ");
			}

			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("���ɶ�ά��ʧ��");
		}

	}

	public static void main(String[] args) {
		String imgPath = "D:/code.png";
		String content = "https://www.baidu.com";
		CreateQrCode handler = new CreateQrCode();
		handler.encoderQRCode(content, imgPath);
	}
}
