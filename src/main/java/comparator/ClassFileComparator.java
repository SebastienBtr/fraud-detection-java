package comparator;

import student.ClassFile;

import javax.swing.tree.DefaultMutableTreeNode;


public class ClassFileComparator
{

    /**
     * Count the number of similarities between two clasFiles
     * @param class1
     * @param class2
     * @return
     */
    public static int compare(DefaultMutableTreeNode class1, DefaultMutableTreeNode class2)
    {
        int similarities = 0;
        //TODO config
        boolean methodNamesAreGiven = true;
        if ( !methodNamesAreGiven &&
                ((ClassFile)class1.getUserObject()).getName().equals(
                        ((ClassFile)class2.getUserObject()).getName()))
        {
            similarities = Similarities.SAME_STRUCTURE_SAME_SPOT;
        }
        if(!class1.isLeaf() && !class2.isLeaf())
        {
            DefaultMutableTreeNode currentChild1;
            DefaultMutableTreeNode currentChild2 = class2.getNextNode();

            while (currentChild2 != null)
            {
                currentChild1 = TreeNodeUtils.contains(class1, class2.getUserObject());

                if(currentChild1 != null)
                {
                    similarities += MethodComparator.compare(currentChild1, currentChild2);
                }

                currentChild2 = currentChild2.getNextSibling();
            }
        }
        return similarities;
    }



}
