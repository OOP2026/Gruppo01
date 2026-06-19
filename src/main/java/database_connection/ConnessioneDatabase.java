package database_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDatabase {

    // Parametri di connessione (modifica la password se necessario)
    private static final String URL = "jdbc:postgresql://localhost:5432/gestione_tesi";
    private static final String USER = "postgres"; // O "fredd" se hai usato il tuo utente di sistema
    private static final String PASSWORD = "admin"; // Inserisci la password che hai impostato

    // L'unica istanza statica della connessione
    private static Connection connection = null;

    // Costruttore privato: impedisce di creare oggetti ConnessioneDB con "new"
    private ConnessioneDatabase() {}

    // Metodo pubblico per ottenere la connessione
    public static Connection getInstance() {
        try {
            if (connection == null || connection.isClosed()) {
                // Carica il driver (opzionale nelle versioni recenti di JDBC, ma garantisce compatibilità)
                Class.forName("org.postgresql.Driver");

                // Stabilisce la connessione fisica
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connessione al database stabilita con successo.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("Errore fatale di connessione al database: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
}