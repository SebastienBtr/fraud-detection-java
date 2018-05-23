package student.algorithmStructure;

import lombok.Getter;
import lombok.Setter;

public class CodeLine  extends Structure{
    @Getter  @Setter private String lineContent;

    public CodeLine(String lineContent) {
        this.lineContent = lineContent;
    }
}
