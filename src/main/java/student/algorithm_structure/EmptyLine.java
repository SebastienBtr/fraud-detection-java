package student.algorithm_structure;

public class EmptyLine implements Structure{

    private EmptyLine(){}

    private static EmptyLine INSTANCE = new EmptyLine();

    /** Point d'accès pour l'instance unique du singleton */
    public static EmptyLine getInstance()
    {   return INSTANCE;
    }

    @Override
    public String toString() {
        return "EMPTYLINE";
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
        return 0;
    }
}
