package student;

import lombok.Getter;
import lombok.Setter;
import student.algorithm_structure.Structure;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Method {

    @Getter
    @Setter
    private ClassMethodType visibility;

    @Getter
    @Setter
    private Boolean isConstructor;

    @Getter
    @Setter
    private boolean isStatic;

    @Getter
    @Setter
    private String returnType;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private List<Variable> parameters;

    public Method(String classDeclaration) {
        String[] signature = classDeclaration.trim().split("\\(")[0].split(" ");

        if(signature.length == 1){
            this.visibility = null;
            this.isStatic = false;
            this.isConstructor = true;
            this.name = signature[0];
        }
        else if(signature.length == 2){
            if(ClassMethodType.isClassMethodType(signature[0])){
                this.visibility = ClassMethodType.fromString(signature[0]);
                this.isConstructor = true;
            }
            else {
                this.returnType = signature[0];
                this.isConstructor = false;
            }

            this.isStatic = false;
            this.name = signature[1];
        }
        else if(signature.length == 3){
            if(ClassMethodType.isClassMethodType(signature[0])){
                this.visibility = ClassMethodType.fromString(signature[0]);
            }
            else {
                this.isStatic = true;
            }

            this.isConstructor = false;
            this.returnType = signature[1];
            this.name = signature[2];
        }
        else {
            this.visibility = ClassMethodType.fromString(signature[0]);
            this.isStatic = true;
            this.isConstructor = false;
            this.returnType = signature[2];
            this.name = signature[3];
        }

        String parametersString = classDeclaration.split("\\(")[1];
        this.parseMethodParameters(parametersString.substring(0, parametersString.length() - 1));
    }

    private void parseMethodParameters(String parameters){
        this.parameters = new ArrayList<Variable>();

        String[] params = parameters.split(",");

        for(String param : params){
            //String[] elements = param.split(" ");
            //this.parameters.add(new Variable(elements[0],elements[1]));
            this.parameters.add(new Variable(param));
        }
    }

    @Override
    public String toString() {
        String ret = "METHODE ("
                + "visibilité : " + this.visibility
                + ", estConstructeur : " + this.isConstructor
                + ", estStatic : " + this.isStatic
                + ", typeRetour : " + this.returnType
                + ", nom : " + this.name
                + ", paramétres : [";

        for(Variable param : this.parameters){
            ret += param.toString();
        }

        ret += "]) {";

        return ret;
    }
}
