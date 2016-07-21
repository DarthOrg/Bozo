package com.darthorg.bozo.model;

/**
 * Created by Gustavo on 21/07/2016.
 */
public class PecaBozo {
    private String nome;
    private String pontuacao;
    private boolean riscado;

    public PecaBozo(String nome, String pontuacao, boolean riscado) {
        this.nome = nome;
        this.pontuacao = pontuacao;
        this.riscado = riscado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(String pontuacao) {
        this.pontuacao = pontuacao;
    }

    public boolean isRiscado() {
        return riscado;
    }

    public void setRiscado(boolean riscado) {
        this.riscado = riscado;
    }
}
