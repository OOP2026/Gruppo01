package dao;

import java.time.LocalDateTime;
import java.util.List;

public interface SeduteDAO{
    void creaSeduta(LocalDateTime data, String sede);
    List<String> getSeduteAperte();
    List<String[]> getInfoSeduta(int idSeduta);
    void chiudiSeduta(int idSeduta);
}
