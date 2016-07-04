package com.darthorg.bozo.view;

/**
 * Created by wende on 03/07/2016.
 */
public class JogadoresPlacar {

    private int id;
    private String nomeJogador;
    private String txtRodada;
    private String numeroRodadas;

    //Construtor


    public JogadoresPlacar(int id, String nomeJogador, String txtRodada, String numeroRodadas) {
        this.id = id;
        this.nomeJogador = nomeJogador;
        this.txtRodada = txtRodada;
        this.numeroRodadas = numeroRodadas;
    }

    //Setter e getter


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeJogador() {
        return nomeJogador;
    }

    public void setNomeJogador(String nomeJogador) {
        this.nomeJogador = nomeJogador;
    }

    public String getTxtRodada() {
        return txtRodada;
    }

    public void setTxtRodada(String txtRodada) {
        this.txtRodada = txtRodada;
    }

    public String getNumeroRodadas() {
        return numeroRodadas;
    }

    public void setNumeroRodadas(String numeroRodadas) {
        this.numeroRodadas = numeroRodadas;
    }
}
