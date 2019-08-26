package com.fooww.research.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

/**
 * @author ：zwy
 * @date ：Created in 2019/8/26 13:17
 */
public class ImageUtil {

  public static int[][][] getRGBPixel(String filePath) {
    File file = new File(filePath);
    BufferedImage bi = null;
    try {
      bi = ImageIO.read(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    assert bi != null;
    int width = bi.getWidth();
    int height = bi.getHeight();
    int minX = bi.getMinX();
    int minY = bi.getMinY();

    int[][][] rgbPixel = new int[3][width - minX][height - minY];

    int[][] rPixel = rgbPixel[0];
    int[][] gPixel = rgbPixel[1];
    int[][] bPixel = rgbPixel[2];

    for (int i = minX; i < width; i++) {
      for (int j = minY; j < height; j++) {
        int pixel = bi.getRGB(i, j);
        rPixel[i - minX][j - minY] = (pixel & 0xff0000) >> 16;
        gPixel[i - minX][j - minY] = (pixel & 0xff00) >> 8;
        bPixel[i - minX][j - minY] = (pixel & 0xff);
      }
    }
    return rgbPixel;
  }

  public static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
    // targetW，targetH分别表示目标长和宽
    int type = source.getType();
    BufferedImage target = null;
    double sx = (double) targetW / source.getWidth();
    double sy = (double) targetH / source.getHeight();

    //等比缩放
    if (sx < sy) {
      sy = sx;
      targetH = (int) (sx * source.getHeight());
    } else {
      sx = sy;
      targetW = (int) (sy * source.getWidth());
    }
    if (type == BufferedImage.TYPE_CUSTOM) { // handmade
      ColorModel cm = source.getColorModel();
      WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
      boolean alphaPremultiplied = cm.isAlphaPremultiplied();
      target = new BufferedImage(cm, raster, alphaPremultiplied, null);
    } else {
      target = new BufferedImage(targetW, targetH, type);
    }
    Graphics2D g = target.createGraphics();
    // smoother than exlax:
    g.setRenderingHint(
        RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
    g.dispose();
    return target;
  }
}
