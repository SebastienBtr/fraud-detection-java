package util;

import java.util.ArrayList;

public class Errors {

    static ArrayList<String> parserErrors;

    static boolean unzipperError;

    public static void addParserError(String student, String file) {
        if (parserErrors == null) {
            parserErrors = new ArrayList<>();
        }
        parserErrors.add("[ERREUR PARSER] " + student + " - file: " + file);
    }

    public static boolean parserErrorsIsEmpty() {
        return parserErrors.isEmpty();
    }

    public static String getParserErrorsMessage() {

        StringBuilder sb = new StringBuilder();
        for (String e : parserErrors) {
            sb.append(e);
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void setUnzipperError(boolean unzipperError) {
        Errors.unzipperError = unzipperError;
    }

    public static boolean isUnzipperError() {
        return unzipperError;
    }
}
