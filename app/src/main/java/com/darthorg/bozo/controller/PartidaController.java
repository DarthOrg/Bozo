package com.darthorg.bozo.controller;

import android.content.Context;
import android.util.Log;

import com.darthorg.bozo.dao.PartidaDAO;
import com.darthorg.bozo.model.Jogador;
import com.darthorg.bozo.model.Partida;
import com.darthorg.bozo.model.Rodada;

import java.util.List;

/**
 * Created by Gustavo on 11/07/2016.
 */
public class PartidaController {

    private Context context;
    private PartidaDAO partidaDAO;
    private RodadaController rodadaController;

    public PartidaController(Context context) {
        this.context = context;
        partidaDAO = new PartidaDAO(context);
        rodadaController = new RodadaController(context);

    }

    //TODO: Nao deixar inserir partidas com nomes iguais
    public long inserirPartida(Partida partida) {
        if (partida.getNome() != null) {

            long idPartida = partidaDAO.novaPartida(partida);
            // Insere as Rodadas Junto
            for (int i = 0; i < partida.getRodadas().size(); i++) {
                partida.getRodadas().get(i).setIdPartida(idPartida);
                rodadaController.inserirRodada(partida.getRodadas().get(i));
            }

            return idPartida;
        } else {
            return -1;
        }
    }

    public Partida buscarPartida(long idPartida) {

        Partida partida;
        partida = partidaDAO.buscarPartidaPorId(idPartida);
        partida.setRodadas(buscarRodadasPartida(partida.getIdPartida()));


        Log.i("partida", "nome : " + partida.getNome() + " " + " id : " + partida.getIdPartida());

        return partida;
    }

    public List<Jogador> buscarJogadoresPartida(long idPartida) {
        return partidaDAO.buscarJogadoresPartida(idPartida);
    }

    public List<Rodada> buscarRodadasPartida(long idPartida) {
        return partidaDAO.buscarRodadasPartida(idPartida);
    }

    public boolean deletarPartida(Partida partida) {
        return partidaDAO.deletarPartida(partida);
    }

    public List<Partida> buscarPartidas() {

        List<Partida> partidasBD = partidaDAO.buscarPartidas();

        for (int i = 0; i < partidasBD.size(); i++) {
            partidasBD.get(i).setRodadas(buscarRodadasPartida(partidasBD.get(i).getIdPartida()));
            for (int j = 0; j < partidasBD.get(i).getRodadas().size(); j++) {
                partidasBD.get(i).getRodadas().get(j).setJogadores(rodadaController.buscarJogadoresRodada(partidasBD.get(i).getRodadas().get(j).getIdRodada()));
            }
        }

        return partidasBD;
    }

    public Partida buscarUltimaPartida() {

        Partida ultimaPartida = partidaDAO.buscarUltimaPartida();

        if(ultimaPartida != null ) {

            ultimaPartida.setRodadas(buscarRodadasPartida(ultimaPartida.getIdPartida()));
            for (int j = 0; j < ultimaPartida.getRodadas().size(); j++) {
                ultimaPartida.getRodadas().get(j).setJogadores(rodadaController.buscarJogadoresRodada(ultimaPartida.getRodadas().get(j).getIdRodada()));
            }
        }
        return ultimaPartida;
    }
}
