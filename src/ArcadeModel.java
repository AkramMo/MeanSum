
public class ArcadeModel extends GameModel {
	
	private int levelGame;
	
	public ArcadeModel() {
		
		levelGame = 1;
	}
	
	public int getNbrRegroupement(int levelGame){
		
		return Math.round((3 + (3*levelGame/20)));
		
	}
	
	public double getDoubleDigitProba(int levelGame) {
		
		return (30 + 30*levelGame/200);
	}
	
	
	public void generateGame() {

		// Nombre aléatoire de regroupement à avoir
		int nbRegroupements = getNbrRegroupement(levelGame);
		double doubleDigitProba = 100 - getDoubleDigitProba(levelGame);
		
		// Réinitialise les composantes du jeux
		suppressionPartie();
		
		

		for(int i = 0; i < nbRegroupements; i++) {

			// Condition des 70%
			if(getRandom(0,100) <= doubleDigitProba ) {

				getListNumber().add(getRandom(1,9));
			}else {

				getListNumber().add(getRandom(10,99));
			}
		}

		System.out.println(getDigits() + "Mode Arcade");
	}
	
	
	
	

}
