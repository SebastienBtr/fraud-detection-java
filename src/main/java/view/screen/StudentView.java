package view.screen;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import launcher.ConfigFile;
import launcher.Launcher;
import view.controllers.BackAction;

public class StudentView extends StackPane
{
    private Scene scene;
    private GridPane table;

    public StudentView(Scene scene)
    {
        this.scene = scene;
    }

    public StudentView()
    {

        this.getStylesheets().add(getClass().getResource("/css/studentView.css").toExternalForm());
        VBox grid = new VBox();
        this.getChildren().add(grid);


        Button back = new Button("retour");
        back.setOnAction(new BackAction(scene));
        grid.getChildren().add(back);

        TabPane tabPane = new TabPane();
        for(String tabName: ConfigFile.indicators.keySet()){
            if(ConfigFile.indicators.get(tabName)){

               Tab tab = new Tab(tabName);
               tab.setClosable(false);
               tab.setContent(studentComparativeTable(tabName));
                tabPane.getTabs().add(tab);


            }
        }

        grid.getChildren().add(tabPane);



    }

    /**
     * Make the table that compares students with each other
     * @param indicator name of the indicator takent into account TODO use it when hetting the score
     * @return pane table
     */
    private ScrollPane studentComparativeTable(String indicator )
    {

        table = new GridPane();
        table.setHgap(0);
        table.setVgap(0);


        ScrollPane scrollPane = new ScrollPane();

        scrollPane.setContent(table);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);



        table.add(makeHeaderFrame("Élèves" ,true),0,0);

        for (int i = 0; i < Launcher.getStudents().size(); i++)
        {
            table.add(makeHeaderFrame(Launcher.getStudents().get(i).getName(),true),0,i+1);
            table.add(makeHeaderFrame(Launcher.getStudents().get(i).getName(),false),i+1,0);
            for (int j = 0; j < Launcher.getStudents().size(); j++)
            {
                if(i ==j){
                    table.add(makeHeaderFrame(Launcher.getStudents().get(i).getScores().get(Launcher.getStudents().get(j).getName()).intValue()+"",false),j+1,i+1);

                }
                else{
                    table.add(makeScoreFrame(Launcher.getStudents().get(i).getScores().get(Launcher.getStudents().get(j).getName())),j+1,i+1);

                }
            }


        }
        return scrollPane;
    }

    /**
     * Style the frame to have the right coloration
     * @param score
     * @return
     */
    private Button makeScoreFrame(Double score){
            Button frame = new Button(score.intValue()+"");
            frame.getStyleClass().add("cell-score");
            frame.getStyleClass().add("cell");
            frame.setStyle("-fx-background-color: "+colorScore(score)+";");



            return frame;
    }

    /**
     * Choose color for score
     * @param score
     * @return
     */
    private String colorScore(Double score){

        if(score < 30){
            return "forestgreen";
        }
        else if(score < 50){
            return "#FFC811";
        }
        if(score < 70){
            return "#FF9111";
        }

            return "#FF1711";


    }


    /**
     * Style header
     * @param text
     * @param isLeft is the left label column
     * @return
     */
    private Label makeHeaderFrame(String text,boolean isLeft){
        Label frame = new Label(text);
        frame.getStyleClass().add("cell");
        if(isLeft){
            frame.getStyleClass().add("left-header");
        }
        Tooltip tooltip = new Tooltip();
        tooltip.setText(text        );
        frame.setTooltip(tooltip);

        return frame;
    }




}
