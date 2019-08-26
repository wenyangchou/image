package com.fooww.research.image.phash;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @desc  相似图片识别（直方图）
 */
public class ImageHistogram {

	private final static int RED_BINS = 4;
	private final static int GREEN_BINS = 4;
	private final static int BLUE_BINS = 4;

	private static float[] filter(BufferedImage src) {
		int width = src.getWidth();
		int height = src.getHeight();

		int[] inPixels = new int[width * height];
		float[] histogramData = new float[RED_BINS * GREEN_BINS * BLUE_BINS];
		getRGB(src, width, height, inPixels);
		int index = 0;
		int redIdx = 0, greenIdx = 0, blueIdx = 0;
		int singleIndex = 0;
		float total = 0;
		for (int row = 0; row < height; row++) {
			int tr = 0, tg = 0, tb = 0;
			for (int col = 0; col < width; col++) {
				index = row * width + col;
				tr = (inPixels[index] >> 16) & 0xff;
				tg = (inPixels[index] >> 8) & 0xff;
				tb = inPixels[index] & 0xff;
				redIdx = (int) getBinIndex(RED_BINS, tr);
				greenIdx = (int) getBinIndex(GREEN_BINS, tg);
				blueIdx = (int) getBinIndex(BLUE_BINS, tb);
				singleIndex = redIdx + greenIdx * RED_BINS + blueIdx * RED_BINS * GREEN_BINS;
				histogramData[singleIndex] += 1;
				total += 1;
			}
		}

		// start to normalize the histogram data
		for (int i = 0; i < histogramData.length; i++) {
			histogramData[i] = histogramData[i] / total;
		}

		return histogramData;
	}

	private static float getBinIndex(int binCount, int color) {
		float binIndex = (((float) color) / ((float) 255)) * ((float) binCount);
		if (binIndex >= binCount)
			binIndex = binCount - 1;
		return binIndex;
	}

	private static void getRGB(BufferedImage image, int width, int height, int[] pixels) {
		int type = image.getType();
		if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB) {
			image.getRaster().getDataElements(0, 0, width, height, pixels);
			return;
		}
		image.getRGB(0, 0, width, height, pixels, 0, width);
	}
	
	/**
	 * Bhattacharyya Coefficient
	 * http://www.cse.yorku.ca/~kosta/CompVis_Notes/bhattacharyya.pdf
	 * 
	 * @return	返回值大于等于0.8可以简单判断这两张图片内容一致
	 * @throws IOException
	 */
	public static double match(File srcFile, File canFile) throws IOException {
		float[] sourceData = filter(ImageIO.read(srcFile));
		float[] candidateData = filter(ImageIO.read(canFile));
		return calcSimilarity(sourceData, candidateData);
	}

	/**
	 * @return	返回值大于等于0.8可以简单判断这两张图片内容一致
	 * @throws IOException
	 */
	public static double match(URL srcUrl, URL canUrl) throws IOException {
		float[] sourceData = filter(ImageIO.read(srcUrl));
		float[] candidateData = filter(ImageIO.read(canUrl));
		return calcSimilarity(sourceData, candidateData);
	}

	private static double calcSimilarity(float[] sourceData, float[] candidateData) {
		double[] mixedData = new double[sourceData.length];
		for (int i = 0; i < sourceData.length; i++) {
			mixedData[i] = Math.sqrt(sourceData[i] * candidateData[i]);
		}

		// The values of Bhattacharyya Coefficient ranges from 0 to 1,
		double similarity = 0;
		for (int i = 0; i < mixedData.length; i++) {
			similarity += mixedData[i];
		}

		// The degree of similarity
		return similarity;
	}

}
