package model;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

public class Tirocinio_Interno extends Tirocinio{
    private String dipartimento;
    private String laboratorio;




    public Tirocinio_Interno (String nome, String durata, LocalDate data_inizio, int n_posti, int n_cfu, String dipartimento, String laboratorio, Docente docente, int id) {
        super(nome, durata, data_inizio, n_posti, n_cfu, docente, id);
        this.dipartimento = dipartimento;
        this.laboratorio = laboratorio;
    }

}


