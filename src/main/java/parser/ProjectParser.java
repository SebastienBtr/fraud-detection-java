package parser;


import student.ClassFile;
import student.ClassMethodType;
import student.Method;
import student.algorithm_structure.*;
import util.Couple;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

public class ProjectParser {

    /**
     * Constructor
     */

    private ProjectParser() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Create a buffered reader and start to parse a file from a filename
     * @param fileName
     * @return A mutable Tree
     */

    public static DefaultMutableTreeNode parseFile(String fileName) throws Exception {

        DefaultMutableTreeNode studentTree = new DefaultMutableTreeNode();

        FileInputStream fstream = null;
        BufferedReader br = null;

        // Open streams
        fstream = new FileInputStream(fileName);
        br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;

        //Read ClassFile Line By Line
        while ((strLine = br.readLine()) != null) {

            if (!isComment(strLine, br) && isClass(strLine)) {

                ClassFile newClass = ProjectParser.createClass(strLine, br);
                DefaultMutableTreeNode body = parseClass(newClass, br);
                studentTree.add(body);

            }
        }

        closeStreams(fstream, br);

        return studentTree;
    }


    /**
     * Parse the inside of a class
     * @param newClass - The class to parse
     * @param br - The reader of the file
     * @return
     * @throws IOException
     */

    private static DefaultMutableTreeNode parseClass(ClassFile newClass, BufferedReader br) throws Exception {
        DefaultMutableTreeNode classTree = new DefaultMutableTreeNode(newClass);

        String strLine;

        //Read th block insine the class until the '}'
        while ((strLine = br.readLine()) != null && !isEndOfBlock(strLine)) {
            //Remove the comment of a string
            strLine = getStringWithoutComment(strLine);

            // Intern Class
            if (isClass(strLine)) {
                ClassFile newClassIntern = ProjectParser.createClass(strLine, br);
                DefaultMutableTreeNode body = parseClass(newClassIntern, br);
                classTree.add(body);
            }
            // Method
            else if (isMethod(strLine)) {
                Method newMethod = new Method(getStructureDeclaration(strLine, br));
                DefaultMutableTreeNode body = parseBody(newMethod, strLine, br).getNode();
                classTree.add(body);
            }
            // Attributes
            else if(strLine.trim() != "" && !isComment(strLine, br) && strLine.trim().length() > 0){
                ArrayList<DefaultMutableTreeNode> listAttributes = parseAttribute(strLine, br);

                for(DefaultMutableTreeNode node : listAttributes){
                    classTree.add(node);
                }
            }
        }

        return classTree;
    }

    /**
     * Parse a list of attribute from a line (can be one or more)
     * @param strLine - The line to parse
     * @param br - The reader
     * @return
     * @throws Exception
     */

    private static ArrayList<DefaultMutableTreeNode> parseAttribute(String strLine, BufferedReader br) throws Exception {
        ArrayList<DefaultMutableTreeNode> listAttribute = new ArrayList<DefaultMutableTreeNode>();

        // Get all the string until the end
        String attributeDeclaration = getStructureDeclaration(strLine, br);
        List<String> attributeParams = new ArrayList<String>();
        attributeParams.addAll(Arrays.asList(attributeDeclaration.trim().split(" ")));

        String visibility = null;
        String type;
        boolean isStatic = false;
        String[] names = null;
        String value = null;

        // Type
        if(ClassMethodType.isClassMethodType(attributeParams.get(0))){
            visibility = attributeParams.get(0);
            attributeParams.remove(0);
        }
        // IsStatic
        if(attributeParams.contains("static")){
            isStatic = true;
            attributeParams.remove("static");
        }

        type = attributeParams.get(0);
        attributeParams.remove(type);

        String endDeclarationAttribute = "";

        for(String param : attributeParams){
            endDeclarationAttribute += param+" ";
        }

        // Value
        if(attributeDeclaration.contains("=")){
            String[] attributeParts = endDeclarationAttribute.split("=");
            value = attributeParts[1].replace(";","");

            names = attributeParts[0].split(",");
        }

        // Names
        if(names == null){
            names = endDeclarationAttribute.replace(";","").split(",");
        }

        for(String name : names){
            Attribute attr;

            if(visibility == null){
                attr = new Attribute(type, name);
            }
            else {
                attr = new Attribute(ClassMethodType.fromString(visibility), type, name);
            }

            attr.setIsStatic(isStatic);
            if(value != null) attr.setValue(value);

            listAttribute.add(new DefaultMutableTreeNode(attr));
        }

        return listAttribute;
    }

