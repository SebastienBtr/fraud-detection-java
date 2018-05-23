package student;

import lombok.Getter;
import lombok.Setter;
import student.algorithmStructure.Structure;

import java.util.List;

public class Method {
    @Getter @Setter private String name;
    @Getter @Setter private String returnType;
    @Getter @Setter private List<Variable> parameters;
    @Getter @Setter private List<Structure> body;

}
