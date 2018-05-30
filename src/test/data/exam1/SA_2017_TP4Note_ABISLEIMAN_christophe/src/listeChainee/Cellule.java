package listeChainee;

public class Cellule {
	private int val;
	private Cellule suivante;

	public Cellule(int val, Cellule suivante) {
		this.val  = val; 
		this.suivante = suivante;
	}
	public int getVal() {
		return this.val;
	}
	public Cellule getSuivante() {
		return this.suivante;
	}
}

