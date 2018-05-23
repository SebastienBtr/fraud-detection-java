package Student.algorithmStructure;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Conditional extends Structure{
    @Getter @Setter private List<Structure> content;
    @Getter @Setter private String conditions;//contents between ()
    @Getter @Setter private Conditional elseCond;


    public Conditional() {

    }
    public Conditional(String conditions) {
        this.conditions = conditions;
    }
}
