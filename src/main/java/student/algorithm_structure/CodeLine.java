package student.algorithm_structure;

import lombok.Getter;
import lombok.Setter;

public class CodeLine extends Structure {

    @Getter
    @Setter
    private String lineContent;

    public CodeLine(String lineContent) {
        this.lineContent = lineContent;
    }

    @Override
    public String toString() {
        return " CODELINE ( " + this.lineContent + " )";
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj.getClass().equals(this.getClass()) )
        {
            return true;
        }
        else
        {
            return  false;
        }


    }
}
