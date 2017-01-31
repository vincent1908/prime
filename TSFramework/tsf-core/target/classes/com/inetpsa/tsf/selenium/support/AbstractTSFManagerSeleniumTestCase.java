package com.inetpsa.tsf.selenium.support;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.server.RemoteControlConfiguration;
import org.openqa.selenium.server.SeleniumServer;

import com.inetpsa.tsf.selenium.TSFConstant;
import com.inetpsa.tsf.selenium.TSFSeleniumServer;
import com.inetpsa.tsf.selenium.report.ReportConstants;
import com.inetpsa.tsf.selenium.report.TSFReport;
import com.inetpsa.tsf.selenium.utils.TSFECWSeleniumUtils;
import com.inetpsa.tsf.selenium.utils.TSFSeleniumActions;
import com.inetpsa.tsf.selenium.utils.TSFSeleniumUtils;
import com.inetpsa.tsf.selenium.utils.TSFWebDriverActions;
import com.thoughtworks.selenium.Selenium;

/**
 * TSFAbstractManagerSeleniumTestCase
 * 
 * @author e353062
 **/
public abstract class AbstractTSFManagerSeleniumTestCase extends TSFAbstractSupport {

    /** LOGGER */
    protected static final Logger LOG = Logger.getLogger("TSF");
    
    /** The methods for selenium operations */
    protected ISeleniumActions seleniumActions;

    /** The browser path. */
    private String browserPath = TSFConstant.DEFAULT_BROWSER_PATH;

    /** The browser. */
    private String browser = TSFConstant.DEFAULT_BROWSER;

    /** The time to wait. */
    private String timeToWait = TSFConstant.DEFAULT_TIME_TO_WAIT;

    /** The time out. */
    private String timeOut = TSFConstant.DEFAULT_TIME_OUT;

	/** The custom url. */
    private String url = TSFConstant.DEFAULT_URL;

    /** The custom context. */
	private String context = TSFConstant.DEFAULT_CONTEXT;

	/** The custom language. */
    private String language = TSFConstant.DEFAULT_LANGUAGE;
    
    /** The custom htmlPath. */
    private String htmlPath = TSFConstant.DEFAULT_HTML_PATH;
    
    /** To judge if it uses web driver */ 
    private boolean isWebDriver;

    /** To judge if it uses grid */
    private boolean isGrid;

    /** ieDriverPath */
    private String ieDriverPath = "";

    /** The instance of server for Selenium */
    private SeleniumServer serveur;

    /** The instance of server for WebDriver */
    protected WebDriver driver;

    /** Line */
    protected static int line;

    /** File */
    protected static String file = "";

    /** The name of IHM */
    protected static String nomIHM = "";

    /** className */
    private String className;

    /** Instance of the class test */
    protected static Class<?> instanceClass;

    /** tempSelenium */
    protected static Selenium tempSelenium;

    /** tempSelenium */
    protected static WebDriver tempWebDriver;
    
    /** errorLineNumberPack */
    protected static IErrorLineNumberPack errorLineNumberPack;
    
    /**
     * setSelenium
     * 
     * @param tempSelenium Selenium
     */
    public static void setSelenium(Selenium tempSelenium) {
        AbstractTSFManagerSeleniumTestCase.tempSelenium = tempSelenium;
    }

    /**
     * tempWebDriver
     * 
     * @param tempWebDriver WebDriver
     */
    public static void setWebDriver(WebDriver tempWebDriver) {
        AbstractTSFManagerSeleniumTestCase.tempWebDriver = tempWebDriver;
    }

    /**
     * Error count met in the current method<br />
     * This error count is given to 0 between each method, and makes it possible to have <i>all errors of
     * <code>verifyEquals, verifyNotEquals, verifyTrue and verifyFalse</code></i> met in the method
     */
    protected static int error;

    /**
     * Allows to record the error messages at the time of the failure of <code>verifyEquals, verifyNotEquals, verifyTrue and verifyFalse</code>
     */
    protected static ArrayList<String> logErrorCurrent = new ArrayList<String>();

