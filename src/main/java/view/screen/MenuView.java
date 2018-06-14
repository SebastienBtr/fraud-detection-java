package view.screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.controllers.FIleBrowserAction;
import view.controllers.ParseButtonAction;
import view.controllers.UnzipButtonAction;

public class MenuView extends StackPane {

    private Scene scene;

    private TextField pathTeacherField;
    private TextField pathStudentFileField;
    private  GridPane panel;
    private Button unzzipBtn;
    private Button parseBtn;
    private Label successMessage;

    public MenuView(Scene scene, Stage primaryStage) {
        this.scene = scene;

        this.getStylesheets().add(getClass().getResource("/css/startView.css").toExternalForm());
        VBox grid = new VBox();
        this.getChildren().add(grid);

        panel = new GridPane();
        panel.setAlignment(Pos.CENTER);
        panel.setHgap(20);
        panel.setVgap(10);
        panel.setPadding(new Insets(25, 25, 25, 25));

        grid.getChildren().add(panel);


        GridPane titleGrid = new GridPane();
        titleGrid.setAlignment(Pos.TOP_CENTER);
        titleGrid.setPadding(new Insets(0, 25, 25, 25));

        Label bigTitle = new Label("DÉTECTION DE FRAUDE");
        bigTitle.getStyleClass().add("roTitre");

        Label subTitle = new Label("Ils ont cru qu'ils pourraient s'en sortir...");
        subTitle.getStyleClass().add("sousTitre");

        titleGrid.add(bigTitle,0,0,4,1);
        titleGrid.add(subTitle,1,1,4,1);

        panel.add(titleGrid,0,0,10,1);

        GridPane studentGrid = new GridPane();
        studentGrid.setAlignment(Pos.TOP_CENTER);
        studentGrid.setHgap(20);
        studentGrid.setVgap(10);
        studentGrid.setPadding(new Insets(25, 25, 0, 25));

        Label pathStudentFileLabel = new Label("Dossier élèves");
        studentGrid.add(pathStudentFileLabel, 0, 2);

        pathStudentFileField = new TextField("C:\\Users\\lea\\Documents\\IMT\\ProjetFraude\\fraud-detection-java\\src\\test\\data\\exam2.zip");
        studentGrid.add(pathStudentFileField, 0, 3,5, 1);

        Button browseStudent = new Button("Parcourir...");
        studentGrid.add(browseStudent, 5, 3);
        browseStudent.setOnAction(new FIleBrowserAction(primaryStage,pathStudentFileField));

        panel.add(studentGrid,0,1,10,1);

        GridPane teacherGrid = new GridPane();
        teacherGrid.setAlignment(Pos.TOP_CENTER);
        teacherGrid.setHgap(20);
        teacherGrid.setVgap(10);
        teacherGrid.setPadding(new Insets(0, 25, 0, 25));

        Label pathTeacherLabel = new Label("Dossier Modèle");
        teacherGrid.add(pathTeacherLabel, 0, 2);

        pathTeacherField = new TextField("C:\\Users\\lea\\Documents\\IMT\\ProjetFraude\\fraud-detection-java\\src\\test\\data\\teacher2.zip");
        teacherGrid.add(pathTeacherField, 0, 3,5, 1);

        Button browseTeacher = new Button("Parcourir...");
        teacherGrid.add(browseTeacher, 5, 3);
        browseTeacher.setOnAction(new FIleBrowserAction(primaryStage,pathTeacherField));


        panel.add(teacherGrid,0,2,10,1);

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
        unzzipBtn.setOnAction(new UnzipButtonAction(pathStudentFileField,pathTeacherField,successMessage,parseBtn));
        unzzipBtn.setMaxSize(500,100);
        buttonGrid.add(unzzipBtn, 0, 6,5,1);


        panel.add(buttonGrid,0,3,10,1);

    }
}
