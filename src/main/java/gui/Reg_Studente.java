package gui;

import controller.Controller;

import javax.swing.*;

;

public class Reg_Studente extends JFrame {
    private Controller controller;
    private JTextPane REGISTRAZIONESTUDENTETextPane;
    private JTextField nomeTextField;
    private JTextField cognomeTextField;
    private JTextField EmailTextField;
    private JTextField MatricolaTextField;
    private JTextField UsernameTextField;
    private JTextField PwdTextField;
    private JTextField CfrmPwdTextField;
    private JButton RegButton;
    private JPanel RegSFinestra;

    public Reg_Studente(Controller controller) {
        this.controller = controller;
        this.setContentPane(RegSFinestra);
        this.setTitle("Portale Registrazione Studente");
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        RegButton.addActionListener(e -> {
            //salviamo in variabili tutte le informazioni inserite dall'utente
            String nome = nomeTextField.getText();
            String cognome = cognomeTextField.getText();
            String email = EmailTextField.getText().trim();
            String matricola = MatricolaTextField.getText();
            String username = UsernameTextField.getText();
            String pwd = PwdTextField.getText();
            //si utilizzerà un JTextField in più per confermare la Password scelta
            String confPwd = CfrmPwdTextField.getText();

            //se qualche campo è stato lasciato vuoto, viene lanciato il messaggio di Errore
            if (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() ||
                    matricola.isEmpty() || username.isEmpty() || pwd.isEmpty() || confPwd.isEmpty()) {

                JOptionPane.showMessageDialog(this, "ERRORE! Tutti i campi sono obbligatori.", "Dati Mancanti", JOptionPane.WARNING_MESSAGE);
                return;
            }
            //verifica che Password e Conferma_Password siano identiche
            if (!pwd.equals(confPwd)) {
                JOptionPane.showMessageDialog(this, "Le password inserite non coincidono.", "Errore Password", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                controller.registraStudente(nome, cognome, email, matricola, username, pwd);
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