    /**
     * {@inheritDoc}
     * 
     * @see com.thoughtworks.selenium.SeleneseTestCase#setUp()
     */
    @Before
    public void setUp() throws Exception {
		errorLineNumberPack = null;
        if (instanceClass == null || !instanceClass.equals(getClass())) {
            // The name of the IHM is reset
            setNomIHM();
        }
        TSFReport.getInstance().startCaseRecord(nomIHM);
        LOG.info(ReportConstants.TEST_CASE + name.getMethodName() + ReportConstants.CASE_START + ReportConstants.WRAP);
        boolean isBatchMode = false;
        // test is in batch mode
        if (TSFSeleniumServer.getBrowserLoc() != null) {
            setProperty();
            isBatchMode = true;
        } else if (StringUtils.isNotEmpty(System.getProperty("property.path"))) {
            parametrizeTestByProperties();
        }
        parametrizeTestByAnnotation(isBatchMode);

        if (!TSFSeleniumServer.isKeepBrowserOpenLoc()) {
            if (isWebDriver()) {
                startWebdriver();
            } else {
                startSelenium();
            }
        } else {
            if (isWebDriver()) {
                seleniumActions = new TSFWebDriverActions(tempWebDriver);
            } else {
                seleniumActions = new TSFSeleniumActions(tempSelenium);
            }
        }

        // Initialze the Utils class.
        TSFSeleniumUtils.setSeleniumActions(seleniumActions);
        TSFSeleniumUtils.setUtilsInstance(this);
    }

    /**
     * Start Selenium
     * @throws Exception Exception
     */
    private void startSelenium() throws Exception {
        if (!isGrid()) {
            serveur = TSFSeleniumServer.getServerLoc();
            //It means that we start test from single tester.
            if (serveur == null) {
                String path = System.getProperty("firefoxconfig");
                if (StringUtils.isNotEmpty(path)) {
                    RemoteControlConfiguration rcc = new RemoteControlConfiguration();
                    rcc.setFirefoxProfileTemplate(new File(path));
                    serveur = new SeleniumServer(false, rcc);
                } else {
                    serveur = new SeleniumServer();
                }
                serveur.start();
            }
        }
        try {
            super.setUp(getUrl(), getBrowser() + " " + getBrowserPath());
        } catch (Exception e) {
            LOG.log(Level.WARNING, ReportConstants.BEGIN_ERROR + e.getMessage() + ReportConstants.WRAP + "END_ERROR", e);
            throw new Exception("ERROR", e);
        }
        selenium.useXpathLibrary("javascript-xpath");
        selenium.open("/" + getContext());
        selenium.setSpeed(getTimeToWait());
        selenium.windowFocus();
        selenium.windowMaximize();
        selenium.setTimeout(getTimeOut());
        seleniumActions = new TSFSeleniumActions(selenium);
    }

    /**
     * Start Webdriver
     * @throws Exception Exception
     */
    private void startWebdriver() throws Exception {
        driver = TSFSeleniumServer.getWebDriverLoc();
        if (driver == null) {
            if (TSFConstant.IE_BROWSER.equals(getBrowser())) {
                if (StringUtils.isEmpty(getIeDriverPath())){
                    String message = "It's necessary to confugre adress of 'IEDriverServer.exe'";
        			LOG.warning(message);
                    throw new Exception(message);
                }
                System.setProperty("webdriver.ie.driver", getIeDriverPath());
                driver = new InternetExplorerDriver();
            } else if (TSFConstant.FF_BROWSER.equals(getBrowser())) {
                System.setProperty("webdriver.firefox.bin", getBrowserPath());
                driver = new FirefoxDriver();
            }
        }
        driver.manage().window().maximize();
        driver.get(getUrl() + getContext());
        seleniumActions = new TSFWebDriverActions(driver);
    }

