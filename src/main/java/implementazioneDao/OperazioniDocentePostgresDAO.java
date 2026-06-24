package implementazioneDao;

import dao.OperazioniDocenteDAO;
import database_connection.ConnessioneDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
}
