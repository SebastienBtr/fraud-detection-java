package comparator;

import org.junit.Before;
import org.junit.Test;
import parser.ProjectParser;

import javax.swing.tree.DefaultMutableTreeNode;

public class ClassFileComparatorTest
{
    private DefaultMutableTreeNode class1;
    private DefaultMutableTreeNode class2;

    @Before
    public void setUp()
    {
        //class1 = ProjectParser.parseFile("src/test/data/comparator/ClassFile1").getNextNode();
        //class2 = ProjectParser.parseFile("src/test/data/comparator/ClassFile2").getNextNode();
    }

    @Test
    public void testSameMethods(){

        int grade =  ClassFileComparator.compare(class1,class1);

        System.out.println(grade);
    }

    @Test
    public void testDifferentMethods(){
        int grade =  ClassFileComparator.compare(class1,class2);

        System.out.println(grade);
    }

}
