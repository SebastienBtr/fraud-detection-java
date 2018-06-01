package launcher;

import student.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzipper {

    /**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;

    /**
     * List of students
     */
    private static List<Student> students;

    private Unzipper() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Get the list of students
     *
     * @return the List of students
     */
    static List<Student> getStudents() {
        return students;
    }

    /**
     * Extracts a zip file specified by the zipFilePath to a directory specified by
     * destDirectory (will be created if does not exists)
     *
     * @param zipFilePath   the path of the zip file
     * @param destDirectory the path to save unzip files
     * @throws IOException
     */
    static void unzip(String zipFilePath, String destDirectory, boolean recursive, boolean teacherZip) throws IOException {

        if (students == null) {
            students = new ArrayList<Student>();
        }

        ZipInputStream zipIn = null;

        try {
            zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
            ZipEntry entry = zipIn.getNextEntry();

            if (!recursive && !teacherZip) {

                String[] parts = entry.getName().split("/");
                String repName = parts[0];

                String studentDiretory = destDirectory + File.separator + repName;

                File dir = new File(studentDiretory);
                dir.mkdir();

                students.add(new Student(repName, studentDiretory));
            }

            // iterates over entries in the zip file
            while (entry != null) {

                createFiles(destDirectory, zipIn, entry, recursive);

                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());

        } finally {
            if (zipIn != null) {
                zipIn.close();
            }
        }
    }

    /**
     * Create the ZipEntry file or directory and its children
     *
     * @param destDirectory path where create the file
     * @param zipIn         the zip input stream
     * @param entry         the zip entry
     * @throws IOException
     */
    private static void createFiles(String destDirectory, ZipInputStream zipIn, ZipEntry entry, boolean recursive) throws IOException {

        String filePath = destDirectory + File.separator + entry.getName();

        if (!entry.isDirectory()) {

            boolean isZipFile = filePath.endsWith(".zip");
            boolean isJavaFile = filePath.endsWith(".java");

            if (isZipFile || isJavaFile) {

                writeFile(zipIn, filePath);

                if (recursive) {
                    File file = new File(filePath);
                    String newDestFile = file.getParentFile().getAbsolutePath();
                    unzip(filePath, newDestFile, false, false);
                }
            }

        } else {
            File dir = new File(filePath);
            dir.mkdir();
        }
    }

    /**
     * Extracts a zip entry (file entry)
     *
     * @param zipIn    the ZipInputStream
     * @param filePath the file path
     * @throws IOException
     */
    private static void writeFile(ZipInputStream zipIn, String filePath) throws IOException {

        String parent = filePath.replace(filePath.substring(filePath.lastIndexOf('/')), "");
        createParents(parent);

        BufferedOutputStream bos = null;

        try {
            bos = new BufferedOutputStream(new FileOutputStream(filePath));
            byte[] bytesIn = new byte[BUFFER_SIZE];

            int read = 0;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());

        } finally {
            if (bos != null) {
                bos.close();
            }
        }
    }

    /**
     * Create the tree files of the given path
     * @param path path of our tree
     */
    private static void createParents(String path) {

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
