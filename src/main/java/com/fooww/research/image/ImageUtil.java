package com.fooww.research.image;

import com.fooww.research.image.phash.ImagePHash;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

/**
 * @author ：zwy
 * @date ：Created in 2019/8/26 19:11
 */
public class ImageUtil {

    public static BufferedImage resize(BufferedImage image,int width,int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    public static int[][] garyScale(BufferedImage bufferedImage){
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        ColorConvertOp colorConvert = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        colorConvert.filter(bufferedImage, bufferedImage);
        int[][] pixel = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixel[x][y] = (bufferedImage.getRGB(x, y)) & 0xff;
            }
        }
        return pixel;
    }
}
