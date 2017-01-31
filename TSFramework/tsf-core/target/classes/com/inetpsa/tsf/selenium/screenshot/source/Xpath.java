package com.inetpsa.tsf.selenium.screenshot.source;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Management of the xpath for files HTML
 *
 */
public class Xpath{

    /** Logger. */
    private static final Logger LOG = Logger.getLogger("TSF");
	/** Root of the tree */
	private Node root;
	/** Source code of the page */
	private String source;
	/** position of the current positon */
	private int currentPosition;
	/** position of the end of the preceding positon */
	private int finPosition;
	/** Current data */
	private String currentData;

	/**
	 * Construction of the tree for XPath research
	 * @param source : source html
	 */
	public Xpath(String source){
	    /** Remove the line '< !-- …-->'*/
	    String htmlSource = source.replaceAll("<!--[\\s\\S]*?-->","");
		//Topologie project bug : if existed cssclass, "="" will appear in the html source when using firefox 8.
		if (source.contains("\"=\"\"")) {
			this.source = htmlSource.replaceAll("\"\"=\"\"", "").replaceAll("\"=\"\"", "");
		} else {
			this.source = htmlSource;
		}
		constructionTree();
	}
	
	/**
	 * construction of the tree for the xpath
	 */
	private void constructionTree(){
		baliseSuivante();
		root  = new Node(currentData, currentPosition, null);
		Node actuel = root;
		while (actuel != null) {
			baliseSuivante();
			if (currentData == null)
				break;
			if (baliseFermante()) {
				actuel.marqueFinBalise(currentData, currentPosition);
				actuel = actuel.parent();
			}
			else{
				actuel.creerFils(currentData, currentPosition);
				if (actuel.dernierFils().peutAvoirUnFils()) {
					actuel = actuel.dernierFils();
				}
			}
		}
	}
	
	
	/**
	 * passage to the following position, used for the construction of the tree
	 */
	private void baliseSuivante(){
		boolean estUnCommentaire=true;
		if (currentData == null && currentPosition == -1) {
			return ;
		}
		if (currentData == null) {
			currentPosition = 0;
			finPosition = 0;
		}
		while (estUnCommentaire) {
			if (currentData != null && (currentData.contains("<script") || currentData.contains("<SCRIPT") )) { 
				currentPosition = source.indexOf("</script>",finPosition);
				if (currentPosition == -1) {
					currentPosition = source.indexOf("</SCRIPT>",finPosition);
				}
			} else if (currentData != null && (currentData.contains("<textarea") || currentData.contains("<TEXTAREA") )) { 
				currentPosition = source.indexOf("</textarea>",finPosition);
				if (currentPosition == -1) {
					currentPosition = source.indexOf("</TEXTAREA>",finPosition);
				}
			} else if (currentData != null && (currentData.contains("<style") || currentData.contains("<STYLE") )){
				currentPosition = source.indexOf("</style>",finPosition);	
				if (currentPosition == -1) {
					currentPosition = source.indexOf("</STYLE>",finPosition);
				}
			} else {
				currentPosition = source.indexOf("<",finPosition);
			}
			finPosition = StringTools.indexOf(source, ">", currentPosition);
			if (source.charAt(currentPosition+1) != '!' || currentPosition == -1) {
				estUnCommentaire = false;
			}
		}
		if (currentPosition == -1) {
			currentData = null;
		} else {
			currentData = source.substring(currentPosition, finPosition + 1);
		}
	}
	
	/**
	 * Source
	 * @return la source
	 */
	public String getSource(){
		return source;
	}
	
	/**
	 * setSource
	 * @param newSource : new source
	 */
	public void setSource(String newSource){
		source = newSource;
		refresh();
	}
	
	/**
	 * To remove part of code using expression regular
	 * @param regex : regular expression 
	 */
	public void deleteText(String regex) {
		replaceText(regex, "");
	}
	
	/**
	 * Replace part of code using expression regular
	 * @param regex : regular expression 
	 * @param replacement : one replaces the regular expression
	 */
	public void replaceText(String regex, String replacement) {
		source = source.replaceAll(regex, replacement);
		refresh();
	}

	/**
	 * Writing of the source in a file
	 * @param file file need to write
	 */
	public void writeSource(String file){
		try{
			FileWriter fw = new FileWriter(file, false);
			BufferedWriter output = new BufferedWriter(fw);
			output.write(source);
			output.flush();
			output.close();
		}
		catch (IOException e) {
		    LOG.log(Level.WARNING, "IOException", e);
		}
	}
	
