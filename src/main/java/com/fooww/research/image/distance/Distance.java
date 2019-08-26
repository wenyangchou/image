package com.fooww.research.image.distance;

/**
 * @author ：zwy
 * @date ：Created in 2019/8/26 19:32
 */
public class Distance {

  public static int getHammingDistance(String str1, String str2) {
    System.out.println(str1.length() + "\t" + str2.length());
    int distance = 0;
    if (str1.length() != str2.length()) {
      return -1;
    }

    for (int i = 0; i < str1.length(); i++) {
      if (str1.charAt(i) != str2.charAt(i)) {
        distance++;
      }
    }
    return distance;
  }
}
