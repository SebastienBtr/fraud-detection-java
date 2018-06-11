package view.screen;


import com.guigarage.flatterfx.FlatterFX;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class StartView extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {

        StackPane root = new StackPane();
        root.getStylesheets().add(getClass().getResource("/css/startView.css").toExternalForm());

        primaryStage.setTitle("Fraude Buster");
        primaryStage.setMaximized(true);
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);


        scene.setRoot(new MenuView(scene, primaryStage));


        primaryStage.show();
        FlatterFX.style();
    }




    //nouvel intent
    //config
    //launch

    //tableau double entre entrés éleves critere
    //un score par critère



}
