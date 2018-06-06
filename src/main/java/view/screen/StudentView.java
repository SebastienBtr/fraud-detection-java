package view.screen;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import launcher.Launcher;

public class StudentView extends StackPane
{

   private GridPane table;

    public StudentView(Scene scene)
    {

    }

    public StudentView()
    {

        this.getStylesheets().add(getClass().getResource("/css/studentView.css").toExternalForm());
        GridPane grid = new GridPane();
        this.getChildren().add(grid);


        table = new GridPane();
        table.setHgap(0);
        table.setVgap(0);
//        ColumnConstraints col1 = new ColumnConstraints();
//        col1.setPercentWidth(25);
//        ColumnConstraints col2 = new ColumnConstraints();
//        col2.setPercentWidth(75);
//        table.getColumnConstraints().addAll(col1,col2);

        ScrollPane scrollPane = new ScrollPane();

        scrollPane.setContent(table);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        grid.add(scrollPane,2,0);

        grid.add(new Label("zone d'info détaillée"),1,0);


        table.add(makeHeaderFrame("Élèves" ),1,0);
        //TODO pour chaques types d'indicateur
        table.add(makeHeaderFrame("indic1"),2,0);
        table.add(makeHeaderFrame("indic 2"),3,0);


        for (int i = 0; i < Launcher.getStudents().size(); i++)
        {
             table.add(new Label(Launcher.getStudents().get(i).getName()),1,i+1);
            //TODO pour chaques types d'indicateur
             table.add(makeScoreFrame((int)(Math.random() * 50 + 1)),2,i+1);
             table.add(makeScoreFrame((int)(Math.random() * 50 + 1)),3,i+1);

        }
    }

    private Button makeScoreFrame(int score){
            Button frame = new Button(score+"");
            frame.getStyleClass().add("cell-score");
            frame.getStyleClass().add("cell");
            //TODO couleur en fonction du score
            if(score > 30){
                frame.setStyle("-fx-background-color: "+colorScore(score)+";");
            }


            return frame;
    }

    private String colorScore(int score){

        if(score < 25){
            return "forestgreen";
        }
        else if(score < 50){
            return "#FFC811";
        }
        if(score < 75){
            return "#FF9111";
        }

            return "#FF1711";


    }


    private Label makeHeaderFrame(String text){
        Label frame = new Label(text);
        frame.getStyleClass().add("header");
        frame.getStyleClass().add("cell");

        return frame;
    }




}
