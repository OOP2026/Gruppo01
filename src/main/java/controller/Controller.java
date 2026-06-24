package controller;
import dao.DocenteDAO;
import dao.OperazioniDocenteDAO;
import dao.OperazioniStudenteDAO;
import dao.StudenteDAO;
import implementazioneDao.DocentePostgresDAO;
import implementazioneDao.OperazioniDocentePostgresDAO;
import implementazioneDao.OperazioniStudentePostgresDAO;
import implementazioneDao.StudentePostgresDAO;
import model.*;

import java.sql.SQLException;
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
    private Docente docenteLoggato = null;
    private Studente studenteLoggato = null;

    //Costruttore del Controller, non necessita di argomenti.
    public Controller() {}

    //region METODI BASE (HOME E LOGIN)
    public boolean effettuaLoginStudente(String user, String pwd) {
        StudentePostgresDAO studDAO = new StudentePostgresDAO();

        // Riceve solo i dati grezzi
        List<String> dati = studDAO.loginStudente(user, pwd);

        if (dati == null || dati.isEmpty()) {
            return false;
        } else {
            // Il Controller costruisce l'oggetto estraendo i valori dalla lista in base all'ordine di inserimento
            String nome = dati.get(0);
            String cognome = dati.get(1);
            String email = dati.get(2);
            String matricola = dati.get(3);

            this.studenteLoggato = new Studente(nome, cognome, email, matricola, user, pwd);
            return true;
        }
    }


    public boolean effettuaLoginDocente(String user, String pwd) {
        DocentePostgresDAO docDAO = new DocentePostgresDAO();

        // Riceve solo i dati grezzi
        List<String> dati = docDAO.loginDocente(user, pwd);

        if (dati == null || dati.isEmpty()) {
            return false;
        } else {
            // Il Controller costruisce l'oggetto estraendo i valori dalla lista in base all'ordine di inserimento
            String nome = dati.get(0);
            String cognome = dati.get(1);
            String email = dati.get(2);

            this.docenteLoggato = new Docente(nome, cognome, email, user, pwd);
            if(docDAO.checkIsCoordinatore(docenteLoggato.getUsername()))
                docenteLoggato.setIs_coordinatore(true);
            return true;
        }
    }

    public Docente getdocLoggato() {
        return docenteLoggato;
    }

    public Studente getstudLoggato() {
        return studenteLoggato;
    }

    //endregion


    //region METODI DEL DOCENTE LOGGATO
    //trova i tirocini aperti del docente Loggato
    public List<String> getNomiTirociniApertiDelDocente() {
        ArrayList<String> nomiTirociniAperti = new ArrayList<>();
        for (Tirocinio t : listaTirocini) {
            if (t.getDocente().equals(this.docenteLoggato) && t.getStato() == StatoTirocinio.Aperto) {
                nomiTirociniAperti.add(t.getid() + ":" + t.getNome());
            }
        }
        return nomiTirociniAperti;
    }

    //Approva la Richiesta di Tirocinio
    public void approvaRichiestaTirocinio(String matricola, int id) {
        verificaPostiDiposibili(getTirocinioDaId(id));
        for (Richiesta r : listaRichieste) {
            if (r.getRichiedente().getMatricola().equals(matricola) && r.getTirocinio().getid() == id) {
                r.setStato(Stato_richiesta.Approvata);
                r.getTirocinio().decrementaPosti();
            }
            verificaPostiDiposibili(r.getTirocinio());
        }
    }


    public List<String[]> visualizzaTirocinioStudenti() {
        List<String[]> righeTabella = new ArrayList<>();



        OperazioniDocenteDAO dao= new OperazioniDocentePostgresDAO();
        //interroga DAO e riceve una lista di Select contenente le info sulle sedute aperte
        List<String[]> tirociniInCorso = dao.visualizzaTirociniInCorso(docenteLoggato.getUsername());
        if (tirociniInCorso == null) {
            return null;
        }
        return tirociniInCorso;

    }



    // la Richiesta di Tirocinio
    public void rifiutaRichiestaTirocinio(String matricola, int id) {
        for (Richiesta r : listaRichieste) {
            if (r.getRichiedente().getMatricola().equals(matricola) && r.getTirocinio().getid() == id) {
                r.setStato(Stato_richiesta.Rifiutata);
            }
        }
    }


    public List<String> getArgomentiDocLoggato() {
        List<String> argList = new ArrayList<>();
        OperazioniDocenteDAO dao = new OperazioniDocentePostgresDAO();
        argList = dao.getArgomentiDocente(docenteLoggato.getUsername());
        return argList;
    }

    public void aggiungiArgomenti(String s) {
        OperazioniDocenteDAO dao= new OperazioniDocentePostgresDAO();
        try {
            // 1. Garantisci l'esistenza del padre
            dao.aggiungiArgomento(s);

            // 2. Crea il legame
            dao.associaDocenteArgomento(s, docenteLoggato.getUsername());

            System.out.println("Associazione creata con successo!");
        } catch (SQLException e) {
            System.err.println("Errore durante l'inserimento: " + e.getMessage());
        }
    }

    public void rimuoviArgomento (String s) {
        OperazioniDocenteDAO dao= new OperazioniDocentePostgresDAO();
        dao.rimuoviArgomento(s, docenteLoggato.getUsername());
        System.out.println("Associazione creata con successo!");
        docenteLoggato.rimuoviArgomento(s);
    }


    //restituisce la lista delle richieste arrivate per quello specifico tirocinio
    public List<String> richiesteTir(String tirocinio) {
        List<String> listaStud = new ArrayList<>();
        for (Richiesta r : listaRichieste) {
            if(r.getTirocinio().getNome().equals(tirocinio)) {
                listaStud.add(r.getRichiedente().getMatricola());
            }
        }
        return listaStud;
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
        ArrayList<String> lista = new ArrayList<>();
        for (Tesi t : docenteLoggato.getTesi()) {
            lista.add(t.getId() + ": " + t.getTitolo());
        }
        return lista;
    }

    public Tesi getTesidaID(String idTitolo) {
        for(Tesi t: docenteLoggato.getTesi()){
            int risultato = Integer.parseInt(idTitolo.split(":")[0].trim());
            if (risultato== t.getId()){
                return t;
            }
        }
        throw new IllegalArgumentException("Tesi non presente nel sistema");
    }
    //endregion


    //region METODI REGISTRAZIONE
    //Richiamato dalla GUI per la registrazione del Docente
    public boolean registraStudente(String nome, String cognome, String email, String matricola, String username, String password) {


        StudenteDAO dao = new StudentePostgresDAO();

        boolean successoDB = dao.registraStudente(nome, cognome, email, matricola, username, password);
        if(successoDB) {
            Studente nuovoStudente = new Studente(nome, cognome, email, matricola, username, password);
            listaStudenti.add(nuovoStudente);
        }

        return successoDB;
    }

    public boolean registraDocente(String nome, String cognome, String email, String username, String password) {

        DocenteDAO dao = new DocentePostgresDAO();

        boolean successoDB = dao.registraDocente(nome, cognome, email, username, password);
        if(successoDB) {
            Docente nuovoDocente = new Docente(nome, cognome, email, username, password);
            listaDocenti.add(nuovoDocente);
        }

        return successoDB;
    }
    //endregion


    //region METODI COORDINATORE
    //Il docente speciale COORDINATORE crea l'oggetto seduta, lo aggiunge alla lista delle sedute del coordinatore
    public void inserisciSeduta(LocalDateTime data_ora, String sede) {
        OperazioniDocenteDAO dao = new OperazioniDocentePostgresDAO();
        dao.creaSeduta(data_ora,sede);
    }

    //La gui per creare la lista delle sedute aperte ha bisogno di una Lista di Stringhe
    public List<String> creaListaSeduteAperte() {
        List<String> result = new ArrayList<>();
        List<Seduta> seduteAperte = new ArrayList<>();

        // Raccogliamo solo gli oggetti Seduta con stato aperto
        for (Seduta s : docenteLoggato.getListaSedute()) {
            if (s.getStato()) {
                seduteAperte.add(s);
            }
        }

        // Ordiniamo gli oggetti in base alla loro data
        seduteAperte.sort(Comparator.comparing(Seduta::getData_ora));

        // Estrapoliamo le stringhe
        for (Seduta s : seduteAperte) {
            result.add(s.getSede() + " - " + s.getData_ora().toString());
        }

        return result;
    }

    public Seduta assemblaSeduta(String sedeData) {
        // Usiamo il separatore robusto definito sopra
        String[] parts = sedeData.split(" - ");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Formato stringa seduta non valido.");
        }

        String sede = parts[0].trim();
        String dataOra = parts[1].trim();

        for (Seduta s : docenteLoggato.getListaSedute()) {
            if (sede.equals(s.getSede()) && dataOra.equals(s.getData_ora().toString())) {
                return s;
            }
        }
        throw new IllegalArgumentException("Seduta inesistente");
    }

    public void confermaSeduta(String sede_data) {
        Seduta seduta = assemblaSeduta(sede_data);
        seduta.setStato(false);
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

        for (Tesi t : this.listaTesi) {

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

        return righeTabella;
    }

    //riceve dalla gui "Gestisci_commissioni" la stringa contenente
    public void confermaSeduta() {

    }

    //endregion


    //region METODI STUDENTE
    public List<String> visualizzaTirociniDisponibili() {
        OperazioniStudenteDAO dao = new OperazioniStudentePostgresDAO();
        //interroga DAO e riceve una lista di Select contenente le info sulle sedute aperte
        List<String> listaTirociniDisponibili = dao.getTirociniDisponibili();
        if (listaTirociniDisponibili == null) {
            return null;
        }
        return listaTirociniDisponibili;
    }

    public void compilaRichiesta(String tirScelto) {

        //formatta la stringa per trovare il codice del tirocinio e creare l'oggetto richiesta
        int idTirocinio = Integer.parseInt(tirScelto.split(":")[0]);
        OperazioniStudentePostgresDAO dao = new OperazioniStudentePostgresDAO();

        dao.compilaRichiesta(idTirocinio, studenteLoggato.getMatricola());

    }

    public void caricaTesi(String sedutaScelta, String titolo, String documento) {
        if ((studenteLoggato.getTesi() != null) && (getStatoTesi(studenteLoggato.getMatricola()).equals(Stato_Tesi.Approvata.toString()) || (getStatoTesi(studenteLoggato.getMatricola()).equals(Stato_Tesi.In_attesa.toString())))) {
            throw new IllegalStateException("ERRORE! Hai già una proposta di tesi attiva.");
        }
        OperazioniStudentePostgresDAO dao = new OperazioniStudentePostgresDAO();
        String relatore = dao.getDocenteRelatore(getMatricola());

        // 1. Ottiene la stringa grezza dal DAO
        int idseduta = Integer.parseInt(sedutaScelta.split(":")[0]);
        dao.caricaTesi(titolo,documento, studenteLoggato.getMatricola(),relatore,idseduta);
    }

    //ritorna una stringa contenente lo stato della RICHIESTA Studente attualmente Loggato
    public String getStatoRichiesta(String matricola) {
        OperazioniStudentePostgresDAO dao = new OperazioniStudentePostgresDAO();

        // 1. Ottiene la stringa grezza dal DAO
        String statoTestuale = dao.getStatoRichiesta(matricola);

        if (statoTestuale == null) {
            return null; // O gestisci l'errore come preferisci
        }
        return statoTestuale;
    }

    //ritorna una stringa contenente lo stato DELLA TESI dello Studente attualmente Loggato
    public String getStatoTesi(String matricola) {
        OperazioniStudentePostgresDAO dao = new OperazioniStudentePostgresDAO();

        // 1. Ottiene la stringa grezza dal DAO
        String statoTestuale = dao.getStatoTesi(matricola);

        if (statoTestuale == null) {
            return null;
        }
        return statoTestuale;
    }

    public String getMatricola(){
        return studenteLoggato.getMatricola();
    }



    public List<String> getSeduteAperte() {
        OperazioniDocenteDAO dao= new OperazioniDocentePostgresDAO();
        //interroga DAO e riceve una lista di Select contenente le info sulle sedute aperte
        List<String> seduteAperte = dao.getSeduteAperte();
        if (seduteAperte == null) {
            return null;
        }
        return seduteAperte;

    }
    //endregion


    //region GETTER STRANI E UTILITA

    // Resitutisce l'oggetto Tirocinio sulla base del suo nome (id)
    public Tirocinio getTirocinioDaId(int id) {
        for (Tirocinio t : listaTirocini) {
            if (t.getid() == id) {return t;}
        }
        throw new IllegalArgumentException("Tirocinio non presente nel sistema");
    }


    public Seduta getSedutadaID(LocalDateTime data) {
        for (Seduta s : listaSedute) {
            if (s.getData_ora().equals(data)) {
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
}