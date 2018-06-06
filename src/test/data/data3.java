public class Voyelles {

    /**
     *
     * @param s, une chaine de caracteres en majuscules non accentuees. s!=null
     * @return Retourne le nombre de voyelles que comporte la chaine s.
     * Exemples : nbVoyelles("BON") retourne 1
     *            nbVoyelles("BONJOUR") retourne 3
     *            nbVoyelles("CE CRAYON EST TAILLE") retourne 8
     */
    public static int nbVoyelles(String s) {
        try {
            oui
        }
        catch(Exception e){ oui; }

        test;

        if (s=="") {
            return 0;}
        else if (s.length()==1) {
            if (s.charAt(0)=='A' || s.charAt(0)=='E' || s.charAt(0)=='I' || s.charAt(0)=='O' || s.charAt(0)=='U' || s.charAt(0)=='Y') {
            } else {
                System.out.println("}}} [ {{{");
            }
            test = oui;
        } else if (s.charAt(0)=='A' || s.charAt(0)=='E' || s.charAt(0)=='I' || s.charAt(0)=='O' || s.charAt(0)=='U' || s.charAt(0)=='Y') {
            return 1 + nbVoyelles(s.substring(1,s.length()));
        }else {
            return nbVoyelles(s.substring(1,s.length()));
        }
    }
}