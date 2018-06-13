package parser;

import launcher.Launcher;
import student.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWritter
{

    public static void write() throws IOException
    {
        FileWriter writer = new FileWriter("test.csv");
        List<Student> students = Launcher.getStudents();
        writer.append("Élèves;");
        for (int i = 0; i < Launcher.getStudents().size(); i++)
        {
            writer.append( Launcher.getStudents().get(i).getName());
            if(i < Launcher.getStudents().size()-1){
                writer.append(';');
            }


        }
        writer.append("\n");


        for (int i = 0; i < Launcher.getStudents().size(); i++)
        {
            writer.append( Launcher.getStudents().get(i).getName()+";");
            for (int j = 0; j < Launcher.getStudents().size(); j++)
            {
                writer.append( Launcher.getStudents().get(i).getScores().get(Launcher.getStudents().get(j).getName()).intValue()+"");
                if(j < Launcher.getStudents().size()-1){
                    writer.append(';');
                }
            }
            writer.append("\n");
        }

        writer.flush();
        writer.close();
    }
}
