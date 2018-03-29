import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
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
	// Label qui montre la somme total.
	private JLabel currentSum;

	/**
	 * A single tile panel displays all the tiles of the game
	 */
	private TilePanel tilePanel;

	// TODO Add all the other required UI components (labels, buttons, etc.)

	//  M�thode qui g�re tout les listeners
	private void setupListeners() {		
		// TODO Set up the required listeners on the UI components (button clicks, etc.)

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
						showSum();

						//rafraichie la fen�tre
						tilePanel.repaint();
					}

				}
			}
		});


		// Listener ajout� au boutton next
		nextButton.addMouseListener(new MouseAdapter() {

			// Quand le bouton next est cliqu�
			@Override
			public void mouseClicked(MouseEvent e) {

				/// g�n�re une nouvelle partie et rafraichie la fen�tre.
				gameModel.generateGame();
				tilePanel.repaint();

			}

		});
	}

	/**
	 * Constructeur qui instancie mes variable et 
	 * ajoute les composantes � mon JPanel
	 */
	public GameViewController() {
		// TODO Initialize our game model by constructing an instance

		// The layout defines how components are displayed
		// (here, stacked along the Y axis)
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Intialisation de tout mes attributs
		this.gameModel = new GameModel();
		this.nextButton = new JButton("Suivant");
		this.currentSum = new JLabel("Somme :");
		this.tilePanel = new TilePanel(gameModel);

		// Ajoutes toute composantes au JPanel
		this.add(tilePanel);
		this.add(currentSum);
		this.add(nextButton);
		// TODO Initialize all the UI components

		// Intialise tout mes listeners 
		setupListeners();
	}

	/**
	 * M�thode qui modifie le component currentSum
	 * pour ajout� la nouvelle somme lors de nouvelle
	 * s�lection de chiffres. 
	 */
	public void showSum() {

		// Modification du component.
		currentSum.setText("Sommes :" + gameModel.getSum());
	}


}
