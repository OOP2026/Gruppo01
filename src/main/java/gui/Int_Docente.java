package gui;

import controller.Controller;
import model.Docente;
import javax.swing.*;

public class Int_Docente extends JFrame {
    private Controller controller;
    private Docente docenteLoggato;

    public Int_Docente(Controller controller, Docente docente) {
        this.controller = controller;
        this.docenteLoggato = docente;
    }
}