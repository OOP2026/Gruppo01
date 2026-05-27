package gui.Docente;

import controller.Controller;

import javax.swing.*;

public class CreaSeduta extends JFrame{
    private Controller controller;
    private JButton ReturnButton;
    private JButton ValutaButton;
    private JRadioButton approvaRadioButton;
    private JRadioButton rifiutaRadioButton;
    private JTextField textField1;
    private JPanel FinestraSedute;

    public CreaSeduta(Controller controller) {
        this.controller = controller;
        this.setContentPane(FinestraSedute);
        this.setTitle("Portale Login");
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
}
