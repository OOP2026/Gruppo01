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


    public boolean login(String username, String password) {
        return ( username.equals(this.username) && password.equals(this.username));
    }




}
