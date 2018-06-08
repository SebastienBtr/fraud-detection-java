package view.screen;


import com.guigarage.flatterfx.FlatterFX;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import view.controllers.FIleBrowserAction;
import view.controllers.ParseButtonAction;
import view.controllers.UnzippButtonAction;


public class StartView extends Application {

    private TextField pathTeacherField;
    private TextField pathStudentFileField;
    private  String pathTeacher;
    private  String pathStudent;
    private  GridPane grid;
    private Button unzzipBtn;
    private Button parseBtn;
    private Label successMessage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {

        StackPane root = new StackPane();
        root.getStylesheets().add(getClass().getResource("/css/startView.css").toExternalForm());

        primaryStage.setTitle("Fraude Buster");
        primaryStage.setMaximized(true);

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        root.getChildren().add(grid);

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);

        GridPane titleGrid = new GridPane();
        titleGrid.setAlignment(Pos.TOP_CENTER);
        titleGrid.setPadding(new Insets(0, 25, 25, 25));

        Label bigTitle = new Label("DÉTECTION DE FRAUDE");
        bigTitle.getStyleClass().add("roTitre");

        Label subTitle = new Label("Ils ont cru qu'ils pourraient s'en sortir...");
        subTitle.getStyleClass().add("sousTitre");

        titleGrid.add(bigTitle,0,0,4,1);
        titleGrid.add(subTitle,1,1,4,1);

        grid.add(titleGrid,0,0,10,1);

        GridPane studentGrid = new GridPane();
        studentGrid.setAlignment(Pos.TOP_CENTER);
        studentGrid.setHgap(20);
        studentGrid.setVgap(10);
        studentGrid.setPadding(new Insets(25, 25, 0, 25));

        Label pathStudentFileLabel = new Label("Dossier élèves");
        studentGrid.add(pathStudentFileLabel, 0, 2);

        pathStudentFileField = new TextField("/home/gaetan/Documents/IMTA_A1/Outils/src/test/data/exam3.zip");
        studentGrid.add(pathStudentFileField, 0, 3,5, 1);

        Button browseStudent = new Button("Parcourir...");
        studentGrid.add(browseStudent, 5, 3);
        browseStudent.setOnAction(new FIleBrowserAction(primaryStage,pathStudentFileField));

        grid.add(studentGrid,0,1,10,1);

        GridPane teacherGrid = new GridPane();
        teacherGrid.setAlignment(Pos.TOP_CENTER);
        teacherGrid.setHgap(20);
        teacherGrid.setVgap(10);
        teacherGrid.setPadding(new Insets(0, 25, 0, 25));

        Label pathTeacherLabel = new Label("Dossier Modèle");
        teacherGrid.add(pathTeacherLabel, 0, 2);

        pathTeacherField = new TextField("/home/gaetan/Documents/IMTA_A1/Outils/src/test/data/teacher2.zip");
        teacherGrid.add(pathTeacherField, 0, 3,5, 1);

        Button browseTeacher = new Button("Parcourir...");
        teacherGrid.add(browseTeacher, 5, 3);
        browseTeacher.setOnAction(new FIleBrowserAction(primaryStage,pathTeacherField));


        grid.add(teacherGrid,0,2,10,1);

        GridPane buttonGrid = new GridPane();
        buttonGrid.setAlignment(Pos.TOP_CENTER);
        buttonGrid.setHgap(20);
        buttonGrid.setVgap(10);


        successMessage = new Label();
        buttonGrid.add(successMessage,0,7,5,1);

        parseBtn = new Button("Parse");
        parseBtn.setOnAction(null);
        parseBtn.setMaxSize(500,100);
        parseBtn.setVisible(false);
        parseBtn.setOnAction(new ParseButtonAction(scene,successMessage));
        buttonGrid.add(parseBtn,7,6,5,1);



        unzzipBtn = new Button("Décompresser");
        unzzipBtn.setOnAction(new UnzippButtonAction(pathStudentFileField,pathTeacherField,successMessage,parseBtn,root,grid));
        unzzipBtn.setMaxSize(500,100);
        buttonGrid.add(unzzipBtn, 0, 6,5,1);


        grid.add(buttonGrid,0,3,10,1);


        primaryStage.show();
        FlatterFX.style();
    }




    //nouvel intent
    //config
    //launch

    //tableau double entre entrés éleves critere
    //un score par critère



}
