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
import com.darthorg.bozo.model.Partida;
import com.darthorg.bozo.model.Rodada;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gustavo on 04/07/2016.
 */
public class PartidaDAO {

    private SQLiteDatabase db;
    DataSource dataSource;
    ContentValues values;

    public PartidaDAO(Context context) {
        dataSource = new DataSource(context);
        db = dataSource.getWritableDatabase();
    }

    public long novaPartida(Partida partida) {
        values = new ContentValues();
        values.put("nome", partida.getNome());

        long idPartidaInserida = db.insert(DataModel.getTabelaPartidas(), null, values);

        return idPartidaInserida;
    }

    public void atualizarNomePartida(Partida partida) {
        values = new ContentValues();
        values.put("nome", partida.getNome());

        db.update(DataModel.getTabelaPartidas(), values, DataModel.getID() + " = " + partida.getIdPartida(), null);
    }

    public boolean deletarPartida(Partida partida) {
        if (db.delete(DataModel.getTabelaPartidas(), DataModel.getID() + " = " + partida.getIdPartida(), null) != 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<Partida> buscarPartidas() {
        List<Partida> list = new ArrayList<Partida>();

        String[] colunas = new String[]{"_id", "nome"};
        Cursor cursor = db.query(DataModel.getTabelaPartidas(), colunas, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Partida p = new Partida();
                p.setIdPartida(cursor.getLong(0));
                p.setNome(cursor.getString(1));
                list.add(p);

            } while (cursor.moveToNext());
        }
        return list;
    }

    public Partida buscarPartidaPorNome(String nome) {

        Partida partida = null;

        try {

            String[] colunas = new String[]{"_id", "nome"};
            // Idem a: SELECT _id,nome,cpf,idade from pessoa where nome = ?
            Cursor c = db.query(DataModel.getTabelaPartidas(), colunas, "nome " + "='" + nome + "'", null, null, null, null);

            // Se encontrou...
            if (c.moveToNext()) {

                partida = new Partida();
                // utiliza os métodos getLong(), getString(), getInt(), etc para recuperar os valores
                partida.setIdPartida(c.getLong(0));
                partida.setNome(c.getString(1));
            }
        } catch (SQLException e) {
            Log.e("bugsinistro", "Erro ao buscar a pessoa pelo nome: " + e.toString());
            return null;
        }

        return partida;
    }

    public Partida buscarPartidaPorId(long idPartida) {

        Partida partida = null;

        try {

            String[] colunas = new String[]{"_id", "nome"};
            // Idem a: SELECT _id,nome,cpf,idade from pessoa where nome = ?
            Cursor c = db.query(DataModel.getTabelaPartidas(), colunas, "_id " + "='" + idPartida + "'", null, null, null, null);

            // Se encontrou...
            if (c.moveToNext()) {

                partida = new Partida();
                // utiliza os métodos getLong(), getString(), getInt(), etc para recuperar os valores
                partida.setIdPartida(c.getLong(0));
                partida.setNome(c.getString(1));
            }
        } catch (SQLException e) {
            Log.e("bugsinistro", "Erro ao buscar a pessoa pelo id: " + e.toString());
            return null;
        }

        return partida;
    }

    public Partida buscarUltimaPartida() {

        Partida partida = null;

        String[] colunas = new String[]{"_id", "nome"};
        // DataModel.getTabelaPartidas(), colunas, null, null, null, null, "_id DESC", "1"
        Cursor cursor = db.query(DataModel.getTabelaPartidas(), colunas, null, null, null, null, " _id DESC ", "1");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Partida p = new Partida();
                p.setIdPartida(cursor.getLong(0));
                p.setNome(cursor.getString(1));
                partida = p ;

            } while (cursor.moveToNext());
        }
        return partida;
    }


    /**
     * Método que busca os Jogadores de acordo com o id da partida
     *
     * @param idPartida
     * @return lista de objetos Jogadores
     */
    public List<Jogador> buscarJogadoresPartida(long idPartida) {
        List<Jogador> jogadorList = new ArrayList<Jogador>();

        String sql = "SELECT " + DataModel.getTabelaJogadores() + "._id ," + DataModel.getTabelaJogadores() + ".nome ," + DataModel.getTabelaJogadores() + ".pontuacao , " + DataModel.getTabelaJogadores() + ".fk_rodada ";
        sql += " FROM " + DataModel.getTabelaPartidas() + " JOIN " + DataModel.getTabelaRodadas() + " ON " + DataModel.getTabelaRodadas() + ".fk_partida = " + DataModel.getTabelaPartidas() + "._id ";
        sql += " JOIN " + DataModel.getTabelaJogadores() + " ON " + DataModel.getTabelaJogadores() + ".fk_rodada = " + DataModel.getTabelaRodadas() + "._id ";
        sql += " WHERE " + DataModel.getTabelaPartidas() + "._id = " + idPartida + " ;";

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
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

    public List<Rodada> buscarRodadasPartida(long idPartida) {

        List<Rodada> rodadaList = new ArrayList<Rodada>();


        String sql = "SELECT * ";
        sql += " FROM " + DataModel.getTabelaRodadas() + " JOIN " + DataModel.getTabelaPartidas() + " ON " + DataModel.getTabelaRodadas() + ".fk_partida = " + DataModel.getTabelaPartidas() + "._id ";
        sql += " WHERE " + DataModel.getTabelaPartidas() + "._id = " + idPartida + " ;";

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {

                Rodada r = new Rodada();
                r.setIdRodada(cursor.getLong(0));
                r.setNomeVencedor(cursor.getString(1));
                r.setIdPartida(cursor.getLong(2));

                rodadaList.add(r);
            } while (cursor.moveToNext());
        }

        return rodadaList;
    }

}
