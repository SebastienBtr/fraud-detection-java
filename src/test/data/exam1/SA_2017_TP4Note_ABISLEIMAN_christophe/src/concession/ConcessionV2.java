package concession;
import java.util.ArrayList;
import java.util.List;


public class ConcessionV2 extends Concession {
	private List<String> marques;

	public ConcessionV2() {
		super();
		this.marques= new ArrayList<String>();
	}
	
	
	
	public List<String> getMarques() {
		return marques;
	}



	/**
	 * @param marque, une marque de vehicule
	 * @return Retourne une liste composee de tous les vehicules de la concession de marque marque
	 * (retourne null si il n'existe aucun vehicule de cette marque)
	 */
	public List<Voiture> get(String marque) {
		if(! this.marques.isEmpty() && this.marques.contains(marque)){
			 ArrayList<Voiture> nov=new ArrayList<Voiture>();
			 for(Voiture v : this.getParPrix()){
				 if(v.getMarque()==marque){
					 nov.add(v);
				 }
			 }
			 return nov;
			
		}else{
			return null;
		}
	}
	
	/**
	 * Ajoute la voiture v aux autres vehicules de la concession en respectant l'ordre 
	 * de la liste (la liste doit rester triee par ordre de prix croissant)
	 */
	public void add(Voiture v) {
		super.add(v);
		if(!this.getMarques().contains(v.getMarque())){
			this.marques.add(v.getMarque());
		}
	}
}
