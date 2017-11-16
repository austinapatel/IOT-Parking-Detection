// from http://www.samundra.com.np/threshold-image-java-code/104/104

package com.amazonaws.samples;

/** Filename : ImageOperations.java **/
/**
 *
 */
//package mts;

import java.awt.image.BufferedImage;

/**
 * @author Asee Shrestha
 *
 */

/**
 * Thresholds the image at the given ThresholdValue
 * @params requiredThresholdValue The threshold value by which the image is to be threshold
 */
public class ImageOperations {

    public static BufferedImage Threshold(BufferedImage img,int requiredThresholdValue) {

        int height = img.getHeight();
        int width = img.getWidth();
        BufferedImage finalThresholdImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB) ;

        int red = 0;
        int green = 0;
        int blue = 0;

        for (int x = 0; x < width; x++) {
//			System.out.println("Row: " + x);
            try {

                for (int y = 0; y < height; y++) {
                    int color = img.getRGB(x, y);

                    red = ImageOperations.getRed(color);
                    green = ImageOperations.getGreen(color);
                    blue = ImageOperations.getBlue(color);

//					System.out.println("Threshold : " + requiredThresholdValue);
                    if((red+green+green)/3 < (int) (requiredThresholdValue)) {
                        finalThresholdImage.setRGB(x,y,ImageOperations.mixColor(0, 0,0));
                    }
                    else {
                        finalThresholdImage.setRGB(x,y,ImageOperations.mixColor(255, 255,255));
                    }

                }
            } catch (Exception e) {
                e.getMessage();
            }
        }

        return finalThresholdImage;
    }

    private static int mixColor(int red, int green, int blue) {
        return red<<16|green<<8|blue;
    }

    public static int getRed(int color) {
        return (color & 0x00ff0000)  >> 16;
    }

    public static int getGreen(int color) {
        return	(color & 0x0000ff00)  >> 8;
    }

    public static int getBlue(int color) {
        return (color & 0x000000ff)  >> 0;

    }

}