    /**
     * Set property
     */
    private void setProperty() {
        setIsWebDriver(TSFSeleniumServer.isWebDriverLoc());
        setBrowser(TSFSeleniumServer.getBrowserLoc());
        setBrowserPath(TSFSeleniumServer.getBrowserPathLoc());
        setUrl(TSFSeleniumServer.getUrlLoc());
        setContext(TSFSeleniumServer.getContextLoc());
        setLanguage(TSFSeleniumServer.getLanguageLoc());
        setGrid(TSFSeleniumServer.isGridLoc());
        setTimeOut(TSFSeleniumServer.getTimeOutLoc());
        setTimeToWait(TSFSeleniumServer.getTimeToWaitLoc());
        setHtmlPath(TSFSeleniumServer.getHtmlPathLoc());
        setIeDriverPath(TSFSeleniumServer.getIeDriverPathLoc());
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.thoughtworks.selenium.SeleneseTestBase#tearDown()
     */
    @After
    public void tearDown() throws Exception {
        TSFReport.getInstance().recordCaseTime(nomIHM, name.getMethodName());
        LOG.info(ReportConstants.TEST_CASE + name.getMethodName() + ReportConstants.CASE_END + ReportConstants.WRAP);
        if (!TSFSeleniumServer.isKeepBrowserOpenLoc()) {
            if (isWebDriver()) {
                if (TSFSeleniumServer.getBrowserLoc() == null) {
                    driver.quit();
                    seleniumActions = null;
                    if (TSFConstant.IE_BROWSER.equals(getBrowser())) {
                        killProcess();
                    }
                }
            } else {
                super.tearDown();
                if (selenium != null) {
                    selenium.stop();
                    selenium = null;
                }
                // Run the single tester, we need stop the server.
                if (TSFSeleniumServer.getBrowserLoc() == null && !isGrid()) {
                    serveur.stop();
                    seleniumActions = null;
                }
            }
        }
        TSFECWSeleniumUtils.setSeleniumActions(seleniumActions);
        if (error > 0) {
            String formattedMessage;
            if (error > 1)
                formattedMessage = error + " erreurs dans le test : " + ReportConstants.WRAP;
            else
                formattedMessage = error + " erreur dans le test : " + ReportConstants.WRAP;
            for (String message : logErrorCurrent) {
                formattedMessage += message + ReportConstants.WRAP;
            }
            logErrorCurrent.clear();
            error = 0;
            fail(formattedMessage);
        }
    }

	/**
     * Kill process
     * @exception Exception exception
     */
    private void killProcess() throws Exception {
        String command = "taskkill /f /im " + TSFConstant.DEFAULT_IE_DREIVER_FILE_NAME;
        Runtime.getRuntime().exec(command);
    }

    /**
     * Get isWebDriver.
     * 
     * @return isWebDriver
     */
    public boolean isWebDriver() {
        return isWebDriver;
    }

    /**
     * Set isWebDriver.
     * 
     * @param isWebDriver the isWebDriver to set
     */
    public void setIsWebDriver(boolean isWebDriver) {
        this.isWebDriver = isWebDriver;
    }

    /**
     * Get isGrid.
	 *
     * @return isGrid
     */
    public boolean isGrid() {
        return isGrid;
    }

    /**
     * Set isGrid.
	 *
     * @param isGrid the isGrid to set
     */
    public void setGrid(boolean isGrid) {
        this.isGrid = isGrid;
    }

    /**
     * Get browser.
	 *
     * @return browser
     */
    public String getBrowser() {
        return browser;
    }

    /**
     * Set browser.
	 *
     * @param browser the browser to set
     */
    public void setBrowser(String browser) {
        if (StringUtils.isNotEmpty(browser)) {
            this.browser = browser;
        }
    }

    /**
     * Get browserPath.
	 *
     * @return browserPath
     */
    public String getBrowserPath() {
        return browserPath;
    }

    /**
     * Set browserPath.
	 *
     * @param browserPath the browserPath to set
     */
    public void setBrowserPath(String browserPath) {
        if (StringUtils.isNotEmpty(browserPath)) {
            this.browserPath = browserPath;
        }
    }

    /**
     * Get timeToWait.
	 *
     * @return timeToWait
     */
    public String getTimeToWait() {
        return timeToWait;
    }

    /**
     * Set timeToWait.
	 *
     * @param timeToWait the timeToWait to set
     */
    public void setTimeToWait(String timeToWait) {
        if (StringUtils.isNotEmpty(timeToWait)) {
            this.timeToWait = timeToWait;
        }
    }

    /**
     * Get timeOut.
	 *
     * @return timeOut
     */
    public String getTimeOut() {
        return timeOut;
    }

    /**
     * Set timeOut.
	 *
     * @param timeOut the timeOut to set
     */
    public void setTimeOut(String timeOut) {
        if (StringUtils.isNotEmpty(timeOut)) {
            this.timeOut = timeOut;
        }
    }

    /**
     * Get language.
	 *
     * @return language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Set language.
	 *
     * @param language the language to set
     */
    public void setLanguage(String language) {
        if (StringUtils.isNotEmpty(language)) {
            this.language = language.toUpperCase();
        }
    }

	/**
	 * Get context.
	 *
	 * @return context
	 */
	public String getContext() {
		return context;
	}

	/**
	 * Set context.
	 *
	 * @param context the context to set
	 */
	public void setContext(String context) {
		if (StringUtils.isNotEmpty(context)) {
			this.context = context;
		}
	}
	
	/**
     * Get context.
	 *
     * @return context
     */
    public String getHtmlPath() {
        return htmlPath;
    }

    /**
     * Set context.
	 *
     * @param htmlPath the htmlPath to set
     */
    public void setHtmlPath(String htmlPath) {
        if (StringUtils.isNotEmpty(htmlPath)) {
            this.htmlPath = htmlPath;
        }
    }

    /**
     * Get className
     * 
     * @return className
     */
    public String getClassName() {
        return className;
    }

    /**
     * Set className.
     * 
     * @param className the className to set
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Get url.
     * 
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set url.
     * 
     * @param url the url to set
     */
    public void setUrl(String url) {
        if (StringUtils.isNotEmpty(url)) {
            this.url = url;
        }
    }

    /**
     * Get ieDriverPath.
     * 
     * @return ieDriverPath
     */
    public String getIeDriverPath() {
        return ieDriverPath;
    }

    /**
     * Set ieDriverPath.
     * 
     * @param ieDriverPath the ieDriverPath to set
     */
    public void setIeDriverPath(String ieDriverPath) {
        this.ieDriverPath = ieDriverPath;
    }

    /**
     * Generate the report
     * 
     * @throws Exception exception
     */
    @AfterClass
    public static void endReport() throws Exception {
        LOG.info(ReportConstants.TEST_CALSS + nomIHM + ReportConstants.CASE_END + ReportConstants.WRAP);
        TSFReport.getInstance().processAssertionsByIhm(nomIHM);
    }

    /**
     * Between the name of the IHM according to the name of the class
     */
    public void setNomIHM() {
        instanceClass = getClass();
        nomIHM = getClass().getSimpleName();
        TSFReport.getInstance().startRecord(nomIHM);
        LOG.info(ReportConstants.TEST_CALSS + nomIHM + ReportConstants.CASE_START + ReportConstants.WRAP);
    }

    /**
     * Get instance class
     * 
     * @return instanceClass
     */
    public Class<?> getInstanceClass() {
        return instanceClass;
    }

    /**
     * Overload verifyEquals of Selenese to have the errors in JUnit while continuing the method
     * 
     * @param s1 :
     * @param s2 :
     */
    public void verifyEquals(Object s1, Object s2) {
        // One is not worried spaces
        if (!egal(s1.toString(), s2.toString())) {
            getErrorLineNumber();
            error++;
            logErrorCurrent.add(format(s1, s2));
            LOG.warning(formatReport(s1, s2, true, ReportConstants.VERIFY_EQUAL));
            screenshot(TSFConstant.ERROR_SCREEN);
        } else {
            LOG.info(formatReport(s1, s2, false, ReportConstants.VERIFY_EQUAL));
        }
    }

    /**
     * Overload verifyEquals of Selenese to have the errors in JUnit while continuing the method error message
     * 
     * @param s1 :
     * @param s2 :
     * @param message :
     */
    public void verifyEquals(Object s1, Object s2, String message) {
        if (!egal(s1.toString(), s2.toString())) {
            getErrorLineNumber();
            error++;
            logErrorCurrent.add(format(message));
            LOG.warning(formatReport(s1, s2, message, true, ReportConstants.VERIFY_EQUAL));
            screenshot(TSFConstant.ERROR_SCREEN);
        } else {
            LOG.info(formatReport(s1, s2, false, ReportConstants.VERIFY_EQUAL));
        }
    }

    /**
     * Overload verifyNotEquals of Selenese to have the errors in JUnit while continuing the method
     * 
     * @param s1 :
     * @param s2 :
     */
    public void verifyNotEquals(Object s1, Object s2) {
        if (egal(s1.toString(), s2.toString())) {
            getErrorLineNumber();
            error++;
            logErrorCurrent.add(format(s1, s2));
            LOG.warning(formatReport(s1, s2, true, ReportConstants.VERIFY_NOT_EQUAL));
            screenshot(TSFConstant.ERROR_SCREEN);
        } else {
            LOG.info(formatReport(s1, s2, false, ReportConstants.VERIFY_NOT_EQUAL));
        }
    }

    /**
     * Overload verifyNotEquals of Selenese to have the errors in JUnit while continuing the method error message
     * 
     * @param s1 :
     * @param s2 :
     * @param message :
     */
    public void verifyNotEquals(Object s1, Object s2, String message) {
        if (egal(s1.toString(), s2.toString())) {
            getErrorLineNumber();
            error++;
            logErrorCurrent.add(format(message));
            LOG.warning(formatReport(s1, s2, message, true, ReportConstants.VERIFY_NOT_EQUAL));
            screenshot(TSFConstant.ERROR_SCREEN);
        } else {
            LOG.info(formatReport(s1, s2, false, ReportConstants.VERIFY_NOT_EQUAL));
        }
    }

    /**
     * Overload verifyTrue of Selenese to have the errors in JUnit while continuing the method
     * 
     * @param bol :
     */
    public void verifyTrue(boolean bol) {
        if (!bol) {
            getErrorLineNumber();
            error++;
            logErrorCurrent.add(format("" + bol));
            LOG.warning(formatReport(true, ReportConstants.VERIFY_TRUE));
            screenshot(TSFConstant.ERROR_SCREEN);
        } else {
            LOG.info(formatReport(false, ReportConstants.VERIFY_TRUE));
        }
    }

    /**
     * Overload verifyTrue of Selenese to have the errors in JUnit while continuing the method error message
     * 
     * @param bol :
     * @param message :
     */
    public void verifyTrue(boolean bol, String message) {
        if (!bol) {
            getErrorLineNumber();
            error++;
            logErrorCurrent.add(format(message));
            LOG.warning(formatReport(message, true, ReportConstants.VERIFY_TRUE));
            screenshot(TSFConstant.ERROR_SCREEN);
        } else {
            LOG.info(formatReport(message, false, ReportConstants.VERIFY_TRUE));
        }
    }

    /**
     * Overload verifyFalse of Selenese to have the errors in JUnit while continuing the method
     * 
     * @param bol :
     */
    public void verifyFalse(boolean bol) {
        if (bol) {
            getErrorLineNumber();
            error++;
            logErrorCurrent.add(format("" + bol));
            LOG.warning(formatReport(true, ReportConstants.VERIFY_FALSE));
            screenshot(TSFConstant.ERROR_SCREEN);
        } else {
            LOG.info(formatReport(false, ReportConstants.VERIFY_FALSE));
        }
    }

    /**
     * Overload verifyFalse of Selenese to have the errors in JUnit while continuing the method error message
     * 
     * @param bol :
     * @param message :
     */
    public void verifyFalse(boolean bol, String message) {
        if (bol) {
            getErrorLineNumber();
            error++;
            logErrorCurrent.add(format(message));
            LOG.warning(formatReport(message, true, ReportConstants.VERIFY_FALSE));
            screenshot(TSFConstant.ERROR_SCREEN);
        } else {
            LOG.info(formatReport(message, false, ReportConstants.VERIFY_FALSE));
        }
    }

    /**
     * Method to check the equality between two chains, by keeping only one space at the same time, and replacement of % by any chain of character.
     * 
     * @param expected :
     * @param actual :
     * @return resulat
     */
    private boolean egal(String expected, String actual) {
        int i;
        int j = 0;
        if (expected.equals("") || actual.equals("")) {
            return actual.equals(expected);
        }
        String[] expectedWords = expected.split("\\s+");
        String[] actualWords = actual.split("\\s+");
        boolean resulat = true;

        if (expectedWords.length == 0 && actualWords.length == 0)
            resulat = true;
        else if (expectedWords.length == 0 || actualWords.length == 0)
            resulat = false;

        // On cherche la correspondance de tout les mots
        for (i = 0; i < expectedWords.length && i + j < actualWords.length; i++) {
            int k;
            String wordExpected = expectedWords[i];
            String wordActual = actualWords[i + j];
            if (wordExpected.equals("%")) {
                if (i < expectedWords.length - 1) {
                    wordExpected = expectedWords[++i];
                    // recherche du mot suivant
                    for (k = j; (i + k) < actualWords.length; k++) {
                        if (actualWords[k + i].equals(wordExpected)) {
                            wordActual = actualWords[i + k];
                            break;
                        }
                    }
                    j = k;
                    // Si nous ne l'avons pas trouvé
                    if ((i + j) == actualWords.length) {
                        wordActual = "";
                    }
                } else {
                    wordExpected = "";
                    // Si nous ne sommes pas sur le dernier mot de la chaîne
                    // testée actuellement, c'est bon
                    if ((i + j) < actualWords.length)
                        wordActual = "";
                    else
                        wordActual = " ";
                }
            }
            if (!wordActual.equals(wordExpected)) {
                resulat = false;
                break;
            }

        }
        if (i < expectedWords.length) {
            resulat = false;
        }
        return resulat;
    }

    /**
     * Method formattant the error message
     * 
     * @param expected : What was expected
     * @param actual : What is obtained
     * @return message
     */
    protected String format(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            return "Error " + error + " (" + file + ":" + line + ") : \n\t We expected \"" + expected + "\"\n\t but we got \"" + actual + "\"\n\n";
        }
        return "Error " + error + " (" + file + ":" + line + ") : \n\t We didn't expect \"" + expected + "\"\n\t but we got it\n";
    }

