package Student;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Class {
    @Getter @Setter private List<Variable> attributes;
    @Getter @Setter private List<Method> methods;
    @Getter @Setter private String filePath;



}
