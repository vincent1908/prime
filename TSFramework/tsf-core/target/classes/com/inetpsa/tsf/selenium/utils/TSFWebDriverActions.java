package com.inetpsa.tsf.selenium.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.inetpsa.tsf.selenium.TSFConstant;
import com.inetpsa.tsf.selenium.TSFKeys;
import com.inetpsa.tsf.selenium.report.ReportConstants;
import com.inetpsa.tsf.selenium.support.ISeleniumActions;
import com.thoughtworks.selenium.SeleniumException;

/**
 * TSFWebDriverActions
 * 
 * @author e365712
 */
public class TSFWebDriverActions implements ISeleniumActions {

    /** Logger. */
    private static final Logger LOG = Logger.getLogger("TSF");
    /** driver */
    protected WebDriver driver;

    /** useLog */
    private static boolean useLog = true;

    /**
     * TSFWebDriverActions
     * 
     * @param driver WebDriver
     */
    public TSFWebDriverActions(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * deactivate log
     */
    public void disableLog() {
        useLog = false;
    }
    
    /** 
     * generateInfoLog 
     * @param operation String
     * */
    private void generateInfoLog(String operation) {
        if (useLog) {
            StringBuilder sb = new StringBuilder(ReportConstants.ASSERTION_INFORMATION);
            sb.append(operation);
            sb.append(ReportConstants.WRAP);
            LOG.info(sb.toString());    
        } else {
            useLog = true;
        }
    }
    
    /** 
     * generateWarnLog 
     * @param e SeleniumException
     * @param operation String
     * */
    private void generateWarnLog(SeleniumException e, String operation) {
        if (useLog) {
            StringBuilder sb = new StringBuilder(ReportConstants.BEGIN_ERROR);
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            sb.append(baos.toString());
            sb.append(operation);
            sb.append(ReportConstants.WRAP);
            sb.append(ReportConstants.END_ERROR);
            LOG.warning(sb.toString());
        } else {
            useLog = true;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#click(java.lang.String)
     */
    public void click(String locator) {
        try {
            driver.findElement(By.xpath(locator)).click();
            // Temporary replacement waitForPageToLoad()
            Thread.sleep(TSFConstant.THREAD_SLEEP_SHORT);
            generateInfoLog("Selenium.click("+ locator +")");
        } catch (InterruptedException e) {
            LOG.log(Level.WARNING, "InterruptedException when click", e);
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.click("+ locator +")");
            throw new SeleniumException(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#focus(java.lang.String)
     */
    public void focus(String locator) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].focus()", driver.findElement(By.xpath(locator)));
            generateInfoLog("Selenium.focus("+ locator +")");
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.focus("+ locator +")");
            throw new SeleniumException(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#shiftKeyDown()
     */
    @Deprecated
    public void shiftKeyDown() {
        new Actions(driver).keyDown(Keys.SHIFT).perform();
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#combinationKey(TSFKeys, TSFKeys)
     */
    public void combinationKey(TSFKeys controlKey, TSFKeys normalKey) {
        new Actions(driver).keyDown((Keys)controlKey.getKeyWebDriver()).sendKeys(normalKey.getKeyWebDriver()).keyUp((Keys)controlKey.getKeyWebDriver()).perform();
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#combinationKey(TSFKeys, TSFKeys, TSFKeys)
     */
    public void combinationKey(TSFKeys controlKeyA, TSFKeys controlKeyB, TSFKeys normalKey) {
        new Actions(driver).keyDown((Keys)controlKeyA.getKeyWebDriver()).keyDown((Keys)controlKeyB.getKeyWebDriver()).sendKeys(normalKey.getKeyWebDriver()).keyUp((Keys)controlKeyB.getKeyWebDriver()).keyUp((Keys)controlKeyA.getKeyWebDriver()).perform();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#shiftKeyUp()
     */
    @Deprecated
    public void shiftKeyUp() {
        new Actions(driver).keyUp(Keys.SHIFT).perform();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#altKeyDown()
     */
    @Deprecated
    public void altKeyDown() {
        new Actions(driver).keyDown(Keys.ALT).perform();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#altKeyUp()
     */
    @Deprecated
    public void altKeyUp() {
        new Actions(driver).keyUp(Keys.ALT).perform();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#controlKeyDown()
     */
    @Deprecated
    public void controlKeyDown() {
        new Actions(driver).keyDown(Keys.CONTROL).perform();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#controlKeyUp()
     */
    @Deprecated
    public void controlKeyUp() {
        new Actions(driver).keyUp(Keys.CONTROL).perform();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#keyPress(java.lang. String, java.lang.String)
     */
    @Deprecated
    public void keyPress(String locator, String keySequence) {
        driver.findElement(By.xpath(locator)).sendKeys(keySequence);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#keyPress
     */
    @Override
    public void keyPress(String locator, TSFKeys key) {
        focus(locator);
        keyPressNative(key);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#keyDown(java. lang.String)
     */
    @Deprecated
    public void keyDown(String locator, String keySequence) {
        //FIXME
        //NOT IMPLENTMENT IN WEBDRIVER,THE METHOD "KEYDOWN(KEYS)" IS ONLY USED WITH MODIFIER KEY (Keys.SHIFT、Keys.ALT、Keys.CONTROL)
        //INSTEAD OF THE METHOD, PLEASE USE keyPress(String locator, TSFKeys key)
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#keyDown
     */
    @Deprecated
    public void keyDown(String locator, TSFKeys key) {
        //FIXME
        //NOT IMPLENTMENT IN WEBDRIVER,THE METHOD "KEYDOWN(KEYS)" IS ONLY USED WITH MODIFIER KEY (Keys.SHIFT、Keys.ALT、Keys.CONTROL)
        //INSTEAD OF THE METHOD, PLEASE USE keyPress(String locator, TSFKeys key)
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#keyUp(java.lang.String, java.lang.String)
     */
    @Deprecated
    public void keyUp(String locator, String keySequence) {
        //FIXME
        //NOT IMPLENTMENT IN WEBDRIVER,THE METHOD "KEYUP(KEYS)" IS ONLY USED WITH MODIFIER KEY (Keys.SHIFT、Keys.ALT、Keys.CONTROL)
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#keyUp
     */
    @Deprecated
    public void keyUp(String locator, TSFKeys key) {
        //FIXME
        //NOT IMPLENTMENT IN WEBDRIVER,THE METHOD "KEYUP(KEYS)" IS ONLY USED WITH MODIFIER KEY (Keys.SHIFT、Keys.ALT、Keys.CONTROL)
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#mouseOver(java.lang .String)
     */
    public void mouseOver(String locator) {
        try {
            String code = "var fireOnThis = arguments[0];"
                    + "var evObj = document.createEvent('MouseEvents');"
                    + "evObj.initEvent( 'mouseover', true, true );"
                    + "fireOnThis.dispatchEvent(evObj);";
              ((JavascriptExecutor) driver).executeScript(code, driver.findElement(By.xpath(locator)));
            generateInfoLog("Selenium.mouseOver("+ locator +")");
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.mouseOver("+ locator +")");
            throw new SeleniumException(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#mouseOut(java.lang .String)
     */
    public void mouseOut(String locator) {
        new Actions(driver).moveToElement((driver.findElement(By.xpath(locator))), TSFConstant.MOVE_10, TSFConstant.MOVE_10).perform();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#mouseDown(java.lang .String)
     */
    public void mouseDown(String locator) {
        new Actions(driver).clickAndHold(driver.findElement(By.xpath(locator))).perform();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#type(java.lang.String, java.lang.String)
     */
    public void type(String locator, String value) {
        try {
            driver.findElement(By.xpath(locator)).clear();
            driver.findElement(By.xpath(locator)).sendKeys(value);
            generateInfoLog("Selenium.type("+ locator +", "+ value +")");
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.type("+ locator +", "+ value +")");
            throw new SeleniumException(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#typeKeys(java.lang. String, java.lang.String)
     */
    public void typeKeys(String locator, String value) {
        try {
            driver.findElement(By.xpath(locator)).sendKeys(value);
            generateInfoLog("Selenium.typeKeys("+ locator +", "+ value +")");
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.typeKeys("+ locator +", "+ value +")");
            throw new SeleniumException(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#check(java.lang.String)
     */
    public void check(String locator) {
        if (!isChecked(locator)) {
            click(locator);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#uncheck(java.lang.String )
     */
    public void uncheck(String locator) {
        if (isChecked(locator)) {
            click(locator);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#select(java.lang.String , java.lang.String)
     */
    public void select(String selectLocator, String optionLocator) {
        try {
            new Select(driver.findElement(By.xpath(selectLocator))).selectByValue(optionLocator);
            generateInfoLog("Selenium.select("+ selectLocator +", "+ optionLocator +")");
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.select("+ selectLocator +", "+ optionLocator +")");
            throw new SeleniumException(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#open(java.lang.String)
     */
    public void open(String url) {
        try {
            driver.get(url);
            generateInfoLog("Selenium.open("+ url +")");
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.open("+ url +")");
            throw new SeleniumException(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#selectWindow(java.lang .String)
     */
    public void selectWindow(String windowID) {
        try {
            driver.switchTo().window(windowID);
            generateInfoLog("Selenium.selectWindow("+ windowID +")");
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.selectWindow("+ windowID +")");
            throw new SeleniumException(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#selectFrame(java.lang .String)
     */
    public void selectFrame(String locator) {
        try {
            driver.switchTo().frame(locator);
            generateInfoLog("Selenium.selectFrame("+ locator +")");
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.selectFrame("+ locator +")");
            throw new SeleniumException(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#goBack()
     */
    public void goBack() {
        try {
            driver.navigate().back();
            generateInfoLog("selenium.goBack()");
        } catch (SeleniumException e){
            generateWarnLog(e, " selenium.goBack()");
            throw new SeleniumException(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#getBodyText()
     */
    public String getBodyText() {
        String str = "";
        List<WebElement> elements = driver.findElements(By.xpath("//body//*[contains(text(),*)]"));
        for (WebElement e : elements) {
            str += e.getText() + " ";
        }
        return str;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#getValue(java.lang. String)
     */
    public String getValue(String locator) {
        String result = null;
        try {
            result = driver.findElement(By.xpath(locator)).getAttribute("value");
            generateInfoLog("Selenium.getValue("+ locator +")");
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.getValue("+ locator +")");
            throw new SeleniumException(e);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#getText(java.lang.String )
     */
    public String getText(String locator) {
        String result = null;
        try {
            result = driver.findElement(By.xpath(locator)).getText();
            generateInfoLog("Selenium.getText("+ locator +")");
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.getText("+ locator+ ")");
            throw new SeleniumException(e);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#highlight(java.lang .String)
     */
    public void highlight(String locator) {
        try {
            WebElement element = driver.findElement(By.xpath(locator));
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.border = \"5px solid yellow\"", element);
            generateInfoLog("Selenium.highlight("+ locator +")");
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.highlight("+ locator +")");
            throw new SeleniumException(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#getEval(java.lang.String )
     */
    public Object getEval(String script) {
        String args = null;
        Object result = null;
        try {
            result = ((JavascriptExecutor) driver).executeScript(script, args);
            generateInfoLog("Selenium.getEval("+ script +")");
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.getEval("+ script +")");
            throw new SeleniumException(e);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#isChecked(java.lang .String)
     */
    public boolean isChecked(String locator) {
        boolean result = false;
        try {
            result = driver.findElement(By.xpath(locator)).isSelected();
            generateInfoLog("Selenium.isChecked("+ locator +")");
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.isChecked("+ locator +")");
            throw new SeleniumException(e);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#getTable(java.lang. String)
     */
    public String getTable(String tableCellAddress) {
        int index1 = tableCellAddress.trim().indexOf('.');
        int index2 = tableCellAddress.trim().indexOf('.', index1 + 1);
        String tableLocator = tableCellAddress.substring(0, index1);
        WebElement table = driver.findElement(By.xpath(tableLocator));
        int row = Integer.parseInt(tableCellAddress.substring(index1 + 1, index2));
        int cell = Integer.parseInt(tableCellAddress.substring(index2 + 1));
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        WebElement theRow = rows.get(row);
        String text = getCell(theRow, cell);
        return text;
    }

    /**
     * Get cell
     * @param row WebElement
     * @param cell int
     * @return text
     */
    private String getCell(WebElement row, int cell) {
        List<WebElement> cells;
        String text = null;
        if (row.findElements(By.tagName("th")).size() > 0) {
            cells = row.findElements(By.tagName("th"));
            text = cells.get(cell).getText();
        }
        if (row.findElements(By.tagName("td")).size() > 0) {
            cells = row.findElements(By.tagName("td"));
            text = cells.get(cell).getText();
        }
        return text;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#isTextPresent(java. lang.String)
     */
    public boolean isTextPresent(String pattern) {
        boolean result = false;
        try {
            result = driver.findElement(By.tagName("body")).getText().contains(pattern);
            generateInfoLog("Selenium.isTextPresent("+ pattern +")");
        } catch (NoSuchElementException e) {
            LOG.log(Level.WARNING, "NoSuchElementException", e);
            return false;
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.isTextPresent("+ pattern +")");
            throw new SeleniumException(e);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#isElementPresent(java .lang.String)
     */
    public boolean isElementPresent(String locator) {
        return isElementPresent(locator, true);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#isElementPresent(java .lang.String)
     */
    public boolean isElementPresent(String locator, boolean hasLog) {
        boolean result = false;
        try {
            result = driver.findElements(By.xpath(locator)).size() > 0;
            if (hasLog) {
                generateInfoLog("Selenium.isElementPresent("+ locator +")");
            }
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.isElementPresent("+ locator +")");
            throw new SeleniumException(e);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#isVisible(java.lang .String)
     */
    public boolean isVisible(String locator) {
        boolean result = false;
        try {
            result = driver.findElement(By.xpath(locator)).isDisplayed();
            generateInfoLog("Selenium.isVisible("+ locator +")");
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.isVisible("+ locator +")");
            throw new SeleniumException(e);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#isEditable(java.lang .String)
     */
    public boolean isEditable(String locator) {
        boolean result = false;
        try {
            result = driver.findElement(By.xpath(locator)).isEnabled();
            generateInfoLog("Selenium.isEditable("+ locator +")");
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.isEditable("+ locator +")");
            throw new SeleniumException(e);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#getHtmlSource()
     */
    public String getHtmlSource() {
        String result = null;
        try {
            result = driver.getPageSource();
            generateInfoLog("Selenium.getHtmlSource()");
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.getHtmlSource()");
            throw new SeleniumException(e);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#getXpathCount(java. lang.String)
     */
    public Number getXpathCount(String xpath) {
        Number result = null;
        try {
            result = driver.findElements(By.xpath(xpath)).size();
            generateInfoLog("Selenium.getXpathCount("+ xpath +")");
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.getXpathCount("+ xpath +")");
            throw new SeleniumException(e);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#captureEntirePageScreenshot (java.lang.String, java.lang.String)
     */
    public void captureEntirePageScreenshot(String filename, String kwargs) {
        File screenShotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenShotFile, new File(filename));
        } catch (IOException e) {
            LOG.log(Level.WARNING, "IOException", e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#captureScreenshot(java .lang.String)
     */
    public void captureScreenshot(String filename) {
        captureEntirePageScreenshot(filename, null);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#getElementPositionLeft (java.lang.String)
     */
    public Number getElementPositionLeft(String locator) {
        return driver.findElement(By.xpath(locator)).getLocation().getX();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#getElementPositionTop (java.lang.String)
     */
    public Number getElementPositionTop(String locator) {
        return driver.findElement(By.xpath(locator)).getLocation().getY();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#getElementWidth(java .lang.String)
     */
    public Number getElementWidth(String locator) {
        return driver.findElement(By.xpath(locator)).getSize().getWidth();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#getElementHeight(java .lang.String)
     */
    public Number getElementHeight(String locator) {
        return driver.findElement(By.xpath(locator)).getSize().getHeight();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#getAttribute(java.lang .String)
     */
    public String getAttribute(String locator) {
        return getAttribute(locator, null);
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#getAttribute(java.lang .String)
     */
    public String getAttribute(String locator, String attributeLocator) {
        String result = null;
        try {
            result =  driver.findElement(By.xpath(locator)).getAttribute(attributeLocator);
            generateInfoLog("Selenium.getAttribute("+ locator +")");
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.getAttribute("+ locator +")");
            throw new SeleniumException(e);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#keyPressNative(java.lang.String)
     */
    @Deprecated
    public void keyPressNative(String keycode) {
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#keyPressNative
     */
    public void keyPressNative(TSFKeys key) {
        CharSequence keyWebDriver = key.getKeyWebDriver();
        boolean isWordKey = true;
        for (Keys k : Keys.values()) {
            if (k.name().equals(String.valueOf(keyWebDriver))) {
                isWordKey = false;
                break;
            }
        }
        try {
            if (isWordKey) {
                new Actions(driver).sendKeys(key.getKeyWebDriver()).perform();
            } else {
                keyDownNative(key);
                keyUpNative(key);
            }
            generateInfoLog("Selenium.keyPressNative("+ key +")");
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.keyPressNative("+ key +")");
            throw new SeleniumException(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#keyUpNative(java.lang.String)
     */
    @Deprecated
    public void keyUpNative(String keycode) {
        
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#keyUpNative
     */
    public void keyUpNative(TSFKeys key) {
        new Actions(driver).keyUp((Keys) key.getKeyWebDriver()).perform();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#keyDownNative(java.lang.String)
     */
    @Deprecated
    public void keyDownNative(String keycode) {
        //FIXME
        //NOT IMPLENTMENT
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#keyDownNative
     */
    public void keyDownNative(TSFKeys key) {
        new Actions(driver).keyDown((Keys) key.getKeyWebDriver()).perform();
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#waitForPageToLoad(java.lang.String)
     */
    public void waitForPageToLoad(String timeout) {
        try {
            JavascriptExecutor driverJs = (JavascriptExecutor) driver;
            while (!driverJs.executeScript("return document.readyState").equals("complete")) {
                Thread.sleep(TSFConstant.THREAD_SLEEP);
            }
        } catch (InterruptedException e) {
            LOG.log(Level.WARNING, "InterruptedException when wait for page load", e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#getTitle
     */
    public String getTitle() {
        String result = null;
        try {
            result = driver.getTitle();
            generateInfoLog("Selenium.getTitle()");
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.getTitle()");
            throw new SeleniumException(e);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#selectDefaultFrame
     */
    public void selectDefaultFrame() {
        try {
            driver.switchTo().defaultContent();
            generateInfoLog("Selenium.selectDefaultFrame()");
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.selectDefaultFrame()");
            throw new SeleniumException(e);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#getAlert()
     */
    public String getAlert() {
        String result = null;
        try {
            Alert alert = driver.switchTo().alert();
            result = alert.getText();
            alert.accept();
            generateInfoLog("Selenium.getAlert()");
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.getAlert()");
            throw new SeleniumException(e);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#waitForCondition(java.lang.String, java.lang.String)
     */
    public void waitForCondition(String script, String timeout) {
        //FIXME
        //THE METHOD CAN NOT USE THE SAME SCRIPT IN SELENIUM
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#getAllWindowIds
     */
    public List<String> getAllWindowIds() {
        return new ArrayList<String>(driver.getWindowHandles());
    }
    
    
    /**
     * {@inheritDoc}
     * 
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#getAllWindowTitles
     */
    public List<String> getAllWindowTitles() {
       //FIXME THIS METHOD NEED TO DO LATER.
       return null;
    }

    @Override
    public Object getEval(String script, Object element) {
        Object result = null;
        try {
            result = ((JavascriptExecutor) driver).executeScript(script, element);
            generateInfoLog("Selenium.getEval("+ script +")");
        } catch (SeleniumException e){
            generateWarnLog(e, " Selenium.getEval("+ script +")");
            throw new SeleniumException(e);
        }
        return result;
    }
}