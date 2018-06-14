package view.controllers;

        import javafx.event.ActionEvent;
        import javafx.event.EventHandler;
        import javafx.scene.Scene;
        import javafx.scene.control.CheckBox;
        import javafx.scene.control.Label;
        import launcher.ConfigFile;
        import launcher.Launcher;
        import util.Errors;
        import view.screen.ConfigView;


public class ConfigCheckboxAction implements EventHandler<ActionEvent>
{

    private CheckBox cb;

    public ConfigCheckboxAction(CheckBox object)
    {
        this.cb = object;
    }

    public void handle(ActionEvent event)
    {
        try{
            if (this.cb.getText().equals("Nom des classes données")) {
                ConfigFile.classNameAreGiven = this.cb.isSelected();
            }
            else if (this.cb.getText().equals("Nom des méthodes données")) {
                ConfigFile.methodNamesAreGiven = this.cb.isSelected();
            }

        } catch(Exception e){
            e.printStackTrace();
        }

    }

}
