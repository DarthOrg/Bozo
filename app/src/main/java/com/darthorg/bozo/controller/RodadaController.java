package com.darthorg.bozo.controller;

import android.content.Context;

import com.darthorg.bozo.dao.RodadaDAO;
import com.darthorg.bozo.model.Jogador;
import com.darthorg.bozo.model.Rodada;

import java.util.List;

/**
 * Created by Gustavo on 11/07/2016.
 */
public class RodadaController {

    private Context context;
    private RodadaDAO rodadaDAO;
    private JogadorController jogadorController;

    public RodadaController(Context context) {
        this.context = context;
        rodadaDAO = new RodadaDAO(context);
        jogadorController = new JogadorController(context);

    }

    //TODO: Nao deixar inserir rodadas que não tenham partida
    public long inserirRodada(Rodada rodada) {
        if (rodada.getIdPartida() != 0) {

            long idRodada = rodadaDAO.novaRodada(rodada);

            for (int i = 0; i < rodada.getJogadores().size(); i++) {
                rodada.getJogadores().get(i).setIdRodada(idRodada);
                jogadorController.inserirJogador(rodada.getJogadores().get(i));
            }

            return idRodada;
        } else {
            return -1;
        }
    }

    public Rodada buscarRodada(long idPartida) {

        Rodada rodada;

        rodada = rodadaDAO.buscarRodadaPorPartida(idPartida);
        return rodada;
    }

    public List<Jogador> buscarJogadoresRodada(long idRodada) {
        return rodadaDAO.buscarJogadoresRodada(idRodada);
    }
}
