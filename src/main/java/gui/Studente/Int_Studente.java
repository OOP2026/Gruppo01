package gui.Studente;

import controller.Controller;
import gui.Home;

import javax.swing.*;

public class Int_Studente extends JFrame {
    Controller controller;
    private JPanel FinestraStudente;
    private JButton logoutButton;
    private JButton compilaRichiestaButton;
    private JButton caricaTesiButton;
    private JTextField statoTesiTextField;
    private JTextField StatoRichiestaTirocinio;
    private JTextField NomeCognomeS;

    public Int_Studente(Controller controller) {
        this.controller = controller;
        this.setContentPane(FinestraStudente);
        this.setTitle("Interfaccia Studente");
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


        NomeCognomeS.setText(controller.getdocLoggato().getNome() + " " + controller.getdocLoggato().getCognome());

        logoutButton.addActionListener(e -> {
            Home NewHome = new Home(controller);
            NewHome.setVisible(true);
            this.dispose();
        });




        //  - Compila Richiesta (istanzia l'oggetto)
        compilaRichiestaButton.addActionListener(e ->{
            Int_RichiestaT IntRichiesta = new Int_RichiestaT(controller);
            IntRichiesta.setVisible(true);
            this.dispose();
        });



        //  - Carica Tesi
        caricaTesiButton.addActionListener(e ->{
            Int_CaricaTesi IntCT = new Int_CaricaTesi(controller);
            IntCT.setVisible(true);
            this.dispose();
        });




       //  - Verifica Stato_Richiesta e Stato_tesi
        StatoRichiestaTirocinio.setText("Stato Richiesta Tirocinio : " + controller.getStatoStudLoggato());
        statoTesiTextField.setText("Stato Tesi : " + controller.getStatoTesi());

    }
}