package com.inetpsa.tsf.selenium.utils;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.collections.CollectionUtils;

import com.inetpsa.tsf.selenium.TSFConstant;
import com.inetpsa.tsf.selenium.TSFKeys;
import com.inetpsa.tsf.selenium.TSFSeleniumServer;
import com.inetpsa.tsf.selenium.report.ReportConstants;
import com.inetpsa.tsf.selenium.screenshot.GenericBitmapAnalyzer;
import com.inetpsa.tsf.selenium.screenshot.source.Position;
import com.inetpsa.tsf.selenium.support.AbstractTSFManagerSeleniumTestCase;
import com.inetpsa.tsf.selenium.support.ISeleniumActions;
import com.thoughtworks.selenium.SeleniumException;
/**
 * TSFSeleniumUtils
 * @author e365712
 */
public class TSFSeleniumUtils {
    
    /** LOGGER */
    protected static final Logger LOG = Logger.getLogger("TSFSeleniumUtils");

	/** TSFSeleniumUtils */
	public TSFSeleniumUtils(){}
	
	/** TYPE button */
	public static final int TYPE_BOUTON = 0x01;
	
	/** TYPE checkbox */
	public static final int TYPE_CHECKBOX = 0x02;
	
	/** TYPE field text */
	public static final int TYPE_FIELD_TEXT = 0x04;
	
	/** TYPE bouton radio*/
	public static final int TYPE_RADIO = 0x08;
	
	/** TYPE label */
	public static final int TYPE_LABEL = 0x10;
	
	/** Message to be written for an action not envisaged */
	public static final String MESSAGE_NOT_IMPLEMENT = "   ****** Not implement ******   ";
	
	/** Regular expression need to avoid conflict */
	public static final String REGEULAR_EXPRESSION_CHARS = "().?*!+";
	
	/** SeleniumActions */
	public static ISeleniumActions seleniumActions;
	
	/** utilsInstance */
	public static AbstractTSFManagerSeleniumTestCase utilsInstance;
	
	/** positions */
	public static List<Position> positions;
	
	/** 
	 * SetUtilsInstance 
	 * @param utilsInstance TSFAbstractManagerSeleniumTestCase
	 * */
	public static void setUtilsInstance(AbstractTSFManagerSeleniumTestCase utilsInstance) {
		TSFSeleniumUtils.utilsInstance = utilsInstance;
	}
	
	/** 
	 * SetSeleniumActions 
	 * @param seleniumActions ISeleniumActions
	 * */
	public static void setSeleniumActions(ISeleniumActions seleniumActions) {
		TSFSeleniumUtils.seleniumActions = seleniumActions;
	}
	
	/**
	 * Wait for a element display in the IHM
	 * @param element : String
	 * @throws SeleniumException : error
	 * @throws InterruptedException : error
	 */
	public static void waitElementPresent(String element) throws SeleniumException, InterruptedException {
		//Give time to the page to post itself
		Thread.sleep(TSFConstant.THREAD_SLEEP);
		Date debut = new Date();
		String timeOut = utilsInstance.getTimeOut();
		Date fin = new Date(debut.getTime() + Integer.parseInt(timeOut));
		while (true) {
			// Take down in Time-out if the response time is excessive. 
			if (fin.before(new Date())) {
				utilsInstance.ajouterErreur("*** TIMEOUT ***\n element \"" + element + "\" exceed "+ timeOut + "ms");
				break;
			}
			if (seleniumActions.isElementPresent(element, false)) {
				break;
			}
			Thread.sleep(TSFConstant.THREAD_SLEEP_SHORT);
		}
		seleniumActions.isElementPresent(element);
	}
	
	/**
	 * Wait for a text content display in the IHM
	 * @param content : String
	 * @throws SeleniumException : error
	 */
	public static void waitTextPresent(String content) throws SeleniumException {
		Date debut = new Date();
		String timeOut = utilsInstance.getTimeOut();
		Date fin = new Date(debut.getTime() + Integer.parseInt(timeOut));
		while (true) {
			if (fin.before(new Date())) {
				utilsInstance.ajouterErreur("*** TIMEOUT ***\n content \"" + content + "\" exceed "+ timeOut + "ms");
				break;
			}
			if (seleniumActions.isTextPresent(content)) {
				break;
			}
		}
	}
	
