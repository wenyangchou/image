package com.fooww.research.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author ：zwy
 * @date ：Created in 2019/8/26 13:48
 */
public class DHash {

  public static String getPixelDHashCode(int[][] grayScale) {
    int height = grayScale.length;
    int width = grayScale[0].length;
    int[][] hashCodeArray = new int[height][width - 1];
    for (int i = 0; i < height; i++) {
      for (int j = 1; j < width; j++) {
        if (grayScale[i][j] > grayScale[i][j - 1]) {
          hashCodeArray[i][j - 1] = 1;
        } else {
          hashCodeArray[i][j - 1] = 0;
        }
      }
    }
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < height; i++) {
      stringBuilder.append(intArrayToChar(hashCodeArray[i]));
    }
    return stringBuilder.toString();
  }

  public static String getImageDHash(BufferedImage bufferedImage){
    BufferedImage targetBi = ImageUtil.resize(bufferedImage, 9, 8);
    int[][] grayScalePixel = GrayScale.getRedGrayScale(targetBi);
    return DHash.getPixelDHashCode(grayScalePixel);
  }

  public static String getImageDHash(String fileURI){
    File file = new File(fileURI);
    if (file.exists()){
      BufferedImage bufferedImage = null;
      try {
         bufferedImage = ImageIO.read(file);
      } catch (IOException e) {
        e.printStackTrace();
      }
      return getImageDHash(bufferedImage);
    }else{
      return "";
    }
  }

  private static char intArrayToChar(int[] ints) {
    int ascii = 0;
    int binaryBase = 1;

    for (int i = ints.length - 1; i >= 0; i--) {
      ascii += binaryBase * ints[i];
      binaryBase *= 2;
    }
    return (char) ascii;
  }
}