	/**
	 * Remove the contents of all the position indicated by the xpath given in entry
	 * @param xpath indicating the position
	 * @throws NoWayFindXPathException : exception
	 * @throws XPathInvalidException : exception
	 */
	public void removeAllFieldXPath(String xpath) throws NoWayFindXPathException, XPathInvalidException {
		replaceAllFieldXPath(xpath, "");
	}
	
	/**
	 * Replace the contents of the first beacon indicated by the xpath given in entry
	 * @param xpath indicating the position
	 * @param replaceValue : what one puts instead of what currently exists in the position
	 * @throws NoWayFindXPathException : exception
	 * @throws XPathInvalidException : exception
	 */
	public void replaceFieldXPath(String xpath,String replaceValue) throws NoWayFindXPathException, XPathInvalidException {
		Node occurence = premierNoeudXPath(xpath);
		String newSource = source;
		if (!occurence.peutAvoirUnFils()) {
			newSource = source.substring(0, occurence.debutOuvrante() + 1);
			newSource += occurence.tag().replaceAll("'", "");
			Set<String> attributs = occurence.attributs().keySet();
			for (java.util.Iterator<String> attributEnCours = attributs.iterator(); attributEnCours.hasNext();) {
				String attribut = attributEnCours.next();
				if (attribut.equals("value"))
					newSource += " "+attribut+"="+"\""+replaceValue+"\"";
				else
					newSource += " "+attribut+"="+occurence.attributs().get(attribut)+"";
			}
			newSource += source.substring(occurence.finOuvrante());
		}
		else{
			newSource = source.substring(0, occurence.finOuvrante()+1)+replaceValue+source.substring(occurence.debutFermante());
		}
		source=newSource;
		refresh();
	}	
	
	
	/**
	 * Replace the contents corresponding to a regular expression of the first beacon indicated by the xpath given in entered
	 * @param xpath indicating the position
	 * @param regex : regular expression to modify
	 * @param replacement : what one puts instead of what currently exists in the position
	 * @throws NoWayFindXPathException : exception
	 * @throws XPathInvalidException : exception
	 */
	public void replaceFieldXPathRegex(String xpath, String regex, String replacement) throws NoWayFindXPathException, XPathInvalidException {
		Node occurence = premierNoeudXPath(xpath);
		String newSource = source;
		if (!occurence.peutAvoirUnFils()) {
			String nouvelleValeur = source.substring(occurence.debutOuvrante(), occurence.finOuvrante()).replaceAll(regex, replacement);
			newSource = source.substring(0, occurence.debutOuvrante()) + nouvelleValeur + source.substring(occurence.finOuvrante());
		}
		else{
			String texteRemplace = source.substring(occurence.finOuvrante()+1, occurence.debutFermante()).replaceAll(regex, replacement);
			newSource = source.substring(0, occurence.finOuvrante()+1)+texteRemplace+source.substring(occurence.debutFermante());
		}
		source=newSource;
		refresh();
	}
	
	/**
	 * Replace the contents of ALL the position indicated by the xpath given in entry
	 * @param xpath indicating the position
	 * @param replaceValue : what one puts instead of what currently exists in the position
	 * @throws NoWayFindXPathException : exception
	 * @throws XPathInvalidException : exception
	 */
	public void replaceAllFieldXPath(String xpath,String replaceValue) throws NoWayFindXPathException, XPathInvalidException {
		//One to be able to go from the beginning to the end or the end at the beginning
		ArrayList<Node> occurences = triSelonDebutOuvrante(toutNoeudsXPath(xpath));
		String newSource = source;
		for (int i=occurences.size()-1; i >= 0; i--) {
			Node occurence = occurences.get(i);
			if (!occurence.peutAvoirUnFils()) {
				newSource = source.substring(0, occurence.debutOuvrante() + 1);
				newSource += occurence.tag().replaceAll("'", "");
				Set<String> attributs = occurence.attributs().keySet();
				for (java.util.Iterator<String> attributEnCours = attributs.iterator(); attributEnCours.hasNext();) {
					String attribut = attributEnCours.next();
					if (attribut.equals("value"))
						newSource += " "+attribut+"="+"\""+replaceValue+"\"";
					else
						newSource += " "+attribut+"="+occurence.attributs().get(attribut)+"";
				}
				newSource += source.substring(occurence.finOuvrante());
			}
			else{
				newSource = source.substring(0, occurence.finOuvrante() + 1) + replaceValue + source.substring(occurence.debutFermante());
			}
			source=newSource;
			refresh();
		}
	}
	
