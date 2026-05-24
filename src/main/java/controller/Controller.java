package controller;
import model.*;
import java.time.LocalDateTime;
import java.util.*;

/*
ATTENZIONE!!! LA LISTA DI OGGETTI CHE SEGUE È SOLO UN MODO PER GESTIRE MOMENTANEAMENTE (IN QUESTA FASE DEL
PROGETTO IL DIALOGO TRA IL CONTROLLER E IL DATABASE. IN FASI PIù AVANZATE DEL PROGETTO, SI SOSTITUIRÀ TALE
INSIEME DI LISTE CON L'EFFETTIVA RELAZIONE COL DATABASE. LO STESSO VALE PER GLI ADD PRESENTI
NEI METODI DEL CONTROLLER PER LA CREAZIONE DEI VARI OGGETTI.
 */
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



    public Controller() {
    }

    //Richiamato dalla GUI per la registrazione del Docente
    public Docente creaDocente(String nome, String cognome, String email, String password, String username) {
        Docente doc = new Docente(nome, cognome, email, password, username);
        listaDocenti.add(doc); // da rivedere in fase DB
        return doc;
    }

    //Richiamato dalla gui per la registrazione dello Studente
    public Studente creaStudente(String nome, String cognome, String email, String password, String username, String matricola) {
        Studente stud = new Studente(nome, cognome, email, password, username, matricola);
        listaStudenti.add(stud); // da rivedere in fase DB
        return stud;
    }


    // Il docente (richiamato tra gli argomenti del metodo) costruisce l'oggetto TirocinioInterno
    public void aggiungiTirocinoInterno(String nome, int durata, LocalDateTime data_inizio, int n_posti, int n_cfu, String dipartimento, String laboratorio, Docente docente) {
        Tirocinio_Interno nuovoTirocinio = new Tirocinio_Interno(nome, durata, data_inizio, n_posti, n_cfu, dipartimento, laboratorio, docente);
        docente.aggiungiTirocinio(nuovoTirocinio);
        listaTirocini.add(nuovoTirocinio); // da rivedere in fase DB
    }

    // Il docente (richiamato tra gli argomenti del metodo) costruisce l'oggetto TirocinioEsterno
    public void aggiungiTirocinioEsterno(String nome, int durata, LocalDateTime data_inizio, int n_posti, int n_cfu, String azienda, String referente_aziendale, Docente docente) {
        Tirocinio_esterno nuovoTirocinio = new Tirocinio_esterno(nome, durata, data_inizio, n_posti, n_cfu, azienda, referente_aziendale, docente);
        docente.aggiungiTirocinio(nuovoTirocinio);
        listaTirocini.add(nuovoTirocinio); // da rivedere in fase DB
    }

    //Il Docente visualizza le richieste a lui arrivate
    public List<Richiesta> visualizzaRichieste() {
        return Collections.unmodifiableList(this.listaRichieste);
    }

    //Il docente valuta le richieste di Tirocinio a lui arrivate
    public void valutaRichiesta(Docente doc, Richiesta r, Stato_richiesta esito) {
        if (!r.getTirocinio().getDocente().equals(doc)) {
            throw new SecurityException("ERRORE! Operazione negata. Non sei il referente di questo tirocinio.");
        }
        if (r.getStato() != Stato_richiesta.In_attesa) {
            throw new IllegalStateException("ERRORE! Richiesta già valutata!");
        }
        if (esito == Stato_richiesta.In_attesa) {
            throw new IllegalArgumentException("ERRORE! Scegliere tra Approvata e Rifiutata.");
        }
        if (esito == Stato_richiesta.Approvata) {
            Tirocinio t = r.getTirocinio();
            if (t.getN_posti() <= 0) {
                throw new IllegalStateException("ERRORE! I posti per questo tirocinio sono esauriti.");
            }
            r.setStato(esito);
            r.getRichiedente().setTirocinio(t);
            t.decrementaPosti();
        } else if (esito == Stato_richiesta.Rifiutata) {
            r.setStato(esito);
            System.out.println("Richiesta rifiutata correttamente");
            //se il valore è Rifiutata, lo studente può fare una nuova richiesta tirocinio.
        }
    }

    //Il docente speciale COORDINATORE crea l'oggetto seduta, lo aggiunge alla lista delle sedute del coordinatore
    public Seduta inserisciSeduta(Docente coord, LocalDateTime data_ora, String sede, int numero_posti) {
        if (coord.getisCoordinatore()) {
            Seduta sedutaCreata = new Seduta(data_ora, sede, numero_posti, coord);
            coord.aggiungiSeduta(sedutaCreata);
            listaSedute.add(sedutaCreata);
            return sedutaCreata;
            } else {
            throw new SecurityException("PERMESSO NEGATO! Funzioone disponibile solo per il coordinatore.");
        }
    }

    //Il docente valuta la Tesi
    public void valutaTesi(Tesi t, Stato_Tesi esito) {
        if (t.getStato() != Stato_Tesi.In_attesa) {
            throw new IllegalStateException("ERRORE! Richiesta già valutata!");
        }
        if (esito == Stato_Tesi.In_attesa) {
            throw new IllegalArgumentException("ERRORE! Scegliere tra Approvata e Rifiutata.");
        }
        t.setStato(esito);
        if (esito == Stato_Tesi.Approvata) {
            Seduta s = t.getSeduta_richiesta();
            s.decrementaPosti();
        } else {
            System.out.println("Richiesta rifiutata correttamente");

        }
    }

    public List<Tirocinio> visualizzaTirociniInCorso() {
        ArrayList<Tirocinio> listaInCorso = new ArrayList<>();
        for (Tirocinio t : listaTirocini) {
            if (t.getStato() == StatoTirocinio.In_corso) {
                listaInCorso.add(t);
            }
        }
        return listaInCorso;
    }

    public void impostaSeduta(Seduta s, Docente coord) {
        if (coord.getisCoordinatore()) {
            // visualizzazione studenti (se la tesi è stata approvata)
            List<Studente> listaStudenti = s.getStudentiPrenotati();
            //imposta docenti
            for (Studente stud : listaStudenti) {
                s.aggiungiInCommissione(stud.getTesi().getValutatore());
            }
            //crea commissione
        } else {
            throw new IllegalStateException("ERRORE! Operazione permessa solo al coordinatore del corso.");
        }
    }

    public void aggiungiArgomenti(Tirocinio t, String s) {
        t.aggiungiArgomento(s);
    }

    public List<Tirocinio> visualizzaTirocini() {
        List<Tirocinio> listaTirociniDisponibili = new ArrayList<>();
        for (Tirocinio t : listaTirocini) {
            if (t.getStato() == StatoTirocinio.Aperto && t.getN_posti() > 0) {
                listaTirociniDisponibili.add(t);
            }
        }
        return listaTirociniDisponibili;
    }


    public void compilaRichiesta(Tirocinio tirScelto, Studente stud) {
        if ((stud.getRichiesta() != null) && ((stud.getRichiesta().getStato() == Stato_richiesta.Approvata) || (stud.getRichiesta().getStato() == Stato_richiesta.In_attesa))) {
            throw new IllegalStateException("ERRORE! Hai già una richiesta attiva.");
        }
        Richiesta r = new Richiesta(stud, tirScelto);
        stud.setRichiesta(r);
        listaRichieste.add(r);
    }

    public void caricaTesi(Studente stud, Seduta seduta, String titolo, String documento, Docente relatore) {
        if ((stud.getTesi() != null) && ((stud.getTesi().getStato() == Stato_Tesi.Approvata) || (stud.getTesi().getStato() == Stato_Tesi.In_attesa))) {
            throw new IllegalStateException("ERRORE! Hai già una proposta di tesi attiva.");
        }
        Tesi tesiDaCaricare = new Tesi(titolo, documento, stud, seduta, relatore);
        stud.setTesi(tesiDaCaricare);
        relatore.aggiungiTesi(tesiDaCaricare);
        listaTesi.add(tesiDaCaricare);
    }

    public Stato_richiesta verificaStatoRichiesta(Studente s) {
        if (s.getRichiesta() == null) {
            throw new IllegalStateException("ERRORE! Nessuna richiesta attiva per questo studente.");
        }
        return s.getRichiesta().getStato();
    }

    public Stato_Tesi verificaStatoTesi(Studente s) {
        if (s.getTesi() == null) {
            throw new IllegalStateException("ERRORE! Nessuna tesi caricata per questo studente.");
        }
        return s.getTesi().getStato();
    }

    public boolean effettuaLoginStudente(String user, String pwd) {
        for (Studente stud : listaStudenti) {
            if (stud.login(user, pwd)) {
                return true;
            }
        }
        throw new IllegalArgumentException("ERRORE| Nome_utente o password errati.");
    }

    public String getMatricolaStudente(Studente s) {
        return s.getMatricola();
    }

    public boolean effettuaLoginDocente(String user, String pwd) {
        for (Docente d : listaDocenti) {
            if (d.login(user, pwd)) {
                return true;
            }
        }
        throw new IllegalArgumentException("ERRORE| Nome_utente o password errati.");
    }
    public String getEmailDocente(Docente d){
        return d.getEmail();
    }


    public void registraStudente(String nome, String cognome, String email, String matricola, String username, String password) {

        for (Utente u : listaUtenti) {
            if (u.getUsername().equals(username) || u.getEmail().equals(email)) {
                throw new IllegalArgumentException("ERRORE! Username o Email già presenti nel sistema.");
            }
        }

        Studente nuovoStudente = new Studente(nome, cognome, email, matricola, username, password);
        listaStudenti.add(nuovoStudente);
    }
    //si ipotizza che la verifica dell'esistenza effettiva degli utenti nell'Università sia di docente che di studente siano fatte in un codice a parte, esterno al progetto.

    public void registraDocente(String nome, String cognome, String email, String username, String password) {

        for (Utente u : listaUtenti) {
            if (u.getUsername().equals(username) || u.getEmail().equals(email)) {
                throw new IllegalArgumentException("ERRORE! Username o Email già presenti nel sistema.");
            }
        }

        Docente nuovoDocente = new Docente(nome, cognome, email, username, password);
        listaDocenti.add(nuovoDocente);
    }

}



