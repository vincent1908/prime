package com.inetpsa.tsf.selenium;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.server.SeleniumServer;

import com.inetpsa.tsf.selenium.support.ISeleniumActions;
import com.thoughtworks.selenium.Selenium;

/**
 * TSFSeleniumServer
 * @author e365712
 */
public class TSFSeleniumServer {
	
	/** Logger. */
	private static final Logger LOG = Logger.getLogger("TSF");
	
	/** Use this object to run all of your selenium tests */
	protected Selenium selenium;

	/** judge if it uses web driver */
	private boolean isWebDriver;
	
	/** judge if it keeps browser open */
	private boolean keepBrowserOpen;
	
	/** judge if it uses report */
	private boolean activateReport;
	
	/** The path of report */
	private static String reportPath;
	
	/** The path of screen */
	private static String screenPath;
	
	/** judge if it uses grid */
	private boolean isGrid;

	/** the instance for launching the Selenium */
	protected SeleniumServer serveur;

	/** the instance for launching the WebDriver */
	protected WebDriver driver;

	/** the methods for selenium operations */
	private ISeleniumActions seleniumActions;

	/** The browser. */
	private String browser = TSFConstant.DEFAULT_BROWSER;
	
	/** objet to judge if it uses webdriver */
	private static boolean isWebDriverLoc;
	
	/** object to judge if it uses grid */
	private static boolean isGridLoc;
	
	/** object to judge which browser path it uses */
	private static String browserPathLoc;
	
	/** objet to judge which browser it uses */
	private static String browserLoc;
	
	/** objet to judge which reportPath it uses */
	private static String reportPathLoc;
	
	/** objet to judge which screenPath it uses */
	private static String screenPathLoc;
	
	/** objet server for webDreiver */
	private static WebDriver driverLoc;
	
	/** objet judge if it activates Report  */
	private static boolean activateReportLoc;
	
	/** objet server for selenium */
	private static SeleniumServer serverLoc;
	
	/** object url for selenium */
	private static String urlLoc;
	
	/** object context for selenium */
	private static String contextLoc;
	
	/** object language for selenium */
	private static String languageLoc;
	
	/** object timeOutLoc */
	private static String timeOutLoc;
	
	/** object timeToWait */
	private static String timeToWaitLoc;
	
	/** object reportDate */
	private static String reportDate;

	/** object timeToWait */
	private static boolean keepBrowserOpenLoc;
	
	/** object htmlPath */
	private static String htmlPathLoc;
	
	/** object ieDriverPath */
    private static String ieDriverPathLoc;
    
    /**
     * Get ieDriverPathLoc.
     * @return ieDriverPathLoc
     */
    public static String getIeDriverPathLoc() {
        return ieDriverPathLoc;
    }
    
    /**
     * Set ieDriverPathLoc.
     * @param ieDriverPathLoc the ieDriverPathLoc to set
     */
    public static void setIeDriverPathLoc(String ieDriverPathLoc) {
        TSFSeleniumServer.ieDriverPathLoc = ieDriverPathLoc;
    }

    /**
     * Get htmlPathLoc.
     * @return htmlPathLoc
     */
    public static String getHtmlPathLoc() {
        return htmlPathLoc;
    }
    
    /**
     * Set htmlPathLoc.
     * @param htmlPathLoc the htmlPathLoc to set
     */
    public static void setHtmlPathLoc(String htmlPathLoc) {
        TSFSeleniumServer.htmlPathLoc = htmlPathLoc;
    }
    
	/**
	 * Get isWebDriverLoc.
	 * @return isWebDriverLoc
	 */
	public static boolean isKeepBrowserOpenLoc() {
		return keepBrowserOpenLoc;
	}

	/**
	 * Set isKeepBrowserOpenLoc.
	 * @param keepBrowserOpenLoc the keepBrowserOpenLoc to set
	 */
	public static void setKeepBrowserOpenLoc(boolean keepBrowserOpenLoc) {
		TSFSeleniumServer.keepBrowserOpenLoc = keepBrowserOpenLoc;
	}
	
