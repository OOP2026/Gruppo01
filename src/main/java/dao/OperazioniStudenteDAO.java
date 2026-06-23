package dao;
import java.util.*;

public interface OperazioniStudenteDAO {
    String getStatoTesi(String matricola);
    String getStatoRichiesta(String matricola);
    List<String> getSeduteAperte();
    void caricaTesi(String titolo,String path, String usernameDocente, String matricolaAutore,int idSeduta);
    String getDocenteRelatore(String matricola);
    List<String> getTirociniDisponibili();
    void compilaRichiesta(int idTirocnio, String matricola);
}
