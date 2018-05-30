package planification;
import java.util.ArrayList;

public class Planning {
	private ArrayList<Cours> lesCours;

	public Planning() {
		this.lesCours = new ArrayList<Cours>();
	}
	public ArrayList<Cours> get() {
		return this.lesCours;
	}
	public void add(Cours c) {
		this.get().add(c);
	}
	public String toString() {
		return this.get().toString();
	}
	
	/**
	 * @return Retourne la charge de la semaine la plus chargee (parmi les 52 semaines de l'annee, numerotees de 1 a 52)
	 * c'est a dire la somme des heures des differents cours ayant lieu durant cette semaine.
	 */
	public int getMaxCharge() {
		int max=0;
		for(int i=0;i<52;i++){
			int charge=0;
			for(Cours C: this.get()){
				if(i>=C.getSemaineDebut() && i<=C.getsemaineFin()){
					charge+=C.getNbHeures();
				}
			}
			if(charge>max){
				max=charge;
			}
		}
		return max;
	}
}
