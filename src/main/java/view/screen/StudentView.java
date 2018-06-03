package view.screen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import launcher.Launcher;
import student.Student;

import java.util.ArrayList;

public class StudentView extends Scene
{
   private GridPane grid;
   private Launcher launcher;private TableView<Student> studentTable;
    private TableColumn<Student, String> studentCol;
    private final ObservableList<Student> studentList ;

    private TableColumn<Student, Integer> similarityCol;

    public StudentView(Parent root)
    {
        super(root);

        this.grid = (GridPane) root;

        studentList = null;//   FXCollections.observableArrayList(Launcher.getStudents());
        initializeTable(grid);






    }

    private void initializeTable(GridPane grid) {
        studentTable = new TableView();
        studentTable.setEditable(false);

        studentCol = new TableColumn("Élève");
        similarityCol = new TableColumn("Similarités");

        studentTable.getColumns().add(studentCol);
        studentTable.getColumns().add(similarityCol);
        //studentTable.setItems(studentList);
        studentTable.setItems( mockAddStudents());


        grid.getChildren().add(studentTable);
    }

    private ObservableList mockAddStudents(){
        ArrayList list =new ArrayList();
        list.add(new Student("pierre",""));
        list.add(new Student("paul",""));
        list.add(new Student("jaques",""));
        list.add(new Student("henry",""));


        return   FXCollections.observableArrayList(list);
    }

}
