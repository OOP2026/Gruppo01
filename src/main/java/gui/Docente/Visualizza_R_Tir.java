package gui.Docente;
import controller.Controller;

import javax.swing.*;

public class Visualizza_R_Tir extends JFrame {
    private Controller controller;
    private JButton ReturnButton;
    private JRadioButton approvaRadioButton;
    private JRadioButton rifiutaRadioButton;

    public Visualizza_R_Tir(Controller controller) {
        this.controller = controller;
        this.setContentPane(FinestraDocente);
        this.setTitle("Portale Login");
        this.setSize(1024, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


}
