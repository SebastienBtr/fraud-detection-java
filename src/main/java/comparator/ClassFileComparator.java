package comparator;

import launcher.ConfigFile;
import student.ClassFile;
import util.TreeNodeUtils;

import javax.swing.tree.DefaultMutableTreeNode;


public class ClassFileComparator {

    /**
     * Count the number of similarities between two clasFiles
     *
     * @param class1
     * @param class2
     * @return
     */
    public static int compare(DefaultMutableTreeNode class1, DefaultMutableTreeNode class2) {
        /*System.out.println(class1);
        System.out.println(class2);
*/
        int similarities = 0;
        boolean classNameAreGiven = ConfigFile.classNameAreGiven;
        boolean methodNameAreGiven = ConfigFile.methodNamesAreGiven;

        if (!classNameAreGiven && classNameMatched(class1, class2)) {
            similarities = Similarities.SAME_STRUCTURE_SAME_SPOT;
        }

        if (!class1.isLeaf() && !class2.isLeaf()) {

            DefaultMutableTreeNode currentChild1;
            DefaultMutableTreeNode currentChild2 = class2.getNextNode();

            while (currentChild2 != null) {

                if (methodNameAreGiven) {

                    currentChild1 = TreeNodeUtils.contains(class1, currentChild2.getUserObject());

                    if (currentChild1 != null) {
                        similarities += MethodComparator.compare(currentChild1, currentChild2);
                    }

                } else {

                    currentChild1 = class1.getNextNode();

                    while (currentChild1 != null) {
                        similarities += MethodComparator.compare(currentChild1, currentChild2);
                        currentChild1 = currentChild1.getNextSibling();
                    }
                }

                currentChild2 = currentChild2.getNextSibling();
            }
        }

        return similarities;
    }

    private static boolean classNameMatched(DefaultMutableTreeNode tree1, DefaultMutableTreeNode tree2) {

        if (tree1.getNextNode() != null && tree2.getNextNode() != null) {
            ClassFile file1 = (ClassFile) tree1.getNextNode().getUserObject();
            ClassFile file2 = (ClassFile) tree2.getNextNode().getUserObject();

            return file1.getName().equals(file2.getName());
        }
        else {
            return false;
        }
    }


}
