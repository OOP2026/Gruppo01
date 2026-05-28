package gui.Docente;

import controller.Controller;
import gui.Home;

import javax.swing.*;

public class Int_Docente extends JFrame {
    Controller controller;
    private JPanel FinestraDocente;
    private JTextPane interfacciaDocenteTextPane;
    private JButton aggiungiArgomButton;
    private JButton visualizzaRichiesteTButton;
    private JButton logoutButton;
    private JButton VisualizzaTesiButton;
    private JButton CreaSedutaButton;
    private JPanel Coordinatore;
    private JButton GestisciCommissioniButton;
    private JPanel Commissioni;
    private JTextField NomeCognomeDoc;
    private JButton VisualizzaTirociniButton;


    public Int_Docente(Controller controller) {
        this.controller = controller;
        this.setContentPane(FinestraDocente);
        this.setTitle("Portale Login");
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.Coordinatore.setVisible(false);
        this.Commissioni.setVisible(false);

        if(controller.getdocLoggato().getisCoordinatore()) {
        this.Coordinatore.setVisible(true);
        this.Commissioni.setVisible(true);
        }

        NomeCognomeDoc.setText(controller.getdocLoggato().getNome() + " " + controller.getdocLoggato().getCognome());

        CreaSedutaButton.addActionListener(e -> {
            CreaSeduta impostaSeduta  = new CreaSeduta(controller);
            impostaSeduta.setVisible(true);
            this.dispose();
        });

        GestisciCommissioniButton.addActionListener(e -> {
            Gestisci_Commissioni gestisciCommissioni  = new Gestisci_Commissioni(controller);
            gestisciCommissioni.setVisible(true);
            this.dispose();
        });



        //Crea oggetto Tirocinio (diviso in Interno e Esterno, con Jbutton)
        aggiungiArgomButton.addActionListener(e ->{
           Aggiunta_Argomenti finestraAggArg = new Aggiunta_Argomenti(controller);
           finestraAggArg.setVisible(true);
           this.dispose();
        });

        logoutButton.addActionListener(e -> {
            Home NewHome = new Home(controller);
            NewHome.setVisible(true);
            this.dispose();
        });


        // Visualizza le richieste di Tirocinio arrivate e le valuta
        visualizzaRichiesteTButton.addActionListener(e -> {
            Visualizza_R_Tir finestraRTir = new Visualizza_R_Tir(controller);
            finestraRTir.setVisible(true);
            this.dispose();
        });

        VisualizzaTesiButton.addActionListener(e-> {
            Valuta_Tesi valutaTesi = new Valuta_Tesi(controller);
            valutaTesi.setVisible(true);
            this.dispose();
        });
        // Visualizza Tesi a lui a associate e le valuta

        // Visualizza Tirocini disponibili

        //Se coordinatore, imposta la Seduta




    }
}