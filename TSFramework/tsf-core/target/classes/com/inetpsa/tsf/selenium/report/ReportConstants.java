package com.inetpsa.tsf.selenium.report;

/**
 * TestConstants.
 * @author E365712
 */
public final class ReportConstants {
	
	/** UNICODE */
	public static final String UNICODE = "UTF-8";
	
	/** Percent */
	public static final int PER_CENT = 100;
	
	/** Initial copy byte array. */
	public static final int INITAL_COPY_BYTE_ARRAY = 8 * 1024;
	
	/** REPORT directory name. */
	public static final String REPORT_DIR_NAME = "testReport";
	
	/** White space. */
	public static final String WHITE_SPACE = " ";
	
	/** URL white space. */
	public static final String WHITE_SPACE_URL = "%20";
	
	/** BackSlash */
	public static final String BACK_SLASH = "\\";
	
	/** .LOG */
	public static final String DOT_LOG = ".log";
	
	/** Min number */
	public static final double MIN_NUM = 0.01;
	
	/** Max number */
	public static final double MAX_NUM = 99.99;
	
	/** TEN_TIME */
	public static final int TEN_TIME = 10;
	
	/** Slash */
	public static final String SLASH = "/";
	
	/** Log folder */
	public static final String LOG_FOLDER = "log";
	
	/** Wrap */
	public static final String WRAP = "\n";
	
	/** Wrap */
	public static final String WRAP_LINE = "<br>";
	
	/** File slash. */
	public static final String FILE_SLASH = "file:///";
	
	/** SELENIUM. */
	public static final String SELENIUM = "selenium";
	
	/** XML. */
	public static final String XML = ".xml";
	
	/** HTML. */
	public static final String HTML = ".html";
	
	/** INDEX. */
	public static final String INDEX = "index";
	
	/** Help icon. */
	public static final String ICON_HELP = "help.png";

	/** Error icon. */
	public static final String ICON_ERROR = "error.png";

	/** Log icon. */
	public static final String ICON_LOG_BOOK = "logbook.png";

	/** Log INFO icon. */
	public static final String ICON_LOG_INFO = "loginfo.png";

	/** Log WARN icon. */
	public static final String ICON_LOG_WARN = "logwarn.png";

	/** Log ERROR icon. */
	public static final String ICON_LOG_ERROR = "logerror.png";
	
	/** Log FAILURE icon. */
	public static final String ICON_FAILURE = "failure.png";

	/** Group icon. */
	public static final String ICON_GROUP = "group.png";

	/** OK icon. */
	public static final String ICON_ASSERT_SUCCESS = "success.png";
	
	/** Screen shot icon. */
	public static final String ICON_SCREEN_SHOT = "screenshot.png";

	/** KO icon. */
	public static final String ICON_ASSERT_FAILURE = "failure.png";
	
	/** Back icon. */
	public static final String ICON_BACK = "back.png";

	/** TIME icon. */
	public static final String ICON_TIME = "time.png";

	/** OK icon. */
	public static final String ICON_TEST_OK = "ok.png";

	/** KO icon. */
	public static final String ICON_TEST_KO = "ko.png";

	/** OPEN icon. */
	public static final String ICON_OPEN = "open.png";
	
	/** Scenario icon. */
	public static final String ICON_NB_TESTCASES = "script.png";

	/** ProjectTester CSS file name. */
	public static final String PROJECT_TESTER_CSS_NAME = "projectTester.css";
	
	/** ProjectTester JS file name. */
	public static final String PROJECT_TESTER_JS_NAME = "projectTester.js";

	/** Double quote. */
	public static final String DOUBLE_QUOTE = "";
	
	/** SPACE. */
	public static final String SPACE = "&nbsp;";
	
	/** Report resources directory. */
	public static final String REPORT_RESOURCES_DIR = "resources";
	
	/** Report scenarios directory. */
	public static final String REPORT_SCENARIOS_DIR = "scenarios";
	
	/** INDEX_TEMPLATE_VM. */
	public static final String INDEX_TEMPLATE_VM = "index_template.vm";
	
	/** TESTCASE_TEMPLATE_VM. */
	public static final String TESTCASE_TEMPLATE_VM = "testCase_template.vm";
	
	/** LOG_TEMPLATE_VM. */
	public static final String LOG_TEMPLATE_VM = "log_template.vm";
	
	/** The flag that represents the beginning of warning message */
	public static final String BEGIN_ERROR = "ERROR: ";
	
	/** The flag that represents the ending of warning message */
	public static final String END_ERROR = "END_ERROR";
	
	/** The flag that represents the beginning of info message */
	public static final String BEGIN_INFO = "INFO ";
	
