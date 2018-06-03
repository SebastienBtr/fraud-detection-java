package view.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FIleBrowserAction implements EventHandler<ActionEvent>
{
    final FileChooser fileChooser = new FileChooser();
    private Stage stage;
    private TextField textField;
    private String path;

    public FIleBrowserAction(Stage stage, TextField textField, String path)
    {
        this.stage = stage;
        this.textField = textField;
        this.path = path;
    }


    public void handle(ActionEvent event)
    {
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            path = file.getPath();
            textField.setText(file.getPath());
        }
    }
}
