package ordreTotal;
import java.util.ArrayList;
import java.util.List;


public class OrdreTotal {

	private List<Integer> valeurs; // La liste formee des valeurs de [0, valeurs.size()-1] placees dans l'ordre
	// on a : 
	//    valeurs.get(0) == min
	//    valeurs.get( i+1 ) == next( valeurs.get(i) ) (pour tout i de 0 a valeurs.size()-2)
	//    valeurs.get( valeurs.size()-1 ) == max

	/**
	 * Constructeur initialisant la liste valeurs avec une liste constituee des valeurs de tab (dans le meme ordre)
	 * Vous pouvez considerer, sans qu'il soit utile de le verifier, que tab correspond bien a un ordre total sur les entiers de 0 a tab.length-1.
	 *     
	 * Exemples : 
	 *  - si tab vaut {3, 2, 0, 1, 4} alors initialise la liste valeurs pour correspondre a [3, 2, 0, 1, 4]. 
	 *  - si tab vaut {0, 1, 3, 4, 2} alors initialise la liste valeurs pour correspondre a [0, 1, 3, 4, 2]. 
	 * @param tab, un tableau d'au moins 2 entiers (tab.length>1) correspondant a un ordre total sur les 
	 *        entiers de 0 a tab.length-1.
	 */
	public OrdreTotal(int[] tab) {
		ArrayList<Integer> nov=new ArrayList<Integer>();
		for(int i : tab){
			nov.add(i);
			
		}
		this.valeurs=nov;
	}

	/**
	 * @return retourne la valeur minimale selon l'ordre
	 */
	public Integer min() {
		return this.valeurs.get(0);
	}

	/**
	 * @return retourne la valeur maximale selon l'ordre
	 */
	public Integer max() {
		return this.valeurs.get(this.valeurs.size()-1);
	}

	/**
	 * @return Accesseur retournant la liste des valeurs dans l'ordre de la permutation
	 */
	public List<Integer> get() {
		return this.valeurs;
	}
	/**
	 * METHODE UTILISEE UNIQUEMENT POUR LES TESTS
	 * @param l
	 */
	public void set(List<Integer>l) {
		this.valeurs = l;
	}
	/**
	 * Si val est une valeur de l'intervalle [0, this.valeurs.size()-1] differente de max, 
	 * next(va) retourne la plus petite valeur differente de val qui est superieure a val.
	 * Sinon, retourne -1.
	 * 
	 * Exemples : 
	 * - Si this correspond a 3<=2<=0<=1<=4, alors this.next(3) retourne 2
	 * - Si this correspond a 3<=2<=0<=1<=4, alors this.next(2) retourne 0
	 * - Si this correspond a 3<=2<=0<=1<=4, alors this.next(0) retourne 1
	 * - Si this correspond a 3<=2<=0<=1<=4, alors this.next(1) retourne 4
	 * - Si this correspond a 3<=2<=0<=1<=4, alors this.next(4) retourne -1
	 * - Si this correspond a 3<=2<=0<=1<=4, alors this.next(600) retourne -1
	 * - Si this correspond a 3<=2<=0<=1<=4, alors this.next(-3) retourne -1
	 */
	public int next(int val) {
		if(val>=0 && val<this.valeurs.size()){
			if(this.get().get(this.valeurs.size()-1)==val){
				return -1;
			}else{
				

			int i=0;
			while(i< this.valeurs.size() && this.get().get(i)!=val){
				i++;
			}
			return this.get().get(i+1);
			}
		}else{
			return -1;
		}

	}

	/**
	 * 
	 * @param x, un entier
	 * @param y, un entier
	 * @return Retourne true si x et y appartiennent a [0, this.valeurs.size()-1] et si 
	 * selon l'ordre this x est inferieur ou egal a y
	 * Exemples : 
	 * - Si this correspond a 3<=2<=0<=1<=4, alors this.inferieurOuEgal(3, 3) retourne true
	 * - Si this correspond a 3<=2<=0<=1<=4, alors this.inferieurOuEgal(0, 0) retourne true
	 * - Si this correspond a 3<=2<=0<=1<=4, alors this.inferieurOuEgal(3, 2) retourne true
	 * - Si this correspond a 3<=2<=0<=1<=4, alors this.inferieurOuEgal(2, 1) retourne true
	 * - Si this correspond a 3<=2<=0<=1<=4, alors this.inferieurOuEgal(2, 3) retourne false
	 * - Si this correspond a 3<=2<=0<=1<=4, alors this.inferieurOuEgal(4, 2) retourne false
	 * - Si this correspond a 3<=2<=0<=1<=4, alors this.inferieurOuEgal(-1, 0) retourne false
	 * - Si this correspond a 3<=2<=0<=1<=4, alors this.inferieurOuEgal(1, 120) retourne false
	 */
	public boolean inferieurOuEgal(Integer x, Integer y) {
		if(x>=0 && x<this.valeurs.size() && y>=0 && y<this.valeurs.size() ){
			if(this.next(x)==-1){
				return false;
			}else{
			
				ArrayList<Integer> sup=new ArrayList<Integer>();
				int i=this.next(x);
				sup.add(i);
				while(this.next(i)!=-1){
					i=this.next(i);
					sup.add(i);
				}
				return sup.contains(y);
					
				}
			}
			else{
			return false;
		}

	}

	public String toString() {
		String res ="[";
		List<Integer> l = this.get();
		for (Integer i : l) {
			res+=i+" ";
		}
		return res+"]";
	}
}
