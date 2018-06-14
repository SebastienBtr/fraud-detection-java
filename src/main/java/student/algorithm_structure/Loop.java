package student.algorithm_structure;

import util.StringSimilarity;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Loop loop = (Loop) o;


        if (this.name.equals(loop.name))
        {
            return  true;
        }
        else
        {
            return false;
        }

    }


    @Override
    public int closeness(Structure structure)
    {

        if (structure == null || getClass() != structure.getClass()) return 0;

        Loop loop = (Loop) structure;

        int closeness = 0;
        if (this.name.equals(loop.name))
        {
            closeness++;
        }
//        if (this.iterator.equals(loop.iterator))
//        {
//            closeness++;
//        }
        if (conditions != null && loop.conditions != null) {
            if (this.conditions.equals(loop.conditions)) {
                closeness += 8;
            }
        }


        return  closeness;
    }
}
