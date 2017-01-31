package com.inetpsa.tsf.selenium.screenshot;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.inetpsa.tsf.selenium.screenshot.source.NoWayFindXPathException;
import com.inetpsa.tsf.selenium.screenshot.source.XPathInvalidException;

/**
 * GenericBitmapAnalyzer
 * @author e365712
 **/
public class ECWBitmapAnalyzer extends GenericBitmapAnalyzer {
	
	/** The data replaced to by a fixed date */
	private static final String MODIFIER_DATE = "01/01/2000 01:01:01";
	
	/** The data format regex */
	private static final String DATE_FORMAT_REGEX = "\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2}";
	
	/** Remove the ecw message bar */
	private static void replaceMessageBar() {
		replaceSource("<div[^>/].*? id=\"EcwMessageBar\">(.*?)</div>", "");
	}
	
	/** Remove the line '<html:base/>' in the html */
	private static void replaceHtmlBase() {
		replaceSource("<base[^>/].*?>", "");
	}
	
	/** Remove 'Mise à jour le......' in the html */
	private static void replaceCurrentDate() {
		replaceSource("&nbsp;Mise à jour le&nbsp;<span class=\"ecwCurrentDate\">[^<]*</span>", "");
	}
	
	/** Remove the line 'http://.*com/' in the html */
	private static void replaceHtmlCom() {
		replaceSource("http://.*com/", "");
	}
	
	/**
	 * Remove the js in the html
	 * * @param id the path of js name
	 */
	private static void replaceEcwScript(String id) {
		String originHtml = "";
		String replaceHtml = "";
		String script = "<script type='text/javascript' src='";
		String patternStr = "<script.*src.*"+ id +".js.*>";  
		Pattern pattern = Pattern.compile(patternStr);  
        Matcher matcher = pattern.matcher(modifyHtml.getSource());  
        while (matcher.find()) {
        	if (matcher.group().contains(id)) {
        		originHtml = matcher.group();                             
        		replaceHtml = script + matcher.group().substring(matcher.group().indexOf(id), matcher.group().length());
        		modifyHtml.setSource(modifyHtml.getSource().replace(originHtml, replaceHtml.replaceAll("\"", "\'")));
        	}
        }
	}
	
	/** Remove focus in the html */
	private static void replaceFocus(){
		replaceSource("ecwTableLineActive", "");
	}
	
	/**
	 * Remove the table data
	 * @throws NoWayFindXPathException exception
	 * @throws XPathInvalidException exception
	 */
	private static void replaceTable() throws NoWayFindXPathException, XPathInvalidException {
		Map<String, String> tableMap = new LinkedHashMap<String, String>();
		if (modifyHtml.getSource().contains("ecwTableBody")) {
			tableMap.put("//div[@class='ecwTableBody']", "");
			replaceAllZones(tableMap);
		}
	}
	
	/**
	 * Remove the date
	 * @throws NoWayFindXPathException exception
	 * @throws XPathInvalidException exception
	 */
	private static void replaceDate() throws NoWayFindXPathException, XPathInvalidException {
			modifyHtml.replaceText(DATE_FORMAT_REGEX, MODIFIER_DATE);
	}
	
	/**
	 * Capture page
	 * @param path the path of screenshot
	 * @param zones zones which should be remove or modify 
	 * @throws NoWayFindXPathException exception
	 * @throws XPathInvalidException exception
	 */
	public static void capturePage(String path, Map<String, String> zones) throws NoWayFindXPathException, XPathInvalidException {
		replaceMessageBar();
		replaceHtmlBase();
		replaceCurrentDate();
		replaceHtmlCom();
		replaceEcwScript("ecw");
		replaceEcwScript("ltp");
		replaceTable();
		replaceDate();
		GenericBitmapAnalyzer.capturePage(path, zones);
	}
	
	/**
	 * Capture page with table data
	 * @param path the path of screenshot
	 * @param zones zones which should be remove or modify 
	 * @throws NoWayFindXPathException exception
	 * @throws XPathInvalidException exception
	 */
	public static void capturePageWithTableData(String path, Map<String, String> zones) throws NoWayFindXPathException, XPathInvalidException {
		replaceMessageBar();
		replaceHtmlBase();
		replaceCurrentDate();
		replaceHtmlCom();
		replaceEcwScript("ecw");
		replaceEcwScript("ltp");
		replaceDate();
		replaceFocus();
		GenericBitmapAnalyzer.capturePage(path, zones);
	}
}
