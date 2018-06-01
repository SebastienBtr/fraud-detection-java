package student;

public class ClassFile {


    private String visibility;


    private boolean isStatic;

    private String name;

    public ClassFile(String classDeclaration) {
           String[] signature = classDeclaration.split("\\{")[0].split(" ");

           this.visibility = signature[0];

           if(signature.length >= 3){
               this.isStatic = true;
               this.name = signature[2];
           }
           else {
               this.isStatic = false;
               this.name = signature[1];
           }
    }

    public String getVisibility()
    {
        return visibility;
    }

    public void setVisibility(String visibility)
    {
        this.visibility = visibility;
    }

    public boolean isStatic()
    {
        return isStatic;
    }

    public void setStatic(boolean aStatic)
    {
        isStatic = aStatic;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClassFile classFile = (ClassFile) o;

        if (isStatic != classFile.isStatic) return false;
        if (visibility != null ? !visibility.equals(classFile.visibility) : classFile.visibility != null) return false;
        return name != null ? name.equals(classFile.name) : classFile.name == null;
    }


    @Override
    public String toString() {
        return "CLASS ("
                + "visibilité : " + this.visibility
                + ", estStatic : " + this.isStatic
                + ", nom : " + this.name +") {";
    }
}