	/**
	 * Simulation of the enter key
	 * 
	 * @throws SeleniumException : error
	 * @throws InterruptedException : error
	 */
	public static void pressEnter() throws SeleniumException, InterruptedException {
		seleniumActions.keyPressNative(TSFKeys.ENTER);
		Thread.sleep(TSFConstant.THREAD_SLEEP);
	}
	
	/**
	 * <h3>Identifer the nature of the field</h3>
	 * <br />These codes are defined as a constant in this class :
	 * <em>TYPE_BOUTON</em>,<em>TYPE_CHAMP_TEXTE</em>,<em>TYPE_RADIO</em>,<em>TYPE_CHECKBOX</em>
	 * @param fieldName : name of the field
	 * @return code of the field nature
	 * @see #TYPE_BOUTON
	 * @see #TYPE_CHAMP_TEXTE
	 * @see #TYPE_CHECKBOX
	 * @see #TYPE_RADIO
	 * @throws SeleniumException : error
	 */
	public static int natureField(String fieldName) throws SeleniumException {
		String attribute;
		int code = 0;
	      try {
	            attribute = seleniumActions.getAttribute(fieldName, "type");
	        } catch(SeleniumException e) {
	          LOG.log(Level.WARNING, "type is label", e);
	          return TYPE_LABEL;
	      }
		if (attribute.contains("Text") || attribute.contains("text")) {
			code = TYPE_FIELD_TEXT;
		} else if (attribute.contains("Button") || attribute.contains("button")) {
			code = TYPE_BOUTON;
		} else if (attribute.contains("Radio") || attribute.contains("radio")) {
			code = TYPE_RADIO;
		} else if (attribute.contains("Checkbox") || attribute.contains("checkbox")) {
			code = TYPE_CHECKBOX;
		}
		return code;
	}
	
	/**
	 * Check the state of the check box(click, short cut and space key)
	 * @param checkBoxName : name of the checkbox to be tested
	 * @throws InterruptedException : error
	 * @throws SeleniumException : error
	 */
	public static void checkBox(String checkBoxName) throws SeleniumException, InterruptedException {
		//If the checkbox is checked
		if (seleniumActions.isChecked(checkBoxName)) {
			//By click
			seleniumActions.click(checkBoxName);
			utilsInstance.verifyFalse(seleniumActions.isChecked(checkBoxName), "La checkbox \""+ checkBoxName +"\" devrait ne pas être cochée");
			//By press the space bar
			seleniumActions.focus(checkBoxName);
			seleniumActions.keyPressNative(TSFKeys.SPACE);Thread.sleep(TSFConstant.THREAD_SLEEP);
			utilsInstance.verifyTrue(seleniumActions.isChecked(checkBoxName), "La checkbox \""+ checkBoxName +"\" devrait ne pas être cochée");
			seleniumActions.focus(checkBoxName);
			seleniumActions.keyPressNative(TSFKeys.SPACE);Thread.sleep(TSFConstant.THREAD_SLEEP);
			utilsInstance.verifyFalse(seleniumActions.isChecked(checkBoxName), "La checkbox \""+ checkBoxName +"\" devrait être cochée");
		}
		else{
			//By click
			seleniumActions.click(checkBoxName);
			utilsInstance.verifyTrue(seleniumActions.isChecked(checkBoxName), "La checkbox \""+ checkBoxName +"\" devrait être cochée");
			//By press the space bar
			seleniumActions.focus(checkBoxName);
			seleniumActions.keyPressNative(TSFKeys.SPACE);Thread.sleep(TSFConstant.THREAD_SLEEP);
			utilsInstance.verifyFalse(seleniumActions.isChecked(checkBoxName), "La checkbox \""+ checkBoxName +"\" devrait être cochée");
			seleniumActions.focus(checkBoxName);
			seleniumActions.keyPressNative(TSFKeys.SPACE);Thread.sleep(TSFConstant.THREAD_SLEEP);
			utilsInstance.verifyTrue(seleniumActions.isChecked(checkBoxName), "La checkbox \""+ checkBoxName +"\" devrait ne pas être cochée");
		}
	}	
	
