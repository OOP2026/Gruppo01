package database_connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import util.ConfigLoader;

public class ConnessioneDatabase {

    // Parametri di connessione (modifica la password se necessario)
    private static final String URL = ConfigLoader.get("db.url");
    private static final String USER = ConfigLoader.get("db.user");
    private static final String PASSWORD = ConfigLoader.get("db.password");

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