import org.apache.commons.cli.*;

public class Test {

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
		return options;
	}
		

	public static void main (String[] args) throws ParseException {
		Options options = configParameters();
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse(options, args);
			String mot = cmd.getOptionValue("mot").toLowerCase();
			String[] infos = cmd.getOptionValues("info");
			for (String info : infos) {
				switch(info) {
					case "prononciation" :
						//afficherPrononciation(mot);
						System.out.println("Prononciation du mot " + mot);
						break;
					case "categorie" :
						//afficherCategorie(mot);
						System.out.println("Catégorie du mot " + mot);
						break;
					case "traduction" :
						//afficherTraduction(mot);
						System.out.println("Traduction du mot " + mot);
						break;
					case "synonyme" :
						//afficherSynonyme(mot);
						System.out.println("Synonyme du mot " + mot);
						break;
					case "antonyme" :
						//afficherAntonyme(mot);
						System.out.println("Antonyme du mot " + mot);
						break;
					default :
						System.out.println("Mauvaise information recherchée");
						break;
				}
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}

	}
}
