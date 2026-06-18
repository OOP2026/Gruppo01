package model;
import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;

public class Seduta {
    private String id;
    private LocalDateTime data_ora;
    private String sede;
    private boolean stato; //true se è aperta, false se è chiusa (dopo che il coordinatore ha impostato la commissione)
    private List<Tesi> prenotazioni;
    private Docente coordinatore;
    private HashSet<Docente> commissione;

    public Seduta(LocalDateTime data_ora, String sede,Docente coordinatore) {
        this.id = UUID.randomUUID().toString();
        this.data_ora = data_ora;
        this.sede = sede;
        this.stato = true;
        this.prenotazioni = new ArrayList<Tesi>();
        this.coordinatore = coordinatore;
        this.commissione  = new HashSet<Docente>();
    }

    public LocalDateTime getData_ora() {
        return data_ora;
    }

    public String getId() {return id;}

    public String getSede() {
        return sede;
    }

    public void setDocente(Docente coordinatore) {
        this.coordinatore = coordinatore;
    }


    public void AggiungiPrenotazione(Tesi t){
        this.prenotazioni.add(t);
    }

    public boolean getStato() {
        return this.stato;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }
}
