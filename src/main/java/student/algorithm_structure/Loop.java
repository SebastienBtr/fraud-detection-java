package student.algorithm_structure;

import lombok.Getter;
import lombok.Setter;

public class Loop extends Structure {



    @Getter
    @Setter
    private String conditions;//contents between ()

    @Getter
    @Setter
    private LoopType name;

    @Getter
    @Setter
    private IteratingType iterator;


    public Loop(String conditions) {
        this.conditions = conditions;

        if(conditions.contains(";")) this.name = LoopType.FOR;
        else this.name = LoopType.FOREACH;
    }

    public Loop(String conditions, LoopType name) {
        this.conditions = conditions;
        this.name = name;
    }

    @Override
    public String toString() {
        return name.toString().toUpperCase() + " ( " + conditions + " )";
    }


    @Override
    public boolean equals(Object obj) {
        if ( obj.getClass().equals(this.getClass()) )
        {
            if (this.name.equals(((Loop)obj).name))
            {
                return  true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return  false;
        }


    }


}
