package implementazioneDao;

import dao.OperazioniStudenteDAO;
import database_connection.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OperazioniStudentePostgresDAO implements OperazioniStudenteDAO {
    public String getStatoTesi(String matricola) {
        String sql = "SELECT stato FROM TESI WHERE matricola_autore = ?";

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, matricola);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("stato");
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore SQL durante il recupero dello stato tesi: " + e.getMessage());
        }
        return null;
    }

    public String getStatoRichiesta(String matricola) {
        String sql = "SELECT stato FROM STUDENTE WHERE matricola_studente = ?";

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, matricola);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("stato");
                }
            }

        } catch (SQLException e) {
            System.err.println("Errore SQL durante il recupero dello stato della richiesta: " + e.getMessage());
        }

        return null;
    }

    public List<String> getSeduteAperte() {
        String sql = "SELECT * FROM Seduta WHERE stato = 'Aperta'";
        List<String> listaSedute = new ArrayList<>();

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    //recupera riga per riga la query formattandola come la richiese il Controller
                    String id = rs.getString("id");
                    String dataOra = rs.getString("data_ora");
                    String sede = rs.getString("sede");
                    String rigaFormattata = String.format("%s: %s - %s", id, dataOra, sede);

                    listaSedute.add(rigaFormattata);
                }
            }

        } catch (SQLException e) {
            System.err.println("Errore SQL durante il recupero delle sedute: " + e.getMessage());
        }

        return listaSedute;
    }
}