    /**
     * Parse the body of a block : From '{' to '}'
     * @param object - The object to parse
     * @param br - The reader
     * @return - The node of the object
     * @throws IOException
     */

    private static Couple parseBody(Object object, String line, BufferedReader br) throws Exception {
        DefaultMutableTreeNode ret = new DefaultMutableTreeNode(object);
        String strLine;
        Boolean endLine = false;

        String[] endOfLine = line.split("\\{");

        // Check and parse the end of a line after a '{'
        if(endOfLine.length > 1 && endOfLine[1].trim().length() > 1){
            if(isEndOfBlock(endOfLine[1])){
                Structure structure = checkLineStructure(endOfLine[1].split("}")[0], br);
                ret.add(new DefaultMutableTreeNode(structure));
            }
            else {
                Structure structure = checkLineStructure(endOfLine[1], br);
                ret.add(new DefaultMutableTreeNode(structure));
            }
        }

        // Read the block
        while ((strLine = br.readLine()) != null) {
            if(endLine) endLine = false;

            // Check if the '}' is related to a object declaration inside the line (ex: 'catch (...) { test ; }' )
            if(isEndOfBlock(strLine)){
                if(strLine.contains("{") && strLine.trim().split("\\{").length > 1){
                    // Get the parent class (ex: 'CATCH')
                    Structure parentStructure = checkLineStructure(strLine.substring(0, strLine.lastIndexOf("{")+1), br);
                    DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode(parentStructure);

                    // Get the child class (ex: 'CODELINE(test;)')
                    Structure childStructure = checkLineStructure(strLine.substring(strLine.lastIndexOf("{")+1, strLine.length()-1), br);
                    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(childStructure);

                    parentNode.add(childNode);
                    ret.add(parentNode);

                    endLine = true;
                }
                else break;
            }

            // All other case
            if(!endLine && !isComment(strLine, br)){
                strLine = getStringWithoutComment(strLine);
                Structure structure = checkLineStructure(strLine, br);

                // Check if the object is a simple object
                if (structure.getClass().equals(CodeLine.class)
                        || structure.getClass().equals(EmptyLine.class)
                        || structure.getClass().equals(Return.class)){

                    ret.add(new DefaultMutableTreeNode(structure));
                }
                // Check if the conditionnal is wrote like (ex: 'If(...) test;' )
                else if(structure.getClass().equals(Conditional.class)
                        && ((Conditional) structure).getCodeInDeclaration() != null){

                    DefaultMutableTreeNode ifNode = new DefaultMutableTreeNode(structure);
                    Structure insideStructure = checkLineStructure(((Conditional) structure).getCodeInDeclaration(),br);
                    ifNode.add(new DefaultMutableTreeNode(insideStructure));

                    ret.add(ifNode);
                }
                // All other objects
                else {
                    Couple body = parseBody(structure,strLine, br);
                    ret.add(body.getNode());

                    // Get all the object declared after the end of a block (ex: '} else {')
                    while (body.getLastLine() != null && body.getLastLine().trim().split("}").length > 1){
                        String[] lastLineParts = body.getLastLine().trim().split("}");
                        structure = checkLineStructure(lastLineParts[1], br);

                        if (structure.getClass().equals(CodeLine.class)
                                || structure.getClass().equals(EmptyLine.class)
                                || structure.getClass().equals(Return.class)){

                            ret.add(new DefaultMutableTreeNode(structure));
                            body.setLastLine(null);
                        }
                        else if(structure.getClass().equals(Conditional.class)
                                && ((Conditional) structure).getCodeInDeclaration() != null){

                            DefaultMutableTreeNode ifNode = new DefaultMutableTreeNode(structure);
                            Structure insideStructure = checkLineStructure(((Conditional) structure).getCodeInDeclaration(),br);
                            ifNode.add(new DefaultMutableTreeNode(insideStructure));

                            ret.add(ifNode);
                            body.setLastLine(null);
                        }
                        else {
                            body = parseBody(structure,strLine, br);
                            ret.add(body.getNode());
                        }
                    }
                }
            }

        }


        // Get the code before the '}' (ex: 'do(); }')
        if(strLine != null && isEndOfBlock(strLine)){

            String[] startOfLine = strLine.split("}");

            if(startOfLine.length > 0 && startOfLine[0].trim().length() > 1){

                Structure structure = checkLineStructure(startOfLine[0], br);

                // Check if it is a simple object
                if (structure.getClass().equals(CodeLine.class)
                        || structure.getClass().equals(EmptyLine.class)
                        || structure.getClass().equals(Return.class)){

                    ret.add(new DefaultMutableTreeNode(structure));
                }
                // It's a new block
                else {
                    Couple body = parseBody(structure, strLine, br);
                    ret.add(body.getNode());
                }


            }
        }

        return new Couple(ret, strLine);
    }


