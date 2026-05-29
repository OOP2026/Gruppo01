package model;
import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;

public class Seduta {
    private LocalDateTime data_ora;
    private String sede;
    private boolean stato; //true se è aperta, false se è chiusa (dopo che il coordinatore ha impostato la commissione)
    private List<Tesi> prenotazioni;
    private Docente coordinatore;
    private HashSet<Docente> commissione;

    public Seduta(LocalDateTime data_ora, String sede,Docente coordinatore) {
        this.data_ora = data_ora;
        this.sede = sede;
        this.stato = true;
        this.prenotazioni = new ArrayList<Tesi>();
        this.coordinatore = coordinatore;
        this.commissione  = new HashSet<Docente>();
    }

    public void aggiungiInCommissione(Docente d) {
        commissione.add(d);
    }

    public void rimuoviDaCommissione(Docente d) {
        commissione.remove(d);
    }

    public LocalDateTime getData_ora() {
        return data_ora;
    }

    public String getSede() {
        return sede;
    }


    public List<Tesi> getPrenotazioni() {
        return prenotazioni;
    }

    public Docente getCoordinatore() {
        return coordinatore;
    }

    public void setSede(String sede) {
        this.sede = sede;
    }

    public void setDocente(Docente coordinatore) {
        this.coordinatore = coordinatore;
    }


    public void AggiungiPrenotazione(Tesi t){
        this.prenotazioni.add(t);
    }
    public void rimuoviPrenotazione(Tesi t){
        this.prenotazioni.remove(t);
    }


    public List<Studente> getStudentiPrenotati() {
        ArrayList<Studente> listaPrenotati = new ArrayList<>();
        for (Tesi t : prenotazioni) {
            if (t.getStato() == Stato_Tesi.Approvata) {
                listaPrenotati.add(t.getAutore());
            }
        }
        return listaPrenotati;
    }


    public boolean getStato() {
        return this.stato;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }
}
