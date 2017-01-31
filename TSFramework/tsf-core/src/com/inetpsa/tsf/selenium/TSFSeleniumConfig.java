package com.inetpsa.tsf.selenium;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TSFSeleniumConfig
 * @author e353062
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface TSFSeleniumConfig {

	/** Selenium browser */
	String browser() default TSFConstant.DEFAULT_BROWSER;
	
	/** Selenium url */
	String url() default TSFConstant.DEFAULT_URL;
	
	/** Selenium time out */
	String timeToWait() default TSFConstant.DEFAULT_TIME_TO_WAIT;
	
	/** Selenium time out */
	String timeOut() default TSFConstant.DEFAULT_TIME_OUT;
	
	/** Selenium server(true: use webdriver, false: use selenium1) */
	boolean isWebDriver() default false;
	
	/** the path of "IEDriverServer.exe", it's necessary to configure it when the process run in IE */
	String ieDriverPath();
	
}
