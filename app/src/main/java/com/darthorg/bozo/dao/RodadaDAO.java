package com.darthorg.bozo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.darthorg.bozo.datamodel.DataModel;
import com.darthorg.bozo.datasource.DataSource;
import com.darthorg.bozo.model.Jogador;
import com.darthorg.bozo.model.Rodada;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gustavo on 04/07/2016.
 */
public class RodadaDAO {

    private SQLiteDatabase db;
    DataSource dataSource;
    ContentValues values;

    public RodadaDAO(Context context) {
        dataSource = new DataSource(context);
        db = dataSource.getWritableDatabase();
    }

    /**
     * Para inserir uma Rodada é preciso um id da partida
     *
     * @param rodada Objeto do tipo rodada.
     */
    public long novaRodada(Rodada rodada) {
        values = new ContentValues();
        values.put("vencedor", rodada.getNomeVencedor());
        values.put("fk_partida", rodada.getIdPartida());


        long idRodadaInserida = db.insert(DataModel.getTabelaRodadas(), null, values);

        return idRodadaInserida;
    }

    public boolean deletarRodada(Rodada rodada) {
        if (db.delete(DataModel.getTabelaRodadas(), DataModel.getID() + " = " + rodada.getIdRodada(), null) != 0) {
            return true;
        } else {
            return false;
        }
    }

    public void inserirVencedorRodada(Rodada rodada) {
        values = new ContentValues();
        values.put("vencedor", rodada.getNomeVencedor());

        db.update(DataModel.getTabelaRodadas(), values, DataModel.getID() + " = " + rodada.getIdRodada(), null);
    }

    public List<Rodada> buscarRodadas() {
        List<Rodada> list = new ArrayList<Rodada>();

        String[] colunas = new String[]{"_id", "vencedor", "fk_partida"};
        Cursor cursor = db.query(DataModel.getTabelaRodadas(), colunas, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Rodada r = new Rodada();
                r.setIdRodada(cursor.getLong(0));
                r.setNomeVencedor(cursor.getString(1));
                r.setIdPartida(cursor.getLong(2));
                list.add(r);

            } while (cursor.moveToNext());
        }
        return list;
    }

    public Rodada buscarRodadaPorPartida(long idPartida) {

        Rodada rodada = null;

        try {

            String[] colunas = new String[]{"_id", "vencedor", "fk_partida"};
            // Idem a: SELECT _id,nome,cpf,idade from pessoa where nome = ?
            Cursor c = db.query(DataModel.getTabelaRodadas(), colunas, "fk_partida " + "='" + idPartida + "'", null, null, null, null);

            // Se encontrou...
            if (c.moveToNext()) {

                rodada = new Rodada();
                // utiliza os métodos getLong(), getString(), getInt(), etc para recuperar os valores
                rodada.setIdRodada(c.getLong(0));
                rodada.setNomeVencedor(c.getString(1));
                rodada.setIdPartida(c.getLong(2));
            }
        } catch (SQLException e) {
            Log.e("bugsinistro", "Erro ao buscar a pessoa pelo nome: " + e.toString());
            return null;
        }

        return rodada;
    }

    /**
     * Traz os Jogadores que estao na Rodada
     *
     * @param idRodada
     * @return lista de Jogadores
     */
    public List<Jogador> buscarJogadoresRodada(long idRodada) {
        List<Jogador> jogadorList = new ArrayList<Jogador>();

        String sql = "SELECT " + DataModel.getTabelaJogadores() + "._id ," + DataModel.getTabelaJogadores() + ".nome ," + DataModel.getTabelaJogadores() + ".pontuacao , " + DataModel.getTabelaJogadores() + ".fk_rodada ";
        sql += " FROM " + DataModel.getTabelaPartidas() + " JOIN " + DataModel.getTabelaRodadas() + " ON " + DataModel.getTabelaRodadas() + ".fk_partida = " + DataModel.getTabelaPartidas() + "._id ";
        sql += " JOIN " + DataModel.getTabelaJogadores() + " ON " + DataModel.getTabelaJogadores() + ".fk_rodada = " + DataModel.getTabelaRodadas() + "._id ";
        sql += " WHERE " + DataModel.getTabelaRodadas() + "._id = " + idRodada + " ;";

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                Jogador j = new Jogador();
                j.setIdJogador(cursor.getLong(0));
                j.setNome(cursor.getString(1));
                j.setPontuacao(cursor.getInt(2));
                j.setIdRodada(cursor.getLong(3));

                jogadorList.add(j);
            } while (cursor.moveToNext());
        }

        return jogadorList;
    }


}
