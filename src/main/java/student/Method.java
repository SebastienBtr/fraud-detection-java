package student;

import java.util.ArrayList;
import java.util.List;

public class Method {

    private ClassMethodType visibility;

    private Boolean isConstructor;

    private boolean isStatic;

    private String returnType;

    private String name;

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

    public ClassMethodType getVisibility()
    {
        return visibility;
    }

    public void setVisibility(ClassMethodType visibility)
    {
        this.visibility = visibility;
    }

    public Boolean getConstructor()
    {
        return isConstructor;
    }

    public void setConstructor(Boolean constructor)
    {
        isConstructor = constructor;
    }

    public boolean isStatic()
    {
        return isStatic;
    }

    public void setStatic(boolean aStatic)
    {
        isStatic = aStatic;
    }

    public String getReturnType()
    {
        return returnType;
    }

    public void setReturnType(String returnType)
    {
        this.returnType = returnType;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Variable> getParameters()
    {
        return parameters;
    }

    public void setParameters(List<Variable> parameters)
    {
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Method method = (Method) o;


        if (name != null ? !name.equals(method.name) : method.name != null) return false;
        return parameters != null ? parameters.equals(method.parameters) : method.parameters == null;
    }



    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
           ret.append( "METHODE ("
                + "visibilité : " + this.visibility
                + ", estConstructeur : " + this.isConstructor
                + ", estStatic : " + this.isStatic
                + ", typeRetour : " + this.returnType
                + ", nom : " + this.name
                + ", paramétres : [");

        for(Variable param : this.parameters){
            ret.append(param.toString());
        }

        ret.append("]) {");

        return ret.toString();
    }
}
