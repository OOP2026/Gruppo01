package controller;
import dao.*;
import implementazioneDao.*;
import model.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/*
ATTENZIONE!!! LA LISTA DI OGGETTI CHE SEGUE È SOLO UN MODO PER GESTIRE MOMENTANEAMENTE (IN QUESTA FASE DEL
PROGETTO IL DIALOGO TRA IL CONTROLLER E IL DATABASE. IN FASI PIù AVANZATE DEL PROGETTO, SI SOSTITUIRÀ TALE
INSIEME DI LISTE CON L'EFFETTIVA RELAZIONE COL DATABASE. LO STESSO VALE PER GLI ADD PRESENTI
NEI METODI DEL CONTROLLER PER LA CREAZIONE DEI VARI OGGETTI.
 */

public class Controller {

   //Il controller tiene traccia delle info sull'user attualmente loggato
    private Docente docenteLoggato = null;
    private Studente studenteLoggato = null;

    //region METODI BASE (HOME E LOGIN)
    public boolean effettuaLoginAdmin(String user, String pwd) {
        AdminDAO adminDAO = new AdminPostgresDAO();

        // Riceve solo i dati grezzi
        List<String> dati = adminDAO.loginAdmin(user, pwd);

        return dati != null && !dati.isEmpty();
    }
    public boolean effettuaLoginStudente(String user, String pwd) {
        StudentePostgresDAO studDAO = new StudentePostgresDAO();

        // Riceve solo i dati grezzi
        List<String> dati = studDAO.loginStudente(user, pwd);

        if (dati == null || dati.isEmpty()) {
            return false;
        } else {
            // Il Controller costruisce l'oggetto estraendo i valori dalla lista in base all'ordine di inserimento

            this.studenteLoggato = new Studente(dati.get(0), dati.get(1), dati.get(2), dati.get(3), user, pwd);
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
            this.docenteLoggato = new Docente(dati.get(0), dati.get(1), dati.get(2), user, pwd);
            if(docDAO.checkIsCoordinatore(docenteLoggato.getUsername()))
                docenteLoggato.setIs_coordinatore(true);
            return true;
        }
    }


    //endregion

    //region METODI ADMIN
    public List<String> getDocNotCoord() {
        DocenteDAO dao= new DocentePostgresDAO();
        return dao.getDocNotCoord();
    }

    public void impostaCoordinatore(String userDoc) {
        DocenteDAO dao= new DocentePostgresDAO();
        dao.impostaCoordinatore(userDoc);
    }


    public void inserisciTirocinio(String argomento, String nome, int ncfu, int durata, LocalDate dataInizio, String tipo,
                                   String azienda, String refAzienda, String dip, String lab, String relatore) {

        // Operatore ternario: se la stringa è vuota assegna null, altrimenti lascia il testo inserito
        String dbAzienda = (azienda != null && !azienda.isEmpty()) ? azienda : null;
        String dbRefAzienda = (refAzienda != null && !refAzienda.isEmpty()) ? refAzienda : null;
        String dbDip = (dip != null && !dip.isEmpty()) ? dip : null;
        String dbLab = (lab != null && !lab.isEmpty()) ? lab : null;

        TirociniDAO dao = new TirociniPostgresDAO();

        // Passa i dati "puliti" al DAO
        boolean successo = dao.registraTirocinio(argomento, nome, ncfu, durata, dataInizio, tipo, dbAzienda, dbRefAzienda, dbDip, dbLab, relatore);

        if (!successo) {
            throw new IllegalArgumentException("Errore durante il salvataggio nel database.");
        }
    }




    //endregion


    //region METODI DEL DOCENTE LOGGATO

    //metodo richiamato da Visualizza_R_Tir, restitituisce una lista di stringhe
    //contenenti le informazioni sui tirocini aperti del docente Loggato
    public List<String> getTirociniAperti() {
        TirociniDAO dao = new TirociniPostgresDAO();
        return dao.getTirociniAperti(docenteLoggato.getUsername());
    }

