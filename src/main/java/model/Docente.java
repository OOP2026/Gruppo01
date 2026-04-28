package model;

import java.time.LocalDateTime;
import java.util.*;


public class Docente extends Utente {
    private boolean is_coordinatore;
    private List<Tirocinio> listaTirocini;
    List<Seduta> sedute_pubblicate;
    List<Tesi> tesi_valutate;

    public Docente(String nome, String cognome, String email, String password, String username) {
        super(nome, cognome, email, password, username);
        this.is_coordinatore = false;
        this.listaTirocini = new ArrayList<>();
        this.sedute_pubblicate = null;
        this.tesi_valutate = null;
    }

}
