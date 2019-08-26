package com.fooww.research;

import com.fooww.research.image.DHash;
import com.fooww.research.image.GrayScale;
import com.fooww.research.image.ImageUtil;
import com.fooww.research.image.Similarity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @author ：zwy
 * @date ：Created in 2019/8/26 13:09
 */
public class Main {

  public static void main(String[] args) throws Exception {
    File file = new File("C:\\Users\\fooww\\Desktop\\image-1.png");
    File file1 = new File("C:\\Users\\fooww\\Desktop\\save.png");

    BufferedImage bi1 = ImageIO.read(file);
    BufferedImage bi2 = ImageIO.read(file1);
    System.out.println(Similarity.getImageSimilarity(bi1, bi2));
  }
}
