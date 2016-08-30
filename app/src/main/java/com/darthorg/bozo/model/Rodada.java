package com.darthorg.bozo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gustavo on 01/07/2016.
 */
public class Rodada implements Parcelable {
    private long idRodada;
    private long idPartida;
    private String nomeVencedor;
    private List<Jogador> jogadores;

    public Rodada() {
    }


    // Implementação parcelable
    protected Rodada(Parcel in) {
        idRodada = in.readLong();
        idPartida = in.readLong();
        nomeVencedor = in.readString();
        jogadores = new ArrayList<Jogador>();
        in.readList(this.jogadores, Jogador.class.getClassLoader());
    }

    public static final Creator<Rodada> CREATOR = new Creator<Rodada>() {
        @Override
        public Rodada createFromParcel(Parcel in) {
            return new Rodada(in);
        }

        @Override
        public Rodada[] newArray(int size) {
            return new Rodada[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(idRodada);
        dest.writeLong(idPartida);
        dest.writeString(nomeVencedor);
        dest.writeList(jogadores);
    }

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

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }
}
