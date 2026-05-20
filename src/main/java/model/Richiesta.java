package model;

public class Richiesta {
    private Stato_richiesta stato;
    private Studente richiedente;
    private Tirocinio tirocinio;

    public Richiesta(Studente richiedente, Tirocinio tirocinio) {
        this.stato = Stato_richiesta.In_attesa;
        this.richiedente = richiedente;
        this.tirocinio = tirocinio;
    }

    public Stato_richiesta getStato(){
        return stato;
    }

    public Studente getRichiedente(){
        return richiedente;
    }

    public Tirocinio gettirocinio() {
        return tirocinio;
    }

    public void setStato(Stato_richiesta stato) {
        this.stato = stato;
    }
}







