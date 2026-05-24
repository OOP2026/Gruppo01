package gui;

import controller.Controller;;
import javax.swing.*;

public class Reg_Studente extends JFrame {
    private Controller controller;
    private JTextPane REGISTRAZIONESTUDENTETextPane;
    private JTextField nomeTextField;
    private JTextField congnomeTextField;
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
            String nome = nomeTextField.getText();
            String cognome = congnomeTextField.getText();
            String email = EmailTextField.getText().trim();
            String matricola = MatricolaTextField.getText();
            String username = UsernameTextField.getText();
            String pwd = PwdTextField.getText();
            String confPwd = CfrmPwdTextField.getText();

            if (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() ||
                    matricola.isEmpty() || username.isEmpty() || pwd.isEmpty() || confPwd.isEmpty()) {

                JOptionPane.showMessageDialog(this, "ERRORE! Tutti i campi sono obbligatori.", "Dati Mancanti", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!pwd.equals(confPwd)) {
                JOptionPane.showMessageDialog(this, "Le password inserite non coincidono.", "Errore Password", JOptionPane.WARNING_MESSAGE);
                return;
            }


        });

    }



}