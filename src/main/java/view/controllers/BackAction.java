package view.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.screen.MenuView;
import view.screen.StartView;
import view.screen.StudentView;

public class BackAction implements EventHandler<ActionEvent>
{
    private Scene scene;

    public BackAction(Scene scene)
    {
        this.scene = scene;
    }

    @Override
    public void handle(ActionEvent event)
    {
       //scene.setRoot(new MenuView(scene));
    }
}
