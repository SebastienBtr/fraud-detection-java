package view.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;

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
       // scene.setRoot(new StudentView());
    }
}
