package student.algorithm_structure;

public class Return implements Structure{

    private String name;

    public Return(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RETURN ( "+this.name+" )";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;

    }
}
