package launcher;

import java.io.*;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Launcher {

    /**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.println("Path of the zipFile : ");
        String fileZip = in.next();

        File destDir = new File("unzipFiles");

        if (!destDir.exists()) {
            destDir.mkdir();

        } else {
            cleanDirectory(destDir);
            destDir.mkdir();
        }

        try {
            unzip(fileZip, destDir.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //TODO call parser or other methods
    }


    /**
     * Extracts a zip file specified by the zipFilePath to a directory specified by
     * destDirectory (will be created if does not exists)
     *
     * @param zipFilePath   the path of the zip file
     * @param destDirectory the path to save unzip files
     * @throws IOException
     */
    private static void unzip(String zipFilePath, String destDirectory) throws IOException {

        ZipInputStream zipIn = null;

        try {
            zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
            ZipEntry entry = zipIn.getNextEntry();

            // iterates over entries in the zip file
            while (entry != null) {

                CreateFiles(destDirectory, zipIn, entry);

                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (zipIn != null) {
                zipIn.close();
            }
        }
    }

    /**
     * Create the file or the directory and its children of a ZipEntry
     *
     * @param destDirectory path where create the file
     * @param zipIn         the zip input stream
     * @param entry         the zip entry
     * @throws IOException
     */
    private static void CreateFiles(String destDirectory, ZipInputStream zipIn, ZipEntry entry) throws IOException {
        String filePath = destDirectory + File.separator + entry.getName();

        if (!entry.isDirectory()) {

            boolean isZipFile = filePath.endsWith(".zip");
            writeFile(zipIn, filePath);

            if (isZipFile) {
                File file = new File(filePath);
                String newDestFile = file.getParentFile().getAbsolutePath();
                unzip(filePath, newDestFile);
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

        BufferedOutputStream bos = null;

        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            bos = new BufferedOutputStream(fileOut);
            byte[] bytesIn = new byte[BUFFER_SIZE];

            int read = 0;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (bos != null) {
                bos.close();
            }
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
}
