import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

/**
 * The view-controller class handles the display (tiles, buttons, etc.)
 * and the user input (actions from selections, clicks, etc.).
 *
 */
public class GameViewController extends JPanel {

	/**
	 * Instance of the game (logic, state, etc.)
	 */
	private GameModel gameModel;

	// Bouton Next 
	private JButton nextButton;
	// Bouton Reset de partie
	private JButton resetButton;

	// Label qui affiche la somme total.
	private JLabel currentSum;
	// Affiche la somme � obtenir
	private JLabel goal;
	
	private JLabel timerLabel;
	
	private JMenuBar menuBar;
	/**
	 * A single tile panel displays all the tiles of the game
	 */
	private TilePanel tilePanel;

	//  M�thode qui g�re tout les listeners
	private void setupListeners() {		

		// Ajout de listener au rectangle sup�rieur de la fen�tre
		tilePanel.addMouseListener(new MouseAdapter() {

			// Variable repr�sentant les rectangles s�lectionn�s
			int ps1;
			int ps2;


			// M�thode qui est appel� quand il y a un clique de la souris.
			public void mousePressed(MouseEvent e) {

				// Identifie quel rectangle a �t� s�lectionn� quand la souris est press�e
				int identifiantRectangle = tilePanel.getRectanglePosition(e.getX(), e.getY());

				// Si le rectangle existe il attribut la valeur � la variable ps1 
				if(identifiantRectangle != -1) {

					ps1 = identifiantRectangle;

				}

			}

			// M�thode appel� quand la souris est lib�r�e
			public void mouseReleased(MouseEvent e) {

				// Identifie quel est le rectangles o� la souris a �t� lib�r�
				int identifiantRectangle = tilePanel.getRectanglePosition(e.getX(), e.getY());

				// v�rifie si le rectangles existes
				if(identifiantRectangle != -1) {

					// attributs la position � la variable ps2
					ps2 = identifiantRectangle;

					// v�rifie que la selection est valides
					if(gameModel.selectionValide(ps1, ps2)){

						// change l'�tat de mes cases
						gameModel.changeState(ps1, ps2);

						// Met � jour la somme affich� 
						updateSum();

						//rafraichie la fen�tre
						tilePanel.repaint();
					}

				}
			}
		});


		// Listener ajout� au bouton next
		nextButton.addMouseListener(new MouseAdapter() {

			// Quand le bouton next est cliqu�
			@Override
			public void mouseClicked(MouseEvent e) {

				/// g�n�re une nouvelle partie et rafraichie la fen�tre.
				gameModel.generateGame();
				
				updateSum();
				updateGoal();
				tilePanel.repaint();

			}

		});

		// Listener ajout� au bouton reset
		resetButton.addMouseListener(new MouseAdapter() {

			// Quand le bouton suivant est cliqu�
			@Override
			public void mouseClicked(MouseEvent e) {

				/// M�me partie, mais remet le tout � z�ro.
				gameModel.reinitialisationPartie();
				updateSum();
				updateGoal();
				tilePanel.repaint();

			}

		});
	}

	/**
	 * Constructeur qui instancie mes variables et 
	 * ajoute les composantes � mon JPanel. 
	 * Il ajoute aussi des listeners aux
	 * composantes qui en ont besoin.
	 */
	public GameViewController() {

		// The layout defines how components are displayed
		// (here, stacked along the Y axis)
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Intialisation de tout mes attributs
		gameModel = new GameModel();
		resetButton = new JButton("R�initialiser");
		nextButton = new JButton("Suivant");
		currentSum = new JLabel("Somme : 0");	
		tilePanel = new TilePanel(gameModel);
		goal = new JLabel("Objectif : " + gameModel.getGoal());
		timerLabel = new JLabel("Chronom�tre : " );
		
		
		// Ajoutes toute composantes au JPanel
		this.add(tilePanel);
		this.add(goal);
		this.add(currentSum);
		this.add(nextButton);
		this.add(resetButton);<>

		// Intialise tout mes listeners 
		setupListeners();
	}

	/**
	 * M�thode qui modifie le component currentSum
	 * pour ajout� la nouvelle somme lors de nouvelle
	 * s�lection de chiffres. 
	 */
	private void updateSum() {

		// Modification du component.
		currentSum.setText("Somme : " + gameModel.getSum());
	}

	/**
	 * M�thode qui met � jour l'objectif(JLabel)
	 * lors d'une nouvelle partie. 
	 */
	private void updateGoal() {

		goal.setText("Objectif : " + gameModel.getGoal());

	}

	private void updateTimer()
	{
		
		timerLabel.setText("Chronom�tre : ");
		
	}



}
