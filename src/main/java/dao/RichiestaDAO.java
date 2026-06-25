package dao;

public interface RichiestaDAO {
    void approvaRichiestaTirocinio(String matricola);
    void rifiutaRichiestaTirocinio(String matricola);

    String getStatoRichiesta(String matricola);

    void compilaRichiesta(int idTirocnio, String matricola);
}
