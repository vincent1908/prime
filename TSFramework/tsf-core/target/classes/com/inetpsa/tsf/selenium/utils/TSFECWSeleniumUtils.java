package com.inetpsa.tsf.selenium.utils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.inetpsa.tsf.selenium.TSFConstant;
import com.inetpsa.tsf.selenium.TSFKeys;
import com.inetpsa.tsf.selenium.report.ReportConstants;
import com.inetpsa.tsf.selenium.screenshot.ECWBitmapAnalyzer;
import com.inetpsa.tsf.selenium.screenshot.source.NoWayFindXPathException;
import com.inetpsa.tsf.selenium.screenshot.source.XPathInvalidException;
import com.inetpsa.tsf.selenium.screenshot.source.Xpath;
import com.thoughtworks.selenium.SeleniumException;

/**
 * TSFECWSeleniumUtils
 * 
 * @author e365712
 */
// CHECKSTYLE:OFF (Plus de 750 lignes)
public class TSFECWSeleniumUtils extends TSFSeleniumUtils {
    // CHECKSTYLE:ON
    /** TSFECWSeleniumUtils */
    public TSFECWSeleniumUtils() {
    }

    /** EcwCurrentDate */
    private static String currentDate;

    /**
     * <h3>To make a short cut keyboard of type ALT+SHIF+LETTRE</h3>
     * 
     * @param code String, character of the short cut
     * @throws SeleniumException : error
     * @throws InterruptedException : error
     */
    public static void toDoKeyboardshortCut(String code) throws SeleniumException, InterruptedException {
        // Take the TSFKeys of the code
        TSFKeys key = TSFKeys.valueOf(code.toUpperCase());

        // To position the cursor apart from the zones
        seleniumActions.click("//span");
        seleniumActions.focus("//span");

        // To simulate shift + alt + code of the zone
        seleniumActions.keyDownNative(TSFKeys.SHIFT);
        seleniumActions.keyDownNative(TSFKeys.ALT);
        seleniumActions.keyPressNative(key);

        Thread.sleep(TSFConstant.THREAD_SLEEP);
        // Release the keys alt and shift
        seleniumActions.keyUpNative(TSFKeys.SHIFT);
        seleniumActions.keyUpNative(TSFKeys.ALT);
    }

    /**
     * <h3>Activate or deactivate a checkBox</h3>
     * 
     * @param zoneName String, checkBox name
     * @param state String, activation = on ; desactivation = off
     * @throws SeleniumException : error
     */
    public static void clickCheckBox(String zoneName, String state) throws SeleniumException {
        // Only click if necessary to have the state changed.
        if (state.equalsIgnoreCase("on")) {
            if (seleniumActions.getValue(zoneName).equals("off")) {
                seleniumActions.click(zoneName);
            }
        } else if (state.equalsIgnoreCase("off")) {
            if (seleniumActions.getValue(zoneName).equals("on")) {
                seleniumActions.click(zoneName);
            }
        } else {
            throw new SeleniumException("invalid parameter state :" + state);
        }
    }

    /**
     * Research of the position then click on a value of a table whose elements do not have key (standard Ajax)
     * 
     * @param tableName : the name of the table
     * @param expectedValue : the element which wants to select
     * @param column : the column of the table
     * @throws SeleniumException : error
     */
    public static void selectValueInTableWithoutKey(String tableName, String expectedValue, int column) throws SeleniumException {
        // Note: the table provided by 'tr' is in double ==> one divides by 2 and minus one 'tr' which contains 'th'
        int rowCount = ((Integer) seleniumActions.getEval("return document.getElementById('" + tableName + "').getElementsByTagName('tr').length"))
                .intValue() / 2 - 1;
        String expectedPosition = "";
        for (int i = 1; i < rowCount + 1; i++) {
            String valuePosition = "//div[@id='" + tableName + "']//div[@class='ecwTableBody']/table/tbody/tr[" + i + "]/td[" + column + "]";
            String currentValue = seleniumActions.getText(valuePosition);
            if (currentValue.equals(expectedValue)) {
                expectedPosition = valuePosition;
                break;
            }
        }
        utilsInstance.verifyEquals(expectedPosition, "", "selectValeurDansTablesansCle. Param " + expectedValue + " non trouvé dans table "
                + tableName);
        seleniumActions.click(expectedPosition);
    }

    /**
     * Seek a value in a table whose elements do not have key (standard Ajax)
     * 
     * @param tableName : the name of the table
     * @param searchValue : the element which wants to search
     * @param column : the column of the table
     * @return isFind boolean
     * @throws SeleniumException : error
     */
    public static boolean searchValeurInTableWithoutCle(String tableName, String searchValue, int column) throws SeleniumException {
        boolean isFind = false;
        // Note: the table provided by 'tr' is in double ==> one divides by 2 and minus one 'tr' which contains 'th'
        int rowCount = 0;
        if (utilsInstance.isWebDriver()) {
            rowCount = ((Long) seleniumActions.getEval("return document.getElementById('" + tableName + "').getElementsByTagName('tr').length"))
                    .intValue() / 2 - 1;
        } else {
            rowCount = (Integer.valueOf((String) seleniumActions.getEval("this.browserbot.findElement('" + tableName
                    + "').getElementsByTagName('tr').length"))).intValue() / 2 - 1;
        }
        for (int i = 1; i < rowCount + 1; i++) {
            String currentPosition = "//div[@id='" + tableName + "']//div[@class='ecwTableBody']/table/tbody/tr[" + i + "]/td[" + column + "]";
            String currentValue = seleniumActions.getText(currentPosition);
            if (currentValue.equals(searchValue)) {
                isFind = true;
                break;
            }
        }
        return isFind;
    }

    /**
     * Get the element of the table
     * 
     * @param tableName : table name
     * @param column : number of column
     * @param row : number of row
     * @return element
     * @throws SeleniumException : error
     */
    public static String arrayElement(String tableName, int column, int row) throws SeleniumException {
        return seleniumActions.getText("//div[@id='" + tableName + "']//div[@class='ecwTableBody']//tbody/tr[" + row + "]/td[" + column + "]");
    }

    /**
     * <h3>Test of the short cuts keyboards</h3> <i>Exemples :</i><br />
     * <i>To test the short cuts keyboard which controls a radio/field text/button/checkbox :</i> <code>keyboardShortCut("O", "nomZone");<br/></code>
     * 
     * @param shortCut : short cut
     * @param fieldName : name of the field
     * @throws SeleniumException : error
     * @throws InterruptedException : error
     */
    public static void keyboardShortCut(String shortCut, String fieldName) throws SeleniumException, InterruptedException {
        String initialValue = null;
        if (!seleniumActions.getAttribute(fieldName, "class").contains("ecwTable")) {
            initialValue = seleniumActions.getValue(fieldName);
        }
        // Make the short cut keyboard to go in the zone
        toDoKeyboardshortCut(shortCut);
        // if the field is a check box
        if (natureField(fieldName) == TYPE_CHECKBOX) {
            // Check the check box state when we make the short cut
            if (initialValue.compareTo("off") == 0) {
                // if the check box is not selected
                utilsInstance.verifyEquals("on", seleniumActions.getValue(fieldName));
                toDoKeyboardshortCut(shortCut);
                utilsInstance.verifyEquals("off", seleniumActions.getValue(fieldName));
            } else {
                // if the check box is selected
                utilsInstance.verifyEquals("off", seleniumActions.getValue(fieldName));
                toDoKeyboardshortCut(shortCut);
                utilsInstance.verifyEquals("on", seleniumActions.getValue(fieldName));
            }
        } else if (natureField(fieldName) == TYPE_RADIO) {
            // if the radio button is selected
            utilsInstance.verifyEquals("on", seleniumActions.getValue(fieldName));
        } else if (seleniumActions.getAttribute(fieldName, "class").contains("ecwTable")) {
            utilsInstance.verifyTrue(seleniumActions.getAttribute(fieldName, "class").contains("Active"));
        } else if (natureField(fieldName) == TYPE_FIELD_TEXT) {
            utilsInstance.verifyTrue(seleniumActions.getAttribute(fieldName, "class").contains("Actif"), "Le champ \"" + fieldName
                    + "\" n'est pas actif alors qu'il le devrait");
        }
    }

    /**
     * <p>
     * Test of the required field<em>press the enter key</em>.
     * </p>
     * The field will be put at blank to test the obligatory character.<br />
     * After the field tested, it will be given the initial value.<br />
     * 
     * @param fieldName : name of the field to be tested
     * @param errorMessage : the error message which displayed in the messageBar
     * @throws SeleniumException : error
     * @throws InterruptedException : error
     */
    public static void requiredField(String fieldName, String errorMessage) throws SeleniumException, InterruptedException {
        requiredField(fieldName, errorMessage, null);
    }

    /**
     * <p>
     * Test of the required field <em>clicking on the button.</em>
     * </p>
     * The field will be put at blank to test the obligatory character.<br />
     * After the field tested, it will be given the initial value.<br />
     * 
     * @param fieldName : name of the field to be tested
     * @param buttonName : name of the button on which to click
     * @param errorMessage : the error message which displayed in the messageBar
     * @throws SeleniumException : error
     * @throws InterruptedException : error
     */
    public static void requiredField(String fieldName, String errorMessage, String buttonName) throws SeleniumException, InterruptedException {
        String initialValue = seleniumActions.getValue(fieldName);
        // Clean the value of zone(trigger the key event)
        seleniumActions.type(fieldName, "");
        seleniumActions.focus(fieldName);
        seleniumActions.keyPressNative(TSFKeys.BACK_SPACE);
        Thread.sleep(TSFConstant.THREAD_SLEEP);
        // To click on the button
        if (buttonName != null) {
            if (buttonName.contains("span")) {
                seleniumActions.click(buttonName);
            } else {
                seleniumActions.getEval("window.document.getElementById('" + buttonName + "').click();");
            }
            Thread.sleep(TSFConstant.THREAD_SLEEP);
        } else {
            pressEnter();
            Thread.sleep(TSFConstant.THREAD_SLEEP);
        }
        String attribute = seleniumActions.getAttribute(fieldName, "class");
        utilsInstance.verifyTrue(attribute.contains("INPUT_text_Actif"), "Le champ " + fieldName + " n'est pas actif ");
        utilsInstance.verifyTrue(attribute.contains("ecwInputInvalid"), "Le champ " + fieldName + " n'est pas encadré en rouge");
        messageBar(errorMessage, TSFConstant.MESSAGE_TYPE_WARNING);

        seleniumActions.type(fieldName, initialValue);
    }

    /**
     * Test the invalid data while entering the chaine into the field(if there is a popup, click the button 'OUI')
     * 
     * @param fieldName : name of the field to be tested
     * @param errorMessage : error message of invalid data
     * @param buttonName : the button to click
     * @throws SeleniumException : error
     * @throws InterruptedException : error
     */
    public static void invalideInput(String fieldName, String errorMessage, String buttonName) throws SeleniumException, InterruptedException {
        invalideInput(fieldName, errorMessage, "Z", buttonName, null);
    }

    /**
     * Test the invalid data while entering the chaine into the field(if there is a popup, click the button 'OUI')
     * 
     * @param fieldName : name of the field to be tested
     * @param errorMessage : error message of invalid data
     * @param buttonName : the button to click
     * @param popButtonId : the id of button 'Oui'
     * @throws SeleniumException : error
     * @throws InterruptedException : error
     */
    public static void invalideInput(String fieldName, String errorMessage, String buttonName, String popButtonId) throws SeleniumException,
            InterruptedException {
        invalideInput(fieldName, errorMessage, "Z", buttonName, popButtonId);
    }

