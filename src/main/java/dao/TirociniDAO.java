package dao;
import java.time.LocalDate;
import java.util.List;

public interface TirociniDAO {
    List<String[]> visualizzaTirociniInCorso(String userDocente);
    List<String> getTirociniAperti(String userDoc);
    String getDocenteRelatore(String matricola);
    List<String> getTirociniDisponibili();
    boolean registraTirocinio(String argomento, String nome, int ncfu, int durata, LocalDate dataInizio, String tipo, String azienda, String refAzienda, String dipartimento, String laboratorio, String relatore);
}
