package implementazioneDao;

import dao.TirociniDAO;
import database_connection.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TirociniPostgresDAO implements TirociniDAO {
    public List<String[]> visualizzaTirociniInCorso(String userDocente) {
        String sql = "SELECT T.id, T.nome AS nome_tir, S.nome AS nome_stud, S.cognome " +
                "FROM RICHIESTA R JOIN STUDENTE S ON S.matricola = R.matricola_studente " +
                "JOIN TIROCINIO T ON R.id_tirocinio = T.id " +
                "WHERE R.stato = 'Approvata' AND T.stato = 'In_corso' AND username_relatore = ?";

        List<String[]> listaTirocini = new ArrayList<>();

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userDocente);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    String id = String.valueOf(rs.getInt("id"));
                    String nomeTirocinio = rs.getString("nome_tir");
                    String nomeStudente = rs.getString("nome_stud");
                    String cognomeStudente = rs.getString("cognome");

                    String[] riga = {id, nomeTirocinio, nomeStudente + " " + cognomeStudente};
                    listaTirocini.add(riga);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore SQL durante il recupero dei tirocini: " + e.getMessage());
        }

        return listaTirocini;
    }
    public List<String> getTirociniAperti(String userDoc){
        List<String> lista = new ArrayList<>();
        String sql = "SELECT id, nome FROM TIROCINIO WHERE stato = 'Aperto' AND username_relatore = ?";
        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userDoc);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    //recupera riga per riga la query formattandola come la richiede il Controller
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    String riga = String.format("%d: %s", id, nome);

                    lista.add(riga);
                }
            }

        } catch (SQLException e) {
            System.err.println("Errore SQL durante il recupero degli argomenti: " + e.getMessage());
        }
        return lista;
    }

    public String getDocenteRelatore(String matricola){
        String sql = "SELECT username_relatore FROM TIROCINIO T,RICHIESTA R WHERE T.id = R.id_tirocinio AND matricola_studente = ?";
        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, matricola);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("username_relatore");
                }
            }

        } catch (SQLException e) {
            System.err.println("Errore SQL durante la registrazione dello studente: " + e.getMessage());

        }
        return null;
    }

    public List<String> getTirociniDisponibili() {
        String sql = "SELECT * FROM TIROCINIO WHERE stato = 'Aperto'";
        List<String> listaTirocini = new ArrayList<>();

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    //recupera riga per riga la query formattandola come la richiese il Controller
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");

                    Date dataInizio = rs.getDate("datainizio");
                    SimpleDateFormat formattatore = new SimpleDateFormat("dd/MM/yyyy");
                    String dataInStringa = formattatore.format(dataInizio);

                    int n_cfu = rs.getInt("n_cfu");
                    String rigaFormattata = String.format("%d: %s - %s - %d", id, nome, dataInStringa, n_cfu);

                    listaTirocini.add(rigaFormattata);
                }
            }

        } catch (SQLException e) {
            System.err.println("Errore SQL durante il recupero dei tirocini: " + e.getMessage());
        }

        return listaTirocini;
    }


}
