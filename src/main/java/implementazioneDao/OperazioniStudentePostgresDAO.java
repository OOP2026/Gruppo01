package implementazioneDao;

import dao.OperazioniStudenteDAO;
import database_connection.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

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

    public void caricaTesi(String titolo, String path, String usernameDocente, String matricolaAutore, int idSeduta) {
        String sql = "INSERT INTO TESI (titolo,file,username_valutatore,matricola_autore,id_seduta) values(?,?,?,?,?)";
        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, titolo);
            ps.setString(2, path);
            ps.setString(3, usernameDocente);
            ps.setString(4, matricolaAutore);
            ps.setInt(5, idSeduta);


        } catch (SQLException e) {
            System.err.println("Errore SQL durante la registrazione dello studente: " + e.getMessage());
        }
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

    public void compilaRichiesta(int idTirocnio, String matricola){
        String sql = "INSERT INTO RICHIESTA (matricola_studente, id_tirocinio) values(?,?)";
        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, matricola);
            ps.setInt(2, idTirocnio);


        } catch (SQLException e) {
            System.err.println("Errore SQL durante la creazione della richiesta: " + e.getMessage());
        }
    };

}

