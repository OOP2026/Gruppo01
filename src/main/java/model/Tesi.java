package model;
import java.util.UUID;

public class Tesi {

    private String titolo;
    private int idTesi;
    private Stato_Tesi stato;
    private String fileTesi; //la stringa contenente il path
    private Studente autore;
    private Seduta seduta_richiesta;
    private Docente valutatore;

    // Costruttore A(per Gui)
    public Tesi(String titolo, String fileTesi, Studente autore, Seduta seduta_richiesta, Docente valutatore) {
        this.titolo = titolo;
        this.idTesi = 0;
        this.fileTesi = fileTesi;
        this.stato = Stato_Tesi.IN_ATTESA;
        this.autore = autore;
        this.seduta_richiesta = seduta_richiesta;
        this.valutatore = valutatore;
    }

    public Tesi(String titolo, String fileTesi, Studente autore, Seduta seduta_richiesta, Docente valutatore, int id) {
        this.idTesi = id;
        this.titolo = titolo;
        this.fileTesi = fileTesi;
        this.stato = Stato_Tesi.IN_ATTESA;
        this.autore = autore;
        this.seduta_richiesta = seduta_richiesta;
        this.valutatore = valutatore;
    }

    public int getId() {
        return idTesi;
    }


    public Stato_Tesi getStato() {
        return stato;
    }

    public void setStato(Stato_Tesi stato) {
        this.stato = stato;
    }

}
