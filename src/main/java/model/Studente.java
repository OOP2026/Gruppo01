package model;



public class Studente extends Utente{

   private String matricola;
   private Tesi tesi;
    private Richiesta richiesta;

    public Studente(String nome, String cognome, String email, String password, String username, String matricola) {
        super(nome, cognome, email, password, username);
        this.matricola = matricola;
        this.tesi = null;
        this.richiesta = null;
    }
    
}
