package com.darthorg.bozo.controller;

import android.content.Context;
import android.util.Log;

import com.darthorg.bozo.dao.PartidaDAO;
import com.darthorg.bozo.model.Jogador;
import com.darthorg.bozo.model.Partida;

import java.util.List;

/**
 * Created by Gustavo on 11/07/2016.
 */
public class PartidaController {

    private Context context;
    private PartidaDAO partidaDAO;

    public PartidaController(Context context) {
        this.context = context;
        partidaDAO = new PartidaDAO(context);

    }

    //TODO: Nao deixar inserir partidas com nomes iguais
    public long inserirPartida(Partida partida) {
        if (partida.getNome() != null) {
            return partidaDAO.novaPartida(partida);
        } else {
            return -1;
        }
    }

    public Partida buscarPartida(long idPartida) {

        Partida partidaAtual;
        partidaAtual = partidaDAO.buscarPartidaPorId(idPartida);

        Log.i("partidaAtual", "nome : " + partidaAtual.getNome() + " " + " id : " + partidaAtual.getIdPartida());

        return partidaAtual;
    }

    public List<Jogador> buscarJogadoresPartida(long idPartida){
        return partidaDAO.buscarJogadoresPartida(idPartida);
    }

}
