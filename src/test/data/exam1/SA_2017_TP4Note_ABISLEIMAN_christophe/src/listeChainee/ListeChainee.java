package listeChainee;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;




public class ListeChainee {

	private Cellule premiere;

	public ListeChainee() {
		this.premiere = null;
	}

	public void add(int val) {
		this.premiere= new Cellule(val, this.premiere);
	}

	public Cellule getPremiere() {
		return this.premiere;
	}
	/**
	 * @param val
	 * @return Retourne true si la liste chainee this contient la valeur val (sinon retourne false)
	 */
	public boolean contains(int val) {
		boolean present=false;
		Cellule c = this.premiere;
		while (c!=null && !present) {
			present = c.getVal()==val;
			c = c.getSuivante();
		}
		return present;
	}
	/**
	 * Modifie la liste this en retirant tous les doublons de la liste. A l'issue de cette methode, la liste contient
	 * les memes entiers mais chacun en un seul exemplaire. L'ordre des entiers dans la liste apres l'appel à cette methode 
	 * importe peu.
	 * Ex. : Si this vaut 4-->3-->4-->1-->3 alors apres supprimeDoublons this vaut 4-->3-->1 (ou 4-->1-->3 ou 3-->4-->1 ou 3-->1-->4 ou 1-->4-->3 ou 1-->3-->4)
	 *       Si this vaut 1-->2-->1-->1-->2 alors apres supprimeDoublons this vaut 1-->2 (ou 2-->1)
	 */

	public void supprimeDoublons() {
		Cellule c=this.getPremiere();
		while(c!=null){
			if(!this.contains(c.getVal())){
				this.add(c.getVal());
			}
			c=c.getSuivante();
			
		}	
	}
}