    /**
     * Method formattant the error message for report
     * 
     * @param expected : What was expected
     * @param actual : What is obtained
     * @param isWarning : warning or info
     * @param methodName : method name
     * @return message
     */
    protected String formatReport(Object expected, Object actual, Boolean isWarning, String methodName) {
        return formatReport(expected, actual, "", isWarning, methodName);
    }

    /**
     * Method formattant the error message for report
     * 
     * @param expected : What was expected
     * @param actual : What is obtained
     * @param message : message which to be display
     * @param isWarning : warning or info
     * @param methodName : method name
     * @return message
     */
    protected String formatReport(Object expected, Object actual, String message, Boolean isWarning, String methodName) {
        StringBuilder sb = null;
        if (isWarning) {
            sb = new StringBuilder(ReportConstants.BEGIN_ERROR);
            sb.append(methodName);
            sb.append("(").append((file + ":" + line));
            sb.append(") : ");
            if (!egal(expected.toString(), actual.toString())) {
                sb.append("Expected : '").append(expected).append("' Result : '").append(actual).append("'");
            } else {
                sb.append("We didn't expect '").append(expected).append("' but we got it");
            }
            if (StringUtils.isNotEmpty(message)) {
                sb.append(" " + message);
            }
        } else {
            sb = new StringBuilder(ReportConstants.BEGIN_INFO);
            sb.append(methodName);
            if (egal(expected.toString(), actual.toString())) {
                sb.append("Expected : '").append(expected).append("' Result : '").append(actual).append("'");
            } else {
                sb.append("We didn't expect '").append(expected).append("' and result is '").append(actual).append("'");
            }
        }
        sb.append(ReportConstants.WRAP);
        if (isWarning) {
            sb.append(ReportConstants.END_ERROR);
        } else {
            sb.append(ReportConstants.END_INFO);
        }
        sb.append(ReportConstants.WRAP);
        return sb.toString();
    }

