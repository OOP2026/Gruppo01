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

    private final List<Docente> listaDocenti = new ArrayList<>();
    private final List<Richiesta> listaRichieste = new ArrayList<>();
    private final List<Seduta> listaSedute = new ArrayList<>();
    private final List<Studente> listaStudenti = new ArrayList<>();
    private final List<Tesi> listaTesi = new ArrayList<>();
    private final List<Tirocinio> listaTirocini = new ArrayList<>();
    private final List<Tirocinio_esterno> listaTirocini_esterni = new ArrayList<>();
    private final List<Tirocinio_Interno> listaTirocini_interni = new ArrayList<>();
    private final List<Utente> listaUtenti = new ArrayList<>();
    private Docente DocenteLoggato = null;
    private Studente StudenteLoggato = null;

    public Controller() {}

    //region METODI BASE (HOME E LOGIN)
    public void effettuaLoginStudente(String user, String pwd) {
        for (Studente stud : listaStudenti) {
            if (stud.login(user, pwd)) {
                StudenteLoggato = stud;
                return;
            }
        }
        throw new IllegalArgumentException("ERRORE| Nome_utente o password errati.");
    }

    public void effettuaLoginDocente(String user, String pwd) {
        for (Docente d : listaDocenti) {
            if (d.login(user, pwd)) {
                DocenteLoggato = d;
                return;
            }
        }
        throw new IllegalArgumentException("ERRORE| Nome_utente o password errati.");
    }

    public Docente getdocLoggato() {
        return DocenteLoggato;
    }

    public Studente getstudLoggato() {
        return StudenteLoggato;
    }

    //endregion


    //region METODI DEL DOCENTE LOGGATO
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

    public void aggiungiArgomenti(String s) {
        DocenteLoggato.aggiungiArgomento(s);
    }

    public void rimuoviArgomento (String s) {
        DocenteLoggato.rimuoviArgomento(s);
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

    //Il docente approva la Tesi
    public void approvaTesi(String id) {
        Tesi t =  getTesidaID(id);
        t.setStato(Stato_Tesi.Approvata);
    }





    //Il docente boccia la Tesi
    public void rifiutaTesi(String id) {
        Tesi t =  getTesidaID(id);
        t.setStato(Stato_Tesi.Rifiutata);
    }




    //Il docente riceve i titoli di tutte le tesi a lui associate, assieme all'id
    public List<String> getIdTesi() {
        ArrayList<String> listaTesi = new ArrayList<>();
        for (Tesi t : DocenteLoggato.getTesi()) {
            listaTesi.add(t.getId() + ": " + t.getTitolo());
        }
        return listaTesi;
    }

    public Tesi getTesidaID(String ID_TITOLO) {
        for(Tesi t: DocenteLoggato.getTesi()){
            String risultato = ID_TITOLO.split(":")[0].trim();
            if (risultato.equals(t.getId())){
                return t;
            }
        }
        throw new IllegalArgumentException("Tesi non presente nel sistema");
    }
    //endregion


    //region METODI REGISTRAZIONE
    //Richiamato dalla GUI per la registrazione del Docente
    public void registraStudente(String nome, String cognome, String email, String matricola, String username, String password) {

        for (Utente u : listaUtenti) {
            if (u.getUsername().equals(username) || u.getEmail().equals(email)) {
                throw new IllegalArgumentException("ERRORE! Username o Email già presenti nel sistema.");
            }
        }

        Studente nuovoStudente = new Studente(nome, cognome, email, matricola, username, password);
        listaStudenti.add(nuovoStudente);
    }

    public void registraDocente(String nome, String cognome, String email, String username, String password) {

        for (Utente u : listaUtenti) {
            if (u.getUsername().equals(username) || u.getEmail().equals(email)) {
                throw new IllegalArgumentException("ERRORE! Username o Email già presenti nel sistema.");
            }
        }

        Docente nuovoDocente = new Docente(nome, cognome, email, username, password);
        listaDocenti.add(nuovoDocente);
    }
    //endregion


    //region METODI COORDINATORE
    //Il docente speciale COORDINATORE crea l'oggetto seduta, lo aggiunge alla lista delle sedute del coordinatore
    public Seduta inserisciSeduta(LocalDateTime data_ora, String sede) {
        if (DocenteLoggato.getisCoordinatore()) {
            Seduta sedutaCreata = new Seduta(data_ora, sede, DocenteLoggato);
            DocenteLoggato.aggiungiSeduta(sedutaCreata);
            listaSedute.add(sedutaCreata);
            return sedutaCreata;
        }
            throw new SecurityException("PERMESSO NEGATO! Funzioone disponibile solo per il coordinatore.");
    }


    public LocalDateTime assemblaDataOra(String giornoStr, String meseStr, String annoStr, String oraStr) {
        try {
            int giorno = Integer.parseInt(giornoStr.trim());
            int mese = Integer.parseInt(meseStr.trim());
            int anno = Integer.parseInt(annoStr.trim());
            int ora = Integer.parseInt(oraStr.trim());
            int minuti = 0 ;

            return LocalDateTime.of(anno, mese, giorno, ora, minuti);

        } catch (NumberFormatException ex) {
            // L'utente ha inserito lettere o lasciato un campo vuoto
            throw new IllegalArgumentException("Per favore, inserisci solo numeri interi nei campi della data.");
        } catch (java.time.DateTimeException ex) {
            throw new IllegalArgumentException("La data o l'orario inserito non esiste sul calendario.");
        }
    }

    public List<String[]> getDatiTabellaSeduta(LocalDateTime dataSeduta) {
        List<String[]> righeTabella = new ArrayList<>();

        // Scorre tutte le tesi caricate nel sistema
        for (Tesi t : this.listaTesi) {

            // Se la tesi fa parte della seduta selezionata dal coordinatore
            if (t.getSeduta_richiesta().getData_ora().equals(dataSeduta)) {

                // Estrae i dati convertendoli in semplici stringhe per proteggere il Model
                String nomeDocente = t.getValutatore().getNome() + " " + t.getValutatore().getCognome();
                String nomeStudente = t.getAutore().getNome() + " " + t.getAutore().getCognome();
                String stato = t.getStato().toString(); // Converte l'Enum in Stringa

                // Crea la riga e la aggiunge alla lista
                String[] riga = {nomeDocente, nomeStudente, stato};
                righeTabella.add(riga);
            }
        }

        return righeTabella;}

    //endregion


    //region METODI STUDENTE
    public List<String> visualizzaTirocini() {
        List<String> listaTirociniDisponibili = new ArrayList<>();
        for (Tirocinio t : listaTirocini) {
            if (t.getStato() == StatoTirocinio.Aperto && t.getN_posti() > 0) {
                listaTirociniDisponibili.add(t.getNome());
            }
        }
        return listaTirociniDisponibili;
    }

    public void compilaRichiesta(String tirScelto) {
        if ((StudenteLoggato.getRichiesta() != null) && ((StudenteLoggato.getRichiesta().getStato() == Stato_richiesta.Approvata) || (StudenteLoggato.getRichiesta().getStato() == Stato_richiesta.In_attesa))) {
            throw new IllegalStateException("ERRORE! Hai già una richiesta attiva.");
        }
        Richiesta r = new Richiesta(StudenteLoggato, getTirocinioDaNome(tirScelto));
        StudenteLoggato.setRichiesta(r);
        listaRichieste.add(r);
    }

    public void caricaTesi(LocalDateTime seduta, String titolo, String documento, Docente relatore) {
        if ((StudenteLoggato.getTesi() != null) && ((StudenteLoggato.getTesi().getStato() == Stato_Tesi.Approvata) || (StudenteLoggato.getTesi().getStato() == Stato_Tesi.In_attesa))) {
            throw new IllegalStateException("ERRORE! Hai già una proposta di tesi attiva.");
        }
        Seduta sedut1 = getSedutaDaData(seduta);
        Tesi tesiDaCaricare = new Tesi(titolo, documento, StudenteLoggato, sedut1, relatore);
        StudenteLoggato.setTesi(tesiDaCaricare);
        relatore.aggiungiTesi(tesiDaCaricare);
        listaTesi.add(tesiDaCaricare);
        sedut1.AggiungiPrenotazione(tesiDaCaricare);
    }

    //ritorna una stringa contenente lo stato della RICHIESTA Studente attualmente Loggato
    public String getStatoStudLoggato() {
        if (StudenteLoggato.getRichiesta() == null) {
            return "Nessuna Richiesta Effettuata";
        }
        if (StudenteLoggato.getRichiesta().getStato() == Stato_richiesta.Approvata)
            return "Approvata";
        if (StudenteLoggato.getRichiesta().getStato() == Stato_richiesta.Rifiutata)
            return "Rifiutata";
        if (StudenteLoggato.getRichiesta().getStato() == Stato_richiesta.In_attesa)
            return "In_attesa";
        return "";
    }

    //ritorna una stringa contenente lo stato DELLA TESI dello Studente attualmente Loggato
    public String getStatoTesi() {
        if (StudenteLoggato.getTesi() == null) {
            return "Nessuna Tesi Caricata";
        }
        if (StudenteLoggato.getTesi().getStato() == Stato_Tesi.Approvata)
            return "Approvata";
        if (StudenteLoggato.getTesi().getStato() == Stato_Tesi.Rifiutata)
            return "Rifiutata";
        if (StudenteLoggato.getTesi().getStato() == Stato_Tesi.In_attesa)
            return "In_attesa";
        return "";
    }
    //endregion


    //region GETTER STRANI E UTILITA
    // Restituisce l'oggetto Studente sulla base della sua Matricola
    public Studente getStudentedDaMatricola(String Matricola) {
        for (Studente s : listaStudenti) {
            if (s.getMatricola().equals(Matricola)) {return s;}
        }
        throw new IllegalArgumentException("Studente non presente nel sistema");
    }

    // Resitutisce l'oggetto Tirocinio sulla base del suo nome (id)
    public Tirocinio getTirocinioDaNome(String Nome) {
        for (Tirocinio t : listaTirocini) {
            if (t.getNome().equals(Nome)) {return t;}
        }
        throw new IllegalArgumentException("Tirocinio non presente nel sistema");
    }


    public Seduta getSedutaDaData(LocalDateTime data) {
        for (Seduta s : listaSedute) {
            if (s.getData_ora() == data) {
                return s;
            }
        }
        throw new IllegalArgumentException("Seduta non presente nel sistema");
    }


    public void verificaPostiDiposibili (Tirocinio t){
        if (t.getN_posti() == 0) {
            t.setStato(StatoTirocinio.Pieno);
        }
    }
    //endregion


    public void caricaDatiDiTest() {

        // --- 1. DOCENTI E COORDINATORE ---
        Docente prof1 = new Docente("Mario", "Rossi", "m.rossi@unina.it", "docente1", "password123");
        prof1.getListaArgomenti().add("Sistemi Operativi");
        prof1.getListaArgomenti().add("Basi di Dati");

        Docente coordinatore = new Docente("Anna", "Bianchi", "a.bianchi@unina.it", "admin", "admin");
        coordinatore.setIs_coordinatore(true); // Promozione a coordinatore

        this.listaDocenti.add(prof1);
        this.listaDocenti.add(coordinatore);


        // --- 2. SEDUTE DI LAUREA ---
        // Ne creiamo una aperta in modo che la tendina dello studente non sia vuota quando carica la tesi
        LocalDateTime dataOraSeduta = LocalDateTime.of(2026, 7, 20, 9, 30);
        Seduta sedutaTest = new Seduta(dataOraSeduta, "Aula Magna", coordinatore);

        coordinatore.aggiungiSeduta(sedutaTest);
        this.listaSedute.add(sedutaTest);


        // --- 3. TIROCINI ---
        LocalDate dataInizioTest = LocalDate.of(2026, 6, 10);

        // Tirocinio 1: Esterno (Per lo Studente 1 che deve ancora scegliere)
        Tirocinio tirocinio1 = new Tirocinio_esterno("Sviluppo Backend Java", "150 ore", dataInizioTest, 3, 6, "Nike", "Maradona", prof1);
        tirocinio1.setStato(StatoTirocinio.Aperto);

        // Tirocinio 2: Interno (Assegnato allo Studente 2)
        Tirocinio tirocinio2 = new Tirocinio_Interno("Machine Learning e AI", "150 ore", dataInizioTest, 2, 6, "ingegneria", "01", prof1);
        tirocinio2.setStato(StatoTirocinio.Aperto);

        this.listaTirocini.add(tirocinio1);
        this.listaTirocini.add(tirocinio2);
        prof1.getListaTirocini().add(tirocinio1);
        prof1.getListaTirocini().add(tirocinio2);


        // --- 4. STUDENTI E RICHIESTE ---
        // Studente 1: Nessuna richiesta (Usa questo per testare la compilazione della richiesta)
        Studente stud1 = new Studente("Luca", "Verdi", "l.verdi@studenti.unina.it", "pas", "st", "N46001111");
        Richiesta r1 = new Richiesta(stud1, tirocinio1);
        // Studente 2: Richiesta giÃ approvata (Usa questo per testare direttamente il caricamento Tesi)
        Studente stud2 = new Studente("Marco", "Neri", "m.neri@studenti.unina.it", "N46002222", "studente2", "password123");
        Richiesta r2 = new Richiesta(stud2, tirocinio2);

        // Forza l'approvazione scavalcando il docente
        r2.setStato(Stato_richiesta.Approvata);
        stud2.setRichiesta(r2);
        this.listaRichieste.add(r2);

        this.listaStudenti.add(stud1);
        this.listaStudenti.add(stud2);

        System.out.println("SISTEMA: Dati di test caricati con successo (Docenti, Sedute, Tirocini, Studenti).");
    }

}