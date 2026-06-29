package dao;

import java.util.List;

public interface DocenteDAO {
    boolean registraDocente(String nome, String cognome, String email, String username, String password);
    List<String> loginDocente(String user, String password);
    boolean checkIsCoordinatore(String username);
    void associaDocenteArgomento(String arg, String userDoc);
    List<String> getDocNotCoord();
    void impostaCoordinatore(String usernameDoc);
}
