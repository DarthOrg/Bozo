package com.darthorg.bozo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Gustavo on 01/07/2016.
 */
public class Jogador implements Parcelable, Comparable<Jogador> {
    private String nome;
    private int pontuacao;
    private long idJogador;
    private long idRodada;

    public Jogador() {
    }

    protected Jogador(Parcel in) {
        nome = in.readString();
        pontuacao = in.readInt();
        idJogador = in.readLong();
        idRodada = in.readLong();
    }

    public static final Creator<Jogador> CREATOR = new Creator<Jogador>() {
        @Override
        public Jogador createFromParcel(Parcel in) {
            return new Jogador(in);
        }

        @Override
        public Jogador[] newArray(int size) {
            return new Jogador[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeInt(pontuacao);
        dest.writeLong(idJogador);
        dest.writeLong(idRodada);
    }

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

    @Override
    public int compareTo(Jogador another) {
        if (this.pontuacao > another.pontuacao) {
            return -1;
        }
        if (this.pontuacao < another.pontuacao) {
            return 1;
        }
        return 0;
    }
}
