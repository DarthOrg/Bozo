package com.darthorg.bozo;

/**
 * Created by wende on 03/07/2016.
 */
public class Partidas {

    private int id;
    private String numero;
    private String nomePartida;
    private String nomeJogadores;

    //Construtor


    public Partidas(int id, String numero, String nomePartida, String nomeJogadores) {
        this.id = id;
        this.numero = numero;
        this.nomePartida = nomePartida;
        this.nomeJogadores = nomeJogadores;
    }

    //Setter e getter


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNomePartida() {
        return nomePartida;
    }

    public void setNomePartida(String nomePartida) {
        this.nomePartida = nomePartida;
    }

    public String getNomeJogadores() {
        return nomeJogadores;
    }

    public void setNomeJogadores(String nomeJogadores) {
        this.nomeJogadores = nomeJogadores;
    }
}
