package gui;

import controller.Controller;;
import javax.swing.*;

public class Reg_Docente extends JFrame {
    private Controller controller;
    private JTextPane REGISTRAZIONEDOCENTETextPane;
    private JTextField nomeTextField;
    private JTextField CognomeTextField;
    private JTextField EmailTextField;
    private JTextField UsernameTextField;
    private JTextField PwdTextField;
    private JTextField CfrmPwdTextField;
    private JButton RegButton;

    public Reg_Docente(Controller controller) {
        this.controller = controller;
    }
}