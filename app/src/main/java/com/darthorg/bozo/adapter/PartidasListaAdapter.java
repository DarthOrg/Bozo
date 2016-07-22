package com.darthorg.bozo.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.darthorg.bozo.R;
import com.darthorg.bozo.dao.PartidaDAO;
import com.darthorg.bozo.model.Partida;

import java.util.List;

/**
 * Created by wende on 03/07/2016.
 */
public class PartidasListaAdapter extends BaseAdapter {

    private Context mContext;
    private List<Partida> mPartidasLista;
    Button btnDeletar;

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
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final View v = View.inflate(mContext, R.layout.adapter_listview_partidas, null);
        TextView tvNumero = (TextView) v.findViewById(R.id.txt_lista_numero);
        TextView tvNomePartida = (TextView) v.findViewById(R.id.txt_lista_nome_partida);




        tvNumero.setText(String.valueOf(position + 1));
        tvNomePartida.setText(mPartidasLista.get(position).getNome());

        v.setTag(mPartidasLista.get(position).getIdPartida());

        return v;
    }

}
