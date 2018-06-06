package launcher;

import comparator.StudentComparator;
import org.apache.commons.io.FileUtils;
import parser.ProjectParser;
import student.Student;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Launcher {

    /**
     * List of students
     */
    private static List<Student> students;

    /**
     * Get the list of students
     *
     * @return the List of students
     */
    public static List<Student> getStudents() {
        return students;
    }

    private Launcher() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Unzip files
     *
     * @param fileZip the directory that contains all student projects
     */
    public static void init(String fileZip) {

        students = new ArrayList<Student>();

        File destDir = new File("unzipFiles");

        checkDirectory(destDir);

        try {
            Unzipper.unzip(fileZip, destDir.getPath(), true, false);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        students = Unzipper.getStudents();
    }

    /**
     * Unzip files when we have teacher's files given
     *
     * @param fileZip          the directory that contains all student projects
     * @param examDefaultFiles files that are given by the teacher
     */
    public static void init(String fileZip, String examDefaultFiles) {

        students = new ArrayList<Student>();

        File studentDestDir = new File("unzipFiles");
        File teacherDestDir = new File("unzipTeacherFiles");

        checkDirectory(studentDestDir);
        checkDirectory(teacherDestDir);

        try {
            Unzipper.unzip(fileZip, studentDestDir.getPath(), true, false);
            Unzipper.unzip(examDefaultFiles, teacherDestDir.getPath(), false, true);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        analyzeTeacherFiles("unzipFiles", "unzipTeacherFiles");

        students = Unzipper.getStudents();
    }


    /**
     * Parse all project files
     */
    public static void parseFiles() {

        for (Student student : students) {

            File directory = new File(student.getDirectoryPath());

            if (directory.exists()) {
                parseStudentFiles(directory, student);
            }
        }
    }

    /**
     * Run the code comparaison between students
     */
    public static void compareStudents() {
        students = StudentComparator.compareStudents(students);
    }

    /**
     * Check if a directory exist :
     * if it exist we clean it with the cleanDirectory method
     * else we create the directory
     *
     * @param directory
     */
    private static void checkDirectory(File directory) {

        if (!directory.exists()) {
            directory.mkdir();

        } else {
            cleanDirectory(directory);
            directory.mkdir();
        }
    }

    /**
     * Delete all the content of a directory
     *
     * @param directory the directory to clean
     */
    private static void cleanDirectory(File directory) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File f : files) {

                if (f.isDirectory()) {
                    cleanDirectory(f);

                } else {
                    boolean delete = f.delete();
                    if (!delete) {
                        throw new UnsupportedOperationException();
                    }
                }
            }
        }

        boolean delete = directory.delete();
        if (!delete) {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Parse all student files
     *
     * @param directory directory or subdirectory of student files
     */
    private static void parseStudentFiles(File directory, Student student) {

        for (File fileEntry : directory.listFiles()) {

            if (fileEntry.isDirectory()) {
                parseStudentFiles(fileEntry, student);

            } else if (fileEntry.getName().endsWith(".java")) {
                System.out.println("CALL PARSER ============= " + student.getName() + "-- file :" + fileEntry.getName());
                DefaultMutableTreeNode tree = ProjectParser.parseFile(fileEntry.getPath());
                student.addTree(tree);
            }
        }
    }

    /**
     * Remove all students files that are the same of the teacher given files
     * @param studentDesDir path of student's files
     * @param teacherDestDir path of teacher's files
     */
    private static void analyzeTeacherFiles(String studentDesDir, String teacherDestDir) {

        File teacherDirectory = new File(teacherDestDir);
        File studentDirectory = new File(studentDesDir);

        for (File teacherFile : FileUtils.listFiles(teacherDirectory, null, true)) {

            if (!teacherFile.isDirectory()) {

                for (File studentFile : FileUtils.listFiles(studentDirectory, null, true)) {

                    if (!studentFile.isDirectory()) {

                        try {
                            boolean filesAreEquals = FileUtils.contentEquals(teacherFile, studentFile);

                            if (filesAreEquals) {

                                boolean isDelete = studentFile.delete();

                                if (!isDelete) {
                                    throw new UnsupportedOperationException();
                                }
                            }

                        } catch (IOException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                }
            }
        }
    }


}
