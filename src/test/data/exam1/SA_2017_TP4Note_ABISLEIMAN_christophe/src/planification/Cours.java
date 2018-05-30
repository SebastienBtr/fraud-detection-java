package planification;

public class Cours {
	private int semaineDebut;
	private int semaineFin;
	private int nbHeures;

	public Cours(int semainedebut, int semaineFin, int nbHeures) {
		this.semaineDebut = semainedebut;
		this.semaineFin = semaineFin;
		this.nbHeures = nbHeures;
	}
	public int getSemaineDebut() {
		return this.semaineDebut;
	}
	public int getsemaineFin() {
		return this.semaineFin;
	}
	public int getNbHeures() {
		return this.nbHeures;
	}
	public String toString() {
		return "["+this.getSemaineDebut()+", "+this.getsemaineFin()+", "+this.getNbHeures()+"]";
	}
}