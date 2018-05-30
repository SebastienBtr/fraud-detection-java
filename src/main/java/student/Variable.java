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
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variable variable = (Variable) o;

        if (type != null ? !type.equals(variable.type) : variable.type != null) return false;
        return name != null ? name.equals(variable.name) : variable.name == null;
    }


    @Override
    public String toString() {
        return " VARIABLE ( nom : "+this.name+" ) ";
    }
}
