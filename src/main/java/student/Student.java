package student;

import lombok.Getter;
import lombok.Setter;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.List;


public class Student {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String directoryPath;

    @Getter
    @Setter
    private List<DefaultMutableTreeNode> fileTrees;

    public Student(String name, String directoryPath) {
        this.name = name;
        this.directoryPath = directoryPath;
        this.fileTrees = new ArrayList<DefaultMutableTreeNode>();
    }

    public void addTree(DefaultMutableTreeNode tree) {
        this.fileTrees.add(tree);
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
