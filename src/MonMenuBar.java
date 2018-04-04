
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


	private Timer timer;
	private JPanel gameView;
	private JButton trainingGame;
	private JButton arcadeGame;
	private JLabel labelTime;
	private int minuteTime = 0;
	private int secondTime = 0;

	public MonMenuBar(JPanel gameView) {

		super();
		this.gameView = gameView;

		initComposante();

	}

	private void initComposante() {

		trainingGame = new JButton("Training");
		arcadeGame = new JButton("Arcade");	
		initTimer();
		labelTime = new JLabel("[ Chronomètre ] : ");

		setupListeners();


		this.add(trainingGame);
		this.add(arcadeGame);
		this.add(Box.createHorizontalGlue());
		this.add(labelTime);
		this.add(Box.createHorizontalGlue());

		//	this.add(timer.getTimer());
	}


	private void initTimer() {
		// TODO Auto-generated method stub
		timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				secondTime++;
				
				if(secondTime < 10) {
					
				labelTime.setText("[ Chronomètre ] : 0" + minuteTime + "m 0" + secondTime + "s");
				}else {
					
					labelTime.setText("[ Chronomètre ] : 0" + minuteTime + "m " + secondTime + "s");
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

	private void setupListeners() {

		trainingGame.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {



			}
		});

		arcadeGame.addMouseListener(new MouseAdapter() {

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
