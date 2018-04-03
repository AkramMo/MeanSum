import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Chronometre {

	private Timer timer;
	
	
	public Chronometre(int delay, ActionListener listener) {
		
		timer = new Timer(delay, listener);
		timer.setInitialDelay(1000);
	}
	
	public Timer getTimer() {
		
		return timer;
	}

}
