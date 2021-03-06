package student.algorithm_structure;

import util.StringSimilarity;

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

    @Override
    public int closeness(Structure structure)
    {
        if (structure == null || getClass() != structure.getClass()) return 0;
        Return line = (Return) structure;
        int closeness = 0;
        if (name.equals(line.name)) {
            closeness += 8;
        }
        return closeness;
    }
}
