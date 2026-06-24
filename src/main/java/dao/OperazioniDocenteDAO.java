package dao;
import java.sql.SQLException;
import java.util.*;

public interface OperazioniDocenteDAO {
    List<String[]> visualizzaTirociniInCorso(String userDocente);
    void aggiungiArgomento(String arg) throws SQLException;
    void associaDocenteArgomento(String arg, String userDoc);
    List<String> getArgomentiDocente(String userDoc);
    void rimuoviArgomento(String arg, String userDoc);
}
