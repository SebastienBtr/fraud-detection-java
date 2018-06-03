package view.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.screen.StudentView;

public class UnzippButtonAction implements EventHandler<ActionEvent>
{
    private String path ;
    private Stage stage;

    public UnzippButtonAction(String path, Stage stage)
    {
        this.path = path;
        this.stage = stage;


    }

    public void handle(ActionEvent event)
    {
//        Thread moveThread = new Thread(new Runnable()
//        {
//            public void run()
//            {
//                //Launcher.init(path);
//                System.out.println("dezippe");
//
//            }
//        });
//        Stage modal =  waiting();
//        moveThread.start();
//        try
//        {
//            moveThread.wait();
//        } catch (InterruptedException e)
//        {
//            e.printStackTrace();//TODO
//        }
//        modal.close();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        stage.setScene(new StudentView(grid));


    }

    private Stage waiting() {
        Stage dialog = new Stage();
        dialog.initOwner(stage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
        return  stage;
    }

}
