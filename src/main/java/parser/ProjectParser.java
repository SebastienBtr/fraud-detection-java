package parser;

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

                if (!isComment(strLine, br) && isClass(strLine)) {

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
        while ((strLine = br.readLine()) != null && !isEndOfBlock(strLine)) {


            strLine = strLine.split("//")[0];

            if (isClass(strLine)) {
                ClassFile newClassIntern = ProjectParser.createClass(strLine, br);
                DefaultMutableTreeNode body = parseClass(newClassIntern, br);
                classTree.add(body);
            }
            else if (isMethod(strLine)) {
                Method newMethod = new Method(getStructureDeclaration(strLine, br));
                DefaultMutableTreeNode body = parseBody(newMethod, br).getNode();
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
     * @param parent
     * @param br
     * @return
     * @throws IOException
     */

    private static Couple parseBody(Object parent, BufferedReader br) throws IOException {
        DefaultMutableTreeNode ret = new DefaultMutableTreeNode(parent);

        String strLine;


        while ((strLine = br.readLine()) != null && !isEndOfBlock(strLine)) {

            strLine = strLine.split("//")[0];


            if(!isComment(strLine, br)){

                Structure structure = checkLineStructure(strLine, br);

                if (structure.getClass().equals(CodeLine.class)
                        || structure.getClass().equals(EmptyLine.class)
                        || structure.getClass().equals(Return.class)
                        || (strLine.trim().endsWith("}") && strLine.trim().startsWith("}"))) {

                    ret.add(new DefaultMutableTreeNode(structure));

                } else {

                    Couple body = parseBody(structure, br);
                    ret.add(body.getNode());


                   while (body.getLastLine() != null && body.getLastLine().trim().split("}").length > 1){

                       String[] lastLineParts = body.getLastLine().trim().split("}");
                       structure = checkLineStructure(lastLineParts[1], br);
                       body = parseBody(structure, br);
                       ret.add(body.getNode());

                   }

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

    private static ClassFile createClass(String strLine, BufferedReader br) throws IOException {
        if(strLine.trim().endsWith("{")) return new ClassFile(strLine);
        else throw new IOException();
    }

    /**
     * CheckLineStructure
     * @param strLine
     * @return
     */

    private static Structure checkLineStructure(String strLine, BufferedReader br) throws IOException {

        // FOR
        if (Pattern.matches(".*for(\\s)*\\(.*", strLine)) {
            return new Loop(getStructureDeclaration(strLine, br));
        }

        // IF
        else if (Pattern.matches("(\\s)*if(\\s)*\\(.*", strLine)) {
            return new Conditional(getStructureDeclaration(strLine, br));
        }

        // ELSE
        else if (strLine.contains("else")) {
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


        // LIGNE CODE
        else {
            return new CodeLine(strLine.trim());
        }

        //else if(Pattern.matches(".*do(\\s)*{(.|\\s)*}(\\s)while(\\s)*\\(([!-z]|\\s)*\\)(\\s)*;", strLine)){
        //    return new Loop(strLine, LoopType.DOWHILE);
        //}

    }

    /**
     * IsAComment
     * @param strLine
     * @param br
     * @return
     * @throws IOException
     */

    private static boolean isComment(String strLine, BufferedReader br) throws IOException {

        if(strLine.trim().startsWith("/*")){
            if(strLine.trim().contains("*/")) return true;
            else {
                String strLine2 = br.readLine();
                strLine = strLine.split("//")[0];

                while (!strLine2.contains("*/")){
                    strLine2 = br.readLine();
                    strLine = strLine.split("//")[0];
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
     * GetStructureDeclaration
     * @param strLine
     * @param br
     * @return
     * @throws IOException
     */

    private static String getStructureDeclaration(String strLine, BufferedReader br) throws IOException {
        String ret = strLine;

        while(!strLine.trim().endsWith("{")) {
            strLine = br.readLine();
            if(strLine.trim().length() != 0) ret += "\n"+strLine;
        }

        return ret;
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
