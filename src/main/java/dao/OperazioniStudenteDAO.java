package dao;
import java.util.*;

public interface OperazioniStudenteDAO {
    String getStatoTesi(String matricola);
    String getStatoRichiesta(String matricola);
    List<String> getSeduteAperte();
}
