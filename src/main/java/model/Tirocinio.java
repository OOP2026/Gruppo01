package model;
import java.time.LocalDate;
import java.util.*;


public abstract class Tirocinio {
    private int id;
    private String nome;
    private String durata; //numero di ore
    private LocalDate data_inizio;
    private String argomento;
    private int n_posti;
    private int n_cfu;
    private Docente docente;
    private List<Richiesta> richieste;
    private StatoTirocinio stato;

    public Tirocinio(String nome, String durata, LocalDate data_inizio, int n_posti, int n_cfu, Docente docente, int id) {
        this.id = id;
        this.nome = nome;
        this.durata = durata;
        this.data_inizio = data_inizio;
        this.argomento = new String();
        this.n_posti = n_posti;
        this.n_cfu = n_cfu;
        this.docente = docente;
        this.richieste = new ArrayList<Richiesta>();
        this.stato = StatoTirocinio.Aggiunto;
    }

    public StatoTirocinio getStato() {return stato;}

    public String getNome() {
        return nome;
    }

    public int getN_posti() {
        return n_posti;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setStato(StatoTirocinio st) {this.stato = st;}

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Richiesta> getRichieste() {
        return richieste;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public void decrementaPosti() {
        n_posti--;
    }

    public int getid() {return id;}
}
