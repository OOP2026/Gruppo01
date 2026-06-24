package implementazioneDao;

import dao.OperazioniDocenteDAO;
import database_connection.ConnessioneDatabase;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class OperazioniDocentePostgresDAO implements OperazioniDocenteDAO {
    public List<String[]> visualizzaTirociniInCorso(String userDocente) {
        String sql = "SELECT T.id, T.nome AS nome_tir, S.nome AS nome_stud, S.cognome " +
                "FROM RICHESTA R JOIN STUDENTE S ON S.marticola = R.matricola_studente " +
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

    public void aggiungiArgomento(String argomento) throws SQLException {
        String sql = "INSERT INTO ARGOMENTO (Nome) SELECT ? WHERE NOT EXISTS (SELECT 1 FROM ARGOMENTO WHERE Nome = ?)";

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, argomento);
            ps.setString(2, argomento);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Errore SQL durante l'aggiunta dell'argomento: " + e.getMessage());
        }

    };

    public void associaDocenteArgomento(String userDocente, String nomeArgomento)  {
        String sql = "INSERT INTO DOCENTE_ARGOMENTO (User_docente, Nome_argomento) VALUES (?, ?)";

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userDocente);
            ps.setString(2, nomeArgomento);
            ps.executeUpdate();
        }catch (SQLException e) {
            System.err.println("Errore SQL durante il recupero degli argomenti: " + e.getMessage());
        }
    }

    public List<String> getArgomentiDocente(String userDoc) {
        String sql = "SELECT nome_argomento FROM DOCENTE_ARGOMENTO WHERE username_docente = ?";

        List<String> listaArg = new ArrayList<>();

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userDoc);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    //recupera riga per riga la query formattandola come la richiese il Controller
                    String nome = rs.getString("nome_argomento");

                    listaArg.add(nome);
                }
            }

        } catch (SQLException e) {
            System.err.println("Errore SQL durante il recupero degli argomenti: " + e.getMessage());
        }
        return listaArg;
    }


    public void rimuoviArgomento(String arg, String userDoc) {
        String sql = "DELETE FROM DOCENTE_ARGOMENTO WHERE username_docente = ? AND nome_argomento = ?";

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userDoc);
            ps.setString(2, arg);
            ps.executeUpdate();
        }catch (SQLException e) {
            System.err.println("Errore SQL durante l'eliminazione dell'argomento: " + e.getMessage());
        }

    };
    public void creaSeduta(LocalDateTime data, String sede){
        String sql = "INSERT INTO seduta (data_ora,sede) VALUES(?,?)";

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(data));
            ps.setString(2, sede);
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
                String riga = String.format("%d: %s, %s", id, dataOra, sede);

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

    public List<String> getInfoTesiDocLoggato(String userDoc) {
        List<String> lista = new ArrayList<>();
        String sql = "Select T.id AS id, T.titolo AS titolo, T.matricola_autore AS autore FROM DOCENTE D JOIN TESI T ON D.username = username_valutatore WHERE username = ?";

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

    public List<String> getTirociniAperti(String userDoc){
        List<String> lista = new ArrayList<>();
        String sql = "SELECT id, nome FROM TIROCINIO WHERE stato = aperto AND username_relatore = ?";
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

    public List<String> getStudentiRichiedenti(int idTirocinio){
        List<String> lista = new ArrayList<>();
        String sql = "SELECT S.matricola_studente INTO matricola, nome,cognome FROM RICHIESTA JOIN STUDENTE ON R.matricola_studente = S.matricola WHERE stato = 'in_attesa' AND id_tirocinio = ?";
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
}

