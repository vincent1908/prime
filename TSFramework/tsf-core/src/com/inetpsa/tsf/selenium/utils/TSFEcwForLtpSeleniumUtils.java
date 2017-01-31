package com.inetpsa.tsf.selenium.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.inetpsa.tsf.selenium.TSFConstant;
import com.inetpsa.tsf.selenium.TSFKeys;

/**
 * TSFEcwForLtpSeleniumUtils
 * @author e365712
 */
public class TSFEcwForLtpSeleniumUtils extends TSFECWSeleniumUtils {

    /** Logger. */
    private static final Logger LOG = Logger.getLogger("TSF");
	
	/** CODETRANSACTION */
	private static final String CODETRANSACTION = "//input[@id='codeTransaction']";
	
	/**
	 * Enter a code transaction on the menu 
	 * @param codeTransaction String
	 */
	public static void enterTransactionCode(String codeTransaction){
		seleniumActions.selectDefaultFrame();
		seleniumActions.selectFrame("menu");
		seleniumActions.type(CODETRANSACTION, codeTransaction);
		seleniumActions.keyPressNative(TSFKeys.ENTER);
		seleniumActions.waitForPageToLoad("5000");
		seleniumActions.selectDefaultFrame();
		seleniumActions.selectFrame("main");
        try {
            Thread.sleep(TSFConstant.THREAD_LONG_SLEEP);
        } catch (InterruptedException e) {
            LOG.log(Level.WARNING, "InterruptedException ", e);
        }
	}
	
	/**
	 * Select a code transaction on the menu 
	 * @param locator String
	 */
	public static void selectTransactionCode(String locator) {
		seleniumActions.selectDefaultFrame();
		seleniumActions.selectFrame("menu");
		seleniumActions.click(locator);
		seleniumActions.waitForPageToLoad("5000");
		seleniumActions.selectDefaultFrame();
		seleniumActions.selectFrame("main");
	}
	
	/**
	 * Connection
	 * @param buttonToLogin String
	 */
	public static void connection(String buttonToLogin) {
		seleniumActions.selectDefaultFrame();
		seleniumActions.selectFrame("login");
		seleniumActions.click(buttonToLogin);
		seleniumActions.waitForPageToLoad("3000");
	}
	
	/**
	 * Disconnection  
	 */
	public static void disconnection() {
		seleniumActions.selectDefaultFrame();
		seleniumActions.selectFrame("top");
		seleniumActions.click(TSFConstant.LIEN_LOGIN);
	}
	
	/**
	 * Change the user right before test
	 * @throws InterruptedException error
	 */
	public static void changeRight() throws InterruptedException {
		seleniumActions.selectDefaultFrame();
		seleniumActions.selectFrame("menu");
		seleniumActions.type(CODETRANSACTION, "SEC10");
		seleniumActions.keyPressNative(TSFKeys.ENTER);
		seleniumActions.selectDefaultFrame();
		seleniumActions.selectFrame("main");
		TSFECWSeleniumUtils.waitElementPresent("//button[@id='_ecwButtonId']");
		seleniumActions.click("//div[@id='tableProfiles']/div[2]/table/tbody//div[text()='TEST']");
		seleniumActions.click("//button[@id='ecwButtonId']");
		Thread.sleep(TSFConstant.THREAD_SLEEP);
		seleniumActions.selectDefaultFrame();
		seleniumActions.selectFrame("main");
	}
	
	/**
	 * Direct return to the menu by click on small return frame "signal" 
	 */
	public static void returnMenu() {
		seleniumActions.selectDefaultFrame();
		seleniumActions.selectFrame("top");
		seleniumActions.click(TSFConstant.LIEN_MENU);
		seleniumActions.selectDefaultFrame();
		seleniumActions.selectFrame("menu");
		waitTextPresent("Menu");
	}
}
