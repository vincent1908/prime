package com.inetpsa.tsf.selenium.screenshot;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.commons.collections.MapUtils;

import com.inetpsa.tsf.selenium.TSFConstant;
import com.inetpsa.tsf.selenium.screenshot.source.NoWayFindXPathException;
import com.inetpsa.tsf.selenium.screenshot.source.Position;
import com.inetpsa.tsf.selenium.screenshot.source.XPathInvalidException;
import com.inetpsa.tsf.selenium.screenshot.source.Xpath;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * GenericBitmapAnalyzer
 * @author e365712
 **/
public class GenericBitmapAnalyzer {
    /** LOGGER */
    protected static final Logger LOG = Logger.getLogger("TSF");
	
	/** Difference between two images should be less than this range  */
	private static final int ACCEPT_RANGE = 6;
	
	/** modifyHtml */
	public static Xpath modifyHtml;
	
	/** 
	 * SetModifyHtml 
	 * @param modifyHtml Xpath
	 */
	public static void setModifyHtml(Xpath modifyHtml) {
		GenericBitmapAnalyzer.modifyHtml = modifyHtml;
	}
	
	/** 
	 * Replace source 
	 * @param originHtml String
	 * @param replaceHtml String
	 */
	public static void replaceSource(String originHtml, String replaceHtml) {
		modifyHtml.setSource(modifyHtml.getSource().replaceAll(originHtml, replaceHtml));
	}
	
	/**
	 * Remove all the script data
	 * @throws NoWayFindXPathException exception
	 * @throws XPathInvalidException exception
	 */
	private static void replaceScript() throws NoWayFindXPathException, XPathInvalidException {
		if (modifyHtml.getSource().contains("script")){
			modifyHtml.removeAllFieldXPath("//script[!@type]");
		}
		//add the window.onerror to avoid the js error pop up
		modifyHtml.addFieldXPath("//body", "<script type=\"text/javascript\">\n"+
				"window.onerror=function(msg){}\n" + "</script>");
	}
	
	/**
	 * Replace zones
	 * @param zones zones which should be remove or modify 
	 * @throws NoWayFindXPathException exception
	 * @throws XPathInvalidException exception
	 */
	public static void replaceZones(Map<String, String> zones) throws NoWayFindXPathException, XPathInvalidException {
		if (MapUtils.isNotEmpty(zones)) {
			Iterator<Map.Entry<String, String>> iterator = zones.entrySet().iterator();
			while (iterator.hasNext()) {
	 			Map.Entry<String, String> entry = iterator.next();
	 			String area = entry.getKey();
				String value = entry.getValue();
				if (area.startsWith("//")) {
				    modifyHtml.replaceFieldXPath(area, value);
				} else {
				    modifyHtml.setSource(modifyHtml.getSource().replaceAll(area, value));
				}
			}
		}
	}
	
	/**
	 * Replace all zones
	 * @param zones zones which should be remove or modify 
	 * @throws NoWayFindXPathException exception
	 * @throws XPathInvalidException exception
	 */
	public static void replaceAllZones(Map<String, String> zones) throws NoWayFindXPathException, XPathInvalidException {
		if (MapUtils.isNotEmpty(zones)) {
			Iterator<Map.Entry<String, String>> iterator = zones.entrySet().iterator();
			while (iterator.hasNext()) {
	 			Map.Entry<String, String> entry = iterator.next();
	 			String area = entry.getKey();
				String value = entry.getValue();
				modifyHtml.replaceAllFieldXPath(area, value);
			}
		}
	}
	
	/**
	 * Capture page
	 * @param path the path of screenshot
	 * @param zones zones which should be remove or modify 
	 * @throws NoWayFindXPathException exception
	 * @throws XPathInvalidException exception
	 */
	public static void capturePage(String path, Map<String, String> zones) throws NoWayFindXPathException, XPathInvalidException {
		replaceScript();
		replaceZones(zones);
		clearMap(zones);
		modifyHtml.writeSource(path + TSFConstant.HTML_SOURCE);
	}
	