    /**
     * Test the invalid data while entering the chaine into the field(if there is a popup, click the button 'OUI')
     * 
     * @param fieldName : name of the field to be tested
     * @param errorMessage : error message of invalid data
     * @param chaine : enter chain to the field for the test
     * @param buttonName : the button to click
     * @param popButtonId : the id of button 'Oui'
     * @throws SeleniumException : error
     * @throws InterruptedException : error
     */
    public static void invalideInput(String fieldName, String errorMessage, String chaine, String buttonName, String popButtonId)
            throws SeleniumException, InterruptedException {
        // To put "" in the zone
        seleniumActions.type(fieldName, "");

        // Focus the zone
        seleniumActions.focus(fieldName);
        seleniumActions.type(fieldName, chaine);
        Thread.sleep(TSFConstant.THREAD_SLEEP);
        // /To click on the button
        if (buttonName != null) {
            if (buttonName.contains("span")) {
                seleniumActions.click(buttonName);
            } else {
                seleniumActions.getEval("window.document.getElementById('" + buttonName + "').click();");
            }
            Thread.sleep(TSFConstant.THREAD_SLEEP);
        } else {
            pressEnter();
            Thread.sleep(TSFConstant.THREAD_SLEEP);
        }
        if (popButtonId != null) {
            seleniumActions.click(popButtonId);
        }
        // /Verify that the current zone is actived and the cursor is placed on the active area
        utilsInstance.verifyTrue(seleniumActions.getAttribute(fieldName, "class").contains("Actif"), "Le champ " + fieldName + " n'est pas actif");
        messageBar(errorMessage, TSFConstant.MESSAGE_TYPE_WARNING);
    }

    /**
     * Check the type of message which will be present in the messageBar
     * 
     * @param message : message present in the messageBar
     * @param typeMessage : Warning ? Information ? error ?
     * @throws SeleniumException : error
     */
    public static void messageBar(String message, String typeMessage) throws SeleniumException {
        String messageRecu;
        try {
            messageRecu = seleniumActions.getText("//div[@id='EcwMessageBar']");
        } catch (SeleniumException e) {
            utilsInstance.ajouterErreur("La barre de message n'est pas présente", e);
            return;
        }
        String attribute = seleniumActions.getAttribute("//div[@id='EcwMessageBar']", "class");
        utilsInstance.verifyEquals(message, messageRecu, "Le message n'est pas le bon : \"" + messageRecu + "\" n'est pas le message attendu : \""
                + message + "\"");
        utilsInstance.verifyTrue(attribute.compareTo(typeMessage) == 0, "Le message n'est pas du bon type : " + typeMessage + " attendu " + attribute
                + " obtenu");
    }

    /**
     * <h3>Check the state of the field</h3>
     * <p>
     * the state of the field (Gray ? Blank ? Checked ? Active ?).<br />
     * the following constants are the available states, resulting from the class {@link TSFConstant} : STATE_BLANK, STATE_NOT_BLANK, STATE_GRAY,
     * STATE_NOT_GRAY, STATE_CHECKED, STATE_NOT_CHECKED, STATE_ACTIVE AND STATE_NOT_ACTIVE.
     * </p>
     * 
     * @param fieldName : name of the field which one will test the state
     * @param states : state of the field
     * @throws SeleniumException : error
     */
    public static void attributesField(String fieldName, int... states) throws SeleniumException {
        attributesField(fieldName, false, null, states);
    }
    
    /**
     * <h3>Check the state of the field</h3>
     * <p>
     * the state of the field (Gray ? Blank ? Checked ? Active ?).<br />
     * the following constants are the available states, resulting from the class {@link TSFConstant} : STATE_BLANK, STATE_NOT_BLANK, STATE_GRAY,
     * STATE_NOT_GRAY, STATE_CHECKED, STATE_NOT_CHECKED, STATE_ACTIVE AND STATE_NOT_ACTIVE.
     * </p>
     * 
     * @param fieldName : name of the field which one will test the state
     * @param states : state of the field
     * @param fieldValue : value of the field which one will test the state
     * @throws SeleniumException : error
     */
    public static void attributesField(String fieldName, String fieldValue, int... states) throws SeleniumException {
        attributesField(fieldName, true, fieldValue, states);
    }


    /**
     * <h3>Check the state of the field</h3>
     * <p>
     * the state of the field (Gray ? Blank ? Checked ? Active ?).<br />
     * the following constants are the available states, resulting from the class {@link TSFConstant} : STATE_BLANK, STATE_NOT_BLANK, STATE_GRAY,
     * STATE_NOT_GRAY, STATE_CHECKED, STATE_NOT_CHECKED, STATE_ACTIVE AND STATE_NOT_ACTIVE.
     * </p>
     * 
     * @param fieldName : name of the field which one will test the state
     * @param hasFiledValueCheck : filed value need to check or not
     * @param fieldValue : value of the field which one will test the state
     * @param states : state of the field
     * @throws SeleniumException : error
     */
    // CHECKSTYLE:OFF (Plus de 50 lignes)
    public static void attributesField(String fieldName, boolean hasFiledValueCheck, String fieldValue, int... states) throws SeleniumException {
        // CHECKSTYLE:ON
        // Determined if it is a button, a field text, a radio button, a checkbox,….
        int champType = natureField(fieldName);

        // To get the value of the zone
        if (hasFiledValueCheck) {
            if ((champType & TYPE_FIELD_TEXT) == champType) {
                utilsInstance.verifyEquals(fieldValue, seleniumActions.getValue(fieldName), "The zone \"" + fieldName + "\" value is not right.");
            } else if (((champType & (TYPE_BOUTON + TYPE_CHECKBOX + TYPE_RADIO)) == champType) && StringUtils.isNotEmpty(fieldValue)) {
                utilsInstance.verifyEquals(fieldValue, seleniumActions.getText(fieldName), "The zone \"" + fieldName + "\" value is not right.");
            }
        }

        for (int state : states) {
            /** If we want to test the state EMPTY **/
            if (state == TSFConstant.STATE_BLANK) {
                // For the Buttons and texts
                if ((champType & (TYPE_BOUTON + TYPE_FIELD_TEXT)) == champType) {
                    utilsInstance.verifyEquals("", seleniumActions.getValue(fieldName), "Le champ \"" + fieldName + "\" devrait être vide.");
                    // For the Label
                } else if ((champType & TYPE_LABEL) == champType) {
                    utilsInstance.verifyEquals("", seleniumActions.getText(fieldName), "Le libellé \"" + fieldName + "\" devrait être vide.");
                } else {
                    utilsInstance.ajouterErreur(MESSAGE_NOT_IMPLEMENT);
                }
            }

            /** If we want to test the state not EMPTY **/
            else if (state == TSFConstant.STATE_NOT_BLANK) {
                // For the Buttons and texts
                if ((champType & (TYPE_BOUTON + TYPE_FIELD_TEXT)) == champType) {
                    utilsInstance
                            .verifyNotEquals("", seleniumActions.getValue(fieldName), "Le champ \"" + fieldName + "\" ne devrait pas être vide.");
                    // For the Label
                } else if ((champType & TYPE_LABEL) == champType) {
                    utilsInstance.verifyNotEquals("", seleniumActions.getText(fieldName), "Le libellé \"" + fieldName + "\" devrait être vide.");
                } else {
                    utilsInstance.ajouterErreur(MESSAGE_NOT_IMPLEMENT);
                }
            }

            /** If we want to test the state GRAY **/
            else if (state == TSFConstant.STATE_GRAY) {
                // For the Buttons and texts, checkbox and radio button
                if ((champType & (TYPE_BOUTON + TYPE_FIELD_TEXT + TYPE_CHECKBOX + TYPE_RADIO)) == champType) {
                    utilsInstance.verifyFalse(seleniumActions.isEditable(fieldName), "Le champ \"" + fieldName + "\" devrait être grisé.");
                } else {
                    utilsInstance.ajouterErreur(MESSAGE_NOT_IMPLEMENT);
                }
            }

            /** If we want to test the state not GRAY **/
            else if (state == TSFConstant.STATE_NOT_GRAY) {
                // For the Buttons and texts, checkbox and radio button
                if ((champType & (TYPE_BOUTON + TYPE_FIELD_TEXT + TYPE_CHECKBOX + TYPE_RADIO)) == champType) {
                    utilsInstance.verifyTrue(seleniumActions.isEditable(fieldName), "Le champ \"" + fieldName + "\" ne devrait pas être grisé.");
                } else {
                    utilsInstance.ajouterErreur(MESSAGE_NOT_IMPLEMENT);
                }
            }

            /** If we want to test the state CHECKED **/
            else if (state == TSFConstant.STATE_CHECKED) {
                // For the checkbox and radio button
                if ((champType & (TYPE_CHECKBOX + TYPE_RADIO)) == champType) {
                    utilsInstance.verifyTrue(seleniumActions.isChecked(fieldName), "Le champ \"" + fieldName + "\" devrait être coché.");
                } else {
                    utilsInstance.ajouterErreur(MESSAGE_NOT_IMPLEMENT);
                }
            }

            /** If we want to test the state not CHECKED **/
            else if (state == TSFConstant.STATE_NOT_CHECKED) {
                // For the checkbox and radio button
                if ((champType & (TYPE_CHECKBOX + TYPE_RADIO)) == champType) {
                    utilsInstance.verifyFalse(seleniumActions.isChecked(fieldName), "Le champ \"" + fieldName + "\" ne devrait pas être coché.");
                } else {
                    utilsInstance.ajouterErreur(MESSAGE_NOT_IMPLEMENT);
                }
            }

            /** If we want to test the state ACTIVE **/
            else if (state == TSFConstant.STATE_ACTIVE) {
                seleniumActions.focus(fieldName);
                // For the button
                if ((champType & TYPE_BOUTON) == champType) {
                    utilsInstance.verifyFalse(seleniumActions.getAttribute(fieldName, "class").contains("BUTTON_Disabled"), "Le champ \"" + fieldName
                            + "\" n'est pas actif alors qu'il le devrait");
                } else {
                    utilsInstance.verifyTrue(seleniumActions.getAttribute(fieldName, "class").contains("Actif"), "Le champ \"" + fieldName
                            + "\" n'est pas actif alors qu'il le devrait");
                }
            }
            /** If we want to test the state not ACTIVE **/
            else if (state == TSFConstant.STATE_NOT_ACTIVE) {
                // For the button
                if ((champType & TYPE_BOUTON) == champType) {
                    utilsInstance.verifyTrue(seleniumActions.getAttribute(fieldName, "class").contains("BUTTON_Disabled"), "Le champ \"" + fieldName
                            + "\" est actif alors qu'il ne le devrait pas");
                } else {
                    utilsInstance.verifyFalse(seleniumActions.getAttribute(fieldName, "class").contains("Actif"), "Le champ \"" + fieldName
                            + "\" est actif alors qu'il ne le devrait pas");
                }
            } else {
                utilsInstance.ajouterErreur(MESSAGE_NOT_IMPLEMENT);
            }
        }
    }

