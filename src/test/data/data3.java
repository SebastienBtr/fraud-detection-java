package chronometre.controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import chronometre.abstraction.Chrono;

import java.awt.*;

public class EcouteurStop implements ActionListener{
    private Chrono c;

    public EcouteurStop(JButton start, JButton stop, JButton reset, Chrono c){
        this.start=start;
        this.stop=stop;
        this.reset=reset;
        this.c=c;
    }



    public void actionPerformed(ActionEvent e) {
        this.start.setEnabled(true);
        this.stop.setEnabled(false);
        this.reset.setEnabled(true);
        this.c.stop();

    }

}
