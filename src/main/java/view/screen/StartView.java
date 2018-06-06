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


        Label bigTitle = new Label("Ils ont cru qu'ils pouvaient s'en sortir ");
        bigTitle.getStyleClass().add("roTitre");

        grid.add(bigTitle,0,0,4,1);

        Label pathStudentFileLabel = new Label("Dossier élèves");
        grid.add(pathStudentFileLabel, 0, 1);

        pathStudentFileField = new TextField("C:\\Users\\lea\\Documents\\IMT\\ProjetFraude\\fraud-detection-java\\src\\test\\data\\exam2.zip");
        grid.add(pathStudentFileField, 1, 2,2, 1);

        Button browseStudent = new Button("Parcourir...");
        grid.add(browseStudent, 3, 2);
        browseStudent.setOnAction(new FIleBrowserAction(primaryStage,pathStudentFileField,pathStudent));


        Label pathTeacherLabel = new Label("Dossier Modèle");
        grid.add(pathTeacherLabel, 0, 3);
//        CheckBox isSkeletonGiven = new CheckBox();
//        grid.add(isSkeletonGiven, 0, 4);

        pathTeacherField = new TextField("C:\\Users\\lea\\Documents\\IMT\\ProjetFraude\\fraud-detection-java\\src\\test\\data\\teacher2.zip");
        grid.add(pathTeacherField, 1, 4,2, 1);

        Button browseTeacher = new Button("Parcourir...");
        grid.add(browseTeacher, 3, 4);
        browseTeacher.setOnAction(new FIleBrowserAction(primaryStage,pathTeacherField,pathTeacher));

        successMessage = new Label();
        grid.add(successMessage,0,6,5,1);

        parseBtn = new Button("Parse");
        parseBtn.setOnAction(null);
        parseBtn.setMaxSize(500,100);
        parseBtn.setVisible(false);
        parseBtn.setOnAction(new ParseButtonAction(scene,successMessage));
        grid.add(parseBtn,3,5);



        unzzipBtn = new Button("Décompresser");
        unzzipBtn.setOnAction(new UnzippButtonAction(pathStudentFileField,pathTeacherField,successMessage,parseBtn,root,grid));
        unzzipBtn.setMaxSize(500,100);
        grid.add(unzzipBtn, 0, 5);


        primaryStage.show();
        FlatterFX.style();
    }




    //nouvel intent
    //config
    //launch

    //tableau double entre entrés éleves critere
    //un score par critère



}
