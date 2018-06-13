package view.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import launcher.Launcher;
import util.Errors;
import view.screen.ConfigView;
import view.screen.StudentView;

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
            scene.setRoot(new StudentView(scene));
        } catch(Exception e){
            e.printStackTrace();
        }

    }
}
