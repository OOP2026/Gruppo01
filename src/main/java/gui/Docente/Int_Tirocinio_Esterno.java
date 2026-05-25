package gui.Docente;
import controller.Controller;
import java.util.Date;
import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Int_Tirocinio_Esterno extends JFrame {
    private Controller controller;
    private JTextPane creaTirocinioEsternoTextPane;
    private JTextField nomeTextField;
    private JTextField DurataTextField;
    private JTextField Data_I_TextField;
    private JTextField N_PostiTextField;
    private JTextField N_CFUTextField;
    private JTextField AziendaTextField;
    private JButton ConfermaButton;
    private JTextField RefAziendaTextField;


    public Int_Tirocinio_Esterno(Controller controller) {
        this.controller = controller;
        this.setContentPane(creaTirocinioEsternoTextPane);
        this.setTitle("Portale Login");
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        String nome = nomeTextField.getText();
        String durata = DurataTextField.getText();
        String data_inizio = Data_I_TextField.getText();
        String nposti =  N_PostiTextField.getText();
        String ncfu = N_CFUTextField.getText();
        String azienda = AziendaTextField.getText();
        String referente = RefAziendaTextField.getText();

        ConfermaButton.addActionListener(e -> {
            if (nome.isEmpty() || data_inizio.isEmpty() || nposti.isEmpty() || ncfu.isEmpty() || azienda.isEmpty() || referente.isEmpty() || durata.isEmpty()) {

                JOptionPane.showMessageDialog(this, "ERRORE! Tutti i campi sono obbligatori.", "Dati Mancanti", JOptionPane.WARNING_MESSAGE);
                return;}

            //conversione stringhe in interi di posti e cfu
            int n_posti = Integer.parseInt(nposti);
            int n_cfu = Integer.parseInt(ncfu);

            //conversione Stringa in formato Date
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            try {
                LocalDate dataInizio = LocalDate.parse(data_inizio, formato);
                controller.aggiungiTirocinioEsterno(nome, durata, dataInizio, n_posti, n_cfu, azienda, referente, controller.getdocLoggato());


            } catch (DateTimeParseException erroreDataInserita) {
                // 4. Se l'utente scrive lettere o sbaglia formato (es. 2026-05-25)
                JOptionPane.showMessageDialog(this, "Errore: devi inserire una data valida nel formato GG/MM/AAAA!");
            }


        });






    }

}