    /**
     * Create the class from a line
     * @param strLine
     * @param br
     * @return
     * @throws IOException
     */

    private static ClassFile createClass(String strLine, BufferedReader br) throws Exception {
        if(strLine.trim().endsWith("{")) return new ClassFile(strLine);
        else throw new IOException();
    }

    /**
     * Check the structure that the line contains and create it
     * @param strLine
     * @return
     */

    private static Structure checkLineStructure(String strLine, BufferedReader br) throws Exception {

        // FOR
        if (Pattern.matches("(\\s)*for(\\s)*\\(.*", strLine)) {
            return new Loop(getStructureDeclaration(strLine, br));
        }

        // IF
        else if (Pattern.matches("(\\s)*if(\\s)*\\(.*", strLine)) {
            return new Conditional(getStructureDeclaration(strLine, br));
        }

        // ELSE
        else if (Pattern.matches("(\\s)*else.*", strLine)) {
            if(strLine.contains("if")){
                return new Conditional(getStructureDeclaration(strLine, br), ConditionalType.ELSEIF);
            }
            else {
                return new Conditional(getStructureDeclaration(strLine, br), ConditionalType.ELSE);
            }
        }

        // EMPTY LINE
        else if (isEmptyLine(strLine)){
            return EmptyLine.getInstance();
        }

        // WHILE
        else if (Pattern.matches(".*while(\\s)*\\(.*", strLine)) {
            return new Loop(getStructureDeclaration(strLine, br), LoopType.WHILE);
        }

        // RETURN
        else if(strLine.trim().startsWith("return")){
            return new Return(strLine);
        }

        // TRY CATCH
        else if(Pattern.matches("\\s*(\\}|)\\s*(try|finally)\\s*\\{", strLine)){
            return new TryCatch(strLine);
        }
        else if(Pattern.matches("\\s*(\\}|)\\s*catch\\s*\\(\\s*\\w*\\s*\\w*\\s*\\)\\s*\\{.*", strLine)){
            return new TryCatch(strLine);
        }

        // SWITCH
        else if(Pattern.matches("\\s*switch\\s*\\(\\s*\\w+\\s*\\)\\s*.*", strLine)){
            return new Conditional(getStructureDeclaration(strLine, br), ConditionalType.SWITCH);
        }

        // CODE LINE
        else {
            return new CodeLine(strLine.trim());
        }

        // DO WHILE
        //else if(Pattern.matches(".*do(\\s)*{(.|\\s)*}(\\s)while(\\s)*\\(([!-z]|\\s)*\\)(\\s)*;", strLine)){
        //    return new Loop(strLine, LoopType.DOWHILE);
        //}

    }

