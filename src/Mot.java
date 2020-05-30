import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import wiktionary_extractor.Page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.Serializable;

public class Mot implements Serializable {

	private String ecriture;
	private String prononciation;
	private HashMap<String, String> traductions;
	private List<String> categories;
	private List<String> synonymes;
	private List<String> antonymes;
	
	public Mot(String ecriture, String prononciation, HashMap<String, String> traductions, List<String> categories, List<String> synonymes, List<String> antonymes) {
		this.ecriture = ecriture;
		this.prononciation = prononciation;
		this.traductions = traductions;
		this.categories = categories;
		this.synonymes = synonymes;
		this.antonymes = antonymes;
	}
	
	public void affPrononciation() {
		System.out.println(prononciation);
	}
	
	public void affCategories() {
		System.out.println(categories);
	}
	
	public void affSynonymes() {
		System.out.println(synonymes);
	}
	
	public void affAntonymes() {
		System.out.println(antonymes);
	}
	
	public void affTraductions() {
		System.out.println(traductions);
	}
	
}
