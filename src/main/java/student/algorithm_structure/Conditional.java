package student.algorithm_structure;

import lombok.Getter;
import lombok.Setter;

public class Conditional extends Structure {

    @Getter
    @Setter
    private String conditions;//contents between ()

    @Getter
    @Setter
    private Conditional elseCond;



    public Conditional(String conditions) {

        this.conditions = conditions;

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
