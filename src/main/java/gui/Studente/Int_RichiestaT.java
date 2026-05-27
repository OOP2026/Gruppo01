package gui.Studente;
import controller.Controller;
import gui.Home;

import javax.swing.*;

public class Int_RichiestaT extends JFrame{
    private Controller controller;
    private JPanel panel1;
    private JTextPane compilaRichiestaTextPane;
    private JComboBox LTDisponibiliComboBox;
    private JButton RichiedioButton;
    private JButton logoutButton;

    public Int_RichiestaT(Controller controller) {
        this.controller = controller;
        this.setContentPane(panel1);
        this.setTitle("Interfaccia Richiesta Tirocinio");
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


        logoutButton.addActionListener(e -> {
            Home NewHome = new Home(controller);
            NewHome.setVisible(true);
            this.dispose();
        });




    }
}
