package com.yedamFinal.aco.emoticon.web;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
public class ImageTest {
  public static void main(String[] args) {
    File imageFile = new File("/D:/img/EMO.jpg");
    BufferedImage bufferedImage = null;
    try {
      bufferedImage = ImageIO.read(imageFile);
      int w = 230;
      int h = 220;
      int xm = 55;
      int ym = 40;
      
      
     
      for(int i=1; i<6; i++) {
    	  for(int j=1; j<7; j++) {
    		  int x = (j-1)*w + xm;
    		  int y = (i-1)*h + ym ;  
    		  System.out.println(x+":"+y);
		      BufferedImage image = bufferedImage.getSubimage(x, y, w, h);
		      File pathFile = new File(String.format("/D:/img/EMO%d%d.jpg", i,j));
		      ImageIO.write(image, "jpg", pathFile);
    	  }
      }
      
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}