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

	private boolean[] etatSelection;
	// TODO Implement constructor and methods (generation of a game, etc.)

	public GameModel() {

		listNumber = new ArrayList<Integer>();
		etatSelection = new boolean[12];
		initArray(etatSelection);
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

	private void initArray(boolean[] arraySelection) {

		for(int i = 0; i < getDigits().length(); i++) {

			arraySelection[i] = false;
		}
	}

	/**
	 * M�thode qui remplie l'Attribut listNumber
	 * selon certaines probabilit� stricte aux crit�res
	 * du jeux MeanSum.
	 */
	public void generateGame() {

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
	 * de caract�re avec tout les nombres
	 * g�n�r�s concat�n�s
	 * @return
	 */
	public String getDigits() {

		String chaineCaractere = "";
		for(int a :this.listNumber) {

			chaineCaractere+= a;
		}

		return chaineCaractere;
	}

	public boolean selectionValide(int ps1, int ps2) {

		String nbrSelected = "";
		boolean selValide = false;

		if(ps1 <  ps2 && ps1 >= 0 && ps2 < (getDigits().length() - 1)) {

			for(int i = ps1; i < ps2; i++) {

				if(listNumber.get(i) > 9 ) {

					nbrSelected += listNumber.get(i);
					i++;

				}else {

					nbrSelected += listNumber.get(i);
				}
			}

			if(listNumber.contains(Integer.parseInt(nbrSelected))){

				selValide = true;
			}

		}

		return selValide;

	}

	public void changeState(int ps1, int ps2) {

		if(ps1 < ps2 && ps2 < getDigits().length()) {

			for(int i = ps1; i < ps2; i++) {

				etatSelection[i] = true;

			}	
		}
	}

	public int currentSum(){

		String nbrSum = "";
		int sum = 0;
		char[] allNumbers = getDigits().toCharArray();

		for(int i = 0; i < etatSelection.length; i++) {


			if(etatSelection[i]) {

				nbrSum += String.valueOf(allNumbers[i]);
				if(i != 11) {
					if(!etatSelection[i+1]) {

						sum = sum + Integer.parseInt(nbrSum);
						nbrSum = "";
					}
				}
			}

		}
		
		return sum;

	}
	/*	public static void main(String[] args) {

		GameModel testMethode = new GameModel();

		testMethode.generateGame();
		System.out.println(testMethode.getDigits());
	}
	 */
}
