package com.inetpsa.tsf.selenium.report;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * TestResultReport
 * @author e365712
 **/
public final class TSFReport {
	
	/** LOGGER */
	private static final Logger LOG = Logger.getLogger("TSF");
	
	/** Instance */
	private static TSFReport instance;
	
	/** Save the path of the logs */
	private ArrayList<String> fileList = new ArrayList<String>(); 
	
	/** Save the assertions of each ihm */
	private Map<String, IhmReport> assertionsList = new LinkedHashMap<String, IhmReport>(); 
	
	/** Save the log of each ihm */
	private Map<String, IhmReport> logList = new LinkedHashMap<String, IhmReport>(); 
	
	/** Save the case time */
	private Map<String, IhmReport> mapIhmsCaseDurationTime; 
	
	/** ihm test start time*/
	private long ihmTestStartTime;
	
	/** ihm case start time*/
	private long ihmCaseStartTime;
	
	/** ihm test start date*/
	private String ihmTestDate;
	
	/** ihm test data base*/
	private String ihmDataBase;
	
	/** ihm project name*/
	private String projectName;
	
	/** activate report */
	private boolean activateReport;
	
	/**
	 * key: name of ihm test<br/>
	 * value: duration time of each ihm test
	 */
	private Map<String, Long> mapIhmsTestDurationTime;
	
	/**
	 * key: name of ihm test<br/>
	 * value: scenario path of each ihm test
	 */
	private Map<String, String> mapIhmsTestScenario;
	
	/**
	 * key: name of ihm test<br/>
	 * value: test result of each ihm test
	 */
	private Map<String, List<String>> mapIhmsTestResult;
	
	/**
	 * key: name of ihm test<br/>
	 * value: database status of each ihm test
	 */
	private Map<String, List<String>> mapIhmsDataBase;
	
	/**
	 * key: name of ihm test<br/>
	 * value: test case result of each ihm test
	 */
	private Map<String, List<String>> mapIhmsTestCaseResult;
	
	/**
	 * key: name of ihm test<br/>
	 * value: test case success rate of each ihm test
	 */
	private Map<String, List<String>> mapIhmsTestSuccess;
	
	/**
	 * key: name of ihm test<br/>
	 * value: test case failure rate of each ihm test
	 */
	private Map<String, List<String>> mapIhmsTestFailure;
	
	/**
	 * isActivateReport.
	 * @return activateReport
	 */
	public boolean isActivateReport() {
		return activateReport;
	}

	/**
	 * Set activateReport.
	 * @param activateReport the activateReport to set
	 */
	public void setActivateReport(boolean activateReport) {
		this.activateReport = activateReport;
	}

	/**
	 * Get projectName.
	 * @return projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * Set projectName.
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	/**
	 * Get assertionslist.
	 * @return assertionslist
	 */
	public Map<String, IhmReport> getAssertionslist() {
		return assertionsList;
	}
	
	/**
	 * Get logList.
	 * @return logList
	 */
	public Map<String, IhmReport> getLogList() {
		return logList;
	}

	/**
	 * TestResultReport
	 */
	private TSFReport() {
		//initialize the map
		mapIhmsTestDurationTime = new HashMap<String, Long>();
		mapIhmsCaseDurationTime = new LinkedHashMap<String, IhmReport>(); 
		mapIhmsTestResult = new LinkedHashMap<String, List<String>>();
		mapIhmsDataBase = new TreeMap<String, List<String>>();
		mapIhmsTestCaseResult = new LinkedHashMap<String, List<String>>();
		mapIhmsTestSuccess = new HashMap<String, List<String>>();
		mapIhmsTestFailure = new HashMap<String, List<String>>();
		mapIhmsTestScenario = new HashMap<String, String>();
		//get project name
		projectName = System.getProperty("user.dir");
		projectName = projectName.substring(projectName.lastIndexOf(ReportConstants.BACK_SLASH) + 1);
	}
	
	/** 
	 * GetInstance 
	 * @return instance
	 * */
	public static synchronized TSFReport getInstance() {
    	if (instance == null) {
    		instance = new TSFReport();
    	}
       return instance;
    }
	
	/**
	 * Start record
	 * @param ihmTestName test name
	 */
	public void startRecord(String ihmTestName) {
		//the first time initialize the list
		if (assertionsList.get(ihmTestName) == null) {
			assertionsList.put(ihmTestName, new IhmReport(ihmTestName));
		}
		if (logList.get(ihmTestName) == null) {
			logList.put(ihmTestName, new IhmReport(ihmTestName));
		}
		//ihm start
		LOG.info(ReportConstants.START_TEST_IHM + ihmTestName);
		ihmTestStartTime = System.currentTimeMillis();
		if (StringUtils.isEmpty(ihmTestDate)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(ihmTestStartTime);
			ihmTestDate = formatter.format(date);
		}
		//initialize the map with ihmTestName
		if (CollectionUtils.isEmpty(mapIhmsTestResult.get(ihmTestName))) {
			mapIhmsTestResult.put(ihmTestName, new ArrayList<String>());
		}
		if (CollectionUtils.isEmpty(mapIhmsDataBase.get(ihmTestName))) {
			mapIhmsDataBase.put(ihmTestName, new ArrayList<String>());
		}
		if (CollectionUtils.isEmpty(mapIhmsTestSuccess.get(ihmTestName))) {
			mapIhmsTestSuccess.put(ihmTestName, new ArrayList<String>());
		}
		if (CollectionUtils.isEmpty(mapIhmsTestFailure.get(ihmTestName))) {
			mapIhmsTestFailure.put(ihmTestName, new ArrayList<String>());
		}
		if (CollectionUtils.isEmpty(mapIhmsTestCaseResult.get(ihmTestName))) {
			mapIhmsTestCaseResult.put(ihmTestName, new ArrayList<String>());
		}
	}
	
	/**
	 * Start case record
	 * @param ihmTestName test name
	 */
	public void startCaseRecord(String ihmTestName) {
		if (mapIhmsCaseDurationTime.get(ihmTestName) == null) {
			mapIhmsCaseDurationTime.put(ihmTestName, new IhmReport(ihmTestName));
		}
		//ihm start
		ihmCaseStartTime = System.currentTimeMillis();
	}
	
