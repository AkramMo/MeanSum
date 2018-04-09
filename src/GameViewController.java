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
	// Affiche la somme � obtenir
	private JLabel goal;
	//Label qui affiche le chronom�tre
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
	 * Tile qui prend comme r�f�rence
	 * celui du mode arcade ou training pour 
	 * les listeners
	 */
	private TilePanel specificTile; 
	/**
	 * GameModel qui prend en r�f�rence
	 * le mode arcade ou training pour les
	 * listeners
	 */
	private GameModel specificGame;
	// Barre d'affichage sup�rieur du jeux
	private MonMenuBar menuBar;
	// Entier qui d�fini dans quel mode nous sommes
	private int gameType;
	// Panel contenant tout les label inf�rieurs du jeux
	private JPanel allLabel;


	/**
	 * M�thode qui g�re mes listener
	 * selon la mode de jeux o� le joueur se trouve
	 * @return MouseAdapter listener pour un tilePanel.
	 */
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
				if(specificTile.getEtatPartie() != EtatPartie.PERDUE && 
						specificTile.getEtatPartie() != EtatPartie.GAGN�E  ){
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

			}
		};

	}
	/**
	 * Met � joueur les attributs sp�cifiquegame
	 * et tile selon le mode de jeu o� le joueur
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
	 * M�thode qui ajoute les listeners aux boutons,label
	 * et panel du jeux.
	 */
	private void setupListeners() {		

		// Tile par d�faut
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

				// Met � jour les specificTile/game selon le mode
				updateSpecification();

				/// g�n�re une nouvelle partie et rafraichie la fen�tre.
				if(specificGame == arcadeModel && specificTile.getEtatPartie() == EtatPartie.GAGN�E
						|| specificGame == gameModel ) {

					// Met � jour les label, et r�active le timer du mode.
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

		// Listener ajout� au bouton reset (r�initialise la partie)
		resetButton.addMouseListener(new MouseAdapter() {

			// Quand le bouton suivant est cliqu�
			@Override
			public void mouseClicked(MouseEvent e) {

				// Met � jour les specificTile/game selon le mode
				updateSpecification();

				// Reset seulement si la partie n'est pas gagn�.
				if( specificTile.getEtatPartie() != EtatPartie.GAGN�E ) {

					specificGame.reinitialisationPartie();
				}
				// Met � jour les label 
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

			// Quant le bouton est appuy�.
			@Override
			public void mouseClicked(MouseEvent e) {

				// Affiche les label pour le mode Arcade
				// Met � jour les label selon la partie en cours
				// dans le mode Arcade.
				levelLabel.setVisible(true);
				restartButton.setVisible(true);
				gameType = 2;
				updateReset();
				updateSum();
				updateGoal();
				updateLevel();
				// Met � jour les timer de chaque partie.
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

			// Quand le bouton suivant est cliqu�
			@Override
			public void mouseClicked(MouseEvent e) {
				// Affiche les label pour le mode training
				// Met � jour les label selon la partie en cours
				// dans le mode training
				levelLabel.setVisible(false);
				restartButton.setVisible(false);
				gameType = 1;
				updateReset();
				updateSum();
				updateGoal();
				// Met � jour les timer de chaque partie
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

			// Quand le bouton next est cliqu�
			@Override
			public void mouseClicked(MouseEvent e) {

				updateSpecification();

				// met le niveau � 1 et met 
				// les label � jour.
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
	 * M�thode qui initialise un timer qui ne 
	 * s'arr�te jamais pour afficher les
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
	 * M�thode qui initialise tout mes
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

		// Panel repr�sentant ma barre d'affichage.
		allLabel.add(goal);
		allLabel.add(currentSum);
		allLabel.add(labelTime);
		allLabel.add(currentReset);
		allLabel.add(levelLabel);


	}

	/**
	 * M�thode qui modifie le component currentSum
	 * pour ajout� la nouvelle somme lors de nouvelle
	 * s�lection de chiffres. 
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
	 * M�thode qui met � jour l'objectif(JLabel)
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
	 * Met � jour le nombre
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
	 * Met � jour le niveau
	 * du joueur du mode arcade.
	 */
	private void updateLevel() {

		arcadeModel.nextLevel(tilePanelArcade.getEtatPartie() == EtatPartie.GAGN�E);
		levelLabel.setText("[Level] " + arcadeModel.getLevel());
	}
}
