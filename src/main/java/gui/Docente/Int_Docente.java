package gui.Docente;

import controller.Controller;
import gui.Home;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
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

        public class PopupTirociniInCorso extends JDialog {

            public PopupTirocini(JFrame parent, List<Tirocinio> listaTirocini) {
                super(parent, "Tirocini in Corso e Studenti Associati", true); // modale = true

                // Configurazione base
                this.setSize(600, 400);
                this.setLocationRelativeTo(parent);
                this.setLayout(new BorderLayout());

                // 1. Creazione del modello della tabella (Celle NON modificabili)
                String[] colonne = {"Matricola", "Studente", "Titolo Tirocinio", "Stato"};
                DefaultTableModel tableModel = new DefaultTableModel(colonne, 0) {
                };

                // 2. Popolamento dei dati
                if (listaTirocini != null) {
                    for (Tirocinio t : listaTirocini) {
                        // Adatta i metodi getter in base a come li hai scritti nel Model
                        String matricola = t.getStudente().getMatricola();
                        String nomeCompleto = t.getStudente().getNome() + " " + t.getStudente().getCognome();
                        String titolo = t.getTitolo();
                        String stato = t.getStato().toString(); // Assumendo che Stato sia una Enum

                        tableModel.addRow(new Object[]{matricola, nomeCompleto, titolo, stato});
                    }
                }

                // 3. Creazione della JTable e aggiunta dello ScrollPane
                JTable table = new JTable(tableModel);
                table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                table.getTableHeader().setReorderingAllowed(false); // Impedisce di spostare le colonne

                JScrollPane scrollPane = new JScrollPane(table);
                this.add(scrollPane, BorderLayout.CENTER);

                // 4. Pannello inferiore con bottone di chiusura
                JPanel panelBottom = new JPanel();
                JButton btnChiudi = new JButton("Chiudi");
                btnChiudi.addActionListener(e -> this.dispose()); // Chiude e distrugge il popup

                panelBottom.add(btnChiudi);
                this.add(panelBottom, BorderLayout.SOUTH);
            }
        }


    }

}