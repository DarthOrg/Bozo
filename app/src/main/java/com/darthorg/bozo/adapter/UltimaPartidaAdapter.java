package com.darthorg.bozo.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.darthorg.bozo.R;
import com.darthorg.bozo.model.Partida;
import com.darthorg.bozo.model.Rodada;

import java.util.Collections;

/**
 * Created by wende on 03/07/2016.
 */
public class UltimaPartidaAdapter extends BaseAdapter {

    private Context mContext;
    private Partida ultimaPartida;
    private Activity parentActivity;

    //Construtor


    public UltimaPartidaAdapter(Context mContext, Partida ultimaPartida, Activity parentActivity) {
        this.mContext = mContext;
        this.ultimaPartida = ultimaPartida;
        this.parentActivity = parentActivity;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return ultimaPartida;
    }

    @Override
    public long getItemId(int position) {
        return ultimaPartida.getIdPartida();
    }


    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View v = View.inflate(mContext, R.layout.adapter_ultima_partida, null);
        TextView tvNumero = (TextView) v.findViewById(R.id.txt_lista_numero);
        TextView tvNomePartida = (TextView) v.findViewById(R.id.txt_lista_nome_partida);
        TextView tvJogadoresUltimaRodada = (TextView) v.findViewById(R.id.tvJogadoresUltimaRodada);

        //Busca a ultima rodada
        Rodada ultimaRodada = ultimaPartida.getRodadas().get(ultimaPartida.getRodadas().size() - 1);

        Collections.sort(ultimaRodada.getJogadores());

        // String com onde ficaram os nomes dos jogadoress
        String nomesJogadores = "";

        for (int i = 0; i < ultimaRodada.getJogadores().size(); i++) {
            if (i != ultimaRodada.getJogadores().size() - 1) {
                nomesJogadores += ultimaRodada.getJogadores().get(i).getNome() + " / ";
            } else {
                nomesJogadores += ultimaRodada.getJogadores().get(i).getNome();
            }
        }
        tvJogadoresUltimaRodada.setText(nomesJogadores);


        tvNumero.setText(String.valueOf(position + 1));
        tvNomePartida.setText(ultimaPartida.getNome());

        v.setTag(ultimaPartida.getIdPartida());

        return v;
    }

}
