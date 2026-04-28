package model;
import java.util.*;
import java.time.LocalDateTime;

public class Seduta {
    LocalDateTime data_ora;
    String sede;
    int numero_posti;
    List<Tesi> prenotazioni;
    Docente coordinatore;

    public Seduta(LocalDateTime data_ora, String sede,int numero_posti, Docente coordinatore) {
        this.data_ora = data_ora;
        this.sede = sede;
        this.numero_posti = numero_posti;
        this.prenotazioni = new ArrayList<Tesi>();
        this.coordinatore = coordinatore;
    }


}