	/**
	 * Adds to the contents of the first position indicated by the xpath given in entry
	 * @param xpath indicating the position
	 * @param addValue : what one adds to what currently exists in the position
	 * @throws NoWayFindXPathException : exception
	 * @throws XPathInvalidException : exception
	 */
	public void addFieldXPath(String xpath,String addValue) throws NoWayFindXPathException, XPathInvalidException {
		Node occurence = premierNoeudXPath(xpath);
		String newSource = source;
		if (!occurence.peutAvoirUnFils()) {
			newSource = source.substring(0, occurence.debutOuvrante()+1);
			newSource += occurence.tag().replaceAll("'", "");
			Set<String> attributs = occurence.attributs().keySet();
			for (java.util.Iterator<String> attributEnCours = attributs.iterator(); attributEnCours.hasNext();) {
				String attribut = attributEnCours.next();
				if (attribut.equals("value"))
					newSource += " "+attribut+"="+"\""+occurence.attributs().get(attribut)+addValue+"\"";
				else
					newSource += " "+attribut+"="+occurence.attributs().get(attribut)+"";
			}
			newSource += source.substring(occurence.finOuvrante());
		}
		else{
			newSource = source.substring(0, occurence.debutFermante()) + addValue+source.substring(occurence.debutFermante());
		}
		source=newSource;
		refresh();
	}
	
	/**
	 * Change the attribute of the first position respecting the xpath
	 * @param xpath indicating the position
	 * @param attributAChanger : the attribute which one wants to change
	 * @param ceQuiEstAChanger : what one wants to change
	 * @param changement : by what one wants to change
	 * @throws NoWayFindXPathException : exception
	 * @throws XPathInvalidException : exception
	 */
	public void changerAttributChampXPath(String xpath,String attributAChanger,String ceQuiEstAChanger,String changement) throws NoWayFindXPathException, XPathInvalidException {
		Node occurence = premierNoeudXPath(xpath);
		String newSource = source;
			newSource = source.substring(0, occurence.debutOuvrante()+1);
			newSource += occurence.tag().replaceAll("'", ""); 
			Set<String> attributs = occurence.attributs().keySet();
			for (java.util.Iterator<String> attributEnCours = attributs.iterator(); attributEnCours.hasNext();) {
				String attribut = attributEnCours.next();
				if (attribut.equals(attributAChanger))
					newSource += " "+attribut+"="+""+occurence.attributs().get(attribut).replaceAll(ceQuiEstAChanger, changement);
				else
					newSource += " "+attribut+"="+occurence.attributs().get(attribut)+"";
			}
			newSource += source.substring(occurence.finOuvrante());
			source=newSource;
			refresh();
	}
	
	/**
	 * First node met checking the xpath
	 * @param xpath to check
	 * @return node node
	 * @throws NoWayFindXPathException : exception
	 * @throws XPathInvalidException : exception
	 */
	private Node premierNoeudXPath(String xpath) throws NoWayFindXPathException, XPathInvalidException{
		return toutNoeudsXPath(xpath).get(0);
	}	
	
	/**
	 * All nodes met checking the xpath
	 * @param xpath to check
	 * @return List found nodes
	 * @throws NoWayFindXPathException : exception
	 * @throws XPathInvalidException : exception
	 */
	private ArrayList<Node> toutNoeudsXPath(String xpath) throws NoWayFindXPathException, XPathInvalidException{
		//Initialization of the portions
		Portion[] portionsXpath = getPortions(xpath);
		//Creation of the list of the node of portion I
		ArrayList<ArrayList<Node>> listeNoeudsDeLaPortion = new ArrayList<ArrayList<Node>>();
		//init
		for (int i=0;i<=portionsXpath.length;i++) {
			listeNoeudsDeLaPortion.add(new ArrayList<Node>());
		}
		//First list: the root!
		listeNoeudsDeLaPortion.get(0).add(root);

		//We make the tests for all the portions
		for (int i=0;i<portionsXpath.length;i++) {
			if (i==0 && portionsXpath[i].nature() == Portion.typePortion.direct) {
				if (root.verifierCondition(portionsXpath[i])) {
					listeNoeudsDeLaPortion.get(i+1).add(root);
				}
			}
			else {
				for (Node noeud:listeNoeudsDeLaPortion.get(i)) {
					if (portionsXpath[i].nature() == Portion.typePortion.direct) {
						ArrayList<Node> noeudsTemporaires = noeud.filsQuiVerifient(portionsXpath[i]);
						for(Node noeudAEntrer:noeudsTemporaires)
							listeNoeudsDeLaPortion.get(i+1).add(noeudAEntrer);
					}
					else {
						ArrayList<Node> noeudsTemporaires = noeud.descendantsQuiVerifient(portionsXpath[i]);
						for(Node noeudAEntrer:noeudsTemporaires)
							listeNoeudsDeLaPortion.get(i+1).add(noeudAEntrer);
					}
				}
			}
		}
		if(listeNoeudsDeLaPortion.get(portionsXpath.length).size()==0)
			throw new NoWayFindXPathException(xpath);
		return listeNoeudsDeLaPortion.get(portionsXpath.length);
	}
	