	/**
	 * Get isKeepBrowserOpen.
	 * @return keepBrowserOpen
	 */
	public boolean isKeepBrowserOpen() {
		return keepBrowserOpen;
	}

	/**
	 * Set setKeepBrowserOpen.
	 * @param keepBrowserOpen the keepBrowserOpen to set
	 */
	public void setKeepBrowserOpen(boolean keepBrowserOpen) {
		this.keepBrowserOpen = keepBrowserOpen;
	}
	
	/**
	 * Get screenPath.
	 * @return screenPath
	 */
	public static String getScreenPath() {
		return screenPath;
	}

	/**
	 * Set screenPath.
	 * @param screenPath the screenPath to set
	 */
	public static void setScreenPath(String screenPath) {
		TSFSeleniumServer.screenPath = screenPath;
	}

	/**
	 * Get screenPathLoc.
	 * @return screenPathLoc
	 */
	public static String getScreenPathLoc() {
		return screenPathLoc;
	}

	/**
	 * Set screenPathLoc.
	 * @param screenPathLoc the screenPathLoc to set
	 */
	public static void setScreenPathLoc(String screenPathLoc) {
		TSFSeleniumServer.screenPathLoc = screenPathLoc;
	}

	/**
	 * Get reportDate.
	 * @return reportDate
	 */
	public static String getReportDate() {
		return reportDate;
	}
	
	/**
	 * Set reportDate.
	 * @param reportDate the reportDate to set
	 */
	public static void setReportDate(String reportDate) {
		TSFSeleniumServer.reportDate = reportDate;
	}
	
	/**
	 * Get timeOutLoc.
	 * @return timeOutLoc
	 */
	public static String getTimeOutLoc() {
		return timeOutLoc;
	}

	/**
	 * Set timeOutLoc.
	 * @param timeOutLoc the timeOutLoc to set
	 */
	public static void setTimeOutLoc(String timeOutLoc) {
		TSFSeleniumServer.timeOutLoc = timeOutLoc;
	}

	/**
	 * Get timeToWaitLoc.
	 * @return timeToWaitLoc
	 */
	public static String getTimeToWaitLoc() {
		return timeToWaitLoc;
	}

	/**
	 * Set timeToWaitLoc.
	 * @param timeToWaitLoc the timeToWaitLoc to set
	 */
	public static void setTimeToWaitLoc(String timeToWaitLoc) {
		TSFSeleniumServer.timeToWaitLoc = timeToWaitLoc;
	}

	/**
	 * Get isWebDriver.
	 * @return isWebDriver
	 */
	public boolean isWebDriver() {
		return isWebDriver;
	}

	/**
	 * Set ModeFieldDataEntry.
	 * @param isWebDriver the isWebDriver to set
	 */
	public void setIsWebDriver(boolean isWebDriver) {
		this.isWebDriver = isWebDriver;
	}
	
	/**
	 * Get isActivateReport.
	 * @return activateReport
	 */
	public boolean isActivateReport() {
		return activateReport;
	}

	/**
	 * setActivateReport.
	 * @param activateReport the activateReport to set
	 */
	public void setActivateReport(boolean activateReport) {
		this.activateReport = activateReport;
	}
	
	/**
	 * Get reportPath.
	 * @return reportPath
	 */
	public String getReportPath() {
		return reportPath;
	}

	/**
	 * setReportPath.
	 * @param reportPath the reportPath to set
	 */
	public void setReportPath(String reportPath) {
		TSFSeleniumServer.reportPath = reportPath;
	}
	
	/**
	 * Get isGrid.
	 * @return isGrid
	 */
	public boolean isGrid() {
		return isGrid;
	}

	/**
	 * Set isGrid.
	 * @param isGrid the isGrid to set
	 */
	public void setIsGrid(boolean isGrid) {
		this.isGrid = isGrid;
	}

	/**
	 * Get seleniumActions.
	 * @return seleniumActions
	 */
	public ISeleniumActions getSeleniumActions() {
		return seleniumActions;
	}

