package model;

public class Tesi {

    private String titolo;
    private Stato_Tesi stato;
    private String fileTesi; //la stringa contenente il path
    private Studente autore;
    private Seduta seduta_richiesta;
    private Docente valutatore;

    public Tesi(String titolo, String fileTesi, Studente autore, Seduta seduta_richiesta, Docente valutatore) {
        this.titolo = titolo;
        this.fileTesi = fileTesi;
        this.stato = Stato_Tesi.In_attesa;
        this.autore = autore;
        this.seduta_richiesta = seduta_richiesta;
        this.valutatore = valutatore;
    }

    public String getTitolo() {
        return titolo;
    }

    public Stato_Tesi getStato() {
        return stato;
    }

    public String getFileTesi() {
        return fileTesi;
    }

    public Studente getAutore() {
        return autore;
    }

    public Seduta getSeduta_richiesta() {
        return seduta_richiesta;
    }

    public Docente getValutatore() {
        return valutatore;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setStato(Stato_Tesi stato) {
        this.stato = stato;
    }

    public void setFileTesi(String fileTesi) {
        this.fileTesi = fileTesi;
    }

    public void setAutore(Studente autore) {
        this.autore = autore;
    }


}
