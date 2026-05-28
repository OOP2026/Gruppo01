package gui.Studente;
import controller.Controller;
import gui.Home;

import javax.swing.*;

public class Int_CaricaTesi extends JFrame{
    Controller controller;
    private JPanel FinestraCaricaTesi;
    private JTextPane caricaTesiTextPane;
    private JTextField pathTextField;
    private JButton ConfermaButton;
    private JButton logoutButton;
    private JButton SfogliaButton;
    private JButton ReturnButton;
    private JTextField SLComboBox;

    public Int_CaricaTesi(Controller controller) {
        this.controller = controller;
        this.setContentPane(FinestraCaricaTesi);
        this.setTitle("Interfaccia Tesi");
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);



      ConfermaButton.addActionListener(e -> {
            String path = pathTextField.getText();

            // Controllo di sicurezza
            if (path.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Devi prima selezionare un file tramite il tasto Sfoglia.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Chiama il metodo del controller passandogli il path (e lo studente loggato se necessario)


                JOptionPane.showMessageDialog(this, "Tesi caricata con successo!");

                // Opzionale: pulizia o chiusura finestra dopo il caricamento
                pathTextField.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });






        logoutButton.addActionListener(e -> {
            Home NewHome = new Home(controller);
            NewHome.setVisible(true);
            this.dispose();
        });

        SfogliaButton.addActionListener(e -> {
            // Crea la finestra di dialogo per la scelta del file
            JFileChooser fileChooser = new JFileChooser();

            // Mostra la finestra e salva la scelta dell'utente in una variabile intera
            int esito = fileChooser.showOpenDialog(this);

            // Se l'utente ha effettivamente scelto un file e premuto "Apri"
            if (esito == JFileChooser.APPROVE_OPTION) {
                // Estrae il percorso assoluto del file come Stringa
                String percorsoScelto = fileChooser.getSelectedFile().getAbsolutePath();

                // Lo inserisce nel JTextField (che avevi reso non modificabile)
                pathTextField.setText(percorsoScelto);
            }
            // Se preme "Annulla", non succede niente e il text field rimane com'è
        });

        ReturnButton.addActionListener(e -> {
            Int_Studente interfacciaStud = new Int_Studente(controller);
            interfacciaStud.setVisible(true);
            this.dispose();
        });





    }

}

