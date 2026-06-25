package dao;

import java.sql.SQLException;
import java.util.List;

public interface ArgomentoDAO {
    void aggiungiArgomento(String arg) throws SQLException;
    List<String> getArgomentiDocente(String userDoc);
    void rimuoviArgomento(String arg, String userDoc);
}