    //Approva la Richiesta di Tirocinio
    public void approvaRichiestaTirocinio(String matricola) {
       RichiestaDAO dao = new RichiestaPostgresDAO();
       dao.approvaRichiestaTirocinio(matricola);
    }

    // la Richiesta di Tirocinio
    public void rifiutaRichiestaTirocinio(String matricola) {
        RichiestaDAO dao = new RichiestaPostgresDAO();
        dao.rifiutaRichiestaTirocinio(matricola);
    }


    public List<String[]> visualizzaTirocinioStudenti() {
        TirociniDAO dao= new TirociniPostgresDAO();
        //interroga DAO e riceve una lista di Select contenente le info sulle sedute aperte
        return dao.visualizzaTirociniInCorso(docenteLoggato.getUsername());

    }




    public List<String> getArgomentiDocLoggato() {
        ArgomentoDAO dao = new ArgomentoPostgresDAO();
        return dao.getArgomentiDocente(docenteLoggato.getUsername());
    }

    public void aggiungiArgomenti(String s) throws SQLIntegrityConstraintViolationException {
        DocenteDAO docDao= new DocentePostgresDAO();
        ArgomentoDAO argDao = new ArgomentoPostgresDAO();
        String argomento = s.trim().toUpperCase();
        try {
            argDao.aggiungiArgomento(argomento);
            docDao.associaDocenteArgomento(docenteLoggato.getUsername(), s);
            System.out.println("Associazione creata con successo!");


        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Argomento già presente" + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Errore SQL" + e.getMessage());
        }
    }

    public void rimuoviArgomento (String s) {
        ArgomentoDAO dao= new ArgomentoPostgresDAO();
        dao.rimuoviArgomento(s, docenteLoggato.getUsername());
        System.out.println("Associazione creata con successo!");
        docenteLoggato.rimuoviArgomento(s);
    }


    //restituisce la lista delle richieste arrivate per quello specifico tirocinio
    public List<String> getStudentiRichiedenti(int tirocinio) {
        StudenteDAO dao = new StudentePostgresDAO();
        return dao.getStudentiRichiedenti(tirocinio);
    }

    //Il docente approva la Tesi
    public void approvaTesi(int id) {
        TesiDAO dao = new TesiPostgresDao();
        dao.approvaTesi(id);
    }





    //Il docente boccia la Tesi
    public void rifiutaTesi(int id) {
        TesiDAO dao = new TesiPostgresDao();
        dao.rifiutaTesi(id);
    }




    //Il docente riceve i titoli di tutte le tesi a lui associate, assieme all'id
    public List<String> getInfoTesi() {
       TesiDAO dao = new TesiPostgresDao();
       return dao.getInfoTesiDocLoggato(docenteLoggato.getUsername());
    }
