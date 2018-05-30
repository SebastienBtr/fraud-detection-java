package student.algorithm_structure;

import lombok.Getter;
import lombok.Setter;

public class Conditional extends Structure {

    @Getter
    @Setter
    private String conditions;//contents between ()

    @Getter
    @Setter
    private ConditionalType type;


    public Conditional(String conditions) {
        this.conditions = conditions;
        this.type = ConditionalType.IF;
    }

    public Conditional(String conditions, ConditionalType type) {
        this.conditions = conditions;
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type.toString()+" ( "+conditions+" )";
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
