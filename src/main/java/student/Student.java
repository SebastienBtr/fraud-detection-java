package student;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


public class Student {


    private String name;

    private String directoryPath;

    private List<DefaultMutableTreeNode> fileTrees;

    private TreeMap<String,Integer> scores;


    public Student(String name, String directoryPath) {
        this.name = name;
        this.directoryPath = directoryPath;
        this.fileTrees = new ArrayList<DefaultMutableTreeNode>();
        this.scores = new TreeMap<>();
    }

    public void addTree(DefaultMutableTreeNode tree) {
        this.fileTrees.add(tree);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDirectoryPath(String directoryPath)
    {
        this.directoryPath = directoryPath;
    }

    public List<DefaultMutableTreeNode> getFileTrees()
    {
        return fileTrees;
    }

    public void setFileTrees(List<DefaultMutableTreeNode> fileTrees)
    {
        this.fileTrees = fileTrees;
    }

    public TreeMap<String, Integer> getScores()
    {
        return scores;
    }

    public void setScores(TreeMap<String, Integer> scores)
    {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", directoryPath='" + directoryPath + '\'' +
                ", fileTrees=" + fileTrees +
                '}';
    }

    public String getDirectoryPath() {
        return directoryPath;
    }
}
