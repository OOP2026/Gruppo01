package model;
import java.util.*;
import java.time.LocalDateTime;

public class Seduta {
    private LocalDateTime data_ora;
    private String sede;
    private int numero_posti;
    private List<Tesi> prenotazioni;
    private Docente coordinatore;

    public Seduta(LocalDateTime data_ora, String sede,int numero_posti, Docente coordinatore) {
        this.data_ora = data_ora;
        this.sede = sede;
        this.numero_posti = numero_posti;
        this.prenotazioni = new ArrayList<Tesi>();
        this.coordinatore = coordinatore;
    }

    public LocalDateTime getData_ora() {
        return data_ora;
    }

    public String getSede() {
        return sede;
    }

    public int getNumero_posti() {
        return numero_posti;
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

    public void setNumero_posti(int numero_posti) {
        this.numero_posti = numero_posti;
    }

    public void AggiungiPrenotazione(Tesi t){
        this.prenotazioni.add(t);
    }
    public void rimuoviPrenotazione(Tesi t){
        this.prenotazioni.remove(t);
    }

    public void decrementaPosti() {
        numero_posti = numero_posti--;
    }

    public List<Studente> studentiPrenotati() {
        ArrayList<Studente> listaPrenotati = new ArrayList<>();
        for (Tesi t : prenotazioni) {
            if (t.getStato() == Stato_Tesi.Approvata) {
                listaPrenotati.add(t.getAutore());
            }
        }
        return listaPrenotati;
    }
}
