
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

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


	private Timer timer;
	// Bouton Next 
	private JButton nextButton;
	// Bouton Reset de partie
	private JButton resetButton;

	// Label qui affiche la somme total.
	private JLabel currentSum;
	// Affiche la somme � obtenir
	private JLabel goal;
	//Label qui affiche le chronom�tre
	private JLabel labelTime;
	/**
	 * A single tile panel displays all the tiles of the game
	 */
	private TilePanel tilePanel;

	private MonMenuBar menuBar;

	private JPanel allLabel;

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

		menuBar.getArcadeButton().addMouseListener(new MouseAdapter() {

			// Quand le bouton suivant est cliqu�
			@Override
			public void mouseClicked(MouseEvent e) {


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
		initTimer();
		labelTime = new JLabel("[ Chronom�tre ] : 00m 00s ");
		allLabel = new JPanel();
		menuBar = new MonMenuBar(this);


		// Ajoutes toute composantes au JPanel
		this.add(menuBar);
		this.add(tilePanel);
		allLabel.add(goal);
		allLabel.add(currentSum);
		allLabel.add(labelTime);
		allLabel.add(nextButton);
		allLabel.add(resetButton);
		this.add(allLabel);


		// Intialise tout mes listeners 
		setupListeners();
	}

	private void initTimer() {
		// TODO Auto-generated method stub
		timer = new Timer(1000, new ActionListener() {

			int secondTime = 0;
			int minuteTime = 0;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				secondTime++;

				if(secondTime < 10) {

					labelTime.setText("[ Chronom�tre ] : 0" + minuteTime + "m 0" + secondTime + "s");
				}else {

					labelTime.setText("[ Chronom�tre ] : 0" + minuteTime + "m " + secondTime + "s");
				}

				if(secondTime == 60) {

					secondTime = 0;
					minuteTime++;
				}
			}
		});

		timer.setDelay(1000);
		timer.start();
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



}
