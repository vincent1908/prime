package com.inetpsa.tsf.selenium;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * The Class Dates.
 */
public class ExtendDates {
 
	/** The locale. Choix de la langue francaise */
	static Locale locale = Locale.getDefault();
	
	/** The actuelle. */
	static Date actuelle = new Date();
	
	/**
	 * Date.
	 * Sort la date au format donné
	 * @param format the format
	 * @return the string
	 */
	public static String date(String format){
		return new SimpleDateFormat(format).format(actuelle);
	}
	
 
	/**
	 * Convert.
	 * Convertit la date issue de la bdd au format jj/mm/yyyy
	 * @param date the date
	 * @return the string
	 */
	public static String convert(String date){
		return date.substring(8,10)+"/"+date.substring(5,7)+"/"+date.substring(0,4);
	}
}