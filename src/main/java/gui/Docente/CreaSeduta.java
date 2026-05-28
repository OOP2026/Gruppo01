package gui.Docente;

import controller.Controller;

import javax.swing.*;

public class CreaSeduta extends JFrame{
    private Controller controller;
    private JButton ReturnButton;
    private JPanel FinestraSedute;
    private JButton confermaButton;
    private JTextField SedeTextField;
    private JTextField GiornoTextPanel;
    private JTextField MeseTextField;
    private JTextField AnnoTextField;
    private JTextField OraTextField;

    public CreaSeduta(Controller controller) {
        this.controller = controller;
        this.setContentPane(FinestraSedute);
        this.setTitle("Portale Login");
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
}
