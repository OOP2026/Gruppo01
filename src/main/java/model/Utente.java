package model;

import java.util.ArrayList;

public abstract class Utente {
    private String username;
    private String password;
    private String email;
    private String nome;
    private String cognome;


    public Utente(String nome, String cognome, String email, String password, String username) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
    }


    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public boolean login(String user, String password) {
        return ( (user.equals(this.username) || user.equals(this.email)) && password.equals(this.password));
    }
    
}
