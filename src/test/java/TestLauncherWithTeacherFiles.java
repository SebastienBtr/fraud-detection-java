import launcher.ConfigFile;
import launcher.Launcher;
import student.Student;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TestLauncherWithTeacherFiles {

    public static void main(String[] args) {

        Launcher.init("src/test/data/PROG_IMP_JAVA-Lien de depot TP10note-48285_2017_TP10Note.zip", "src/test/data/teacher2.zip");
        Launcher.parseFiles();
        ConfigFile.classNameAreGiven = true;
        ConfigFile.methodNamesAreGiven = true;
        Launcher.compareStudents();
        List<Student> students = Launcher.getStudents();
        System.out.println("STUDENTS : " + students.size());
        showResult(students);
    }

    private static void showResult(List<Student> students) {

        for (Student student : students) {

            System.out.println("============== STUDENT " + student.getName() + " ==============");

            TreeMap<String,Double> scores = student.getScores();

            for(Map.Entry<String,Double> entry : scores.entrySet()) {

                String key = entry.getKey();
                Double value = entry.getValue();

                System.out.println(key + " => " + value);
            }
        }

    }
}
