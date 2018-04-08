import java.awt.FlowLayout;
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

	private ArcadeModel arcadeModel;
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
	private JLabel currentReset;
	/**
	 * A single tile panel displays all the tiles of the game
	 */
	private TilePanel tilePanel;

	private TilePanel tilePanelArcade;

	private TilePanel specificTile; 
	private GameModel specificGame;

	private MonMenuBar menuBar;

	private int gameMode;
	private JPanel allLabel;



	private MouseAdapter tileMouseAdapter() {

		return new MouseAdapter() {

			// Variable repr�sentant les rectangles s�lectionn�s
			int ps1;
			int ps2;

			// M�thode qui est appel� quand il y a un clique de la souris.
			public void mousePressed(MouseEvent e) {

				updateSpecification();
				// Identifie quel rectangle a �t� s�lectionn� quand la souris est press�e
				int identifiantRectangle = specificTile.getRectanglePosition(e.getX(), e.getY());

				// Si le rectangle existe il attribut la valeur � la variable ps1 
				if(identifiantRectangle != -1) {

					ps1 = identifiantRectangle;

				}

			}

			// M�thode appel� quand la souris est lib�r�e
			public void mouseReleased(MouseEvent e) {

				updateSpecification();
				// Identifie quel est le rectangles o� la souris a �t� lib�r�
				int identifiantRectangle = specificTile.getRectanglePosition(e.getX(), e.getY());

				// v�rifie si le rectangles existes
				if(identifiantRectangle != -1) {

					// attributs la position � la variable ps2
					ps2 = identifiantRectangle;

					// v�rifie que la selection est valides
					if(specificGame.selectionValide(ps1, ps2)){

						// change l'�tat de mes cases
						specificGame.changeState(ps1, ps2);

						// Met � jour la somme affich� 
						updateSum();

						//rafraichie la fen�tre
						specificTile.repaint();
					}

				}
			}
		};

	}

	private void updateSpecification() {

		if(gameMode == 1) {

			specificTile = tilePanel;
			specificGame = gameModel;

		}else {

			specificTile = tilePanelArcade;
			specificGame = arcadeModel;
		}
	}
	//  M�thode qui g�re tout les listeners
	private void setupListeners() {		


		specificTile = tilePanel;
		specificGame = gameModel;

		// Ajout de listener au rectangle sup�rieur de la fen�tre
		tilePanel.addMouseListener(tileMouseAdapter());

		tilePanelArcade.addMouseListener(tileMouseAdapter());

		// Listener ajout� au bouton next
		nextButton.addMouseListener(new MouseAdapter() {

			// Quand le bouton next est cliqu�
			@Override
			public void mouseClicked(MouseEvent e) {

				updateSpecification();

				/// g�n�re une nouvelle partie et rafraichie la fen�tre.
				specificGame.generateGame();
				updateReset();
				updateSum();
				updateGoal();
				specificTile.repaint();

			}

		});

		// Listener ajout� au bouton reset
		resetButton.addMouseListener(new MouseAdapter() {

			// Quand le bouton suivant est cliqu�
			@Override
			public void mouseClicked(MouseEvent e) {

				updateSpecification();

				if(specificGame == arcadeModel && specificTile.getEtatPartie() != EtatPartie.GAGN�E
						|| specificGame == gameModel ) {

					specificGame.reinitialisationPartie();

				}	
				updateReset();
				updateSum();
				updateGoal();
				specificTile.repaint();
			}

		});

		menuBar.getArcadeButton().addMouseListener(new MouseAdapter() {

			// Quand le bouton suivant est cliqu�
			@Override
			public void mouseClicked(MouseEvent e) {

				gameMode = 2;
				updateReset();
				updateSum();
				updateGoal();
				tilePanelArcade.setVisible(true);
				tilePanel.setVisible(false);


			}

		});

		menuBar.getTrainingButton().addMouseListener(new MouseAdapter() {

			// Quand le bouton suivant est cliqu�
			@Override
			public void mouseClicked(MouseEvent e) {

				gameMode = 1;
				updateReset();
				updateSum();
				updateGoal();
				tilePanelArcade.setVisible(false);
				tilePanel.setVisible(true);


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
		tilePanel = new TilePanel(gameModel);
		arcadeModel = new ArcadeModel();
		tilePanelArcade = new TilePanel(arcadeModel);
		gameMode = 1;

		initLabel();
		initTimer();

		// Ajoutes toute composantes au JPanel
		add(menuBar);
		add(tilePanel);
		add(tilePanelArcade);
		tilePanelArcade.setVisible(false);
		add(allLabel);

		// Intialise tout mes listeners 
		setupListeners();
	}

	private void initLabel() {

		resetButton = new JButton("R�initialiser");
		nextButton = new JButton("Suivant");
		currentSum = new JLabel("[Sum] " + gameModel.getSum());	
		currentReset = new JLabel("[Resets] " + gameModel.getCountReset());
		goal = new JLabel("[Goal] " + gameModel.getGoal());
		labelTime = new JLabel("[Time] 00:00");
		allLabel = new JPanel();
		menuBar = new MonMenuBar(this);


		allLabel.add(goal);
		allLabel.add(currentSum);
		allLabel.add(labelTime);
		allLabel.add(currentReset);
		allLabel.add(nextButton);
		allLabel.add(resetButton);


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

					labelTime.setText("[Time] 0" + minuteTime + ":0" + secondTime);
				}else {

					labelTime.setText("[Time] 0" + minuteTime + ":" + secondTime);
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
		if(gameMode == 1) {

			currentSum.setText("[Sum]" + gameModel.getSum());

		}else if(gameMode == 2) {

			currentSum.setText("[Sum]" + arcadeModel.getSum());

		}
	}

	/**
	 * M�thode qui met � jour l'objectif(JLabel)
	 * lors d'une nouvelle partie. 
	 */
	private void updateGoal() {



		if(gameMode == 1) {

			goal.setText("[Goal] " + gameModel.getGoal());

		}else if(gameMode == 2) {

			goal.setText("[Goal] " + arcadeModel.getGoal());

		}
	}
	
	private void updateReset() {
		
		if(gameMode == 1) {

			currentReset.setText("[Resets] " + gameModel.getCountReset());

		}else if(gameMode == 2) {

			currentReset.setText("[Resets] " + arcadeModel.getCountReset());

		}
	}



}
