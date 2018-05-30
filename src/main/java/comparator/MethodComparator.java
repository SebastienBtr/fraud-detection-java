package comparator;

import student.algorithm_structure.Loop;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Enumeration;

public class MethodComparator
{
    public static int compare(DefaultMutableTreeNode structure1, DefaultMutableTreeNode structure2)
    {
        return orderStructures(structure1,structure2,0);
    }


    /**
     *  Compare two tree of structures
     * @param structure1
     * @param structure2
     * @return a grade of similarity
     */
    private static int orderStructures(DefaultMutableTreeNode structure1, DefaultMutableTreeNode structure2, int grade)
        {
//        System.out.println(structure1.getUserObject().toString());
//        System.out.println(structure2.getUserObject().toString());
//        System.out.println(grade);
        if(!structure1.isLeaf() && !structure2.isLeaf())
        {
            if (structure1.getChildCount() > structure2.getChildCount())
            {
                return compareStructureOrdered(structure1, structure2, grade);
            } else
            {
                return compareStructureOrdered(structure2, structure1, grade);
            }
        }
        return grade;

    }

    /**
     * Compare two trees of structure with the bigger one as the first parameter
     * @param longer must not be a leaf
     * @param shorter must not be a leaf
     * @return grade
     */
    private static int compareStructureOrdered(DefaultMutableTreeNode longer, DefaultMutableTreeNode shorter,int grade)
    {
        DefaultMutableTreeNode longerCurrentChild = longer.getNextNode();
        DefaultMutableTreeNode shorterCurrentCHild =  shorter.getNextNode();
        for (int i = 0; i < longer.getChildCount() ; i++) {

            if ( i < shorter.getChildCount())
            {
                if (longerCurrentChild.getUserObject().equals(shorterCurrentCHild.getUserObject()))
                {
                    grade = structureContentSimilarities(longerCurrentChild, shorterCurrentCHild, grade, Similarities.SAME_STRUCTURE_SAME_SPOT);

                }
                shorterCurrentCHild = shorterCurrentCHild.getNextSibling();
            }
            else
            {
                shorterCurrentCHild = null;
                DefaultMutableTreeNode currentNode;
                for (Enumeration e = shorter.children(); e.hasMoreElements() && shorterCurrentCHild == null;) {
                    currentNode = (DefaultMutableTreeNode) e.nextElement();
                    if (currentNode.getUserObject().equals(longerCurrentChild.getUserObject())) {
                        shorterCurrentCHild = currentNode;
                    }
                }
                if(shorterCurrentCHild != null){
                    grade = structureContentSimilarities(longerCurrentChild, shorterCurrentCHild, grade,Similarities.CONTAIN_STRUCTURE);
                }

            }
            longerCurrentChild  =  longerCurrentChild.getNextSibling();
        }
      return grade;
    }

    /**
     * Grade closeness between two similar structure
     * @param structure1
     * @param structure2
     * @param grade
     * @return grade
     */
    private static int structureContentSimilarities(DefaultMutableTreeNode structure1, DefaultMutableTreeNode structure2, int grade,int similarity)
    {
        if (structure1.getUserObject().getClass().equals(Loop.class))
        {
            if(((Loop)structure1.getUserObject()).getName().equals(((Loop)structure2.getUserObject()).getName()))
            {
                grade += similarity * Similarities.SAME_LOOP;
            }
        }
        else
        {
            grade += similarity;
        }

        grade = orderStructures(structure1,structure2,grade);


        
        return grade;
    }

}
