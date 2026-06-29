package gui;

import controller.Controller;
import database_connection.ConnessioneDatabase;

public class Main {
    public static void main(String[] args) {

        // 1. Inizializzazione del Control
        Controller controllerPrincipale = new Controller();
        // 3. Inizializzazione della GUI

        Home interfacciaHome = new Home(controllerPrincipale);
        interfacciaHome.setLocationRelativeTo(null);
        interfacciaHome.setVisible(true);

        ConnessioneDatabase.getInstance();
    }
}