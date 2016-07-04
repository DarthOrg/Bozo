package com.darthorg.bozo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.darthorg.bozo.datamodel.DataModel;
import com.darthorg.bozo.datasource.DataSource;
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
    public void novaRodada(Rodada rodada) {
        values = new ContentValues();
        values.put("fk_partida", rodada.getIdPartida());

        db.insert(DataModel.getTabelaRodadas(), null, values);
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


}
