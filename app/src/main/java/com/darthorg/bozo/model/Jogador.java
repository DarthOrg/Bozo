package com.darthorg.bozo.model;

/**
 * Created by Gustavo on 01/07/2016.
 */
public class Jogador {
    private String nome;
    private int pontuacao;
    private long idJogador;
    private long idRodada;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int pontuacao) {
        this.pontuacao = pontuacao;
    }

    public long getIdJogador() {
        return idJogador;
    }

    public void setIdJogador(long idJogador) {
        this.idJogador = idJogador;
    }

    public long getIdRodada() {
        return idRodada;
    }

    public void setIdRodada(long idRodada) {
        this.idRodada = idRodada;
    }
}
