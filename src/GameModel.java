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

	// TODO Add attributes (list of numbers, etc.)

	private ArrayList<Integer> listNumber;
	// TODO Implement constructor and methods (generation of a game, etc.)

	public GameModel() {

		listNumber = new ArrayList<Integer>();
	}

	/**
	 * The methode return a random number
	 * between min and max. Max must be greater 
	 * then min.
	 * @param min
	 * @param max
	 * @return
	 */
	private int getRandom(int min, int max) {

		// Vérifie que le min est plus petit que le max
		if(min >= max) {

			throw new IllegalArgumentException("Le minimum est plus grand ou égal au maxium !");
		}

		Random rand = new Random();

		return rand.nextInt((max-min) + 1) + min;



	}

	/**
	 * Méthode qui remplie l'Attribut listNumber
	 * selon certaines probabilité stricte aux critères
	 * du jeux MeanSum.
	 */
	private void generateGame() {

		int nbRegroupements = getRandom(3,6);

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
	 * Fonctions qui retourne une chaine
	 * de caractère avec tout les nombres
	 * générés concaténés
	 * @return
	 */
	private String getDigits() {

		String chaineCaractere = "";
		for(int a :this.listNumber) {

			chaineCaractere+= a;
		}

		return chaineCaractere;
	}

	public static void main(String[] args) {

		GameModel testMethode = new GameModel();

		testMethode.generateGame();
		System.out.println(testMethode.getDigits());
	}

}
