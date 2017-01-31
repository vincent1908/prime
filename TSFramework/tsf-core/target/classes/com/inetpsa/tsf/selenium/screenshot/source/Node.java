package com.inetpsa.tsf.selenium.screenshot.source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Class allowing to manage a tree easily
 */
public class Node{
	/** name of the position */
	private String tag;
	/** together attributes of the position */
	private Map<String,String> attributes;
	/** beginning of the opening position*/
	private int debutOuvrante;
	/** end of the opening position*/
	private int finOuvrante;
	/** beginning of the closing position*/
	private int debutFermante;
	/** end of the closing position*/
	private int finFermante;
	
	/** Node father */
	private Node parent;
	/** Node son */
	private Node filsGauche;
	/** right brother */
	private Node frereDroit;
	/** Possibility of having a son */
	private boolean peutAvoirFils;
	
	/**
	 * Construction of node
	 * @param balise : balise
	 * @param position : position of the balise
	 * @param parent : element relative of the node
	 */
	public Node(String balise,int position,Node parent){
		String baliseTemp = balise;
		if (baliseTemp.contains(" = ")) {
			baliseTemp = baliseTemp.replace(" = ", "=");
		}
		this.parent = parent;
		this.attributes = new HashMap<String, String>();
		int positionFinBalise = baliseTemp.length()-1; 
		debutOuvrante = position;
		finOuvrante = debutOuvrante + positionFinBalise;

		String[] elements = baliseTemp.substring(1, positionFinBalise).split(" +"); //The various elements of the beacon are taken
		tag = "'"+elements[0]+"'"; //The first element it is the tag him even
		tag = tag.toLowerCase();//To avoid the concerns for IE
		attributes.put("tag", tag);
		for (int i = 1; i < elements.length; i++) {
			//One does not take into account it/
			if (!elements[i].equals("/")) {
				//If there is not =, the attribute is not valid
				if (!elements[i].contains("=")) {
					continue; //TODO To manage the error correctly, in the meantime one does nothing
				}
				String attribut = elements[i].split("=")[0];
				String valeur = elements[i].split("=")[1];
				//As long as the following element does not contain =, one adds to the value in progress (Value of attribute containing of spaces)
				while (i + 1<elements.length && (!elements[i+1].contains("=") || elements[i+1].contains("=="))) {
					i++;
					valeur += " "+elements[i];
				}
				attributes.put(attribut,valeur);
			}
		}
		filsGauche = null;
		frereDroit = null;
		//The position observing the following conditions cannot have son!
		//to check that all the case is treated
		if (tag.equals("'input'") || tag.equals("'br'") || tag.equals("'meta'") || tag.equals("'img'")
				||tag.equals("'base'")||tag.equals("'link'") || balise.contains("/>")) {
			peutAvoirFils = false;
			finFermante = -1;
			debutFermante = finFermante;
		}
		else
			peutAvoirFils = true;
	}
	
	/**
	 * peutAvoirUnFils
	 * @return peutAvoirFils
	 */
	public boolean peutAvoirUnFils(){
		return peutAvoirFils;
	}

	
	/**
	 * parent
	 * @return parent
	 */
	public Node parent(){
		return parent;
	}

	
	/**
	 * finOuvrante
	 * @return finOuvrante
	 */
	public int finOuvrante(){
		return finOuvrante;
	}

	
	/**
	 * finFermante
	 * @return finFermante
	 */
	public int finFermante(){
		return finFermante;
	}
	
	/**
	 * debutOuvrante
	 * @return debutOuvrante
	 */
	public int debutOuvrante(){
		return debutOuvrante;
	}
	
	/**
	 * debutFermante
	 * @return debutFermante
	 */
	public int debutFermante(){
		return debutFermante;
	}

	
	/**
	 * attributes
	 * @return attributes
	 */
	public Map<String,String> attributs(){
		return attributes;
	}
	
	/**
	 * tag
	 * @return tag
	 */
	public String tag(){
		return tag;
	}
	
	
	/**
	 * Method of creation of a son
	 * @param baliseFils :
	 * @param position :
	 */
	public void creerFils(String baliseFils,int position) {
		//One acts that if the position can have a son
		if (peutAvoirFils) {
			//If there are no son, one in created one
			if (this.filsGauche == null) {
				this.filsGauche = new Node(baliseFils, position, this);
			}
			//If not one adds it at the end of the chained list
			else {
				Node actuel = this.filsGauche;
				Node precedent = null;
				while (actuel != null) {
					precedent = actuel;
					actuel = actuel.frereDroit;
				}
				precedent.frereDroit = new Node(baliseFils, position, this);
			}
		}
	}	
	
