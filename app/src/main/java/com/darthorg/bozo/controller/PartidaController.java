package com.darthorg.bozo.controller;

import android.content.Context;

import com.darthorg.bozo.dao.PartidaDAO;
import com.darthorg.bozo.model.Partida;

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
    public boolean inserirPartida(Partida partida) {
        if (partida.getNome() != null) {
            partidaDAO.novaPartida(partida);
            return true;
        } else {
            return false;
        }
    }

}
