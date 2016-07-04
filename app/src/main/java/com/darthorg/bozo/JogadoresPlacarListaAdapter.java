package com.darthorg.bozo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wende on 03/07/2016.
 */
public class JogadoresPlacarListaAdapter extends BaseAdapter {

    private Context mContext;
    private List<JogadoresPlacar> mJogadoresPlacar;

    //Construtor


    public JogadoresPlacarListaAdapter(Context mContext, List<JogadoresPlacar> mJogadoresPlacar) {
        this.mContext = mContext;
        this.mJogadoresPlacar = mJogadoresPlacar;
    }

    @Override
    public int getCount() {
        return mJogadoresPlacar.size();
    }

    @Override
    public Object getItem(int position) {
        return mJogadoresPlacar.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = View.inflate(mContext, R.layout.theme_listview_placar, null);
        TextView tvNomeJogador = (TextView)v.findViewById(R.id.txt_lista_nome_jogador);
        TextView tvTxtPlacar = (TextView)v.findViewById(R.id.txt_lista_txt_partidas);
        TextView tvNumeroRodada = (TextView)v.findViewById(R.id.txt_lista_numero_rodada);


        tvNomeJogador.setText(mJogadoresPlacar.get(position).getNomeJogador());
        tvTxtPlacar.setText(mJogadoresPlacar.get(position).getTxtRodada());
        tvNumeroRodada.setText(String.valueOf(mJogadoresPlacar.get(position).getNumeroRodadas()));

        v.setTag(mJogadoresPlacar.get(position).getId());

        return v;
    }
}
