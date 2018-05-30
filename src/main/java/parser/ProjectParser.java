package parser;

import student.ClassFile;
import student.Method;
import student.algorithm_structure.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.io.*;
import java.util.Enumeration;
import java.util.regex.Pattern;

public class ProjectParser {

    /**
     * Constructor
     */

    private ProjectParser() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * ParseFile
     * @param fileName
     * @return
     */

    public static DefaultMutableTreeNode parseFile(String fileName) {

        DefaultMutableTreeNode studentTree = new DefaultMutableTreeNode();

        FileInputStream fstream = null;
        BufferedReader br = null;

        try {

            // Open streams
            fstream = new FileInputStream(fileName);
            br = new BufferedReader(new InputStreamReader(fstream));

            String strLine;

            //Read ClassFile Line By Line
            while ((strLine = br.readLine()) != null) {

                if (!isAComment(strLine, br) && isClass(strLine)) {

                    ClassFile newClass = ProjectParser.createClass(strLine, br);
                    DefaultMutableTreeNode body = parseClass(newClass, br);
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


    /**
     * ParseClass
     * @param newClass
     * @param br
     * @return
     * @throws IOException
     */

    private static DefaultMutableTreeNode parseClass(ClassFile newClass, BufferedReader br) throws IOException {
        DefaultMutableTreeNode classTree = new DefaultMutableTreeNode(newClass);

        String strLine;

        //Read ClassFile Line By Line
        while ((strLine = br.readLine()) != null) {

            if (isMethod(strLine)) {

                Method newMethod = ProjectParser.createMethod(strLine, br);
                DefaultMutableTreeNode body = parseBody(newMethod, br);
                classTree.add(body);

            }
        }

        return classTree;
    }

    /**
     * DefaultMutableTreeNode
     * @param parent
     * @param br
     * @return
     * @throws IOException
     */

    private static DefaultMutableTreeNode parseBody(Object parent, BufferedReader br) throws IOException {
        DefaultMutableTreeNode ret = new DefaultMutableTreeNode(parent);

        String strLine;

        while ((strLine = br.readLine()) != null && !isEndOfBlock(strLine)) {

            if(!isAComment(strLine, br)){
                Structure structure = checkLineStructure(strLine);

                if (structure.getClass().equals(CodeLine.class)
                        || structure.getClass().equals(EmptyLine.class)) {

                    ret.add(new DefaultMutableTreeNode(structure));

                } else {
                    ret.add(parseBody(structure, br));
                }
            }
        }

        return ret;
    }

    /**
     * CheckLineStructure
     * @param strLine
     * @return
     */

    private static Structure checkLineStructure(String strLine) {

        // FOR
        if (Pattern.matches(".*for(\\s)*\\(([!-z]|\\s)*;([!-z]|\\s)*;([!-z]|\\s)*\\)(\\s)*\\{", strLine)) {
            return new Loop(strLine, LoopType.FOR);

        }
        // FOREACH
        else if (Pattern.matches(".*for(\\s)*\\(([a-zA-Z]|\\s)*:([a-zA-Z]|\\s)*\\)(\\s)*\\{", strLine)) {
            return new Loop(strLine, LoopType.FOREACH);

        }
        // WHILE
        else if (Pattern.matches(".*while(\\s)*\\(([!-z]|\\s)*\\)(\\s)*\\{", strLine)) {
            return new Loop(strLine, LoopType.WHILE);
        }
        //else if(Pattern.matches(".*do(\\s)*{(.|\\s)*}(\\s)while(\\s)*\\(([!-z]|\\s)*\\)(\\s)*;", strLine)){
        //    return new Loop(strLine, LoopType.DOWHILE);
        //}

        // IF
        else if (Pattern.matches(".*if(\\s)*\\(([!-z]|\\s)*\\)(\\s)*\\{", strLine)) {
            return new Conditional(strLine);

        }
        else if (isEmptyLine(strLine)){
            return EmptyLine.getInstance();
        }
        // LIGNE CODE
        else {
            return new CodeLine(strLine.trim());
        }
    }

    /**
     * CreateClass
     * @param strLine
     * @param br
     * @return
     * @throws IOException
     */

    private static ClassFile createClass(String strLine, BufferedReader br) throws IOException {
        if(strLine.trim().endsWith("{")) return new ClassFile(strLine);
        else throw new IOException();
    }

    /**
     * CreateMethod
     * @param strLine
     * @param br
     * @return
     * @throws IOException
     */

    private static Method createMethod(String strLine, BufferedReader br) throws IOException {

        if(strLine.trim().endsWith("{")) return new Method(strLine);
        else {
            String method = strLine;

            while(strLine != null && !strLine.trim().endsWith("{")){
                strLine = br.readLine();
                method += strLine;
            }

            return new Method(method);
        }
    }

    /**
     * IsAComment
     * @param strLine
     * @param br
     * @return
     * @throws IOException
     */

    private static boolean isAComment(String strLine, BufferedReader br) throws IOException {

        if(strLine.trim().startsWith("/*")){
            if(strLine.trim().contains("*/")) return true;
            else {
                String strLine2 = br.readLine();

                while (!strLine2.contains("*/")){
                    strLine2 = br.readLine();
                }

                return true;
            }

        }
        else {

            return (strLine.trim().startsWith("*")
                    || strLine.trim().startsWith("//")
                    || strLine.trim().startsWith("/**")
                    || strLine.trim().startsWith("*/"));

        }
    }

    /**
     * IsMethod
     * @param strLine
     * @return
     */

    private static boolean isMethod(String strLine) {
        return Pattern.matches(".*public.*", strLine);
    }

    /**
     * IsClass
     * @param strLine
     * @return
     */

    private static boolean isClass(String strLine) {
        return Pattern.matches("\\b(public|private|protected|)\\b\\s*\\b(static|)\\b\\s*\\b(class)\\b\\s*\\w+\\s*\\{", strLine);
    }

    /**
     * IsEndOfBlock
     * @param strLine
     * @return
     */

    private static boolean isEndOfBlock(String strLine) {

        return Pattern.matches(".*}.*", strLine);
        // TODO } else
    }

    /**
     * IsEmptyLine
     * @param strLine
     * @return
     */

    private static boolean isEmptyLine(String strLine) {
        return (strLine.trim().length() == 0);
    }

    /**
     * TreeToString
     * @param tree
     * @return
     */

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

    /**
     * CloseStreams
     * @param fstream
     * @param br
     */

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
