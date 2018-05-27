package student.algorithm_structure;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class Conditional extends Structure {

    @Getter
    @Setter
    private String conditions;//contents between ()

    @Getter
    @Setter
    private Conditional elseCond;


    public Conditional() {
        this.content = new ArrayList<Structure>();
    }

    public Conditional(String conditions) {

        this.conditions = conditions;
        this.content = new ArrayList<Structure>();
    }

    @Override
    public String toString() {
        return "IF ( "+conditions+" )";
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
