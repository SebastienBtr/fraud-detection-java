package comparator;

import student.algorithm_structure.Loop;
import student.algorithm_structure.Structure;

import java.util.ArrayList;

public class MethodComparator
{


    /**
     *  Compare two tree of structures
     * @param student1
     * @param student2
     * @return a grade of similarity
     */
    public static int compareStructure(ArrayList<Structure> student1, ArrayList<Structure> student2)
        {
        if (student1.size() > student2.size())
        {
             return compareStructureOrdered(student1,student2);
        }
        else
        {
            return compareStructureOrdered(student2,student1);
        }

    }

    /**
     * Compare two trees of structure with the bigger one as the first parameter
     * @param longer
     * @param shorter
     * @return grade
     */
    private static int compareStructureOrdered(ArrayList<Structure> longer, ArrayList<Structure> shorter)
    {
        int grade = 0;
        for (int i = 0; i < longer.size() ; i++) {
            if ( i < shorter.size())
            {
                if (longer.get(i).equals(shorter.get(i)))
                {
                    grade = structureContentSimilarities(longer.get(i), shorter.get(i), grade, Similarities.SAME_STRUCTURE_SAME_SPOT);

                }
            }
            else
            {
                if(shorter.contains(longer.get(i))){
                    grade = structureContentSimilarities(longer.get(i), shorter.get(i), grade,Similarities.CONTAIN_STRUCTURE);
                }

            }
        }
      return grade;
    }

    /**
     * Grade closeness between two similar structure
     * @param structure1
     * @param structure2
     * @param grade
     * @return grade
     */
    private static int structureContentSimilarities(Structure structure1, Structure structure2, int grade,int similarity)
    {
        if (structure1.getClass().equals(Loop.class))
        {
            if(((Loop)structure1).getName().equals(((Loop)structure2).getName()))
            {
                grade += similarity * Similarities.SAME_LOOP;
            }
        }
        else
        {
            grade += similarity;
        }
        grade *= compareStructure(structure1.getContent(),structure2.getContent());
        
        return grade;
    }

}
