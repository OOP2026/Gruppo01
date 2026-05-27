import controller.Controller;
import gui.Home;

public class Main {
    public static void main(String[] args) {

        // 1. Inizializzazione del Control
        Controller controllerPrincipale = new Controller();

        // 2. INIEZIONE DATI DI TEST
        // (Basta commentare questa riga in futuro per avere il sistema vuoto e pulito)
        controllerPrincipale.caricaDatiDiTest();

        // 3. Inizializzazione della GUI
        Home interfacciaHome = new Home(controllerPrincipale);
        interfacciaHome.setVisible(true);
    }
}