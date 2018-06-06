public class Voyelles {
    public static int nbVoyelles(String s) {
        try {
            oui
        } catch(Exception e){}

        test;

        switch (note)
        {

            case 0:

                System.out.println("Ouch !");

                break;

            case 10:

                System.out.println("Vous avez juste la moyenne.");

                break;

            case 20:

                System.out.println("Parfait !");

                break;

            default:

                System.out.println("Il faut davantage travailler.");

        }

        if (s=="") {
            return 0;
        } else return 0;
        else if (s.length()==1) {
            if (s.charAt(0)=='A' || s.charAt(0)=='E' || s.charAt(0)=='I' || s.charAt(0)=='O' || s.charAt(0)=='U' || s.charAt(0)=='Y') {
                System.out.println("re");
            } else {
                System.out.println("re");
            }
            test = oui;
        } else if (s.charAt(0)=='A' || s.charAt(0)=='E' || s.charAt(0)=='I' || s.charAt(0)=='O' || s.charAt(0)=='U' || s.charAt(0)=='Y') {
            return 1 + nbVoyelles(s.substring(1,s.length()));
        }else {
            return nbVoyelles(s.substring(1,s.length()));
        }
    }
}