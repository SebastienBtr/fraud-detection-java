public class Example {


    public static String test1;
    private Boolean test2;


    public static boolean equals(int[] t1, int[] t2) {

        if (t1.length != t2.length) {
            return false;
        }

        boolean id = true;
        int i =0;

        while (id && i<t1.length) {
            id = (t1[i]==t2[i]);
            i++;
        }

        return id;
    }


    public static int nbOccurrences(int[] tab, int x) {

        int occ = 0;

        for (int i = 0; i<tab.length; i++) {

            if (tab[i] == x) {
                occ = occ + 1;
            }

        }

        return occ;
    }


}