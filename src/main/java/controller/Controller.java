package controller;
import model.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
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
    private Docente DocenteLoggato = null;
    private Studente StudenteLoggato = null;


    public Controller() {
    }

    public String getStatoStudLoggato() {
        if (StudenteLoggato.getRichiesta().getStato() == Stato_richiesta.Approvata)
            return "Approvata";
        if (StudenteLoggato.getRichiesta().getStato() == Stato_richiesta.Rifiutata)
            return "Rifiutata";
        if (StudenteLoggato.getRichiesta().getStato() == Stato_richiesta.In_attesa)
            return "In_attesa";
        if (StudenteLoggato.getRichiesta().getStato() == Stato_richiesta.NULL)
            return "Nessuna Richiesta Effettuata";
        return "";
    }
    public String getStatoTesi() {
        if (StudenteLoggato.getTesi().getStato() == Stato_Tesi.Approvata)
            return "Approvata";
        if (StudenteLoggato.getTesi().getStato() == Stato_Tesi.Rifiutata)
            return "Rifiutata";
        if (StudenteLoggato.getTesi().getStato() == Stato_Tesi.In_attesa)
            return "In_attesa";
        return "";
    }
    //Restituisce l'oggetto Studente sulla base della sua Matricola
    public Studente getStudentedDaMatricola(String Matricola) {
        for (Studente s : listaStudenti) {
            if (s.getMatricola().equals(Matricola)) {return s;}

        }
        throw new IllegalArgumentException("Studente non presente nel sistema");
    }



    //Resitutisce l'oggetto Tirocinio sulla base del suo nome (id)
    public Tirocinio getTirocinioDaNome(String Nome) {
        for (Tirocinio t : listaTirocini) {
            if (t.getNome().equals(Nome)) {return t;}

        }
        throw new IllegalArgumentException("Tirocinio non presente nel sistema");
    }


    //trova i tirocini aperti del docente Loggato
    public ArrayList<String> getNomiTirociniApertiDelDocente() {
        ArrayList<String> nomiTirociniAperti = new ArrayList<>();
        for (Tirocinio t : listaTirocini) {
            if (t.getDocente().equals(this.DocenteLoggato) && t.getStato() == StatoTirocinio.Aperto) {
                nomiTirociniAperti.add(t.getNome());
            }
        }

        return nomiTirociniAperti;
    }

    //Il docente valuta le richieste di Tirocinio a lui arrivate

    public void verificaPostiDiposibili (Tirocinio t){
        if (t.getN_posti() == 0) {
            t.setStato(StatoTirocinio.Pieno);
        }
    }
    //Approva la Richiesta di Tirocinio


    public void approvaRichiestaTirocinio(String Matricola, String Nome) {
        //verifichiamo che
        verificaPostiDiposibili(getTirocinioDaNome(Nome));

        for (Richiesta r : listaRichieste) {
            if (r.getRichiedente().getMatricola().equals(Matricola) && r.getTirocinio().getNome().equals(Nome) ) {
                r.setStato(Stato_richiesta.Approvata);
                r.getTirocinio().decrementaPosti();
            }
        }
    }

    //Rifiuta la Richiesta di Tirocinio
    public void rifiutaRichiestaTirocinio(String Matricola, String Nome) {
        for (Richiesta r : listaRichieste) {
            if (r.getRichiedente().getMatricola().equals(Matricola) && r.getTirocinio().getNome().equals(Nome)) {
                r.setStato(Stato_richiesta.Rifiutata);
            }
        }
    }


    public Docente getdocLoggato() {
        return DocenteLoggato;
    }

    public Studente getstudLoggato() {
        return StudenteLoggato;
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

    public List<String> getNomiTirociniDelDocente() {
        List<String> listaNomi = new ArrayList<>();
        for (Tirocinio t : DocenteLoggato.getListaTirocini()) {
            listaNomi.add(t.getNome());
        }
        return listaNomi;
    }

    //restituisce la lista delle richieste arrivate per quello specifico tirocinio
    public List<String> RichiesteTir(String tirocinio) {
        List<String> listaStudenti = new ArrayList<>();
        for (Richiesta r : listaRichieste) {
            if(r.getTirocinio().getNome().equals(tirocinio)) {
                listaStudenti.add(r.getRichiedente().getMatricola());
            }
        }
        return listaStudenti;
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

    public void aggiungiArgomenti(String s, Docente d) {
        d.aggiungiArgomento(s);
    }

    public void rimuoviArgomento (String s, Docente d) {
        d.rimuoviArgomento(s);
    }

    public List<String> getListaArgomenti(Docente d) {
       return d.getListaArgomenti();
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
                StudenteLoggato = stud;
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
                DocenteLoggato = d;
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


