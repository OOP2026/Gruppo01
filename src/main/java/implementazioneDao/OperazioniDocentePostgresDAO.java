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
}
