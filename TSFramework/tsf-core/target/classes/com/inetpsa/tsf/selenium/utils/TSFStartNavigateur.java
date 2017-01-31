package com.inetpsa.tsf.selenium.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

import com.inetpsa.tsf.selenium.TSFConstant;
import com.inetpsa.tsf.selenium.TSFSeleniumServer;
import com.inetpsa.tsf.selenium.report.ReportConstants;
import com.inetpsa.tsf.selenium.report.TSFReport;
import com.inetpsa.tsf.selenium.support.AbstractTSFManagerSeleniumTestCase;
import com.thoughtworks.selenium.DefaultSelenium;

/**
 * Start selenium test
 */
public class TSFStartNavigateur extends TSFSeleniumServer {

    /** LOGGER */
    private static final Logger LOG = Logger.getLogger("TSF");

    /** Port */
    private static final Integer PORT = 4444;

    /**
     * Makes it possible to carry out the set-up of super class
     * 
     * @throws Exception error
     */
    @Test
    public void testDebut() throws Exception {
        // To launch one instace of the Selenium server for a test suite
        parametrizeTestByProperties();
        if (isWebDriver()) {
            if (TSFConstant.IE_BROWSER.equals(getBrowser())) {
                if (StringUtils.isEmpty(getIeDriverPathLoc())){
                    String message = "It's necessary to confugre adress of 'IEDriverServer.exe'";
                    LOG.warning(message);
                    throw new Exception(message);
                }
                System.setProperty("webdriver.ie.driver", getIeDriverPathLoc());
                DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
                ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                driver = new InternetExplorerDriver(ieCapabilities);
            } else if (TSFConstant.FF_BROWSER.equals(getBrowser())) {
                System.setProperty("webdriver.firefox.bin", getBrowserPathLoc());
                driver = new FirefoxDriver();
            }
            setDriverLoc(driver);
            if (isKeepBrowserOpen()) {
                driver.manage().window().maximize();
                driver.get(getUrlLoc() + getContextLoc());
                setDriverLoc(driver);
                AbstractTSFManagerSeleniumTestCase.setWebDriver(driver);
            }
        } else {
            String path = System.getProperty("firefoxconfig");
            if (StringUtils.isNotEmpty(path)) {
                RemoteControlConfiguration rcc = new RemoteControlConfiguration();
                rcc.setFirefoxProfileTemplate(new File(path));
                serveur = new SeleniumServer(false, rcc);
            } else {
                serveur = new SeleniumServer();
            }
            serveur.start();
            setServerLoc(serveur);
            if (isKeepBrowserOpen()) {
                selenium = new DefaultSelenium("localhost", PORT, getBrowser() + " " + getBrowserPathLoc(), getUrlLoc());
                selenium.start();
                selenium.useXpathLibrary("javascript-xpath");
                selenium.open("/" + getContextLoc());
                selenium.setSpeed(getTimeToWaitLoc());
                selenium.windowFocus();
                selenium.windowMaximize();
                selenium.setTimeout(getTimeOutLoc());
                AbstractTSFManagerSeleniumTestCase.setSelenium(selenium);
            }
        }
        if (isActivateReport()) {
            initLog();
        }
        initScreen();
    }

    /**
     * Initialize the screen shot
     */
    private void initScreen() {
        String path = TSFSeleniumServer.getScreenPathLoc() + TSFConstant.SCREENSHOT_TEST;
        String referencePath = TSFSeleniumServer.getScreenPathLoc() + TSFConstant.SCREENSHOT_REFERENCE;
        TSFReport.getInstance().createDir(new File(referencePath));
        TSFReport.getInstance().createDir(new File(path));
    }

    /**
     * Initialize the report log
     */
    private void initLog() {
        setReportDate(new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date()));
        String logFileName = ReportConstants.SELENIUM + ReportConstants.DOT_LOG;
        FileHandler fh;
        try {
            // folder 'testReport' + date
            String folder = ReportConstants.REPORT_DIR_NAME + ReportConstants.SLASH + getReportDate();
            TSFReport.getInstance().copyReportResources(getReportPath() + folder);
            TSFReport.getInstance().copyReportScenarios(getReportPath() + folder);
            File ihmDir = new File(getReportPath() + folder + ReportConstants.SLASH + ReportConstants.LOG_FOLDER);
            if (!ihmDir.exists()) {
                ihmDir.mkdir();
            }
            fh = new FileHandler(getReportPath() + folder + ReportConstants.SLASH + logFileName);
            fh.setFormatter(new SimpleFormatter());
            LOG.addHandler(fh);
            String projectPath = System.getProperty("user.dir");
            String path = ".." + File.separator
                    + projectPath.substring(projectPath.lastIndexOf(ReportConstants.BACK_SLASH) + 1, projectPath.length()) + File.separator
                    + getReportPath();
            // name of folder in occurrence, default "testReport"
            TSFReport.getInstance().searchFileList(path + ReportConstants.REPORT_DIR_NAME, getReportDate());
        } catch (SecurityException e) {
            LOG.log(Level.WARNING, "SecurityException d'ecriture du fichier de log [" + logFileName + "]\n", e);
        } catch (IOException e) {
            LOG.log(Level.WARNING, "IOException d'ecriture du fichier de log [" + logFileName + "]\n", e);
        }
    }
}
