package student;

import lombok.Getter;
import lombok.Setter;
import student.algorithm_structure.Structure;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Method {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String returnType;

    @Getter
    @Setter
    private List<Variable> parameters;

    @Getter
    @Setter
    private TreeSet<Structure> body;

    public Method(String name) {
        this.name = name;
        this.parameters = new ArrayList<Variable>();
        this.body = new TreeSet<Structure>();
    }

    @Override
    public String toString() {
        return "METHODE ( " + this.name + " )";
    }
}
