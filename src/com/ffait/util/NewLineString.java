package com.ffait.util;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;




public class NewLineString {

	/**
     * 将字符串改为每length个像素添加一个换行
     * @param s	  输入字符
     * @param limitWidth 限制长度
     * @return
     */
    public static String StringLine(String s,int limitWidth){

        s = s.replaceAll("\\s*", "");

        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true);
        Font font = new Font("楷体", Font.PLAIN, 12);

        StringBuilder builder = new StringBuilder();
        int tmp = 0;
        for (int i = 1; i < s.length(); i++)
        {
            if ((int)(font.getStringBounds(s.substring(tmp, i + 1), frc).getWidth()) > limitWidth)
            {
                builder.append(s.substring(tmp, i));
                builder.append("\n");
                tmp = i;
            }
        }
        builder.append(s.substring(tmp));
        return builder.toString();
    }
}
