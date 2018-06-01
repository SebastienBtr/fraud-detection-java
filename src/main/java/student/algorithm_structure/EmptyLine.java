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
}
