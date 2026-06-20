package model;



public class Studente extends Utente{

   private String matricola;
   private Tesi tesi;
   private Richiesta richiesta;
   private Tirocinio tirocinio;

    public Studente(String nome, String cognome, String email,String matricola , String username,String password ) {
        super(nome, cognome, email,username , password);
        this.matricola = matricola;
        this.tesi = null;
        this.richiesta = null;
        this.tirocinio = null;
    }
    public String getMatricola() {
        return matricola;
    }

    public Tesi getTesi() {
        return tesi;
    }

    public Richiesta getRichiesta() {
        return richiesta;
    }

    public void setTesi(Tesi tesi) {
        this.tesi = tesi;
    }

    public void setRichiesta(Richiesta richiesta) {
        this.richiesta = richiesta;
    }

    public void setTirocinio(Tirocinio t) {this.tirocinio = t;}
}
