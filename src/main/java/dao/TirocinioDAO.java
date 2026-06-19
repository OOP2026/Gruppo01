package dao;
import model.Tirocinio;
import java.util.List;

public interface TirocinioDAO {
    // Recupera tutti i tirocini presenti nel DB per mostrarli nella ComboBox dello Studente
    List<Tirocinio> getAllTirocini();

    // Recupera un singolo tirocinio tramite il suo ID
    Tirocinio getTirocinioById(int id);
}