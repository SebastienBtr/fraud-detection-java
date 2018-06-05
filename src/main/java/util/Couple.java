package util;

import javax.swing.tree.DefaultMutableTreeNode;

public class Couple{
    private DefaultMutableTreeNode node;
    private String lastLine;

    public Couple(DefaultMutableTreeNode node, String lastLine) {
        this.node = node;
        this.lastLine = lastLine;
    }

    public void setLastLine(String lastLine) {
        this.lastLine = lastLine;
    }

    public DefaultMutableTreeNode getNode() {
        return node;
    }

    public String getLastLine() {
        return lastLine;
    }
}
