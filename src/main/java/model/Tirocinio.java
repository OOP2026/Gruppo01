package model;
import java.time.LocalDateTime;
import java.util.*;


public abstract class Tirocinio {
    private String nome;
    private int durata; //numero di ore
    private LocalDateTime data_inizio;
    private List<String> argomenti;
    private int n_posti;
    private int n_cfu;
    private Docente docente;
    private List<Richiesta> richieste;

    public Tirocinio(String nome, int durata, LocalDateTime data_inizio, List<String> argomenti, int n_posti, int n_cfu, Docente docente) {
        this.nome = nome;
        this.durata = durata;
        this.data_inizio = data_inizio;
        // Se la lista passata è null, inizializza una lista vuota per evitare NullPointerException
        this.argomenti = new ArrayList<String>();
        this.n_posti = n_posti;
        this.n_cfu = n_cfu;
        this.docente = docente;
        this.richieste = new ArrayList<Richiesta>();
    }
}
