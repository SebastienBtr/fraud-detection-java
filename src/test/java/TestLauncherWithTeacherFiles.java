import launcher.Launcher;
import student.Student;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

public class TestLauncherWithTeacherFiles {

    public static void main(String[] args) {

        Launcher.init("src/test/data/exam.zip", "src/test/data/teacher_files.zip");
        //Launcher.parseFiles();
        List<Student> students = Launcher.getStudents();
        System.out.println("STUDENTS : " + students.size());
        //showResult(students);
    }

    private static void showResult(List<Student> students) {

        for (Student student : students) {

            System.out.println("============== STUDENT " + student.getName() + " ==============");
            List<DefaultMutableTreeNode> trees = student.getFileTrees();
            System.out.println(trees.size());

            /*for (DefaultMutableTreeNode tree : trees) {
                ClassFile file = (ClassFile) tree.getNextNode().getUserObject();
                System.out.println(file.getName());
            }*/
        }

    }
}
