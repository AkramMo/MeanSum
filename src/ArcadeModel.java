/**
 * La classe ArcadeModel s'occupe de gérer la logique du
 * mode Arcade du jeux ainsi que ses règles et son comportement.
 * Il hérite de la classe GameModel.
 * @author AkramMo
 *
 */
public class ArcadeModel extends GameModel {

	// Niveau du joueur.
	private int levelGame;

	/**
	 * Constructeur qui initialise
	 * le jeux au niveau 1.
	 */
	public ArcadeModel() {

		levelGame = 1;
	}

	/**
	 * Récupère le nombre de regroupement
	 * de chiffre à trouver.
	 * @param levelGame niveau du joueur
	 * @return
	 */
	private int getNbrRegroupement(int levelGame){

		return Math.round((3 + (3*levelGame/20)));

	}

	/**
	 * Retourne la probabilité d'obtenir
	 * un regroupement à 2 chiffres selon 
	 * le niveau du joueur. Proportionnel
	 * au niveau.
	 * @param levelGame niveau du joueur
	 * @return
	 */
	private double getDoubleDigitProba(int levelGame) {

		return (30 + 30*(levelGame*10)/200);
	}


	/**
	 * Méthode qui génère une nouvelle 
	 * partie générant une liste
	 * de nombre à afficher. Supprime
	 * partie précédente si existante. Version adapté
	 * qui override la méthode parent .
	 */
	@Override
	public void generateGame() {

		// Nombre aléatoire de regroupement à avoir
		int nbRegroupements = getNbrRegroupement(levelGame);
		// probabilité d'avoir un nombre à 2 chiffres
		double doubleDigitProba = 100 - getDoubleDigitProba(levelGame);

		// Réinitialise les composantes du jeux
		suppressionPartie();



		for(int i = 0; i < nbRegroupements; i++) {

			// Condition selon le doubleDigitProba
			if(getRandom(0,100) <= doubleDigitProba ) {

				getListNumber().add(getRandom(1,9));
			}else {

				getListNumber().add(getRandom(10,99));
			}
		}

		// Affiche les informations sur la partie dans la console
		System.out.print(getDigits());
		System.out.println(" Mode Arcade, Level " + levelGame + ", "+ nbRegroupements + " regroupements" 
				+ ", "+ getDoubleDigitProba(levelGame) + "% chance d'obtenir un nombre à 2 chiffres");
	}

	/**
	 * Incrémente le niveau du joueur selon
	 * le paramètre reçus.
	 * @param passOrFail true si niveau réussi,
	 * sinon false.
	 */
	public void nextLevel(boolean passOrFail) {

		if(passOrFail && levelGame < 20) {

			levelGame++;
		}else {

			if(levelGame == 20) {
				
				System.out.println("Vous avez terminé le jeux !");
			}
			levelGame = 1;
		}
	}

	/**
	 * Accesseur du niveau du joueur.
	 * @return
	 */
	public int getLevel() {

		return levelGame;
	}





}
