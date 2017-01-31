package com.inetpsa.tsf.selenium.utils;

import org.junit.Test;

import com.inetpsa.tsf.selenium.TSFConstant;
import com.inetpsa.tsf.selenium.TSFSeleniumServer;
import com.inetpsa.tsf.selenium.report.TSFReport;

/**
 * Stop selenium test
 */
public class TSFStopNavigateur extends TSFSeleniumServer { 

	 /**
     *  Make it possible to carry out the tearDown of super class
     *  @throws Exception error 
     */
	@Test
	public void testFin() throws Exception {
		if (TSFSeleniumServer.isWebDriverLoc()) {
	    	driver = TSFSeleniumServer.getWebDriverLoc();
			driver.close();
			setDriverLoc(null);
			if (TSFConstant.IE_BROWSER.equals(getBrowserLoc())) {
			    killProcess(); 
			}
		} else {
			if (selenium != null) {
				selenium.stop();
				selenium = null;
			}
			serveur =  TSFSeleniumServer.getServerLoc();	
			serveur.stop();
			setServerLoc(null);
		}
		setSeleniumActions(null);
		setWebDriverLoc(false);
		setBrowserLoc(null);
		if (TSFSeleniumServer.isActivateReportLoc()) {
			exportHtml();
		}
	}
		
	 /**
     *  Export html report
     *  @throws Exception error 
     */
	public void exportHtml() throws Exception {
		TSFReport.getInstance().exportResultByVM(TSFReport.getInstance().getAssertionslist(), 
				TSFReport.getInstance().getLogList(), getReportDate(), getReportPath());
	}
}

