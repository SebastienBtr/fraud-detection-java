package comparator;

import student.ClassFile;
import student.Student;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

public class StudentComparator {

    public static void compareStudents(List<Student> students,Student teacherFiles) {

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
        int similarities = 0 ;
        for (DefaultMutableTreeNode treeSt1 : student1Trees) {

            for (DefaultMutableTreeNode treeSt2 : student2Trees) {

                if (classNameAreGiven && classNameMatched(treeSt1, treeSt2)) {
                    similarities += ClassFileComparator.compare(treeSt1, treeSt2);

                } else {
                    similarities += ClassFileComparator.compare(treeSt1, treeSt2);
                }
            }
        }
        student2.getScores().put(student1.getName(),similarities);
        student1.getScores().put(student2.getName(),similarities);
    }



    private static boolean classNameMatched(DefaultMutableTreeNode tree1, DefaultMutableTreeNode tree2) {

        ClassFile file1 = (ClassFile) tree1.getNextNode().getUserObject();
        ClassFile file2 = (ClassFile) tree2.getNextNode().getUserObject();

        return file1.getName().equals(file2.getName());
    }
}
