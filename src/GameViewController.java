import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
	private JButton nextButton;
	private JLabel currentSum;
	/**
	 * A single tile panel displays all the tiles of the game
	 */
	private TilePanel tilePanel;
	
	// TODO Add all the other required UI components (labels, buttons, etc.)
	
	private void setupListeners() {		
		// TODO Set up the required listeners on the UI components (button clicks, etc.)
		
		// EXAMPLE: A mouse listener with a click event
		tilePanel.addMouseListener(new MouseAdapter() {
			/*@Override
			public void mouseClicked(MouseEvent e) {
				
				System.out.println("Mouse pressed on the tile panel");
				
			}*/
			
			public void mousePressed(MouseEvent e) {
				
				System.out.println("PRESS");
			}
			
			public void mouseReleased(MouseEvent e) {
				
				System.out.println("RELEASE");
			}
		});
		
		
		
			nextButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			gameModel.generateGame();
			tilePanel.repaint();
				
			}
			
		});
	}
	
	public GameViewController() {
		// TODO Initialize our game model by constructing an instance
		
		// The layout defines how components are displayed
		// (here, stacked along the Y axis)
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.gameModel = new GameModel();
		this.nextButton = new JButton("Suivant");
		this.currentSum = new JLabel("Somme");
		
		tilePanel = new TilePanel(gameModel);
		this.add(tilePanel);
		this.add(currentSum);
		this.add(nextButton);
		// TODO Initialize all the UI components
		
		setupListeners();
	}
	
}
