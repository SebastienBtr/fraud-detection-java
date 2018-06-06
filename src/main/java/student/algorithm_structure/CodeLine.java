package student.algorithm_structure;

public class CodeLine implements Structure {

    private String lineContent;

    public CodeLine(String lineContent) {
        this.lineContent = lineContent;
    }


    public String getLineContent()
    {
        return lineContent;
    }

    public void setLineContent(String lineContent)
    {
        this.lineContent = lineContent;
    }

    @Override
    public String toString() {
        return "CODELINE ( " + this.lineContent + " )";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeLine line = (CodeLine) o;
        return  lineContent.equals(line.lineContent);


    }
}
