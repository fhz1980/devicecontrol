package com.ffait.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class BlackImg {
	//生成黑色底图
	public static BufferedImage pureColorPictures(int width,int height ) {
		//width 生成图宽度
		// height 生成图高度
		//创建一个width xheight ，RGB高彩图，类型可自定
		BufferedImage img=new BufferedImage(width, height , BufferedImage.TYPE_INT_RGB);
		//取得图形
		Graphics g=img.getGraphics();
		//设置颜色
		g.setColor(Color.BLACK);
		//填充
		g.fillRect(0, 0, img.getWidth(), img.getHeight());
		
        return img;
	}
	  public static BufferedImage deepCopy(BufferedImage bi) {

	        ColorModel cm = bi.getColorModel();

	        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();

	        WritableRaster raster = bi.copyData(null);

	        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);

	    }
	public static void drawFace(BufferedImage image){
        Graphics g = image.getGraphics();

        Graphics2D g2 = (Graphics2D)g;  //g是Graphics对象
        g2.setStroke(new BasicStroke(5.0f));
        //画头
        g2.drawOval(350, 280, 270, 340);
        /*//画眼睛
        g.drawOval(400, 340, 80, 50);
        g.drawOval(500, 340, 80, 50);
        //画鼻子
        g.drawArc(140, 150, 100, 150, -90, 90);
        g.drawArc(260, 150, 100, 150, 180, 90);
        //画嘴巴
        g.drawOval(440, 430, 100, 50);*/
    }

    public static BufferedImage drawRect(BufferedImage image,int x,int y,int width,int height) {

        Graphics g = image.getGraphics();
        g.setColor(Color.RED);//画笔颜色
        g.drawRect(x,y,width,height);//矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
        //g.dispose();
        return image;
    }

}
