import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

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
	// �tat de selection pour chaque rectangles
	private Integer[] etatSelection;
	// Liste des regroupements cr��es par selections
	private ArrayList<Integer> regroupement;
	// Timer pour chronom�trer les parties
	private Timer timer;
	// Format de temps affich� au joueur
	private String timeFormat;
	// Nombre de reset du joueur
	private int countReset;
	// Tableau contenent le nb de minute et 
	// seconde
	private int[] minuteAndSecond;

	/**
	 * Constructeur par d�faut,
	 * initialise tout mes attributs
	 */
	public GameModel() {

		listNumber = new ArrayList<Integer>();
		etatSelection = new Integer[12];
		initArray(etatSelection);
		regroupement = new ArrayList<Integer>();
		initTimer();
		countReset = 0;
		minuteAndSecond = new int[2];
	}


	/**
	 * The methode return a random number
	 * between min and max. Max must be greater 
	 * then min.
	 * @param min enti� minimum � avoir
	 * @param max enti� maximum � avoir
	 * @return
	 */
	public int getRandom(int min, int max) {

		// V�rifie que le min est plus petit que le max
		if(min >= max) {

			throw new IllegalArgumentException("Le minimum est plus grand ou �gal au maxium !");
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
	 * M�thode qui initialise un tableau � 0 
	 * pour toute ses cases
	 * @param Integer[] re�oit un tableau d'entier
	 */
	private void initArray(Integer[] arraySelection) {

		for(int i = 0; i < arraySelection.length; i++) {

			arraySelection[i] = 0;
		}
	}


	/**
	 * M�thode qui g�n�re une nouvelle 
	 * partie g�n�rant une liste
	 * de nombre � afficher. Supprime
	 * partie pr�c�dente si existante.
	 */
	public void generateGame() {

		// Nombre al�atoire de regroupement � avoir
		int nbRegroupements = getRandom(3,6);
		// R�initialise les composantes du jeux
		suppressionPartie();

		for(int i = 0; i < nbRegroupements; i++) {

			// Condition des 70%
			if(getRandom(0,100) <= 70) {

				this.listNumber.add(getRandom(1,9));
			}else {

				this.listNumber.add(getRandom(10,99));
			}
		}

		System.out.println(getDigits() + " Mode Training");
	}



	/**
	 * M�thode qui r�initialise tout les �l�ments
	 * du jeu et supprime la partie en cours 
	 * d�finitivement
	 */
	public void suppressionPartie() {

		// R�initialise tout les attributs du jeux
		listNumber.removeAll(listNumber);
		initArray(etatSelection);
		regroupement.removeAll(regroupement);
		countReset = 0; 
		resetTimer();

	}

	/**
	 * M�thode qui r�initialise tout les �l�ments
	 * du jeu en gardant la m�me partie
	 */
	public void reinitialisationPartie() {

		// R�initialise tout les attributs du jeux
		initArray(etatSelection);
		regroupement.removeAll(regroupement);
		countReset++;
	}

	/**
	 * Fonctions qui retourne une chaine
	 * de caract�res de tout les g�n�r�es 
	 * dans GenerateGame. 
	 * @return String de tout les chiffres affich�
	 */
	public String getDigits() {

		// Variable o� sont concat�n� les chiffres
		String allNumbers = "";

		//Loop pour cr�er le String retour.
		for(int a :this.listNumber) {

			allNumbers += a;
		}

		return allNumbers;
	}

	/**
	 * M�thode qui v�rifie si une s�lection
	 * est valide ou non.
	 * @param ps1 entier qui d�fini le rectangle initial
	 * @param ps2 entier qui d�fini le rectangle de fin de selection
	 * @return true ou false selon si la selection est valide ou non
	 */
	public boolean selectionValide(int ps1, int ps2) {

		// Instanci� � false
		boolean validation = false;

		// V�rifie que la selection se fait ur au maximum 2 rectangles.
		if(ps1 == (ps2 - 1) || ps1 == (ps2 + 1) || ps1 == ps2) {

			validation = true;
		}

		return validation;
	}

	/**
	 * M�thode qui permet de changer l'�tat du rectangle
	 * s�lectionn�. 1 � 2 rectangles simultan�ment.
	 * @param ps1 entier qui d�fini rectangle initial
	 * @param ps2 entier qui d�fini le rectangle de fin de s�lection
	 */
	public void changeState(int ps1, int ps2) {

		// Tout d�pendemment si c'est 1 rectangles ou 2
		// les proc�dures sont diff�rentes.
		if(ps1 == ps2) {

			etatSelection[ps1 - 1] = 1;
			updateState(etatSelection[ps1 - 1],ps1);

			// Les proc�dures suivante permmettent de g�rer
			// si la s�lection est fait de droite � gauche
			// et vice-versa
		}else if(ps1 == ps2 - 1) {

			etatSelection[ps1 - 1] = 2;
			updateState(etatSelection[ps1 - 1], ps1);
			etatSelection[ps2 - 1] = 3;
			updateState(etatSelection[ps2 - 1], ps2);

		}else {

			etatSelection[ps1 - 1] = 3;
			updateState(etatSelection[ps1 - 1], ps1);
			etatSelection[ps2 - 1] = 2;
			updateState(etatSelection[ps2 - 1], ps2);
		}

		updateRegroupement();
	}

	/**
	 * M�thode qui met � jour les regroupement
	 * selon l'�tat de s�lection des rectangles.
	 */
	private void updateRegroupement() {

		regroupement.removeAll(regroupement);
		String allNumbers = getDigits();
		int compteur = 0;

		// Traverse tout le tableau de s�lection
		while(compteur < etatSelection.length ) {

			if(etatSelection[compteur] == 1) {

				regroupement.add(Integer.parseInt(allNumbers.substring(compteur, compteur + 1)));
			}else if(etatSelection[compteur] == 2) {

				regroupement.add(Integer.parseInt(allNumbers.substring(compteur, compteur + 2)));

			}

			compteur++;
		}
	}

	/**
	 * Met � jour le tableau d'�tat de
	 * s�lection des rectangles. 
	 * @param state �tat du rectangles
	 * @param ps position du rectangle
	 */
	private void updateState(Integer state, int ps) {

		/**
		 * Cette suite de "if" est un algorithme
		 * qui permet de g�rer le tableau d'�tat de s�lection.
		 * Si un �tat est chang�, il va s'occuper de mettre � jour
		 * tout ses �tats voisin pour que l'information reste "lisible".
		 * Un genre d'op�ration en chaine, selon le changement d'une case
		 * du tableau et de ses voisins directe.
		 */
		if(state == 1 ) {

			if(ps > 1) {

				if(etatSelection[ps - 2] == 2 ) {

					etatSelection[ps - 2] = 1;
				}
			}
			if(etatSelection[ps] == 3){

				etatSelection[ps] = 1;
			}
		}else if(state == 2) {

			if(ps > 1) {

				if(etatSelection[ps - 2] == 2) {

					etatSelection[ps - 2] = 1;
					etatSelection[ps] = 3;
				}
			}else {

				etatSelection[ps] = 3;
			}
		}else if(state == 3) {

			if(etatSelection[ps - 2] != 3) {

				etatSelection[ps - 2] = 2;
			}else if(etatSelection[ps] == 3){

				etatSelection[ps] = 1;
			}else {

				etatSelection[ps - 2] = 2;
				etatSelection[ps - 3] = 1;
			}
			if(etatSelection[ps ] == 3) {

				etatSelection[ps ] = 1;
			}
		}
	}

	/**
	 * Initie le Timer du jeux selon
	 * un saut de 1000ms et en instan�iant son
	 * listener.
	 */
	private void initTimer() {
		timer = new Timer(1000, new ActionListener() {



			/**
			 * Listener qui � chaque seconde, met � jour
			 * le tableau du nb de minute et de seconde.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {

				minuteAndSecond[1]++;

				if(minuteAndSecond[1] < 10) {

					timeFormat = ("[Time] 0" + minuteAndSecond[0] + ":0" + minuteAndSecond[1]);
				}else {

					timeFormat = ("[Time] 0" + minuteAndSecond[0] + ":" + minuteAndSecond[1]);
				}

				if(minuteAndSecond[1] == 60) {

					minuteAndSecond[1] = 0;
					minuteAndSecond[0]++;
				}


			}
		});

		timer.setDelay(1000);
		timer.start();
	}


	/**
	 * Fonctions qui retourne la somme 
	 * des cases s�lectionn� selon un tableau
	 * qui retient tout les regroupements cr��s
	 * @return entier repr�sentant la somme des regroupements.
	 */
	public int getSum(){

		// Somme initialis�e � z�ro
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
	 * l'objectif de la partie pr�sente.
	 * G�n�rer une nouvelle partie retourne
	 * un nouvelle objectif
	 * @return entier repr�sentant le nouvelle objectif
	 */
	public int getGoal() {

		//Objectif initialis� � z�ro
		int goal = 0;

		//Addition tout les nombres
		// pr�sent dans le tableau 
		for(int a: listNumber) {

			goal += a;
		}

		return goal;

	}

	/**
	 * Accesseur
	 * @return un string avec un format
	 * de temp.
	 */
	public String getTimeFormat() {

		return timeFormat;
	}

	/**
	 * Accesseur
	 * @return retourne un entier
	 * qui repr�sente le nbr de reset pour
	 * 1 partie.
	 */
	public int getCountReset() {

		return countReset;
	}

	/**
	 * m�thode qui arr�te le timer.
	 */
	public void pauseTimer() {

		timer.stop();
	}

	/**
	 * Active le timer, si il est arr�t�.
	 */
	public void startTimer() {

		timer.start();
	}

	/**
	 * M�thode qui r�initialise le 
	 * chronom�tre en mettant � 0 le
	 * tableau minuteAndSecond
	 */
	public void resetTimer() {

		minuteAndSecond[0] = 0;
		minuteAndSecond[1] = 0;
	}
}
