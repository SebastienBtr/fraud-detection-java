package view.screen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import util.Errors;
import view.controllers.CompareButtonAction;
import view.controllers.ConfigCheckboxAction;


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

        Label title = new Label("Informations");
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

        GridPane configGrid = new GridPane();
        configGrid.setAlignment(Pos.CENTER);
        configGrid.setHgap(10);
        configGrid.setVgap(12);

        CheckBox cb1 = new CheckBox("Nom des classes données");
        cb1.setSelected(true);
        cb1.setOnAction(new ConfigCheckboxAction(cb1));
        cb1.setPadding(new Insets(25, 25, 25, 25));

        configGrid.add(cb1,0,0,6,1);

        CheckBox cb2 = new CheckBox("Nom des méthodes données");
        cb2.setSelected(true);
        cb2.setOnAction(new ConfigCheckboxAction(cb2));
        cb2.setPadding(new Insets(25, 25, 25, 25));

        configGrid.add(cb2,7,0,6,1);


        compareBtn = new Button("Comparer");
        compareBtn.setMaxSize(500, 100);
        compareBtn.setOnAction(new CompareButtonAction(scene,text));
        compareBtn.setPadding(new Insets(25, 25, 25, 25));

        configGrid.add(compareBtn,4,3,12,1);

        pageGrid.setBottom(configGrid);

        grid.getChildren().add(pageGrid);

    }
}
