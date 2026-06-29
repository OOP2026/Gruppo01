package database_connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConfigLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDatabase {

    // Logger configurato per questa classe
    private static final Logger logger = LoggerFactory.getLogger(ConnessioneDatabase.class);

    private static final String URL = ConfigLoader.get("db.url");
    private static final String USER = ConfigLoader.get("db.user");
    private static final String PASSWORD = ConfigLoader.get("db.password");

    private static Connection connection = null;

    private ConnessioneDatabase() {}

    public static Connection getInstance() {
        try {
            if (connection == null || connection.isClosed()) {
                // In Java moderno il driver PostgreSQL viene caricato automaticamente,
                // ma lasciarlo non fa danni.
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                logger.info("Connessione al database stabilita con successo.");
            }
        } catch (SQLException e) {
            // Loggare l'eccezione correttamente (incluso lo stack trace)
            logger.error("Errore fatale di connessione al database", e);

            // Rilanciare come RuntimeException per fermare l'esecuzione in modo pulito
            throw new RuntimeException("Impossibile connettersi al database", e);
        }
        return connection;
    }
}