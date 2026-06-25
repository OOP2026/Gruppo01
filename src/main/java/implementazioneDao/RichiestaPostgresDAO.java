package implementazioneDao;

import dao.RichiestaDAO;
import database_connection.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RichiestaPostgresDAO implements RichiestaDAO {

    public void approvaRichiestaTirocinio(String matricola){
        String sql = "UPDATE RICHIESTA SET stato = 'approvata' WHERE matricola_studente = ? AND stato = in_attesa";
        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, matricola);
            ps.executeUpdate();
        }catch (SQLException e) {
            System.err.println("Errore SQL durante l'aggionamento del dataabase: " + e.getMessage());
        }
    }

    public void rifiutaRichiestaTirocinio(String matricola){
        String sql = "UPDATE RICHIESTA SET stato = 'rifiutata' WHERE matricola_studente = ? AND stato = in_attesa";
        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, matricola);
            ps.executeUpdate();
        }catch (SQLException e) {
            System.err.println("Errore SQL durante l'aggionamento del dataabase: " + e.getMessage());
        }
    }

    public String getStatoRichiesta(String matricola) {
        String sql = "SELECT stato FROM richiesta WHERE matricola_studente = ?";

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

    public void compilaRichiesta(int idTirocnio, String matricola){
        String sql = "INSERT INTO RICHIESTA (matricola_studente, id_tirocinio) values(?,?)";
        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, matricola);
            ps.setInt(2, idTirocnio);


        } catch (SQLException e) {
            System.err.println("Errore SQL durante la creazione della richiesta: " + e.getMessage());
        }
    }

    public void eliminaRichiesta(String matricola) {
        String sql = "DELETE FROM RICHIESTA WHERE matricola_studente = ?";
        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, matricola);
        } catch (SQLException e) {
            System.err.println("Errore SQL durante l'eliminazione della richiesta: " + e.getMessage());
        }
    }

}
