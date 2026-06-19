package dao;

public interface StudenteDAO {
    boolean registraStudente(String Nome, String Cognome, String Email, String Password, String Username, String Matricola);
}