package parser;

import launcher.Launcher;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import student.Student;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class XlsWritter
{
    public static String write() throws IOException
    {

        List<Student> students = Launcher.getStudents();
        Workbook workbook = new XSSFWorkbook();

        CreationHelper createHelper = workbook.getCreationHelper();

        // TODO FOR EACH SIMILARITIES
        Sheet sheet = workbook.createSheet("Moyenne");


        // Create a CellStyle with the font
        CellStyle redStyle = workbook.createCellStyle();
        redStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        redStyle.setFillForegroundColor(IndexedColors.CORAL.getIndex());

        CellStyle orangeStyle = workbook.createCellStyle();
        orangeStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        orangeStyle.setFillForegroundColor(IndexedColors.TAN.getIndex());

        CellStyle yellowStyle = workbook.createCellStyle();
        yellowStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        yellowStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());

        CellStyle greenStyle = workbook.createCellStyle();
        greenStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        greenStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());


        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Create header
        Cell cell1 = headerRow.createCell(0);
        cell1.setCellValue("Élèves");

        for (int i = 0; i < Launcher.getStudents().size(); i++)
        {
            Cell cell = headerRow.createCell(i+1);
            cell.setCellValue(Launcher.getStudents().get(i).getName());



        }

        // Create Other rows and cells with student data
        int rowNum = 1;

        for (Student student : students) {

            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(student.getName());

            TreeMap<String, Double> scores = student.getScores();

            int i = 1;

            for (Map.Entry<String, Double> entry : scores.entrySet()) {

                String key = entry.getKey();
                int value = entry.getValue().intValue();

                Cell cell = row.createCell(i);
                cell.setCellValue(value);

                if (!key.equals(student.getName())) {
                    if(value <25){
                        cell.setCellStyle(greenStyle);
                    }
                    else if(value < 45){
                        cell.setCellStyle(yellowStyle);
                    }
                    else if(value < 70){
                        cell.setCellStyle(orangeStyle);
                    }
                    else{
                        cell.setCellStyle(redStyle);
                    }
                }

                i++;
            }
        }


        // Resize all columns to fit the content size
        for(int i = 0; i <  Launcher.getStudents().size()+1; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        String path = Launcher.getDirectory();
        FileOutputStream fileOut = new FileOutputStream(path.split("\\.")[0]+".xlsx");
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();
        return  path.split("\\.")[0]+".xlsx";
    }


}
