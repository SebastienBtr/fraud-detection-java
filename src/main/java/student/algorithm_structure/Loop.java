package student.algorithm_structure;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Loop extends Structure {

    @Getter
    @Setter
    private List<Structure> content;

    @Getter
    @Setter
    private String conditions;//contents between ()

    @Getter
    @Setter
    private LoopType name;

    @Getter
    @Setter
    private IteratingType iterator;

    public Loop() {
        this.content = new ArrayList<Structure>();
    }

    public Loop(String conditions, LoopType name) {
        this.conditions = conditions;
        this.name = name;
        this.content = new ArrayList<Structure>();
    }
}
