package student.algorithm_structure;

public class TryCatch extends Structure {

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
}
