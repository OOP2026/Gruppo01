package gui.Docente;

import controller.Controller;
import gui.Home;

import javax.swing.*;

public class Valuta_Tesi extends JFrame {
    private Controller controller;
    private JPanel FinestraValTesi;
    private JButton ReturnButton;
    private JButton ValutaButton;
    private JRadioButton approvaRadioButton;
    private JRadioButton rifiutaRadioButton;
    private JComboBox<String> TesiComboBox;
    private JButton logoutButton;


    public Valuta_Tesi(Controller controller) {
        this.controller = controller;
        this.setContentPane(FinestraValTesi);
        this.setTitle("Valuta Richieste Tirocinio");
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);


        for(String idTesi: controller.getIdTesi()) {
            TesiComboBox.addItem(idTesi);
        }

        ValutaButton.addActionListener(e -> {


            String TesiSelezionata = (String) TesiComboBox.getSelectedItem();

            if (TesiSelezionata == null) {
                JOptionPane.showMessageDialog(this, "Seleziona una tesi.", "Dati mancanti", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Determina l'esito dai RadioButton
            if (approvaRadioButton.isSelected()) {
                controller.approvaTesi(TesiSelezionata);
            } else if (rifiutaRadioButton.isSelected()) {
                controller.rifiutaTesi(TesiSelezionata);
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona Approvata o Rifiutata.", "Dati mancanti", JOptionPane.WARNING_MESSAGE);
                return;
            }
        });


        logoutButton.addActionListener(e -> {
            Home NewHome = new Home(controller);
            NewHome.setVisible(true);
            this.dispose();
        });

        ReturnButton.addActionListener(e -> {
            Int_Docente interfacciaDoc = new Int_Docente(controller);
            interfacciaDoc.setVisible(true);
            this.dispose();
        });







    }



}
