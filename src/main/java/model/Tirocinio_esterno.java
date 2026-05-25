package model;

import java.time.LocalDate;
import java.util.List;

public class Tirocinio_esterno extends Tirocinio{
    private String azienda;
    private String referente_aziendale;


    public Tirocinio_esterno(String nome, String durata, LocalDate data_inizio, int n_posti, int n_cfu, String azienda, String referente_aziendale, Docente docente) {
        super(nome, durata, data_inizio, n_posti, n_cfu, docente);
        this.azienda = azienda;
        this.referente_aziendale = referente_aziendale;
    }

    public String getAzienda() {
        return azienda;
    }

    public String getReferente_aziendale() {
        return referente_aziendale;
    }

    public void setAzienda(String azienda) {
        this.azienda = azienda;
    }

    public void setReferente_aziendale(String referente_aziendale) {
        this.referente_aziendale = referente_aziendale;
    }
}