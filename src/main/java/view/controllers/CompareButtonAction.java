package view.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import launcher.Launcher;
import parser.XlsWritter;

public class CompareButtonAction implements EventHandler<ActionEvent> {

    private Scene scene;
    private TextArea text;

    public CompareButtonAction(Scene scene, TextArea text)
    {
        this.scene = scene;
        this.text = text;
    }

    public void handle(ActionEvent event)
    {
        try{
            Launcher.compareStudents();
            String path =  XlsWritter.write();
            text.setText("Un fichier comparatif est disponible: \n"+path);
            //scene.setRoot(new StudentView(scene));
        } catch(Exception e){
            e.printStackTrace();
        }

    }
}
