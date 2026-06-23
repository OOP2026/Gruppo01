package implementazioneDao;

import dao.OperazioniStudenteDAO;
import database_connection.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public String getStatoRichista(String matricola) {
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

}
