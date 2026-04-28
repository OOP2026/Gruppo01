package model;

public class Richiesta {
    private Stato stato;
    private Studente richiedente;
    private Tirocinio tirocinio;

    public Richiesta(Studente richiedente, Tirocinio tirocinio) {
        this.stato = Stato.In_attesa;
        this.richiedente = richiedente;
        this.tirocinio = tirocinio;
    }

}