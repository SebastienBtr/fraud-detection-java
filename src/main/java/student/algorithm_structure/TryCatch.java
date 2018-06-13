package student.algorithm_structure;

public class TryCatch implements Structure {

    public ExceptionType getType() {
        return type;
    }

    public ExceptionType type;

    public TryCatch(String line) {
        if(line.contains("try")) {
            this.type = ExceptionType.TRY;
        }
        else if (line.contains("catch")) {
            this.type = ExceptionType.CATCH;
        }
        else {
            this.type = ExceptionType.FINALLY;
        }
    }

    @Override
    public String toString() {
        return type.toString().toUpperCase();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TryCatch tryCatch = (TryCatch) o;
        return ((TryCatch) o).type.equals(this.type);
       

    }

    @Override
    public int closeness(Structure structure)
    {
        if (structure == null || getClass() != structure.getClass()) return 1;

        TryCatch tryCatch = (TryCatch) structure;

        int closeness = 1;
        if(tryCatch.type.equals(this.type)){
            closeness++;
        }

        return  closeness;
    }
}
