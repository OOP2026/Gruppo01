package controller;
import model.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Controller {
	private List<Docente> listaDocenti = new ArrayList<>();
	private List<Richiesta> listaRichieste = new ArrayList<>();
	private List<Seduta> listaSedute = new ArrayList<>();
	private List<Studente> listaStudenti = new ArrayList<>();
	private List<Tesi> listaTesi = new ArrayList<>();
	private List<Tirocinio> listaTirocini = new ArrayList<>();
	private List<Tirocinio_esterno> listaTirocini_esterni = new ArrayList<>();
	private List<Tirocinio_Interno> listaTirocini_interni = new ArrayList<>();
	private List<Utente> listaUtenti = new ArrayList<>();

	public Controller(){};

	public void aggiungiTirocinoInterno(String nome, int durata, LocalDateTime data_inizio, int n_posti, int n_cfu, String dipartimento, String laboratorio, Docente docente){
        Tirocinio_Interno nuovoTirocinio = new Tirocinio_Interno(nome, durata, data_inizio, n_posti, n_cfu, dipartimento, laboratorio, docente);
        docente.aggiungiTirocinio(nuovoTirocinio);
        listaTirocini.add(nuovoTirocinio);
    }

    public void aggiungiTirocinioEsterno(String nome, int durata, LocalDateTime data_inizio, int n_posti, int n_cfu, String azienda, String referente_aziendale, Docente docente){
        Tirocinio_esterno nuovoTirocinio = new Tirocinio_esterno(nome, durata, data_inizio, n_posti, n_cfu, azienda, referente_aziendale, docente);
        docente.aggiungiTirocinio(nuovoTirocinio);
        listaTirocini.add(nuovoTirocinio);
    }

    public void valutaRichiesta(Richiesta r, Stato_richiesta esito) {
        if (r.getStato() != Stato_richiesta.In_attesa) {
            throw new IllegalStateException("ERRORE! Richiesta già valutata!");
        } else {
            r.setStato(esito);
        }
    }

    public void inserisciSeduta(Docente coord, Seduta seduta){
        if (coord.getisCoordinatore()) {
            coord.aggiungiSeduta(seduta);
            listaSedute.add(seduta);
        };
    }




}