	/**
	 * Record case time 
	 * @param ihmTestName test name
	 * @param ihmCaseName test case name
	 */
	public void recordCaseTime(String ihmTestName, String ihmCaseName) {
		//record ihm test time
		long ihmCaseEndTime = System.currentTimeMillis();
		if (null == mapIhmsCaseDurationTime.get(ihmTestName).getMapCaseTime().get(ihmCaseName)) {
			mapIhmsCaseDurationTime.get(ihmTestName).getMapCaseTime().put(ihmCaseName, ihmCaseEndTime - ihmCaseStartTime);
		} else {
			long lastDurationTime = mapIhmsCaseDurationTime.get(ihmTestName).getMapCaseTime().get(ihmCaseName);
			mapIhmsCaseDurationTime.get(ihmTestName).getMapCaseTime().put(ihmTestName, lastDurationTime + (ihmCaseEndTime - ihmCaseStartTime));
		}
	}
	
	/**
	 * Record test time 
	 * @param ihmTestName test name
	 */
	public void recordTestTime(String ihmTestName) {
		//record ihm test time
		long ihmTestEndTime = System.currentTimeMillis();
		if (null == mapIhmsTestDurationTime.get(ihmTestName)) {
			mapIhmsTestDurationTime.put(ihmTestName, ihmTestEndTime - ihmTestStartTime);
		} else {
			long lastDurationTime = mapIhmsTestDurationTime.get(ihmTestName);
			mapIhmsTestDurationTime.put(ihmTestName, lastDurationTime + (ihmTestEndTime - ihmTestStartTime));
		}
	}
	
	/**
	 * Get absolute file.
	 * @param filePathName File path name.
	 * @return Absolute file.
	 */
	public File getAbsoluteFile(final String filePathName) {
		return new File(getAbsolutePathName(filePathName));
	}
	
	/**
	 * Get absolute file path name.
	 * @param filePathName File path name.
	 * @return Absolute file path name.
	 */
	public String getAbsolutePathName(final String filePathName) {
		return StringUtils.defaultString(getPortablePath(filePathName));
	}
	
	/**
	 * Get portable path.
	 * @param path  Path.
	 * @return Portable path.
	 */
	public String getPortablePath(final String path) {
		String newPath = path;
		if (newPath.indexOf(ReportConstants.WHITE_SPACE_URL) != -1) {
			newPath = newPath.replaceAll(ReportConstants.WHITE_SPACE_URL, ReportConstants.WHITE_SPACE);
		}
		if (newPath.indexOf(ReportConstants.WHITE_SPACE) != -1) {
			if (newPath.indexOf("Documents and Settings") != -1) {
				newPath = newPath.replaceFirst("Documents and Settings", "Docume~1");
			} else if (newPath.indexOf("Program Files") != -1) {
				newPath = newPath.replaceFirst("Program Files", "Progra~1");
			}
		}
		return StringUtils.replace(newPath, ReportConstants.BACK_SLASH, ReportConstants.SLASH);
	}
	
	/**
	 * Get absolute file path name.
	 * @param baseDir Base directory.
	 * @param filePathName  File path name.
	 * @return Absolute file path name.
	 */
	public String getAbsoluteFilePathName(final String baseDir, final String filePathName) {
		String newBaseDir = StringUtils.defaultString(baseDir);
		final File newBaseDirFile = getAbsoluteFile(newBaseDir);
		newBaseDir = getPortablePath(newBaseDirFile.getAbsolutePath());
		if (newBaseDir.endsWith(ReportConstants.SLASH)) {
			newBaseDir = newBaseDir.substring(0, newBaseDir.length() - 1);
		}
		String newFilePathName = StringUtils.defaultString(getPortablePath(filePathName));
		if (newFilePathName.startsWith(ReportConstants.SLASH)) {
			newFilePathName = newFilePathName.substring(1);
		}
		return newBaseDir + ReportConstants.SLASH + newFilePathName;
	}
	
	/**
	 * Create directory.
	 * @param dirFile Parameter.
	 */
	public void createDir(final File dirFile) {
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
	}
	
	 /**
     * Copy Scenarios.
     * @param baseDir base directory
	 * @throws IOException Configuration error.
     */
    public void copyReportScenarios(String baseDir) throws IOException {
    	if (StringUtils.isNotEmpty(System.getProperty("scenario.path"))) {
 			String scenariosPath = System.getProperty("user.dir") + System.getProperty("scenario.path");
 			File in = new File(scenariosPath);  
 	        if (in.exists()) {
 	        	 File[] file = in.listFiles();
 	        	 for (int i = 0; i < file.length; i++) {
 	 	        	if (StringUtils.equals(ReportConstants.REPORT_SCENARIOS_DIR, file[i].getName())) {
 	 	        		if (file[i].isDirectory()) {
 	 	 	                String sourceDir = scenariosPath + File.separator + file[i].getName();
 	 	 	                String targetDir = baseDir + File.separator + file[i].getName();
 	 	 	                copyDirectiory(sourceDir, targetDir);
 	 	 	            }
 	 	        	}
 	 	        }
 	        }
    	}    	
    }
    