	/**
	 * <h3>Seizure of a character string by simulation of keys keyboard.</h3>
	 * Support only the alphanumerics (a-zA-Z0-9) and some special characters.
	 *  
	 * @param chaine : string of digital and alpha
	 * @throws SeleniumException : error
	 * @throws InterruptedException : error
	 */
	//TODO The method maybe has problem need to be done.
	public static void enterCharacterString(String chaine) throws SeleniumException, InterruptedException {
		String upperChaine  = chaine.toUpperCase();
		for (int i = 0; i< chaine.length(); i++) {
			//One supports on shift that for A-Z and 0-9
			boolean hasShift = (upperChaine.charAt(i) == chaine.charAt(i) || chaine.charAt(i) == '/' || chaine.charAt(i) == '.') 
				&& chaine.charAt(i) != '*' && chaine.charAt(i) != ')' && chaine.charAt(i) != '(' && chaine.charAt(i) != '<';
			if (hasShift) {
			    seleniumActions.keyDownNative(String.valueOf(KeyEvent.VK_SHIFT));
			    Thread.sleep(TSFConstant.KEY_SLEEP);
			}
			String codeCharacter;
			//We have 6 particular cases for the characters
			switch (chaine.charAt(i)) {
				case '*' : codeCharacter = String.valueOf(KeyEvent.VK_ASTERISK);break;
				case '/' : codeCharacter = String.valueOf(KeyEvent.VK_COLON);break;//On fact SHIFT + :
				case '.' : codeCharacter = String.valueOf((int)';');break; //On fact ; + SHIFT
				case '<' : codeCharacter = String.valueOf(KeyEvent.VK_LESS);break;
				case '(' : codeCharacter = String.valueOf((int)'5');break;//One supports on the key with the 5 top
				case ')' : codeCharacter = String.valueOf(KeyEvent.VK_RIGHT_PARENTHESIS);break;
				case '_' : codeCharacter = String.valueOf(KeyEvent.VK_UNDERSCORE);break;
				default:   codeCharacter = String.valueOf((int)upperChaine.charAt(i));break;
			}
			//One supports on the key corresponding to the codeCharacter obtained higher
			try {
				seleniumActions.disableLog();
				seleniumActions.keyPressNative(codeCharacter);
				Thread.sleep(TSFConstant.KEY_SLEEP);
			}
			catch (SeleniumException e) {
			    LOG.log(Level.WARNING, "Code incorrect", e);
				//In the event of bad character, one adds an error and one continuous
				utilsInstance.ajouterErreur("Code caractère " + ((int)upperChaine.charAt(i)) + " ('" + chaine.charAt(i) + "') incorrect");
			}
			if (hasShift) {
				seleniumActions.keyUpNative(String.valueOf(KeyEvent.VK_SHIFT));
				Thread.sleep(TSFConstant.KEY_SLEEP);
			}
		}
	}
	
	/**
	 * Screen conformity
	 *  
	 * @param name : image name 
	 * @throws SeleniumException : error
	 * @throws InterruptedException : error
	 * @throws IOException : error
	 */
	public static void screenConformity(String name) throws SeleniumException, InterruptedException, IOException {
		String testPath = utilsInstance.createComparisonDir();
		boolean isMatched = false;
		seleniumActions.captureEntirePageScreenshot(testPath + ReportConstants.SLASH + name + TSFConstant.PNG, "");
		String referencePath = TSFConstant.CURRENT_PATH + ReportConstants.SLASH + TSFSeleniumServer.getScreenPathLoc() + TSFConstant.SCREENSHOT_REFERENCE + name;
		File referenceFile = new File(referencePath + TSFConstant.PNG);
		if (!referenceFile.exists()) {
			seleniumActions.captureEntirePageScreenshot(referencePath + TSFConstant.PNG, "");
			utilsInstance.addReferenceResult(name, testPath);
		} else {
			isMatched = GenericBitmapAnalyzer.contrastImages(referencePath, testPath + ReportConstants.SLASH + name);
			utilsInstance.comparisonResult(isMatched, name, testPath);
		}
		if (utilsInstance.isWebDriver()) {
			seleniumActions.getEval("(function closeWindow(){window.close();})()");
		} else {
			seleniumActions.goBack();	
		}
		Thread.sleep(TSFConstant.THREAD_SLEEP);
	}
	
