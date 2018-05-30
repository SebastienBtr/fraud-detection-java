package comparator;

import student.algorithm_structure.Loop;

import javax.swing.tree.DefaultMutableTreeNode;


public class MethodComparator
{

    /**
     *
     * @param method1
     * @param method2
     * @return
     */
    public static int compare(DefaultMutableTreeNode method1, DefaultMutableTreeNode method2)
    {
        int similarities = 0;
        if (method1.getUserObject().equals(method2.getUserObject()))
        {
            similarities = Similarities.SAME_STRUCTURE_SAME_SPOT;
        }
        return orderStructures(method1,method2,similarities);
    }


    /**
     *  Compare two tree of structures
     * @param structure1
     * @param structure2
     * @return  number of similarity
     */
    private static int orderStructures(DefaultMutableTreeNode structure1, DefaultMutableTreeNode structure2, int similarities)
        {
        System.out.println(structure1.getUserObject().toString());
        System.out.println(structure2.getUserObject().toString());
        System.out.println(similarities);
        if(!structure1.isLeaf() && !structure2.isLeaf())
        {
            if (structure1.getChildCount() > structure2.getChildCount())
            {
                return compareStructureOrdered(structure1, structure2, similarities);
            } else
            {
                return compareStructureOrdered(structure2, structure1, similarities);
            }
        }
        return similarities;

    }

    /**
     * Compare two trees of structure with the bigger one as the first parameter
     * @param longer must not be a leaf
     * @param shorter must not be a leaf
     * @return similarities
     */
    private static int compareStructureOrdered(DefaultMutableTreeNode longer, DefaultMutableTreeNode shorter,int similarities)
    {
        DefaultMutableTreeNode longerCurrentChild = longer.getNextNode();
        DefaultMutableTreeNode shorterCurrentCHild =  shorter.getNextNode();
        for (int i = 0; i < longer.getChildCount() ; i++) {

            if ( i < shorter.getChildCount())
            {
                if (longerCurrentChild.getUserObject().equals(shorterCurrentCHild.getUserObject()))
                {
                    similarities = structureContentSimilarities(longerCurrentChild, shorterCurrentCHild, similarities, Similarities.SAME_STRUCTURE_SAME_SPOT);

                }
                shorterCurrentCHild = shorterCurrentCHild.getNextSibling();
            }
            else
            {
                shorterCurrentCHild = TreeNodeUtils.contains(shorter,longerCurrentChild.getUserObject());
                if(shorterCurrentCHild != null){
                    similarities = structureContentSimilarities(longerCurrentChild, shorterCurrentCHild, similarities,Similarities.CONTAIN_STRUCTURE);
                }

            }
            longerCurrentChild  =  longerCurrentChild.getNextSibling();
        }
      return similarities;
    }

    /**
     * similarities closeness between two similar structure
     * @param structure1
     * @param structure2
     * @param similarities
     * @return similarities
     */
    private static int structureContentSimilarities(DefaultMutableTreeNode structure1, DefaultMutableTreeNode structure2, int similarities,int similarity)
    {
        if (structure1.getUserObject().getClass().equals(Loop.class))
        {
            if(((Loop)structure1.getUserObject()).getName().equals(((Loop)structure2.getUserObject()).getName()))
            {
                similarities += similarity * Similarities.SAME_LOOP;
            }
        }
        else
        {
            similarities += similarity;
        }

        similarities = orderStructures(structure1,structure2,similarities);


        
        return similarities;
    }

}
