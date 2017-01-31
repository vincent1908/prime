package com.inetpsa.tsf.selenium.screenshot.source;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Some tools for the character strings
 */
public final class StringTools {

	/**
	 * StringTools
	 */
	private StringTools(){
	}
	/**
	 * Research of the position of the 1st caract of a chain contained in another chain.
	 * <em>The zones of text contained between simple or double quotation marks are excluded </em> 
	 * <em>Position beginning of research = parameter method</em>
	 * @param chaineOuChercher : the chaine of characters or one must seek
	 * @param chaineAChercher :  <b>regular expression</b> the chaine of characters or one must seek
	 * @param indexDepart : position of beginning of research in the chain to be sought
	 * @return position de la chaine à chercher, ou <code>-1</code> if the chaine did not find
	 */
	public static int indexOf(String chaineOuChercher,String chaineAChercher,int indexDepart){
		boolean dansGuillemetsDouble = false;
		String regex = chaineAChercher;
		if(indexDepart >= chaineOuChercher.length() || indexDepart < 0)
			throw new ArrayIndexOutOfBoundsException(indexDepart);
		if(chaineAChercher.startsWith("^"))
			regex = chaineAChercher.substring(1);
		Pattern pattern = Pattern.compile("^"+regex);
		for(int i=indexDepart;i<chaineOuChercher.length();i++){
			if(dansGuillemetsDouble){
				if(chaineOuChercher.charAt(i)=='"' && chaineOuChercher.charAt(i-1)!='\\')
					dansGuillemetsDouble= false;
			}
			else{
				if(chaineOuChercher.charAt(i) == '"')
					dansGuillemetsDouble = true;
				else if(pattern.matcher(chaineOuChercher.substring(i)).find())
					return i;
			}
		}
		return -1;
	}

	/**
	 * Research of the position of the 1st caract of a chain contained in another chain.
	 * <em>The zones of text contained between simple or double quotation marks are excluded </em> 
	 * <em>Position beginning of research = beginning of chain</em> 
	 * @param chaineOuChercher : the chaine of characters or one must seek
	 * @param chaineAChercher :  <b>regular expression</b> the chaine of characters or one must seek
	 * @return position of the chaine to be sought, or <code>-1</code> if the chaine did not find
	 */
	public static int indexOf(String chaineOuChercher,String chaineAChercher){
		return indexOf(chaineOuChercher,chaineAChercher,0);
	}


	/**
	 * Cutting of a character string
	 * <em>The zones of text contained between simple or double quotation marks are excluded </em> 
	 * @param chaineASpliter : the chaine of characters which one must cut out
	 * @param chaineLimite : <b>regular expression</b> who is used as limit
	 * @return table containing different the chaines 
	 */
	public static String[] split(String chaineASpliter,String chaineLimite){
		ArrayList<String> listeChaines = new ArrayList<String>();

		int prochainePosition = indexOf(chaineASpliter,chaineLimite);
		int anciennePosition = -1;
		while(prochainePosition != -1){
			listeChaines.add(chaineASpliter.substring(anciennePosition+1,prochainePosition));
			anciennePosition = prochainePosition;
			prochainePosition = indexOf(chaineASpliter,chaineLimite,anciennePosition+1);

		}
		return listeChaines.toArray(new String[0]);
	}

}
