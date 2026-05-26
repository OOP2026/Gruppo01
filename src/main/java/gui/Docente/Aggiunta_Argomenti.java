package gui.Docente;
import controller.Controller;

import javax.swing.*;


public class Aggiunta_Argomenti extends JFrame {
    private Controller controller;
    private JTextField ArgTextField;
    private JButton AggiungiButton;
    private JButton RimuoviButton;
    private JComboBox ArgComboBox;
    private JButton ReturnButton;
    private JPanel finestraArgomenti;


    public Aggiunta_Argomenti(Controller controller) {
        this.controller = controller;
        this.setContentPane(finestraArgomenti);
        this.setTitle("Portale Login");
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        ArgComboBox.removeAllItems();
        for (String arg : controller.getdocLoggato().getListaArgomenti()) {
            ArgComboBox.addItem(arg);
        }

        AggiungiButton.addActionListener(e -> {
            String Argomento = ArgTextField.getText();
            if (Argomento.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Inserire un argomento.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (controller.getdocLoggato().getListaArgomenti().contains(Argomento)) {
                JOptionPane.showMessageDialog(this, "Argomento già inserito.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }
            controller.aggiungiArgomenti(Argomento, controller.getdocLoggato());
            JOptionPane.showMessageDialog(this, "Argomento aggiunto con successo!");
        });


        RimuoviButton.addActionListener(e -> {
            String argomentoSelezionato = (String) ArgComboBox.getSelectedItem();
            if (argomentoSelezionato == null) {
                JOptionPane.showMessageDialog(this, "Errore: Nessun argomento selezionato da rimuovere.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                return; // Blocca l'esecuzione
            }

            controller.getdocLoggato().getListaArgomenti().remove(argomentoSelezionato);
            ArgComboBox.removeItem(argomentoSelezionato);
            JOptionPane.showMessageDialog(this, "Argomento rimosso con successo!");
        });

        ReturnButton.addActionListener(e -> {
            Int_Docente intDocente = new Int_Docente(controller);
            intDocente.setVisible(true);
            this.dispose();
        });
    }
}

