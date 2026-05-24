package gui.Docente;

import controller.Controller;

import javax.swing.*;

public class Int_Coordinatore {
    private Controller controller;
    private JPanel FinestraDocente;
    private JTextPane interfacciaCoordinatoreTextPane;
    private JButton creaTirocinioButton;
    private JButton visualizzaRichiesteTButton;
    private JButton VisualizzaTesiButton;
    private JButton VisualizzaTirInCorsoButton;
    private JButton logoutButton;

    public Int_Coordinatore(Controller controller) {
        this.controller = controller;

    }
}
