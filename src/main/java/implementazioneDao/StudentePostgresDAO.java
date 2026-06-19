package implementazioneDao;

import dao.StudenteDAO;
import database_connection.ConnessioneDatabase;
import model.Studente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentePostgresDAO implements StudenteDAO {

    @Override
    public boolean registraStudente(Studente studente) {
        // Ipotizzo le colonne base. Dovranno corrispondere esattemente allo script SQL di Pasquale.
        String sql = "INSERT INTO STUDENTE (Nome, Cognome, Email, Password, Username, Matricola) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, studente.getNome());
            ps.setString(2, studente.getCognome());
            ps.setString(3, studente.getEmail());
            ps.setString(4, studente.getPassword());
            ps.setString(5, studente.getUsername());
            ps.setString(6, studente.getMatricola());



            int righeInserite = ps.executeUpdate();
            return righeInserite > 0;

        } catch (SQLException e) {
            System.err.println("Errore SQL durante la registrazione dello studente: " + e.getMessage());
            return false;
        }
    }
}