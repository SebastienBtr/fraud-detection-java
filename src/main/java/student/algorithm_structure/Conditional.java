package student.algorithm_structure;

public class Conditional implements Structure {

    private String conditions;//contents between ()

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return  true;

    }
}
