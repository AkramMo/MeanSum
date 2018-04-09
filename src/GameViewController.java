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
	/**
	 * Instance du mode Arcade(logic,state,etc.)
	 */
	private ArcadeModel arcadeModel;
	// timer de la vue.
	private Timer timer;
	// Bouton Next qui change de partie 
	private JButton nextButton;
	// Bouton Reset de partie
	private JButton resetButton;
	// Recommence au niveau 1 du mode Arcade
	private JButton restartButton;
	// Label qui affiche la somme total.
	private JLabel currentSum;
	// Affiche la somme à obtenir
	private JLabel goal;
	//Label qui affiche le chronomètre
	private JLabel labelTime;
	// Affiche nombre de reset d'une partie
	private JLabel currentReset;
	// Affiche le niveau du joueur en mode Arcade
	private JLabel levelLabel;
	/**
	 * A single tile panel displays all the tiles of the game
	 */
	private TilePanel tilePanel;
	/**
	 * Tile panel qui affiche tout 
	 * les rectangles du mode Arcade.
	 */
	private TilePanel tilePanelArcade;
	/**
	 * Tile qui prend comme référence
	 * celui du mode arcade ou training pour 
	 * les listeners
	 */
	private TilePanel specificTile; 
	/**
	 * GameModel qui prend en référence
	 * le mode arcade ou training pour les
	 * listeners
	 */
	private GameModel specificGame;
	// Barre d'affichage supérieur du jeux
	private MonMenuBar menuBar;
	// Entier qui défini dans quel mode nous sommes
	private int gameType;
	// Panel contenant tout les label inférieurs du jeux
	private JPanel allLabel;


	/**
	 * Méthode qui gére mes listener
	 * selon la mode de jeux où le joueur se trouve
	 * @return MouseAdapter listener pour un tilePanel.
	 */
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
				if(specificTile.getEtatPartie() != EtatPartie.PERDUE && 
						specificTile.getEtatPartie() != EtatPartie.GAGNÉE  ){
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

			}
		};

	}
	/**
	 * Met à joueur les attributs spécifiquegame
	 * et tile selon le mode de jeu où le joueur
	 * se trouve.
	 */
	private void updateSpecification() {

		if(gameType == 1) {

			specificTile = tilePanel;
			specificGame = gameModel;
		}else {

			specificTile = tilePanelArcade;
			specificGame = arcadeModel;
		}
	}
	/**
	 * Méthode qui ajoute les listeners aux boutons,label
	 * et panel du jeux.
	 */
	private void setupListeners() {		

		// Tile par défaut
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

				// Met à jour les specificTile/game selon le mode
				updateSpecification();

				/// génère une nouvelle partie et rafraichie la fenêtre.
				if(specificGame == arcadeModel && specificTile.getEtatPartie() == EtatPartie.GAGNÉE
						|| specificGame == gameModel ) {

					// Met à jour les label, et réactive le timer du mode.
					updateLevel();
					specificGame.generateGame();
					specificGame.startTimer();		
					updateReset();
					updateSum();
					updateGoal();
					specificTile.repaint();
				}

			}

		});

		// Listener ajouté au bouton reset (réinitialise la partie)
		resetButton.addMouseListener(new MouseAdapter() {

			// Quand le bouton suivant est cliqué
			@Override
			public void mouseClicked(MouseEvent e) {

				// Met à jour les specificTile/game selon le mode
				updateSpecification();

				// Reset seulement si la partie n'est pas gagné.
				if( specificTile.getEtatPartie() != EtatPartie.GAGNÉE ) {

					specificGame.reinitialisationPartie();
				}
				// Met à jour les label 
				updateReset();
				updateSum();
				updateGoal();
				specificTile.repaint();
			}

		});

		/**
		 * Permet de passer au mode arcade.
		 */
		menuBar.getArcadeButton().addMouseListener(new MouseAdapter() {

			// Quant le bouton est appuyé.
			@Override
			public void mouseClicked(MouseEvent e) {

				// Affiche les label pour le mode Arcade
				// Met à jour les label selon la partie en cours
				// dans le mode Arcade.
				levelLabel.setVisible(true);
				restartButton.setVisible(true);
				gameType = 2;
				updateReset();
				updateSum();
				updateGoal();
				updateLevel();
				// Met à jour les timer de chaque partie.
				gameModel.pauseTimer();
				arcadeModel.startTimer();
				tilePanelArcade.setVisible(true);
				tilePanel.setVisible(false);

			}

		});

		/**
		 * Permet de passer au mode Training
		 */
		menuBar.getTrainingButton().addMouseListener(new MouseAdapter() {

			// Quand le bouton suivant est cliqué
			@Override
			public void mouseClicked(MouseEvent e) {
				// Affiche les label pour le mode training
				// Met à jour les label selon la partie en cours
				// dans le mode training
				levelLabel.setVisible(false);
				restartButton.setVisible(false);
				gameType = 1;
				updateReset();
				updateSum();
				updateGoal();
				// Met à jour les timer de chaque partie
				gameModel.startTimer();
				arcadeModel.pauseTimer();
				tilePanelArcade.setVisible(false);
				tilePanel.setVisible(true);


			}

		});

		/**
		 * Recommence une partie au niveau 1 pour
		 * le mode arcade en tout temp.
		 */
		restartButton.addMouseListener(new MouseAdapter() {

			// Quand le bouton next est cliqué
			@Override
			public void mouseClicked(MouseEvent e) {

				updateSpecification();

				// met le niveau à 1 et met 
				// les label à jour.
				arcadeModel.nextLevel(false);
				updateLevel();
				specificGame.generateGame();
				specificGame.startTimer();		
				updateReset();
				updateSum();
				updateGoal();
				specificTile.repaint();
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
		arcadeModel.pauseTimer();
		gameType = 1;

		// Initialise le timer et le label.
		initTimer();
		initLabel();

		// Ajoutes toute composantes au JPanel
		// et cache le mode arcade .
		add(menuBar);
		add(tilePanel);
		add(tilePanelArcade);
		tilePanelArcade.setVisible(false);
		add(allLabel);
		add(nextButton);
		add(resetButton);
		add(restartButton);

		// Intialise tout mes listeners 
		setupListeners();
	}

	/**
	 * Méthode qui initialise un timer qui ne 
	 * s'arrête jamais pour afficher les
	 * timer de mes autre classe(game).
	 */
	private void initTimer() {
		timer = new Timer(1000, new ActionListener() {


			/**
			 * Affiche le temp pour le label
			 * Timer selon le mode de jeux.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {

				if(gameType == 1) {

					labelTime.setText(gameModel.getTimeFormat());

				}else if(gameType == 2) {

					labelTime.setText(arcadeModel.getTimeFormat());

				}


			}
		});

		timer.setDelay(1000);
		timer.start();
	}

	/**
	 * Méthode qui initialise tout mes
	 * boutton et label avec leur noms 
	 * respectif.
	 */
	private void initLabel() {

		resetButton = new JButton("Reset");
		nextButton = new JButton("Next");
		restartButton = new JButton("Restart");
		restartButton.setVisible(false);
		currentSum = new JLabel("[Sum] " + gameModel.getSum());	
		currentReset = new JLabel("[Resets] " + gameModel.getCountReset());
		goal = new JLabel("[Goal] " + gameModel.getGoal());
		labelTime = new JLabel("[Time] 00:00");
		levelLabel = new JLabel("[Level] 1");
		levelLabel.setVisible(false);
		allLabel = new JPanel();
		menuBar = new MonMenuBar();

		// Panel représentant ma barre d'affichage.
		allLabel.add(goal);
		allLabel.add(currentSum);
		allLabel.add(labelTime);
		allLabel.add(currentReset);
		allLabel.add(levelLabel);


	}

	/**
	 * Méthode qui modifie le component currentSum
	 * pour ajouté la nouvelle somme lors de nouvelle
	 * sélection de chiffres. 
	 */
	private void updateSum() {

		// Modification du component.
		if(gameType == 1) {

			currentSum.setText("[Sum]" + gameModel.getSum());

		}else if(gameType == 2) {

			currentSum.setText("[Sum]" + arcadeModel.getSum());

		}
	}

	/**
	 * Méthode qui met à jour l'objectif(JLabel)
	 * lors d'une nouvelle partie. 
	 */
	private void updateGoal() {

		if(gameType == 1) {

			goal.setText("[Goal] " + gameModel.getGoal());

		}else if(gameType == 2) {

			goal.setText("[Goal] " + arcadeModel.getGoal());

		}
	}

	/**
	 * Met à jour le nombre
	 * de reset d'une partie selon 
	 * le mode.
	 */
	private void updateReset() {

		if(gameType == 1) {

			currentReset.setText("[Resets] " + gameModel.getCountReset());

		}else if(gameType == 2) {

			currentReset.setText("[Resets] " + arcadeModel.getCountReset());

		}
	}

	/**
	 * Met à jour le niveau
	 * du joueur du mode arcade.
	 */
	private void updateLevel() {

		arcadeModel.nextLevel(tilePanelArcade.getEtatPartie() == EtatPartie.GAGNÉE);
		levelLabel.setText("[Level] " + arcadeModel.getLevel());
	}
}
