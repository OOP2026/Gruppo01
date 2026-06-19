package dao;
import model.Docente;
import model.Studente;

public interface DocenteDAO {
    boolean registraDocente(String nome, String cognome, String email, String username, String password);
}
