package com.fooww.research.image;

import com.fooww.research.image.dhash.ImageDHash;
import com.fooww.research.image.distance.Distance;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * @author ：zwy
 * @date ：Created in 2019/8/26 17:59
 */
public class Main {

  //beb147d04df6c80
  //befba1b295778cc8
  public static void main(String[] args) throws Exception {
    URL url1 = new URL("https://img.fooww.com/group5/M02/3A/CA/ZyjCG1zEiqyAAdT_AAZA4nVIvnQ778.jpg");
    BufferedImage bufferedImage1 = ImageIO.read(url1);

    URL url2 = new URL("https://img.fooww.com/group3/M04/38/EE/ZyjCHl0lIReEc7HvAAAAAK9ILFk003.jpg");
    BufferedImage bufferedImage2 = ImageIO.read(url2);

    String hash1 = ImageDHash.getDHash(bufferedImage1);
    String hash2 = ImageDHash.getDHash(bufferedImage2);

    System.out.println(Distance.getHammingDistance(hash1, hash2));
  }


}
