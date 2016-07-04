package com.darthorg.bozo;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wende on 03/07/2016.
 */
public class PartidasListaAdapter extends BaseAdapter {

    private Context mContext;
    private List<Partidas> mPartidasLista;

    //Construtor


    public PartidasListaAdapter(Context mContext, List<Partidas> mPartidasLista) {
        this.mContext = mContext;
        this.mPartidasLista = mPartidasLista;
    }

    @Override
    public int getCount() {
        return mPartidasLista.size();
    }

    @Override
    public Object getItem(int position) {
        return mPartidasLista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = View.inflate(mContext, R.layout.theme_listview_partidas, null);
        TextView tvNumero = (TextView)v.findViewById(R.id.txt_lista_numero);
        TextView tvNomePartida = (TextView)v.findViewById(R.id.txt_lista_nome_partida);
        TextView tvNomeJogadores = (TextView)v.findViewById(R.id.txt_lista_nome_jogadores);

        tvNumero.setText(String.valueOf(mPartidasLista.get(position).getNumero()));
        tvNomePartida.setText(mPartidasLista.get(position).getNomePartida());
        tvNomeJogadores.setText(mPartidasLista.get(position).getNomeJogadores());

        v.setTag(mPartidasLista.get(position).getId());

        return v;
    }
}
