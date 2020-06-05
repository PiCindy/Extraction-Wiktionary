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
	
	/***
	 * Constructeur de notre classe
	 */
	public Mot(String ecriture, String prononciation, HashMap<String, String> traductions, List<String> categories, List<String> synonymes, List<String> antonymes) {
		this.ecriture = ecriture;
		this.prononciation = prononciation;
		this.traductions = traductions;
		this.categories = categories;
		this.synonymes = synonymes;
		this.antonymes = antonymes;
	}
	
	/***
	 * @return : affiche le mot dans le terminal
	 */
	public void affMot() {
		System.out.println(ecriture);
	}
	
	/***
	 * @return : affiche la prononciation dans le terminal
	 */
	public void affPrononciation() {
		System.out.println("Prononciation : " + prononciation);
	}
	
	/***
	 * @return : affiche la catégorie morpho-syntaxique dans le terminal
	 */
	public void affCategories() {
		System.out.println("Catégories morpho-syntaxiques : " + categories);
	}
	
	/***
	 * @return : affiche les synonymes dans le terminal
	 */
	public void affSynonymes() {
		System.out.println("Synonymes : " + synonymes);
	}
	
	/***
	 * @return : affiche les antonymes dans le terminal
	 */
	public void affAntonymes() {
		System.out.println("Antonymes : " + antonymes);
	}
	
	/***
	 * @return : affiche les traductions dans le terminal
	 */
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