    /**
     * <h3>Allows the various checks related to a combo</h3> <li>Click on the icon - clicks on the first element - checking element put in the field
     * text</li> <li>One returns in the zone - Short cut of the combo - one goes down from an element - valider by enter - checking element put in the
     * field text</li>
     * 
     * @param fieldName : name of the field text related to the combo
     * @throws SeleniumException : error
     * @throws InterruptedException : error
     */
    public static void combo(String fieldName) throws SeleniumException, InterruptedException {
        // Take the initial value of the field
        String initialValue = seleniumActions.getValue(fieldName);

        /****************************************************
         * \ Check the combo while clicking on the icon * \
         ****************************************************/
        // 1 - Click on the icon
        seleniumActions.click(fieldName + "/../span[@class='ecwImageComboBox']");
        Thread.sleep(TSFConstant.THREAD_SLEEP);

        // 2 - The data of the combo is recovered: the first element
        String valeur = seleniumActions.getText(fieldName + "/..//div[@class='ecwTableBody']/table/tbody/tr[1]/td[1]");

        // 3 - One clicks on the data of the combo
        seleniumActions.click(fieldName + "/..//div[@class='ecwTableBody']/table/tbody/tr[1]/td[1]");
        if (seleniumActions.isElementPresent("//div[@id='EcwMsgBox']") && seleniumActions.isVisible("//div[@id='EcwMsgBox']")
                && seleniumActions.getText("//div[@id='EcwMsgBoxTitle']").equals("Confirmation")) {
            seleniumActions.click("//button/span[@class='ecwValidate']");
        }
        Thread.sleep(TSFConstant.THREAD_SLEEP);
        // 4 - It is checked that one has the right data
        utilsInstance.verifyEquals(valeur, seleniumActions.getValue(fieldName));

        /**************************************************
         * \ Checking of the combo by short cut keyboard * \
         **************************************************/
        // 1 - Foucs on the combo
        seleniumActions.focus(fieldName);
        // 2 - One puts white in the zone
        seleniumActions.type(fieldName, "");

        // 3 - One opens the combo with short cut PAGE_DOWN
        seleniumActions.keyPressNative(TSFKeys.PAGE_DOWN);
        if (seleniumActions.isElementPresent("//div[@id='EcwMsgBox']") && seleniumActions.isVisible("//div[@id='EcwMsgBox']")
                && seleniumActions.getText("//div[@id='EcwMsgBoxTitle']").equals("Confirmation")) {
            seleniumActions.click("//button/span[@class='ecwValidate']");
        }
        Thread.sleep(TSFConstant.THREAD_SLEEP);

        // 4 - Selection of element :
        // 4_1- If there is more than one element, one supports on the arrow of bottom to select the second
        int nombreElements = seleniumActions.getXpathCount(fieldName + "/..//div[@class='ecwTableBody']/table/tbody/tr").intValue();
        if (nombreElements > 1) {
            seleniumActions.keyPressNative(TSFKeys.DOWN);
            valeur = seleniumActions.getText(fieldName + "/..//div[@class='ecwTableBody']/table/tbody/tr[2]/td[1]");
        }

        // 4_2 - One supports on [ENTER] validating
        seleniumActions.keyPressNative(TSFKeys.ENTER);
        Thread.sleep(TSFConstant.THREAD_SLEEP);
        // 5 - One checks what has in the zone
        utilsInstance.verifyEquals(valeur, seleniumActions.getValue(fieldName));

        // One recovers the initial value in the field
        seleniumActions.focus(fieldName);
        seleniumActions.type(fieldName, "");
        seleniumActions.type(fieldName, initialValue);
        Thread.sleep(TSFConstant.THREAD_SLEEP);
    }

    /**
     * Verify radio button by click and by arrows
     * 
     * @param radioButton : radio buttons to test (key, "radioId,shortCut")
     * @throws SeleniumException : error
     * @throws InterruptedException : error
     */
    public static void radioButton(Map<String, String> radioButton) throws SeleniumException, InterruptedException {
        if (radioButton != null && !radioButton.isEmpty()) {
            // List to put the id of radio button
            List<String> radioButtons = new ArrayList<String>();
            // List to put the shortCut of radio button
            List<String> shortCutList = new ArrayList<String>();
            int length = radioButton.size();
            for (int i = 1; i < length + 1; i++) {
                if (radioButton.get("radio" + i) != null) {
                    String[] radio = radioButton.get("radio" + i).split(",");
                    radioButtons.add(radio[0]);
                    // shortCut exist
                    if (radio.length > 1)
                        shortCutList.add(radio[1]);
                }
            }
            // By click
            for (int i = 0; i < length; i++) {
                // Click the radio active
                seleniumActions.click(radioButtons.get(i));
                seleniumActions.focus(radioButtons.get(i));
                utilsInstance.verifyTrue(seleniumActions.getAttribute(radioButtons.get(i), "class").contains("Actif"));
            }
            // By -->
            for (int i = length - 1; i > 0; i--) {
                // transition from one to another, check the current radio is active
                seleniumActions.keyPressNative(TSFKeys.LEFT);
                Thread.sleep(TSFConstant.THREAD_SLEEP);
                utilsInstance.verifyTrue(seleniumActions.getAttribute(radioButtons.get(i - 1), "class").contains("Actif"));
            }
            // By <--
            for (int i = 0; i < length - 1; i++) {
                // transition from one to another, check the current radio is active
                seleniumActions.keyPressNative(TSFKeys.RIGHT);
                Thread.sleep(TSFConstant.THREAD_SLEEP);
                utilsInstance.verifyTrue(seleniumActions.getAttribute(radioButtons.get(i + 1), "class").contains("Actif"));
            }
            // By shortCut
            for (int i = 0; i < shortCutList.size(); i++) {
                // Click the radio and check radio is active
                toDoKeyboardshortCut(shortCutList.get(i));
                utilsInstance.verifyTrue(seleniumActions.getAttribute(radioButtons.get(i), "class").contains("Actif"));
            }
            // Reset the radio to the initial state
            if (!radioButtons.isEmpty()) {
                seleniumActions.click(radioButtons.get(0));
                seleniumActions.focus(radioButtons.get(0));
                utilsInstance.verifyTrue(seleniumActions.getAttribute(radioButtons.get(0), "class").contains("Actif"));
            }
            // /Clean the map
            radioButton.clear();
        }
    }

    /**
     * Method which will test the sort table without tr number
     * 
     * @param tableName : name of the table name
     * @param columns : number of the column which will be sorted, to separate them by a comma
     * @throws SeleniumException : error
     */
    public static void sortTable(String tableName, int... columns) throws SeleniumException {
        sortTable(TSFConstant.VALEUR_01, tableName, columns);
    }

    /**
     * <h3>Method which will test the table sort</h3> - test of the possible sorting of the columns<br />
     * 
     * @param row : number of the row
     * @param tableName : name of the table name
     * @param columns : number of the column which will be sorted, to separate them by a comma
     * @throws SeleniumException : error
     */
    public static void sortTable(int row, String tableName, int... columns) throws SeleniumException {
        String xpathTableauTitre = "//div[@id='" + tableName + "']//div[@class='ecwTableHead']//thead/tr[" + row + "]";
        int nombreElement = seleniumActions.getXpathCount("//div[@id='" + tableName + "']//div[@class='ecwTableBody']//tbody/tr").intValue();
        int nombreColonneVide = seleniumActions.getXpathCount(xpathTableauTitre + "//td").intValue();
        for (int column : columns) {
            utilsInstance.verifyTrue(seleniumActions.getAttribute(xpathTableauTitre + "/th[" + column + "]", "class").contains("ecwTableSortable"),
                    "La colonne " + column + " du tableau " + tableName + " ne peut pas être trié !!");
            if (!seleniumActions.getAttribute(xpathTableauTitre + "/th[" + column + "]", "class").contains("ecwTableSortIncrease")) {
                seleniumActions.click(xpathTableauTitre + "/th[" + column + "]");
            }
            // One clicks on the column to sort it
            utilsInstance.verifyTrue(seleniumActions.getAttribute(xpathTableauTitre + "/th[" + column + "]", "class")
                    .contains("ecwTableSortIncrease"), "La colonne n'est pas triée comme elle le devrait !");

            String premierElement = arrayElement(tableName, column + nombreColonneVide, 1);
            String dernierElement = arrayElement(tableName, column + nombreColonneVide, nombreElement);

            // One clicks to reverse sort
            seleniumActions.click(xpathTableauTitre + "/th[" + column + "]");
            utilsInstance.verifyTrue(seleniumActions.getAttribute(xpathTableauTitre + "/th[" + column + "]", "class")
                    .contains("ecwTableSortDecrease"), "La colonne n'est pas triée comme elle le devrait !");
            utilsInstance.verifyEquals(premierElement, arrayElement(tableName, column + nombreColonneVide, nombreElement));
            utilsInstance.verifyEquals(dernierElement, arrayElement(tableName, column + nombreColonneVide, 1));
        }
    }

    /**
     * Method which will test the sort ajax combo without tr number
     * 
     * @param comboName : name of the combo name
     * @param columns : number of the column which will be sorted, to separate them by a comma
     * @throws SeleniumException : error
     * @throws InterruptedException : error
     */
    public static void sortAjaxCombo(String comboName, int... columns) throws SeleniumException, InterruptedException {
        sortAjaxCombo(TSFConstant.VALEUR_01, comboName, columns);
    }

    /**
     * <h3>Method which will test the ajax combo sort</h3> - test of the possible sorting of the columns<br />
     * 
     * @param row : number of the row
     * @param comboName : name of the table name
     * @param columns : number of the column which will be sorted, to separate them by a comma
     * @throws SeleniumException : error
     * @throws InterruptedException : error
     */
    public static void sortAjaxCombo(int row, String comboName, int... columns) throws SeleniumException, InterruptedException {
        // Get the size of each page
        int pageSize = Integer.valueOf(seleniumActions.getAttribute(comboName, "ecwpagesize"));
        String totalPage = seleniumActions.getText("//div[@id='" + comboName + "']//div[@class='ecwTableStatus']/table/tbody/tr/td[1]");
        // Get the total rows of the combo.
        int rowNums = Integer.valueOf(totalPage.split("Total : ")[1]);
        // Get the page numbers of the combo.
        int pageNum = rowNums % pageSize == 0 ? rowNums / pageSize : rowNums / pageSize + 1;
        // Initial the current page.
        int currentPage = 0;
        String xpathTableauTitre = "//div[@id='" + comboName + "']//div[@class='ecwTableHead']//thead/tr[" + row + "]";
        int nombreElement = 0;
        int nombreColonneVide = seleniumActions.getXpathCount(xpathTableauTitre + "//td").intValue();
        for (int column : columns) {
            boolean isLastPage = false;
            boolean isFirstPage = false;
            utilsInstance.verifyTrue(seleniumActions.getAttribute(xpathTableauTitre + "/th[" + column + "]", "class").contains("ecwTableSortable"),
                    "La colonne " + column + " du tableau " + comboName + " ne peut pas être trié !!");
            // One clicks on the column to sort it
            if (!seleniumActions.getAttribute(xpathTableauTitre + "/th[" + column + "]", "class").contains("ecwTableSortIncrease")) {
                seleniumActions.click(xpathTableauTitre + "/th[" + column + "]");
            }
            utilsInstance.verifyTrue(seleniumActions.getAttribute(xpathTableauTitre + "/th[" + column + "]", "class")
                    .contains("ecwTableSortIncrease"), "La colonne n'est pas triée comme elle le devrait !");
            String premierElement = arrayElement(comboName, column + nombreColonneVide, 1);
            // One clicks to reverse sort
            seleniumActions.click(xpathTableauTitre + "/th[" + column + "]");
            utilsInstance.verifyTrue(seleniumActions.getAttribute(xpathTableauTitre + "/th[" + column + "]", "class")
                    .contains("ecwTableSortDecrease"), "La colonne n'est pas triée comme elle le devrait !");
            if (pageNum > 1) {
                while (!isLastPage) {
                    // Click the next page button.
                    seleniumActions.click("//div[@id='" + comboName + "']//span[@class='ecwImageComboNextPage']");
                    Thread.sleep(TSFConstant.THREAD_SLEEP_SHORT);
                    // Get the current page.
                    currentPage = Integer.valueOf(seleniumActions.getText(
                            "//div[@id='" + comboName + "']//div[@class='ecwTableStatus']/table/tbody/tr/td[3]").split("/")[0].split("Page : ")[1]);
                    if (currentPage == pageNum) {
                        isLastPage = true;
                    }
                }
                nombreElement = seleniumActions.getXpathCount("//div[@id='" + comboName + "']//div[@class='ecwTableBody']//tbody/tr").intValue();
                utilsInstance.verifyEquals(premierElement, arrayElement(comboName, column + nombreColonneVide, nombreElement));
                while (!isFirstPage) {
                    // Click the previous page button.
                    seleniumActions.click("//div[@id='" + comboName + "']//span[@class='ecwImageComboPreviousPage']");
                    Thread.sleep(TSFConstant.THREAD_SLEEP_SHORT);
                    // Get the current page.
                    currentPage = Integer.valueOf(seleniumActions.getText(
                            "//div[@id='" + comboName + "']//div[@class='ecwTableStatus']/table/tbody/tr/td[3]").split("/")[0].split("Page : ")[1]);
                    if (currentPage == 1) {
                        isFirstPage = true;
                    }
                }
                // Page num = 1
            } else {
                nombreElement = seleniumActions.getXpathCount("//div[@id='" + comboName + "']//div[@class='ecwTableBody']//tbody/tr").intValue();
                utilsInstance.verifyEquals(premierElement, arrayElement(comboName, column + nombreColonneVide, nombreElement));
            }
        }
    }

