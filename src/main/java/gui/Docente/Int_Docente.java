package gui.Docente;

import controller.Controller;
import gui.Home;

import javax.swing.*;
import java.util.List;

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
    private JTable tabellaTirocini;
    private JButton terminaVisualizzazioneButton;


    public Int_Docente(Controller controller) {
        this.controller = controller;
        this.setContentPane(FinestraDocente);
        this.setTitle("Portale Login");
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.Coordinatore.setVisible(false);
        this.Commissioni.setVisible(false);
        this.tabellaTirocini.setVisible(false);
        this.terminaVisualizzazioneButton.setVisible(false);

        if(controller.getdocLoggato().getisCoordinatore()) {
        this.Coordinatore.setVisible(true);
        this.Commissioni.setVisible(true);
        }

        NomeCognomeDoc.setText(controller.getdocLoggato().getNome() + " " + controller.getdocLoggato().getCognome());

        //Se coordinatore, imposta la Seduta
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



        //Apre l'interfaccia per aggiungere l'argomento
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


        // Visualizza Tesi a lui associate e le valuta
        VisualizzaTesiButton.addActionListener(e-> {
            Valuta_Tesi valutaTesi = new Valuta_Tesi(controller);
            valutaTesi.setVisible(true);
            this.dispose();
        });



        // Visualizza Tirocini in corso
        VisualizzaTirociniButton.addActionListener(e -> {
            try {
               // Recupera dal controller
                List<String[]> datiTabella = controller.visualizzaTirocinioStudenti();

                // 2. Se la lista è vuota, avvisa l'utente e interrompi
                if (datiTabella == null || datiTabella.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Nessun tirocinio in corso associato a questo docente.",
                            "Informazione",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                // 3. Crea e mostra la tabella passando la lista di array di stringhe
                tabellaTirocini.setVisible(true);
                terminaVisualizzazioneButton.setVisible(true);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Errore durante il caricamento dei tirocini: " + ex.getMessage(),
                        "Errore",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        terminaVisualizzazioneButton.addActionListener(e -> {
            tabellaTirocini.setVisible(false);
        });




    }

}