    /**
     * Method formattant the error message
     * 
     * @param message : error message
     * @return message
     */
    protected static String format(String message) {
        return "Error " + error + " (" + file + ":" + line + ") : \n\t" + message + "\n";
    }

    /**
     * Method formattant the error message
     * 
     * @param isWarning : warning or info
     * @param methodName : method name
     * @return message
     */
    protected static String formatReport(Boolean isWarning, String methodName) {
        return formatReport("", isWarning, methodName);
    }

    /**
     * Method formattant the error message
     * 
     * @param message : error message
     * @param isWarning : warning or info
     * @param methodName : method name
     * @return message
     */
    protected static String formatReport(String message, Boolean isWarning, String methodName) {
        StringBuilder sb = null;
        if (isWarning) {
            sb = new StringBuilder(ReportConstants.BEGIN_ERROR);
            sb.append(methodName);
            sb.append("(").append((file + ":" + line));
            sb.append(") : ");
            if (StringUtils.isNotEmpty(message)) {
                sb.append(message);
            }
        } else {
            sb = new StringBuilder(ReportConstants.BEGIN_INFO);
            sb.append(methodName);
        }
        sb.append(ReportConstants.WRAP);
        if (isWarning) {
            sb.append(ReportConstants.END_ERROR);
        } else {
            sb.append(ReportConstants.END_INFO);
        }
        sb.append(ReportConstants.WRAP);
        return sb.toString();
    }

