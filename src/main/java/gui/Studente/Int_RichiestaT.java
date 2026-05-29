package gui.Studente;
import controller.Controller;
import gui.Home;

import javax.swing.*;

public class Int_RichiestaT extends JFrame{
    Controller controller;
    private JPanel panel1;
    private JLabel COMPILARICHIESTALabel;
    private JComboBox LTDisponibiliComboBox;
    private JButton ReturnButton;
    private JButton logoutButton;
    private JButton RichiediButton;

    public Int_RichiestaT(Controller controller) {
        this.controller = controller;
        this.setContentPane(panel1);
        this.setTitle("RICHIESTA DI TIROCINIO");
        this.setSize(500, 350);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);



        logoutButton.addActionListener(e -> {
            Home NewHome = new Home(controller);
            NewHome.setVisible(true);
            this.dispose();
        });

        ReturnButton.addActionListener(e -> {
            Int_Studente interfacciaStud = new Int_Studente(controller);
            interfacciaStud.setVisible(true);
            this.dispose();
        });
        LTDisponibiliComboBox.removeAllItems();
        for(String nometirocinio : controller.visualizzaTirocini()){
            LTDisponibiliComboBox.addItem(nometirocinio);
        }

        RichiediButton.addActionListener(e -> {
            // Estrai il valore direttamente quando l'utente conferma l'azione
            String tirocinioSelezionato = (String) LTDisponibiliComboBox.getSelectedItem();

            if (tirocinioSelezionato != null) {
                controller.compilaRichiesta(tirocinioSelezionato);
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona un tirocinio prima di procedere.");
            }
            Int_Studente interfacciaStud = new Int_Studente(controller);
            interfacciaStud.setVisible(true);
            this.dispose();
        });




    }
}
