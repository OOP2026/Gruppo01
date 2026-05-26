package gui.Registrazioni;

import controller.Controller;
import gui.Home;
import javax.swing.*;

public class Reg_Docente extends JFrame {
    private Controller controller;
    private JTextPane REGISTRAZIONEDOCENTETextPane;
    private JTextField nomeTextField;
    private JTextField CognomeTextField;
    private JTextField EmailTextField;
    private JTextField UsernameTextField;
    private JTextField PwdTextField;
    private JTextField CfrmPwdTextField;
    private JButton RegButton;
    private JPanel RegDFinestra;

    public Reg_Docente(Controller controller) {
        this.controller = controller;
        this.setContentPane(RegDFinestra);
        this.setTitle("Portale Registrazione Docente");
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        RegButton.addActionListener(e -> {
            //salviamo in variabili tutte le informazioni inserite dall'utente
            String nome = nomeTextField.getText();
            String cognome = CognomeTextField.getText();
            String email = EmailTextField.getText().trim();
            String username = UsernameTextField.getText();
            String pwd = PwdTextField.getText();
            //si utilizzerà un JTextField in più per confermare la Password scelta
            String confPwd = CfrmPwdTextField.getText();
            //si ipotizza di avere una lista di mail di docenti coordinatori, impostati successivamente all'implementazione del database

            //se qualche campo è stato lasciato vuoto, viene lanciato il messaggio di Errore
            if (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || username.isEmpty() || pwd.isEmpty() || confPwd.isEmpty()) {

                JOptionPane.showMessageDialog(this, "ERRORE! Tutti i campi sono obbligatori.", "Dati Mancanti", JOptionPane.WARNING_MESSAGE);
                return;
            }
            //verifica che Password e Conferma_Password siano identiche
            if (!pwd.equals(confPwd)) {
                JOptionPane.showMessageDialog(this, "Le password inserite non coincidono.", "Errore Password", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                controller.registraDocente(nome, cognome, email, username, pwd);
                JOptionPane.showMessageDialog(this, "Registrazione completata con successo! Ora puoi accedere.");
                this.dispose();
                Home finestraLogin = new Home();
                finestraLogin.setVisible(true);
                // Cattura gli errori lanciati dal Controller (es. "Username già in uso")
            }catch (IllegalArgumentException regError) {
                JOptionPane.showMessageDialog(this, regError.getMessage(), "Errore di Registrazione", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}