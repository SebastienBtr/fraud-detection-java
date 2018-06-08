package parser;

import java.util.ArrayList;

public class ParsingException
{
    static ArrayList<String>  exception;


    public static void addException(String student,String file) {
        if (exception == null) {
            exception = new ArrayList<>();
        }
        exception.add("[ERREUR PARSER] " + student + " - file: " + file);
    }

    public static boolean isEmpty(){
        return exception.isEmpty();
    }


    public static String getMessage()
    {

        StringBuilder sb = new StringBuilder();
        for (String e : exception)
        {
            sb.append(e);
            sb.append("\n");
        }
        return sb.toString();
    }
}
