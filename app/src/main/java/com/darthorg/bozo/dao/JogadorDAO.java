package com.darthorg.bozo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.darthorg.bozo.datamodel.DataModel;
import com.darthorg.bozo.datasource.DataSource;
import com.darthorg.bozo.model.Jogador;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gustavo on 04/07/2016.
 */
public class JogadorDAO {

    private SQLiteDatabase db;
    DataSource dataSource;
    ContentValues values;

    public JogadorDAO(Context context) {
        dataSource = new DataSource(context);
        db = dataSource.getWritableDatabase();
    }

    /**
     * Para inserir um jogador é necessario um id da rodada em que ele esta jogando.
     *
     * @param jogador objeto do tipo jogador.
     */
    public void novoJogador(Jogador jogador) {
        values = new ContentValues();
        values.put("nome", jogador.getNome());
        values.put("fk_rodada", jogador.getIdRodada());

        db.insert(DataModel.getTabelaJogadores(), null, values);
    }

    public void atualizaNomeJogador(Jogador jogador) {
        ContentValues values = new ContentValues();
        values.put("nome", jogador.getNome());

        db.update(DataModel.getTabelaJogadores(), values, DataModel.getID() + " = " + jogador.getIdJogador(), null);
    }

    public void inserirPontuacaoJogador(Jogador jogador) {
        ContentValues values = new ContentValues();
        values.put("pontuacao", jogador.getPontuacao());

        db.update(DataModel.getTabelaJogadores(), values, DataModel.getID() + " = " + jogador.getIdJogador(), null);
    }

    public void removerJogador(Jogador jogador) {
        db.delete(DataModel.getTabelaJogadores(), DataModel.getID() + " = " + jogador.getIdJogador(), null);
    }

    public List<Jogador> buscarJogadores() {
        List<Jogador> list = new ArrayList<Jogador>();

        String[] colunas = new String[]{"_id", "nome", "fk_rodada"};
        Cursor cursor = db.query("partida", colunas, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Jogador j = new Jogador();
                j.setIdJogador(cursor.getLong(0));
                j.setNome(cursor.getString(1));
                j.setIdRodada(cursor.getLong(2));
                list.add(j);

            } while (cursor.moveToNext());
        }
        return list;
    }


}
