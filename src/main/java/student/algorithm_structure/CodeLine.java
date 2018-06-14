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

    @Override
    public int closeness(Structure structure)
    {
        if (structure == null || getClass() != structure.getClass()) return 0;
        CodeLine line = (CodeLine) structure;
        //Double closeness = 0.0 + StringSimilarity.similarity(lineContent, line.lineContent);
        if ( this.lineContent != null){
            boolean same = this.lineContent.equalsIgnoreCase(line.lineContent);
            return  same ? 1 : 0;

        }
        return 0;

    }
}