    /**
     * Method which will test the sort ajax table without tr number
     * 
     * @param tableName : name of the table name
     * @param columns : number of the column which will be sorted, to separate them by a comma
     * @throws SeleniumException : error
     */
    public static void sortAjaxTable(String tableName, int... columns) throws SeleniumException {
        sortAjaxTable(TSFConstant.VALEUR_01, tableName, columns);
    }

    /**
     * <h3>Method which will test the ajax table sort</h3> - test of the possible sorting of the columns<br />
     * 
     * @param row : number of the row
     * @param tableName : name of the table name
     * @param columns : number of the column which will be sorted, to separate them by a comma
     * @throws SeleniumException : error
     */
    public static void sortAjaxTable(int row, String tableName, int... columns) throws SeleniumException {
        String xpathTableauTitre = "//div[@id='" + tableName + "']//div[@class='ecwTableHead']//thead/tr[" + row + "]";
        int nombreElement = 0;
        int nombreColonneVide = seleniumActions.getXpathCount(xpathTableauTitre + "//td").intValue();
        for (int column : columns) {
            utilsInstance.verifyTrue(seleniumActions.getAttribute(xpathTableauTitre + "/th[" + column + "]", "class").contains("ecwTableSortable"),
                    "La colonne " + column + " du tableau " + tableName + " ne peut pas être trié !!");
            // One clicks on the column to sort it
            if (!seleniumActions.getAttribute(xpathTableauTitre + "/th[" + column + "]", "class").contains("ecwTableSortIncrease")) {
                seleniumActions.click(xpathTableauTitre + "/th[" + column + "]");
            }
            utilsInstance.verifyTrue(seleniumActions.getAttribute(xpathTableauTitre + "/th[" + column + "]", "class")
                    .contains("ecwTableSortIncrease"), "La colonne n'est pas triée comme elle le devrait !");
            String premierElement = arrayElement(tableName, column + nombreColonneVide, 1);
            // One clicks to reverse sort
            seleniumActions.click(xpathTableauTitre + "/th[" + column + "]");
            utilsInstance.verifyTrue(seleniumActions.getAttribute(xpathTableauTitre + "/th[" + column + "]", "class")
                    .contains("ecwTableSortDecrease"), "La colonne n'est pas triée comme elle le devrait !");
            seleniumActions.click("//span[@class='ecwTableLastPage']");
            nombreElement = seleniumActions.getXpathCount("//div[@id='" + tableName + "']//div[@class='ecwTableBody']//tbody/tr").intValue();
            utilsInstance.verifyEquals(premierElement, arrayElement(tableName, column + nombreColonneVide, nombreElement));
            seleniumActions.click("//span[@class='ecwTableFirstPage']");
        }
    }

    /**
     * Check the pagination of the ajax combo
     * 
     * @param comboName String
     * @throws SeleniumException : error
     * @throws InterruptedException : error
     */
    public static void checkAjaxComboPagination(String comboName) throws SeleniumException, InterruptedException {
        seleniumActions.click("//div[@id='" + comboName + "']/span");
        // Get the size of each page
        int pageSize = Integer.valueOf(seleniumActions.getAttribute(comboName, "ecwpagesize"));
        String totalPage = seleniumActions.getText("//div[@id='" + comboName + "']//div[@class='ecwTableStatus']/table/tbody/tr/td[1]");
        // Get the total rows of the combo.
        int rowNums = Integer.valueOf(totalPage.split("Total : ")[1]);
        // Get the page numbers of the combo.
        int pageNum = rowNums % pageSize == 0 ? rowNums / pageSize : rowNums / pageSize + 1;
        // Get the current page.
        int currentPage = Integer.valueOf(seleniumActions
                .getText("//div[@id='" + comboName + "']//div[@class='ecwTableStatus']/table/tbody/tr/td[3]").split("/")[0].split("Page : ")[1]);
        if (pageNum > 1) {
            if (currentPage == pageNum) {
                seleniumActions.click("//div[@id='" + comboName + "']//div[@class='ecwTableStatus']/table/tbody/tr/td[2]/span");
                Thread.sleep(TSFConstant.THREAD_SLEEP_SHORT);
            }
            // Click the next page button.
            seleniumActions.click("//div[@id='" + comboName + "']//div[@class='ecwTableStatus']/table/tbody/tr/td[3]/span");
            Thread.sleep(TSFConstant.THREAD_SLEEP_SHORT);
            // Current page number changed(add 1).
            String displayCurrentPage = seleniumActions.getText("//div[@id='" + comboName + "']//div[@class='ecwTableStatus']/table/tbody/tr/td[3]")
                    .split("/")[0].split("Page : ")[1];
            utilsInstance.verifyEquals(displayCurrentPage, (currentPage + 1) + "", "Go to the wrong page!");

            // Click the previous page button.
            seleniumActions.click("//div[@id='" + comboName + "']//div[@class='ecwTableStatus']/table/tbody/tr/td[2]/span");
            Thread.sleep(TSFConstant.THREAD_SLEEP_SHORT);
            // Current page number changed(minus 1).
            displayCurrentPage = seleniumActions.getText("//div[@id='" + comboName + "']//div[@class='ecwTableStatus']/table/tbody/tr/td[3]").split(
                    "/")[0].split("Page : ")[1];
            utilsInstance.verifyEquals(displayCurrentPage, currentPage + "", "Go to the wrong page!");
        }
    }

    /**
     * Check the pagination of the ajax table
     * 
     * @param tableName String
     * @throws SeleniumException : error
     * @throws InterruptedException : error
     */
    public static void checkAjaxTablePagination(String tableName) throws SeleniumException, InterruptedException {
        // Get the size of each page.
        int pageSize = Integer.valueOf(seleniumActions.getAttribute(tableName, "ecwpagesize"));

        String diaplayPage = seleniumActions.getText("//tr[@class='ecwTablePagination']/td/table/tbody/tr/td[1]/div");
        // Get the total rows of the table.
        int rowNums = Integer.valueOf(diaplayPage.split("sur ")[1]);
        // Get the page numbers of the table.
        int pageNum = rowNums % pageSize == 0 ? rowNums / pageSize : rowNums / pageSize + 1;
        if (pageNum > 1) {
            String currentPage = "";
            // Click the next page button.
            seleniumActions.click("//span[@class='ecwTableNextPage']");
            // Click the previous page button.
            seleniumActions.click("//span[@class='ecwTablePreviousPage']");

            // Click the last page button.
            seleniumActions.click("//span[@class='ecwTableLastPage']");
            // The pagination of the last page becomes bold.
            currentPage = seleniumActions.getText("//tr[@class='ecwTablePagination']/td/table/tbody/..//div/b");
            utilsInstance.verifyEquals(currentPage, pageNum + "", "Go to the wrong page!");

            // Click the first page button.
            seleniumActions.click("//span[@class='ecwTableFirstPage']");
            // The pagination '1' becomes bold.
            currentPage = seleniumActions.getText("//tr[@class='ecwTablePagination']/td/table/tbody/..//div/b");
            utilsInstance.verifyEquals(currentPage, "1", "Go to the wrong page!");

            // Enter a nonexistent page number in textBox 'Aller à la page'.
            seleniumActions.type("inputGoToPage_" + tableName, (pageNum + 2) + "");
            seleniumActions.click("//span[@class='ecwTableGoToPage']");
            Thread.sleep(TSFConstant.THREAD_SLEEP_SHORT);
            // The pagination has no changing.
            currentPage = seleniumActions.getText("//tr[@class='ecwTablePagination']/td/table/tbody/..//div/b");
            utilsInstance.verifyEquals(currentPage, "1", "Go to the wrong page!");

            // Enter a specific page number in textBox 'Aller à la page'.
            seleniumActions.type("inputGoToPage_" + tableName, pageNum + "");
            seleniumActions.click("//span[@class='ecwTableGoToPage']");
            Thread.sleep(TSFConstant.THREAD_SLEEP_SHORT);
            // The pagination changed.
            currentPage = seleniumActions.getText("//tr[@class='ecwTablePagination']/td/table/tbody/..//div/b");
            utilsInstance.verifyEquals(currentPage, pageNum + "", "Go to the wrong page!");
        }
    }

    /**
     * Checking of the return by arrow or ESC
     * 
     * @param fieldName : field name of the IHM which will make it possible to recognize it - this zone can be the wording, a text field, a span or
     *            other
     * @param fieldValue : value having to be present in previous page
     * @param type : arrow or ESC
     * @throws SeleniumException : error
     * @throws InterruptedException : error
     */
    public static void returns(String fieldName, String fieldValue, String type) throws SeleniumException, InterruptedException {
        String value;
        // Return basic screen by Arrow or ESC
        boolean typePopup = seleniumActions.isElementPresent("//div[@id='ecwPopup0']");
        if (StringUtils.equals(TSFConstant.RETURN_TYPE_ARROW, type)) {
            seleniumActions.click("//span[@class='ecwTitleButtonBackIhm']");
        } else if (StringUtils.equals(TSFConstant.RETURN_TYPE_ESC, type)) {
            seleniumActions.keyPressNative(TSFKeys.ESCAPE);
        }
        if (typePopup) {
            Thread.sleep(TSFConstant.THREAD_SLEEP_SHORT);
        } else {
            seleniumActions.waitForPageToLoad(TSFConstant.WAIT_FOR_PAGE_TO_LOAD);
            Thread.sleep(TSFConstant.THREAD_LONG_SLEEP);
        }
        // To check that one is well on the principal page
        seleniumActions.selectDefaultFrame();
        seleniumActions.selectFrame("main");
        if (seleniumActions.isElementPresent(fieldName)) {
            value = seleniumActions.getText(fieldName);
        } else {
            seleniumActions.selectDefaultFrame();
            seleniumActions.selectFrame("menu");
            value = seleniumActions.getText(fieldName);
        }
        if (StringUtils.equals("Arrow", type)) {
            utilsInstance.verifyEquals(fieldValue, value, "We did not arrive on the previous page with the click marks with arrows return");
        } else if (StringUtils.equals("ESC", type)) {
            utilsInstance.verifyEquals(fieldValue, value, "We did not arrive on the previous page with ESCAPE" + value + fieldValue);
        }
    }

