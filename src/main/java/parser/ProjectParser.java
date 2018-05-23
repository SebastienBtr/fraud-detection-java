package parser;

import student.Method;
import student.Student;
import student.algorithm_structure.*;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.io.*;
import java.util.Enumeration;
import java.util.regex.Pattern;

public class ProjectParser {

    public static void parseFile(String fileName){

        try{
            DefaultMutableTreeNode studentTree = new DefaultMutableTreeNode(fileName);

            // Open the file
            FileInputStream fstream = new FileInputStream(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String strLine;

            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {

                if(!ProjectParser.isAComment(strLine)) {
                    if (ProjectParser.isMethod(strLine)) {
                        Method newMethod = ProjectParser.createMethod(strLine);

                        DefaultMutableTreeNode body = parseBody(newMethod, br);

                        studentTree.add(body);
                    }
                }
            }

            System.out.println(ProjectParser.treeToString(studentTree));

            //Close the input stream
            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static boolean isMethod(String strLine){
        boolean ret = false;

        if(Pattern.matches(".*public.*", strLine)){
            ret = true;
        }

        return ret;
    }

    public static Method createMethod(String strLine){
        return new Method(strLine);
    }

    public static DefaultMutableTreeNode parseBody(Object parent, BufferedReader br) throws IOException {
        DefaultMutableTreeNode ret = new DefaultMutableTreeNode(parent);

        String strLine;

        while ((strLine = br.readLine()) != null
                && !strLine.contains("}")
                && !ProjectParser.isAComment(strLine))   {

            //&& !strLine.contains("}")
            //    && !strLine.contains("*")
            //        && !strLine.contains("//")

            if(!ProjectParser.isAComment(strLine)) {

                Structure structure = checkLineStructure(strLine);

                if (structure.getClass().equals(CodeLine.class)) {
                    ret.add(new DefaultMutableTreeNode(structure));
                } else ret.add(parseBody(structure, br));

            }
        }

        return ret;
    }

    private static Structure checkLineStructure(String strLine){

        if(Pattern.matches(".*for(\\s)*\\(([!-z]|\\s)*;([!-z]|\\s)*;([!-z]|\\s)*\\)(\\s)*\\{",strLine)){
            return new Loop(strLine, LoopType.FOR);
        }
        else if(Pattern.matches(".*for(\\s)*\\(([a-zA-Z]|\\s)*:([a-zA-Z]|\\s)*\\)(\\s)*\\{",strLine)){
            return new Loop(strLine, LoopType.FOREACH);
        }
        else if(Pattern.matches(".*while(\\s)*\\(([!-z]|\\s)*\\)(\\s)*\\{", strLine)){
            return new Loop(strLine, LoopType.WHILE);
        }
        //else if(Pattern.matches(".*do(\\s)*{(.|\\s)*}(\\s)while(\\s)*\\(([!-z]|\\s)*\\)(\\s)*;", strLine)){
        //    return new Loop(strLine, LoopType.DOWHILE);
        //}
        else if(Pattern.matches(".*if(\\s)*\\(([!-z]|\\s)*\\)(\\s)*\\{", strLine)){
            return new Conditional(strLine);
        }
        else {
            return new CodeLine(strLine);
        }
    }

    private static String treeToString(DefaultMutableTreeNode tree){
        Enumeration<DefaultMutableTreeNode> en = tree.preorderEnumeration();

        while (en.hasMoreElements())
        {
            DefaultMutableTreeNode node = en.nextElement();
            TreeNode[] path = node.getPath();

            int level = node.getLevel();

            String tabs = "";
            for(int i=0;i<level;i++){
                tabs += "  ";
            }

            System.out.println(tabs + (node.isLeaf() ? " - " : " + ") + path[path.length - 1]);
        }

        return null;
    }

    private static boolean isAComment(String strLine){
        if(strLine.trim().startsWith("*")
                || strLine.trim().startsWith("//")
                || strLine.trim().startsWith("/**")
                || strLine.trim().startsWith("*/")) return true;
        else return false;
    }
}
