/**
 * La classe ArcadeModel s'occupe de g�rer la logique du
 * mode Arcade du jeux ainsi que ses r�gles et son comportement.
 * Il h�rite de la classe GameModel.
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
	 * R�cup�re le nombre de regroupement
	 * de chiffre � trouver.
	 * @param levelGame niveau du joueur
	 * @return
	 */
	private int getNbrRegroupement(int levelGame){

		return Math.round((3 + (3*levelGame/20)));

	}

	/**
	 * Retourne la probabilit� d'obtenir
	 * un regroupement � 2 chiffres selon 
	 * le niveau du joueur. Proportionnel
	 * au niveau.
	 * @param levelGame niveau du joueur
	 * @return
	 */
	private double getDoubleDigitProba(int levelGame) {

		return (30 + 30*(levelGame*10)/200);
	}


	/**
	 * M�thode qui g�n�re une nouvelle 
	 * partie g�n�rant une liste
	 * de nombre � afficher. Supprime
	 * partie pr�c�dente si existante. Version adapt�
	 * qui override la m�thode parent .
	 */
	@Override
	public void generateGame() {

		// Nombre al�atoire de regroupement � avoir
		int nbRegroupements = getNbrRegroupement(levelGame);
		// probabilit� d'avoir un nombre � 2 chiffres
		double doubleDigitProba = 100 - getDoubleDigitProba(levelGame);

		// R�initialise les composantes du jeux
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
				+ ", "+ getDoubleDigitProba(levelGame) + "% chance d'obtenir un nombre � 2 chiffres");
	}

	/**
	 * Incr�mente le niveau du joueur selon
	 * le param�tre re�us.
	 * @param passOrFail true si niveau r�ussi,
	 * sinon false.
	 */
	public void nextLevel(boolean passOrFail) {

		if(passOrFail && levelGame < 20) {

			levelGame++;
		}else {

			if(levelGame == 20) {
				
				System.out.println("Vous avez termin� le jeux !");
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
