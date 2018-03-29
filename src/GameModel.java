import java.util.ArrayList;
import java.util.Random;

/**
 * The game model handles the logic of the game (generating the numbers, etc.).
 * The instance of the model is used by the view-controller module
 * to trigger actions (for example, generate a new game) and retrieve information
 * about the current status of the game (the digits, the goal, etc.).
 *
 */
public class GameModel {

	// Attribut qui contient la liste des nombres
	private ArrayList<Integer> listNumber;
	// État de selection pour chaque rectangles
	private Integer[] etatSelection;
	// Liste des regroupements créées par selections
	private ArrayList<Integer> regroupement;

	/**
	 * Constructeur par défaut,
	 * initialise tout mes attributs
	 */
	public GameModel() {

		listNumber = new ArrayList<Integer>();
		etatSelection = new Integer[12];
		initArray(etatSelection);
		regroupement = new ArrayList<Integer>();
	}

	/**
	 * The methode return a random number
	 * between min and max. Max must be greater 
	 * then min.
	 * @param min entié minimum à avoir
	 * @param max entié maximum à avoir
	 * @return
	 */
	private int getRandom(int min, int max) {

		// Vérifie que le min est plus petit que le max
		if(min >= max) {

			throw new IllegalArgumentException("Le minimum est plus grand ou égal au maxium !");
		}

		// Instanciation variable de type Random
		Random rand = new Random();

		// retourne un entier
		return rand.nextInt((max-min) + 1) + min;

	}

	/**
	 * Accesseur de l'attribut getEtatSelection
	 * @return Integer[] tableau d'entier
	 */
	public Integer[] getEtatSelection() {

		return etatSelection;
	}

	/**
	 * Accesseur de la liste des regroupements
	 * @return Integer[] tableau d'entier
	 */
	public ArrayList<Integer> getRegroupement(){

		return regroupement;
	}

	/**
	 * Accesseur de la liste des nombres a obtenir
	 * @return Arraylist d'entier
	 */
	public ArrayList<Integer> getListNumber() {

		return listNumber;
	}

	/**
	 * Méthode qui initialise un tableau à 0 
	 * pour toute ses cases
	 * @param Integer[] reçoit un tableau d'entier
	 */
	private void initArray(Integer[] arraySelection) {

		for(int i = 0; i < arraySelection.length; i++) {

			arraySelection[i] = 0;
		}
	}


	/**
	 * Méthode qui génère une nouvelle 
	 * partie générant une liste
	 * de nombre à afficher. Supprime
	 * partie précédente si existante.
	 */
	public void generateGame() {

		// Nombre aléatoire de regroupement à avoir
		int nbRegroupements = getRandom(3,6);
		// Réinitialise les composantes du jeux
		resetNext();

		for(int i = 0; i < nbRegroupements; i++) {

			// Condition des 70%
			if(getRandom(0,100) <= 70) {

				this.listNumber.add(getRandom(1,9));
			}else {

				this.listNumber.add(getRandom(10,99));
			}
		}
	}

	/**
	 * Méthode qui réinitialise tout les éléments
	 * du jeux
	 */
	public void resetNext() {

		// Réinitialise tout les attributs du jeux
		listNumber.removeAll(listNumber);
		initArray(etatSelection);
		regroupement.removeAll(regroupement);
	}

	/**
	 * Méthode qui réinitialise tout les éléments
	 * du jeux en gardant la même partie
	 */
	public void resetGame() {

		// Réinitialise tout les attributs du jeux
		initArray(etatSelection);
		regroupement.removeAll(regroupement);
	}

	/**
	 * Fonctions qui retourne une chaine
	 * de caractères de tout les générées 
	 * dans GenerateGame. 
	 * @return String de tout les chiffres affiché
	 */
	public String getDigits() {

		// Variable où sont concaténé les chiffres
		String allNumbers = "";

		//Loop pour créer le String retour.
		for(int a :this.listNumber) {

			allNumbers += a;
		}

		return allNumbers;
	}

	/**
	 * Méthode qui vérifie si une sélection
	 * est valide ou non.
	 * @param ps1 entier qui défini le rectangle initial
	 * @param ps2 entier qui défini le rectangle de fin de selection
	 * @return true ou false selon si la selection est valide ou non
	 */
	public boolean selectionValide(int ps1, int ps2) {

		// Instancié à false
		boolean validation = false;

		// Vérifie que la selection se fait ur au maximum 2 rectangles.
		if(ps1 == (ps2 - 1) || ps1 == (ps2 + 1) || ps1 == ps2) {

			validation = true;
		}

		return validation;
	}

	/**
	 * Méthode qui permet de changer l'état du rectangle
	 * sélectionné. 1 à 2 rectangles simultanément.
	 * @param ps1 entier qui défini rectangle initial
	 * @param ps2 entier qui défini le rectangle de fin de sélection
	 */
	public void changeState(int ps1, int ps2) {

		// Obtient une concaténation de tout les
		// chiffre affiché.
		String allNumbers = getDigits();

		//Vérifie que l'état des rectangles à modifié ne l'ont
		// pas déjà été
		if(etatSelection[ps1 - 1] == 0 && etatSelection[ps2 - 1] == 0) {

			// Tout dépendemment si c'est 1 rectangles ou 2
			// les procédures sont différentes.
			if(ps1 == ps2) {

				etatSelection[ps1 - 1] = 1;
				regroupement.add(Integer.parseInt(allNumbers.substring(ps1 - 1, ps2)));

				// Les procédures suivante permmettent de gérer
				// si la sélection est fait de droite à gauche
				// et vice-versa
			}else if(ps1 == ps2 - 1) {

				etatSelection[ps1 - 1] = 2;
				etatSelection[ps2 - 1] = 3;
				regroupement.add(Integer.parseInt(allNumbers.substring(ps1 - 1, ps2)));

			}else {

				etatSelection[ps1 - 1] = 3;
				etatSelection[ps2 - 1] = 2;
				regroupement.add(Integer.parseInt(allNumbers.substring(ps2 - 1, ps1)));
			}
		}
	}

	/**
	 * Fonctions qui retourne la somme 
	 * des cases sélectionné selon un tableau
	 * qui retient tout les regroupements créés
	 * @return entier représentant la somme des regroupements.
	 */
	public int getSum(){

		// Somme initialisée à zéro
		int sum = 0;

		// Boucle qui additionne 
		// toute les valeurs du tableau 
		// regroupement.
		for(int a: regroupement) {

			sum += a;
		}

		return sum;
	}

	/**
	 * Fonction qui retourne 
	 * l'objectif de la partie présente.
	 * Générer une nouvelle partie retourne
	 * un nouvelle objectif
	 * @return entier représentant le nouvelle objectif
	 */
	public int getGoal() {

		//Objectif initialisé à zéro
		int goal = 0;

		//Addition tout les nombres
		// présent dans le tableau 
		for(int a: listNumber) {

			goal += a;
		}

		return goal;

	}
}
