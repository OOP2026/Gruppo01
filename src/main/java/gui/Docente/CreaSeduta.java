package gui.Docente;

import controller.Controller;
import gui.Home;
import gui.Studente.Int_Studente;

import javax.swing.*;
import java.time.LocalDateTime;

public class CreaSeduta extends JFrame{
    Controller controller;
    private JButton ReturnButton;
    private JPanel FinestraSedute;
    private JButton confermaButton;
    private JTextField SedeTextField;
    private JTextField GiornoTextPanel;
    private JTextField MeseTextField;
    private JTextField AnnoTextField;
    private JTextField OraTextField;
    private JButton logoutButton;

    public CreaSeduta(Controller controller) {
        this.controller = controller;
        this.setContentPane(FinestraSedute);
        this.setTitle("Portale Login");
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


        confermaButton.addActionListener(e -> {
            String Giorno = GiornoTextPanel.getText();
            String Mese = MeseTextField.getText();
            String Anno = AnnoTextField.getText();
            String Ora = OraTextField.getText();
            String Sede = SedeTextField.getText();

            // Controllo campi vuoti
            if( Giorno.isEmpty() || Mese.isEmpty() || Anno.isEmpty() || Ora.isEmpty() || Sede.isEmpty() ) {
                JOptionPane.showMessageDialog(this, "Riempire correttamente tutti i campi", "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                //Crea la data e inserisce la seduta
                LocalDateTime dataOraSeduta = controller.assemblaDataOra(Giorno, Mese, Anno, Ora);

                controller.inserisciSeduta(dataOraSeduta, Sede);

                // Conferma l'avvenuta creazione
                JOptionPane.showMessageDialog(this, "Seduta creata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);

                // SVUOTA I CAMPI GRAFICI
                GiornoTextPanel.setText("");
                MeseTextField.setText("");
                AnnoTextField.setText("");
                OraTextField.setText("");
                SedeTextField.setText("");

            } catch (IllegalArgumentException ex) {
                // Cattura eventuali inserimenti errati
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore nei dati", JOptionPane.ERROR_MESSAGE);
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
