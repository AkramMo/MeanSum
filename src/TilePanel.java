import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 * The tile panel displays all the tiles (one per digit) of the game.
 *
 */
public class TilePanel extends JPanel {

	/**
	 * The tile panel object holds a reference to the game model to
	 * request information to display (view) and to modify its state (controller)
	 */
	private GameModel gameModelHandle;

	/**
	 * A table of colours that can be used to draw the tiles
	 */
	private Color[] colours;

	/**
	 * Initialize an array of pre-set colours.
	 * The colours are picked to ensure readability and avoid confusion.
	 */
	private void initializeColours() {
		// Some tile colours in the '0xRRGGBB' format
		String[] tileColourCodes = new String[] {
				"0x89CFF0", "0xF4C2C2", "0xFFBF00", "0xFBCEB1",
				"0x6495ED", "0x9BDDFF", "0xFBEC5D",	"0xFF7F50",
				"0x00FFFF", "0x98777B", "0x99BADD", "0x654321"
		};

		// Allocate and fill our colour array with the colour codes
		colours = new Color[tileColourCodes.length];
		for (int i = 0; i < colours.length; ++i)
			colours[i] = Color.decode(tileColourCodes[i]);
	}

	/**
	 * M�thode qui permet dessiner/peindre sur 
	 * sont JFrame
	 * @param g encapsule tout les "desseins"
	 * avant de le rendu.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		// M�thodes qui initialise le paintcomponent du JPanel
		super.paintComponent(g);

		// Nombre de rectangles a afficher
		int nbrRectangle = gameModelHandle.getDigits().length();
		// Positions des chiffres sur les rectangles
		int stringPosition = 0;
		// Chaine de charact�re de tout les chiffres
		String nbrString = gameModelHandle.getDigits();
		// tableau de L'etat de selection de chaque rectangles
		Integer[] etatSelection = gameModelHandle.getEtatSelection();

		// Attribut une police aux textes 
		g.setFont(new Font("Arial", Font.PLAIN, 40));

		// Dessine les rectangles sur le paintComponent
		generateRectangle(nbrRectangle, g, stringPosition, nbrString);

		// Dessine les cases s�lectionn�es
		generateSelection(etatSelection, g, stringPosition, nbrString, nbrRectangle);

		//v�rifie si la partie est termin�
		if(verifyEndGame(etatSelection)) {

			//V�rife si elle est gagn�e ou perdu.
			winOrFail(etatSelection, nbrRectangle, g, stringPosition, nbrString);
		}
	}

	/**
	 * M�thode qui si une partie � atteind
	 * sa fin.
	 * @param etatSelection tableau d'entier des �tats.
	 * @return true ou false selon si la partie est fini ou pas
	 */
	private boolean verifyEndGame(Integer[] etatSelection) {

		// Valeur retour mis � false quand instanci�
		boolean endGame = false;
		// Compteur permet de v�rifi� le nbr de
		// cases s�lectionn�es.
		int compteur = 0;

		// Boucle qui traverse le tableau d'�tat
		for(int i = 0; i < etatSelection.length; i++) {

			// Si case s�lectionn� incr�ment compteur
			if(etatSelection[i] != 0) {

				compteur++;
			}
		}

		// Si le nbr de case s�lectionn� �quivaut aux nbrs de 
		// rectangles affich� mettre valeur retour � true.
		if(compteur == gameModelHandle.getDigits().length()) {

			endGame = true;
		}

		return endGame;
	}

	/**
	 * M�thode qui v�rife si une partie
	 * confirm�e comme �tant termin� est
	 * gagn�e ou perdu
	 * @param etatSelection tableau des �tats
	 * @param nbrRectangle nbr de Rectangles(entier)
	 * @param g de type Graphics, o� sont fait les "dessein"
	 * @param stringPosition entier qui d�fini la position des chiffres
	 * @param nbrString nbr de chiffre � dessiner
	 * @return
	 */
	private boolean winOrFail(Integer[] etatSelection, int nbrRectangle, Graphics g,
			int stringPosition, String nbrString) {

		// Valeur retour instanci� � true
		boolean winOrFail = true;
		Color green = Color.GREEN;
		Color red = Color.RED;

		// Boucle qui v�rife que la liste des regroupements
		// est bien celle voulu pour gagner la partie.
		for(int a: gameModelHandle.getListNumber()) {

			if(!gameModelHandle.getRegroupement().contains(a)) {

				winOrFail = false;
			}
		}

		// si true, dessine rectangle en vert sinon rouge
		// pour partie perdu.
		if(winOrFail) {

			drawColorOnRectangle(nbrRectangle, g, stringPosition, nbrString, green);
		}else {

			drawColorOnRectangle(nbrRectangle, g, stringPosition, nbrString, red);
		}

		return winOrFail;
	}


