package comparator;

import launcher.ConfigFile;
import student.ClassFile;
import student.Student;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

public class StudentComparator {

    private static int maxScore;

    private StudentComparator() {
        throw new IllegalStateException("Utility class");
    }

    public static List<Student> compareStudents(List<Student> students) {

        for (int i = 0; i < students.size(); i++) {

            compareTwoStudent(students.get(i), students.get(i));

            for (int j = i + 1; j < students.size(); j++) {

                compareTwoStudent(students.get(i), students.get(j));
            }
        }

        return students;
    }

    private static void compareTwoStudent(Student student1, Student student2) {

        boolean classNameAreGiven = ConfigFile.classNameAreGiven;

        List<DefaultMutableTreeNode> student1Trees = student1.getFileTrees();
        List<DefaultMutableTreeNode> student2Trees = student2.getFileTrees();

        int similarities = 0 ;

        for (DefaultMutableTreeNode treeSt1 : student1Trees) {

            for (DefaultMutableTreeNode treeSt2 : student2Trees) {

                if (treeSt1 != null && treeSt2 != null) {
                    if (classNameAreGiven && classNameMatched(treeSt1, treeSt2)) {
                        similarities += ClassFileComparator.compare(treeSt1, treeSt2);

                    } else if (!classNameAreGiven){
                        similarities += ClassFileComparator.compare(treeSt1, treeSt2);
                    }
                }
            }
        }

        if (student2.getDirectoryPath().equals(student1.getDirectoryPath())) { //same students
            maxScore = similarities;
        }

        Double ratio = similarities * 1.0 / maxScore;

        student2.getScores().put(student1.getName(),100 * ratio);
        student1.getScores().put(student2.getName(),100 * ratio);
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
