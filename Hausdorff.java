import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Hausdorff {
	public static int WIDTH, HEIGHT;
	public static final String DIR = "C:\\Users\\erick\\Documents\\Esteban\\GPU\\vectorAdd2\\bin\\8192\\";
	public static final byte EUCLIDEAN = 0, MANHATAN = 1, SUPREMUM = 2;
	
	public static void main(String[] args) {
		long start = System.nanoTime();
		//boolean[] img1 = getBinaryImage(DIR + "1.bmp"), img2 = getBinaryImage(DIR + "2.bmp");
		//System.out.println(img1[0] + "~~" + img1[1] + "~~" + img2[0] + "~~" + img1[0]);
		for (int k=0; k<10; k++){
			hdCPU(getBinaryImage(DIR + "1.bmp"), getBinaryImage(DIR + "2.bmp"), (byte)2);
		}
		System.out.printf("Elapsed time: %f \n", (System.nanoTime() - start)/(1000000000f));
	}

	
	private static BufferedImage bImg;
	private static Raster raster;
	private static boolean[] getBinaryImage(String path){
		try {
			bImg = ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WIDTH = bImg.getWidth(); HEIGHT = bImg.getHeight();
		raster = bImg.getRaster();
		boolean[] imgB = new boolean[WIDTH*HEIGHT];
		for (int i=0; i<HEIGHT; i++){
			for (int j=0; j<WIDTH; j++){
				//if (i == 0 && j == 0) System.out.println(path + "~~" + raster.getSample(j, i, 0) + "~~" + raster.getSample(j+1, 0, 0));
				//Change the == 0 below according to your imagetype
				imgB[i*WIDTH + j] = (raster.getSample(j, i, 0) == 0); 
			}
		}
		return imgB;
	}
	public static int hdCPU(boolean[] img1, boolean[] img2, byte METRIC){
		int minImg1 = Integer.MAX_VALUE, minImg2 = Integer.MAX_VALUE;
		int maxImg1 = 0, maxImg2 = 0;

		int pIndex1, pIndex2;

		//System.out.printf("Processing images (width=%d, height=%d) \n", WIDTH, HEIGHT);
		
		//System.out.printf("Getting minimum distance from image 1 to image 2. \n");
		//IMG1
		//for every pixel of the first image
		for (int i = 0; i < HEIGHT; i++){
			for (int j = 0; j < WIDTH; j++){
				pIndex1 = i*WIDTH + j;
				if (!img1[pIndex1]) continue;
				//every of the second
				for (int i2 = 0; i2 < HEIGHT; i2++){
					for (int j2 = 0; j2 < WIDTH; j2++){
						pIndex2 = i2*WIDTH + j2;
						if (img2[pIndex2])
							if (d(j, i, j2, i2, METRIC) < minImg1) minImg1 = d(j, i, j2, i2, METRIC);
					}
				}
				maxImg1 = (maxImg1 > minImg1 ? maxImg1 : minImg1);
			}
		}
		//System.out.printf("Getting minimum distance from image 2 to image 1. \n");
		//IMG2
		//for every pixel of the second image
		for (int i = 0; i < HEIGHT; i++){
			for (int j = 0; j < WIDTH; j++){
				pIndex2 = i*WIDTH + j;
				if (!img2[pIndex2]) continue;
				//every of the first
				for (int i2 = 0; i2 < HEIGHT; i2++){
					for (int j2 = 0; j2 < WIDTH; j2++){
						pIndex1 = i2*WIDTH + j2;
						if (img1[pIndex1])
							if (d(j, i, j2, i2, METRIC) < minImg2) minImg2 = d(j, i, j2, i2, METRIC);
					}
				}
				maxImg2 = (maxImg2 > minImg2 ? maxImg2 : minImg2);
			}
		}

		System.out.printf("Hausdorff distance: %d \n", (maxImg1 > maxImg2) ? maxImg1 : maxImg2);
		return (maxImg1 > maxImg2) ? maxImg1 : maxImg2;
	}
	private static int d(int x1, int y1, int x2, int y2, int METRIC){
		if (METRIC == EUCLIDEAN){
			return (int) Math.round(Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1)));
		}
		else if (METRIC == MANHATAN){
			return Math.abs(x2 - x1) + Math.abs(y2 - y1);
		}
		else{//supremum
			return (Math.abs(x2 - x1) > Math.abs(y2 - y1) ? Math.abs(x2 - x1) : Math.abs(y2 - y1));
		}
	}

}