    /**
     * Test the "Tab" key
     * 
     * @param areaToRemove : the area which need not verify.
     * @param areaToJump : the area which need jump.
     * @throws SeleniumException : error
     */
    public static void checkTabulation(List<String> areaToRemove, List<String> areaToJump) throws SeleniumException {
        checkTabulation(null, areaToRemove, areaToJump);
    }

    /**
     * Test the "Tab" key
     * 
     * @param formName : the name of the current active form
     * @param areaToRemove : the area which need not verify.
     * @param areaToJump : the area which need jump.
     * @throws SeleniumException : error
     */
    // CHECKSTYLE:OFF (more than 50 lines)
    public static void checkTabulation(String formName, List<String> areaToRemove, List<String> areaToJump) throws SeleniumException {
        // CHECKSTYLE:ON
        String tagErased = "";
        if (formName != null) {
            tagErased = "||(inputs[i].tagName.toUpperCase() == 'FORM' && inputs[i].getAttribute('name') && inputs[i].getAttribute('name') != '"
                    + formName + "')";
        }
        // /Get all the tagName in the htmlSource and then get the tag which we want.
        String allActiveArea = (String) seleniumActions
                .getEval("(function(){ var allArea = ''; var areaItem = ''; var trouve;"
                        + "var inputs = window.document.body.getElementsByTagName('*');"
                        + "for (var i = 0; i < inputs.length; i++) {trouve = false;"
                        +
                        // If the tag contains the temp attribute, remove these tag from the list.
                        "if (inputs[i].getAttribute('tempDisplayNone') && inputs[i].getAttribute('tempDisplayNone') == 'true') {"
                        + "inputs[i].removeAttribute('tempDisplayNone');continue;} "
                        +
                        // If the tag is not displayed, remove these tag and children node from the list.
                        "else if (inputs[i].getAttribute('class') == 'saisieIdStyleCache' || inputs[i].getAttribute('style') "
                        + "&& inputs[i].getAttribute('style').trim().indexOf('display: none') > -1 || inputs[i].getAttribute('style') && inputs[i].getAttribute('style').trim().indexOf('display:none') > -1 || inputs[i].getAttribute('style') "
                        + "&& inputs[i].getAttribute('style').trim().indexOf('visibility: hidden') > -1 || inputs[i].getAttribute('style') && inputs[i].getAttribute('style').trim().indexOf('visibility:hidden') > -1 "
                        + ""
                        + tagErased
                        + ") {"
                        + "var childNodes = inputs[i].getElementsByTagName('*'); for (var i0 = 0; i0 < childNodes.length; i0++) {"
                        + "var children = childNodes[i0]; children.setAttribute('tempDisplayNone', 'true');}} "
                        +
                        // Get the tag which type is "INPUT".
                        "else if ((inputs[i].getAttribute('type') && inputs[i].getAttribute('type') != 'hidden' && inputs[i].getAttribute('disabled') != '' && inputs[i].getAttribute('disabled') != 'disabled') "
                        + "|| (inputs[i].getAttribute('id') && inputs[i].getAttribute('id') != '' && inputs[i].getAttribute('id').indexOf('inputGoToPage') > -1) "
                        + "|| (inputs[i].getAttribute('size') && inputs[i].getAttribute('class') && inputs[i].getAttribute('class').indexOf('INPUT_text') > -1 && inputs[i].getAttribute('disabled') != '' && inputs[i].getAttribute('disabled') != 'disabled')) {"
                        + "if ((inputs[i].tagName.toUpperCase() == 'INPUT' && inputs[i].getAttribute('id') != '' && ((inputs[i].getAttribute('type') == 'radio' && inputs[i].checked) "
                        + "|| inputs[i].getAttribute('type') == 'text' || inputs[i].getAttribute('type') == 'checkbox')) || (inputs[i].getAttribute('id') && inputs[i].getAttribute('id') != '' && inputs[i].getAttribute('id').indexOf('inputGoToPage') > -1)"
                        + "|| (inputs[i].getAttribute('size') && inputs[i].getAttribute('class') && inputs[i].getAttribute('class').indexOf('INPUT_text') > -1)) {"
                        + "trouve = true;} "
                        + "else if (inputs[i].tagName.toUpperCase() == 'BUTTON') {trouve = true;}} "
                        + "else if (inputs[i].tagName.toUpperCase() == 'TEXTAREA' && inputs[i].getAttribute('class') != 'TEXTAREA_Disabled') { trouve = true;} "
                        +
                        // Get the tag which type is "Table" and "tab label".
                        "else if (inputs[i].tagName.toUpperCase() == 'DIV' && inputs[i].getAttribute('class')) {"
                        + "if ( (inputs[i].getAttribute('class') == 'ecwTable' || inputs[i].getAttribute('class') == 'ecwTableActive ecwTable' ||"
                        + "inputs[i].getAttribute('class') == 'ecwTableV2' || inputs[i].getAttribute('class') == 'ecwTableActive ecwTableV2') "
                        + "|| inputs[i].getAttribute('class').indexOf('ecwTabLabelActif') > -1) {trouve = true;}}"
                        +
                        // Get the tag which type is "A".
                        "else if (inputs[i].tagName.toUpperCase() == 'A') {trouve = true;} "
                        +
                        // Put the id or class to the allArea.
                        "if (trouve == true) {if (inputs[i].getAttribute('id')) {areaItem = inputs[i].getAttribute('id');} "
                        + "else if (inputs[i].getAttribute('class') && inputs[i].tagName.toUpperCase() != 'A') {areaItem = inputs[i].getAttribute('class');}"
                        + "else {areaItem = inputs[i].getAttribute('onclick');}" + "allArea += areaItem + ',';}} return allArea;})()");
        if (!allActiveArea.isEmpty()) {
            // /Remove the area which should not verify.
            if (areaToRemove != null && !areaToRemove.isEmpty()) {
                for (String area : areaToRemove) {
                    if (allActiveArea.contains(area)) {
                        allActiveArea = allActiveArea.replace(area + ",", "");
                    }
                }
            }
            String[] allActiveAreaList = allActiveArea.split(",");
            // Focus the first area which has the "id" attribute.
            if (seleniumActions.isElementPresent(allActiveAreaList[0])) {
                // If this area is a table, click the table head to let the table active.
                if (seleniumActions.getAttribute(allActiveAreaList[0], "class").contains("ecwTable")) {
                    if (!seleniumActions.isElementPresent("//div[@id='" + allActiveAreaList[0]
                            + "']/div[@class='ecwTableBody']/table/tbody/tr[@class='ecwTableLineEmpty']")) {
                        seleniumActions.click("//div[@id='" + allActiveAreaList[0] + "']/div[@class='ecwTableBody']/table/tbody/tr[1]/td");
                    } else {
                        if (!seleniumActions.getAttribute(allActiveAreaList[0], "class").contains("Actif")
                                && !seleniumActions.getAttribute(allActiveAreaList[0], "class").contains("ecwTableActive")) {
                            allActiveAreaList = allActiveArea.replace(allActiveAreaList[0] + ",", "").split(",");
                        }
                    }
                } else {
                    // Focus on this area.
                    seleniumActions.focus(allActiveAreaList[0]);
                }
                // Click the area which has not the "id" attribute.
            } else if (seleniumActions.isElementPresent("//div[@class='" + allActiveAreaList[0] + "']")) {
                seleniumActions.click("//div[@class='" + allActiveAreaList[0] + "']");
                // Focus on the area which tag is 'A'.
            } else if (seleniumActions.isElementPresent("//a[@onclick='" + allActiveAreaList[0] + "']")) {
                seleniumActions.focus("//a[@onclick='" + allActiveAreaList[0] + "']");
            }
            // /Test tab
            for (int i = 0; i < allActiveAreaList.length; i++) {
                // /If there are many areas with the same id, we need not check these areas.
                if (areaToJump != null && !areaToJump.isEmpty() && areaToJump.contains(allActiveAreaList[i])) {
                    seleniumActions.keyPressNative(TSFKeys.TAB);
                } else {
                    // If the current item has "id" attribute.
                    if (seleniumActions.isElementPresent(allActiveAreaList[i])) {
                        // If current area is active.
                        if (seleniumActions.getAttribute(allActiveAreaList[i], "class").contains("Actif")
                                || seleniumActions.getAttribute(allActiveAreaList[i], "class").contains("ecwTableActive")) {
                            // Press tab
                            seleniumActions.keyPressNative(TSFKeys.TAB);
                        } else {
                            utilsInstance.ajouterErreur("The current area " + allActiveAreaList[i] + " is not active...");
                        }
                        // The current item has not "id" attribute or the current item is tab label.
                    } else if (seleniumActions.isElementPresent("//div[@class='" + allActiveAreaList[i] + "']")
                            || allActiveAreaList[i].contains("ecwTabLabel")) {
                        if (allActiveAreaList[i].contains("Actif")) {
                            seleniumActions.keyPressNative(TSFKeys.TAB);
                        } else {
                            utilsInstance.ajouterErreur("The current area " + allActiveAreaList[i] + " is not active...");
                        }
                        // The current item tag is "A".
                    } else if (seleniumActions.isElementPresent("//a[@onclick='" + allActiveAreaList[i] + "']")) {
                        if (seleniumActions.getAttribute("//a[@onclick='" + allActiveAreaList[i] + "']", "class").contains("Actif")) {
                            seleniumActions.keyPressNative(TSFKeys.TAB);
                        } else {
                            utilsInstance.ajouterErreur("The current area " + allActiveAreaList[i] + " is not active...");
                        }
                    }
                }
            }
        }
    }

    /**
     * Check search button
     * 
     * @param verifyRechercher : data to check with button Rechercher
     * @param verifyNouvRecherche : data to check with button NouvRecherche
     * @throws SeleniumException : error
     * @throws InterruptedException : error
     */
    public static void checkButtonRecherche(Map<String, String> verifyRechercher, Map<String, String> verifyNouvRecherche) throws SeleniumException,
            InterruptedException {
        // verify with button 'Rechercher'
        if (verifyRechercher != null && !verifyRechercher.isEmpty()) {
            // Click the button 'Rechercher'
            if (verifyRechercher.get("ButtonRechercher") != null) {
                seleniumActions.click(verifyRechercher.get("ButtonRechercher"));
                Thread.sleep(TSFConstant.THREAD_SLEEP);
                verifyRechercher.remove("ButtonRechercher");
            }
            // Verify the data
            checkRechOrNouvRech(verifyRechercher);
        }
        // verify with button NouvRecherche
        if (verifyNouvRecherche != null && !verifyNouvRecherche.isEmpty()) {
            // Click the button 'NouvRecherche'
            if (verifyNouvRecherche.get("ButtonNouvRecherche") != null) {
                seleniumActions.click(verifyNouvRecherche.get("ButtonNouvRecherche"));
                Thread.sleep(TSFConstant.THREAD_SLEEP);
                verifyNouvRecherche.remove("ButtonNouvRecherche");
            }
            // Verify the data
            checkRechOrNouvRech(verifyNouvRecherche);
        }
    }

