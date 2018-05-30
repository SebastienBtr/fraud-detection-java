package student;

import lombok.Getter;
import lombok.Setter;

public class ClassFile {

    @Getter
    @Setter
    private String visibility;

    @Getter
    @Setter
    private boolean isStatic;

    @Getter
    @Setter
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
