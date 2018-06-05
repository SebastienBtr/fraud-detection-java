import launcher.Launcher;
import student.ClassFile;
import student.Student;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

public class TestLauncher {

    public static void main(String[] args) {

        Launcher.init("src/test/data/PROG_IMP_JAVA-Lien de depot TP10note-48285_2017_TP10Note.zip");
        Launcher.parseFiles();
        List<Student> students = Launcher.getStudents();
        System.out.println("STUDENTS : " + students.size());
        showResult(students);
    }

    private static void showResult(List<Student> students) {

        for (Student student : students) {

            System.out.println("============== STUDENT " + student.getDirectoryPath() + " ==============");
            List<DefaultMutableTreeNode> trees = student.getFileTrees();
            System.out.println(trees.size());

            /*for (DefaultMutableTreeNode tree : trees) {
                ClassFile file = (ClassFile) tree.getNextNode().getUserObject();
                System.out.println(file.getName());
            }*/
        }

    }
}
