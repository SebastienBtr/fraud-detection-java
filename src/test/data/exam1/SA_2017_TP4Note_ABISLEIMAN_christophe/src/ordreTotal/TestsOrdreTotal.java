package ordreTotal;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestsOrdreTotal implements ClipboardOwner {
	public static int DELAI = 250; // Temps d'attente entre les affichages lors d'une levee d'exception

	public static Class getClass(String nomClasse) {
		Class t;
		try  {
			t = Class.forName(nomClasse);
			return t;
		}  catch (ClassNotFoundException e) {
			return null;
		}
	}

	public static boolean isAbstract(Class c) {
		return Modifier.isAbstract(c.getModifiers());
	}

	public static boolean implementsInterface(Class c, Class interfac) {
		List<Class> interfaces = Arrays.asList( c.getInterfaces() );
		return interfaces.contains(interfac);
	}

	public static boolean extendsClass(Class c, Class superClasse) {
		return (c.getSuperclass().equals(superClasse));
	}

	public static Class[] getArgs(String[] argsClass) {
		if (argsClass==null) {
			return null;
		}
		Class[] args = new Class[ argsClass.length];
		for (int i=0; i<argsClass.length; i++) {
			if (argsClass[i].equals("int")) {
				args[i]=int.class;
			} else if (argsClass[i].equals("String")) {
				args[i]=String.class;
			} else {
				args[i]=getClass(argsClass[i]);
			}
		}
		return args;

	}
	public static Constructor getConstructor(Class c, Class[] args) {
		Constructor cons=null;
		try {
			cons= c.getConstructor(args);
		} catch(Exception e) {};
		return cons;
	}
	public static Constructor getConstructor(Class c, String[] argsClass) {
		return getConstructor( c, getArgs(argsClass));
	}

	public static Method getMethode(Class c, String methode, Class[] args) {
		try {
			return c.getMethod(methode, args);
		} catch (Exception nonCapturee) {
			return null;
		}
	}
	public static Method getMethode(Class c, String methode,String[] args) {
		return getMethode(c, methode,  getArgs(args));
	}
	public static String remove(String fic, String mot) {
		String res = fic; 
		while (res.contains(mot)) {
			res=res.replace(mot,"");
		}
		return res;
	}
	public static String removeStartingChars(String s, char c) {
		String res= s;
		while (res!=null && res.length()>0 && res.charAt(0)==c) {
			res=res.substring(1);
		}
		return res;
	}
	public static String startingId(String s) {
		String res= s;
		String delimiters = " \t,;(){}";
		while (s.length()>0 && delimiters.contains(""+s.charAt(0))) {
			s=s.substring(1);
		}
		int end=0;

		while (res!=null && res.length()>0 && end<res.length() && !delimiters.contains(""+res.charAt(end))) {
			end++;
		}
		return res.substring(0, end);
	}
	public static String withoutStartingId(String s) {
		String res= s;
		String delimiters = " \t,;(){}"+(char)10+(char)13;
		while (res.length()>0 && delimiters.contains(""+res.charAt(0))) {
			res=res.substring(1);
		}
		int end=0;
		while (res!=null && res.length()>0 && end<res.length() && !delimiters.contains(""+res.charAt(end))) {
			end++;
		}
		return res.substring(end, res.length());
	}
	public static String constructeurNomFic(String nomfic, String nomClasse, String[] args) {
		String fic = sansCommentaires(nomfic);
		return constructeur(fic, nomClasse, args);
	}
	public static String sansEspacesAvantParentheses(String s) {
		String res = s;
		while (res.indexOf(" (")>=0) {
			res = res.replace(" (","(");
		}
		while (res.indexOf("\t(")>=0) {
			res = res.replace("\t(","(");
		}
		return res;
	}
	public static String constructeur(String fic, String nomClasse, String[] args) {
		fic = sansEspacesAvantParentheses(fic);
		int index = fic.indexOf(nomClasse+"(");
		if (index<0) {
			return "";
		} else {
			fic = fic.substring(index);
			fic = withoutStartingId(fic);
			if (fic.length()>0) fic = fic.substring(1);
			int p = 0;
			while (
					p<args.length 
					&& fic.charAt(0)!=')') {
				if (startingId(fic).equals(args[p])) {
					fic = withoutStartingId( withoutStartingId(fic));
					p++;
				} else {
					return constructeur(fic, nomClasse, args);
				}
			}
			fic =removeStartingChars(fic,' ');
			fic = removeStartingChars(fic, '\t');
			if (p<args.length || fic.length()==0 || fic.charAt(0)!=')') {
				return  constructeur(fic, nomClasse, args);
			}
			int i= 0;
			while (i<fic.length() && fic.charAt(i)!='{') {
				i++;
			}
			i++;
			int nbOuvertes=1;
			while (nbOuvertes>0 && i<fic.length()) {
				if (fic.charAt(i)=='{') {
					nbOuvertes++;
				}
				if (fic.charAt(i)=='}') {
					nbOuvertes--;
				}
				i++;
			}
			if (i<fic.length()) {
				return fic.substring(fic.indexOf("{"), i);
			} else {
				return "";
			}
		}
	}

	public static String methode(String fic, String nomMethode) {
		int index = fic.indexOf(nomMethode);
		if (index<0) {
			return "";
		} else {
			int i= index+1;
			while (i<fic.length() && fic.charAt(i)!='{') {
				i++;
			}
			i++;
			int nbOuvertes=1;
			while (nbOuvertes>0 && i<fic.length()) {
				if (fic.charAt(i)=='{') {
					nbOuvertes++;
				}
				if (fic.charAt(i)=='}') {
					nbOuvertes--;
				}
				i++;
			}
			if (i<fic.length()) {
				return fic.substring(index, i);
			} else {
				return "";
			}
		}
	}
	public static List<String> lignesSansCommentaires(String fic) {
		List<String> l=new ArrayList<String>();
		List<String> m=new ArrayList<String>();
		try {
			FileReader fr = new FileReader(fic);
			BufferedReader br = new BufferedReader( fr );
			String s;
			do {
				s = br.readLine();
				if (s!=null) {
					l.add(s);
				}
			} while (s!=null);

			//Iterator<String> it = br.lines().iterator();

			//String s;
			//while (it.hasNext()) {
			//	s =it.next();
			/*				int index = s.indexOf("//");
				if (index>=0) { 
					s=s.substring(0, index);
				}
			 */			//	l.add(s);
			//}

			br.close();
		} catch (Exception e) {
			System.out.println(e);
		}	
		int i=0; 

		while (i<l.size()) {
			int debut = l.get(i).indexOf("/*");
			int fin = l.get(i).indexOf("*/");
			if (debut>=0) {
				if (fin>=0) {
					m.add(l.get(i).substring(0, debut)+""+ l.get(i).substring(fin+2));
				} else {
					m.add(l.get(i).substring(0, debut));
					i++;
					while (i<l.size() && l.get(i).indexOf("*/")<0) {
						i++;
					}
					fin = l.get(i).indexOf("*/");
					m.add(l.get(i).substring(fin+2));
				}
			} else if ( l.get(i).indexOf("//")>=0) {
				m.add(l.get(i).substring(0, l.get(i).indexOf("//")));
			}	else {
				m.add(l.get(i));
			}
			i++;
		}
		return m;
	}
	public static String sansCommentaires(String fic) {
		return reunirLignes( lignesSansCommentaires(fic));
	}
	public static String reunirLignes(List<String> lignes) {
		String res="";
		for (String r : lignes) {
			res=res+r+"\n";
		}
		return res;
	}
	public static int nbDeclaredFields(Class c) {
		return c.getDeclaredFields().length;
	}
	public static int nbDeclaredFieldsOfType(Class c, Class typ) {
		Field[] fields = c.getDeclaredFields();
		int nb=0;
		for (Field f : fields) {
			if (f.getType().equals(typ)) {
				nb++;
			}
		}
		return nb;
	}

	public static boolean fieldsDeclaredPrivate(Class c) {
		Field[] fields = c.getDeclaredFields();
		for (Field f : fields) {
			if (!Modifier.isPrivate(f.getModifiers())) {
				return false;
			}
		}
		return true;
	}
	public static boolean hasFieldOfType(Class c, Class type ) {
		Field[] fields = c.getDeclaredFields();
		for (Field f : fields) {
			if (f.getType().equals(type)) {
				return true;
			} 
		}
		return false;
	}

	public static List<String> getMots(String s) {
		List<String> l = new ArrayList<String>();
		String delimiteurs = " ,.;:/?%*+=-<>\\_()[]{}&"+(char)13+"|"+(char)10+"\t";
		String mot="";
		for (int i=0; i<s.length(); i++) {
			if (delimiteurs.contains(""+s.charAt(i))) {
				if (!mot.equals("")) {
					l.add(mot);
					mot="";
				}
			} else {
				mot=mot+s.charAt(i);
			}
		}
		return l;

	}

	public static boolean estUneVariableLocale(String file, Class c, String methode, String var) {
		String met = methode(sansCommentaires(file),methode);
		List<String> mots = getMots(met);
		int pos = mots.indexOf(var);
		if (pos>0) {
			String[] primitifs= {"int", "char", "boolean", "float", "long", "double"};
			List<String> lprimitifs = Arrays.asList(primitifs);
			if (lprimitifs.contains(mots.get(pos-1))) {
				return true;
			} else {
				return getClass(mots.get(pos-1))!=null ||  getClass(c.getPackage().getName()+"."+mots.get(pos-1))!=null;
			}
		} else {
			return false;
		}
	}
	public static boolean respecteDeveloppementEnCouche(String file, Class c, String methode) {
		String met = methode(sansCommentaires(file),methode);

		Method[] methodes = c.getDeclaredMethods();
		String sans = met;
		for (Method m : methodes) {
			sans = remove(sans, m.getName());
		}
		boolean couche = true;
		Field[] fields = c.getDeclaredFields();

		int i=0;
		while (couche && i<fields.length) {
			if (getMots(sans).contains(fields[i].getName())){// && sans.contains(fields[i].getName()+".")) {
				if (!estUneVariableLocale(file, c, methode,fields[i].getName() )) {
					couche=false;
				}
			}
			i++;
		}
		return couche;
	}

	public static void afficheThrowable( Throwable e, String test) {
		System.out.println(">>>>>> AIE..."+test+" lance "+e.toString());
		try {Thread.sleep(DELAI);} catch (Exception ex) {};
		e.printStackTrace();
		try {Thread.sleep(DELAI);} catch (Exception ex) {};
		if (e.getCause()!=null) { 
			System.out.println("Cause :");
			e.getCause().printStackTrace();
		}
		try {Thread.sleep(DELAI);} catch (Exception ex) {};
		System.out.println("<<<<<<<<<<<<<<<<<<");
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////START
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static int testConstructeur() {
		int note=100;
		System.out.println("Test du constructeur de OrdreTotal");
		Class cl = getClass("ordreTotal.OrdreTotal");

		Field[] fields = cl.getDeclaredFields();
		boolean inchange = fields.length==1;
		if (!inchange) {
			System.out.println("   Aie... vous ne devez pas modifier l'etat. Il y a "+fields.length+" variables d'instance declarees alors qu'il ne devrait y avoir qu'une List");
			note=0;
		} else {
			for (Field f : fields) {
				if (!f.getType().toString().equals("interface java.util.List")) {
					System.out.println("   Aie... vous ne devez pas modifier l'etat. Vous avez declare une variable d'instance de type "+f.getType()+" alors qu'il ne devrait y avoir qu'une List pour variable d'instance");
					note=0;
					inchange=false;
				}
			}
		}
		if (note>0) {
			int[] t1 = {3, 2, 0, 1, 4}; 
			int[] t2 = {0, 1, 3, 4, 2}; 
			int[] t3 = {3, 2, 0, 1, 4};
			int[] t4 = {0, 1};
			int[] t5 = {1, 0};
			OrdreTotal p1 = new OrdreTotal(t1);
			OrdreTotal p2 = new OrdreTotal(t2);
			OrdreTotal p3 = new OrdreTotal(t3);
			OrdreTotal p4 = new OrdreTotal(t4);
			OrdreTotal p5 = new OrdreTotal(t5);



			if (!p1.get().toString().equals("[3, 2, 0, 1, 4]")) {
				System.out.println("   Aie... new OrdreTotal("+Arrays.toString(t1)+") initialise la liste a "+p1.get().toString()+" au lieu de [3, 2, 0, 1, 4]");
				note=0;
			} else {

				if (!p2.get().toString().equals("[0, 1, 3, 4, 2]")) {
					System.out.println("   Aie... new OrdreTotal("+Arrays.toString(t2)+") initialise la liste a "+p2.get().toString()+" au lieu de [0, 1, 3, 4, 2]");
					note=0;
				} else {
					if (!p3.get().toString().equals("[3, 2, 0, 1, 4]")) {
						System.out.println("   Aie... new OrdreTotal("+Arrays.toString(t3)+") initialise la liste a "+p3.get().toString()+" au lieu de [3, 2, 0, 1, 4]");
						note=0;
					} else {
						if (!p4.get().toString().equals("[0, 1]")) {
							System.out.println("   Aie... new OrdreTotal("+Arrays.toString(t4)+") initialise la liste a "+p4.get().toString()+" au lieu de [0, 1]");
							note=0;
						} else {
							if (!p5.get().toString().equals("[1, 0]")) {
								System.out.println("   Aie... new OrdreTotal("+Arrays.toString(t5)+") initialise la liste a "+p5.get().toString()+" au lieu de [1, 0]");
								note=0;
							} else {

								System.out.println("   Ok. Votre implementation passe le test");

							}
						}
					}
				}
			}
		}
		return note;
	}

	public static int testNext() {
		int note=100;
		System.out.println("\nTest de la methode next de OrdreTotal");
		Class cl = getClass("ordreTotal.OrdreTotal");

		Field[] fields = cl.getDeclaredFields();
		boolean inchange = fields.length==1;
		if (!inchange) {
			System.out.println("   Aie... vous ne devez pas modifier l'etat. Il y a "+fields.length+" variables d'instance declarees alors qu'il ne devrait y avoir qu'une List");
			note=0;
		} else {
			for (Field f : fields) {
				if (!f.getType().toString().equals("interface java.util.List")) {
					System.out.println("   Aie... vous ne devez pas modifier l'etat. Vous avez declare une variable d'instance de type "+f.getType()+" alors qu'il ne devrait y avoir qu'une List pour variable d'instance");
					note=0;
					inchange=false;
				}
			}
		}
		if (note>0) {
			int[][] t = {{3, 2, 0, 1, 4},
					{0, 1, 3, 4, 2},
					{4, 3, 2, 1, 0},
					{0, 1},
					{1, 0}};
			ArrayList<ArrayList<Integer>> l = new ArrayList<ArrayList<Integer>>();
			OrdreTotal[] to = new OrdreTotal[t.length];
			for (int i=0; i<t.length; i++) {
				ArrayList<Integer> liste = new ArrayList<Integer>();
				for (Integer in : t[i]) {
					liste.add(in);
				}
				l.add(liste);
				to[i]=new OrdreTotal(t[i]);
				to[i].set(liste);
			}

			if (to[0].next(3)!=2) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(0)+" next(3) retourne "+to[0].next(3)+" au lieu de 2");
				note=0;
			} else if (to[0].next(2)!=0) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(0)+" next(2) retourne "+to[0].next(2)+" au lieu de 0");
				note=0;
			} else if (to[0].next(0)!=1) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(0)+" next(0) retourne "+to[0].next(0)+" au lieu de 1");
				note=0;
			} else if (to[0].next(1)!=4) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(0)+" next(1) retourne "+to[0].next(1)+" au lieu de 4");
				note=0;
			} else if (to[0].next(4)!=-1) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(0)+" next(4) retourne "+to[0].next(4)+" au lieu de -1");
				note=0;
			} else if (to[1].next(3)!=4) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(1)+" next(3) retourne "+to[1].next(3)+" au lieu de 4");
				note=0;
			} else if (to[1].next(2)!=-1) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(1)+" next(2) retourne "+to[1].next(2)+" au lieu de -1");
				note=0;
			} else if (to[1].next(0)!=1) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(1)+" next(0) retourne "+to[1].next(0)+" au lieu de 1");
				note=0;
			} else if (to[1].next(1)!=3) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(1)+" next(1) retourne "+to[1].next(1)+" au lieu de 3");
				note=0;
			} else if (to[1].next(4)!=2) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(1)+" next(4) retourne "+to[1].next(4)+" au lieu de 2");
				note=0;
			} else if (to[2].next(3)!=2) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(2)+" next(3) retourne "+to[2].next(3)+" au lieu de 2");
				note=0;
			} else if (to[2].next(2)!=1) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(2)+" next(2) retourne "+to[2].next(2)+" au lieu de 1");
				note=0;
			} else if (to[2].next(0)!=-1) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(2)+" next(0) retourne "+to[2].next(0)+" au lieu de -1");
				note=0;
			} else if (to[2].next(1)!=0) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(2)+" next(1) retourne "+to[2].next(1)+" au lieu de 0");
				note=0;
			} else if (to[2].next(4)!=3) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(2)+" next(4) retourne "+to[2].next(4)+" au lieu de 3");
				note=0;
			} else if (to[2].next(-1)!=-1) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(2)+" next(-1) retourne "+to[2].next(-1)+" au lieu de -1");
				note=0;
			} else if (to[3].next(0)!=1) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(3)+" next(0) retourne "+to[3].next(0)+" au lieu de 3");
				note=0;
			} else if (to[3].next(1)!=-1) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(3)+" next(1) retourne "+to[3].next(1)+" au lieu de -1");
				note=0;
			} else {
				System.out.println("   Ok. Votre implementation passe le test");
			}
		}
		return note;
	}


	public static int testInf() {
		int note=100;
		System.out.println("\nTest de la methode inferieurOuEgal de OrdreTotal");
		Class cl = getClass("ordreTotal.OrdreTotal");

		Field[] fields = cl.getDeclaredFields();
		boolean inchange = fields.length==1;
		if (!inchange) {
			System.out.println("   Aie... vous ne devez pas modifier l'etat. Il y a "+fields.length+" variables d'instance declarees alors qu'il ne devrait y avoir qu'une List");
			note=0;
		} else {
			for (Field f : fields) {
				if (!f.getType().toString().equals("interface java.util.List")) {
					System.out.println("   Aie... vous ne devez pas modifier l'etat. Vous avez declare une variable d'instance de type "+f.getType()+" alors qu'il ne devrait y avoir qu'une List pour variable d'instance");
					note=0;
					inchange=false;
				}
			}
		}
		if (note>0) {
			int[][] t = {{3, 2, 0, 1, 4},
					{0, 1, 3, 4, 2},
					{4, 3, 2, 1, 0},
					{0, 1},
					{1, 0}};
			ArrayList<ArrayList<Integer>> l = new ArrayList<ArrayList<Integer>>();
			OrdreTotal[] to = new OrdreTotal[t.length];
			for (int i=0; i<t.length; i++) {
				ArrayList<Integer> liste = new ArrayList<Integer>();
				for (Integer in : t[i]) {
					liste.add(in);
				}
				l.add(liste);
				to[i]=new OrdreTotal(t[i]);
				to[i].set(liste);
			}

			if (!to[0].inferieurOuEgal(3, 2)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(0)+" inferieurOuEgal(3, 2) retourne false au lieu de true");
				note=0;
			} else if (!to[0].inferieurOuEgal(3, 0)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(0)+" inferieurOuEgal(3, 0) retourne false au lieu de true");
				note=0;
			} else if (!to[0].inferieurOuEgal(3, 1)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(0)+" inferieurOuEgal(3, 1) retourne false au lieu de true");
				note=0;
			} else if (!to[0].inferieurOuEgal(3, 4)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(0)+" inferieurOuEgal(3, 4) retourne false au lieu de true");
				note=0;
			} else if (!to[0].inferieurOuEgal(2, 0)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(0)+" inferieurOuEgal(2, 0) retourne false au lieu de true");
				note=0;
			} else if (!to[0].inferieurOuEgal(2, 1)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(0)+" inferieurOuEgal(2, 1) retourne false au lieu de true");
				note=0;
			} else if (!to[0].inferieurOuEgal(2, 4)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(0)+" inferieurOuEgal(2, 4) retourne false au lieu de true");
				note=0;
			} else if (!to[0].inferieurOuEgal(0, 1)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(0)+" inferieurOuEgal(0, 1) retourne false au lieu de true");
				note=0;
			} else if (!to[0].inferieurOuEgal(0, 4)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(0)+" inferieurOuEgal(0, 4) retourne false au lieu de true");
				note=0;
			} else if (!to[0].inferieurOuEgal(1, 4)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(0)+" inferieurOuEgal(1, 4) retourne false au lieu de true");
				note=0;
			} else if (to[0].inferieurOuEgal(0, 3)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(0)+" inferieurOuEgal(0, 3) retourne true au lieu de false");
				note=0;
			} else if (!to[1].inferieurOuEgal(0, 1)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(1)+" inferieurOuEgal(0, 1) retourne false au lieu de true");
				note=0;
			} else if (!to[1].inferieurOuEgal(0, 3)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(1)+" inferieurOuEgal(0, 3) retourne false au lieu de true");
				note=0;
			} else if (!to[1].inferieurOuEgal(0, 4)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(1)+" inferieurOuEgal(0, 4) retourne false au lieu de true");
				note=0;
			} else if (!to[1].inferieurOuEgal(0, 2)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(1)+" inferieurOuEgal(0, 2) retourne false au lieu de true");
				note=0;
			} else if (!to[1].inferieurOuEgal(1, 3)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(1)+" inferieurOuEgal(1, 3) retourne false au lieu de true");
				note=0;
			} else if (!to[1].inferieurOuEgal(1, 4)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(1)+" inferieurOuEgal(1, 4) retourne false au lieu de true");
				note=0;
			} else if (!to[1].inferieurOuEgal(1, 2)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(1)+" inferieurOuEgal(1, 2) retourne false au lieu de true");
				note=0;
			} else if (!to[1].inferieurOuEgal(3, 4)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(1)+" inferieurOuEgal(3, 4) retourne false au lieu de true");
				note=0;
			} else if (!to[1].inferieurOuEgal(3, 2)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(1)+" inferieurOuEgal(3, 2) retourne false au lieu de true");
				note=0;
			} else if (!to[1].inferieurOuEgal(4, 2)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(1)+" inferieurOuEgal(4, 2) retourne false au lieu de true");
				note=0;
			} else if (to[1].inferieurOuEgal(2, 0)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(1)+" inferieurOuEgal(2, 0) retourne true au lieu de false");
				note=0;
			} else  if (to[1].inferieurOuEgal(2, 1)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(1)+" inferieurOuEgal(2, 1) retourne true au lieu de false");
				note=0;
			} else  if (to[1].inferieurOuEgal(4, 1)) {
				System.out.println("   Aie... sur l'ordre correspondant a "+l.get(1)+" inferieurOuEgal(4, 1) retourne true au lieu de false");
				note=0;
			} else {
				System.out.println("   Ok. Votre implementation passe le test");
			}
		}
		return note;
	}//{0, 1, 3, 4, 2},
	
	public static void main(String[] args) {
		int nTestsConstructeur = 0;
		int nTestsNext = 0;
		int nTestsInf = 0;

		try {
			nTestsConstructeur = testConstructeur();
			System.out.println("--> "+nTestsConstructeur+"/100");
		} catch(Throwable e) {
			afficheThrowable(e, "testConstructeur");
		}
		finally {
			try {
				nTestsNext = testNext();
				System.out.println("--> "+nTestsNext+"/100");
			} catch(Throwable e) {
				afficheThrowable(e, "testNext");
			}
			finally {
				try {
					nTestsInf = testInf();
					System.out.println("--> "+nTestsInf+"/100");
				} catch(Throwable e) {
					afficheThrowable(e, "testInf");
				}
				finally {


				}
			}
		}
	}

	public static String toString(int[] t) {
		String res = "{";
		for (int i=0; i<t.length-1; i++) {
			res += t[i]+", ";
		}
		if (t.length>0) {
			res+=t[t.length-1];
		}
		return res+"}";
	}

	public static boolean equals(int[] t1, int[]t2, int pos) {
		return  (pos==0) ? true : t1[pos-1]==t2[pos-1] && equals(t1, t2, pos-1);
	}
	public static boolean equals(int[]t1, int[]t2) {
		return  t1!=null && t2!=null && t1.length==t2.length && equals(t1, t2, t1.length);
	}
	public void setClipboardContents(String s) {
		StringSelection ss = new StringSelection(s);
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		cb.setContents(ss, this);
	}
	public void lostOwnership(Clipboard arg0, Transferable arg1) {
		System.out.println("<<< copie dans le presse-papier >>>");
	}

	public static void setClipboardContentsStatic(String s) {
		TestsOrdreTotal t = new TestsOrdreTotal();
		t.setClipboardContents(s);
	}
}