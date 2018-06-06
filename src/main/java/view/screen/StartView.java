package view.screen;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import launcher.Launcher;
import view.controllers.FIleBrowserAction;
import view.controllers.ParseButtonAction;
import view.controllers.UnzippButtonAction;


public class StartView extends Application {

    private Launcher launcher;
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



        Label pathStudentFileLabel = new Label("Dossier élèves");
        grid.add(pathStudentFileLabel, 0, 0);

        pathStudentFileField = new TextField();
        grid.add(pathStudentFileField, 1, 1,2, 1);

        Button browseStudent = new Button("Parcourir...");
        grid.add(browseStudent, 3, 1);
        browseStudent.setOnAction(new FIleBrowserAction(primaryStage,pathStudentFileField,pathStudent));


        Label pathTeacherLabel = new Label("Dossier Modèle");
        grid.add(pathTeacherLabel, 0, 2);
        CheckBox isSkeletonGiven = new CheckBox();
        grid.add(isSkeletonGiven, 0, 3);

        pathTeacherField = new TextField();
        grid.add(pathTeacherField, 1, 3,2, 1);

        Button browseTeacher = new Button("Parcourir...");
        grid.add(browseTeacher, 3, 3);
        browseTeacher.setOnAction(new FIleBrowserAction(primaryStage,pathTeacherField,pathTeacher));


        parseBtn = new Button("Parse");
        parseBtn.setOnAction(null);
        parseBtn.setMaxSize(500,100);
        parseBtn.setVisible(false);
        parseBtn.setOnAction(new ParseButtonAction(scene));
        grid.add(parseBtn,2,4);
        successMessage = new Label();
        grid.add(successMessage,1,4);


        unzzipBtn = new Button("Décompresser");
        unzzipBtn.setOnAction(new UnzippButtonAction(pathStudent,pathStudentFileField,successMessage,parseBtn,root,grid));
        unzzipBtn.setMaxSize(500,100);
        grid.add(unzzipBtn, 0, 4);







        primaryStage.show();
        //FlatterFX.style();
    }

    public GridPane getGrid()
    {
        return grid;
    }

///dezipper -> parse pas affiché
    //apuye sur parse + etat du chargement
    //fini met le bouton parse la la pace du bouton decompresser

    //nouvel intent
    //config
    //launch

    //tableau double entre entrés éleves critere
    //un score par critère



}
