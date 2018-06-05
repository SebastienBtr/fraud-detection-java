package util;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Enumeration;

public class TreeNodeUtils
{

    /**
     * Return the node containing the objetc Null if
     * the tree does not contain the object
     * @param tree
     * @param object
     * @return
     */
    public static DefaultMutableTreeNode contains(DefaultMutableTreeNode tree,Object object){
        DefaultMutableTreeNode returnedNode = null;
        DefaultMutableTreeNode currentNode;

        for (Enumeration e = tree.children(); e.hasMoreElements() && returnedNode == null;) {
            currentNode = (DefaultMutableTreeNode) e.nextElement();

            if (currentNode.getUserObject().equals(object)) {
                returnedNode = currentNode;
            }
        }
        return  returnedNode;
    }
}
