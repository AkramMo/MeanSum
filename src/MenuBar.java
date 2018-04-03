import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {
	
	
	public MenuBar() {
		
		super();
		
		initComposante();
		
	}
	
	private void initComposante() {
		
		JMenuItem trainingGame = new JMenuItem("Training");
		
		JMenuItem ArcadeGame = new JMenuItem("Arcade");
		
		setupListeners();
	}

	
	private void setupListeners() {
		
		
	}
}
