package com.inetpsa.tsf.selenium.screenshot.source;

/**
 * Thrown when no way can find the xpath
 * @author e365712
 */
public class NoWayFindXPathException extends Exception {

	/** serialVersionUID */
	private static final long serialVersionUID = -2440943998533495861L;

	/**
	 * Exception 
	 * @param xpath : xpath
	 */
	public NoWayFindXPathException(final String xpath) {
		super("No way can find the xpath :" + xpath);
	}
}
