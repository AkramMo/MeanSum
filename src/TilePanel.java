import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

/**
 * The tile panel displays all the tiles (one per digit) of the game.
 *
 */
public class TilePanel extends JPanel {

	/**
	 * The tile panel object holds a reference to the game model to
	 * request information to display (view) and to modify its state (controller)
	 */
	private GameModel gameModelHandle;

	/**
	 * A table of colours that can be used to draw the tiles
	 */
	private Color[] colours;
	
	/**
	 * Initialize an array of pre-set colours.
	 * The colours are picked to ensure readability and avoid confusion.
	 */
	private void initializeColours() {
		// Some tile colours in the '0xRRGGBB' format
		String[] tileColourCodes = new String[] {
				"0x89CFF0", "0xF4C2C2", "0xFFBF00", "0xFBCEB1",
    			"0x6495ED", "0x9BDDFF", "0xFBEC5D",	"0xFF7F50",
    			"0x00FFFF", "0x98777B", "0x99BADD", "0x654321"
    			};
		
		// Allocate and fill our colour array with the colour codes
		colours = new Color[tileColourCodes.length];
		for (int i = 0; i < colours.length; ++i)
			colours[i] = Color.decode(tileColourCodes[i]);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		char[] allnumbers = gameModelHandle.getDigits().toCharArray();
		int nbrRectangle = gameModelHandle.getDigits().length();
		int stringPosition;
		String nbrString;
		
		
		g.setFont(new Font("Arial", Font.PLAIN, 40));
		// TODO Seek current game information from the model and draw the tiles accordingly

		// EXAMPLE: Paint a rectangle with the first colour
		for(int i = 0; i < nbrRectangle; i++) {
			
			
		g.setColor(colours[i]);
		
		g.fillRoundRect((this.getWidth()/nbrRectangle)*i, 0, this.getWidth()/nbrRectangle, 128, 30, 30);
		
		g.setColor(Color.BLACK);
		
		nbrString = String.valueOf(allnumbers[i]);
		stringPosition = (this.getWidth()/nbrRectangle)*i+((this.getWidth()/nbrRectangle)/2);
		
		g.drawString(nbrString, stringPosition, 64);

		}
	}
	
	public TilePanel(GameModel gameModel) {
		if (gameModel == null)
			throw new IllegalArgumentException("Should provide a valid instance of GameModel!");
		gameModelHandle = gameModel;
		gameModel.generateGame();
		// Initialize our array of tile colours
		initializeColours();
	}
	
}
