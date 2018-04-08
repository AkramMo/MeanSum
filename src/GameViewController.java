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
	// Affiche la somme à obtenir
	private JLabel goal;
	//Label qui affiche le chronomètre
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

			// Variable représentant les rectangles sélectionnés
			int ps1;
			int ps2;

			// Méthode qui est appelé quand il y a un clique de la souris.
			public void mousePressed(MouseEvent e) {

				updateSpecification();
				// Identifie quel rectangle a été sélectionné quand la souris est pressée
				int identifiantRectangle = specificTile.getRectanglePosition(e.getX(), e.getY());

				// Si le rectangle existe il attribut la valeur à la variable ps1 
				if(identifiantRectangle != -1) {

					ps1 = identifiantRectangle;

				}

			}

			// Méthode appelé quand la souris est libérée
			public void mouseReleased(MouseEvent e) {

				updateSpecification();
				// Identifie quel est le rectangles où la souris a été libéré
				int identifiantRectangle = specificTile.getRectanglePosition(e.getX(), e.getY());

				// vérifie si le rectangles existes
				if(identifiantRectangle != -1) {

					// attributs la position à la variable ps2
					ps2 = identifiantRectangle;

					// vérifie que la selection est valides
					if(specificGame.selectionValide(ps1, ps2)){

						// change l'état de mes cases
						specificGame.changeState(ps1, ps2);

						// Met à jour la somme affiché 
						updateSum();

						//rafraichie la fenêtre
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
	//  Méthode qui gère tout les listeners
	private void setupListeners() {		


		specificTile = tilePanel;
		specificGame = gameModel;

		// Ajout de listener au rectangle supérieur de la fenêtre
		tilePanel.addMouseListener(tileMouseAdapter());

		tilePanelArcade.addMouseListener(tileMouseAdapter());

		// Listener ajouté au bouton next
		nextButton.addMouseListener(new MouseAdapter() {

			// Quand le bouton next est cliqué
			@Override
			public void mouseClicked(MouseEvent e) {

				updateSpecification();

				/// génère une nouvelle partie et rafraichie la fenêtre.
				specificGame.generateGame();
				updateReset();
				updateSum();
				updateGoal();
				specificTile.repaint();

			}

		});

		// Listener ajouté au bouton reset
		resetButton.addMouseListener(new MouseAdapter() {

			// Quand le bouton suivant est cliqué
			@Override
			public void mouseClicked(MouseEvent e) {

				updateSpecification();

				if(specificGame == arcadeModel && specificTile.getEtatPartie() != EtatPartie.GAGNÉE
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

			// Quand le bouton suivant est cliqué
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

			// Quand le bouton suivant est cliqué
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
	 * ajoute les composantes à mon JPanel. 
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

		resetButton = new JButton("Réinitialiser");
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
	 * Méthode qui modifie le component currentSum
	 * pour ajouté la nouvelle somme lors de nouvelle
	 * sélection de chiffres. 
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
	 * Méthode qui met à jour l'objectif(JLabel)
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
