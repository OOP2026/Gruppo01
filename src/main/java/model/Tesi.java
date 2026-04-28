package model;
import java.util.*;

public class Tesi {

    private String titolo;
    private boolean stato;
    private String fileTesi; //la stringa contenente il path
    private Studente autore;
    private Seduta seduta_richiesta;
    private Docente valutatore;

    public Tesi(String titolo, String fileTesi, Studente autore, Seduta seduta_richiesta, Docente valutatore) {
        this.titolo = titolo;
        this.fileTesi = fileTesi;
        this.stato = false;
        this.autore = autore;
        this.seduta_richiesta = seduta_richiesta;
        this.valutatore = valutatore;
    }

}
