package gui.Docente;

import controller.Controller;
import gui.Studente.Int_Studente;

import javax.swing.*;

public class Int_Docente extends JFrame {
    private Controller controller;
    private JPanel FinestraDocente;
    private JTextPane interfacciaDocenteTextPane;
    private JButton creaTirocinioButton;
    private JButton visualizzaRichiesteTButton;
    private JButton logoutButton;
    private JButton VisualizzaTesiButton;
    private JButton VisualizzaTirInCorsoButton;
    private JRadioButton internoRadioButton;
    private JRadioButton esternoRadioButton;

    public Int_Docente(Controller controller) {
        this.controller = controller;

        //Crea oggetto Tirocinio (diviso in Interno e Esterno, con Jbutton)
        creaTirocinioButton.addActionListener(e ->{
            if (internoRadioButton.isSelected()) {
                //crea l'interfaccia per la creazione del Tirocinio interno.
                Int_Tirocinio_Interno interfaccia_tir = new Int_Tirocinio_Interno(this.controller);
                interfaccia_tir.setVisible(true);
                this.dispose();
            } else if (esternoRadioButton.isSelected()) {
                //crea l'interfaccia per la creazione del Tirocinio esterno
                Int_Tirocinio_Esterno interfaccia_tir = new Int_Tirocinio_Esterno(this.controller);
                interfaccia_tir.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona un tipo di tirocinio.", "Attenzione", JOptionPane.WARNING_MESSAGE);				}
        });


        // Visualizza le richieste di Tirocinio arrivate e le valuta
        visualizzaRichiesteTButton.addActionListener(e -> {

        });


        // Visualizza Tesi a lui a associate e le valuta

        // Visualizza Tirocini disponibili

        //Se coordinatore, imposta la Seduta




    }
}