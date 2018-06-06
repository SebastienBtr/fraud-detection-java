package view.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import launcher.Launcher;
import view.screen.StudentView;


public class ParseButtonAction implements EventHandler<ActionEvent>
{

        private Scene scene;

    public ParseButtonAction( Scene scene)
    {

        this.scene = scene;
    }

    public void handle(ActionEvent event)
        {
            Launcher.parseFiles();

            scene.setRoot(new StudentView());



        }

}
