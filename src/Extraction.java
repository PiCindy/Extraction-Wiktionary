import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import wiktionary_extractor.Page;
import wiktionary_extractor.PageExtractor;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.HashMap;


public class Extraction {
	
	/***
	 * Paramètre : un mot
	 * @return la prononciation du mot entré en paramètre
	 */
	public static String extractionPrononciation(String content) {
		Pattern p = Pattern.compile("\\{pron\\|([^\\|]*)\\|");
		Matcher m = p.matcher(content);
		if (m.find()) {
 			return content.substring(m.start(1), m.end(1));
		}
		return "Prononciation introuvable";
	}
	
	/***
	 * Paramètre : un mot
	 * @return la liste des catégories morpho-syntaxiques du mot entré en paramètre
	 */
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
	
	/***
	 * Paramètre : un mot
	 * @return un dictionnaire contenant la.les traduction.s du mot entré en paramètre
	 */
	public static HashMap<String, String> extractionTraductions(String content) {
		Pattern p = Pattern.compile("\\{trad[+|\\-]*\\|([^\\|]*)\\|([^\\||\\}]*)[\\|\\}]");
		Matcher m = p.matcher(content);
		HashMap<String, String> traductions = new HashMap<String, String>();
		while (m.find()) {
			if (!traductions.containsKey(content.substring(m.start(1), m.end(1)))) {
				traductions.put(content.substring(m.start(1), m.end(1)), content.substring(m.start(2), m.end(2)));
			}
		}
		return traductions;
	}
	
	/***
	 * Paramètre : un mot
	 * @return la liste des synonymes du mot entré en paramètre
	 */
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
	
	/***
	 * Paramètre : un mot
	 * @return la liste des antonymes du mot entré en paramètre
	 */
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
	
	/***
	 * Paramètre : un mot
	 * @return les informations uniquement en français de la page du mot
	 */
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
	
	/***
	 * Paramètre : les options entrés par l'utilisateur
	 * @return : les informations demandées par l'utilisateur
	 * 
	 * Dans cette fonction, nous rendons également les données sérialisables.
	 */
	public static void main(String[] args) throws IOException, XMLStreamException, ParserConfigurationException, TransformerException, ClassNotFoundException {
		String filename = "small.xml";
		//String mot = "homogène";
		for (Page page : new PageExtractor(filename)) {
			page.toXML("page.xml");
			String content = french(page.content);
			if (!content.contentEquals("")) {
			//if (page.title.contentEquals(mot)) {
				String title = page.title;
				String filemot = "donnees/" + title + ".pkl";
				String prononciation = extractionPrononciation(content);
				HashMap<String, String> traductions = extractionTraductions(content);
				List<String> categories = extractionCategories(content);
				List<String> synonymes = extractionSynonymes(content);
				List<String> antonymes = extractionAntonymes(content);
				Mot m = new Mot(title, prononciation, traductions, categories, synonymes, antonymes);
				// On sérialise la donnée :	
				ObjectOutputStream ostream = new ObjectOutputStream(new FileOutputStream(filemot));
				ostream.writeObject(m);
				ostream.close();
				// On désérialise (récupère) la donnée : 
				ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(filemot));
				Mot m2 = (Mot)ois.readObject();
				m2.affMot();
				m2.affPrononciation();
				ois.close();
			}
			//System.out.println(page.title);
			/*if (page.title.contentEquals(mot)) {
				System.out.println(page.title);
				System.out.println(content);
			}*/
		}
		
		
	}
}
