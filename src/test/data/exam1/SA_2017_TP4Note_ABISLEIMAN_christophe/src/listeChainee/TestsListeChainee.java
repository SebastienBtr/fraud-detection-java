package listeChainee;

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

public class TestsListeChainee implements ClipboardOwner {
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
	public static String toStringListe(ListeChainee l) {
		Cellule c = l.getPremiere();
		String res="[";
		while (c!=null) {
			res=res+c.getVal();
			if (c.getSuivante()!=null) {
				res=res+", ";
			}
			c=c.getSuivante();
		}
		return res+"]";
	}
	public static int testContains() {
		int note=100;
		String before;
		ListeChainee l = new ListeChainee();
		System.out.println("Test de contains");
		before = toStringListe(l);
		if (l.contains(1)) {
			System.out.println("   Aie... sur une liste vide contains(1) retourne true au lieu de false");
			note=0;
		} else {
			if (!before.equals(toStringListe(l))) {
				System.out.println("   Aie... contains ne doit pas modifer la liste. Avant l'appel a contains(1) la liste etait "+before+" et apres l'appel elle est egale a "+toStringListe(l));
				note=0;
			} else {
				l.add(1);
				before = toStringListe(l);
				if (!l.contains(1)) {
					System.out.println("   Aie... sur la liste "+before+" contains(1) retourne false au lieu de true");
					note=0;
				} else {
					if (!before.equals(toStringListe(l))) {
						System.out.println("   Aie... contains ne doit pas modifer la liste. Avant l'appel a contains(1) la liste etait "+before+" et apres l'appel elle est egale a "+toStringListe(l));
						note=0;
					} else {
						if (l.contains(2)) {
							System.out.println("   Aie... sur la liste "+before+" contains(2) retourne true au lieu de false");
							note=0;
						} else {
							l.add(2);
							before = toStringListe(l);
							if (!l.contains(1)) {
								System.out.println("   Aie... sur la liste "+before+" contains(1) retourne false au lieu de true");
								note=0;
							} else {
								if (!before.equals(toStringListe(l))) {
									System.out.println("   Aie... contains ne doit pas modifer la liste. Avant l'appel a contains(1) la liste etait "+before+" et apres l'appel elle est egale a "+toStringListe(l));
									note=0;
								} else {
									if (!l.contains(2)) {
										System.out.println("   Aie... sur la liste "+before+" contains(2) retourne false au lieu de true");
										note=0;
									} else {
										l.add(1);
										before = toStringListe(l);
										if (!l.contains(1)) {
											System.out.println("   Aie... sur la liste "+before+" contains(1) retourne false au lieu de true");
											note=0;
										} else {
											if (!before.equals(toStringListe(l))) {
												System.out.println("   Aie... contains ne doit pas modifer la liste. Avant l'appel a contains(1) la liste etait "+before+" et apres l'appel elle est egale a "+toStringListe(l));
												note=0;
											} else {
												if (!l.contains(2)) {
													System.out.println("   Aie... sur la liste "+before+" contains(2) retourne false au lieu de true");
													note=0;
												} else {
													l.add(3);
													before = toStringListe(l);
													if (!l.contains(1)) {
														System.out.println("   Aie... sur la liste "+before+" contains(1) retourne false au lieu de true");
														note=0;
													} else {
														if (!before.equals(toStringListe(l))) {
															System.out.println("   Aie... contains ne doit pas modifer la liste. Avant l'appel a contains(1) la liste etait "+before+" et apres l'appel elle est egale a "+toStringListe(l));
															note=0;
														} else {
															if (!l.contains(2)) {
																System.out.println("   Aie... sur la liste "+before+" contains(2) retourne false au lieu de true");
																note=0;
															} else {
																if (!l.contains(3)) {
																	System.out.println("   Aie... sur la liste "+before+" contains(3) retourne false au lieu de true");
																	note=0;
																} else {
																	l = new ListeChainee();
																	l.add(3);
																	l.add(2);
																	l.add(1);
																	l.add(6);
																	before = toStringListe(l);
																	if (!l.contains(3)) {
																		System.out.println("   Aie... sur la liste "+before+" contains(3) retourne false au lieu de true");
																		note=0;
																	} else {
																		if (!before.equals(toStringListe(l))) {
																			System.out.println("   Aie... contains ne doit pas modifer la liste. Avant l'appel a contains(3) la liste etait "+before+" et apres l'appel elle est egale a "+toStringListe(l));
																			note=0;
																		} else {
																			if (!l.contains(2)) {
																				System.out.println("   Aie... sur la liste "+before+" contains(2) retourne false au lieu de true");
																				note=0;
																			} else {
																				if (!l.contains(1)) {
																					System.out.println("   Aie... sur la liste "+before+" contains(1) retourne false au lieu de true");
																					note=0;
																				} else {
																					if (!l.contains(6)) {
																						System.out.println("   Aie... sur la liste "+before+" contains(6) retourne false au lieu de true");
																						note=0;
																					} else {
																						System.out.println("   Ok. Votre implementation passe le test");
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return note;
	}
	public static ArrayList<Integer> getist(ListeChainee l) {
		ArrayList<Integer> a = new ArrayList<Integer>();
		Cellule c=l.getPremiere();
		while (c!=null) {
			a.add(c.getVal());
			c=c.getSuivante();
		}
		return a;
	}
	public static ArrayList<Integer> getTriee(ArrayList<Integer>l) {
		ArrayList<Integer> a = new ArrayList<Integer>();
		for (Integer i : l) {
			a.add(new Integer(i));
		}
		Collections.sort(a);
		return a;
	}

	public static int testDoublons() {
		int note=100;
		System.out.println("\nTest de supprimeDoublons");
		ListeChainee l = new ListeChainee();
		l.add(3);
		l.add(2);
		l.add(1);
		l.add(6);
		l.add(2);
		l.add(2);
		l.add(1);
		ArrayList<Integer> bTriee;
		ArrayList<Integer> b;

		String before = toStringListe(l);
		l.supprimeDoublons();

		b=getist(l);
		bTriee = getTriee(b);

		if (!bTriee.toString().equals("[1, 2, 3, 6]")) {
			System.out.println("   Aie... "+before+".supprimerdoublons() aboutit a la liste "+b.toString()+" au lieu d'une permutation de [1, 2, 3, 6]");
			note=0;
		} else {
			l = new ListeChainee();
			l.add(1);
			l.add(3);
			l.add(1);
			l.add(3);
			l.add(2);
			l.add(2);
			l.add(3);
			before = toStringListe(l);
			l.supprimeDoublons();
			b=getist(l);
			bTriee = getTriee(b);

			if (!bTriee.toString().equals("[1, 2, 3]")) {
				System.out.println("   Aie... "+before+".supprimerdoublons() aboutit a la liste "+b.toString()+" au lieu d'une permutation de [1, 2, 3]");
				note=0;
			} else {
				l = new ListeChainee();
				l.add(1);
				l.add(1);
				l.add(1);
				l.add(1);
				l.add(1);
				l.add(1);
				l.add(1);
				before = toStringListe(l);
				l.supprimeDoublons();
				b=getist(l);
				bTriee = getTriee(b);

				if (!bTriee.toString().equals("[1]")) {
					System.out.println("   Aie... "+before+".supprimerdoublons() aboutit a la liste "+b.toString()+" au lieu de [1]");
					note=0;
				} else {
					System.out.println("   Ok. Votre implementation passe le test");
				}
			}
		}
		return note;
	}



	public static void main(String[] args) {
		int nTestsContains = 0;
		int ntestDoublons = 0;
		int ntestEgaux =0;
		int ntestSupprimerToutes=0;
		int ntestSupprimer=0;

		int DeclarationNEtConst=0;
		try {
			nTestsContains = testContains();
			System.out.println("--> "+nTestsContains+"/100");
		} catch(Throwable e) {
			afficheThrowable(e, "testContains");
		}
		finally {

			try {
				ntestDoublons = testDoublons();
				System.out.println("--> "+ntestDoublons+"/100");
			} catch(Throwable e) {
				afficheThrowable(e, "testSupprimerDoublons");
			}
			finally {
				
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
		TestsListeChainee t = new TestsListeChainee();
		t.setClipboardContents(s);
	}
}