package com.darthorg.bozo.model;

import java.util.ArrayList;

/**
 * Created by Gustavo on 21/07/2016.
 */
public class PecaBozo {
    private String nome;
    private String pontuacao;
    private boolean riscado;
    ArrayList<Integer> valoresPossiveis;


    public PecaBozo(String nome, String pontuacao, boolean riscado) {
        this.nome = nome;
        this.pontuacao = pontuacao;
        this.riscado = riscado;
        valoresPossiveisCadaPeca(nome);
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

    public ArrayList<Integer> getValoresPossiveis() {
        return valoresPossiveis;
    }

    public void valoresPossiveisCadaPeca(String peca) {
        switch (peca) {
            case "Az":
                valoresPossiveis = new ArrayList<Integer>() {{
                    add(0);add(1);add(2);add(3);add(4);add(5);}};
                break;
            case "Duque":
                valoresPossiveis = new ArrayList<Integer>() {{
                    add(0);add(2);add(4);add(6);add(8);add(10);}};
                break;
            case "Terno":
                valoresPossiveis = new ArrayList<Integer>() {{
                    add(0);add(3);add(6);add(9);add(12);add(15);}};
                break;
            case "Quadra":
                valoresPossiveis = new ArrayList<Integer>() {{
                    add(0);add(4);add(8);add(12);add(16);add(20);}};
                break;
            case "Quina":
                valoresPossiveis = new ArrayList<Integer>() {{
                    add(0);add(5);add(10);add(15);add(20);add(25);}};
                break;
            case "Sena":
                valoresPossiveis = new ArrayList<Integer>() {{
                    add(0);add(6);add(12);add(18);add(24);add(30);}};
                break;
            case "Full":
                valoresPossiveis = new ArrayList<Integer>() {{
                    add(0);add(10);add(15);}};
                break;
            case "Seguida":
                valoresPossiveis = new ArrayList<Integer>() {{
                    add(0);add(20);add(25);}};
                break;
            case "Quadrada":
                valoresPossiveis = new ArrayList<Integer>() {{
                    add(0);add(30);add(35);}};
                break;
            case "General":
                valoresPossiveis = new ArrayList<Integer>() {{
                    add(0);add(40);add(100);}};
                break;
        }
    }
}
