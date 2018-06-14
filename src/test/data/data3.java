package chronometre.presentation;

import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import chronometre.abstraction.Chrono;
import chronometre.abstraction.Chrono;

public class clearControl implements ActionListener{
    private LabelChrono labelChrono;
    private Chrono chrono;
    private JButton start;
    private JButton stop;
    private JButton reset;
    private JButton split;
    private JButton clear;
    private JTextArea texte;

    clearControl(LabelChrono labelChrono, Chrono chrono, JButton start, JButton stop, JButton reset, JButton split, JButton clear, JTextArea texte){
        this.labelChrono=labelChrono;
        this.chrono=chrono;
        this.start=start;
        this.stop=stop;
        this.reset=reset;
        this.split=split;
        this.clear=clear;
        this.texte=texte;
    }

    public void actionPerformed(ActionEvent e) {
        JButton clear=(JButton)e.getSource();
        texte.setText("");
        clear.setEnabled(false);

    }
}
