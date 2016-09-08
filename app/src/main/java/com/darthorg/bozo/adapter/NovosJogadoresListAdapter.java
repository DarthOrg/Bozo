package com.darthorg.bozo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.darthorg.bozo.R;

import java.util.ArrayList;

/**
 * Created by Gustavo on 08/09/2016.
 */
public class NovosJogadoresListAdapter extends BaseAdapter {
    private ArrayList<String> mJogadores;
    private Context mContext;

    public NovosJogadoresListAdapter(ArrayList<String> mJogadores, Context mContext) {
        this.mJogadores = mJogadores;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mJogadores.size();
    }

    @Override
    public Object getItem(int position) {
        return mJogadores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.adapter_novos_jogadores, null);

        TextView txtNovoJogador = (TextView) v.findViewById(R.id.txtNovoJogador);
        ImageButton btnExcluirJogador = (ImageButton) v.findViewById(R.id.btnDelNovoJogador);

        txtNovoJogador.setText(mJogadores.get(position));
        btnExcluirJogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJogadores.remove(mJogadores.get(position));
                notifyDataSetChanged();
            }
        });


        return v;
    }
}
