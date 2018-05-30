package concession;

public class Voiture {
	private String marque;
	private String modele;
	private int annee;
	private int kilometrage;
	private int prix;

	public Voiture(String marque, String modele, int annee, int kilometrage, int prix) {
		this.marque = marque;
		this.modele = modele;
		this.annee = annee;
		this.kilometrage = kilometrage;
		this.prix = prix;
	}

	public String getMarque() {
		return marque;
	}

	public String getModele() {
		return modele;
	}

	public int getAnnee() {
		return annee;
	}

	public int getKilometrage() {
		return kilometrage;
	}

	public int getPrix() {
		return prix;
	}

	public void setKilometrage(int kilometrage) {
		this.kilometrage = kilometrage;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}
	public String toString() {
		return ""+this.getMarque()+" "+this.getModele()+" "+this.getAnnee()+" "+this.getKilometrage()+" "+this.getPrix();
	}
}
