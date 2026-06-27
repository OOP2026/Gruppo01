package implementazioneDao;

import dao.TesiDAO;
import database_connection.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TesiPostgresDao implements TesiDAO {

    public List<String> getInfoTesiDocLoggato(String userDoc) {
        List<String> lista = new ArrayList<>();
        String sql = "Select T.id AS id, T.titolo AS titolo, T.matricola_autore AS autore FROM DOCENTE D JOIN TESI T ON D.username = username_valutatore WHERE username = ? and stato = 'In_attesa'";

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, userDoc);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        //recupera riga per riga la query formattandola come la richiese il Controller
                        int id = rs.getInt("id");
                        String titolo = rs.getString("titolo");
                        String autore = rs.getString("autore");
                        String riga = String.format("%d: %s - %s", id, autore, titolo);

                    lista.add(riga);
                }
            }

        } catch (SQLException e) {
            System.err.println("Errore SQL durante il recupero degli argomenti: " + e.getMessage());
        }
        return lista;
    }

    public void approvaTesi(int idTesi) {
        String sql = "UPDATE TESI SET STATO = 'Approvata' WHERE id = ?";

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTesi);
            ps.executeUpdate();
        }catch (SQLException e) {
            System.err.println("Errore SQL durante l'aggionamento del dataabase: " + e.getMessage());
        }
    }

    public void rifiutaTesi(int idTesi) {
        String sql = "UPDATE TESI SET STATO = 'Rifiutata' WHERE id = ?";

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTesi);
            ps.executeUpdate();
        }catch (SQLException e) {
            System.err.println("Errore SQL durante l'aggionamento del dataabase: " + e.getMessage());
        }
    }


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

    public void caricaTesi(String titolo, String path,String matricolaAutore, String usernameDocente , int idSeduta) {
        String sql = "INSERT INTO TESI (titolo,file,username_valutatore,matricola_autore,id_seduta) values(?,?,?,?,?)";
        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, titolo);
            ps.setString(2, path);
            ps.setString(3, usernameDocente);
            ps.setString(4, matricolaAutore);
            ps.setInt(5, idSeduta);
            ps.executeUpdate();


        } catch (SQLException e) {
            System.err.println("Errore SQL durante la registrazione dello studente: " + e.getMessage());
        }
    }

    public void eliminaTesi(String matricola) {
        String sql = "DELETE FROM TESI WHERE matricola_autore = ?";
        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, matricola);
        } catch (SQLException e) {
            System.err.println("Errore SQL durante l'eliminazione della tesi: " + e.getMessage());
        }
    }
}
