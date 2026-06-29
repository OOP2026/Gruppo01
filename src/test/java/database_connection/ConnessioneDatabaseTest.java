package database_connection;

import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Connection;

public class ConnessioneDatabaseTest {

    @Test
    public void testGetInstanceConnessioneValida() {
        // 1. Chiamo il metodo
        Connection conn = ConnessioneDatabase.getInstance();

        // 2. Controllo che NON sia null
        assertNotNull("La connessione non dovrebbe essere nulla", conn);

        try {
            // 3. Controllo che sia effettivamente aperta
            assertFalse("La connessione dovrebbe essere aperta", conn.isClosed());
        } catch (Exception e) {
            fail("Errore durante il controllo dello stato della connessione: " + e.getMessage());
        }
    }
}