//endregion

    //region METODI REGISTRAZIONE
    //Richiamato dalla GUI per la registrazione del Docente
    public boolean registraStudente(String nome, String cognome, String email, String matricola, String username, String password) {
        StudenteDAO dao = new StudentePostgresDAO();
        return dao.registraStudente(nome, cognome, email, matricola, username, password);
    }

    public boolean registraDocente(String nome, String cognome, String email, String username, String password) {

        DocenteDAO dao = new DocentePostgresDAO();

        return dao.registraDocente(nome, cognome, email, username, password);
    }
    //endregion


    //region METODI COORDINATORE
    //Il docente speciale COORDINATORE crea l'oggetto seduta, lo aggiunge alla lista delle sedute del coordinatore
    public void inserisciSeduta(LocalDateTime data_ora, String sede) throws IllegalArgumentException {
        SeduteDAO dao = new SedutePostgresDAO();
        if((LocalDateTime.now(ZoneId.of("Europe/Rome"))).isAfter(data_ora))
            throw new IllegalArgumentException("ERRORE: data non valida");
        if(dao.checkseduta(data_ora,sede))
            dao.creaSeduta(data_ora,sede,docenteLoggato.getUsername());
        else
            throw new IllegalArgumentException("Seduta gia' presente nel sistema");

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


    public LocalDate assemblaData(String giornoStr, String meseStr, String annoStr) {
        try {
            int giorno = Integer.parseInt(giornoStr.trim());
            int mese = Integer.parseInt(meseStr.trim());
            int anno = Integer.parseInt(annoStr.trim());

            return LocalDate.of(anno, mese, giorno);

        } catch (NumberFormatException ex) {
            // L'utente ha inserito lettere o lasciato un campo vuoto
            throw new IllegalArgumentException("Per favore, inserisci solo numeri interi nei campi della data.");
        } catch (java.time.DateTimeException ex) {
            throw new IllegalArgumentException("La data inserita non esiste sul calendario.");
        }
    }

    public List<String[]> getDatiTabellaSeduta(int idSeduta) {
        SeduteDAO dao = new SedutePostgresDAO();

        List<String[]> righeTabella = dao.getInfoSeduta(idSeduta);

        // Se è null, restituisci una lista vuota invece di null
        if (righeTabella == null) {
            return new ArrayList<>();
        }

        return righeTabella;
    }

    //riceve dalla gui "Gestisci_commissioni" la stringa contenente
    public void confermaSeduta(int idSeduta) {
        SeduteDAO dao = new SedutePostgresDAO();
        dao.chiudiSeduta(idSeduta);
    }

    //endregion


    //region METODI STUDENTE
    public List<String> visualizzaTirociniDisponibili() {
        TirociniDAO dao = new TirociniPostgresDAO();
        //interroga DAO e riceve una lista di Select contenente le info sulle sedute aperte
        return dao.getTirociniDisponibili();
    }

    public void compilaRichiesta(String tirScelto) {

        //formatta la stringa per trovare il codice del tirocinio e creare l'oggetto richiesta
        int idTirocinio = Integer.parseInt(tirScelto.split(":")[0]);
        RichiestaDAO dao = new RichiestaPostgresDAO();
        if (("Rifiutata").equals(dao.getStatoRichiesta(getMatricola()))) {
            dao.eliminaRichiesta(getMatricola());
        }
        dao.compilaRichiesta(idTirocinio, studenteLoggato.getMatricola());

    }

    public void caricaTesi(String sedutaScelta, String titolo, String documento) {
        TesiDAO daoTesi = new TesiPostgresDao();
        TirociniDAO daoTirocini = new TirociniPostgresDAO();
        String relatore = daoTirocini.getDocenteRelatore(getMatricola());
        if ("Rifiutata".equals(daoTesi.getStatoTesi(getMatricola()))) {
            daoTesi.eliminaTesi(getMatricola());
        }
        // 1. Ottiene la stringa grezza dal DAO
        int idseduta = Integer.parseInt(sedutaScelta.split(":")[0]);
        daoTesi.caricaTesi(titolo,documento, studenteLoggato.getMatricola(),relatore,idseduta);
    }

    //ritorna una stringa contenente lo stato della RICHIESTA Studente attualmente Loggato
    public String getStatoRichiesta(String matricola) {
        RichiestaDAO dao = new RichiestaPostgresDAO();
        return dao.getStatoRichiesta(matricola);
    }

    //ritorna una stringa contenente lo stato DELLA TESI dello Studente attualmente Loggato
    public String getStatoTesi(String matricola) {
        TesiDAO dao = new TesiPostgresDao();

        return dao.getStatoTesi(matricola);
    }

    public String getMatricola(){
        return studenteLoggato.getMatricola();
    }



    public List<String> getSeduteAperte() {
        SeduteDAO dao= new SedutePostgresDAO();
        //interroga DAO e riceve una lista di Select contenente le info sulle sedute aperte
        return dao.getSeduteAperte();

    }
    //endregion


    //region UTILITY

    public int getIdDaStringa(String s) {
        String result = s.trim().split(":")[0];
        return Integer.parseInt(result);
    }

    public Docente getdocLoggato() {
        return docenteLoggato;
    }

    public Studente getstudLoggato() {
        return studenteLoggato;
    }


    //endregion
}