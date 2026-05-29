package gui.Docente;
import controller.Controller;
import gui.Home;

import javax.swing.*;

public class Visualizza_R_Tir extends JFrame {
    Controller controller;
    private JButton ReturnButton;
    private JRadioButton approvaRadioButton;
    private JRadioButton rifiutaRadioButton;
    private JPanel VisTir;
    private JComboBox TirociniComboBox;
    private JComboBox StudentiComboBox;
    private JButton ValutaButton;
    private JButton logoutButton;
    private JTextArea textArea1;
    private JLabel RICHIESTEDITIROCINIOLabel;
    ButtonGroup gruppoScelte = new ButtonGroup();

    public Visualizza_R_Tir(Controller controller) {
        this.controller = controller;
        this.setContentPane(VisTir);
        this.setTitle("VALUTAZIONE RICHIESTE DI TIROCINIO");
        this.setSize(500, 300);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        gruppoScelte.add(approvaRadioButton);
        gruppoScelte.add(rifiutaRadioButton);

       // Aggiunta tirocini associati al docente loggato
        TirociniComboBox.removeAllItems();
        for (String nomeTir : controller.getNomiTirociniApertiDelDocente()) {
                TirociniComboBox.addItem(nomeTir);
        }



        // LISTENER COMBOBOX TIROCINI
        TirociniComboBox.addActionListener(e -> {
            String tirocinioSelezionato = (String) TirociniComboBox.getSelectedItem();
            StudentiComboBox.removeAllItems(); // Pulisce gli studenti precedenti

            if (tirocinioSelezionato != null) {
                for (String matricola : controller.richiesteTir(tirocinioSelezionato)) {
                    StudentiComboBox.addItem(matricola);
                }
            }
        });

        // LISTENER BOTTONE VALUTA
        ValutaButton.addActionListener(e -> {

            // A. Estrae i valori attuali
            String tirocinioSel = (String) TirociniComboBox.getSelectedItem();
            String matricolaSel = (String) StudentiComboBox.getSelectedItem();

            // B. Controlli di sicurezza
            if (tirocinioSel == null || matricolaSel == null) {
                JOptionPane.showMessageDialog(this, "Seleziona un tirocinio e uno studente.", "Dati mancanti", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // C. Determina l'esito dai RadioButton
            if (approvaRadioButton.isSelected()) {
                controller.approvaRichiestaTirocinio(matricolaSel, tirocinioSel);
            } else if (rifiutaRadioButton.isSelected()) {
                controller.rifiutaRichiestaTirocinio(matricolaSel, tirocinioSel);
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona Approvata o Rifiutata.", "Dati mancanti", JOptionPane.WARNING_MESSAGE);
                return;
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
