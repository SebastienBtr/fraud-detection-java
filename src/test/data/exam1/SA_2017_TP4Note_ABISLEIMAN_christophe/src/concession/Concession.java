package concession;

import java.util.ArrayList;
import java.util.List;

public class Concession {
	private List<Voiture> voituresParPrix; // Liste contenant l'ensemble des vehicules de la concession ordonnes par prix croissants.

	public Concession() {
		this.voituresParPrix = new ArrayList<Voiture>();
	}
	
	/**
	 * @return Retourne la liste de tous les vehicules de la concession ordonnes par prix croissant
	 */
	public List<Voiture> getParPrix() {
		return this.voituresParPrix;
	}
	
	/**
	 * Ajoute la voiture v aux autres vehicules de la concession en respectant l'ordre 
	 * de la liste (la liste doit rester triee par ordre de prix croissant)
	 */
	public void add(Voiture v) {
		
		int i=0;
		 ArrayList<Voiture> nov=new ArrayList<Voiture>();
		while(i<this.getParPrix().size() && this.getParPrix().get(i).getPrix()<v.getPrix()){
			nov.add(this.getParPrix().get(i));
			i++;
			
		}
		nov.add(v);
		for(int j=i;j<this.getParPrix().size();j++){
			nov.add(this.getParPrix().get(j));
		}
		this.voituresParPrix=nov;
		}
	
}