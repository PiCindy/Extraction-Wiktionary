import java.util.HashMap;
import java.util.List;

import java.io.Serializable;

public class Mot implements Serializable {

	private static final long serialVersionUID = 1L;
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
	
	public void affMot() {
		System.out.println(ecriture);
	}
	
	public void affPrononciation() {
		System.out.println("Prononciation : " + prononciation);
	}
	
	public void affCategories() {
		System.out.println("Catégories morpho-syntaxiques : " + categories);
	}
	
	public void affSynonymes() {
		System.out.println("Synonymes : " + synonymes);
	}
	
	public void affAntonymes() {
		System.out.println("Antonymes : " + antonymes);
	}
	
	public void affTraductions(String[] langues) {
		System.out.print("Traductions : ");
		if (langues == null || langues.length == 0) {
			System.out.println(traductions);
		}
		else {
			for (String l : langues) {
				if (traductions.containsKey(l)) {
					System.out.print(l + "=" + traductions.get(l) + " ");
				}
			}
			System.out.println();
		}
	}
	
}
