package implementazioneDao;

import dao.AdminDAO;
import database_connection.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminPostgresDAO implements AdminDAO {
   public List<String> loginAdmin(String user, String password){
        String sql = "SELECT username, password FROM admin WHERE username = ? AND password = ?";
        List<String> datiAdmin = new ArrayList<>();

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Estrae i dati grezzi e li impacchetta
                    datiAdmin.add(rs.getString("username"));
                    datiAdmin.add(rs.getString("password"));

                    return datiAdmin;
                }
            }

        } catch (SQLException e) {
            System.err.println("Errore SQL durante il login dello studente: " + e.getMessage());
        }

        // Ritorna null se l'utente non esiste o la password è errata
        return null;
    }
}
