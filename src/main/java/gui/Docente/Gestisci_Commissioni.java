package gui.Docente;

import controller.Controller;
import gui.Home;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;

public class Gestisci_Commissioni extends JFrame{
    Controller controller;
    private JPanel FinestraCommissioni;
    private JPanel FinestraSedute;
    private JButton ReturnButton;
    private JButton confermaButton;
    private JComboBox SeduteCombobox;
    private JTable TabellaStudenti;
    private JButton logoutButton;

    public Gestisci_Commissioni(Controller controller) {
        this.controller = controller;
        this.setContentPane(FinestraCommissioni);
        this.setTitle("Portale Login");
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


        String[] nomiColonne = {"Docente (Relatore)", "Studente", "Stato Tesi"};
        DefaultTableModel modelloTabella = new DefaultTableModel(nomiColonne, 0);
        TabellaStudenti.setModel(modelloTabella);


// 2. L'EVENTO SULLA TENDINA
// Aggiungiamo un listener che scatta ogni volta che il coordinatore cambia la selezione
        SeduteCombobox.addActionListener(e -> {

            // Prendi la data selezionata
            LocalDateTime sedutaScelta = (LocalDateTime) SeduteCombobox.getSelectedItem();

            if (sedutaScelta != null) {
                // A. Svuota la tabella da eventuali dati della seduta precedente
                modelloTabella.setRowCount(0);

                // B. Chiedi i dati puliti al Controller (Nessuna violazione architetturale!)
                List<String[]> nuoviDati = controller.getDatiTabellaSeduta(sedutaScelta);

                // C. Inserisci le righe una per una
                for (String[] riga : nuoviDati) {
                    modelloTabella.addRow(riga);
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
