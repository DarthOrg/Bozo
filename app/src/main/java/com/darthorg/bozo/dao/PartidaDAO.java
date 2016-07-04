package com.darthorg.bozo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.darthorg.bozo.datamodel.DataModel;
import com.darthorg.bozo.datasource.DataSource;
import com.darthorg.bozo.model.Partida;

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

    public void novaPartida(Partida partida) {
        values = new ContentValues();
        values.put("nome", partida.getNome());

        db.insert(DataModel.getTabelaPartidas(), null, values);
    }

    public void atualizarNomePartida(Partida partida) {
        values = new ContentValues();
        values.put("nome", partida.getNome());

        db.update(DataModel.getTabelaPartidas(), values, DataModel.getID() + " = " + partida.getIdPartida(), null);
    }

    public void deletarPartida(Partida partida) {
        db.delete(DataModel.getTabelaPartidas(), DataModel.getID() + " = " + partida.getIdPartida(), null);
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

}
