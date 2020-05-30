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
	
	public Mot(Page page) throws IOException, XMLStreamException, ParserConfigurationException, TransformerException {
		page.toXML("page.xml");
		ecriture = page.title;
		String content = french(page.content);
		if (content.contentEquals("")) {
			System.out.println("Ce mot n'est pas français");
		}
		prononciation = extractionPrononciation(content);
		traductions = extractionTraductions(content);
		categories = extractionCategories(content);
		synonymes = extractionSynonymes(content);
		antonymes = extractionAntonymes(content);
		
	}
	
	public static String extractionPrononciation(String content) {
		Pattern p = Pattern.compile("\\{pron\\|([^\\|]*)\\|");
		Matcher m = p.matcher(content);
		if (m.find()) {
 			return content.substring(m.start(1), m.end(1));
		}
		return "Prononciation introuvable";
	}
	
	public static List<String> extractionCategories(String content) {
		Pattern p = Pattern.compile("\\{S\\|([^\\||===]*)\\|fr[\\||\\}]");
		Matcher m = p.matcher(content);
		ArrayList<String> cat = new ArrayList<String>();
		while (m.find()) {
 			cat.add(content.substring(m.start(1), m.end(1)));
		}
		// On retire les doublons en passant par des Set
		Set<String> uniq = new HashSet<String>(cat);
	    List<String> categories = new ArrayList<String>(uniq);
	    categories.remove("homophones");
		return categories;
	}
	
	public static HashMap<String, String> extractionTraductions(String content) {
		Pattern p = Pattern.compile("\\{trad[+|\\-]*\\|([^\\|]*)\\|([^\\||\\}]*)[\\|\\}]");
		Matcher m = p.matcher(content);
		HashMap<String, String> traductions = new HashMap<String, String>();
		while (m.find()) {
			traductions.put(content.substring(m.start(1), m.end(1)), content.substring(m.start(2), m.end(2)));
		}
		return traductions;
	}
	
	public static List<String> extractionSynonymes(String content) {
		// N'extrait qu'un seul synonyme (mauvaise regex) 
		Pattern p = Pattern.compile("S\\|synonymes[^\\[]*\\[\\[([^\\]]*)\\]\\]", Pattern.DOTALL);
		Matcher m = p.matcher(content);
		ArrayList<String> syn = new ArrayList<String>();
		while(m.find()) {
			syn.add(content.substring(m.start(1), m.end(1)));
		}

		// On retire les doublons en passant par des Set
		Set<String> uniq = new HashSet<String>(syn);
	    List<String> synonymes = new ArrayList<String>(uniq);
	    return synonymes;
	}
	
	public static List<String> extractionAntonymes(String content) {
		// N'extrait qu'un seul antonyme (mauvaise regex) 
		Pattern p = Pattern.compile("S\\|antonymes[^\\[]*\\[\\[([^\\]]*)\\]\\]", Pattern.DOTALL);
		Matcher m = p.matcher(content);
		ArrayList<String> ant = new ArrayList<String>();
		while(m.find()) {
			ant.add(content.substring(m.start(1), m.end(1)));
		}

		// On retire les doublons en passant par des Set
		Set<String> uniq = new HashSet<String>(ant);
	    List<String> antonymes = new ArrayList<String>(uniq);
	    return antonymes;
	}
	
	public static String french(String content) {
		Pattern p2 = Pattern.compile("\\{langue\\|fr\\}");
		Matcher m1 = p2.matcher(content);
		if (m1.find()) {
			Pattern p = Pattern.compile("\\{langue\\|fr\\}\\} ==\n(.*?)== \\{\\{langue\\|", Pattern.DOTALL);
			Matcher m = p.matcher(content);
			if (m.find()) {
				return content.substring(m.start(1), m.end(1));
			}
			return content;
		}
		return "";
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
