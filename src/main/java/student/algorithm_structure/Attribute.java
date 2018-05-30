package student.algorithm_structure;

import student.ClassMethodType;

public class Attribute {

    private ClassMethodType visibility;
    private String type;
    private String name;
    private String value;

    public Attribute(String type, String name){
        this.type = type;
        this.name = name.replace(";", "");
    }

    public Attribute(ClassMethodType visibility, String type, String name){
        this.visibility = visibility;
        this.type = type;
        this.name = name.replace(";", "");
    }

    public void setValue(String value){
        this.value = value.replace(";", "");
    }

    @Override
    public String toString() {
        return "ATTRIBUTE ( visibility :"+this.visibility
                +", type : "+this.type
                +", nom : "+this.name
                +", valeur : "+this.value
                +")";
    }
}
