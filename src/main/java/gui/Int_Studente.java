package gui;

import controller.Controller;
import model.Studente;
import javax.swing.*;

public class Int_Studente extends JFrame {
    private Controller controller;
    private Studente studenteLoggato;

    public Int_Studente(Controller controller, Studente studente) {
        this.controller = controller;
        this.studenteLoggato = studente;
    }
}