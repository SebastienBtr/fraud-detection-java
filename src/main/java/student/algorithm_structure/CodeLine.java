package student.algorithm_structure;

import util.StringSimilarity;

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
        if (structure == null || getClass() != structure.getClass()) return 1;
        CodeLine line = (CodeLine) structure;
        Double closeness = 1.0 +  StringSimilarity.similarity(lineContent, line.lineContent);
        return closeness.intValue() ;

    }
}