	/**
	 * To record the end of the position
	 * @param balise .
	 * @param position .
	 */
	public void marqueFinBalise(String balise, int position){
		Node noeudConcernee = this;
		//If one cannot have son, then the closing beacon concerns our father
		if (peutAvoirFils) {
			noeudConcernee = this;
		} else {
			noeudConcernee = this.parent;
		}
		noeudConcernee.debutFermante = position;
		noeudConcernee.finFermante = noeudConcernee.debutFermante + balise.length();
	}	
	
	/**
	 * Give the last son of the node
	 * @return the last node son of the node in progress
	 */
	public Node dernierFils(){
		Node dernierFils = this.filsGauche;
		if (dernierFils!= null) {
			while (dernierFils.frereDroit != null) {
				dernierFils = dernierFils.frereDroit;
			}
		}
		return dernierFils;
	}

	/**
	 * Give the list of the sons who check the portion of the xpath
	 * @param portionXPath : portion to be tested
	 * @return the list of the checking nodes the xpath.<br/> turns over an empty list if no element is found.
	 */
	public ArrayList<Node> filsQuiVerifient(Portion portionXPath){
		Node actuel = this.filsGauche;
		ArrayList<Node> liste = new ArrayList<Node>();
		//On regarde tous les fils
		while (actuel != null) {
			if (actuel.verifierCondition(portionXPath)) {
				liste.add(actuel);
			}
			actuel = actuel.frereDroit;
		}
		//If there is one second condition, one looks at
		if (portionXPath.conditions().size() > 1)
			//If the second condition is a number, one gives only one list containing the 11th element of the list in progress
			if (portionXPath.conditions().get(1).matches("[0-9]+")) {
				int index = Integer.parseInt(portionXPath.conditions().get(1));
				ArrayList<Node> listeT = new ArrayList<Node>();
				if (index -1 >= liste.size()) {
					return listeT;
				}
				listeT.add(liste.get(index-1));
				return listeT;
			}
		return liste;
	}
	
	/**
	 * Give the list of all the descendants which check the portion of the xpath
	 * @param portionXPath : portion to be tested
	 * @return the list of the nodes checking the xpath.<br/> turns over an empty list if no element is found.
	 */
	public ArrayList<Node> descendantsQuiVerifient(Portion portionXPath){
		Node filsActuel = this.filsGauche;
		ArrayList<Node> liste = new ArrayList<Node>();
		//It is looked at initially if there is of the sons who check the conditions
		while (filsActuel != null) {
			//If our son checks the conditions, one adds it to the list
			if (filsActuel.verifierCondition(portionXPath)) {
				liste.add(filsActuel);
			}
			//One passes to his brother
			filsActuel = filsActuel.frereDroit;
		}
		//One then looks at the descent of all our son
		filsActuel = this.filsGauche;
		while (filsActuel!=null) {
			ArrayList<Node> listeDuFilsActuel = null;
			listeDuFilsActuel = filsActuel.descendantsQuiVerifient(portionXPath);
			for (Node noeudFils : listeDuFilsActuel) {
				liste.add(noeudFils);
			}
			//One passes to his brother
			filsActuel = filsActuel.frereDroit;
		}
		return liste;
	}
	
	/**
	 * Method which allows the checking of the various conditions related to the xpath
	 * @param portionXPath : portion to be tested
	 * @return : truth if the node checks all the conditions, if not that will be false.
	 */
	public boolean verifierCondition(Portion portionXPath){
		String attribut = "";
		String valeur = "";
		boolean resultat = true;
		for (String condition : portionXPath.conditions()) {
			boolean doitExister = true;
			//If the condition is on an attribute
			if (condition.contains("=")) {
				attribut = condition.substring(0, condition.indexOf("="));
				valeur = condition.substring(condition.indexOf("=")+1);
			}
			//If the condition relates to the existence
			else if (condition.contains("existe")) {
				attribut = condition.substring(0, condition.indexOf(" "));
				valeur = null;
				if (condition.contains("existePas")) {
					doitExister = false;
				}
			}
			//One tests only if attribute exists (and thus that the 11th beacon is not taken)
			if (!attribut.equals("")) {
				//The presence of the attribute is checked
				boolean attributPresent = attributes.containsKey(attribut);
				//If the element does not exist whereas it must exist, and conversely, one turns over false
				if (attributPresent != doitExister) {
					return false;
				} else if (attributPresent && valeur != null) {
					if (!attributes.get(attribut).equals(valeur) && !valeur.equals("'*'")) {
						return false;
					}
				}
			}
		}
		return resultat;
	}
	
	/**
	 * getTag
	 * @return Returns the tag.
	 */
	public String getTag() {
		return tag;
	}
}
