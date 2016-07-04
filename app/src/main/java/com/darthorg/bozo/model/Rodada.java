package com.darthorg.bozo.model;

/**
 * Created by Gustavo on 01/07/2016.
 */
public class Rodada {
    private long idRodada;
    private long idPartida;
    private String nomeVencedor;

    public String getNomeVencedor() {
        return nomeVencedor;
    }

    public void setNomeVencedor(String nomeVencedor) {
        this.nomeVencedor = nomeVencedor;
    }

    public long getIdRodada() {
        return idRodada;
    }

    public void setIdRodada(long idRodada) {
        this.idRodada = idRodada;
    }

    public long getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(long idPartida) {
        this.idPartida = idPartida;
    }
}