	/**
	 * Comparison images with position
	 *  
	 * @param name : screen shot name
	 * @param testPath : path of screen shot
	 * @param previousImagePath : previous Image Path
	 * @param followingImagePath : following Image Path
	 * @throws SeleniumException : error
	 * @throws InterruptedException : error
	 * @throws IOException : error
	 */
	public static void comparisonImagesWithPosition(String name, String testPath, String previousImagePath
			, String followingImagePath) throws SeleniumException, InterruptedException, IOException {
		comparisonImagesWithPosition(name, testPath, previousImagePath, followingImagePath, null);
	}
	
	/**
	 * Comparison images with position
	 *  
	 * @param name : screen shot name
	 * @param testPath : path of screen shot
	 * @param previousImagePath : previous Image Path
	 * @param followingImagePath : following Image Path
	 * @param zonesNotValider : not valider zones
	 * @throws SeleniumException : error
	 * @throws InterruptedException : error
	 * @throws IOException : error
	 */
	public static void comparisonImagesWithPosition(String name, String testPath, String previousImagePath
			, String followingImagePath, List<String> zonesNotValider) throws SeleniumException, InterruptedException, IOException {
		boolean isMatched = false;
		positions = new ArrayList<Position>();
		addNotValiderZone(zonesNotValider);
		isMatched = GenericBitmapAnalyzer.contrastImagesWithPosition(previousImagePath, followingImagePath, positions);
		utilsInstance.comparisonResult(isMatched, name, testPath);
	}
	
	/**
	 * Add not validate Zone
	 * 
	 * @param zonesNotValider : not validate zones
	 */
	public static void addNotValiderZone(List<String> zonesNotValider) {
		//get the position of the area not check (value in the list)
		if (CollectionUtils.isNotEmpty(zonesNotValider)) {
			for(String zone : zonesNotValider){
				int zoneNotVerifiedLeft = seleniumActions.getElementPositionLeft(zone).intValue();
				int zoneNotVerifiedWidth =  seleniumActions.getElementWidth(zone).intValue();
				int zoneNotVerifiedTop =  seleniumActions.getElementPositionTop(zone).intValue();
				int zoneNotVerifiedHeight =  seleniumActions.getElementHeight(zone).intValue();
				Position position = new Position(zoneNotVerifiedLeft, zoneNotVerifiedWidth, zoneNotVerifiedTop, zoneNotVerifiedHeight);
				positions.add(position);
			}
		}
	}
	
	/** return the data
	 * @param day  : the value of day
	 * @param mon  : the value of month
	 * @param year : the value of year
	 * @return data
	 */
	public static String returnDate(int day, int mon, int year) {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String[] dataList = String.valueOf(sdf.format(dt)).split("/");
		String dayDate = String.valueOf(Integer.valueOf(dataList[0])+day);
		//if the day value < 10, add "0" before the day.
		if (Integer.valueOf(dataList[0])+day < TSFConstant.VALEUR_10) {
			dayDate = "0" + dayDate;
		}
		String monDate = String.valueOf(Integer.valueOf(dataList[1])+mon);
		//if the month value < 10, add "0" before the month.
		if (Integer.valueOf(dataList[1])+mon < TSFConstant.VALEUR_10) {
			monDate = "0" + monDate;
		}
		String data = dayDate+"/"+monDate+"/"+String.valueOf(Integer.valueOf(dataList[2])+year);
		return data;
	}
	
	/**
	 * Select second page.
	 */
	public static void selectSecondPage() {
		List<String> windowIdsBefore = seleniumActions.getAllWindowIds();
		seleniumActions.getEval("(function(){window.open('about:blank');}())");
		List<String> windowIdsAfter  = seleniumActions.getAllWindowIds();
		String newWindowId = null;
		for (String id : windowIdsAfter) {
			if (!windowIdsBefore.contains(id)) {
				newWindowId = id;
				break;
			}
		}
		seleniumActions.selectWindow(newWindowId);
	}
	
	/**
	 * Return to first page.
	 */
	public static void returnFirstPage() {
		List<String> windowIdsBefore = seleniumActions.getAllWindowIds();
		seleniumActions.selectWindow(windowIdsBefore.get(0));
	}
}
