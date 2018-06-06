package comparator;

import launcher.ConfigFile;
import student.algorithm_structure.Loop;
import util.TreeNodeUtils;

import javax.swing.tree.DefaultMutableTreeNode;


public class MethodComparator {

    /**
     * @param method1
     * @param method2
     * @return
     */
    public static int compare(DefaultMutableTreeNode method1, DefaultMutableTreeNode method2) {
/*        System.out.println(method1);
        System.out.println(method2);*/

        boolean methodNamesAreGiven = ConfigFile.methodNamesAreGiven;
        int similarities = 0;

        if (!methodNamesAreGiven && methodMatched(method1, method2)) {
            similarities = Similarities.SAME_STRUCTURE_SAME_SPOT;
        }

        return orderStructures(method1, method2, similarities);
    }


    /**
     * Compare two tree of structures
     *
     * @param structure1
     * @param structure2
     * @return number of similarity
     */
    private static int orderStructures(DefaultMutableTreeNode structure1, DefaultMutableTreeNode structure2, int similarities) {

        if (!structure1.isLeaf() && !structure2.isLeaf()) {

            if (structure1.getChildCount() > structure2.getChildCount()) {
                return compareStructureOrdered(structure1, structure2, similarities);

            } else {
                return compareStructureOrdered(structure2, structure1, similarities);
            }
        }
        return similarities;
    }

    /**
     * Compare two trees of structure with the bigger one as the first parameter
     *
     * @param longer  must not be a leaf
     * @param shorter must not be a leaf
     * @return similarities
     */
    private static int compareStructureOrdered(DefaultMutableTreeNode longer, DefaultMutableTreeNode shorter, int similarities) {

        DefaultMutableTreeNode longerCurrentChild = longer.getNextNode();
        DefaultMutableTreeNode shorterCurrentCHild = shorter.getNextNode();
        DefaultMutableTreeNode shorterCurrentCHildLoop;


        for (int i = 0; i < longer.getChildCount(); i++) {

            if (longerCurrentChild.getUserObject().equals(shorterCurrentCHild.getUserObject())) {
                if (i < shorter.getChildCount()-1) {
                    similarities = structureContentSimilarities(longerCurrentChild, shorterCurrentCHild, similarities, Similarities.SAME_STRUCTURE_SAME_SPOT);
                }

            }
            else{

                shorterCurrentCHildLoop = TreeNodeUtils.contains(shorter, longerCurrentChild.getUserObject());
                if (shorterCurrentCHildLoop != null) {
                    similarities = structureContentSimilarities(longerCurrentChild, shorterCurrentCHildLoop, similarities, Similarities.CONTAIN_STRUCTURE);
                }
            }

            if (i < shorter.getChildCount()-1) {
                shorterCurrentCHild = shorterCurrentCHild.getNextSibling();
            }
            longerCurrentChild = longerCurrentChild.getNextSibling();
        }

        return similarities;
    }

    /**
     * similarities closeness between two similar structure
     *
     * @param structure1
     * @param structure2
     * @param similarities
     * @return similarities
     */
    private static int structureContentSimilarities(DefaultMutableTreeNode structure1, DefaultMutableTreeNode structure2, int similarities, int similarity) {

        if (structure1.getUserObject().getClass().equals(Loop.class)) {

            if (LoopsAreEquals(structure1, structure2)) {
               // System.out.println(structure1+"  "+structure2 +" =  "+Similarities.SAME_LOOP*similarity);
                similarities += similarity * Similarities.SAME_LOOP;
            }

        } else {
            //System.out.println(structure1+"  "+structure2 +" =  "+similarity);
            similarities += similarity;
            //TODO if IF verifier l'interieur des brakcets
            //TODO pareill pour tout
        }

        similarities = orderStructures(structure1, structure2, similarities);


        return similarities;
    }

    private static boolean LoopsAreEquals(DefaultMutableTreeNode structure1, DefaultMutableTreeNode structure2) {
        return ((Loop) structure1.getUserObject()).getName().equals(((Loop) structure2.getUserObject()).getName());
    }

    private static boolean methodMatched(DefaultMutableTreeNode method1, DefaultMutableTreeNode method2) {

        return method1.getUserObject().equals(method2.getUserObject());
    }

}
