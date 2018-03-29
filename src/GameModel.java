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
	 * Accesseur de l'Attribut getEtatSelection
	 * @return un tableau d'entier
	 */
	public Integer[] getEtatSelection() {

		return etatSelection;

	}

	/**
	 * Accesseur de la liste des regroupements
	 * @return tableau d'entier
	 */
	public ArrayList<Integer> getRegroupement(){

		return regroupement;
	}

	/**
	 * Méthode qui initialise un tableau à 0 
	 * pour toute ses cases
	 * @param arraySelection re^coit un tableau d'entier
	 */
	private void initArray(Integer[] arraySelection) {

		for(int i = 0; i < arraySelection.length; i++) {

			arraySelection[i] = 0;
		}
	}


	/**
	 * Méthode qui génère une nouvelle 
	 * partie
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
	 * de caractï¿½re avec tout les nombres
	 * gï¿½nï¿½rï¿½s concatï¿½nï¿½s
	 * @return
	 */
	public String getDigits() {

		String allNumbers = "";
		for(int a :this.listNumber) {

			allNumbers += a;
		}

		return allNumbers;
	}

	public boolean selectionValide(int ps1, int ps2) {

		boolean validation = false;


		if(ps1 == (ps2 - 1) || ps1 == (ps2 + 1) || ps1 == ps2) {

			validation = true;
		}


		return validation;

	}

	public void changeState(int ps1, int ps2) {

		String allNumbers = getDigits();

		if(selectionValide(ps1, ps2)) {

			if(ps1 == ps2) {

				etatSelection[ps1 - 1] = 1;
				regroupement.add(Integer.parseInt(allNumbers.substring(ps1 - 1, ps2)));


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

	public int getSum(){

		String nbrSum = "";
		int sum = 0;
		char[] allNumbers = getDigits().toCharArray();

		for(int a: regroupement) {

			sum += a;

		}
		return sum;

	}
	
	public int getGoal() {
		
		int goal = 0;
		
		for(int a: listNumber) {
			
			goal += a;
		}
		
		return goal;
		
	}
}
