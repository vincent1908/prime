package com.inetpsa.tsf.selenium.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import com.inetpsa.tsf.selenium.TSFConstant;
import com.inetpsa.tsf.selenium.TSFKeys;
import com.inetpsa.tsf.selenium.report.ReportConstants;
import com.inetpsa.tsf.selenium.support.ISeleniumActions;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

/**
 * TSFSeleniumActions
 * @author e365712
 */
public class TSFSeleniumActions implements ISeleniumActions {
	
	/** LOGGER */
	private static final Logger LOG = Logger.getLogger("TSF");
	
	/** selenium */
	protected Selenium selenium;
	
	/** useLog */
	private static boolean useLog = true;
	
	/** 
	 * TSFSeleniumActions 
	 * @param selenium Selenium
	 * */
	public TSFSeleniumActions(Selenium selenium){
		this.selenium = selenium;
	}
	
	/**
	 * disable log
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
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#click(java.lang.String)
	 */
	public void click(String locator) throws SeleniumException {
		try {
			selenium.click(locator);
			generateInfoLog("Selenium.click("+ locator +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.click("+ locator +")");
			throw new SeleniumException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#focus(java.lang.String)
	 */
	public void focus(String locator) throws SeleniumException {
		try {
			selenium.focus(locator);
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
	public void shiftKeyDown() throws SeleniumException {
		try {
			selenium.shiftKeyDown();
			generateInfoLog("Selenium.shiftKeyDown()");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.shiftKeyDown()");
			throw new SeleniumException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#shiftKeyUp()
	 */
	public void shiftKeyUp() throws SeleniumException {
		try {
			selenium.shiftKeyUp();
			generateInfoLog("Selenium.shiftKeyUp()");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.shiftKeyUp()");
			throw new SeleniumException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#altKeyDown()
	 */
	public void altKeyDown() throws SeleniumException {
		try {
			selenium.altKeyDown();
			generateInfoLog("Selenium.altKeyDown()");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.altKeyDown()");
			throw new SeleniumException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#altKeyUp()
	 */
	public void altKeyUp() throws SeleniumException {
		try {
			selenium.altKeyUp();
			generateInfoLog("Selenium.altKeyUp()");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.altKeyUp()");
			throw new SeleniumException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#controlKeyDown()
	 */
	public void controlKeyDown() throws SeleniumException {
		try {
			selenium.controlKeyDown();
			generateInfoLog("Selenium.controlKeyDown()");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.controlKeyDown()");
			throw new SeleniumException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#controlKeyUp()
	 */
	public void controlKeyUp() throws SeleniumException {
		try {
			selenium.controlKeyUp();
			generateInfoLog("Selenium.controlKeyUp()");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.controlKeyUp()");
			throw new SeleniumException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#keyPress(java.lang.
	 * String, java.lang.String)
	 */
	@Deprecated
	public void keyPress(String locator, String keySequence) throws SeleniumException {
		try {
			selenium.keyPress(locator, keySequence);
			generateInfoLog("Selenium.keyPress("+ locator +", "+ keySequence +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.keyPress("+ locator +", "+ keySequence +")");
			throw new SeleniumException(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#keyPress
	 */
	@Deprecated
	public void keyPress(String locator, TSFKeys key) {
		String keySequence = String.valueOf(key.getKeySelenium());
		try {
			selenium.keyPress(locator, keySequence);
			generateInfoLog("Selenium.keyPress("+ locator +", "+ keySequence +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.keyPress("+ locator +", "+ keySequence +")");
			throw new SeleniumException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#keyDownNative(java.
	 * lang.String)
	 */
	@Deprecated
	public void keyDown(String locator, String keySequence) throws SeleniumException {
		try {
			selenium.keyDown(locator, keySequence);
			generateInfoLog("Selenium.keyDown("+ locator +", "+ keySequence +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.keyDown("+ locator +", "+ keySequence +")");
			throw new SeleniumException(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#keyDownNative
	 */
	public void keyDown(String locator, TSFKeys key) {
		String keySequence = String.valueOf(key.getKeySelenium());
		try {
			selenium.keyDown(locator, keySequence);
			generateInfoLog("Selenium.keyDown("+ locator +", "+ keySequence +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.keyDown("+ locator +", "+ keySequence +")");
			throw new SeleniumException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#keyUp(java.lang.String,
	 * java.lang.String)
	 */
	@Deprecated
	public void keyUp(String locator, String keySequence) throws SeleniumException {
		try {
			selenium.keyUp(locator, keySequence);
			generateInfoLog("Selenium.keyUp("+ locator +", "+ keySequence +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.keyUp("+ locator +", "+ keySequence +")");
			throw new SeleniumException(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#keyUp
	 */
	public void keyUp(String locator, TSFKeys key) {
		String keySequence = String.valueOf(key.getKeySelenium());
		try {
			selenium.keyUp(locator, keySequence);
			generateInfoLog("Selenium.keyUp("+ locator +", "+ keySequence +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.keyUp("+ locator +", "+ keySequence +")");
			throw new SeleniumException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#mouseOver(java.lang
	 * .String)
	 */
	public void mouseOver(String locator) throws SeleniumException {
		try {
			selenium.mouseOver(locator);
			generateInfoLog("Selenium.mouseOver("+ locator +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.mouseOver("+ locator +")");
			throw new SeleniumException(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#mouseOut(java.lang
	 * .String)
	 */
	public void mouseOut(String locator) throws SeleniumException {
		try {
			selenium.mouseOut(locator);
			generateInfoLog("Selenium.mouseOut("+ locator +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.mouseOut("+ locator +")");
			throw new SeleniumException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#mouseDown(java.lang
	 * .String)
	 */
	public void mouseDown(String locator) throws SeleniumException {
		try {
			selenium.mouseDown(locator);
			generateInfoLog("Selenium.mouseDown("+ locator +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.mouseDown("+ locator +")");
			throw new SeleniumException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#type(java.lang.String,
	 * java.lang.String)
	 */
	public void type(String locator, String value) throws SeleniumException {
		try {
			selenium.type(locator, value);
			generateInfoLog("Selenium.type("+ locator +", "+ value +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.type("+ locator +", "+ value +")");
			throw new SeleniumException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#typeKeys(java.lang.
	 * String, java.lang.String)
	 */
	public void typeKeys(String locator, String value) throws SeleniumException {
		try {
			selenium.typeKeys(locator, value);
			generateInfoLog("Selenium.typeKeys("+ locator +", "+ value +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.typeKeys("+ locator +", "+ value +")");
			throw new SeleniumException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#check(java.lang.String)
	 */
	public void check(String locator) throws SeleniumException {
		try {
			selenium.check(locator);
			generateInfoLog("Selenium.check("+ locator +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.check("+ locator +")");
			throw new SeleniumException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#uncheck(java.lang.String
	 * )
	 */
	public void uncheck(String locator) throws SeleniumException {
		try {
			selenium.uncheck(locator);
			generateInfoLog("Selenium.uncheck("+ locator +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.uncheck("+ locator +")");
			throw new SeleniumException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#select(java.lang.String
	 * , java.lang.String)
	 */
	public void select(String selectLocator, String optionLocator) throws SeleniumException {
		try {
			selenium.select(selectLocator, optionLocator);
			generateInfoLog("Selenium.select("+ selectLocator +", "+ optionLocator +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.select("+ selectLocator +", "+ optionLocator +")");
			throw new SeleniumException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#open(java.lang.String)
	 */
	public void open(String url) throws SeleniumException {
		try {
			selenium.open(url);
			generateInfoLog("Selenium.open("+ url +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.open("+ url +")");
			throw new SeleniumException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#selectWindow(java.lang
	 * .String)
	 */
	public void selectWindow(String windowID) throws SeleniumException {
		try {
			selenium.selectWindow(windowID);
			generateInfoLog("Selenium.selectWindow("+ windowID +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.selectWindow("+ windowID +")");
			throw new SeleniumException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#selectFrame(java.lang
	 * .String)
	 */
	public void selectFrame(String locator) throws SeleniumException {
		try {
			selenium.selectFrame(locator);
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
	public void goBack() throws SeleniumException {
		try {
			selenium.goBack();
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
	public String getBodyText() throws SeleniumException {
		String result = null;
		try {
			result = selenium.getBodyText();
			generateInfoLog("Selenium.getBodyText()");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.getBodyText()");
			throw new SeleniumException(e);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#getValue(java.lang.
	 * String)
	 */
	public String getValue(String locator) throws SeleniumException {
		String result = null;
		try {
			result = selenium.getValue(locator);
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
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#getText(java.lang.String
	 * )
	 */
	public String getText(String locator) throws SeleniumException {
		String result = null;
		try {
			result = selenium.getText(locator);
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
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#highlight(java.lang
	 * .String)
	 */
	public void highlight(String locator) throws SeleniumException {
		try {
			selenium.highlight(locator);
			generateInfoLog("Selenium.highlight("+ locator +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.highlight("+ locator +")");
			throw new SeleniumException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#getEval(java.lang.String
	 * )
	 */
	public Object getEval(String script) throws SeleniumException {
		Object result = null;
		try {
			result = selenium.getEval(script);
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
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#isChecked(java.lang
	 * .String)
	 */
	public boolean isChecked(String locator) throws SeleniumException {
		boolean result = false;
		try {
			result = selenium.isChecked(locator);
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
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#getTable(java.lang.
	 * String)
	 */
	public String getTable(String tableCellAddress) throws SeleniumException {
		String result = null;
		try{
			result = selenium.getTable(tableCellAddress);
			generateInfoLog("Selenium.getTable("+ tableCellAddress +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.getTable("+ tableCellAddress +")");
			throw new SeleniumException(e);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#isTextPresent(java.
	 * lang.String)
	 */
	public boolean isTextPresent(String pattern) throws SeleniumException {
		boolean result = false;
		try {
			result = selenium.isTextPresent(pattern);
			generateInfoLog("Selenium.isTextPresent("+ pattern +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.isTextPresent("+ pattern +")");
			throw new SeleniumException(e);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#isElementPresent(java
	 * .lang.String)
	 */
	public boolean isElementPresent(String locator) throws SeleniumException {
		return isElementPresent(locator, true);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#isElementPresent(java
	 * .lang.String)
	 */
	public boolean isElementPresent(String locator, boolean hasLog) throws SeleniumException {
		boolean result = false;
		try {
			result = selenium.isElementPresent(locator);
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
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#isVisible(java.lang
	 * .String)
	 */
	public boolean isVisible(String locator) throws SeleniumException {
		boolean result = false;
		try {
			result = selenium.isVisible(locator);
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
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#isEditable(java.lang
	 * .String)
	 */
	public boolean isEditable(String locator) throws SeleniumException {
		boolean result = false;
		try {
			result = selenium.isEditable(locator);
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
	public String getHtmlSource() throws SeleniumException {
		String result = null;
		try {
			result = selenium.getHtmlSource();
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
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#getXpathCount(java.
	 * lang.String)
	 */
	public Number getXpathCount(String xpath) throws SeleniumException {
		Number result = null;
		try {
			result = selenium.getXpathCount(xpath);
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
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#captureEntirePageScreenshot
	 * (java.lang.String, java.lang.String)
	 */
	public void captureEntirePageScreenshot(String filename, String kwargs) throws SeleniumException {
		try {
			selenium.captureEntirePageScreenshot(filename, kwargs);
		} catch (SeleniumException e){
			throw new SeleniumException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#captureScreenshot(java
	 * .lang.String)
	 */
	public void captureScreenshot(String filename) throws SeleniumException {
		try {
			selenium.captureScreenshot(filename);
		} catch (SeleniumException e){
			throw new SeleniumException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#getElementPositionLeft
	 * (java.lang.String)
	 */
	public Number getElementPositionLeft(String locator) throws SeleniumException {
		Number result = null;
		try {
			result = selenium.getElementPositionLeft(locator);
			generateInfoLog("Selenium.getElementPositionLeft("+ locator +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.getElementPositionLeft("+ locator +")");
			throw new SeleniumException(e);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#getElementPositionTop
	 * (java.lang.String)
	 */
	public Number getElementPositionTop(String locator) throws SeleniumException {
		Number result = null;
		try {
			result = selenium.getElementPositionTop(locator);
			generateInfoLog("Selenium.getElementPositionTop("+ locator +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.getElementPositionTop("+ locator +")");
			throw new SeleniumException(e);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#getElementWidth(java
	 * .lang.String)
	 */
	public Number getElementWidth(String locator) throws SeleniumException {
		Number result = null;
		try {
			result = selenium.getElementWidth(locator);
			generateInfoLog("Selenium.getElementWidth("+ locator +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.getElementWidth("+ locator +")");
			throw new SeleniumException(e);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#getElementHeight(java
	 * .lang.String)
	 */
	public Number getElementHeight(String locator) throws SeleniumException {
		Number result = null;
		try {
			result = selenium.getElementHeight(locator);
			generateInfoLog("Selenium.getElementHeight("+ locator +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.getElementHeight("+ locator +")");
			throw new SeleniumException(e);
		}
		return result;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#getAttribute(java.lang
	 * .String)
	 */
	public String getAttribute(String locator) throws SeleniumException {
		return getAttribute(locator, null);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see
	 * com.inetpsa.tsf.selenium.support.ISeleniumActions#getAttribute(java.lang
	 * .String)
	 */
	public String getAttribute(String locator, String attributeLocator) throws SeleniumException {
		String result = null;
		try {
			if (attributeLocator != null) {
				result = selenium.getAttribute(locator + "@" + attributeLocator);
			} else {
				result = selenium.getAttribute(locator);
			}
			generateInfoLog("Selenium.getAttribute("+ locator +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.getAttribute("+ locator +")");
			throw new SeleniumException(e);
		}
		return result;
	}
	
	/** {@inheritDoc}
	 * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#keyPressNative(java.lang.String)
	 */
	@Deprecated
	public void keyPressNative(String keycode) throws SeleniumException {
		try {
			selenium.keyPressNative(keycode);
			generateInfoLog("Selenium.keyPressNative("+ keycode +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.keyPressNative("+ keycode +")");
			throw new SeleniumException(e);
		}
	}
	
	/** {@inheritDoc}
	 * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#keyPressNative
	 */
	public void keyPressNative(TSFKeys key) {
		int keyCode = key.getKeySelenium();
		String strKeyCode = String.valueOf(keyCode);
		if (isCapitaLetter(keyCode)) {
			selenium.keyDownNative(String.valueOf(TSFKeys.SHIFT.getKeySelenium()));
		} else if (keyCode <= TSFConstant.ASCII_L_Z && keyCode >= TSFConstant.ASCII_L_A) {
			strKeyCode = String.valueOf(keyCode - TSFConstant.LOWERCASE_TO_UPPERCASE);
		}
		try {
			selenium.keyPressNative(strKeyCode);
			if (isCapitaLetter(keyCode)) {
				selenium.keyUpNative(String.valueOf(TSFKeys.SHIFT.getKeySelenium()));
			}
			generateInfoLog("Selenium.keyPressNative("+ keyCode +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.keyPressNative("+ keyCode +")");
			throw new SeleniumException(e);
		}
		
	}
	
	/** {@inheritDoc}
	 * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#keyUpNative(java.lang.String)
	 */
	@Deprecated
	public void keyUpNative(String keycode) throws SeleniumException {
		try {
			selenium.keyUpNative(keycode);
			generateInfoLog("Selenium.keyUpNative("+ keycode +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.keyUpNative("+ keycode +")");
			throw new SeleniumException(e);
		}
	}
	
	/** {@inheritDoc}
	 * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#keyUpNative
	 */
	public void keyUpNative(TSFKeys key) {
		String keycode = String.valueOf(key.getKeySelenium());
		try {
			selenium.keyUpNative(keycode);
			generateInfoLog("Selenium.keyUpNative("+ keycode +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.keyUpNative("+ keycode +")");
			throw new SeleniumException(e);
		}
	}
	
	/** {@inheritDoc}
	 * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#keyDownNative(java.lang.String)
	 */
	@Deprecated
	public void keyDownNative(String keycode) throws SeleniumException {
		try {
			selenium.keyDownNative(keycode);
			generateInfoLog("Selenium.keyDownNative("+ keycode +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.keyDownNative("+ keycode +")");
			throw new SeleniumException(e);
		}
	}
	
	/** {@inheritDoc}
	 * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#keyDownNative
	 */
	public void keyDownNative(TSFKeys key) {
		String keycode = String.valueOf(key.getKeySelenium());
		try {
			selenium.keyDownNative(keycode);
			generateInfoLog("Selenium.keyDownNative("+ keycode +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.keyDownNative("+ keycode +")");
			throw new SeleniumException(e);
		}
	}
	
	/** {@inheritDoc}
	 * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#waitForPageToLoad(java.lang.String)
	 */
	public void waitForPageToLoad(String timeout) throws SeleniumException {
		try {
			selenium.waitForPageToLoad(timeout);
			generateInfoLog("Selenium.waitForPageToLoad("+ timeout +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.waitForPageToLoad("+ timeout +")");
			throw new SeleniumException(e);}
	}
	
	/** {@inheritDoc}
	 * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#getTitle()
	 */
	public String getTitle() throws SeleniumException {
		String result = null;
		try {
			result = selenium.getTitle();
			generateInfoLog("Selenium.getTitle()");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.getTitle()");
			throw new SeleniumException(e);
		}
		return result;
	}
	
	/** {@inheritDoc}
	 * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#selectDefaultFrame()
	 */
	public void selectDefaultFrame() throws SeleniumException {
		try {
			selenium.selectFrame("relative=up");
			generateInfoLog("Selenium.selectDefaultFrame()");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.selectDefaultFrame()");
			throw new SeleniumException(e);
		}
	}
	

	/** {@inheritDoc}
	 * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#getAlert()
	 */
	public String getAlert() throws SeleniumException {
		String result = null;
		try {
			result = selenium.getAlert();
			generateInfoLog("Selenium.getAlert()");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.getAlert()");
			throw new SeleniumException(e);
		}
		return result;
	}

	/** {@inheritDoc}
	 * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#waitForCondition(java.lang.String,
	 * java.lang.String)
	 */
	public void waitForCondition(String script, String timeout) {
		try {
			selenium.waitForCondition(script, timeout);
			generateInfoLog("Selenium.waitForCondition("+ script +", "+ timeout +")");
		} catch (SeleniumException e){
			generateWarnLog(e, " Selenium.waitForCondition("+ script +", "+ timeout +")");
			throw new SeleniumException(e);
		}
	}

	/** {@inheritDoc}
	 * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#getAllWindowIds()
	 */
	public List<String> getAllWindowIds() {
		return Arrays.asList(selenium.getAllWindowIds());
	}
	
	/** {@inheritDoc}
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#getAllWindowTitles()
     */
    public List<String> getAllWindowTitles() {
        return Arrays.asList(selenium.getAllWindowTitles());
    }
	
	/**
	 * To check the keyCode is capita letters
	 * @param keyCode int
	 * @return boolean
	 * */
	private boolean isCapitaLetter(int keyCode) {
		if (keyCode <= TSFConstant.ASCII_Z && keyCode >= TSFConstant.ASCII_A) {
			return true;
		}
		return false;
	}

	/** {@inheritDoc}
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#combinationKey(TSFKeys, TSFKeys)
     */
    public void combinationKey(TSFKeys controlKey, TSFKeys normalkey) {
        keyDownNative(controlKey);
        keyPressNative(normalkey);
        keyUpNative(controlKey);
    }
    
    /** {@inheritDoc}
     * @see com.inetpsa.tsf.selenium.support.ISeleniumActions#combinationKey(TSFKeys, TSFKeys, TSFKeys)
     */
    public void combinationKey(TSFKeys controlKeyA, TSFKeys controlKeyB, TSFKeys normalkey) {
        keyDownNative(controlKeyA);
        keyDownNative(controlKeyB);
        keyPressNative(normalkey);
        keyUpNative(controlKeyB);
        keyUpNative(controlKeyA);
    }

    @Override
    public Object getEval(String script, Object element) {
        //not used in selenium(only used in webdriver)
        return null;
    }
}