import java.io.IOException;

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


public class Extraction {

	public static String extractionPrononciation(String content) {
		Pattern p = Pattern.compile("\\{pron\\|([^\\|]*)\\|");
		Matcher m = p.matcher(content);
		if (m.find()) {
 			return content.substring(m.start(1), m.end(1));
		}
		return "Prononciation introuvable";
	}
	
	public static List<String> extractionCategorie(String content) {
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
	
	public static boolean isFrench(String content) {
		Pattern p = Pattern.compile("\\{langue\\|fr\\}");
		Matcher m = p.matcher(content);
		return m.find();
	}
	
	public static void main(String[] args) throws IOException, XMLStreamException, ParserConfigurationException, TransformerException {
		String filename = "small.xml";
		//String mot = "lire";
		for (Page page : new PageExtractor(filename)) {
			page.toXML("page.xml");
			if (isFrench(page.content)) {
				System.out.println(page.title + " : " + extractionPrononciation(page.content) + ", " + extractionCategorie(page.content));
			}
			//System.out.println(page.title);
			/*if (page.title.contentEquals(mot)) {
				System.out.println(page.title);
				System.out.println(page.content);
			}*/
		}
		
		
	}
}
