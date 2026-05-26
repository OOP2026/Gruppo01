package gui.Docente;

import controller.Controller;
import gui.Home;
import gui.Registrazioni.Reg_Studente;

import javax.swing.*;

public class Int_Docente extends JFrame {
    private Controller controller;
    private JPanel FinestraDocente;
    private JTextPane interfacciaDocenteTextPane;
    private JButton aggiungiArgomButton;
    private JButton visualizzaRichiesteTButton;
    private JButton logoutButton;
    private JButton VisualizzaTesiButton;
    private JButton VisualizzaTirInCorsoButton;
    private JRadioButton internoRadioButton;
    private JRadioButton esternoRadioButton;

    public Int_Docente(Controller controller) {
        this.controller = controller;
        this.setContentPane(FinestraDocente);
        this.setTitle("Portale Login");
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        //Crea oggetto Tirocinio (diviso in Interno e Esterno, con Jbutton)
        aggiungiArgomButton.addActionListener(e ->{
           Aggiunta_Argomenti finestraAggArg = new Aggiunta_Argomenti(controller);
           finestraAggArg.setVisible(true);
           this.dispose();
        });

        logoutButton.addActionListener(e -> {
            Home NewHome = new Home();
            NewHome.setVisible(true);
            this.dispose();
        });


        // Visualizza le richieste di Tirocinio arrivate e le valuta
        visualizzaRichiesteTButton.addActionListener(e -> {
            Visualizza_R_Tir finestraRTir = new Visualizza_R_Tir(controller);
            finestraRTir.setVisible(true);
            this.dispose();
        });


        // Visualizza Tesi a lui a associate e le valuta

        // Visualizza Tirocini disponibili

        //Se coordinatore, imposta la Seduta




    }
}