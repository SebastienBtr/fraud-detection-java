package view.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import launcher.Launcher;
import parser.ParsingException;
import view.screen.StudentView;


public class ParseButtonAction implements EventHandler<ActionEvent>
{

    private Scene scene;
    private Label message;

    public ParseButtonAction(Scene scene, Label message)
    {
        this.scene = scene;
        this.message = message;
    }

    public void handle(ActionEvent event)
    {
        try{
            Launcher.parseFiles();
            Launcher.compareStudents();
            scene.setRoot(new StudentView());
        } catch(Exception e){
            e.printStackTrace();
        }

        if (!ParsingException.isEmpty()) {
            message.setText(ParsingException.getMessage());
        }

    }

}
