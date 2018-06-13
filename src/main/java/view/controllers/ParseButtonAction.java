package view.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import launcher.Launcher;
import util.Errors;
import view.screen.ConfigView;
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
            scene.setRoot(new ConfigView(scene));
        } catch(Exception e){
            e.printStackTrace();
        }

        if (!Errors.parserErrorsIsEmpty()) {
            message.setText(Errors.getParserErrorsMessage());
        }

    }

}