    /**
     * Verify the data after click the button 'Recherche' or 'Nouvelle Recherche'
     * 
     * @param verifyMap : value to check
     * @throws SeleniumException : error
     */
    private static void checkRechOrNouvRech(Map<String, String> verifyMap) throws SeleniumException {
        for (Entry<String, String> entry : verifyMap.entrySet()) {
            String value = entry.getValue();
            String area = entry.getKey();
            // /Verify the data after click the button 'Recherche' or 'Nouvelle Recherche'
            // /Check the button
            if (area.startsWith("activeButton")) {
                String[] activeButtons = value.split(",");
                for (String activeButton : activeButtons) {
                    attributesField(activeButton, TSFConstant.STATE_NOT_GRAY);
                }
            } else if (area.startsWith("inactiveButton")) {
                String[] inactiveButtons = value.split(",");
                for (String inactiveButton : inactiveButtons) {
                    attributesField(inactiveButton, TSFConstant.STATE_GRAY);
                }
                // /Check the area for initialize
            } else if (area.startsWith("activeArea")) {
                String[] activeAreas = value.split(",");
                for (String activeArea : activeAreas) {
                    utilsInstance.verifyTrue(seleniumActions.isElementPresent(activeArea));
                }
            } else if (area.startsWith("inactiveArea")) {
                String[] inactiveAreas = value.split(",");
                for (String inactiveArea : inactiveAreas) {
                    if (seleniumActions.isElementPresent(inactiveArea)) {
                        utilsInstance.verifyFalse(seleniumActions.isVisible(inactiveArea));
                    } else {
                        utilsInstance.verifyFalse(seleniumActions.isElementPresent(inactiveArea));
                    }
                }
                // /Check the postion of focus
            } else if (area.startsWith("positionCurseur")) {
                utilsInstance.verifyTrue(seleniumActions.getAttribute(value, "class").contains("Actif"));
                // /Check text value
            } else {
                utilsInstance.verifyEquals(value, seleniumActions.getText(area));
            }
        }
        if (!verifyMap.isEmpty()) {
            verifyMap.clear();
        }
    }

    /**
     * Method which will test the double table by arrow
     * 
     * @param leftTableName : the name of left table
     * @param rightTableName : the name of right table
     * @throws SeleniumException : error
     * @throws InterruptedException : error
     */
    // CHECKSTYLE:OFF (more than 50 lines)
    public static void checkDoubleTable(String leftTableName, String rightTableName) throws SeleniumException, InterruptedException {
        // CHECKSTYLE:ON
        // Initialization : Transfer all records in the left table
        seleniumActions.click("//div[@class='ecwTableDoubleSelect']//span[@class='ecwTableRemoveAllLine']");
        Thread.sleep(TSFConstant.THREAD_SLEEP);

        // Select the first record in the left table and pass it on the right table by the unitary arrow
        seleniumActions.click("//div[@id='" + leftTableName + "']//div[@class='ecwTableBody']//tbody/tr[1]");
        seleniumActions.click("//div[@class='ecwTableDoubleSelect']//span[@class='ecwTableAddLine']");
        Thread.sleep(TSFConstant.THREAD_SLEEP);
        // Verify there is one line in the right table
        utilsInstance.verifyEquals(1, seleniumActions.getXpathCount("//div[@id='" + rightTableName + "']//div[@class='ecwTableBody']//tbody/tr")
                .intValue());

        // Select the record in the right table and pass it in the left table with the unitary arrow
        seleniumActions.click("//div[@id='" + rightTableName + "']//div[@class='ecwTableBody']//tbody/tr[1]");
        seleniumActions.click("//div[@class='ecwTableDoubleSelect']//span[@class='ecwTableRemoveLine']");
        Thread.sleep(TSFConstant.THREAD_SLEEP);
        // Verify there is no line in the right table
        if (seleniumActions.isElementPresent("//div[@id='" + rightTableName + "']/div[@class='ecwTableBody']/table/tbody/tr")) {
           utilsInstance.verifyTrue(seleniumActions.getAttribute("//div[@id='" + rightTableName + "']/div[@class='ecwTableBody']/table/tbody/tr@class").contains("ecwTableLineEmpty"));
        } else {
            utilsInstance.verifyFalse(seleniumActions
                    .isElementPresent("//div[@id='" + rightTableName + "']/div[@class='ecwTableBody']/table/tbody/tr[1]"));
        }

        // Click the right arrow (all lines)
        seleniumActions.click("//div[@class='ecwTableDoubleSelect']//span[@class='ecwTableAddAllLine']");
        Thread.sleep(TSFConstant.THREAD_SLEEP);
        // Verify there is no line in the left table
        if (seleniumActions.isElementPresent("//div[@id='" + leftTableName + "']/div[@class='ecwTableBody']/table/tbody/tr")) {
            utilsInstance.verifyTrue(seleniumActions.getAttribute("//div[@id='" + leftTableName + "']/div[@class='ecwTableBody']/table/tbody/tr@class").contains("ecwTableLineEmpty"));
        } else {
            utilsInstance.verifyFalse(seleniumActions
                    .isElementPresent("//div[@id='" + leftTableName + "']/div[@class='ecwTableBody']/table/tbody/tr[1]"));
        }

        // Click the left arrow (all lines)
        seleniumActions.click("//div[@class='ecwTableDoubleSelect']//span[@class='ecwTableRemoveAllLine']");
        Thread.sleep(TSFConstant.THREAD_SLEEP);
        // Verify there is no line in the right table
        if (seleniumActions.isElementPresent("//div[@id='" + rightTableName + "']/div[@class='ecwTableBody']/table/tbody/tr")) {
            utilsInstance.verifyTrue(seleniumActions.getAttribute("//div[@id='" + rightTableName + "']/div[@class='ecwTableBody']/table/tbody/tr@class").contains("ecwTableLineEmpty"));
        } else {
            utilsInstance.verifyFalse(seleniumActions
                    .isElementPresent("//div[@id='" + rightTableName + "']/div[@class='ecwTableBody']/table/tbody/tr[1]"));
        }

        // Select the first record in the left table
        seleniumActions.click("//div[@id='" + leftTableName + "']//div[@class='ecwTableBody']//tbody/tr[1]");
        // do "shift" then click to the third record if exist otherwise click on the second recording if exist otherwise do nothing
        int leftTableNum = seleniumActions.getXpathCount("//div[@id='" + leftTableName + "']//div[@class='ecwTableBody']//tbody/tr").intValue();
        int leftNum = 1;
        if (leftTableNum >= TSFConstant.VALEUR_03) {
            leftNum = TSFConstant.VALEUR_03;
        } else if (leftTableNum == TSFConstant.VALEUR_02) {
            leftNum = TSFConstant.VALEUR_02;
        }
        if (leftNum > 1) {
            seleniumActions.shiftKeyDown();
            seleniumActions.click("//div[@id='" + leftTableName + "']//div[@class='ecwTableBody']//tbody/tr[" + leftNum + "]");
            seleniumActions.shiftKeyUp();
            // Click the right arrow unitary
            seleniumActions.click("//div[@class='ecwTableDoubleSelect']//span[@class='ecwTableAddLine']");
            Thread.sleep(TSFConstant.THREAD_SLEEP);
            // Verify there are 2 or 3 lines in the left table
            utilsInstance.verifyEquals(leftNum,
                    seleniumActions.getXpathCount("//div[@id='" + rightTableName + "']//div[@class='ecwTableBody']//tbody/tr").intValue());
        }

        // Select the first record in the right table
        seleniumActions.click("//div[@id='" + rightTableName + "']//div[@class='ecwTableBody']//tbody/tr[1]");
        // do "shift" then click to the third record if exist otherwise click on the second recording if exist otherwise do nothing
        int rightTableNum = seleniumActions.getXpathCount("//div[@id='" + rightTableName + "']//div[@class='ecwTableBody']//tbody/tr").intValue();
        int rightNum = 1;
        if (rightTableNum >= TSFConstant.VALEUR_03) {
            rightNum = TSFConstant.VALEUR_03;
        } else if (rightTableNum == TSFConstant.VALEUR_02) {
            rightNum = TSFConstant.VALEUR_02;
        }
        if (rightNum > 1) {
            seleniumActions.shiftKeyDown();
            seleniumActions.click("//div[@id='" + rightTableName + "']//div[@class='ecwTableBody']//tbody/tr[" + rightNum + "]");
            seleniumActions.shiftKeyUp();
            // Click the left arrow unitary
            seleniumActions.click("//div[@class='ecwTableDoubleSelect']//span[@class='ecwTableRemoveLine']");
            Thread.sleep(TSFConstant.THREAD_SLEEP);
            // Verify there is no line in the right table
            if (seleniumActions.isElementPresent("//div[@id='" + rightTableName + "']/div[@class='ecwTableBody']/table/tbody/tr")) {
                utilsInstance.verifyTrue(seleniumActions.getAttribute("//div[@id='" + rightTableName + "']/div[@class='ecwTableBody']/table/tbody/tr@class").contains("ecwTableLineEmpty"));
            } else {
                utilsInstance.verifyFalse(seleniumActions
                        .isElementPresent("//div[@id='" + rightTableName + "']/div[@class='ecwTableBody']/table/tbody/tr[1]"));
            }
        }

        // Select the first record in the left table
        seleniumActions.click("//div[@id='" + leftTableName + "']//div[@class='ecwTableBody']//tbody/tr[1]");
        // press Ctrl, Alt and click to the third recording if there is otherwise click the second recording if there is otherwise nothing
        if (leftNum > 1) {
            seleniumActions.controlKeyDown();
            seleniumActions.altKeyDown();
            seleniumActions.click("//div[@id='" + leftTableName + "']//div[@class='ecwTableBody']//tbody/tr[" + leftNum + "]");
            seleniumActions.altKeyUp();
            seleniumActions.controlKeyUp();
            // Click the right arrow unitary
            seleniumActions.click("//div[@class='ecwTableDoubleSelect']//span[@class='ecwTableAddLine']");
            Thread.sleep(TSFConstant.THREAD_SLEEP);
            // Verify there are 2 lines in the left table
            utilsInstance.verifyEquals(2, seleniumActions.getXpathCount("//div[@id='" + rightTableName + "']//div[@class='ecwTableBody']//tbody/tr")
                    .intValue());
        }

        // Select the first record in the right table
        seleniumActions.click("//div[@id='" + rightTableName + "']//div[@class='ecwTableBody']//tbody/tr[1]");
        // press Ctrl, Alt and click to the third recording if there is otherwise click the second recording if there is otherwise nothing
        if (rightNum > 1) {
            seleniumActions.controlKeyDown();
            seleniumActions.altKeyDown();
            seleniumActions.click("//div[@id='" + rightTableName + "']//div[@class='ecwTableBody']//tbody/tr[2]");
            seleniumActions.altKeyUp();
            seleniumActions.controlKeyUp();
            // Click the left arrow unitary
            seleniumActions.click("//div[@class='ecwTableDoubleSelect']//span[@class='ecwTableRemoveLine']");
            Thread.sleep(TSFConstant.THREAD_SLEEP);
            // Verify there is no line in the right table
            if (seleniumActions.isElementPresent("//div[@id='" + rightTableName + "']/div[@class='ecwTableBody']/table/tbody/tr")) {
                utilsInstance.verifyTrue(seleniumActions.getAttribute("//div[@id='" + rightTableName + "']/div[@class='ecwTableBody']/table/tbody/tr@class").contains("ecwTableLineEmpty"));
            } else {
                utilsInstance.verifyFalse(seleniumActions
                        .isElementPresent("//div[@id='" + rightTableName + "']/div[@class='ecwTableBody']/table/tbody/tr[1]"));
            }
        }
    }

