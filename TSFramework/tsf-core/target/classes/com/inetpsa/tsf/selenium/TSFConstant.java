package com.inetpsa.tsf.selenium;

/**
 * TSFConstant
 * @author e365712
 */
public final class TSFConstant {

	/** The default selenium url */
	public static final String DEFAULT_URL = "http://localhost:8080";

	/** The default browser */
	public static final String DEFAULT_BROWSER = "*firefox";
	
	/** Return xpath */
	public static final String ARROW_RETURN = "//span[@class='ecwTitleButtonBackIhm']";
	
	/** The default browser path*/
	public static final String DEFAULT_BROWSER_PATH = "";
	
    /** The default context*/
    public static final String DEFAULT_CONTEXT = "";
    
    /** The default htmlPath*/
    public static final String DEFAULT_HTML_PATH = "web";
	
	/** The default html*/
	public static final String HTML_SOURCE = ".html";

	/** The default language*/
	public static final String DEFAULT_LANGUAGE = "FR";
	/** The error screen shot*/
	public static final String ERROR_SCREEN = "_ERROR";

	/** The default selenium type */
	public static final boolean DEFAULT_SELENIUM_TYPE = false;

	/** The default time to wait */
	public static final String DEFAULT_TIME_TO_WAIT = "500";

	/** The default time out */
	public static final String DEFAULT_TIME_OUT = "20000";

	/** The default server Selenium RC. */
	public static final Integer DEFAULT_PORT = 5555;
	
	/** Project path */
	public static final String CURRENT_PATH = System.getProperty("user.dir");
	
	/** The default destination of screenshot reference*/
	public static final String SCREENSHOT_REFERENCE = "screenshot\\reference\\";
	
	/** The default destination of screenshot test*/
	public static final String SCREENSHOT_TEST = "screenshot\\test\\";

	/** The default destination of screenshot */
	public static final String SCREENSHOT_PATH = "selenium\\screenshot\\";
	
	/** The default destination of comparison */
	public static final String COMPARISION_PATH = "comparison\\";
	
	/** BackSlash */
	public static final String BACK_SLASH = "\\";
	
	/** UnderLine */
	public static final String UNDER_LINE = "_";
	
	/** Screen shot */
	public static final String PNG = ".png";
	
	/** Previous screen shot */
	public static final String PRE = "_pre";
	
	/** Following Screen shot */
	public static final String FOL = "_fol";
	
	/** Screen shot Link*/
	public static final String LINK = "_LINK";
	
	/** FireFox screen shot */
	public static final String FF_PNG = "-ff.png";
	
	/** IE screen shot */
	public static final String IE_PNG = "-ie.png";
	
	/** The IE_browser. */
	public static final String IE_BROWSER = "*iexplore";

	/** The FF_browser. */
	public static final String FF_BROWSER = "*firefox";

	/** The FF_port. */
	public static final Integer FF_PORT = 5555;

	/** The IE_port. */
	public static final Integer IE_PORT = 5556;

	/** The informations about the server Selenium Grid */
	public static final String HUB_HOST = "localhost";

	/** The HUB_port. */
	public static final Integer HUB_PORT = 4444;

	/** The HUB_url. */
	public static final String HUB_URL = "http://" + HUB_HOST + ":" + HUB_PORT.toString();

	/** The number of IHM's name */
	public static final int TAILLE_NOM_IHM = 5;
	
	/** The constant of parametrized selenium methods */
	public static final String PROPERTIES_FILE_NAME = "selenium.properties";
	
	/** The path of properties file */
	public static final String PROPERTIES_FILE_PATH = "/data/" + PROPERTIES_FILE_NAME;
	
	/** Unused method in annotation {@link TSFSeleniumConfig} They will be used for filtering methods*/
	public static final String[] USELESS_ANNOTATION_METHODS = {"equals", "toString", "hashCode", "annotationType"};
	
	/** It's unnecessary to set value in these attributes in bath mode */
	public static final String[] UNUSED_ATTRUBYTE = {"isWebDriver", "browser"};
	
	/** Program pauses */
	public static final int THREAD_SLEEP = 3000;
	
	/** Program pauses */
    public static final int THREAD_LONG_SLEEP = 5000;
	
	/** Key pauses */
	public static final int KEY_SLEEP = 25;
	
	/** Xpath of toward the menu */
	public static final String LIEN_MENU = "//a[contains(text(),'Retour au Menu')]";
	
	/** Xpath of toward login */
	public static final String LIEN_LOGIN = "//a[contains(text(),'Déconnexion')]";
	
	/** Wait Selenium makes an attempt loading page */
	public static final String WAIT_FOR_PAGE_TO_LOAD = "30000";
	
	/** Program pauses */
	public static final int THREAD_SLEEP_SHORT = 1000;
	
	/** Type of information message */
	public static final String MESSAGE_TYPE_INFORMATION = "EcwMessageBarI";
	
	/** Type of warning message */
	public static final String MESSAGE_TYPE_WARNING = "EcwMessageBarW";
	
	/** Type of return */
	public static final String RETURN_TYPE_ESC = "ESC";
	
	/** Type of return */
	public static final String RETURN_TYPE_ARROW = "Arrow";
	
	/** Type of error message */
	public static final String MESSAGE_TYPE_ERREUR = "EcwMessageBarE";
	
	/** Empty state */
	public static final int STATE_BLANK 	 = 0x0001;
	
	/** Not empty state */
	public static final int STATE_NOT_BLANK	 = 0x0002;
	
	/** Active state */
	public static final int STATE_ACTIVE	 = 0x0004;
	
	/** Not active state */
	public static final int STATE_NOT_ACTIVE = 0x0008;
	
	/** Grayed state */
	public static final int STATE_GRAY		 = 0x0010;
	
	/** Not Grayed state */
	public static final int STATE_NOT_GRAY	 = 0x0020;
	
	/** Checked state */
	public static final int STATE_CHECKED 	 = 0x0040;
	
	/** Not checked state */
	public static final int STATE_NOT_CHECKED = 0x0080;
	
	/** Value 01 */
	public static final int VALEUR_01 = 01;	
	
	/** Value 02 */
	public static final int VALEUR_02 = 02;		
	
	/** Value 03 */
	public static final int VALEUR_03 = 03;	
	
	/** Value 04 */
	public static final int VALEUR_04 = 04;	
	
	/** Value 05 */
	public static final int VALEUR_05 = 05;
	
	/** Value 06 */
	public static final int VALEUR_06 = 06;
	
	/** Value 07 */
	public static final int VALEUR_07 = 07;
	
	/** Value 08 */
	public static final int VALEUR_08 = 8;
	
	/** Value 09 */
	public static final int VALEUR_09 = 9;
	
	/** Value 10 */
	public static final int VALEUR_10 = 10;

	/** The DEFAULT_IE_DREIVER_FILE_NAME. */
	public static final String DEFAULT_IE_DREIVER_FILE_NAME = "IEDriverServer.exe";
	
	/** Mouse move 10 */
	public static final int MOVE_10 = -10;
	
	/** Ascii lowercase a */
	public static final int ASCII_L_A = 97;
	
	/** Ascii lowercase z */
	public static final int ASCII_L_Z = 122;
	
	/** Ascii uppercase A */
	public static final int ASCII_A = 65;
	
	/** Ascii uppercase Z */
    public static final int ASCII_Z = 90;
	
	/**Ascii lowercase to uppercase (a->A)*/
	public static final int LOWERCASE_TO_UPPERCASE = 32;
	
	
}
