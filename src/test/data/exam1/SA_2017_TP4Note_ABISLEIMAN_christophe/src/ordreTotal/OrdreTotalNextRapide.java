package ordreTotal;

import java.util.ArrayList;
import java.util.List;

public class OrdreTotalNextRapide {
	private int min; // la valeur minimale selon l'ordre  (min== min() ).
	private int[] next; // next[val] vaut la valeur suivant val dans l'ordre de la permutation
	// pour x==max   next[x]==-1
	// pour tout x!=max de [0, next.length-1] on a next[x]==next(x)

	/**
	 * Constructeur initialisant les variables d'instance de sorte a ce que l'ordre represente par this
	 * corresponde a l'ordre des valeurs dans tab.
	 * Vous pouvez considerer, sans qu'il soit utile de le verifier, que tab correspond bien a un ordre total 
	 * sur les entiers de 0 a tab.length-1 (tab contient tous les entiers de 0 a tab.length-1).  
	 * 
	 * Exemples : 
	 *  - si tab vaut {3, 2, 0, 1, 4} alors initialise min a 3 et next de sorte qu'il corresponde a [1, 4, 0, 2, -1]. 
	 *  - si tab vaut {0, 1, 3, 4, 2} alors initialise min a 0 et next de sorte qu'il corresponde a [1, 3, -1, 4, 2]. 
	 * @param tab, un tableau d'au moins 2 entiers (tab.length>1) correspondant a un ordre total sur les 
	 *        entiers de 0 a tab.length-1.
	 */
	public OrdreTotalNextRapide(int[] tab) {
		this.min=tab[0];
		int[] next=new int[tab.length];
		for(int i=0;i<tab.length;i++){
			int j=0;
			while(j< tab.length && tab[j]!=i){
				j++;
			}
			if(j==tab.length-1){
				next[i]=-1;
			}else{
			next[i]=tab[j+1];
		}
		}
		
		this.next=next;
		
	}
	/**
	 * METHODE UTILISEE UNIQUEMENT POUR LES TESTS : inutile pour vous. Ne pas la modifier.
	 */
	public void set(int[] next) {
		this.next = next;
	}
	/**
	 * METHODE UTILISEE UNIQUEMENT POUR LES TESTS : inutile pour vous. Ne pas la modifier.
	 */
	public int[] getNext() {
		return this.next;
	}
	/**
	 * @return Accesseur retournant la liste des valeurs dans l'ordre de la permutation
	 */
	public List<Integer> get() {
		ArrayList<Integer> val=new ArrayList<Integer>();
		val.add(this.min);
		int compt=0;
		int i=this.getNext()[min];
		while(compt<this.getNext().length && i!=-1){
			val.add(i);
			i=this.getNext()[i];
			
			compt++;
		}
		return val;
	}



	/**
	 * @return retourne la valeur minimale selon l'ordre
	 */
	public Integer min() {
		return this.min;
	}

	/**
	 * @return retourne la valeur maximale selon l'ordre
	 */
	public Integer max() {
		return this.get().get(this.next.length-1);
		
	}


	/**
	 * Si val est une valeur de l'intervalle [0, this.valeurs.size()-1] differente de max, 
	 * next(va) retourne la plus petite valeur differente de val qui est superieure a val.
	 * Sinon, retourne -1.
	 * 
	 * Exemples : 
	 * - Si this correcpond a 3<=2<=0<=1<=4, alors this.next(3) retourne 2
	 * - Si this correcpond a 3<=2<=0<=1<=4, alors this.next(2) retourne 0
	 * - Si this correcpond a 3<=2<=0<=1<=4, alors this.next(0) retourne 1
	 * - Si this correcpond a 3<=2<=0<=1<=4, alors this.next(1) retourne 4
	 * - Si this correcpond a 3<=2<=0<=1<=4, alors this.next(4) retourne -1
	 * - Si this correcpond a 3<=2<=0<=1<=4, alors this.next(600) retourne -1
	 * - Si this correcpond a 3<=2<=0<=1<=4, alors this.next(-3) retourne -1
	 */
	public int next(int val) {
		if(val>0 && val<this.get().size()-1){
			
		
		int i=0;
		while(i<this.get().size() && this.get().get(i)!=val ){
			i++;
		}
		if(i!=this.get().size()-1){
			
		
		return this.get().get(i+1);
		}else{
			return -1;
		}
		}else{
			return -1;
		}
	}

	/**
	 * 
	 * @param x, un entier
	 * @param y, un entier
	 * @return Retourne true si x et y appartiennent a [0, this.next.length-1] et si 
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
		// A VOUS DE COMPLETER
		return false;
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
