package comparator;

import student.File;
import student.Student;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Enumeration;
import java.util.List;

public class StudentComparator {

    public static void compareStudents(List<Student> students) {

        for (int i = 0; i < students.size() - 1; i++) {

            for (int j = i + 1; j < students.size(); j++) {

                compareTwoStudent(students.get(i), students.get(j));
            }
        }
    }

    private static void compareTwoStudent(Student student1, Student student2) {

        //TODO get with config
        boolean classNameAreGiven = true;

        List<DefaultMutableTreeNode> student1Trees = student1.getFileTrees();
        List<DefaultMutableTreeNode> student2Trees = student2.getFileTrees();

        for (DefaultMutableTreeNode treeSt1 : student1Trees) {

            for (DefaultMutableTreeNode treeSt2 : student2Trees) {

                if (classNameAreGiven && classNameMatched(treeSt1, treeSt2)) {
                    analyzeTrees(treeSt1, treeSt2);

                } else {
                    analyzeTrees(treeSt1, treeSt2);
                }
            }
        }
    }

    private static void analyzeTrees(DefaultMutableTreeNode tree1, DefaultMutableTreeNode tree2) {

        Enumeration en1 = tree1.breadthFirstEnumeration();
        Enumeration en2 = tree2.breadthFirstEnumeration();

/*        while (en1.hasMoreElements()) {

            while (en2.hasMoreElements()) {

            }
        }*/

    }

    private static boolean classNameMatched(DefaultMutableTreeNode tree1, DefaultMutableTreeNode tree2) {

        File file1 = (File) tree1.getNextNode().getUserObject();
        File file2 = (File) tree2.getNextNode().getUserObject();

        return file1.getFilePath().equals(file2.getFilePath());
    }
}
