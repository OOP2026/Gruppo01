package implementazioneDao;

import dao.DocenteDAO;
import database_connection.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocentePostgresDAO implements DocenteDAO{

    public boolean registraDocente(String nome, String cognome, String email, String username, String password) {
        // Ipotizzo le colonne base. Dovranno corrispondere esattemente allo script SQL di Pasquale.
        String sql = "INSERT INTO DOCENTE (Nome, Cognome, Email, Username,Password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nome);
            ps.setString(2, cognome);
            ps.setString(3, email);
            ps.setString(4, username);
            ps.setString(5, password);



            int righeInserite = ps.executeUpdate();
            return righeInserite > 0;

        } catch (SQLException e) {
            System.err.println("Errore SQL durante la registrazione dello studente: " + e.getMessage());
            return false;
        }
    }
    public List<String> loginDocente(String utente, String password) {
        String sql = "SELECT * FROM DOCENTE WHERE Username = ? AND Password = ?";
        List<String> datiDocente = new ArrayList<>();

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, utente);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Estrae i dati grezzi e li impacchetta
                    datiDocente.add(rs.getString("Nome"));
                    datiDocente.add(rs.getString("Cognome"));
                    datiDocente.add(rs.getString("Email"));

                    return datiDocente;
                }
            }

        } catch (SQLException e) {
            System.err.println("Errore SQL durante il login dello studente: " + e.getMessage());
        }

        // Ritorna null se l'utente non esiste o la password è errata
        return null;
    }
    public boolean checkIsCoordinatore(String username){
        String sql = "SELECT iscoordinatore FROM DOCENTE WHERE Username = ?";
        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("iscoordinatore");
                }
            }

        } catch (SQLException e) {
            System.err.println("Errore SQL durante il login dello studente: " + e.getMessage());
        }
        return false;
    }

    public void associaDocenteArgomento(String userDocente, String nomeArgomento)  {
        String sql = "INSERT INTO docente_argomento (username_docente, nome_argomento) VALUES (?, ?)";

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userDocente);
            ps.setString(2, nomeArgomento);
            ps.executeUpdate();
        }catch (SQLException e) {
            System.err.println("Errore SQL durante il recupero degli argomenti: " + e.getMessage());
        }
    }
    public List<String> getDocNotCoord(){
        List<String> lista = new ArrayList<>();
        String sql = "SELECT username FROM DOCENTE WHERE iscoordinatore = false";


        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    //recupera riga per riga la query formattandola come la richiede il Controller
                    String username = rs.getString("username");
                    lista.add(username);
                }
            }

        } catch (SQLException e) {
            System.err.println("Errore SQL durante il recupero degli argomenti: " + e.getMessage());
        }
        return lista;
    };

    public void impostaCoordinatore(String usernameDoc){
        String sql = "UPDATE docente SET iscoordinatore = true WHERE username = ?";

        try (Connection conn = ConnessioneDatabase.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usernameDoc);
            ps.executeUpdate();
        }catch (SQLException e) {
            System.err.println("Errore SQL durante l'impostazione del coordinatore: " + e.getMessage());
        }

    }


}
