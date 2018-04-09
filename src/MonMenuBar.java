import javax.swing.JButton;
import javax.swing.JMenuBar;
/**
 * Classe qui hérite de JMenuBar,
 * représente le menu supérieur du 
 * MeanSumGame. Comprend les boutons du
 * menu.
 * @author AkramMo
 *
 */
public class MonMenuBar extends JMenuBar {

	// Bouton permettant de passer d'un
	// mode à l'autre.
	private JButton trainingButton;
	private JButton arcadeButton;

	/**
	 * Constructeur qui initialise
	 * la classe parent et les attributs(boutons)
	 * @param gameView
	 */
	public MonMenuBar() {

		super();
		// initialise les boutons.
		initComposante();

	}

	/**
	 * Initialise mes boutons avec leur
	 * nom.
	 */
	private void initComposante() {

		trainingButton = new JButton("Training");
		arcadeButton = new JButton("Arcade");	


		this.add(trainingButton);
		this.add(arcadeButton);
	}

	/**
	 * Accesseur 
	 * @return JButton bouton training.
	 */
	public JButton getTrainingButton() {

		return this.trainingButton;
	}

	/**
	 * Accesseur
	 * @return JButton bouton arcade.
	 */
	public JButton getArcadeButton() {

		return this.arcadeButton;
	}
}
