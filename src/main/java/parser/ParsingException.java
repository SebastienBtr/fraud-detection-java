package parser;

import java.util.ArrayList;

public class ParsingException extends  Exception
{
   ArrayList<String> exception;

    public ParsingException()
    {
        exception = new ArrayList<>();
    }

    public void addException(String student,String file){
        exception.add("[ERREUR PARSER] " + student + " - file: " + file);
    }

    public boolean isEmpty(){
        return exception.isEmpty();
    }

    @Override
    public String getMessage()
    {

        StringBuilder sb = new StringBuilder();
        for (String e : exception        )
        {
            sb.append(e);
            sb.append("\n");
        }
        return sb.toString();
    }
}
