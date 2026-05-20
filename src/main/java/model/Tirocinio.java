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

    public Tirocinio(String nome, int durata, LocalDateTime data_inizio, int n_posti, int n_cfu, Docente docente) {
        this.nome = nome;
        this.durata = durata;
        this.data_inizio = data_inizio;
        this.argomenti = new ArrayList<String>();
        this.n_posti = n_posti;
        this.n_cfu = n_cfu;
        this.docente = docente;
        this.richieste = new ArrayList<Richiesta>();
    }

    public String getNome() {
        return nome;
    }

    public int getDurata() {
        return durata;
    }

    public LocalDateTime getData_inizio() {
        return data_inizio;
    }

    public List<String> getArgomenti() {
        return argomenti;
    }

    public int getN_posti() {
        return n_posti;
    }

    public int getN_cfu() {
        return n_cfu;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public void setData_inizio(LocalDateTime data_inizio) {
        this.data_inizio = data_inizio;
    }

    public List<Richiesta> getRichieste() {
        return richieste;
    }

    public void setN_posti(int n_posti) {
        this.n_posti = n_posti;
    }

    public void setN_cfu(int n_cfu) {
        this.n_cfu = n_cfu;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }
   public void AggiungiArgomeni(argomenti A){
        this.argomenti.add(A);
    }
    public void  rimuoviArgomenti(argomenti A){
        this.argomenti.remove(A);
    }
    public void AggiungiRichieste(richieste R){
        this.richieste.add(R);
    }
    public void  rimuoviArgomenti(richieste R){
        this.richieste.remove(R);
    }

}