	/**
	 * M�thode qui dessine tout les rectangles selon la couleur
	 * rentr� en param�tre en gardant les chiffres inscrit 
	 * et leurs nombres. 
	 * @param nbrRectangle nbr de rectangle inscrit
	 * @param g variable de type Graphic o� sont dessiner les rectangles
	 * @param stringPosition postion (int) des chiffres dessin�s
	 * @param nbrString nombre de chiffre � dessiner. Entier.
	 * @param color couleur des rectangles dessin�s
	 */
	private void drawColorOnRectangle(int nbrRectangle, Graphics g,
			int stringPosition, String nbrString, Color color) {

		// Boucle pour dessiner les cases n�cessaire
		for(int i = 0; i < nbrRectangle; i++) {

			// Set la couleurs des rectangle
			g.setColor(color);

			// dessines un rectangle arrondie
			g.fillRoundRect((this.getWidth()/nbrRectangle)*i, 5, 
					this.getWidth()/nbrRectangle - 10, 128, 30, 30);

			// Couleur du prochain desseins
			g.setColor(Color.BLACK);

			// position du texte
			stringPosition = (this.getWidth()/nbrRectangle)*i+((this.getWidth()/nbrRectangle)/2) - 12;

			// Dessine un chiffre selon la chaine de charact�res
			g.drawString(nbrString.substring(i, i+1), stringPosition, 76);
		}
	}

	/**
	 * M�thode qui g�n�re les rectangles n�cessaire lors 
	 * d'une nouvelle partie
	 * @param nbrRectangle entier repr�sent le nbr de case
	 * @param g variable de type Graphics o� sont dessin� les rectangles
	 * @param stringPosition  
	 * @param nbrString
	 */
	private void generateRectangle(int nbrRectangle, Graphics g, int stringPosition, String nbrString) {

		// Boucle pour dessiner les cases n�cessaire
		for(int i = 0; i < nbrRectangle; i++) {


			// Set la couleurs des rectangle
			g.setColor(Color.white);

			// dessines un rectangle arrondie
			g.fillRoundRect((this.getWidth()/nbrRectangle)*i, 5, this.getWidth()/nbrRectangle - 10, 128, 30, 30);

			// Couleur du prochain desseins
			g.setColor(Color.BLACK);

			// position du texte
			stringPosition = (this.getWidth()/nbrRectangle)*i+((this.getWidth()/nbrRectangle)/2) - 12;

			// Dessine un chiffre selon la chaine de charact�res
			g.drawString(nbrString.substring(i, i+1), stringPosition, 76);

		}
	}

	/**
	 * M�thode qui g�n�re les rectangles lors d'une nouvelle
	 * s�lection. Accorde une couleur associ� � leur regroupement
	 * respectif
	 * @param etatSelection tableau des �tats de s�lection
	 * @param g variable de type Graphics qui permet de dessin� sur le Jpanel
	 * @param stringPosition position des chiffres, un entier
	 * @param nbrString chaine des chiffres � dessin�
	 * @param nbrRectangle nombre de rectangle � dessin�er(entier)
	 */
	private void generateSelection(Integer[] etatSelection, Graphics g, int stringPosition, 
			String nbrString, int nbrRectangle) {

		// V�rifie si il y a des regroupements d�j� existant
		if(!gameModelHandle.getRegroupement().isEmpty()) {

			// Boucle pour modifier la couleurs de tout les regroupements
			for(int i = 0; i < etatSelection.length; i++) {

				// Selon l'�tat , les rectangles changes de couleurs
				if(etatSelection[i] == 1) {


					drawSingleSelection(g, nbrRectangle, stringPosition, i, nbrString);

					// �quivalent que plus haut, mais pour un �tat o� il y a 
					// 2 rectangles s�lectionn�
				}else if(etatSelection[i] == 2) {

					drawDoubleSelection(g, nbrRectangle, stringPosition, nbrString, i);
				}
			}
		}
	}

