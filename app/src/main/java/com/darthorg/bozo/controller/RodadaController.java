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
    public boolean inserirRodada(Rodada rodada) {
        if (rodada.getIdPartida() != 0) {
            rodadaDAO.novaRodada(rodada);
            return true;
        } else {
            return false;
        }
    }
}
