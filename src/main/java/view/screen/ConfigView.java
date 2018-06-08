package view.screen;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import view.controllers.CompareButtonAction;


public class ConfigView extends StackPane {

    private Scene scene;
    private Button compareBtn;
    private GridPane buttonGrid;


    public ConfigView(Scene scene) {

        this.scene = scene;

        this.getStylesheets().add(getClass().getResource("/css/configView.css").toExternalForm());
        VBox grid = new VBox();
        this.getChildren().add(grid);


        buttonGrid = new GridPane();

        compareBtn = new Button("Comparer");
        compareBtn.setMaxSize(500, 100);
        compareBtn.setOnAction(new CompareButtonAction(scene));
        buttonGrid.add(compareBtn, 6, 20, 12, 1);
        buttonGrid.setAlignment(Pos.CENTER);
        buttonGrid.setHgap(20);
        buttonGrid.setVgap(10);


        grid.getChildren().add(buttonGrid);

    }
}
