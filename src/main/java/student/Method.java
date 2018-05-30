package student;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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



    public Method(String name) {
        this.name = name;
        this.parameters = new ArrayList<Variable>();

    }

    @Override
    public String toString() {
        return "METHODE ( " + this.name + " )";
    }
}
