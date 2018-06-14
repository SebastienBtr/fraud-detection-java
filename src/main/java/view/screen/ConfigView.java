package view.screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.Errors;
import view.controllers.CompareButtonAction;


public class ConfigView extends StackPane {

    private Scene scene;
    private Button compareBtn;
    private BorderPane pageGrid;


    public ConfigView(Scene scene) {

        this.scene = scene;

        this.getStylesheets().add(getClass().getResource("/css/configView.css").toExternalForm());
        VBox grid = new VBox();
        this.getChildren().add(grid);


        pageGrid = new BorderPane();

        GridPane titleGrid = new GridPane();
        titleGrid.setAlignment(Pos.CENTER);
        titleGrid.setHgap(10);
        titleGrid.setVgap(12);

        Label title = new Label("Informations du Parser");
        title.setAlignment(Pos.CENTER);
        title.getStyleClass().add("roTitre");
        title.setPadding(new Insets(50, 25, 25, 25));
        titleGrid.add(title,0,0,12,1);

        pageGrid.setTop(titleGrid);

        GridPane textGrid = new GridPane();
        textGrid.setAlignment(Pos.CENTER);
        textGrid.setHgap(10);
        textGrid.setVgap(12);

        TextArea text = new TextArea();
        if (Errors.parserErrorsIsEmpty()) {
            text.setText("Pas de soucis lors du parse");
        }
        else {
            text.setText(Errors.getParserErrorsMessage());
        }
        text.setEditable(false);

        textGrid.add(text,1,0,10,1);
        pageGrid.setCenter(textGrid);

        GridPane buttonGrid = new GridPane();
        buttonGrid.setAlignment(Pos.CENTER);
        buttonGrid.setHgap(10);
        buttonGrid.setVgap(12);

        compareBtn = new Button("Comparer");
        compareBtn.setMaxSize(500, 100);
        compareBtn.setOnAction(new CompareButtonAction(scene));
        compareBtn.setPadding(new Insets(25, 25, 25, 25));

        buttonGrid.add(compareBtn,0,0,12,1);
        buttonGrid.setPadding(new Insets(50, 25, 25, 25));

        pageGrid.setBottom(buttonGrid);

        grid.getChildren().add(pageGrid);

    }
}
