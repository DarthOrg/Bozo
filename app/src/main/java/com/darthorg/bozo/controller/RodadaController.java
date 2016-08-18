package com.darthorg.bozo.controller;

import android.content.Context;

import com.darthorg.bozo.dao.RodadaDAO;
import com.darthorg.bozo.model.Rodada;

/**
 * Created by Gustavo on 11/07/2016.
 */
public class RodadaController {

    private Context context;
    private RodadaDAO rodadaDAO;

    public RodadaController(Context context) {
        this.context = context;
        rodadaDAO = new RodadaDAO(context);

    }

    //TODO: Nao deixar inserir rodadas que não tenham partida
    public long inserirRodada(Rodada rodada) {
        if (rodada.getIdPartida() != 0) {
            return rodadaDAO.novaRodada(rodada);
        } else {
            return -1;
        }
    }

    public Rodada buscarRodada(long idPartida) {

        Rodada rodada;

        rodada = rodadaDAO.buscarRodadaPorPartida(idPartida);
        return rodada;
    }
}
