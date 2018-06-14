package view.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import launcher.Launcher;
import parser.XlsWritter;

public class CompareButtonAction implements EventHandler<ActionEvent> {

    private Scene scene;

    public CompareButtonAction(Scene scene)
    {
        this.scene = scene;
    }

    public void handle(ActionEvent event)
    {
        try{
            Launcher.compareStudents();
            XlsWritter.write();
            //scene.setRoot(new StudentView(scene));
        } catch(Exception e){
            e.printStackTrace();
        }

    }
}
