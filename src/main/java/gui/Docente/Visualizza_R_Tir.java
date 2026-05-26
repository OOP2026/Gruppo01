package gui.Docente;
import controller.Controller;
import gui.Registrazioni.Reg_Studente;

import javax.swing.*;

public class Visualizza_R_Tir extends JFrame {
    private Controller controller;
    private JButton ReturnButton;
    private JRadioButton approvaRadioButton;
    private JRadioButton rifiutaRadioButton;
    private JPanel VisTir;
    private JComboBox TirociniComboBox;
    private JComboBox StudentiComboBox;
    private JButton ValutaButton;
    ButtonGroup gruppoScelte = new ButtonGroup();

    public Visualizza_R_Tir(Controller controller) {
        this.controller = controller;
        this.setContentPane(VisTir);
        this.setTitle("Portale Login");
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        gruppoScelte.add(approvaRadioButton);
        gruppoScelte.add(rifiutaRadioButton);

        //impostiamo la lista di tutti i tirocini asosciati al docente loggato
        TirociniComboBox.removeAllItems();
        for (String nomeTir : controller.getNomiTirociniDelDocente()) {
            TirociniComboBox.addItem(nomeTir);
        }

        //Listener della combo Box dei Tirocini (se niente è selezionato, lancia il messaggio)
        TirociniComboBox.addActionListener(e -> {
            String TirocinioSelezionato = (String) TirociniComboBox.getSelectedItem();
            if (TirocinioSelezionato == null) {
                JOptionPane.showMessageDialog(this, "Errore: Nessun tirocinio selezionato.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }

            //Listener della ComboBox delle Matricole associate a quel tirocinio (si riempie una volta selezionato il tirocinio in questione)
            StudentiComboBox.removeAllItems();
            for (String matricola : controller.RichiesteTir(TirocinioSelezionato)) {
                StudentiComboBox.addItem(matricola);
            };
            StudentiComboBox.addActionListener(e2-> {
                String RichiestaSelezionata = (String) StudentiComboBox.getSelectedItem();
                if (RichiestaSelezionata == null) {
                    JOptionPane.showMessageDialog(this, "Errore: Nessun tirocinio selezionato.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                ValutaButton.addActionListener(e3 -> {
                    if (approvaRadioButton.isSelected()) {
                        //se viene premuto il JRadioButton Approva, richiama il metodo che cambia lo stato della richiesta.
                        Reg_Studente regStud = new Reg_Studente(this.controller);
                    } else if (rifiutaRadioButton.isSelected()) {
                        //se viene premuto il JRadioButton Docente, crea l'interfaccia registrazione Studente, e gli passa il controller
                        Reg_Studente regStud = new Reg_Studente(this.controller);
                    } else {
                        JOptionPane.showMessageDialog(this, "Seleziona un ruolo (Studente o Docente) prima di provare a Registrarti.", "Attenzione", JOptionPane.WARNING_MESSAGE);				}
                });


            });


        });
    }
}
