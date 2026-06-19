package model;

import java.time.LocalDate;
import java.util.List;

public class Tirocinio_esterno extends Tirocinio{
    private String azienda;
    private String referente_aziendale;


    public Tirocinio_esterno(String nome, String durata, LocalDate data_inizio, int n_posti, int n_cfu, String azienda, String referente_aziendale, Docente docente, int id) {
        super(nome, durata, data_inizio, n_posti, n_cfu, docente,id);
        this.azienda = azienda;
        this.referente_aziendale = referente_aziendale;
    }
}