package student;

public enum ClassMethodType {

    PUBLIC("public"),
    PRIVATE("private"),
    PROTECTED("protected");

    private String type;

    ClassMethodType(String type){
        this.type = type;
    }

    static boolean isClassMethodType(String type){
        return (type == "public" || type == "private" || type == "protected");
    }

    public static ClassMethodType fromString(String text) {
        for (ClassMethodType b : ClassMethodType.values()) {
            if (b.type.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
