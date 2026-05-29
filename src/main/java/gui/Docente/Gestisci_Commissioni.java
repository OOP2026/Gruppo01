package gui.Docente;

import controller.Controller;
import gui.Home;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDateTime;
import java.util.List;

public class Gestisci_Commissioni extends JFrame{
    Controller controller;
    private JPanel FinestraCommissioni;
    private JPanel FinestraSedute;
    private JButton ReturnButton;
    private JButton confermaButton;
    private JComboBox<String> SeduteCombobox;
    private JTable TabellaStudenti;
    private JButton logoutButton;
    private JLabel GESTISCICOMMISSIONELabel;

    public Gestisci_Commissioni(Controller controller) {
        this.controller = controller;
        this.setContentPane(FinestraCommissioni);
        this.setTitle("GESTIONE COMMISSIONE");
        this.setSize(500, 600);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


        String[] nomiColonne = {"Docente (Relatore)", "Studente", "Stato Tesi"};
        DefaultTableModel modelloTabella = new DefaultTableModel(nomiColonne, 0);
        TabellaStudenti.setModel(modelloTabella);

        //Riempiamo la ComboBox delle sedute disponibili
        List<String> seduteDisponibili = controller.creaListaSeduteAperte();
        if (seduteDisponibili != null && !seduteDisponibili.isEmpty()) {
            for (String seduta : seduteDisponibili) {
                SeduteCombobox.addItem(seduta);
            }
        }

        // Il listener scatta ogni volta che il coordinatore cambia la selezione
        SeduteCombobox.addActionListener(e -> {

            LocalDateTime sedutaScelta = (LocalDateTime) SeduteCombobox.getSelectedItem();

            if (sedutaScelta != null) {
                modelloTabella.setRowCount(0);

                List<String[]> nuoviDati = controller.getDatiTabellaSeduta(sedutaScelta);

                for (String[] riga : nuoviDati) {
                    modelloTabella.addRow(riga);
                }
            }
        });

        confermaButton.addActionListener(e -> {
            String sedutaSelezionata = (String) SeduteCombobox.getSelectedItem();

            if (sedutaSelezionata != null) {
                try {
                    // Passi la stringa al Controller (che farà split e ricerca)
                    controller.confermaSeduta(sedutaSelezionata);

                    JOptionPane.showMessageDialog(this,
                            "Seduta confermata e chiusa con successo.",
                            "Seduta chiusa",
                            JOptionPane.INFORMATION_MESSAGE);

                } catch (IllegalArgumentException ex) {
                    // Intercetta l'errore "Seduta inesistente" o "Formato stringa non valido"
                    JOptionPane.showMessageDialog(this,
                            "Errore del sistema: " + ex.getMessage(),
                            "Errore",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        ReturnButton.addActionListener(e -> {
            Int_Docente interfacciaDoc = new Int_Docente(controller);
            interfacciaDoc.setVisible(true);
            this.dispose();
        });
        logoutButton.addActionListener(e -> {
            Home NewHome = new Home(controller);
            NewHome.setVisible(true);
            this.dispose();
        });


    }
}
