package com.inetpsa.tsf.selenium.screenshot.source;

import java.util.ArrayList;

/**
 * To manage different portions from the tests xpath
 *
 */
public class Portion{
	/** The differenet type of portion : <li><code>any</code> : all the descent</li><br /><li><code>direct</code> : sons</li> */
	static enum typePortion {any, direct};
	/** any or direct */
	private typePortion nature;
	/** Examples :<br /> "id='12354'"<br />"id exist "<br />"tag='fieldset'"*/
	private ArrayList<String> conditions;
	/**
	 * Built the portion with the conditions according to the portion xpath
	 * @param portion xpath
	 * @throws XPathInvalidException : exception
	 */
	public Portion(String portion) throws XPathInvalidException{
		conditions = new ArrayList<String>();
		int offset = 0;
		//To know if one must look at the direct sons or descent
		if(portion.startsWith("//")){
			nature=typePortion.any;
			offset = 2;
		}
		else if(portion.startsWith("/")){
			nature=typePortion.direct;
			offset = 1;
		}
		else{
			nature=typePortion.any;
			offset = 0;
		}
		
		//Conditions
		String portionCond = portion.substring(offset);
		if(portion.substring(offset).startsWith("id(")){
			conditions().add("id='"+portion.substring(portion.indexOf("(")+1, portion.indexOf(")"))+"'");
		}
		else if(portionCond.contains("[")){
			if(!portionCond.contains("]")){
				throw new XPathInvalidException("le xpath contient [ mais pas ] : "+portion);
			}
			conditions.add("tag='"+portionCond.substring(0, portionCond.indexOf("["))+"'");
			String portionConditions = portionCond.substring(portionCond.indexOf("[")+2, portionCond.indexOf("]"));
			//If there is one @
			if(portionCond.contains("@")){
				String[] localConditions = portionConditions.split("\\s*[ ][aA][nN][dD][ ]\\s*");
				for(String condition:localConditions){
					if(condition.contains("="))
						conditions.add(condition.replaceAll("@", "").replaceAll("'", "\""));
					else if(portionCond.contains("!@"))
						conditions.add(condition.replaceAll("@", "")+" existePas");
					else
						conditions.add(condition.replaceAll("@", "")+" existe");
				}
			}
			else{
				conditions.add(portionCond.substring(portionCond.indexOf("[")+1,portionCond.indexOf("]")));
			}
		}
		else{
			conditions.add("tag='"+portionCond+"'");
		}
	}

	/**
	 * Nature
	 * @return the nature
	 */
	public typePortion nature(){
		return nature;
	}

	/**
	 * Conditions
	 * @return the conditions
	 */
	public ArrayList<String> conditions() {
		return conditions;
	}
}
