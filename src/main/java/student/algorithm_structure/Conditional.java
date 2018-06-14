package student.algorithm_structure;

import util.StringSimilarity;

public class Conditional implements Structure {

    private String codeInDeclaration;

    public String getConditions() {
        return conditions;
    }

    public ConditionalType getType() {
        return type;
    }

    private String conditions;//contents between ()

    private ConditionalType type;


    public Conditional(String conditions) throws Exception{
        this.conditions = conditions;
        this.type = ConditionalType.IF;

        checkCodeInDeclaration();
    }

    public Conditional(String conditions, ConditionalType type) throws Exception{
        this.conditions = conditions;
        this.type = type;

        checkCodeInDeclaration();
    }

    private void checkCodeInDeclaration() throws Exception{
        if(!this.conditions.trim().endsWith("{")){
            if(this.type == ConditionalType.ELSE){
                this.codeInDeclaration = this.conditions.split("else")[1];
                this.conditions = null;
            }
            else {
                char[] listChar = this.conditions.toCharArray();
                int indexLastParent = 0;
                int parentCount = -1;

                while (parentCount != 0){
                    if(listChar[indexLastParent] == '('){
                        if(parentCount == -1){
                            parentCount = 1;
                        }
                        else {
                            parentCount ++;
                        }
                    }
                    else if(listChar[indexLastParent] == ')') {
                        parentCount--;
                    }

                    indexLastParent++;
                }

                this.codeInDeclaration = this.conditions.substring(indexLastParent,this.conditions.length());
                this.conditions = this.conditions.substring(0,indexLastParent);
            }
        }
    }

    public String getCodeInDeclaration() {
        return codeInDeclaration;
    }

    @Override
    public String toString() {
        return this.type.toString()+" ( "+conditions+" )";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;

    }

    @Override
    public int closeness(Structure structure)
    {
        if (structure == null || getClass() != structure.getClass()) return 0;

        Conditional cond = (Conditional) structure;

        int closeness = 0;
        if (cond.type.equals(this.type))
        {
            closeness++;
        }

        if (conditions != null && cond.conditions != null) {
            if(conditions.equals(cond.conditions)) {
                closeness += 8;
            }
        }


        return  closeness;
    }
}