    /**
     * Test the message on the message bar without some values {0} represnts any characters. Example : If the message like 'Création du mannequin
     * MANNE005 (badge : 2208) effectuée', MANNE005 and 2208 are generated by system and we have not the possibility to test it. Between 2 tests,
     * there are not the same. the parameter message should be 'Création du mannequin {0} (badge : {0}) effectuée'.
     * 
     * @param message : message displayed on the message bar
     * @param messageType : warning ? information ? error
     * @throws SeleniumException : error
     */
    public static void messageBarWithoutValues(String message, String messageType) throws SeleniumException {
        String messageRecu;
        boolean result = false;
        String tempMessageStr = "";
        for (int i = 0; i < message.length(); i++) {
            if (REGEULAR_EXPRESSION_CHARS.contains(message.charAt(i) + "")) {
                tempMessageStr += "\\";
            }
            tempMessageStr += message.charAt(i);
        }
        try {
            messageRecu = seleniumActions.getText("//div[@id='EcwMessageBar']");
            tempMessageStr = StringUtils.replace(tempMessageStr, "{0}", ".*");
            Pattern pattern = Pattern.compile(tempMessageStr);
            Matcher matcher = pattern.matcher(messageRecu);
            while (matcher.find()) {
                result = true;
            }
        } catch (SeleniumException e) {
            utilsInstance.ajouterErreur("La barre de message n'est pas présente", e);
            return;
        }
        String attribute = seleniumActions.getAttribute("//div[@id='EcwMessageBar']", "class");
        utilsInstance.verifyTrue(result, "Le message n'est pas le bon : \"" + messageRecu + "\" n'est pas le message attendu : \"" + tempMessageStr
                + "\"");
        utilsInstance.verifyTrue(attribute.compareTo(messageType) == 0, "Le message n'est pas du bon type : " + messageType + " attendu " + attribute
                + " obtenu");
    }

    /**
     * Check fields that are in upper case
     * 
     * @param inputValue String
     * @param currentZone String
     * @param changeLowercaseUppercase boolean
     * @throws SeleniumException error
     * @throws InterruptedException error
     */
    public static void characteristicsZone(String inputValue, String currentZone, boolean changeLowercaseUppercase) throws SeleniumException,
            InterruptedException {
        characteristicsZone(inputValue, 0, currentZone, changeLowercaseUppercase);
    }

    /**
     * Check fields that are in upper case and test field length
     * 
     * @param inputValue String
     * @param zoneSize int
     * @param currentZone String
     * @param isUpperCase boolean
     * @throws SeleniumException error
     * @throws InterruptedException error
     */
    public static void characteristicsZone(String inputValue, int zoneSize, String currentZone, boolean isUpperCase) throws SeleniumException,
            InterruptedException {
        // Get the initial value of zone.
        String initialValue = seleniumActions.getValue(currentZone);
        String chaineAEnter;
        // Clean the value of zone.
        seleniumActions.type(currentZone, StringUtils.EMPTY);
        // Focus the zone.
        seleniumActions.focus(currentZone);
        if (StringUtils.isEmpty(inputValue)) {
            // Manager the character string.
            chaineAEnter = "a";
            for (int i = 0; i < zoneSize - 1; i++) {
                chaineAEnter = chaineAEnter + "A";
            }
        } else {
            chaineAEnter = inputValue;
        }
        if (isUpperCase) {
            // Upper case the character string.
            chaineAEnter = chaineAEnter.toUpperCase();
        }

        // FIXME The method has some problem need to be done.
        //enterCharacterString(chaineAEnter);

        // Enter the character string into zone.
        for (int i = 0; i < chaineAEnter.length(); i++) {
            char c = chaineAEnter.charAt(i);
            String keyName = String.valueOf(c).toUpperCase();
            if (c >= TSFConstant.ASCII_L_A) {
                keyName = "L_" + keyName;
            }
            seleniumActions.keyPressNative(TSFKeys.valueOf(keyName));
        }

        seleniumActions.keyPressNative(TSFKeys.TAB);
        seleniumActions.shiftKeyDown();
        seleniumActions.keyPressNative(TSFKeys.TAB);
        seleniumActions.shiftKeyUp();
        if (!seleniumActions.isElementPresent(currentZone)) {
            utilsInstance.ajouterErreur("The screen does not stay at the current screen.");
        } else {
            // Verify that the character string has correctly length or upper case.
            utilsInstance.verifyEquals(chaineAEnter, seleniumActions.getValue(currentZone));
            seleniumActions.type(currentZone, initialValue);
        }
        Thread.sleep(TSFConstant.THREAD_SLEEP);
    }

    /**
     * screenConformityForECW
     * 
     * @param name screen shot name
     * @throws SeleniumException error
     * @throws InterruptedException error
     * @throws IOException error
     */
    public static void screenConformityForECW(String name) throws SeleniumException, InterruptedException, IOException {
        screenConformityForECW(name, null);
    }

    /**
     * screenConformityForECW
     * 
     * @param name screen shot name
     * @param zones not validate zones
     * @throws SeleniumException error
     * @throws InterruptedException error
     * @throws IOException error
     */
    public static void screenConformityForECW(String name, Map<String, String> zones) throws SeleniumException, InterruptedException, IOException {
        ECWBitmapAnalyzer.setModifyHtml(new Xpath("<html>\n" + seleniumActions.getHtmlSource() + "\n</html>"));
        try {
            ECWBitmapAnalyzer.capturePage(utilsInstance.getHtmlPath() + ReportConstants.BACK_SLASH + name, zones);
        } catch (NoWayFindXPathException e) {
            utilsInstance.ajouterErreur(e.getMessage() + "\n\n" + e.getStackTrace(), e);
        } catch (XPathInvalidException e) {
            utilsInstance.ajouterErreur("XPath invalide \n" + e.getMessage() + " " + e.getStackTrace(), e);
        }
        String url = ReportConstants.FILE_SLASH + TSFConstant.CURRENT_PATH + ReportConstants.BACK_SLASH + utilsInstance.getHtmlPath() + ReportConstants.BACK_SLASH + name + ".html";
        if (utilsInstance.isWebDriver()) {
            TSFSeleniumUtils.selectSecondPage();
            seleniumActions.open(url);
            TSFSeleniumUtils.screenConformity(name);
            TSFSeleniumUtils.returnFirstPage();
        } else {
            seleniumActions.open(url);
            TSFSeleniumUtils.screenConformity(name);
        }
        Thread.sleep(TSFConstant.THREAD_SLEEP);
    }

    /**
     * screenConformityWithTableDataForECW
     * 
     * @param name screen shot name
     * @throws SeleniumException error
     * @throws InterruptedException error
     * @throws IOException error
     */
    public static void screenConformityWithTableDataForECW(String name) throws SeleniumException, InterruptedException, IOException {
        screenConformityWithTableDataForECW(name, null);
    }

    /**
     * screenConformityWithTableDataForECW
     * 
     * @param name screen shot name
     * @param zones not validate zones
     * @throws SeleniumException error
     * @throws InterruptedException error
     * @throws IOException error
     */
    public static void screenConformityWithTableDataForECW(String name, Map<String, String> zones) throws SeleniumException, InterruptedException,
            IOException {
        ECWBitmapAnalyzer.setModifyHtml(new Xpath("<html>\n" + seleniumActions.getHtmlSource() + "\n</html>"));
        try {
            ECWBitmapAnalyzer.capturePageWithTableData(utilsInstance.getHtmlPath() + ReportConstants.BACK_SLASH + name, zones);
        } catch (NoWayFindXPathException e) {
            utilsInstance.ajouterErreur(e.getMessage() + "\n\n" + e.getStackTrace(), e);
        } catch (XPathInvalidException e) {
            utilsInstance.ajouterErreur("XPath invalide \n" + e.getMessage() + " " + e.getStackTrace(), e);
        }
        seleniumActions.open(ReportConstants.FILE_SLASH + TSFConstant.CURRENT_PATH + ReportConstants.BACK_SLASH + utilsInstance.getHtmlPath() + ReportConstants.BACK_SLASH + name + ".html");
        TSFSeleniumUtils.screenConformity(name);
    }

    /**
     * checkECWModification
     * 
     * @param name screen shot name
     * @throws Exception error
     */
    public static void checkECWModification(String name) throws Exception {
        checkECWModification(name, null, new ArrayList<String>(), null, null);
    }

    /**
     * checkECWModification
     * 
     * @param name screen shot name
     * @param buttonXpath click button
     * @throws Exception error
     */
    public static void checkECWModification(String name, String buttonXpath) throws Exception {
        checkECWModification(name, buttonXpath, new ArrayList<String>(), null, null);
    }

    /**
     * checkECWModification
     * 
     * @param name screen shot name
     * @param buttonXpath click button
     * @param methodName reflect method name default : accesPage
     * @throws Exception error
     */
    public static void checkECWModification(String name, String buttonXpath, String methodName) throws Exception {
        checkECWModification(name, buttonXpath, new ArrayList<String>(), null, methodName);
    }

    /**
     * checkModification
     * 
     * @param name screen shot name
     * @param buttonXpath click button
     * @param zonesNotValider not validate zone
     * @param changeOrder zone need to be click in order to change the table order
     * @param methodName reflect method name default : accesPage
     * @throws Exception error
     */
    public static void checkECWModification(String name, String buttonXpath, List<String> zonesNotValider, String changeOrder, String methodName)
            throws Exception {
        String testPath = utilsInstance.createComparisonDir();
        String previousImageAddress = testPath + ReportConstants.SLASH + name + TSFConstant.PRE + TSFConstant.PNG;
        String followingImageAddress = testPath + ReportConstants.SLASH + name + TSFConstant.FOL + TSFConstant.PNG;
        // Capture the screen after enter the data
        seleniumActions.captureEntirePageScreenshot(previousImageAddress, "");
        // Click the button "Valider" and return to the origin page
        if (buttonXpath != null) {
            seleniumActions.click(buttonXpath);
        } else {
            seleniumActions.click("//button[@ecwvalue='validate']");
        }
        Thread.sleep(TSFConstant.THREAD_SLEEP);
        // If there is popup displayed, click the button "OUI" on the pop-up
        if (seleniumActions.isElementPresent("//div[@id='EcwMsgBox']")) {
            if (seleniumActions.isVisible("//div[@id='EcwMsgBox']")) {
                seleniumActions.click("//span[@class='ecwValidate']");
            }
        }
        // Return to the page of modification
        Method accesPasserelle;
        if (methodName != null) {
            accesPasserelle = utilsInstance.getInstanceClass().getDeclaredMethod(methodName);
        } else {
            accesPasserelle = utilsInstance.getInstanceClass().getDeclaredMethod("accesPage");
        }
        accesPasserelle.invoke(utilsInstance);

        // Change the order of the list
        if (changeOrder != null) {
            seleniumActions.click(changeOrder);
        }
        Thread.sleep(TSFConstant.THREAD_SLEEP); // To take time If the display is a pop-up
        // Capture the screen after modify
        seleniumActions.captureEntirePageScreenshot(followingImageAddress, "");

        if (zonesNotValider != null) {
            // get the position of the area not to check (if current time at page bottom and popup exist).
            if (seleniumActions.isElementPresent("//div[@id='ecwPopup0']")) {
                zonesNotValider.add("//div[@id='ecwPopup0']/form/table/tbody/tr[3]/td/table/tbody/tr/td[1]/span");
            }
            if (seleniumActions.isElementPresent("//td[@class='ecwInfoIhm']")) {
                zonesNotValider.add("//td[@class='ecwInfoIhm']");
            }
        }
        TSFSeleniumUtils.comparisonImagesWithPosition(name, testPath, previousImageAddress, followingImageAddress, zonesNotValider);
    }

    /**
     * checkECWScreenLink
     * 
     * @param name screen shot name
     * @param linkButton link button
     * @throws Exception error
     */
    public static void checkECWScreenLink(String name, String linkButton) throws Exception {
        checkECWScreenLink(name, linkButton, null, null, null, null);
    }