    /**
     * Get error line number
     */
    private static void getErrorLineNumber() {
    	if (errorLineNumberPack == null) {
    		getErrorLineNumber(null);
    	} else {
    		errorLineNumberPack.getErrorLineNumberPack();
    	}
    }
    
    /**
     * Get error line number
     * 
     * @param methodName String
     */
    private static void getErrorLineNumber(String methodName) {
        StackTraceElement[] stack = new Throwable().getStackTrace();
        for (StackTraceElement pile : stack) {
            if (StringUtils.isNotEmpty(methodName) && pile.getFileName().contains(methodName)) {
                file = pile.getFileName();
                line = pile.getLineNumber();
                break;
            } else if (pile.getFileName().compareTo("TestMain.java") == 0) {
                file = pile.getFileName();
                line = pile.getLineNumber();
                break;
            } else if (pile.getFileName().regionMatches(0, nomIHM, 0, TSFConstant.TAILLE_NOM_IHM)) {
                file = pile.getFileName();
                line = pile.getLineNumber();
                break;
            }
        }
    }

    /**
     * Add screen shot error message
     * 
     * @param name :
     * @param message :
     */
    public void addScreenError(String name, String message) {
        getErrorLineNumber();
        error++;
        logErrorCurrent.add(format(message));
        LOG.warning(message + ReportConstants.SCREEN_PATH + TSFConstant.COMPARISION_PATH + nomIHM + TSFConstant.BACK_SLASH + name
                + TSFConstant.ERROR_SCREEN + TSFConstant.PNG + ReportConstants.WRAP);
    }

    /**
     * Add error message
     * 
     * @param message :
     * @param methodName :
     */
    public void ajouterErreur(String message, String methodName) {
        getErrorLineNumber(methodName);
        error++;
        logErrorCurrent.add(format(message));
        LOG.warning(formatReport(message, true, ""));
    }

    /**
     * Add error message
     * 
     * @param message :
     */
    public void ajouterErreur(String message) {
        getErrorLineNumber();
        error++;
        logErrorCurrent.add(format(message));
        LOG.warning(formatReport(message, true, ""));
    }
    
