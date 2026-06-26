package implementazioneDao;

import dao.SeduteDAO;
import database_connection.ConnessioneDatabase;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SedutePostgresDAO implements SeduteDAO {

    public void creaSeduta(LocalDateTime data, String sede, String username){
        String sql = "INSERT INTO seduta (data_ora,sede,username_coordinatore) VALUES(?,?,?)";

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(data));
            ps.setString(2, sede);
            ps.setString(3, username);

            ps.executeUpdate();
        }catch (SQLException e) {
            System.err.println("Errore SQL durante l'eliminazione dell'argomento: " + e.getMessage());
        }

    }

    public List<String> getSeduteAperte(){
        List<String> result = new ArrayList<>();
        String sql = "SELECT * FROM SEDUTA WHERE STATO=true";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String dataOra = rs.getTimestamp("data_ora").toLocalDateTime().format(formatter);
                String sede = rs.getString("sede");

                // Formattazione richiesta: "ID: DataOra, Sede"
                String riga = String.format("%d:        %s,       %s", id, dataOra, sede);

                result.add(riga);
            }
        } catch (SQLException e) {
            System.err.println("Errore SQL nel recupero delle sedute: " + e.getMessage());
        }

        return result;

    }

    public List<String[]> getInfoSeduta(int idSeduta){
        String sql = "SELECT D.username AS userDoc, S.matricola as matricolaS, T.stato AS statoTesi " +
                "FROM DOCENTE D JOIN TESI T ON D.username = T.username_valutatore " +
                "JOIN STUDENTE S ON S.matricola = T.matricola_autore " +
                "WHERE T.stato = 'Approvata' AND T.id_seduta = ?";
        //userDocente, MatricolaStudente, Stato-tesi
        List<String[]> listaInfoSeduta = new ArrayList<>();

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idSeduta);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    String userDocente = String.valueOf(rs.getString("userDoc"));
                    String matricolaStudente = rs.getString("matricolaS");
                    String statoTesi = rs.getString("statoTesi");

                    String[] riga = {userDocente, matricolaStudente, statoTesi };
                    listaInfoSeduta.add(riga);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore SQL durante il recupero dei tirocini: " + e.getMessage());
        }

        return listaInfoSeduta;
    }

    public void chiudiSeduta(int idSeduta) {
        String sql = "UPDATE SEDUTA SET STATO = FALSE WHERE id = ?";

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSeduta);
            ps.executeUpdate();
        }catch (SQLException e) {
            System.err.println("Errore SQL durante la chiusura della seduta: " + e.getMessage());
        }
    }
    public boolean checkseduta(LocalDateTime data, String sede) {
        String sql = "SELECT count(*) as conteggio FROM SEDUTA WHERE data_ora = ? AND sede = ?";

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(data));
            ps.setString(2, sede);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("conteggio");
                    // Ritorna false se esiste già una seduta (count > 0), altrimenti true
                    return count == 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore SQL durante il controllo della seduta: " + e.getMessage());
        }

        // Ritorna false in caso di errore per evitare di considerare la sede come "disponibile"
        return false;
    }

}
