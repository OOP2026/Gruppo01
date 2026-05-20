package model;

import java.time.LocalDateTime;
import java.util.*;


public class Docente extends Utente {
    private boolean is_coordinatore;
    private List<Tirocinio> listaTirocini;
    private List<Seduta> sedutePubblicate;
    private List<Tesi> tesiValutate;

    public Docente(String nome, String cognome, String email, String password, String username) {
        super(nome, cognome, email, password, username);
        this.is_coordinatore = false;
        this.listaTirocini = new ArrayList<Tirocinio>();
        this.sedutePubblicate = new ArrayList<Seduta>();
        this.tesiValutate = new ArrayList<Tesi>();
    }

    public List<Tirocinio> getListaTirocini(){
        return listaTirocini;
    }

    public List<Seduta> getSedutePubblicate() {
        return sedutePubblicate;
    }

    public List<Tesi> getTesiValutate() {
        return tesiValutate;
    }

    public void setIs_coordinatore(boolean is_coordinatore) {
        this.is_coordinatore = is_coordinatore;
    }

    public void aggiungiTirocinio(Tirocinio t) {
        if (t != null && ! this.listaTirocini.contains(t)) {
            this.listaTirocini.add(t);
        }
    }

    public void inserisciSeduta(Seduta s) {
        if (is_coordinatore) {
            this.sedutePubblicate.add(s);
        }
    }

    
}