    /**
     * Get all the structure declaration from a line
     * Read until a ';' or a '{'
     * @param strLine
     * @param br
     * @return
     * @throws IOException
     */

    private static String getStructureDeclaration(String strLine, BufferedReader br) throws Exception {
        String ret = strLine;

        while(!strLine.trim().endsWith("{") && !strLine.trim().endsWith(";")) {
            strLine = br.readLine();
            if(strLine.trim().length() != 0) ret += "\n"+strLine;
        }

        return ret;
    }

    /**
     * Remove the comment from a string
     * @param strLine
     * @return
     */

    private static String getStringWithoutComment(String strLine){
        if(strLine.contains("//")){
            if(strLine.split("//")[0].length()
                    - strLine.split("//")[0].replace("\"","").length() % 2 == 0){

                strLine = strLine.split("//")[0];
            }
        }

        return strLine;
    }

    /**
     * Check if the line is a comment and read until a code line without comments
     * @param strLine
     * @param br
     * @return
     * @throws IOException
     */

    private static boolean isComment(String strLine, BufferedReader br) throws Exception {

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
     * Check if the line is a method declaration
     * @param strLine
     * @return
     */

    private static boolean isMethod(String strLine) {
        return Pattern.matches("\\s*(public|private|protected|)\\s*(static|)\\s*\\w*\\b(?<!\\breturn)\\s*(\\[|\\<|)\\s*\\s*(\\w+)*\\s*(\\s*,(\\s*)\\w+)*(\\]|\\>|)\\s*\\w+\\s*\\(.*", strLine);
    }

    /**
     * Check if the line is a class declaration
     * @param strLine
     * @return
     */

    private static boolean isClass(String strLine) {
        return Pattern.matches("\\s*\\b(public|private|protected|)\\b\\s*\\b(static|)\\b\\s*\\b(class)\\b\\s*\\w+\\s*\\b(implements|extends|)\\b\\s*(\\w+)*\\s*(\\s*,(\\s*)\\w+)*\\s*\\{.*", strLine);
    }

    /**
     * Check if the line is the end of a block '}'
     * @param strLine
     * @return
     */

    private static boolean isEndOfBlock(String strLine) {

        if ((strLine.contains("}")) ) {
            if (!strLine.contains("\"") && !strLine.contains("\'")) {
                return true;
            }
            else {
                boolean isIn = false;
                for (char c: strLine.toCharArray() ) {
                    if (c == '\"' || c == '\'') {
                        isIn = !isIn;
                    }
                    else if (c == '}' && !isIn) {
                        return true;
                    }


                }
                return false;
            }
        }
        else {
            return false;
        }
    }

    /**
     * Check if the line is empty
     * @param strLine
     * @return
     */

    private static boolean isEmptyLine(String strLine) {
        return (strLine.trim().length() == 0);
    }

    /**
     * Create a string to print from a TreeNode
     * @param tree
     * @return
     */

    private static String treeToString(DefaultMutableTreeNode tree) {
        Enumeration<DefaultMutableTreeNode> en = tree.preorderEnumeration();

        String ret = "";

        while (en.hasMoreElements()) {
            DefaultMutableTreeNode node = en.nextElement();
            TreeNode[] path = node.getPath();

            int level = node.getLevel();

            StringBuilder bld = new StringBuilder();
            for (int i = 0; i < level; i++) {
                bld.append("  ");
            }
            String tabs = bld.toString();

            ret += tabs + (node.isLeaf() ? " - " : " + ") + path[path.length - 1] + "\n";
        }

        return ret;
    }

    /**
     * Close the streams that are used to read the file
     * @param fstream
     * @param br
     */

    private static void closeStreams(FileInputStream fstream, BufferedReader br) throws Exception {

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
