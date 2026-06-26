package implementazioneDao;
import java.sql.ResultSet;
import java.util.*;
import dao.StudenteDAO;
import database_connection.ConnessioneDatabase;
import controller.Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentePostgresDAO implements StudenteDAO {

    public boolean registraStudente(String nome, String cognome, String email, String matricola, String username, String password) {
        // Ipotizzo le colonne base. Dovranno corrispondere esattemente allo script SQL di Pasquale.
        String sql = "INSERT INTO STUDENTE (Nome, Cognome, Email,  Matricola, Username,Password) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nome);
            ps.setString(2, cognome);
            ps.setString(3, email);
            ps.setString(4, matricola);
            ps.setString(5, username);
            ps.setString(6, password);


            int righeInserite = ps.executeUpdate();
            return righeInserite > 0;

        } catch (SQLException e) {
            System.err.println("Errore SQL durante la registrazione dello studente: " + e.getMessage());
            return false;
        }
    }

    public List<String> loginStudente(String utente, String password) {
        String sql = "SELECT * FROM STUDENTE WHERE Username = ? AND Password = ?";
        List<String> datiStudente = new ArrayList<>();

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, utente);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Estrae i dati grezzi e li impacchetta
                    datiStudente.add(rs.getString("Nome"));
                    datiStudente.add(rs.getString("Cognome"));
                    datiStudente.add(rs.getString("Email"));
                    datiStudente.add(rs.getString("Matricola"));

                    return datiStudente;
                }
            }

        } catch (SQLException e) {
            System.err.println("Errore SQL durante il login dello studente: " + e.getMessage());
        }

        // Ritorna null se l'utente non esiste o la password è errata
        return null;
    }

    public List<String> getStudentiRichiedenti(int idTirocinio){
        List<String> lista = new ArrayList<>();
        String sql = "SELECT S.matricola, S.nome, S.cognome " +
                "FROM RICHIESTA R " +
                "JOIN STUDENTE S ON R.matricola_studente = S.matricola " +
                "WHERE R.stato = 'In_attesa' AND R.id_tirocinio = ?";

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idTirocinio);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    //recupera riga per riga la query formattandola come la richiede il Controller
                    String matricola = rs.getString("matricola");
                    String nome = rs.getString("nome");
                    String cognome = rs.getString("cognome");

                    String riga = String.format("%s: %s %s", matricola, nome, cognome);
                    lista.add(riga);
                }
            }

        } catch (SQLException e) {
            System.err.println("Errore SQL durante il recupero degli argomenti: " + e.getMessage());
        }
        return lista;
    };






}