package parser;

import student.Method;
import student.algorithm_structure.*;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.*;
import java.util.regex.Pattern;

public class ProjectParser {

    public static DefaultMutableTreeNode parseFile(String fileName) {

        DefaultMutableTreeNode studentTree = new DefaultMutableTreeNode();

        FileInputStream fstream = null;
        BufferedReader br = null;

        try {

            // Open streams
            fstream = new FileInputStream(fileName);
            br = new BufferedReader(new InputStreamReader(fstream));

            String strLine;

            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {

                if (ProjectParser.isMethod(strLine)) {
                    Method newMethod = ProjectParser.createMethod(strLine);

                    DefaultMutableTreeNode body = parseBody(newMethod, br);

                    studentTree.add(body);
                }

                // Print the content on the console
                //System.out.println (strLine);
            }

            System.out.println(ProjectParser.treeToString(studentTree));

        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());

        } finally { //close streams

            if (br != null) {

                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fstream != null) {

                try {
                    fstream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return studentTree;
    }

    private static boolean isMethod(String strLine) {
        boolean ret = false;

        if (Pattern.matches(".*public.*", strLine)) {
            ret = true;
        }

        return ret;
    }

    private static Method createMethod(String strLine) {
        return new Method(strLine);
    }

    private static DefaultMutableTreeNode parseBody(Object parent, BufferedReader br) throws IOException {
        DefaultMutableTreeNode ret = new DefaultMutableTreeNode(parent);

        String strLine;

        while ((strLine = br.readLine()) != null && !strLine.contains("}")) {
            Structure structure = checkLineStructure(strLine);
            ret.add(parseBody(structure, br));
        }

        return ret;
    }

    private static Structure checkLineStructure(String strLine) {

        if (Pattern.matches(".*for\\(.*;.*;.*\\).*", strLine)) {
            return new Loop(strLine, LoopType.FOR);
        } else if (Pattern.matches(".*for\\(.*:.*\\).*", strLine)) {
            return new Loop(strLine, LoopType.FOREACH);
        } else if (strLine.startsWith(".*while\\(.*).*")) {
            return new Loop(strLine, LoopType.WHILE);
        } else if (strLine.startsWith(".*do.*")) {
            return new Loop(strLine, LoopType.DOWHILE);
        } else if (strLine.startsWith(".*if\\(.*).*")) {
            return new Conditional(strLine);
        } else {
            return new CodeLine(strLine);
        }
    }

    private static String treeToString(DefaultMutableTreeNode tree) {
        //String ret = "------PARENT------";
        //ret += "\n"+tree.getParent().getChildAt(1);

        System.out.println(tree.getDepth());
        System.out.println(tree.getFirstLeaf());
        System.out.println(tree.getLeafCount());
        String ret = "\n\n------ENFANTS------";

        return ret;
    }
}
