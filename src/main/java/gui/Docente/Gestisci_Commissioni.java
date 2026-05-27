package gui.Docente;

import controller.Controller;

import javax.swing.*;

public class Gestisci_Commissioni extends JFrame{
    private Controller controller;
    private JPanel FinestraCommissioni;

    public Gestisci_Commissioni(Controller controller) {
        this.controller = controller;
        this.setContentPane(FinestraCommissioni);
        this.setTitle("Portale Login");
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
}
