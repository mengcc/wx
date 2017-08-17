package com.mzs.main.admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;

@Clear()
public class VerifyCode extends Controller {
	
	public void index(){
		try {
			HttpServletRequest request = this.getRequest();
			HttpServletResponse response = this.getResponse();
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("image/jpeg");
			// 验证码边框的长高
			int width = 55;
			int height = 24;
			// 用RGB模式输出图像区域
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			// 定义画笔
			Graphics graph = image.getGraphics();
			// 设置验证码框背景色0-255
			graph.setColor(new Color(220, 220, 220));
			// 填充矩形
			graph.fillRect(0, 0, width, height);
			// 产生1000-9999之间的随机数
			String code[] = { "2", "3", "4", "5", "6", "7", "8", "9", "2", "3", "4", "5", "6", "7", "8", "9", "2", "3", "4", "5", "6", "7", "8", "9" };
			String rndStr = "";
			Random rnd = new Random();
			for (int i = 0; i < 4; i++) {
				rndStr += code[rnd.nextInt(24)];
			}
			HttpSession session = request.getSession();
			session.setAttribute("varifycode", rndStr.toUpperCase());
			// 设置矩形区域中随机数及干扰点的颜色
			graph.setColor(Color.RED);
			// 设置随机数的字体大小
			graph.setFont(new Font("", Font.PLAIN, 19));
			// 在已有的矩形区域中绘制随机数
			graph.drawString(rndStr, 1, 19);
			// 随机产生100个干扰点
			/*
			 * for (int i = 0; i < 100; i++) { int x = rnd.nextInt(width); int y
			 * = rnd.nextInt(height); //设置干扰点的位置长宽 graph.drawOval(x, y, 1, 1);
			 * //这里是加干扰点的，把它去掉了。 }
			 */
			// 将图像输出到页面上
			graph.dispose();
			ImageIO.write(image, "JPEG", response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderNull();
	}
}
