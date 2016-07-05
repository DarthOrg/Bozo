package com.darthorg.bozo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.darthorg.bozo.R;
import com.darthorg.bozo.model.Partida;

import java.util.List;

/**
 * Created by wende on 03/07/2016.
 */
public class PartidasListaAdapter extends BaseAdapter {

    private Context mContext;
    private List<Partida> mPartidasLista;

    //Construtor


    public PartidasListaAdapter(Context mContext, List<Partida> mPartidasLista) {
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

        tvNumero.setText(String.valueOf(mPartidasLista.get(position).getIdPartida()));
        tvNomePartida.setText(mPartidasLista.get(position).getNome());
        //tvNomeJogadores.setText(mPartidasLista.get(position).getNomeJogadores());

        //v.setTag(mPartidasLista.get(position).getId());

        return v;
    }
}
