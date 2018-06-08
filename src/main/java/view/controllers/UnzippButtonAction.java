package view.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import launcher.Launcher;
import util.Errors;

public class UnzippButtonAction implements EventHandler<ActionEvent> {

    private TextField studentFilePath;
    private TextField teacherPath;
    private Label successMessage;
    private Button parseBtn;
    private StackPane root;
    private GridPane grid;
    private VBox box;

    public UnzippButtonAction(TextField studentFilePath, TextField teacherPath, Label successMessage, Button parseBtn, StackPane root, GridPane grid) {
        this.studentFilePath = studentFilePath;
        this.teacherPath = teacherPath;
        this.successMessage = successMessage;
        this.parseBtn = parseBtn;
        this.root = root;
        this.grid = grid;

    }

    public void handle(ActionEvent event) {
        if (studentFilePath.getText().length() > 0) {

            //waiting();
            Errors.setUnzipperError(false);

            if (teacherPath.getText().length() > 0) {
                Launcher.init(studentFilePath.getText(), teacherPath.getText());

            } else {
                Launcher.init(studentFilePath.getText());
            }

            //goOn();

            if (Errors.isUnzipperError()) {
                successMessage.setText("Erreur lors de la Décompression");

            } else {
                parseBtn.setVisible(true);
                successMessage.setText("Décompression réussie");
            }

        } else {
            parseBtn.setVisible(false);
            successMessage.setText("Aucun dossier spécifé");
        }

        System.out.println(Launcher.getStudents().size());
    }

/*    private void goOn() {
        System.out.println("Unzipped finished");
        grid.setDisable(false);
        root.getChildren().remove(box);
    }

    private void waiting() {
        ProgressIndicator pi = new ProgressIndicator();
        box = new VBox(pi);
        box.setAlignment(Pos.CENTER);
        // Grey Background
        grid.setDisable(true);
        root.getChildren().add(box);

    }*/


}
