package student;

public class Variable {

    private String type;

    private String name;

    public Variable(String name){
        this.type = null;
        this.name = name;
    }

    public Variable(String type, String name){
        this.type = type;
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
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

        Variable variable = (Variable) o;

        if (type != null ? !type.equals(variable.type) : variable.type != null) return false;
        return name != null ? name.equals(variable.name) : variable.name == null;
    }


    @Override
    public String toString() {
        return " VARIABLE ( nom : "+this.name+" ) ";
    }
}
