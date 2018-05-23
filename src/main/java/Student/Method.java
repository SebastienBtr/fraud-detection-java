package Student;

import Student.algorithmStructure.CodeLine;
import Student.algorithmStructure.Structure;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Method {
    @Getter @Setter private String name;
    @Getter @Setter private String returnType;
    @Getter @Setter private List<Variable> parameters;
    @Getter @Setter private List<Structure> body;

}
