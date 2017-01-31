package com.inetpsa.tsf.selenium.report;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * IhmReport
 * @author e365712
 **/
public class IhmReport {
	
	/**
	 * name of ihm
	 * */
	private String ihm;
	
	/**
	 * the result of ihm
	 * */
	private boolean result;
	
	/**
	 * the status of database
	 * */
	private String status;
	
	/**
	 * [ihm : String, ex:testReport; assertions : List::String]
	 * */
	private Map<String, List<String>> mapAssertions = new LinkedHashMap<String, List<String>>();
	
	/**
	 * the result of testCase
	 * */
	private Map<String, String> mapCaseResult = new LinkedHashMap<String, String>();
	
	/**
	 * the info of testCase
	 * */
	private Map<String, String> mapCaseInfo = new HashMap<String, String>();
	
	/**
	 * the info of testCase
	 * */
	private Map<String, Long> mapCaseTime = new HashMap<String, Long>();
	
	/**
	 * IhmReport
	 * @param ihm String 
	 * */
	public IhmReport(String ihm){
		this.ihm = ihm;
	}

	/**
	 * getIhm
	 * @return ihm
	 * */
	public String getIhm() {
		return ihm;
	}
	
	/**
	 * isResult
	 * @return result
	 * */
	public boolean isResult() {
		return result;
	}

	/**
	 * Set result.
	 * @param result the result to set
	 */
	public void setResult(boolean result) {
		this.result = result;
	}
	
	/**
	 * getStatus
	 * @return status
	 * */
	public String getStatus() {
		return status;
	}

	/**
	 * Set status.
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * [ihm : String, ex:testReport; assertions : List::String]
	 * @return mapAssertions
	 * */
	public Map<String, List<String>> getMapAssertions() {
		return mapAssertions;
	}

	/**
	 * Set mapAssertions.
	 * @param mapAssertions the mapAssertions to set
	 */
	public void setMapAssertions(Map<String, List<String>> mapAssertions) {
		this.mapAssertions = mapAssertions;
	}

	/**
	 * getMapCaseResult
	 * @return mapCaseResult
	 * */
	public Map<String, String> getMapCaseResult() {
		return mapCaseResult;
	}

	/**
	 * Set mapCaseResult.
	 * @param mapCaseResult the mapCaseResult to set
	 */
	public void setMapCaseResult(Map<String, String> mapCaseResult) {
		this.mapCaseResult = mapCaseResult;
	}

	/**
	 * mapCaseInfo
	 * @return mapCaseInfo
	 * */
	public Map<String, String> getMapCaseInfo() {
		return mapCaseInfo;
	}

	/**
	 * Set mapCaseInfo.
	 * @param mapCaseInfo the mapCaseInfo to set
	 */
	public void setMapCaseInfo(Map<String, String> mapCaseInfo) {
		this.mapCaseInfo = mapCaseInfo;
	}

	/**
	 * mapCaseTime
	 * @return mapCaseTime
	 * */
	public Map<String, Long> getMapCaseTime() {
		return mapCaseTime;
	}

	/**
	 * Set mapCaseTime.
	 * @param mapCaseTime the mapCaseTime to set
	 */
	public void setMapCaseTime(Map<String, Long> mapCaseTime) {
		this.mapCaseTime = mapCaseTime;
	}
}
