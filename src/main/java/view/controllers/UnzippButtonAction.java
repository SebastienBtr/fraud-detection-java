package view.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import launcher.Launcher;

public class UnzippButtonAction implements EventHandler<ActionEvent>
{
    private String path ;

    private TextField studentFilePath;
    private Label successMessage;
    private Button parseBtn;
    private StackPane root;
    private GridPane grid;
    private VBox box;

     public UnzippButtonAction(String path, TextField studentFilePath, Label successMessage, Button parseBtn, StackPane root, GridPane grid)
    {
        this.path = path;
        this.studentFilePath = studentFilePath;
        this.successMessage = successMessage;
        this.parseBtn = parseBtn;
        this.root = root;
        this.grid = grid;
    }

    public void handle(ActionEvent event)
    {
        if (studentFilePath.getText().length() > 0){

            try{
                waiting();

               Launcher.init(path);


            }catch(Exception e){
                parseBtn.setVisible(false);
                successMessage.setText("Une erreur est survenue lors de la décompression");
                goOn();

            }
            parseBtn.setVisible(true);
            successMessage.setText("Décompression réussie");
        }
        else{
            parseBtn.setVisible(false);
            successMessage.setText("Aucun dossier spécifé");
        }



    }

    private void goOn(){
        grid.setDisable(false);
        root.getChildren().remove(box);
    }

    private void waiting(){
        ProgressIndicator pi = new ProgressIndicator();
        box = new VBox(pi);
        box.setAlignment(Pos.CENTER);
        // Grey Background
        grid.setDisable(true);
        root.getChildren().add(box);

    }





}