	/**
	 * Clear the map
	 * @param zones zones which should be remove or modify 
	 */
	private static void clearMap(Map<String, String> zones){
		if (MapUtils.isNotEmpty(zones)) {
			zones.clear();
		}
	}
	
	/**
	 * Change RGB to XYZ
	 * 
	 * @param rgb int
	 * @return xyz
	 */
	public static ColorXYZ rgb2xyz(int rgb) {
		ColorXYZ xyz = new ColorXYZ();
		int r = (rgb & 0xff0000) >> 16;
		int g = (rgb & 0xff00) >> 8;
		int b = (rgb & 0xff);
		if ((r == 0) && (g == 0) && (b == 0)) {
			xyz.x = 0;
			xyz.y = 0;
			xyz.z = 0;
		} else {
			xyz.x = (0.490 * r + 0.310 * g + 0.200 * b) / (0.667 * r + 1.132 * g + 1.200 * b);
			xyz.y = (0.117 * r + 0.812 * g + 0.010 * b) / (0.667 * r + 1.132 * g + 1.200 * b);
			xyz.z = (0.000 * r + 0.010 * g + 0.990 * b) / (0.667 * r + 1.132 * g + 1.200 * b);
		}
		return xyz;
	}

	/**
	 * Change XYZ to LAB
	 * 
	 * @param xyz ColorXYZ
	 * @return lab
	 */
	public static ColorLAB xyz2lab(ColorXYZ xyz) {
		ColorLAB lab = new ColorLAB();
		double x = xyz.x / 95.047;
		double y = xyz.y / 100.000;
		double z = xyz.z / 108.883;
		x = (x > 0.008856) ? Math.pow(x, 1.0 / 3.0) : (7.787 * x + 16.0 / 116);
		y = (y > 0.008856) ? Math.pow(y, 1.0 / 3.0) : (7.787 * y + 16.0 / 116);
		z = (z > 0.008856) ? Math.pow(z, 1.0 / 3.0) : (7.787 * z + 16.0 / 116);
		lab.l = 116 * Math.pow(y, 1.0 / 3.0) - 16;
		lab.a = 500 * (Math.pow(x, 1.0 / 3.0) - Math.pow(y, 1.0 / 3.0));
		lab.b = 200 * (Math.pow(y, 1.0 / 3.0) - Math.pow(z, 1.0 / 3.0));
		return lab;
	}

	/**
	 * Calculate the color difference
	 * 
	 * @param lab1 ColorLAB
	 * @param lab2 ColorLAB
	 * @return totalColorDifference
	 */
	public static double getDelta(ColorLAB lab1, ColorLAB lab2) {
		// lightness difference
		double deltaL = lab1.l - lab2.l; 
		// chromaticity difference
		double deltaA = lab1.a - lab2.a; 
		// chromaticity difference
		double deltaB = lab1.b - lab2.b; 
		// total color difference
		return Math.pow((Math.pow(deltaL, 2) + Math.pow(deltaA, 2) + Math.pow(deltaB, 2)), 0.5); 
	}
	