	/**
	 * Set seleniumActions.
	 * @param seleniumActions the seleniumActions to set
	 */
	public void setSeleniumActions(ISeleniumActions seleniumActions) {
		this.seleniumActions = seleniumActions;
	}

	/**
	 * Get browser.
	 * @return browser
	 */
	public String getBrowser() {
		return browser;
	}

	/**
	 * Set browser.
	 * @param browser the browser to set
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}

	/**
	 * Get serveur.
	 * @return serveur
	 */
	public SeleniumServer getServeur() {
		return serveur;
	}

	/**
	 * Get driver.
	 * @return driver
	 */
	public WebDriver getDriver() {
		return driver;
	}

	/**
	 * Get isWebDriver.
	 * @return isWebDriver
	 */
	public static WebDriver getWebDriverLoc() {
		return driverLoc;
	}

	/**
	 * Set driverLoc.
	 * @param driverLoc the driverLoc to set
	 */
	public static void setDriverLoc(WebDriver driverLoc) {
		TSFSeleniumServer.driverLoc = driverLoc;
	}

	/**
	 * Get serverLoc.
	 * @return serverLoc
	 */
	public static SeleniumServer getServerLoc() {
		return serverLoc;
	}

	/**
	 * Set serverLoc.
	 * @param serverLoc the serverLoc to set
	 */
	public static void setServerLoc(SeleniumServer serverLoc) {
		TSFSeleniumServer.serverLoc = serverLoc;
	}
	
	/**
	 * Get isWebDriverLoc.
	 * @return isWebDriverLoc
	 */
	public static boolean isWebDriverLoc() {
		return isWebDriverLoc;
	}

	/**
	 * Set isWebDriverLoc.
	 * @param isWebDriverLoc the isWebDriverLoc to set
	 */
	public static void setWebDriverLoc(boolean isWebDriverLoc) {
		TSFSeleniumServer.isWebDriverLoc = isWebDriverLoc;
	}
	
	/**
	 * Get activateReportLoc.
	 * @return activateReportLoc
	 */
	public static boolean isActivateReportLoc() {
		return activateReportLoc;
	}

	/**
	 * Set isActivateReportLoc.
	 * @param activateReportLoc the activateReportLoc to set
	 */
	public static void setActivateReportLoc(boolean activateReportLoc) {
		TSFSeleniumServer.activateReportLoc = activateReportLoc;
	}
	
	/**
	 * Get isGridLoc.
	 * @return isGridLoc
	 */
	public static boolean isGridLoc() {
		return isGridLoc;
	}

	/**
	 * Set isGridLoc.
	 * @param isGridLoc the isGridLoc to set
	 */
	public static void setGridLoc(boolean isGridLoc) {
		TSFSeleniumServer.isGridLoc = isGridLoc;
	}

	/**
	 * Get browserLoc.
	 * @return browserLoc
	 */
	public static String getBrowserLoc() {
		return browserLoc;
	}

	/**
	 * Set browserLoc.
	 * @param browserLoc the browserLoc to set
	 */
	public static void setBrowserLoc(String browserLoc) {
		TSFSeleniumServer.browserLoc = browserLoc;
	}
	
	/**
	 * Get languageLoc.
	 * @return languageLoc
	 */
	public static String getLanguageLoc() {
		return languageLoc;
	}

	/**
	 * Set languageLoc.
	 * @param languageLoc the languageLoc to set
	 */
	public static void setLanguageLoc(String languageLoc) {
		TSFSeleniumServer.languageLoc = languageLoc;
	}

	/**
	 * Get contextLoc.
	 * @return contextLoc
	 */
	public static String getContextLoc() {
		return contextLoc;
	}

	/**
	 * Set contextLoc.
	 * @param contextLoc the contextLoc to set
	 */
	public static void setContextLoc(String contextLoc) {
		TSFSeleniumServer.contextLoc = contextLoc;
	}
	
	/**
	 * Get urlLoc.
	 * @return urlLoc
	 */
	public static String getUrlLoc() {
		return urlLoc;
	}

