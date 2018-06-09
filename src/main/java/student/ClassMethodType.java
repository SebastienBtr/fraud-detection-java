package student;

public enum ClassMethodType {

    PUBLIC("public"),
    PRIVATE("private"),
    PROTECTED("protected");

    private String type;

    ClassMethodType(String type){
        this.type = type;
    }

    public static boolean isClassMethodType(String type){
        return (type.equals("public") || type.equals("private") || type.equals("protected"));
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
