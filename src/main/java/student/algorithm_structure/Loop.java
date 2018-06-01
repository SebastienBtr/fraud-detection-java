package student.algorithm_structure;

public class Loop implements Structure {


    private String conditions;//contents between ()

    private LoopType name;

    private IteratingType iterator;


    public Loop(String conditions) {
        this.conditions = conditions;

        if(conditions.contains(";")) this.name = LoopType.FOR;
        else this.name = LoopType.FOREACH;
    }

    public Loop(String conditions, LoopType name) {
        this.conditions = conditions;
        this.name = name;
    }

    public String getConditions()
    {
        return conditions;
    }

    public void setConditions(String conditions)
    {
        this.conditions = conditions;
    }

    public LoopType getName()
    {
        return name;
    }

    public void setName(LoopType name)
    {
        this.name = name;
    }

    public IteratingType getIterator()
    {
        return iterator;
    }

    public void setIterator(IteratingType iterator)
    {
        this.iterator = iterator;
    }

    @Override
    public String toString() {
        return name.toString().toUpperCase() + " ( " + conditions + " )";
    }


    @Override
    public boolean equals(Object obj) {
        if ( obj.getClass().equals(this.getClass()) )
        {
            if (this.name.equals(((Loop)obj).name))
            {
                return  true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return  false;
        }


    }


}
