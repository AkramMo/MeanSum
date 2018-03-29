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

	private Integer[] etatSelection;
	private ArrayList<Integer> regroupement;
	// TODO Implement constructor and methods (generation of a game, etc.)

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
	 * @param min
	 * @param max
	 * @return
	 */
	private int getRandom(int min, int max) {

		// V�rifie que le min est plus petit que le max
		if(min >= max) {

			throw new IllegalArgumentException("Le minimum est plus grand ou �gal au maxium !");
		}

		Random rand = new Random();

		return rand.nextInt((max-min) + 1) + min;



	}

	public Integer[] getEtatSelection() {

		return etatSelection;

	}

	public ArrayList<Integer> getRegroupement(){

		return regroupement;
	}

	private void initArray(Integer[] arraySelection) {

		for(int i = 0; i < arraySelection.length; i++) {

			arraySelection[i] = 0;
		}
	}

	/**
	 * M�thode qui remplie l'Attribut listNumber
	 * selon certaines probabilit� stricte aux crit�res
	 * du jeux MeanSum.
	 */
	public void generateGame() {

		int nbRegroupements = getRandom(3,6);
		listNumber.removeAll(listNumber);
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
	 * de caract�re avec tout les nombres
	 * g�n�r�s concat�n�s
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
				regroupement.add(Integer.parseInt(allNumbers.substring(ps1 , ps1 + 1)));
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
		/*	for(int i = 0; i < etatSelection.length; i++) {


			if(etatSelection[i]) {

				nbrSum += String.valueOf(allNumbers[i]);
				if(i != 11) {
					if(!etatSelection[i+1]) {

						sum = sum + Integer.parseInt(nbrSum);
						nbrSum = "";
					}
				}
			}

		}*/

		return sum;

	}
	/*	public static void main(String[] args) {

		GameModel testMethode = new GameModel();

		testMethode.generateGame();
		System.out.println(testMethode.getDigits());
	}
	 */
}
