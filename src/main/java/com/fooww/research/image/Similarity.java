package com.fooww.research.image;

import java.awt.image.BufferedImage;

/**
 * @author ：zwy
 * @date ：Created in 2019/8/26 14:29
 */
public class Similarity {

  public static Float getImageSimilarity(BufferedImage bufferedImage1, BufferedImage bufferedImage2) {
    String hashCode1 = DHash.getImageDHash(bufferedImage1);
    String hashCode2 = DHash.getImageDHash(bufferedImage2);

    System.out.println(hashCode1+"\t"+hashCode2);

    int maxDistance = Math.max(hashCode1.length(), hashCode2.length());
    int hammingDistance = getHammingDistance(hashCode1, hashCode2);
    if (hammingDistance == -1) {
      return 0F;
    } else {
      return 1 - hammingDistance / (float) maxDistance;
    }
  }

  private static int getEditDistance(String string1, String string2) {
    if (string1.equals(string2)) {
      return 0;
    }
    int x = string1.length();
    int y = string2.length();
    int[][] distanceMatrix = new int[x + 1][y + 1];
    distanceMatrix[0][0] = 0;
    for (int i = 1; i < x; i++) {
      distanceMatrix[i][0] = distanceMatrix[i - 1][0] + 1;
    }
    for (int i = 1; i < y; i++) {
      distanceMatrix[0][i] = distanceMatrix[0][i] + 1;
    }
    int cost;
    for (int i = 1; i < x; i++) {
      for (int j = 1; j < y; j++) {
        if (string1.charAt(i - 1) == string2.charAt(j - 1)) {
          cost = 0;
        } else {
          cost = 1;
        }
        distanceMatrix[i][j] =
            Math.min(
                distanceMatrix[i - 1][j] + 1,
                Math.min(distanceMatrix[i][j - 1] + 1, distanceMatrix[i - 1][j - 1] + cost));
      }
    }
    return distanceMatrix[x-1][y-1];
  }

  private static int getHammingDistance(String string1, String string2) {

    if (string1.length() != string2.length()) {
      return -1;
    }

    int distance = 0;
    for (int i = 0; i < string1.length(); i++) {
      if (string1.toCharArray()[i] != string2.toCharArray()[i]) {
        distance++;
      }
    }
    return distance;
  }
}