	 /**
     * Copy file.
     * @param sourceFile source file
     * @param targetFile target file
	 * @throws IOException Configuration error.
     */
    public void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
            byte[] b = new byte[ReportConstants.INITAL_COPY_BYTE_ARRAY];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            outBuff.flush();
        } finally {
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
    
    /**
     * Copy directiory.
     * @param sourceDir source directiory
     * @param targetDir target directiory
	 * @throws IOException Configuration error.
     */
    public void copyDirectiory(String sourceDir, String targetDir) throws IOException {
        (new File(targetDir)).mkdirs();
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                File sourceFile = file[i];
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                String dir1 = sourceDir + ReportConstants.SLASH + file[i].getName();
                String dir2 = targetDir + ReportConstants.SLASH + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }
	
	 /**
     * Copy images.
     * @param baseDir base directory
	 * @throws IOException Configuration error.
     */
    public void copyReportResources(String baseDir) throws IOException {
        final String[] copyResources = ReportConstants.COPY_REPORT_RESOURCES;
        for (int i = 0; i < copyResources.length; i++) {
            final String name = copyResources[i];
            final String targetDir = getAbsoluteFilePathName(getAbsoluteFilePathName(baseDir, ReportConstants.DOUBLE_QUOTE),
                    ReportConstants.REPORT_RESOURCES_DIR);
            createDir((getAbsoluteFile(targetDir)));
            copyResource(ReportConstants.PROJECT_TESTER_RESOURCES_PATH + name, targetDir, name);
        }
    }
    
    /**
     * Copy resource.
     * @param sourceName Source name.
     * @param targetDir  Target directory.
     * @param targetName  Target name.
     * @return Target file.
     * @throws IOException Copying resource error.
     */
    private File copyResource(final String sourceName, final String targetDir, final String targetName) throws IOException {
        final File target = new File(getAbsoluteFilePathName(targetDir, targetName));
        try {
            final InputStream src = getResourceAsInputStream(sourceName);
            copyResource(src, target);
        } catch (final IOException e) {
            throw new IOException("Copying resource error", e);
        } 
        return target;
    }
    
	/**
	 * Resource copy.
	 * @param inputStream Input stream.
	 * @param file Output File.
	 * @throws IOException  I/O error.
	 */
	public void copyResource(final InputStream inputStream, final File file) throws IOException {
		final FileOutputStream fos = new FileOutputStream(file);
		copyResource(inputStream, fos);
		fos.close();
	}
	
	/**
	 * Resource copy.
	 * @param inputStream  Input stream.
	 * @param outputStream  Output stream.
	 * @throws IOException  I/O error.
	 */
	public void copyResource(final InputStream inputStream, final OutputStream outputStream) throws IOException {
		final byte[] bytes = new byte[ReportConstants.INITAL_COPY_BYTE_ARRAY];
		int size;
		while ((size = inputStream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, size);
		}
	}
    
    /**
	 * Getter resource input stream.
	 * @param resourcePathName  Resource path name.
	 * @return InputStream.
     * @throws FileNotFoundException  Core error.
	 */
	public InputStream getResourceAsInputStream(final String resourcePathName) throws FileNotFoundException {
		final ClassLoader classLoader = getClassLoader();
		InputStream resourceStream = classLoader.getResourceAsStream(resourcePathName);
		if (resourceStream == null) {
			resourceStream = new FileInputStream(resourcePathName);
		}
		return resourceStream;
	}
	
	/**
	 * Getter field ClassLoader.
	 * @return Field ClassLoader.
	 */
	private ClassLoader getClassLoader() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = ClassLoader.getSystemClassLoader();
		}
		return classLoader;
	}
	
	/**
	 * Export html by testResult
	 * @param assertionsMap assertionsMap
	 * @param logMap logMap
	 * @param strDate test start date
	 * @param reportPath report path
	 * @throws FileNotFoundException exception
	 */
	public void exportResultByVM(Map<String, IhmReport> assertionsMap, Map<String, IhmReport> logMap, String strDate, String reportPath) throws FileNotFoundException {
		//generate report directory
		String resultDirectory = reportPath + ReportConstants.REPORT_DIR_NAME + ReportConstants.SLASH + strDate;
		try {
			Velocity.init();
			//generate each testCase
			int countOfFail = generateIhmTestHtml(resultDirectory, assertionsMap);
			LOG.info("Generate testCase succeeded 1/2");
			//generate index.html, contain one or more ihm test case
			generateIndexHtml(resultDirectory, countOfFail, assertionsMap);
			LOG.info("Generate index.html succeeded 2/3");
			//generate log, contain one or more ihm log
			generateLogHtml(resultDirectory, logMap);
			LOG.info("Generate log succeeded 3/3");
		} catch (FileNotFoundException e) {
			LOG.log(Level.WARNING, "FileNotFoundException " , e);
		}
	}
	
	/**
	 * Generate log.html
	 * 
	 * @param resultDirectory String
	 * @param logMap Map
	 * @throws FileNotFoundException Exception
	 */
	private void generateLogHtml(String resultDirectory, Map<String, IhmReport> logMap) throws FileNotFoundException {
		//use log_template.vm to generate the log html
		Template template = Velocity.getTemplate(resultDirectory + ReportConstants.SLASH + 
				ReportConstants.REPORT_RESOURCES_DIR + ReportConstants.SLASH + ReportConstants.LOG_TEMPLATE_VM, ReportConstants.UNICODE);
		Set<String> ihmNames = logMap.keySet();
		//loop the ihm
		for (String ihmName : ihmNames) {
			VelocityContext ctx = new VelocityContext();
			IhmReport ihmReport = logMap.get(ihmName);
			List<String> logForHtml = new ArrayList<String>();
			Map<String, List<String>> reports = ihmReport.getMapAssertions();
			if (reports != null && !reports.isEmpty()) {
				Set<String> caseNames = reports.keySet();
				StringBuilder sb = new StringBuilder();
				//loop the each test case in one ihm
				for (String caseName : caseNames) {
					//generate the log title
					if (CollectionUtils.isNotEmpty(reports.get(caseName))) {
						sb.append("<div class='row head " + caseName + "_DISPLAY'>").append(ReportConstants.WRAP);
						sb.append("<div class='cell_img'><a name='" + caseName + "' href='../" + ihmName + ".html?show=single&id=" + caseName + "_DISPLAY'>");
						sb.append("<img src='../resources/logbook.png' border='0'/>");
						sb.append("</a></div>").append(ReportConstants.WRAP);
						sb.append("<div class='cell' style='width:100%'>");
						sb.append("Log of the current test - " + caseName);
						sb.append("</div>").append(ReportConstants.WRAP);
						sb.append("<div class='cell_img'><a href='../index.html'>");
						sb.append("<img src='../resources/back.png' title='Back to the main page' border='0'/>");
						sb.append("</a></div></div>").append(ReportConstants.WRAP);
						//generate the log detail
						for (String line : reports.get(caseName)) {
							sb.append("<div class='row " + caseName + "_DISPLAY'>").append(ReportConstants.WRAP);
							//generate line contains log time
							if (line.contains(ReportConstants.LOG_INFORMATION) && !line.contains("java")) {
								sb.append("<div class='cell_img'></div>").append(ReportConstants.WRAP);
								sb.append("<div class='cell' style='width:100%'><pre>" + line + "</pre></div>").append(ReportConstants.WRAP);
								sb.append("<div class='cell'></div>").append(ReportConstants.WRAP);
							//generate line contains error log
							} else if (line.contains(ReportConstants.ERROR)) {
								sb.append("<div class='cell_img'><img src='../resources/failure.png' border='0'/></div>").append(ReportConstants.WRAP);
								sb.append("<div class='cell' style='width:100%'><pre>" + line + "</pre></div>").append(ReportConstants.WRAP);
								sb.append("<div class='cell'></div>").append(ReportConstants.WRAP);
							//generate line contains info log
							} else {
								sb.append("<div class='cell_img'><img src='../resources/loginfo.png' border='0'/></div>").append(ReportConstants.WRAP);
								sb.append("<div class='cell' style='width:100%'><pre>" + line + "</pre></div>").append(ReportConstants.WRAP);
								sb.append("<div class='cell'></div>").append(ReportConstants.WRAP);
							}
							sb.append("</div>").append(ReportConstants.WRAP);
						}
					}
				}
				logForHtml.add(sb.toString());
			}
			ctx.put("IhmsLogName", ihmName);
			ctx.put("logInfo", logForHtml);
			//generate the log file into log folder
			String filePath = resultDirectory + ReportConstants.SLASH + ReportConstants.LOG_FOLDER + ReportConstants.SLASH + ihmName + ReportConstants.HTML;
			PrintWriter dw = new PrintWriter(filePath);
			template.merge(ctx, dw);
			dw.close();
		}
	}

	/**
	 * Generate ihmTest.html
	 * 
	 * @param resultDirectory String
	 * @param assertionsMap Map
	 * @return countOfFail int
	 * @throws FileNotFoundException Exception
	 */
	//CHECKSTYLE:OFF (Plus de 50 lignes)
	private int generateIhmTestHtml(String resultDirectory, Map<String, IhmReport> assertionsMap) throws FileNotFoundException {
	//CHECKSTYLE:ON	
		int countOfFail = 0; 
		//loop the ihm
		for (String ihmName : assertionsMap.keySet()) {
			//count of error 
			Integer countError = 0;
			//count of test step 
			Integer countTest = 0;
			//Judge if the case has error
			boolean hasError = false;
			IhmReport ihmReport = assertionsMap.get(ihmName);
			List<String> resultForHtml = new ArrayList<String>();
			Map<String, List<String>> reports = ihmReport.getMapAssertions();
			if (reports != null && !reports.isEmpty()) {
				Set<String> caseNames = reports.keySet();
				//decide which line use different css style(odd or even)
				int cssControl = 0;
				StringBuilder sb = new StringBuilder();
				//loop the each test case in one ihm
				for (String caseName : caseNames) {
					if (CollectionUtils.isNotEmpty(reports.get(caseName))) {
						//generate the test case title
						sb.append(appendCaseTitle(caseName, ihmName));
						for (String line : reports.get(caseName)) {
							//generate the test case detail
							sb.append(appendLineCss(cssControl, caseName));
							//generate line contains "ERROR: " flag
							if (line.contains(ReportConstants.BEGIN_ERROR)) {
								sb.append(appendLineError(line));
								countError++;
								hasError = true;
							//generate line contains "INFO: INFO " flag
							} else if (line.contains(ReportConstants.BEGIN_INFO)) {
								sb.append(appendLineInfo(line));
							//generate line contains "INFO: ASSERTIONS In TEST:" flag	
							} else if (line.contains(ReportConstants.ASSERTION_INFORMATION)) {
								sb.append(appendLineAssertion(line));	
							//generate line contains "WARNING: COMPARISON RESULT: " flag	
							} else if (line.contains(ReportConstants.WARNING + ReportConstants.COMPARISON_RESULT)) {
								sb.append(appendLineComparison(line));
								countError++;
								hasError = true;
							//generate line contains "INFO: SCREEN SHOT: " flag	
							} else if (line.contains(ReportConstants.SCREEN_SHOT)) {
								sb.append(appendLineShot(line));
								countTest--;
							//generate line contains "INFO: Sub case: " flag(Now used in Corail project)
							} else if (line.contains(ReportConstants.SUB_CASE)) {
								sb.append(appendLineSub(line));
								countTest--;
							//generate blank line
							} else {
								sb.append(appendLineBlank(line));
							}
							sb.append("</div>");
							cssControl++;
							countTest++;
						}
						//If the test case has error, the title should be red, otherwise is green.
						sb.append(appendTitleCss(hasError, caseName)).append("</div>");
					}
					hasError = false;
				}
				resultForHtml.add(sb.toString());
			}
			countTestRate(ihmName, countTest, countError);
			mapIhmsTestResult.get(ihmName).add(Boolean.toString(ihmReport.isResult()));
			generateVelocityCase(resultDirectory, ihmName, resultForHtml);
			//count the number of error
			if (!ihmReport.isResult()) {
				countOfFail++;
			}
		}
		return countOfFail;
	}
	
	/**
	 * Append line css
	 * @param cssControl css control
	 * @param caseName case name
	 * @return line css
	 */
	private String appendLineCss(int cssControl, String caseName) {
		StringBuilder sb = new StringBuilder();
		if (cssControl % 2 == 0) {
			sb.append("<div class='row " + caseName + "_DISPLAY odd'>").append(ReportConstants.WRAP);
		} else {
			sb.append("<div class='row " + caseName + "_DISPLAY even'>").append(ReportConstants.WRAP);
		}
		return sb.toString();
	}
	
	/**
	 * Append line blank
	 * @param line line
	 * @return line blank
	 */
	private String appendLineBlank(String line) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell' style='width:100%'><pre>" + line + "</pre></div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'></div>").append(ReportConstants.WRAP);
		return sb.toString();
	}
	
	/**
	 * Append line sub case
	 * @param line line
	 * @return line sub case
	 */
	private String appendLineSub(String line) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='cell_img'><img src='resources/loginfo.png' border='0'/></div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell' style='width:100%'><pre><b>" 
				+ getDisplayLine(line, ReportConstants.SUB_CASE).replace(ReportConstants.SUB_CASE, ReportConstants.WHITE_SPACE) 
				+ "</b></pre></div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'></div>").append(ReportConstants.WRAP);
		return sb.toString();
	}
	
	/**
	 * Append line screen shot
	 * @param line line
	 * @return line screen shot
	 */
	private String appendLineShot(String line) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='cell_img'><a href= " + getDisplayLine(line, ReportConstants.SCREEN_SHOT).replace(ReportConstants.SCREEN_SHOT, "") 
				+ "><img src='resources/screenshot.png' border='0'/></a></div>").append(ReportConstants.WRAP);
		String screenshot = line.replace(getDisplayLine(line, ReportConstants.SCREEN_SHOT), "").replace(ReportConstants.MESSAGE_INFO, "");
		String name = screenshot.replace(ReportConstants.SCREEN_SHOT_TESTS, "").replace(ReportConstants.SCREEN_SHOT_TEST, "");
		sb.append("<div class='cell' style='width:100%'><pre> Screenshot "+ name +"</pre></div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'></div>").append(ReportConstants.WRAP);
		return sb.toString();
	}
	
	/**
	 * Append line comparison result
	 * @param line line
	 * @return line comparison result
	 */
	private String appendLineComparison(String line) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='cell_img'><img src='resources/failure.png' border='0'/></div>").append(ReportConstants.WRAP);
		String result = line.replace(getDisplayLine(line, ReportConstants.SCREEN_PATH), "").replace(ReportConstants.WARNING, "");
		sb.append("<div class='cell'><span>&nbsp;" + result + "</span><a href= " + getDisplayLine(line, ReportConstants.SCREEN_PATH).replace(ReportConstants.SCREEN_PATH, "") 
				+ "><img src='resources/screenshot.png' border='0'/></a></div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'></div>").append(ReportConstants.WRAP);
		return sb.toString();
	}
	
	/**
	 * Append line assertion
	 * @param line line
	 * @return line assertion
	 */
	private String appendLineAssertion(String line) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='cell_img'><img src='resources/success.png' border='0'/></div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell' style='width:100%'><pre>" 
				+ getDisplayLine(line, ReportConstants.ASSERTION_INFORMATION).replace(ReportConstants.ASSERTION_INFORMATION, ReportConstants.WHITE_SPACE) 
				+ "</pre></div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'></div>").append(ReportConstants.WRAP);
		return sb.toString();
	}
	
	/**
	 * Append line info
	 * @param line line
	 * @return line info
	 */
	private String appendLineInfo(String line) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='cell_img'><img src='resources/success.png' border='0'/></div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell' style='width:100%'><pre>" 
				+ getDisplayLine(line, ReportConstants.BEGIN_INFO).replace(ReportConstants.BEGIN_INFO, ReportConstants.WHITE_SPACE) 
				+ "</pre></div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'></div>").append(ReportConstants.WRAP);
		return sb.toString();
	}
	
	/**
	 * Append line error
	 * @param line line
	 * @return line error
	 */
	private String appendLineError(String line) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='cell_img'><img src='resources/failure.png' border='0'/></div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell' style='width:100%'><pre>" 
				+ ReportConstants.WHITE_SPACE
				+ getDisplayLine(line, ReportConstants.BEGIN_ERROR).replace(ReportConstants.BEGIN_ERROR, ReportConstants.DOUBLE_QUOTE)
				+ "</pre></div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'></div>").append(ReportConstants.WRAP);
		return sb.toString();
	}

	/**
	 * Append case title
	 * @param caseName case name 
	 * @param ihmName ihm name 
	 * @return case title
	 */
	private String appendCaseTitle(String caseName, String ihmName) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='table'>").append(ReportConstants.WRAP);
		sb.append("<div class='row " + caseName + "_DISPLAY " + caseName + "'>").append(ReportConstants.WRAP);
		sb.append("<div class='cell_img'><a href='log/" + ihmName + ".html#" + caseName + "'>").append(ReportConstants.WRAP);
		sb.append("<img src='resources/logbook.png' title='View Log Report' border='0'/>").append("</a></div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell' style='width:100%'>").append(ReportConstants.WRAP);
		sb.append("Detail of the current test - " + caseName).append("</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell_img'><a href='index.html'>").append(ReportConstants.WRAP);
		sb.append("<img src='resources/back.png' title='Back to the main page' border='0'/>").append("</a></div></div>").append(ReportConstants.WRAP);
		return sb.toString();
	}

	/**
	 * Append title css
	 * @param hasError if the case has error
	 * @param caseName case name
	 * @return titleCss
	 */
	private String appendTitleCss(boolean hasError, String caseName) {
		StringBuilder sb = new StringBuilder();
		if (hasError) {
			sb.append("<style>." + caseName + " {background-color:#EF252A;color:#FFFFFF;} ." + caseName 
					+ "_DISPLAY {display:none;}</style>").append(ReportConstants.WRAP);
			sb.append("<script type='text/javascript'>idFailure[idFailure.length] = '" + caseName 
					+ "_DISPLAY';</script>").append(ReportConstants.WRAP);	
		} else {
			sb.append("<style>." + caseName + " {background-color:#79C042;color:#FFFFFF;} ." + caseName 
					+ "_DISPLAY {display:none;}</style>").append(ReportConstants.WRAP);
			sb.append("<script type='text/javascript'>idSuccess[idSuccess.length] = '" + caseName 
					+ "_DISPLAY';</script>").append(ReportConstants.WRAP);
		}
		return sb.toString();
	}

	/**
     * Generate Velocity case html
     * @param resultDirectory String
     * @param ihmName String
     * @param resultForHtml List     
	 * @throws FileNotFoundException exception
     * */
	private void generateVelocityCase(String resultDirectory, String ihmName, List<String> resultForHtml) throws FileNotFoundException {
		//use testCase_template.vm to generate the test case html
		Template template = Velocity.getTemplate(resultDirectory + ReportConstants.SLASH + 
				ReportConstants.REPORT_RESOURCES_DIR + ReportConstants.SLASH + ReportConstants.TESTCASE_TEMPLATE_VM, ReportConstants.UNICODE);
		VelocityContext ctx = new VelocityContext();
		ctx.put("IhmsTestName", ihmName);
		ctx.put("resultInfo", resultForHtml);
		//generate the test case file
		String filePath = resultDirectory + ReportConstants.SLASH + ihmName + ReportConstants.HTML;
		PrintWriter dw = new PrintWriter(filePath);
		template.merge(ctx, dw);
		dw.close();
	}

	/**
	 * Count test rate
	 * @param ihmName String
	 * @param countTest Integer
	 * @param countError Integer
	 */
	private void countTestRate(String ihmName, Integer countTest, Integer countError) {
		Double failurePercentage = 0.0;
		//The count of success
		Integer countSuccess = 0;
		countSuccess = countTest - countError;
		//The count of test step
		Integer resultSize = countTest;
		//Reserve one decimal
		DecimalFormat df = new DecimalFormat("##.#");
		failurePercentage = getPercentage(countError, resultSize);
		Double successPercentage = ReportConstants.PER_CENT - failurePercentage;
		//display like 100.0%(30)
		mapIhmsTestSuccess.get(ihmName).add(df.format(successPercentage) + "%" + ReportConstants.SPACE + "(" + countSuccess + ")");
		mapIhmsTestFailure.get(ihmName).add(df.format(failurePercentage) + "%" + ReportConstants.SPACE + "(" + countError + ")");
	}

	/**
	 * generate index.html
	 * @param resultDirectory String
	 * @param countOfFail int
	 * @param assertionsMap map
	 * @throws FileNotFoundException Exception
	 */
	private void generateIndexHtml(String resultDirectory, int countOfFail, Map<String, IhmReport> assertionsMap) throws FileNotFoundException {
		List<String> scenarioList = null;
		String filePath = "";
		//get the scenario path from the user custom setting
		if (StringUtils.isNotEmpty(System.getProperty("scenario.path"))) {
			filePath = resultDirectory + ReportConstants.SLASH + ReportConstants.REPORT_SCENARIOS_DIR;
			File filelist = new File(filePath);
			if (filelist.exists()) {
				scenarioList = Arrays.asList(filelist.list());  
			}
		}
		Set<String> ihmNames = assertionsMap.keySet();
		//loop the each test case in one ihm
		for (String ihmName : ihmNames) {
			if (CollectionUtils.isNotEmpty(scenarioList)) {
				if (scenarioList.contains(ihmName + ReportConstants.XML)) {
					mapIhmsTestScenario.put(ihmName, ReportConstants.REPORT_SCENARIOS_DIR + ReportConstants.BACK_SLASH + ihmName);
				} else {
					mapIhmsTestScenario.put(ihmName, "");
				}
			} else {
				mapIhmsTestScenario.put(ihmName, "");
			}
			IhmReport ihmReport = assertionsMap.get(ihmName);
			//add the ihm database status into mapIhmsDataBase
			mapIhmsDataBase.get(ihmName).add(ihmReport.getStatus());
			Map<String, String> caseResults = ihmReport.getMapCaseResult();
			Map<String, String> caseInfo = ihmReport.getMapCaseInfo();
			//decide which line use different css style(odd or even)
			int cssControl = 0;
			StringBuilder sb = new StringBuilder();
			//if it has no database problem
			if (StringUtils.isEmpty(ihmReport.getStatus())) {
				//loop the each test case in one ihm
				for (String caseName : caseResults.keySet()) {
					if(cssControl % 2 == 0) {
						sb.append("<div class='row group " + ihmName + " odd'>").append(ReportConstants.WRAP);
					} else {
						sb.append("<div class='row group " + ihmName + " even'>").append(ReportConstants.WRAP);
					}
					sb.append(appendLineIndex(ihmName, caseName, caseResults.get(caseName)));
					sb.append("</div>").append(ReportConstants.WRAP);
					//generate line with special project info message
					if (StringUtils.isNotEmpty(caseInfo.get(caseName))) {
						sb.append(appendLineSpecial(ihmName, caseInfo.get(caseName)));
					}
					cssControl++;
				}
			}
			mapIhmsTestCaseResult.get(ihmName).add(sb.toString());
		}
		generateVelocityIndex(resultDirectory, countOfFail);
	}

	/**
	 * Append line index
	 * @param ihmName ihm name
	 * @param caseName case name
	 * @param caseResult caseResult
	 * @return line index
	 */
	private String appendLineIndex(String ihmName, String caseName, String caseResult) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='cell_img'>&nbsp;</div>").append(ReportConstants.WRAP);
		//generate line with ok or ko img
		if (StringUtils.equals(ReportConstants.SUCCESS, caseResult)) {
			sb.append("<div class='cell_img'><a href='" + ihmName + ".html?show=single&id=" + caseName 
					+ "_DISPLAY'><img src='resources/ok.png' border='0'/></img></a></div>").append(ReportConstants.WRAP);
		} else if (StringUtils.equals(ReportConstants.ERROR, caseResult)){
			sb.append("<div class='cell_img'><a href='" + ihmName + ".html?show=single&id=" + caseName 
					+ "_DISPLAY'><img src='resources/ko.png' border='0'/></img></a></div>").append(ReportConstants.WRAP);
		}
			sb.append("<div class='cell'>" + caseName + "</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell' style='width:100%'></div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'><img src='resources/time.png' border='0'/></div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>"+ converLongTimeToStr(mapIhmsCaseDurationTime.get(ihmName).getMapCaseTime().get(caseName)) +"</div>").append(ReportConstants.WRAP);
		return sb.toString();
	}

	/**
	 * Append line special
	 * @param ihmName ihmName
	 * @param caseName caseName
	 * @return line special
	 */
	private String appendLineSpecial(String ihmName, String caseName) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='row' class='" + ihmName + "'>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell' style='width:100%'>" 
				+ caseName.replace(ReportConstants.MESSAGE_INFO + ReportConstants.SUB_CASE, ReportConstants.WHITE_SPACE) 
				+ "</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("<div class='cell'>&nbsp;</div>").append(ReportConstants.WRAP);
		sb.append("</div>").append(ReportConstants.WRAP);
		return sb.toString();
	}

	/**
     * Generate Velocity Index html
     * @param resultDirectory String
     * @param countOfFail int
	 * @throws FileNotFoundException exception
     * */
	private void generateVelocityIndex(String resultDirectory, int countOfFail) throws FileNotFoundException {
		//use index_template.vm to generate the index.html
		Template template = Velocity.getTemplate(resultDirectory + ReportConstants.SLASH + 
				ReportConstants.REPORT_RESOURCES_DIR + ReportConstants.SLASH + ReportConstants.INDEX_TEMPLATE_VM, ReportConstants.UNICODE);
		VelocityContext ctx = new VelocityContext();
		ctx.put("ihmTestDate", ihmTestDate);
		ctx.put("ihmDataBase", ihmDataBase);
		ctx.put("indexTitle", projectName);
		ctx.put("testResult", mapIhmsTestResult);
		ctx.put("testDataBase", mapIhmsDataBase);
		ctx.put("testSuccess", mapIhmsTestSuccess);
		ctx.put("testFailure", mapIhmsTestFailure);
		ctx.put("testCaseResult", mapIhmsTestCaseResult);
		ctx.put("testScenario", mapIhmsTestScenario);
		Map<String, String> ihmsTestDurationTime = new HashMap<String, String>();
		for (String ihmName : mapIhmsTestDurationTime.keySet()) {
			long durationTime = mapIhmsTestDurationTime.get(ihmName);
			ihmsTestDurationTime.put(ihmName, converLongTimeToStr(durationTime));
		}
		ctx.put("durationTime", ihmsTestDurationTime);
		String totalSuccessPercent = getPercent(countOfFail, mapIhmsTestResult.size());
		ctx.put("successRate", totalSuccessPercent);
		//generate index.html
		PrintWriter dw = new PrintWriter(resultDirectory + ReportConstants.SLASH + ReportConstants.INDEX + ReportConstants.HTML);
		template.merge(ctx, dw);
		dw.close();
	}

	/**
     * converLongTimeToStr 01h01m30s
     * @param time long
     * @return time
     * */
    public String converLongTimeToStr(long time) {
        int ss = ReportConstants.SECOND;
        int mi = ss * ReportConstants.MINUTE;
        int hh = mi * ReportConstants.HOUR;
        long hour = (time) / hh;
        long minute = (time - hour * hh) / mi;
        long second = (time - hour * hh - minute * mi) / ss;
        String strHour = "";
        String strMinute = "";
        String strSecond = "";
        if (hour < ReportConstants.TEN_TIME) {
        	strHour = "0" + hour;
        } else {
        	strHour = "" + hour;
        }
        if (minute < ReportConstants.TEN_TIME) {
        	strMinute = "0" + minute;
        } else {
        	strMinute = "" + minute;
        }
        if (second < ReportConstants.TEN_TIME) {
        	strSecond = "0" + second;
        } else {
        	strSecond = "" + second;
        }
        if (hour > 0) {
            return strHour + "h" + strMinute + "m" + strSecond + "s";
        }
        return strMinute + "m" + strSecond + "s";
    }
	
	/**
	 * calculate percent of test case
	 * @param errors double
	 * @param total double
	 * @return result String
	 */
	private String getPercent(double errors, double total) {
		String result = null;
		if(errors == total) {
			result = "0%";
		} else {
			double percent = (total - errors)/total;
			NumberFormat nf = NumberFormat.getPercentInstance();
			nf.setMinimumFractionDigits(2);
			result = nf.format(percent);
		}
		return result;
	}
	
	/**
	 * deal with the logs by ihm
	 * @param ihm test ihm
	 * @throws IOException exception
	 */
	public void processAssertionsByIhm(String ihm) throws IOException{ 
		//process the assertionlist and loglist
		for (int j = 0; j < fileList.size(); j++){
    		File file = new File(fileList.get(j));
			processAssertion(file, ihm);
			processLog(file, ihm);
    	}
		recordTestTime(ihm);
	 }
	
	/**
	 * Process Log file
	 * @param file File
	 * @param ihm String	
	 * @throws IOException Exception
	 */
	//CHECKSTYLE:OFF (Plus de 50 lignes)
	private void processAssertion(File file, String ihm) throws IOException {
	//CHECKSTYLE:ON		
		String line = ""; 
		boolean beginClassFlag = false;
		boolean beginCaseFlag = false;
		boolean errorFlag = false;
		boolean infoFlag = false;
		boolean caseResultFlag = true;
		boolean ihmResultFlag = true;
		String dataBaseStatus = "";
		String caseInfo = "";
		String methodName = "";
		StringBuilder sb = null;
		FileReader fr = new FileReader(file);
		BufferedReader in = new BufferedReader(fr);
		while (in.ready()) {
			line = in.readLine();
			//The flag represents the beginning of ihm
			if (line.contains(ReportConstants.TEST_CALSS + ihm + ReportConstants.CASE_START)) {
				beginClassFlag = true;
			}
			if (beginClassFlag) {
				//The flag represents the error of database
				if (line.contains(ReportConstants.DATABASE_ERROR)) {
					dataBaseStatus = line.replace(ReportConstants.DATABASE_ERROR, "").trim();
				}					
				//The flag represents the beginning of case
				if (line.contains(ReportConstants.TEST_CASE) && line.contains(ReportConstants.CASE_START)) {
					beginCaseFlag = true;
					methodName = getDisplayLine(line, ReportConstants.TEST_CASE).replace(ReportConstants.TEST_CASE, "").replace(ReportConstants.CASE_START, "").trim();
				}
				//Case begin
				if (beginCaseFlag) {
					//The flag represents the assertion message
					if (line.contains(ReportConstants.ASSERTION_INFORMATION)) {
						errorFlag = false;
						initializeList(ihm, methodName);
						//save the info assertion
						saveAssertion(ihm, methodName, line);
					}
					//The flag represents the screen shot
					if (line.contains(ReportConstants.SCREEN_SHOT)) {
						initializeList(ihm, methodName);
						//save the info assertion
						saveAssertion(ihm, methodName, line);
					}
					//The flag represents the comparison result
					if (line.contains(ReportConstants.WARNING + ReportConstants.COMPARISON_RESULT)) {
						caseResultFlag = false;
						initializeList(ihm, methodName);
						//save the info assertion
						saveAssertion(ihm, methodName, line);
					}
					//The flag represents the special info message(now used in project Corail)
					if (line.contains(ReportConstants.SUB_CASE)) {
						sb = new StringBuilder();
						sb.append(line).append(ReportConstants.WRAP_LINE);
						caseInfo += sb.toString();
						initializeList(ihm, methodName);
						//save the info assertion
						saveAssertion(ihm, methodName, line);
					}
					//The flag represents the beginning of error message
					if (line.contains(ReportConstants.BEGIN_ERROR)) {
						errorFlag = true;
						caseResultFlag = false;
						sb = new StringBuilder();
						initializeList(ihm, methodName);
					}
					//The flag represents the end of error message
					if (line.contains(ReportConstants.END_ERROR)) {
						errorFlag = false;
						//save the warning assertion
						saveAssertion(ihm, methodName, sb.toString());
					}
					if (line.contains(ReportConstants.BEGIN_ERROR) || errorFlag) {
						sb.append(line);
						sb.append(ReportConstants.WRAP_LINE);
					}
					//The flag represents the beginning of info message
					if (line.contains(ReportConstants.BEGIN_INFO)) {
						infoFlag = true;
						sb = new StringBuilder();
						initializeList(ihm, methodName);
					}
					//The flag represents the end of info message
					if (line.contains(ReportConstants.END_INFO)) {
						infoFlag = false;
						//save the warning assertion
						saveAssertion(ihm, methodName, sb.toString());
					}
					if (line.contains(ReportConstants.BEGIN_INFO) || infoFlag) {
						sb.append(line).append(ReportConstants.WRAP_LINE);
					}
				}
				//The flag represents the end of case
				if(line.contains(ReportConstants.TEST_CASE) && line.contains(ReportConstants.CASE_END)){
					//case status(SUCCESS or ERROR)
					if (caseResultFlag) {
						assertionsList.get(ihm).getMapCaseResult().put(methodName, ReportConstants.SUCCESS);
					} else {
						assertionsList.get(ihm).getMapCaseResult().put(methodName, ReportConstants.ERROR);
					}
					if (!caseResultFlag) {
						ihmResultFlag = false;
					}
					//special info message
					if (StringUtils.isNotEmpty(caseInfo)) {
						assertionsList.get(ihm).getMapCaseInfo().put(methodName, caseInfo);
					}
					//reset the flag
					beginCaseFlag = false;
					caseInfo = "";
					methodName = "";
					caseResultFlag = true;
				}
			}
			//set the database status
			if (StringUtils.isNotEmpty(dataBaseStatus)) {
				assertionsList.get(ihm).setStatus(dataBaseStatus);
				ihmResultFlag = false;
			} else {
				assertionsList.get(ihm).setStatus("");
			}
			//The flag represents the end of ihm
			if (line.contains(ReportConstants.TEST_CALSS + ihm + ReportConstants.CASE_END)) {
				beginClassFlag = false;
				ihmResultFlag = true;
				//set the result of ihm
				assertionsList.get(ihm).setResult(ihmResultFlag);
				break;
			}
		}	
		in.close();
	}
	
	/**
	 * Save assertion
	 * @param ihm String
	 * @param methodName String	
	 * @param line String
	 */
	private void saveAssertion(String ihm, String methodName, String line) {
		assertionsList.get(ihm).getMapAssertions().get(methodName).add(line);
	}

	/**
	 * Initialize assertionsList 
	 * @param ihm String
	 * @param methodName String	
	 */
	private void initializeList(String ihm, String methodName) {
		if (assertionsList.get(ihm).getMapAssertions().get(methodName) == null){
			assertionsList.get(ihm).getMapAssertions().put(methodName, new ArrayList<String>());
		}
	}

	/**
	 * Process Log file
	 * @param file File
	 * @param ihm String	
	 * @throws IOException exception
	 */
	private void processLog(File file, String ihm) throws IOException {
		String line = ""; 
		boolean beginClassFlag = false;
		boolean beginCaseFlag = false;
		String methodName = "";
		StringBuilder sb = new StringBuilder();
		FileReader fr = new FileReader(file);
		BufferedReader in = new BufferedReader(fr);
		while (in.ready()) {
			line = in.readLine();
			if (StringUtils.isEmpty(line)) {
				continue;
			}
			//The flag represents the beginning of case
			if (line.contains(ReportConstants.TEST_CALSS + ihm + ReportConstants.CASE_START)) {
				beginClassFlag = true;
				continue;
			}
			if (beginClassFlag) {
				//The flag represents the beginning of case
				if (line.contains(ReportConstants.TEST_CASE) && line.contains(ReportConstants.CASE_START)) {
					beginCaseFlag = true;
					methodName = getDisplayLine(line, ReportConstants.TEST_CASE).replace(ReportConstants.TEST_CASE, "");
					methodName = methodName.replace(ReportConstants.CASE_START, "");
					methodName = methodName.trim();
					continue;
				}
				if (beginCaseFlag) {
					//The flag represents the beginning of the log
					if (line.contains(ReportConstants.LOG_INFORMATION) && !line.contains("java")) {
						if (StringUtils.isNotEmpty(sb.toString())) {
							if (logList.get(ihm).getMapAssertions().get(methodName) == null) {
								logList.get(ihm).getMapAssertions().put(methodName, new ArrayList<String>());
							}
							//save the log 
							logList.get(ihm).getMapAssertions().get(methodName).add(sb.toString());
							sb = new StringBuilder();
						} 
						if (!line.contains(ReportConstants.LOG_TEARDOWN)) {
							if (logList.get(ihm).getMapAssertions().get(methodName) == null) {
								logList.get(ihm).getMapAssertions().put(methodName, new ArrayList<String>());
							}
							//save the log 
							logList.get(ihm).getMapAssertions().get(methodName).add(line);
						} else {
							beginCaseFlag = false;
						}
						continue;
					}
                    sb.append(line);
                    sb.append(ReportConstants.WRAP_LINE);
				}
				//The flag represents the end of the case
				if (line.contains(ReportConstants.TEST_CASE) && line.contains(ReportConstants.CASE_END)) {
					beginCaseFlag = false;
					methodName = "";
				}
			}
			//The flag represents the end of the ihm
			if (line.contains(ReportConstants.TEST_CALSS + ihm + ReportConstants.CASE_END)) {
				beginClassFlag = false;
				break;
			}
		}
		in.close();
	}

	/**
	 * Search the logs in the occurrence
	 * @param path the report path
	 * @param strDate test start date
	 */
	public void searchFileList(String path, String strDate) { 
		//The string of date is the same as the name of the folder
		String strPath = path + ReportConstants.BACK_SLASH + strDate;
        File dir = new File(strPath); 
        File[] files = dir.listFiles(); 
        if (files == null){
        	LOG.warning("There are no files in the occurrence");
        	return; 
        }
        //addd the log file into filelist
        for (int i = 0; i < files.length; i++) { 
            if (!files[i].isDirectory()) { 
            	String strFileName = files[i].getAbsolutePath(); 
            	if(strFileName.indexOf(ReportConstants.DOT_LOG) != -1 && strFileName.indexOf(ReportConstants.SELENIUM) != -1){
            		fileList.add(files[i].getAbsolutePath()); 
            	}
            } 
        }
	 }
	
	/**
	 * Get percentage.
	 * @param value Value.
	 * @param total  Total.
	 * @return Percentage.
	 */
	private double getPercentage(int value, int total) {
		if (value == 0 || total == 0) {
			return 0;
		}
		final double v = new Integer(value).doubleValue();
		final double t = new Integer(total).doubleValue();
		double percentage = (v / t) * ReportConstants.PER_CENT;
		if (value > 0) {
			percentage = Math.max(ReportConstants.MIN_NUM, percentage);
		}
		if (value < total) {
			percentage = Math.min(ReportConstants.MAX_NUM, percentage);
		}
		return percentage;
	}
	
	/**
	 * Get display line
	 * @param line Value.
	 * @param beginWord Total.
	 * @return display line.
	 */
	private String getDisplayLine(String line, String beginWord) {
		return line.substring(line.indexOf(beginWord));
	}
}