	/**
	 * Test the fact that the position is closing
	 * @return the fact that the current position is closing
	 */
	private boolean baliseFermante(){
		return currentData.startsWith("</");
	}

	/**
	 * Cooling of the tree xpath
	 */
	private void refresh(){
		//On réinitialise la balise courante
		currentData = null;
		currentPosition = 0;
		constructionTree();
	}
	
	/**
	 * Sorting by bubble
	 * @param listeEntree : list has to sort
	 * @return list sorted
	 */
	private ArrayList<Node> triSelonDebutOuvrante(ArrayList<Node> listeEntree){
		ArrayList<Node> listeTriee = new ArrayList<Node>();
		//One starts with copied the elements of the list of entry in the local list
		for(Node noeud:listeEntree){
			listeTriee.add(noeud);
		}
		//Algorithm of sorting 
		for (int i = 0; i < listeTriee.size()-1; i++) {
			for (int j = i + 1; j < listeTriee.size(); j++) {
				if (listeTriee.get(j).debutOuvrante()<listeTriee.get(i).debutOuvrante()) {
					//If the opening position of J is before that of I, both are exchanged
					Node temp = listeTriee.get(i);
					listeTriee.set(i, listeTriee.get(j));
					listeTriee.set(j, temp);
				}
			}
		}
		return listeTriee;
	}
	
	/**
	 * Method which makes it possible to obtain the list of the portions of a xpath given
	 * @param xpath to cut out in portions
	 * @return portions xpath
	 * @throws XPathInvalidException : exception
	 */
	private Portion[] getPortions(String xpath) throws XPathInvalidException{
		ArrayList<Portion> portions = new ArrayList<Portion>();
		if (xpath.startsWith("//")) {
			//One cuts out the xpath in parts separated by //
			String[] xpathsDoubleSlash = xpath.split("//");
			//For each part (except the first, empties) one Re-cutting according to/
			for (int i = 1; i < xpathsDoubleSlash.length; i++) {
				String [] xpaths = xpathsDoubleSlash[i].split("/");
				//In each first portion, //is added
				portions.add(new Portion("//" + xpaths[0]));
					for (int j = 1; j < xpaths.length; j++) {
						//in the others, it is given/
						portions.add(new Portion("/" + xpaths[j]));
					}	
			}
		}
		else if (xpath.startsWith("/")) {
			String[] xpathsD = xpath.split("//");
			for (int i = 0; i < xpathsD.length; i++) {
				int departJ = 2;
				String [] xpaths = xpathsD[i].split("/");
				//For the first of xpathD, the first xpaths is empty
				if (i==0) {
					portions.add(new Portion("/"+xpaths[1]));
				}
				else {
					portions.add(new Portion("//" + xpaths[0]));
					departJ = 1;
				}
				for (int j = departJ; j< xpaths.length; j++) {
					portions.add(new Portion("/" + xpaths[j]));
				}	
			}
		}
		//If the xpath contains neither/nor [, the id of the zone is taken
		else if (!xpath.contains("/") && !xpath.contains("[")) {
			portions.add(new Portion("//*[@id='"+xpath+"']"));
		}
		else {
			String[] xpathsD = xpath.split("//");
			for (int i = 1;i < xpathsD.length; i++) {
				String [] xpaths = xpath.split("/");
				portions.add(new Portion("//"+xpaths[0]));
				for (int j = 1; j < xpaths.length; j++) {
					portions.add(new Portion("/"+xpaths[j]));
				}	
			}
		}
		return portions.toArray(new Portion[0]);
	} 
}
