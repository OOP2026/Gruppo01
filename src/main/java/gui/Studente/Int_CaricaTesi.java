package gui.Studente;
import controller.Controller;
import gui.Home;

import javax.swing.*;
import java.time.LocalDateTime;

public class Int_CaricaTesi extends JFrame{
    Controller controller;
    private JPanel FinestraCaricaTesi;
    private JTextPane caricaTesiTextPane;
    private JTextField pathTextField;
    private JButton ConfermaButton;
    private JButton logoutButton;
    private JButton SfogliaButton;
    private JButton ReturnButton;
    private JComboBox SLComboBox;
    private JTextField TitoloTesiTextField;

    public Int_CaricaTesi(Controller controller) {
        this.controller = controller;
        this.setContentPane(FinestraCaricaTesi);
        this.setTitle("Interfaccia Tesi");
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);



        ConfermaButton.addActionListener(e -> {
            String path = pathTextField.getText();
            String titolo = TitoloTesiTextField.getText();
            LocalDateTime dataora = (LocalDateTime) SLComboBox.getSelectedItem();
            // Controllo di sicurezza
            if (path.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Devi prima selezionare un file tramite il tasto Sfoglia.", "Percorso vuoto", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (titolo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Devi prima inserire un titolo.", "Titolo Vuoto", JOptionPane.ERROR_MESSAGE);
            }
            if (dataora == null) {
                JOptionPane.showMessageDialog(this, "Devi prima selezionare una seduta.", "DataOra Vuota", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                // Chiama il metodo del controller passandogli path, titolo e seduta
                controller.caricaTesi(dataora, titolo, path, controller.getstudLoggato().getRichiesta().getTirocinio().getDocente());
                JOptionPane.showMessageDialog(this, "Tesi caricata con successo!");

                pathTextField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Tesi non caricata", JOptionPane.ERROR_MESSAGE);
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

