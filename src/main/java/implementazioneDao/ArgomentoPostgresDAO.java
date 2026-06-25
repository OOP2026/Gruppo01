package implementazioneDao;

import dao.ArgomentoDAO;
import database_connection.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArgomentoPostgresDAO implements ArgomentoDAO {

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
