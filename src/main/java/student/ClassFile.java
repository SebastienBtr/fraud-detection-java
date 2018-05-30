package student;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    public String toString() {
        return "CLASS ("
                + "visibilité : " + this.visibility
                + ", estStatic : " + this.isStatic
                + ", nom : " + this.name +") {";
    }
}
