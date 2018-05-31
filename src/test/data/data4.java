


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
import java.util.Iterator;
import java.util.List;

public class TestsTrigonometrie implements ClipboardOwner {
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
            //System.out.println(Arrays.toString(c.getConstructors()));
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
            //System.out.println("index = "+index);
            fic = fic.substring(index);
            fic = withoutStartingId(fic);
            if (fic.length()>0) fic = fic.substring(1);
            //System.out.println(" trouve : "+fic);
            //int ip=index+1;
            int p = 0;
            while (//ip<fic.length() &&
                    p<args.length
                            && fic.charAt(0)!=')') {
                //fic = removeStartingChars(fic, ' ');
                //fic = removeStartingChars(fic, '\t');
                if (startingId(fic).equals(args[p])) {
                    //System.out.println("avant="+fic);
                    //System.out.println("un seul = "+withoutStartingId(fic));
                    fic = withoutStartingId( withoutStartingId(fic));
                    //System.out.println("apres="+fic);
                    p++;
                } else {
                    //	System.out.println("aie... fic="+fic+"\n ne commence pas par "+args[p]);
                    return constructeur(fic, nomClasse, args);
                }
            }
            //System.out.println("FFic="+fic);
            fic =removeStartingChars(fic,' ');
            fic = removeStartingChars(fic, '\t');
            //System.out.println(" trouve : "+fic);System.exit(0);
            if (p<args.length || fic.length()==0 || fic.charAt(0)!=')') {
                //System.out.println(" trouve : "+fic);//System.exit(0);
                return  constructeur(fic, nomClasse, args);
            }
            int i= 0;//index+1;
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
                //return fic.substring(index, i);
                return fic.substring(fic.indexOf("{"), i);
            } else {
                return "";
            }
        }
    }
    public static String methode(String fic, String nomMethode) {
        //System.out.println("fic recu par methode() : "+fic);
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
                //	System.out.println("fic renvoye par methode() : "+fic.substring(index, i));
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

            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        int i=0;

        while (i<l.size()) {
            if  ( l.get(i).indexOf("//")>=0) {
                m.add(l.get(i).substring(0, l.get(i).indexOf("//")));
            } else {
                m.add(l.get(i));
            }
            i++;
        }
        l=m;
        m=new ArrayList<String>();
        i=0;
        while (i<l.size()) {
            int debut = l.get(i).indexOf("/*");
            int fin = l.get(i).indexOf("*/");
            if (debut>=0 && l.get(i).indexOf("//")>l.get(i).indexOf("/*")) {
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

    public static boolean fieldsDeclaredPrivate(//String file,
                                                Class c) {
        //	boolean res=true;
        //	List<String> lignes = lignesSansCommentaires(file);
        Field[] fields = c.getDeclaredFields();
        for (Field f : fields) {
            if (!Modifier.isPrivate(f.getModifiers())) {
                return false;
            }
        }
        return true;
    }
    /*	public static boolean fieldsDeclaredPrivate(String file, Class c) {
        boolean res=true;
        List<String> lignes = lignesSansCommentaires(file);
        Field[] fields = c.getDeclaredFields();
        for (Field f : fields) {
            int lig =0;
            while (lig<lignes.size() && !lignes.get(lig).contains(f.getName())) {
                lig++;
            }
            if (lig<lignes.size() && !lignes.get(lig).contains("private")) {
                res=false;
            }
        }
        return res;
    }*/
    public static boolean hasFieldOfType(Class c, Class type ) {
        boolean res=true;
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
                //System.out.println(c.getPackage().getName());
                return getClass(mots.get(pos-1))!=null ||  getClass(c.getPackage().getName()+"."+mots.get(pos-1))!=null;
            }
        } else {
            return false;
        }
    }
    public static boolean respecteDeveloppementEnCouche(String file, Class c, String methode) {
        String met = methode(sansCommentaires(file),methode);

        //System.out.println(" ---- avant retrait des methodes : "+met);
        Method[] methodes = c.getDeclaredMethods();
        String sans = met;
        for (Method m : methodes) {
            sans = remove(sans, m.getName());
        }
        //System.out.println(" ---- apres retrait des methodes : "+sans);
        boolean couche = true;
        Field[] fields = c.getDeclaredFields();

        int i=0;
        while (couche && i<fields.length) {
            //System.out.println("field "+fields[i].getName()+"\n"+sans);
            //System.out.println("mots :"+getMots(sans));
            if (getMots(sans).contains(fields[i].getName())){// && sans.contains(fields[i].getName()+".")) {
                if (!estUneVariableLocale(file, c, methode,fields[i].getName() )) {
                    couche=false;
                }
                //System.out.println("field "+fields[i].getName()+" trouve");
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
    public static int testSinusRec() {
        int note=100;

        System.out.println("Test de sinusRec : ");
        boolean ok=true;
        for (double angle=0.0; ok && angle<3.0; angle+=0.01) {
            ok = Math.abs(Trigonometrie.sinusRec(angle)-Math.sin(angle))<0.01;
            if (!ok) {
                System.out.println("   Aie... sinusRec("+angle+") retourne "+Trigonometrie.sinusRec(angle)+" au lieu de "+Math.sin(angle));
                note=0;
            }
        }
        if (note>0) {
            String met = methode(sansCommentaires("src"+File.separator+"Trigonometrie.java"),"sinusRec").substring(2);
            if (!met.contains("sinusRec")) {
                System.out.println("   Aie... votre implementation de sinusRec doit etre RECURSIVE");
                note=0;
            } else if (met.contains("Math.")) {
                System.out.println("   Aie... vous ne devez pas utiliser les methodes de Math");
                note=0;
            }
        }

        if (note==100) {
            System.out.println("  Ok. Votre code passe le test");
        }
        return note;
    }
    public static int testCosinusRec() {
        int note=100;

        System.out.println("\nTest de cosinusRec : ");
        boolean ok=true;
        for (double angle=0.0; ok && angle<3.0; angle+=0.01) {
            ok = Math.abs(Trigonometrie.cosinusRec(angle)-Math.cos(angle))<0.01;
            if (!ok) {
                System.out.println("   Aie... cosinusRec("+angle+") retourne "+Trigonometrie.cosinusRec(angle)+" au lieu de "+Math.cos(angle));
                note=0;
            }
        }
        if (note>0) {
            String met = methode(sansCommentaires("src"+File.separator+"Trigonometrie.java"),"cosinusRec").substring(2);
            if (!met.contains("cosinusRec")) {
                System.out.println("   Aie... votre implementation de cosinusRec doit etre RECURSIVE");
                note=0;
            } else if (met.contains("Math.")) {
                System.out.println("   Aie... vous ne devez pas utiliser les methodes de Math");
                note=0;
            }
        }
        if (note==100) {
            System.out.println("  Ok. Votre code passe le test");
        }
        return note;
    }

    public static int testCosinusIte() {
        int note=100;
/*		System.out.println("\nTest de cosinusIte : ");
		boolean ok=true;
		for (double angle=0.0; ok && angle<3.0; angle+=0.01) {
			ok = Math.abs(Trigonometrie.sinusRec(angle)-Math.sin(angle))<0.01;
			if (!ok) {
				System.out.println("   Aie... cosinusIte("+angle+") retourne "+Trigonometrie.cosinusIte(angle)+" au lieu de "+Math.cos(angle));
				note=0;
			}
		}
		if (note>0) {
			String met = methode(sansCommentaires("src"+File.separator+"Trigonometrie.java"),"cosinusIte").substring(2);
			if (met.contains("cosinusIte")) {
				System.out.println("   Aie... votre implementation de cosinusIte NE doit PAS etre RECURSIVE");
				note=0;
			} else if (met.contains("Math.")) {
				System.out.println("   Aie... vous ne devez pas utiliser les methodes de Math");
				note=0;
			}
		}
		if (note==100) {
			System.out.println("  Ok. Votre code passe le test");
		}*/
        return note;
    }

    public static void main(String[] args) {
        int ntestSinusRec = 0;
        int ntestCosinusRec = 0;
        int ntestCosinusIte = 0;
        try {
            ntestSinusRec = testSinusRec();
            System.out.println("--> "+ntestSinusRec+"/100");
        } catch(Throwable e) {
            afficheThrowable(e, "testSinusRec");
        }
        finally {
            try {
                ntestCosinusRec = testCosinusRec();
                System.out.println("--> "+ntestCosinusRec+"/100");
            } catch(Throwable e) {
                afficheThrowable(e, "testCosinusRec");
            }
            finally {
/*				try {
					ntestCosinusIte = testCosinusIte();
					System.out.println("--> "+ntestCosinusIte+"/100");
				} catch(Throwable e) {
					afficheThrowable(e, "testCosinusIte");
				}
				finally {

				}*/
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
        TestsTrigonometrie t = new TestsTrigonometrie();
        t.setClipboardContents(s);
    }
}