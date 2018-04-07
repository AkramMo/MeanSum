
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.Timer;


public class MonMenuBar extends JMenuBar {


	private JPanel gameView;
	private JButton trainingButton;
	private JButton arcadeButton;

	public MonMenuBar(JPanel gameView) {

		super();
		this.gameView = gameView;

		initComposante();

	}

	private void initComposante() {

		trainingButton = new JButton("Training");
		arcadeButton = new JButton("Arcade");	


		setupListeners();


		this.add(trainingButton);
		this.add(arcadeButton);

		//	this.add(timer.getTimer());
	}

	public JButton getTrainingButton() {

		return this.trainingButton;
	}
	public JButton getArcadeButton() {

		return this.arcadeButton;
	}




	private void setupListeners() {

		trainingButton.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {



			}
		});

		arcadeButton.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {


			}

		});

	}

	private class Ecouteur implements ActionListener{

		private JPanel JFrameActif;

		private Ecouteur(JPanel JFrameActif) {

			this.JFrameActif = JFrameActif;
		}


		public void actionPerformed(ActionEvent e) {


		}
	}


}
