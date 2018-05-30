package student;

import lombok.Getter;
import lombok.Setter;

public class Variable {

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private String name;

    public Variable(String name){
        this.type = null;
        this.name = name;
    }

    public Variable(String type, String name){
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return " VARIABLE ( nom : "+this.name+" ) ";
    }
}
