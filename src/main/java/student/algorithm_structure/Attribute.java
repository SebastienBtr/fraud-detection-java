package student.algorithm_structure;

import student.ClassMethodType;
import util.StringSimilarity;

public class Attribute implements Structure {

    private ClassMethodType visibility;
    private Boolean isStatic;
    private String type;
    private String name;
    private String value;

    public Attribute(String type, String name){
        this.type = type;
        this.name = name.trim();
    }

    public Attribute(ClassMethodType visibility, String type, String name){
        this.visibility = visibility;
        this.type = type;
        this.name = name.trim();
    }

    public void setValue(String value){
        this.value = value.replace(";", "");
    }

    public void setIsStatic(boolean isStatic){
        this.isStatic = isStatic;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute attribute = (Attribute) o;

        if (type != null ? !type.equals(attribute.type) : attribute.type != null) return false;
        if (name != null ? !name.equals(attribute.name) : attribute.name != null) return false;
        return value != null ? value.equals(attribute.value) : attribute.value == null;
    }

    @Override
    public int hashCode()
    {
        int result = visibility != null ? visibility.hashCode() : 0;
        result = 31 * result + (isStatic != null ? isStatic.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ATTRIBUTE ( visibility :"+this.visibility
                +", is Static : "+this.isStatic
                +", type : "+this.type
                +", nom : "+this.name
                +", valeur : "+this.value
                +")";
    }

    @Override
    public int closeness(Structure structure)
    {
        if (structure == null || getClass() != structure.getClass()) return 0;
        Attribute attribute = (Attribute) structure;
        int closeness = 0;
        if (this.name.equals(attribute.name))
        {
            closeness += 3;
        }
        if (this.isStatic.equals(attribute.isStatic))
        {
            closeness += 5;
        }
        if (this.type.equals(attribute.type))
        {
            closeness++;
        }

        if (value != null && attribute.value != null) {
            if (value.equals(attribute.value)) {
                closeness += 8;
            }
        }


        return  closeness;
    }
}
