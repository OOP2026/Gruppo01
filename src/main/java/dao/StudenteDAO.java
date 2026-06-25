package dao;
import java.util.*;

public interface StudenteDAO {
    boolean registraStudente(String Nome, String Cognome, String Email,String Matricola , String Username,String Password );
    List<String> loginStudente(String user, String password);
    List<String> getStudentiRichiedenti(int idTirocinio);
}