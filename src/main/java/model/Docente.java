package model;


import java.util.*;


public class Docente extends Utente {
    private boolean is_coordinatore;
    private final List<Tirocinio> listaTirocini;
    private final List<Seduta> sedutePubblicate;
    private final List<Tesi> tesiDaValutare;
    private final List<String> listaArgomenti = new ArrayList<>();

    public Docente(String nome, String cognome, String email,String username ,String password ) {
        super(nome, cognome, email, username,password );
        this.is_coordinatore = false;
        this.listaTirocini = new ArrayList<Tirocinio>();
        this.sedutePubblicate = new ArrayList<Seduta>();
        this.tesiDaValutare = new ArrayList<Tesi>();
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


    public void rimuoviArgomento(String a) {
        listaArgomenti.remove(a);
    }

    public List<Seduta> getListaSedute() {return sedutePubblicate;}
}
