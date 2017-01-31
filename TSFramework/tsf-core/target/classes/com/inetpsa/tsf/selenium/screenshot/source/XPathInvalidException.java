package com.inetpsa.tsf.selenium.screenshot.source;

/**
 * Thrown when a xpath is invalid
 * @author e365712
 */
public class XPathInvalidException extends Exception {

	/** serialVersionUID */
	private static final long serialVersionUID = -8639096078264982567L;

	/**
	 * Exception 
	 * @param message : message
	 */
	public XPathInvalidException(String message){
		super("The xpath is invalid :" + message);
	}
}