	/**
	 * M�thode qui met � jour les rectangles du jeux
	 * quand il y a la s�lection d'une case
	 * @param g variable type Graphics
	 * @param nbrRectangle entier repr�sent nbr de rectangles dessin�
	 * @param stringPosition position int des chiffres
	 * @param i compteurs int
	 * @param nbrString string de tout les chiffres
	 */
	private void drawSingleSelection(Graphics g, int nbrRectangle, int stringPosition, int i, String nbrString) {

		// Couleurs pour le rectangles et dessine le prochaine rectangles 
		// et son chiffre
		g.setColor(colours[i]);
		g.fillRoundRect((this.getWidth()/nbrRectangle)*i, 5, this.getWidth()/nbrRectangle - 10, 128, 30, 30);
		g.setColor(Color.black);
		stringPosition = (this.getWidth()/nbrRectangle)*i+((this.getWidth()/nbrRectangle)/2) - 12;
		g.drawString(nbrString.substring(i, i+1), stringPosition, 76);

	}

	/**
	 * M�thode qui met � jour les rectangles du jeux
	 * quand il y a une double s�lection de case.
	 * @param g variable de type Graphics
	 * @param nbrRectangle nombre de rectangles dessin�, int
	 * @param stringPosition position des chiffre, int
	 * @param nbrString string de tout les chiffres
	 * @param i compteur int
	 */
	private void drawDoubleSelection(Graphics g, int nbrRectangle,
			int stringPosition, String nbrString, int i) {

		// Set couleur du regroupement et dessine ceux-ci
		g.setColor(colours[i]);
		g.fillRoundRect((this.getWidth()/nbrRectangle)*i, 5, 
				this.getWidth()/nbrRectangle - 10, 128, 30, 30);
		g.fillRoundRect((this.getWidth()/nbrRectangle)*(i + 1), 5, 
				this.getWidth()/nbrRectangle - 10, 128, 30, 30);

		// set la couleur de la police, sa position et sa grosseur ds chaque rectangles.
		g.setColor(Color.black);
		stringPosition = (this.getWidth()/nbrRectangle)*i+((this.getWidth()/nbrRectangle)/2) - 12;
		g.drawString(nbrString.substring(i, i+1), stringPosition, 76);
		stringPosition = (this.getWidth()/nbrRectangle)*(i+1)+((this.getWidth()/nbrRectangle)/2) - 12;
		g.drawString(nbrString.substring(i+1, i+2), stringPosition, 76);
	}

	/**
	 * Fonction qui permet d'identifier le num�ro
	 * du rectangles selectionn� selon une position
	 * @param x position de la souris en x ( int )
	 * @param y position de la souris en y ( int )
	 * @return retourne num�ro du rectangle ou -1 si rien trouv� 
	 */
	public int getRectanglePosition(int x, int y) {

		// Nombre de rectangles pr�sent, leurs tailles
		int nbrRectangle = gameModelHandle.getDigits().length();
		int sizeRectangle = this.getWidth()/nbrRectangle;
		// Variable pour arr�ter la boucle 
		boolean postionVerified = false;
		// compteur du nombre de rectangles
		int compteur = 1;
		// Num�ro de la case identifi�e
		int caseIdentifiant = -1;

		// V�rifie que la position en y est la bonne
		if(y <= 128 && y >= 5) {

			// Boucle qui v�rifie que la position (x,y) se trouve bien au sein d'un rectangles
			do {

				if( x < (sizeRectangle*compteur - 10) && x > (sizeRectangle*(compteur - 1))){

					caseIdentifiant = compteur;
					postionVerified = true;
				}else {

					compteur++;
				}
				// Arr�te si une position est trouv� o� que le compteur d�passe le nbr de rectangles
			}while(!postionVerified && compteur <= nbrRectangle );
		}

		return caseIdentifiant;
	}

	/**
	 * Constructeur du panel
	 * @param gameModel model et condition du jeux
	 */
	public TilePanel(GameModel gameModel) {
		if (gameModel == null)
			throw new IllegalArgumentException("Should provide a valid instance of GameModel!");
		gameModelHandle = gameModel;
		gameModel.generateGame();
		// Initialize our array of tile colours
		initializeColours();
	}
}