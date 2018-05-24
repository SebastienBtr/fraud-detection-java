package student.algorithm_structure;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Conditional extends Structure {

    @Getter
    @Setter
    private List<Structure> content;

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
}