	/** The flag that represents the info message */
	public static final String MESSAGE_INFO = "INFO: ";
	
	/** The flag that represents the ending of info message */
	public static final String END_INFO = "END_INFO";
	
	/** ProjectTester DTD file path. */
	public static final String PROJECT_TESTER_RESOURCES_PATH = "com/inetpsa/tsf/selenium/report/resources/";

	/** Copy resources. */
	public static final String[] COPY_REPORT_RESOURCES = {ICON_OPEN, ICON_TEST_KO, ICON_TEST_OK, 
		ICON_GROUP, ICON_LOG_ERROR, ICON_LOG_INFO, ICON_LOG_BOOK, ICON_ASSERT_SUCCESS, ICON_TIME, 
		ICON_NB_TESTCASES, PROJECT_TESTER_CSS_NAME, PROJECT_TESTER_JS_NAME, 
		INDEX_TEMPLATE_VM, LOG_TEMPLATE_VM, TESTCASE_TEMPLATE_VM, ICON_SCREEN_SHOT, ICON_BACK, ICON_FAILURE};

	/** Flag of log 'com.' which need to be displayed */
	public static final String LOG_INFORMATION = "com.inetpsa.tsf";
	
	/** Flag of log 'teardown' which need not to be displayed */
	public static final String LOG_TEARDOWN = "tearDown";

	/** Flag of assertion which need to be displayed */
	public static final String ASSERTION_INFORMATION = "ASSERTIONS In TEST: ";
	
	/** Flag of sub case which need to be displayed */
	public static final String SUB_CASE = "Sub case: ";
	
	/** Flag of screen shot which need to to be displayed */
	public static final String SCREEN_SHOT = "SCREEN SHOT: ";
	
	/** Flag of screen path which need to to be displayed */
	public static final String SCREEN_PATH = "SCREEN PATH: ";
	
	/** Flag of comparison result which need to to be displayed */
	public static final String COMPARISON_RESULT = "COMPARISON RESULT: ";
	
	/** Flag of successful comparison result which need to to be displayed */
	public static final String COMPARISON_SUCCESS = "CONTRAST THE IMAGE CORRECTLY";
	
	/** Flag of failure comparison result which need to to be displayed */
	public static final String COMPARISON_FAILURE = "IMAGE COMPARISON FAILS";
	
	/** Flag of reference comparison result which need to to be displayed */
	public static final String COMPARISON_REFERENCE = "REFERENCE FILE DOES NOT EXIST";
	
	/** Flag of screen shot name which need to to be displayed */
	public static final String SCREEN_SHOT_TEST = "Test";
	
	/** Flag of screen shot name which need to to be displayed */
	public static final String SCREEN_SHOT_TESTS = "Tests";
	
	/** Flag of warning which need to be displayed */
	public static final String WARNING = "WARNING: ";
	
	/** Flag of database error which need to be displayed */
	public static final String DATABASE_ERROR = "Database error: ";
	
	/** Flag of case start which need to be displayed */
	public static final String CASE_START = " STARTING...";
	
	/** Flag of case end error which need to be displayed */
	public static final String CASE_END = " ENDING...";
	
	/** Flag of test case error which need to be displayed */
	public static final String TEST_CASE = "TEST CASE :";
	
	/** Flag of test class error which need to be displayed */
	public static final String TEST_CALSS = "TEST CLASS : ";
	
	/** Test result which is success */
	public static final String SUCCESS = "SUCCESS";
	
	/** Test result which is error */
	public static final String ERROR = "ERROR";
	
	/** Flag of verifyFalse which need to be displayed */
	public static final String VERIFY_FALSE = "verifyFalse ";
	
	/** Flag of verifyTrue which need to be displayed */
	public static final String VERIFY_TRUE = "verifyTrue ";
	
	/** Flag of verifyEqual which need to be displayed */
	public static final String VERIFY_EQUAL = "verifyEqual ";
	
	/** Flag of verifyNotEqual which need to be displayed */
	public static final String VERIFY_NOT_EQUAL = "verifyNotEqual ";
	
	/** Flag of Start test ihm which need to be displayed */
	public static final String START_TEST_IHM = "Start test ihm: ";
	
	/** SECOND */
	public static final int SECOND = 1000;
	
	/** MINUTE */
	public static final int MINUTE = 60;
	
	/** HOUR */
	public static final int HOUR = 60;
	
	/**
	 * Constructor.
	 */
	private ReportConstants() {
	}

	/**
	 * Get image path.
	 * 
	 * @param name Name.
	 * @return Image path;
	 */
	public static String getImagePath(final String name) {
		return REPORT_RESOURCES_DIR + SLASH + name;
	}
}