	/**
	 * Set urlLoc.
	 * @param urlLoc the urlLoc to set
	 */
	public static void setUrlLoc(String urlLoc) {
		TSFSeleniumServer.urlLoc = urlLoc;
	}
	
	/**
	 * Get browserPathLoc.
	 * @return browserPathLoc
	 */
	public static String getBrowserPathLoc() {
		return browserPathLoc;
	}

	/**
	 * Set browserPathLoc.
	 * @param browserPathLoc the browserPathLoc to set
	 */
	public static void setBrowserPathLoc(String browserPathLoc) {
		TSFSeleniumServer.browserPathLoc = browserPathLoc;
	}
	
	/**
	 * Get reportPathLoc.
	 * @return reportPathLoc
	 */
	public static String getReportPathLoc() {
		return reportPathLoc;
	}

	/**
	 * Set reportPathLoc.
	 * @param reportPathLoc the reportPathLoc to set
	 */
	public static void setReportPathLoc(String reportPathLoc) {
		TSFSeleniumServer.reportPathLoc = reportPathLoc;
	}

	/**
	 * parametrizeTestByProperties
	 */
	//CHECKSTYLE:OFF (Plus de 50 lignes)
	public void parametrizeTestByProperties() {
	//CHECKSTYLE:ON	
		InputStream in = null;
		try {
			String filePath = System.getProperty("user.dir") + System.getProperty("property.path");
			in = new BufferedInputStream(new FileInputStream(filePath));
			Properties p = new Properties();
			p.load(in);
			boolean isWebdriver = Boolean.valueOf(p.getProperty("isWebDriver"));
			boolean isGrid = Boolean.valueOf(p.getProperty("isGrid"));
			boolean activateReport = Boolean.valueOf(p.getProperty("activateReport"));
			boolean keepBrowserOpen = Boolean.valueOf(p.getProperty("keepBrowserOpen"));
			String browser = p.getProperty("browser");
			String browserPath = p.getProperty("browserPath");
			String url = p.getProperty("url");
			String context = p.getProperty("context");
			String language = p.getProperty("language");
			String timeToWait = p.getProperty("timeToWait");
			String timeOut = p.getProperty("timeOut");
			String reportPath = p.getProperty("reportPath");
			String screenPath = p.getProperty("screenPath");
			String htmlPath = p.getProperty("htmlPath");
			String ieDriverPath = p.getProperty("ieDriverPath");
			if (StringUtils.isEmpty(screenPath)) {
				screenPath = "";
			}
			if (StringUtils.isEmpty(reportPath)) {
				reportPath = "";
			}
			setKeepBrowserOpen(keepBrowserOpen);
			setActivateReport(activateReport);
			setScreenPath(screenPath);
			setScreenPathLoc(screenPath);
			setReportPath(reportPath);
			setReportPathLoc(reportPath);
			setIsWebDriver(isWebdriver);
			setBrowser(browser);
			setIsGrid(isGrid);
			setKeepBrowserOpenLoc(keepBrowserOpen);
			setActivateReportLoc(activateReport);
			setWebDriverLoc(isWebdriver);
			setBrowserLoc(browser);
			setBrowserPathLoc(browserPath);
			setUrlLoc(url);
			setContextLoc(context);
			setLanguageLoc(language);
			setTimeToWaitLoc(timeToWait);
			setTimeOutLoc(timeOut);
			setHtmlPathLoc(htmlPath);
			setIeDriverPathLoc(ieDriverPath);
		} catch (FileNotFoundException e) {
			LOG.log(Level.WARNING, "Cannot find the file [" + TSFConstant.PROPERTIES_FILE_NAME + "]", e);
		} catch (IOException e) {
			LOG.log(Level.WARNING, "Cannot load test data from resource ", e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				LOG.log(Level.WARNING,  "Cannot load test data from resource ", e);
			}
		}
	}

	/**
     * Kill process.
     * @throws Exception exception
     */
	protected void killProcess() throws Exception {
		String command = "taskkill /f /im " + TSFConstant.DEFAULT_IE_DREIVER_FILE_NAME; 
		Runtime.getRuntime().exec(command); 
	}
}
