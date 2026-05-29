package gui.Docente;

import controller.Controller;
import gui.Home;

import javax.swing.*;

public class Valuta_Tesi extends JFrame {
    Controller controller;
    private JPanel FinestraValTesi;
    private JButton ReturnButton;
    private JButton ValutaButton;
    private JRadioButton approvaRadioButton;
    private JRadioButton rifiutaRadioButton;
    private JComboBox<String> TesiComboBox;
    private JButton logoutButton;
    private JLabel VALUTATESILabel;
    ButtonGroup gruppoScelte = new ButtonGroup();


    public Valuta_Tesi(Controller controller) {
        this.controller = controller;
        this.setContentPane(FinestraValTesi);
        this.setTitle("VALUTAZIONE TESI");
        this.setSize(500, 300);
        this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        gruppoScelte.add(approvaRadioButton);
        gruppoScelte.add(rifiutaRadioButton);



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
