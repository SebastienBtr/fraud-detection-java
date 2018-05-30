import launcher.Launcher;
import student.ClassFile;
import student.Student;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

public class TestLauncher {

    public static void main(String[] args) {

        List<Student> students = Launcher.init("src/test/data/exam1.zip");

        showResult(students);
    }

    private static void showResult(List<Student> students) {

        for (Student student : students) {

            System.out.println("============== STUDENT " + student.getName() + " ==============");
            List<DefaultMutableTreeNode> trees = student.getFileTrees();
            System.out.println(trees.size());

            for (DefaultMutableTreeNode tree : trees) {

                ClassFile file = (ClassFile) tree.getNextNode().getUserObject();
                System.out.println(file.getName());
            }
        }

    }
}
