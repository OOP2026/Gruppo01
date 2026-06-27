package implementazioneDao;

import dao.TirociniDAO;
import database_connection.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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



    public boolean registraTirocinio(String argomento, String nome, int ncfu, int durata, LocalDate dataInizio, String tipo,
                              String azienda, String refAzienda, String dipartimento, String laboratorio, String relatore){

        String sql = "INSERT INTO TIROCINIO (nome, durata, datainizio, n_cfu, tipo, dipartimento, laboratorio, " +
                "azienda, referente_aziendale, nome_argomento, username_relatore) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nome);
            ps.setInt(2, durata);

            // Conversione della data da LocalDate a Date
            ps.setDate(3, java.sql.Date.valueOf(dataInizio));
            ps.setInt(4, ncfu);
            ps.setString(5, tipo);
            // I parametri specifici. Se contengono 'null', su Postgres andrà NULL
            ps.setString(6, dipartimento);
            ps.setString(7, laboratorio);
            ps.setString(8, azienda);
            ps.setString(9, refAzienda);
            ps.setString(10, argomento);
            ps.setString(11, relatore);

            int righeInserite = ps.executeUpdate();
            return righeInserite > 0;

        } catch (SQLException e) {
            System.err.println("Errore SQL durante l'inserimento del tirocinio: " + e.getMessage());
            return false;
        }

    }


}
