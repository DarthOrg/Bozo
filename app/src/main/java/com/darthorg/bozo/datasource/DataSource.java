package com.darthorg.bozo.datasource;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.darthorg.bozo.datamodel.DataModel;

/**
 * Created by Gustavo on 04/07/2016.
 */
public class DataSource extends SQLiteOpenHelper {

    private static final int VERSION_DB = 1;

    public DataSource(Context ctx) {
        super(ctx, DataModel.getDbName(), null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataModel.criarTabelaPartidas());
        db.execSQL(DataModel.criarTabelaRodadas());
        db.execSQL(DataModel.criarTabelaJogadores());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + DataModel.getTabelaPartidas());
        db.execSQL("drop table " + DataModel.getTabelaRodadas());
        db.execSQL("drop table " + DataModel.getTabelaJogadores());

        onCreate(db);
    }
}
