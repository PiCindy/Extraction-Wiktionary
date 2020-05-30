import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

import org.apache.commons.cli.*;

public class Main {

	/***
	 * Paramètre les options nécessaires au programme
	 * @return Options : options nécessaires
	 */
	private static Options configParameters() {
		Options options = new Options();		
		Option mot = Option.builder()
							.longOpt("mot")
							.desc("Mot recherché")
							.hasArg()
							.required()
							.build();
		options.addOption(mot);
		
		Option info = Option.builder()
							.longOpt("info")
							.desc("Information attendue")
							.hasArgs()
							.required()
							.build();
		options.addOption(info);
		
		Option langues = Option.builder()
				.longOpt("langue")
				.desc("Langues recherchées")
				.hasArgs()
				.build();
		options.addOption(langues);
		
		return options;
	}
		

	public static void main (String[] args) throws ParseException, IOException, ClassNotFoundException {
		Options options = configParameters();
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, args);
			String title = cmd.getOptionValue("mot").toLowerCase();
			String filemot = "donnees/" + title + ".pkl";
			ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(filemot));
			Mot mot = (Mot)ois.readObject();
			ois.close();
			String[] infos = cmd.getOptionValues("info");
			mot.affMot();
			for (String info : infos) {
				switch(info) {
					case "prononciation" :
						mot.affPrononciation();
						break;
					case "categorie" :
						mot.affCategories();
						break;
					case "traduction" :
						String[] langues = cmd.getOptionValues("langue");
						mot.affTraductions(langues);
						break;
					case "synonyme" :
						mot.affSynonymes();
						break;
					case "antonyme" :
						mot.affAntonymes();
						break;
					default :
						System.out.println("Mauvaise information recherchée");
						break;
				}
			}
		}
		catch (Exception e) {
			System.out.println("Erreur : " + e);
		}

	}
}
