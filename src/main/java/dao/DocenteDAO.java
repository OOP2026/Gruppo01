package dao;
import model.Docente;
import model.Studente;

import java.util.List;

public interface DocenteDAO {
    boolean registraDocente(String nome, String cognome, String email, String username, String password);
    List<String> loginDocente(String user, String password);
    boolean checkIsCoordinatore(String username);
    void associaDocenteArgomento(String arg, String userDoc);
}
