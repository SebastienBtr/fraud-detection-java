package comparator;

import org.junit.Before;
import org.junit.Test;
import parser.ProjectParser;

import javax.swing.tree.DefaultMutableTreeNode;

public class MethodComparatorTest
{
    private DefaultMutableTreeNode tree;

    @Before
    public void setUp()
    {
       tree = ProjectParser.parseFile("src/test/data/comparator/MethodComparator");
    }

    @Test
    public void testSameMethods(){
        DefaultMutableTreeNode  method1 = tree.getNextNode();
        DefaultMutableTreeNode method2 = tree.getNextNode();
      int grade =  MethodComparator.compare(method1,method2);
        //System.out.println(method1.toString()+  "\n"+method2.toString());
        System.out.println(grade);
    }

    @Test
    public void testDifferentMethods(){
        DefaultMutableTreeNode  method1 = tree.getNextNode();
        DefaultMutableTreeNode method2 = method1.getNextSibling();
        int grade =  MethodComparator.compare(method1,method2);
        //System.out.println(method1.toString()+  "\n"+method2.toString());
        System.out.println(grade);
    }

}
