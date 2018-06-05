package launcher;

import parser.ProjectParser;
import student.Student;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Launcher {

    /**
     * List of students
     */

    private static List<Student> students;

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.println("Path of the zipFile : ");
        String fileZip = in.next();

        init(fileZip);
    }

    public static List<Student> getStudents()
    {
        return students;
    }

    /**
     * Run all app operations :
     * Unzip files, parse files, compare students
     *
     * @param fileZip the directory that contains all student projects
     */
    public static List<Student> init(String fileZip) {

        students = new ArrayList<Student>();

        File destDir = new File("unzipFiles");

        if (!destDir.exists()) {
            destDir.mkdir();

        } else {
            cleanDirectory(destDir);
            destDir.mkdir();
        }

        try {
            Unzipper.unzip(fileZip, destDir.getPath(), true);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        students = Unzipper.getStudents();
        parseFiles();
        // StudentComparator.compareStudents(students);

        return students;
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
     * Parse all project files
     */
    private static void parseFiles() {

        for (Student student : students) {

            File directory = new File(student.getDirectoryPath());

            if (directory.exists()) {
                parseStudentFiles(directory, student);
            }
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
                System.out.println("CALL PARSER " + fileEntry.getName() + "  " + student.getName());
                DefaultMutableTreeNode tree = ProjectParser.parseFile(fileEntry.getPath());
                student.addTree(tree);
            }
        }
    }


}
