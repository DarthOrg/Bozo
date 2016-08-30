package com.darthorg.bozo.model;

import java.util.List;

/**
 * Created by Gustavo on 01/07/2016.
 */
public class Partida {
    private String nome;
    private long idPartida;
    private List<Rodada> rodadas;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(long idPartida) {
        this.idPartida = idPartida;
    }

    public List<Rodada> getRodadas() {
        return rodadas;
    }

    public void setRodadas(List<Rodada> rodadas) {
        this.rodadas = rodadas;
    }
}
