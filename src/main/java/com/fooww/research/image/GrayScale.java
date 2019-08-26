package com.fooww.research.image;

import java.awt.image.BufferedImage;

/**
 * @author ：zwy
 * @date ：Created in 2019/8/26 13:16
 */
public class GrayScale {

    //横向 表达
  public static int[][] getRedGrayScale(BufferedImage bufferedImage) {
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();
    int[][] redPixel = new int[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
          int pixel = bufferedImage.getRGB(j, i);
          redPixel[i][j] = (pixel & 0xff0000) >> 16;
      }
    }
    return redPixel;
  }
}
