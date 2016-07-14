package com.darthorg.bozo.controller;

import android.content.Context;

import com.darthorg.bozo.dao.JogadorDAO;
import com.darthorg.bozo.model.Jogador;

/**
 * Created by Gustavo on 11/07/2016.
 */
public class JogadorController {

    private Context context;
    private JogadorDAO jogadorDAO;

    public JogadorController(Context context) {
        this.context = context;
        jogadorDAO = new JogadorDAO(context);
    }

    //Todo: Não poder inserir jogador sem rodada
    public boolean inserirJogador(Jogador jogador) {
        if (jogador.getIdRodada() != 0) {
            jogadorDAO.novoJogador(jogador);
            return true;
        } else {
            return false;
        }
    }
}
