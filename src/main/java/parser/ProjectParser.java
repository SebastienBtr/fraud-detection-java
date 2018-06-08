package parser;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.xml.internal.ws.util.StringUtils;
import student.ClassFile;
import student.ClassMethodType;
import student.Method;
import student.algorithm_structure.*;
import util.Couple;

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
     * ParseClass
     * @param newClass
     * @param br
     * @return
     * @throws IOException
     */

    private static DefaultMutableTreeNode parseClass(ClassFile newClass, BufferedReader br) throws Exception {
        DefaultMutableTreeNode classTree = new DefaultMutableTreeNode(newClass);

        String strLine;

        //Read ClassFile Line By Line
        while ((strLine = br.readLine()) != null && !isEndOfBlock(strLine)) {

            strLine = getStringWithoutComment(strLine);

            if (isClass(strLine)) {
                ClassFile newClassIntern = ProjectParser.createClass(strLine, br);
                DefaultMutableTreeNode body = parseClass(newClassIntern, br);
                classTree.add(body);
            }
            else if (isMethod(strLine)) {
                Method newMethod = new Method(getStructureDeclaration(strLine, br));
                DefaultMutableTreeNode body = parseBody(newMethod, strLine, br).getNode();
                classTree.add(body);
            }
            else if(strLine.trim() != "" && !isComment(strLine, br)){
                boolean isStatic;
                isStatic = strLine.contains("static");

                String[] attributeParams = strLine.trim().split(" ");

                if(attributeParams.length > 1){
                    Attribute attribute;


                    if(attributeParams.length == 2 && !isStatic)  {
                        attribute = new Attribute(attributeParams[0], attributeParams[1]);
                        attribute.setIsStatic(isStatic);
                    }
                    else if (attributeParams.length == 3 && isStatic){
                        attribute = new Attribute(attributeParams[1], attributeParams[2]);
                        attribute.setIsStatic(isStatic);
                    }
                    else {
                        if (!isStatic) {
                            attribute = new Attribute(ClassMethodType.fromString(attributeParams[0]),
                                    attributeParams[1],
                                    attributeParams[2]);
                            attribute.setIsStatic(isStatic);
                        }
                        else {
                            attribute = new Attribute(ClassMethodType.fromString(attributeParams[0]),
                                    attributeParams[2],
                                    attributeParams[3]);
                            attribute.setIsStatic(isStatic);
                        }


                        if(!isStatic && attributeParams.length > 3){
                            attribute.setValue(strLine.trim().split("=")[1]);
                        }
                        else if (attributeParams.length >4){
                            attribute.setValue(strLine.trim().split("=")[1]);
                        }
                    }

                    classTree.add(new DefaultMutableTreeNode(attribute));
                }

            }
        }

        return classTree;
    }

    /**
     * DefaultMutableTreeNode
     * @param object
     * @param br
     * @return
     * @throws IOException
     */

    private static Couple parseBody(Object object, String line, BufferedReader br) throws Exception {
        DefaultMutableTreeNode ret = new DefaultMutableTreeNode(object);
        String strLine;
        Boolean endLine = false;

        String[] endOfLine = line.split("\\{");

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

        while ((strLine = br.readLine()) != null) {
            if(endLine) endLine = false;

            if(isEndOfBlock(strLine)){
                if(strLine.contains("{") && strLine.trim().split("\\{").length > 1){
                    Structure parentStructure = checkLineStructure(strLine.substring(0, strLine.lastIndexOf("{")+1), br);
                    DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode(parentStructure);

                    Structure childStructure = checkLineStructure(strLine.substring(strLine.lastIndexOf("{")+1, strLine.length()-1), br);
                    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(childStructure);

                    parentNode.add(childNode);
                    ret.add(parentNode);

                    endLine = true;
                }
                else break;
            }

            if(!endLine && !isComment(strLine, br)){
                strLine = getStringWithoutComment(strLine);
                Structure structure = checkLineStructure(strLine, br);

                if (structure.getClass().equals(CodeLine.class)
                        || structure.getClass().equals(EmptyLine.class)
                        || structure.getClass().equals(Return.class)){

                    ret.add(new DefaultMutableTreeNode(structure));
                }
                else if(structure.getClass().equals(Conditional.class)
                        && ((Conditional) structure).getCodeInDeclaration() != null){

                    DefaultMutableTreeNode ifNode = new DefaultMutableTreeNode(structure);
                    Structure insideStructure = checkLineStructure(((Conditional) structure).getCodeInDeclaration(),br);
                    ifNode.add(new DefaultMutableTreeNode(insideStructure));

                    ret.add(ifNode);
                }
                else {
                    Couple body = parseBody(structure,strLine, br);
                    ret.add(body.getNode());

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



        if(strLine != null && isEndOfBlock(strLine)){

            String[] startOfLine = strLine.split("}");

            if(startOfLine.length > 0 && startOfLine[0].trim().length() > 1){

                Structure structure = checkLineStructure(startOfLine[0], br);

                if (structure.getClass().equals(CodeLine.class)
                        || structure.getClass().equals(EmptyLine.class)
                        || structure.getClass().equals(Return.class)){

                    ret.add(new DefaultMutableTreeNode(structure));
                }
                else {
                    Couple body = parseBody(structure, strLine, br);
                    ret.add(body.getNode());
                }


            }
        }

        return new Couple(ret, strLine);
    }


    /**
     * CreateClass
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
     * CheckLineStructure
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

        // LIGNE VIDE
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

        // LIGNE CODE
        else {
            return new CodeLine(strLine.trim());
        }

        //else if(Pattern.matches(".*do(\\s)*{(.|\\s)*}(\\s)while(\\s)*\\(([!-z]|\\s)*\\)(\\s)*;", strLine)){
        //    return new Loop(strLine, LoopType.DOWHILE);
        //}

    }

    /**
     * GetStructureDeclaration
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
     * GetStringWithoutComment
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
     * IsAComment
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
     * IsMethod
     * @param strLine
     * @return
     */

    private static boolean isMethod(String strLine) {
        return Pattern.matches("\\s*(public|private|protected|)\\s*(static|)\\s*\\w+\\w+\\b(?<!\\breturn)\\s*(\\[|\\<|)\\s*\\s*(\\w+)*\\s*(\\s*,(\\s*)\\w+)*(\\]|\\>|)\\s*\\w+\\s*\\(.*", strLine);
    }

    /**
     * IsClass
     * @param strLine
     * @return
     */

    private static boolean isClass(String strLine) {
        return Pattern.matches("\\s*\\b(public|private|protected|)\\b\\s*\\b(static|)\\b\\s*\\b(class)\\b\\s*\\w+\\s*\\b(implements|extends|)\\b\\s*(\\w+)*\\s*(\\s*,(\\s*)\\w+)*\\s*\\{.*", strLine);
    }

    /**
     * IsEndOfBlock
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
     * CloseStreams
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