    /**
     * checkECWScreenLink
     * 
     * @param name screen shot name
     * @param linkButton link button
     * @param selectLine select line in table
     * @throws Exception error
     */
    public static void checkECWScreenLink(String name, String linkButton, String selectLine) throws Exception {
        checkECWScreenLink(name, linkButton, null, selectLine, null, null);
    }

    /**
     * checkECWScreenLink
     * 
     * @param name screen shot name
     * @param linkButton link button
     * @param checkMap zone need to check
     * @param zoneMap not validate zone
     * @throws Exception error
     */
    public static void checkECWScreenLink(String name, String linkButton, Map<String, String> checkMap, Map<String, String> zoneMap) throws Exception {
        checkECWScreenLink(name, linkButton, null, null, checkMap, zoneMap);
    }

    /**
     * checkECWScreenLink
     * 
     * @param name screen shot name
     * @param linkButton link button
     * @param menuButton menu button
     * @param selectLine select line in table
     * @param checkMap zone need to check
     * @param zoneMap not validate zone
     * @throws Exception error
     */
    public static void checkECWScreenLink(String name, String linkButton, String menuButton, String selectLine, Map<String, String> checkMap,
            Map<String, String> zoneMap) throws Exception {
        String testPath = utilsInstance.createComparisonDir();
        String previousImageAddress = testPath + ReportConstants.SLASH + name + TSFConstant.LINK + TSFConstant.PRE + TSFConstant.PNG;
        String followingImageAddress = testPath + ReportConstants.SLASH + name + TSFConstant.LINK + TSFConstant.FOL + TSFConstant.PNG;
        // Select the line in the table
        if (selectLine != null) {
            seleniumActions.click(selectLine);
            Thread.sleep(TSFConstant.THREAD_SLEEP_SHORT);
        }
        // Capture the current screen
        seleniumActions.captureEntirePageScreenshot(previousImageAddress, "");
        Thread.sleep(TSFConstant.THREAD_SLEEP);

        // call of the screen to be displayed by the key function
        if (menuButton != null) {
            seleniumActions.click(menuButton);
            // If not use selenium.mouseDown(...), we can't get the button element when the button in the ecwMenu.
            seleniumActions.mouseDown(linkButton);
        }
        seleniumActions.click(linkButton);
        Thread.sleep(TSFConstant.THREAD_SLEEP);

        screenConformityWithTableDataForECW(name, zoneMap);
        Thread.sleep(TSFConstant.THREAD_SLEEP);

        // Check the value in the screen
        if (MapUtils.isNotEmpty(checkMap)) {
            Iterator<Map.Entry<String, String>> iterator = checkMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                String area = entry.getKey();
                String value = entry.getValue();
                if (area.contains("//input")) {
                    // check the value exist in the textbox and combo
                    utilsInstance.verifyEquals(value, seleniumActions.getValue(area));
                } else {
                    // check the value exist in the table and label
                    utilsInstance.verifyEquals(value, seleniumActions.getText(area));
                }
            }
        }

        // Return to the origal ihm
        seleniumActions.click(TSFConstant.ARROW_RETURN);
        Thread.sleep(TSFConstant.THREAD_SLEEP);
        // Select the line in the table
        if (selectLine != null) {
            seleniumActions.click(selectLine);
            Thread.sleep(TSFConstant.THREAD_SLEEP_SHORT);
        }
        // Capture the screen after consult
        seleniumActions.captureEntirePageScreenshot(followingImageAddress, "");

        List<String> zonesNotValider = new ArrayList<String>();
        // get the position of the area not to check (if current time at page bottom and popup exist).
        if (seleniumActions.isElementPresent("//div[@id='ecwPopup0']")) {
            zonesNotValider.add("//div[@id='ecwPopup0']/form/table/tbody/tr[3]/td/table/tbody/tr/td[1]/span");
        }
        if (seleniumActions.isElementPresent("//td[@class='ecwInfoIhm']")) {
            zonesNotValider.add("//td[@class='ecwInfoIhm']");
        }
        TSFSeleniumUtils
                .comparisonImagesWithPosition(name + TSFConstant.LINK, testPath, previousImageAddress, followingImageAddress, zonesNotValider);
        // Clean the map
        clearMap(checkMap);
        clearMap(zoneMap);
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
     * Click and get the ecw current date
     * 
     * @param locator : String
     */
    public static void clickAndGetTime(String locator) {
        currentDate = seleniumActions.getText("//span[@class='ecwCurrentDate']");
        seleniumActions.click(locator);
    }

    /**
     * Wait for current page load
     * 
     * @throws InterruptedException : error
     */
    public static void waitCurrentPageLoad() throws InterruptedException {
        // Give time to the page to post itself
        Thread.sleep(TSFConstant.THREAD_SLEEP);
        Date debut = new Date();
        Date fin = new Date(debut.getTime() + Integer.parseInt(TSFConstant.WAIT_FOR_PAGE_TO_LOAD));
        String refreshDate = currentDate;
        while (true) {
            if (fin.before(new Date())) {
                utilsInstance.ajouterErreur("*** TIMEOUT ***\n\"" + "\" exceed " + TSFConstant.WAIT_FOR_PAGE_TO_LOAD + "ms");
                break;
            }
            if (seleniumActions.isElementPresent("//span[@class='ecwCurrentDate']", false)) {
                refreshDate = seleniumActions.getText("//span[@class='ecwCurrentDate']");
            }

            if (!StringUtils.equals(refreshDate, currentDate)) {
                break;
            }
            Thread.sleep(TSFConstant.THREAD_SLEEP_SHORT);
        }
    }

    /**
     * Test the contents of a combo box
     * 
     * @param comboZone : the current combo list
     * @param comboValues : the values in the combox to test
     */
    public static void checkComboValue(String comboZone, String... comboValues) {
        if (comboValues.length != 0) {
            for (int i = 0; i < comboValues.length; i++) {
                utilsInstance.verifyEquals(comboValues[i],
                        seleniumActions.getText("//div[@id='" + comboZone + "']//div[@class='ecwTableBody']/table/tbody/tr[" + (i + 1) + "]/td[1]"));
            }
        }
    }

    /**
     * Verify the button "Valider" without parameter
     * 
     * @throws Exception : error
     */
    public static void checkValider() throws Exception {
        checkValider("//button[@ecwvalue='validate']", null);
    }

    /**
     * Verify the button "Valider" with the button xpath
     * 
     * @param buttonXpath : Xpath of the button 'Valider'
     * @param message : the message which should be tested
     * @throws Exception : error
     */
    public static void checkValider(String buttonXpath, String message) throws Exception {
        // /Click the button "Valider"
        seleniumActions.click(buttonXpath);
        Thread.sleep(TSFConstant.THREAD_SLEEP);
        // /Verify presence of the pop-up
        if (seleniumActions.isElementPresent("//div[@id='EcwMsgBox']")) {
            if (seleniumActions.isVisible("//div[@id='EcwMsgBox']")) {
                // /Click the button "NON" on the pop-up
                seleniumActions.click("//button/span[@class='ecwCancel']");
                Thread.sleep(TSFConstant.THREAD_SLEEP);
                // /Verify suppress of the pop-up
                utilsInstance.verifyFalse(seleniumActions.isVisible("//div[@id='EcwMsgBox']"));
                // /Click the button "Valider" by the short cut
                if (seleniumActions.isElementPresent(buttonXpath + "/span")
                        && seleniumActions.getAttribute(buttonXpath + "/span", "class").contains("ecwAccesskey")) {
                    toDoKeyboardshortCut(seleniumActions.getText(buttonXpath + "/span"));
                    Thread.sleep(TSFConstant.THREAD_SLEEP);
                    // /Verify presence of the pop-up
                    utilsInstance.verifyTrue(seleniumActions.isVisible("//div[@id='EcwMsgBox']"));
                    // /Click the button "NON" on the pop-up by the short cut
                    toDoKeyboardshortCut("N");
                    Thread.sleep(TSFConstant.THREAD_SLEEP);
                    // /Verify suppress of the pop-up
                    utilsInstance.verifyFalse(seleniumActions.isVisible("//div[@id='EcwMsgBox']"));
                }
                // /Click the button "Valider"
                seleniumActions.click(buttonXpath);
                Thread.sleep(TSFConstant.THREAD_SLEEP);
                // /Verify presence of the pop-up
                utilsInstance.verifyTrue(seleniumActions.isVisible("//div[@id='EcwMsgBox']"));
                // /Click the button "OUI" on the pop-up
                seleniumActions.click("//button/span[@class='ecwValidate']");
                Thread.sleep(TSFConstant.THREAD_SLEEP);
            }
        }
        // /Verify the message
        if (!StringUtils.isEmpty(message)) {
            messageBarWithoutValues(message, TSFConstant.MESSAGE_TYPE_INFORMATION);
        } else {
            if (seleniumActions.isElementPresent("//div[@id='EcwMessageBar']")) {
                messageBar("Création effectuée", TSFConstant.MESSAGE_TYPE_INFORMATION);
            }
        }
    }

    /**
     * Verify radio button by click and by arrows
     * 
     * @param radioButtonGroup : radio buttons to test (key: radioId value :shortCut")
     * @throws Exception : error
     */
    public static void checkRadioGroupButton(Map<String, String> radioButtonGroup) throws Exception {
        if (MapUtils.isNotEmpty(radioButtonGroup)) {
            // list to put the id of radio button
            List<String> radioButtons = new ArrayList<String>();
            // list to put the shortCut of radio button
            List<String> shortCutList = new ArrayList<String>();
            int length = radioButtonGroup.size();
            for (Entry<String, String> entry : radioButtonGroup.entrySet()) {
                String area = entry.getKey();
                String value = entry.getValue();
                radioButtons.add(area);
                // shortCut exist
                if (StringUtils.isNotEmpty(value))
                    shortCutList.add(value);
            }
            // by click
            for (int i = 0; i < length; i++) {
                // Click the radio and check radio is active
                seleniumActions.click(radioButtons.get(i));
                seleniumActions.focus(radioButtons.get(i));
                utilsInstance.verifyTrue(seleniumActions.getAttribute(radioButtons.get(i), "class").contains("Actif"));
            }
            // by -->
            for (int i = length - 1; i > 0; i--) {
                // transition from one to another, check the current radio is active
                seleniumActions.keyPressNative(TSFKeys.LEFT);
                Thread.sleep(TSFConstant.THREAD_SLEEP);
                utilsInstance.verifyTrue(seleniumActions.getAttribute(radioButtons.get(i - 1), "class").contains("Actif"));
            }
            // by <--
            for (int i = 0; i < length - 1; i++) {
                // transition from one to another, check the current radio is active
                seleniumActions.keyPressNative(TSFKeys.RIGHT);
                Thread.sleep(TSFConstant.THREAD_SLEEP);
                utilsInstance.verifyTrue(seleniumActions.getAttribute(radioButtons.get(i + 1), "class").contains("Actif"));
            }
            // by shortCut
            for (int i = 0; i < shortCutList.size(); i++) {
                // Click the radio and check radio is active
                toDoKeyboardshortCut(shortCutList.get(i));
                utilsInstance.verifyTrue(seleniumActions.getAttribute(radioButtons.get(i), "class").contains("Actif"));
            }
            // Reset the radio to the initial state
            if (!radioButtons.isEmpty()) {
                seleniumActions.click(radioButtons.get(0));
                seleniumActions.focus(radioButtons.get(0));
                utilsInstance.verifyTrue(seleniumActions.getAttribute(radioButtons.get(0), "class").contains("Actif"));
            }
            // /Clean the map
            radioButtonGroup.clear();
        }
    }

}
