package parser;

import student.Method;
import student.algorithm_structure.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.io.*;
import java.util.Enumeration;
import java.util.regex.Pattern;

public class ProjectParser {

    private ProjectParser() {
        throw new IllegalStateException("Utility class");
    }

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

                if (!isAComment(strLine) && isMethod(strLine)) {

                    Method newMethod = ProjectParser.createMethod(strLine);
                    DefaultMutableTreeNode body = parseBody(newMethod, br);
                    studentTree.add(body);

                }
            }

        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());

        } catch (IOException e) {
            System.err.println(e.getMessage());

        } finally {

            closeStreams(fstream, br);
        }

        System.out.println(treeToString(studentTree));
        return studentTree;
    }

    private static boolean isAComment(String strLine) {

        return (strLine.trim().startsWith("*")
                || strLine.trim().startsWith("//")
                || strLine.trim().startsWith("/**")
                || strLine.trim().startsWith("*/"));

        //TODO check for block comment like :

        /*blblb
        nknknmn
        mmnmn*/
    }

    private static boolean endOfBlock(String strLine) {

        return Pattern.matches(".*}.*", strLine);
        // TODO } else
    }

    private static boolean isMethod(String strLine) {
        return Pattern.matches(".*public.*", strLine);
    }

    private static Method createMethod(String strLine) {
        return new Method(strLine);
    }

    private static DefaultMutableTreeNode parseBody(Object parent, BufferedReader br) throws IOException {
        DefaultMutableTreeNode ret = new DefaultMutableTreeNode(parent);

        String strLine;

        while ((strLine = br.readLine()) != null
                && !endOfBlock(strLine)
                && !isAComment(strLine)) {

            Structure structure = checkLineStructure(strLine);

            if (structure.getClass().equals(CodeLine.class)) {

                ret.add(new DefaultMutableTreeNode(structure));

            } else {
                ret.add(parseBody(structure, br));
            }
        }

        return ret;
    }

    private static Structure checkLineStructure(String strLine) {

        if (Pattern.matches(".*for(\\s)*\\(([!-z]|\\s)*;([!-z]|\\s)*;([!-z]|\\s)*\\)(\\s)*\\{", strLine)) {
            return new Loop(strLine, LoopType.FOR);

        } else if (Pattern.matches(".*for(\\s)*\\(([a-zA-Z]|\\s)*:([a-zA-Z]|\\s)*\\)(\\s)*\\{", strLine)) {
            return new Loop(strLine, LoopType.FOREACH);

        } else if (Pattern.matches(".*while(\\s)*\\(([!-z]|\\s)*\\)(\\s)*\\{", strLine)) {
            return new Loop(strLine, LoopType.WHILE);
        }
        //else if(Pattern.matches(".*do(\\s)*{(.|\\s)*}(\\s)while(\\s)*\\(([!-z]|\\s)*\\)(\\s)*;", strLine)){
        //    return new Loop(strLine, LoopType.DOWHILE);
        //}
        else if (Pattern.matches(".*if(\\s)*\\(([!-z]|\\s)*\\)(\\s)*\\{", strLine)) {
            return new Conditional(strLine);

        } else {
            return new CodeLine(strLine);
        }
    }

    private static String treeToString(DefaultMutableTreeNode tree) {
        Enumeration<DefaultMutableTreeNode> en = tree.preorderEnumeration();

        while (en.hasMoreElements()) {
            DefaultMutableTreeNode node = en.nextElement();
            TreeNode[] path = node.getPath();

            int level = node.getLevel();

            StringBuilder bld = new StringBuilder();
            for (int i = 0; i < level; i++) {
                bld.append("  ");
            }
            String tabs = bld.toString();

            System.out.println(tabs + (node.isLeaf() ? " - " : " + ") + path[path.length - 1]);
        }

        return null;
    }

    private static void closeStreams(FileInputStream fstream, BufferedReader br) {

        if (br != null) {

            try {
                br.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

        if (fstream != null) {

            try {
                fstream.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
