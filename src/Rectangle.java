
public class Rectangle {
	
	private String chiffreInscrit;
	private int chiffrePosition;
	private int etatSelection;
	
	public Rectangle(String chiffreInscrit, int chiffrePosition, int etatSelection) {
		
		this.chiffreInscrit = chiffreInscrit;
		this.chiffrePosition = chiffrePosition;
		this.etatSelection = etatSelection;
		
	}

	public String getChiffreInscrit() {
		return chiffreInscrit;
	}

	public void setChiffreInscrit(String chiffreInscrit) {
		this.chiffreInscrit = chiffreInscrit;
	}

	public int getChiffrePosition() {
		return chiffrePosition;
	}

	public void setChiffrePosition(int chiffrePosition) {
		this.chiffrePosition = chiffrePosition;
	}

	public int getEtatSelection() {
		return etatSelection;
	}

	public void setEtatSelection(int etatSelection) {
		this.etatSelection = etatSelection;
	}
	
}
