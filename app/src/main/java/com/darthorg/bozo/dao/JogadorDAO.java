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
        values.put("pontuacao", jogador.getPontuacao());
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

        String[] colunas = new String[]{"_id", "nome", "pontuacao", "fk_rodada"};
        Cursor cursor = db.query(DataModel.getTabelaJogadores(), colunas, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Jogador j = new Jogador();
                j.setIdJogador(cursor.getLong(0));
                j.setNome(cursor.getString(1));
                j.setPontuacao(cursor.getInt(2));
                j.setIdRodada(cursor.getLong(3));
                list.add(j);

            } while (cursor.moveToNext());
        }
        return list;
    }

    public Jogador buscarJogadorPorNome(String nome) {

        Jogador jogador = null;

        try {

            String[] colunas = new String[]{"_id", "nome", "pontuacao", "fk_rodada"};
            // Idem a: SELECT _id,nome,cpf,idade from pessoa where nome = ?
            Cursor c = db.query(DataModel.getTabelaJogadores(), colunas, "nome " + "='" + nome + "'", null, null, null, null);

            // Se encontrou...
            if (c.moveToNext()) {

                jogador = new Jogador();
                // utiliza os métodos getLong(), getString(), getInt(), etc para recuperar os valores
                jogador.setIdJogador(c.getLong(0));
                jogador.setNome(c.getString(1));
                jogador.setPontuacao(c.getInt(2));
                jogador.setIdRodada(c.getLong(3));
            }
        } catch (SQLException e) {
            Log.e("bugsinistro", "Erro ao buscar a pessoa pelo nome: " + e.toString());
            return null;
        }
        return jogador;
    }


}
