package dao;

import java.util.List;

public interface TirociniDAO {
    List<String[]> visualizzaTirociniInCorso(String userDocente);
    List<String> getTirociniAperti(String userDoc);
    String getDocenteRelatore(String matricola);
    List<String> getTirociniDisponibili();
}
