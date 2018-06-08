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


    public FIleBrowserAction(Stage stage, TextField textField)
    {
        this.stage = stage;
        this.textField = textField;

    }


    public void handle(ActionEvent event)
    {
        fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter("zip", "*.zip") );
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            textField.setText(file.getPath());
        }
    }
}
