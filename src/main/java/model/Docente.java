package model;


import java.util.*;


public class Docente extends Utente {
    private boolean is_coordinatore;
    private final List<Tirocinio> listaTirocini;
    private final List<Seduta> sedutePubblicate;
    private final List<Tesi> tesiDaValutare;
    private final List<String> listaArgomenti = new ArrayList<>();

    public Docente(String nome, String cognome, String email, String password, String username) {
        super(nome, cognome, email, password, username);
        this.is_coordinatore = false;
        this.listaTirocini = new ArrayList<Tirocinio>();
        this.sedutePubblicate = new ArrayList<Seduta>();
        this.tesiDaValutare = new ArrayList<Tesi>();
    }

    public List<Tirocinio> getListaTirocini(){
        return listaTirocini;
    }

    public List<Tesi> getTesi() {
        return tesiDaValutare;
    }

    public boolean getisCoordinatore() {
        return is_coordinatore;
    }

    public void setIs_coordinatore(boolean is_coordinatore) {
        this.is_coordinatore = is_coordinatore;
    }

    public void aggiungiTirocinio(Tirocinio t) {
        if (t != null && ! this.listaTirocini.contains(t)) {
            this.listaTirocini.add(t);
        }
    }

    public void aggiungiSeduta(Seduta s) {
        this.sedutePubblicate.add(s);
    }

    public void aggiungiTesi(Tesi ts) {
        if (ts != null && !this.tesiDaValutare.contains(ts)) {
            this.tesiDaValutare.add(ts);
        }
    }

    public void aggiungiArgomento(String a) {
        listaArgomenti.add(a);
    }

    public void rimuoviArgomento(String a) {
        listaArgomenti.remove(a);
    }

    public List<String> getListaArgomenti() {
        return listaArgomenti;
    }
}
