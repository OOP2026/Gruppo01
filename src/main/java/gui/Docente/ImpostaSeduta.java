package gui.Docente;

import controller.Controller;
import gui.Home;

import javax.swing.*;

public class ImpostaSeduta extends JFrame{
    private Controller controller;
    private JPanel VisTir;
    private JButton ReturnButton;
    private JButton ValutaButton;
    private JRadioButton approvaRadioButton;
    private JRadioButton rifiutaRadioButton;
    private JTextField textField1;

    public ImpostaSeduta(Controller controller) {
        this.controller = controller;
        this.setContentPane(ImpostaSeduta);
        this.setTitle("Portale Login");
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
}