    /**
     * Add error message
     * 
     * @param message String
     * @param e Exception
     */
    public void ajouterErreur(String message, Exception e) {
        getErrorLineNumber();
        error++;
        logErrorCurrent.add(format(message));
        LOG.log(Level.WARNING, formatReport(message, true, ""), e);
    }

    /**
     * Create comparison result folder.
     * 
     * @exception IOException exception
     * @return testPath the path store the comparison image
     */
    public String createComparisonDir() throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String time = sdf.format(new Date());
        String path = TSFConstant.CURRENT_PATH + ReportConstants.SLASH + TSFSeleniumServer.getScreenPathLoc() + TSFConstant.SCREENSHOT_TEST;
        String referencePath = TSFConstant.CURRENT_PATH + ReportConstants.SLASH + TSFSeleniumServer.getScreenPathLoc()
                + TSFConstant.SCREENSHOT_REFERENCE;
        TSFReport.getInstance().createDir(new File(referencePath));
        TSFReport.getInstance().createDir(new File(path + time));
        return (path + time);
    }

    /**
     * Screenshot.
     */
    public void screenshot() {
        screenshot("");
    }

    /**
     * Screenshot.
     * 
     * @param addition add the file name '-ERROR' when the verification failed
     */
    public void screenshot(String addition) {
        if (StringUtils.isNotEmpty(TSFSeleniumServer.getReportDate())) {
            String screenPath = TSFSeleniumServer.getReportPathLoc() + ReportConstants.REPORT_DIR_NAME + ReportConstants.SLASH
                    + TSFSeleniumServer.getReportDate() + ReportConstants.BACK_SLASH + TSFConstant.SCREENSHOT_PATH;
            File rep = new File(screenPath + nomIHM);
            if (!rep.exists()) {
                rep.mkdirs();
            }
            String screenShot = TSFConstant.CURRENT_PATH + ReportConstants.SLASH + screenPath + nomIHM + TSFConstant.BACK_SLASH
                    + name.getMethodName();
            String path = TSFConstant.SCREENSHOT_PATH + nomIHM + TSFConstant.BACK_SLASH + name.getMethodName();
            String date = new SimpleDateFormat("hhmmss-MMdd").format(new Date());
            if (StringUtils.equals(TSFConstant.IE_BROWSER, getBrowser())) {
                seleniumActions.captureScreenshot(screenShot + addition + TSFConstant.UNDER_LINE + date + TSFConstant.IE_PNG);
                LOG.info(nomIHM + ReportConstants.SCREEN_SHOT + path + addition + TSFConstant.UNDER_LINE + date + TSFConstant.IE_PNG
                        + ReportConstants.WRAP);
            } else {
                seleniumActions.captureEntirePageScreenshot(screenShot + addition + TSFConstant.UNDER_LINE + date + TSFConstant.FF_PNG, "");
                LOG.info(nomIHM + ReportConstants.SCREEN_SHOT + path + addition + TSFConstant.UNDER_LINE + date + TSFConstant.FF_PNG
                        + ReportConstants.WRAP);
            }
        }
    }

    /**
     * Add Reference Result
     * 
     * @param name the image name
     * @param path the image save path
     * @exception IOException exception
     */
    public void addReferenceResult(String name, String path) throws IOException {
        // Reference file does not exist
        LOG.info(ReportConstants.ASSERTION_INFORMATION + ReportConstants.COMPARISON_RESULT + name + ReportConstants.WHITE_SPACE
                + ReportConstants.COMPARISON_REFERENCE);
        deleteFile(new File(path));
    }

    /**
     * Comparison result.
     * 
     * @param isMatched True: the two images are same. False : the two images are different
     * @param name the image name
     * @param path the image save path
     */
    public void comparisonResult(boolean isMatched, String name, String path) {
        if (StringUtils.isNotEmpty(TSFSeleniumServer.getReportDate())) {
            String screenPath = TSFSeleniumServer.getReportPathLoc() + ReportConstants.REPORT_DIR_NAME + ReportConstants.SLASH
                    + TSFSeleniumServer.getReportDate() + ReportConstants.BACK_SLASH + TSFConstant.COMPARISION_PATH;
            if (isMatched) {
                LOG.info(ReportConstants.ASSERTION_INFORMATION + ReportConstants.COMPARISON_RESULT + name + ReportConstants.WHITE_SPACE
                        + ReportConstants.COMPARISON_SUCCESS);
                deleteFile(new File(path));
            } else {
                try {
                    File sourceFile = new File(new File(path).getAbsolutePath() + ReportConstants.SLASH + name + TSFConstant.ERROR_SCREEN
                            + TSFConstant.PNG);
                    File targetFile = new File(screenPath + nomIHM);
                    if (!targetFile.exists()) {
                        targetFile.mkdirs();
                    }
                    TSFReport.getInstance().copyFile(sourceFile,
                            new File(screenPath + nomIHM + ReportConstants.SLASH + name + TSFConstant.ERROR_SCREEN + TSFConstant.PNG));
                    addScreenError(name, ReportConstants.COMPARISON_RESULT + name + ReportConstants.WHITE_SPACE + ReportConstants.COMPARISON_FAILURE);
                } catch (FileNotFoundException e) {
                    LOG.log(Level.WARNING, "Cannot find the file, ", e);
                } catch (IOException e) {
                    LOG.log(Level.WARNING, "I/O exception, ", e);
                }
            }
        }
    }

    /**
     * Comparison result.
     * 
     * @param isMatched True: the two images are same. False : the two images are different
     * @param name the image name
     * @param path the image save path
     */
    public void comparisonResultWithPostion(boolean isMatched, String name, String path) {
        if (StringUtils.isNotEmpty(TSFSeleniumServer.getReportDate())) {
            String screenPath = TSFSeleniumServer.getReportPathLoc() + ReportConstants.REPORT_DIR_NAME + ReportConstants.SLASH
                    + TSFSeleniumServer.getReportDate() + ReportConstants.BACK_SLASH + TSFConstant.COMPARISION_PATH;
            if (isMatched) {
                LOG.info(ReportConstants.ASSERTION_INFORMATION + ReportConstants.COMPARISON_RESULT + ReportConstants.COMPARISON_SUCCESS);
                deleteFile(new File(path));
            } else {
                try {
                    File sourceFile = new File(new File(path).getAbsolutePath() + ReportConstants.SLASH + name + TSFConstant.ERROR_SCREEN
                            + TSFConstant.PNG);
                    File targetFile = new File(screenPath + nomIHM);
                    if (!targetFile.exists()) {
                        targetFile.mkdirs();
                    }
                    TSFReport.getInstance().copyFile(sourceFile,
                            new File(screenPath + nomIHM + ReportConstants.SLASH + name + TSFConstant.ERROR_SCREEN + TSFConstant.PNG));
                    addScreenError(name, ReportConstants.COMPARISON_RESULT + ReportConstants.COMPARISON_FAILURE);
                } catch (FileNotFoundException e) {
                    LOG.log(Level.WARNING, "Cannot find the file, ", e);
                } catch (IOException e) {
                    LOG.log(Level.WARNING, "I/O exception, ", e);
                }
            }
        }
    }

    /**
     * Remove temporary files created for screens comparison
     * 
     * @param className Name of the class
     * @param fileSuffix Name complement
     */
    public void removeTemporaryFiles(String className, String fileSuffix) {
        File filePng = new File(TSFConstant.CURRENT_PATH + ReportConstants.SLASH + TSFSeleniumServer.getScreenPathLoc() + "\\screenshots\\"
                + className + fileSuffix + TSFConstant.PNG);
        filePng.delete();
    }

    /**
     * parametrizeTestByProperties
     */
    private void parametrizeTestByProperties() {
        InputStream in = null;
        try {
            String filePath = System.getProperty("user.dir") + System.getProperty("property.path");
            in = new BufferedInputStream(new FileInputStream(filePath));
            Properties p = new Properties();
            p.load(in);
            boolean isWebdriver = Boolean.valueOf(p.getProperty("isWebDriver"));
            boolean isGrid = Boolean.valueOf(p.getProperty("isGrid"));
            String browser = p.getProperty("browser");
            String browserPath = p.getProperty("browserPath");
            String url = p.getProperty("url");
            String context = p.getProperty("context");
            String language = p.getProperty("language");
            String timeToWait = p.getProperty("timeToWait");
            String timeOut = p.getProperty("timeOut");
            String ieDriverPath = p.getProperty("ieDriverPath");
            setIsWebDriver(isWebdriver);
            setBrowser(browser);
            setBrowserPath(browserPath);
            setUrl(url);
            setContext(context);
            setLanguage(language);
            setGrid(isGrid);
            setTimeOut(timeOut);
            setTimeToWait(timeToWait);
            setIeDriverPath(ieDriverPath);
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
                LOG.log(Level.WARNING, "Cannot load test data from resource ", e);
            }
        }
    }

    /**
     * Delete file and directory
     * 
     * @param f File
     */
    public void deleteFile(File f) {
        try {
            for (File fi : f.listFiles()) {
                if (fi.isDirectory()) {
                    deleteFile(fi);
                } else {
                    fi.delete();
                }
            }
            f.delete();
        } catch (NullPointerException e) {
            LOG.log(Level.WARNING, "NullPointerException ", e);
            ajouterErreur(e + " Can't find the delete file");
        }
    }
}
