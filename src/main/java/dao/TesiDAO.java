package dao;

import java.util.List;

public interface TesiDAO {
    List<String> getInfoTesiDocLoggato(String userDoc);
    void approvaTesi(int idTesi);
    void rifiutaTesi(int idTesi);

    String getStatoTesi(String matricola);
    void caricaTesi(String titolo,String path, String usernameDocente, String matricolaAutore,int idSeduta);


}