	/**
	 * Contrast images
	 * 
	 * @param referencePath String
	 * @param testPath String
	 * @return isMatched boolean
	 * @throws IOException exception
	 */
	public static boolean contrastImages(String referencePath, String testPath) throws IOException {
		String differenceImagePath = testPath + TSFConstant.ERROR_SCREEN;
		BufferedImage reference = null;
		BufferedImage test = null;
		BufferedImage difference = null;
		boolean isMatched = true;
		final int rgbError = Color.black.getRGB();
		final int rgbSuccess = Color.white.getRGB();		
		try {
			reference = ImageIO.read(new File(referencePath + TSFConstant.PNG));
			test = ImageIO.read(new File(testPath + TSFConstant.PNG));
		} catch (IOException e) {
		    LOG.log(Level.WARNING, "IOException for contrastImages", e);
		}
		difference = new BufferedImage(reference.getWidth(), reference.getHeight(), BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < Math.min(reference.getWidth(), test.getWidth()); x++) {
			for (int y = 0; y < Math.min(reference.getHeight(), test.getHeight()); y++) {
				int expRGB = reference.getRGB(x, y);
				int actRGB = test.getRGB(x, y);
				int newRGB = rgbSuccess;
				if (expRGB != actRGB) {
					double deltaE = getDelta(xyz2lab(rgb2xyz(expRGB)), xyz2lab(rgb2xyz(actRGB)));
					if (deltaE < ACCEPT_RANGE) {
						newRGB = rgbError;// set black in difference place
						isMatched = false;
					} else {
						newRGB = rgbSuccess;
					}
				}
				difference.setRGB(x, y, newRGB);
			}
		}
		FileOutputStream out = null;
		if (!isMatched) {
			try {
				out = new FileOutputStream(differenceImagePath + TSFConstant.PNG);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(difference);
			} catch (IOException e) {
			    LOG.log(Level.WARNING, "IOException", e);
			} finally {
				out.close();
			}
		}
		return isMatched;
	}
	
	/**
	 * Contrast images
	 * 
	 * @param previousImagePath String
	 * @param followingImagePath String
	 * @param positions List
	 * @return isMatched boolean
	 * @throws IOException exception
	 */
	public static boolean contrastImagesWithPosition(String previousImagePath, String followingImagePath, List<Position> positions) throws IOException {
		BufferedImage previousImage = null;
		BufferedImage followingImage = null;
		BufferedImage difference = null;
		String differenceImagePath = previousImagePath.substring(0, previousImagePath.indexOf(TSFConstant.PRE)) + TSFConstant.ERROR_SCREEN;
		boolean isMatched = true;
		final int rgbError = Color.black.getRGB();
		final int rgbSuccess = Color.white.getRGB();		
		try {
			previousImage = ImageIO.read(new File(previousImagePath));
			followingImage = ImageIO.read(new File(followingImagePath));
		} catch (IOException e) {
		    LOG.log(Level.WARNING, "IOException for contrastImagesWithPosition", e);
		}
		difference = new BufferedImage(followingImage.getWidth(), followingImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < Math.min(followingImage.getWidth(), previousImage.getWidth()); x++) {
			for (int y = 0; y < Math.min(followingImage.getHeight(), previousImage.getHeight()); y++) {
				boolean isIgnored = false;
				for (Position position : positions) {
					if (position.isIgnored(x, y)) {
						isIgnored = true;
						break;
					}
				}
				int expRGB = followingImage.getRGB(x, y);
				int actRGB = previousImage.getRGB(x, y);
				int newRGB = rgbSuccess;
				if (isIgnored) {
					newRGB = rgbSuccess;
				} else if (expRGB != actRGB) {
					double deltaE = getDelta(xyz2lab(rgb2xyz(expRGB)), xyz2lab(rgb2xyz(actRGB)));
					if (deltaE < ACCEPT_RANGE) {
						newRGB = rgbError;// set black in difference place
						isMatched = false;
					} else {
						newRGB = rgbSuccess;
					}
				}
				difference.setRGB(x, y, newRGB);
			}
		}
		FileOutputStream out = null;
		if (!isMatched) {
			try {
				out = new FileOutputStream(differenceImagePath + TSFConstant.PNG);
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(difference);
			} catch (IOException e) {
			    LOG.log(Level.WARNING, "IOException ", e);
			} finally {
				out.close();
			}
		}
		return isMatched;
	}
	
	/**
	 * RGB color space
	 * @author e365712
	 */
	public static class ColorLAB {
	    /** color l*/
		public double l;
		/** color a*/
		public double a;
		/** color b*/
		public double b;
	}
	
	/**
	 * XYZ color space
	 * @author e365712	
	 */
	public static class ColorXYZ {
	    /** color x*/
		public double x;
		/** color y*/
		public double y;
		/** color z*/
		public double z;
	}
}
