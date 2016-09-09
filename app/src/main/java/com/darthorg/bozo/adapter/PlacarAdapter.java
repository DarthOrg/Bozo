package com.darthorg.bozo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.darthorg.bozo.R;
import com.darthorg.bozo.model.Rodada;

import java.util.ArrayList;

/**
 * Created by wende on 03/07/2016.
 */
public class PlacarAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Rodada> mRodadas;

    //Construtor


    public PlacarAdapter(Context mContext, ArrayList<Rodada> mRodadas) {
        this.mContext = mContext;
        this.mRodadas = mRodadas;
    }

    @Override
    public int getCount() {
        return mRodadas.size();
    }

    @Override
    public Object getItem(int position) {
        return mRodadas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mRodadas.get(position).getIdRodada();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View v = View.inflate(mContext, R.layout.adapter_listview_placar, null);

        Button tvNumRodada = (Button) v.findViewById(R.id.tvNumRodada);
        TextView tvVencedorRodada = (TextView) v.findViewById(R.id.tvVencedorRodada);
        TextView btnPontuacao = (TextView) v.findViewById(R.id.btnPontuacao);

        tvNumRodada.setText(String.valueOf(position + 1));
        tvVencedorRodada.setText(mRodadas.get(position).getNomeVencedor());

        // GAMBI NERVOSA KKK
        for (int i = 0; i < mRodadas.get(position).getJogadores().size(); i++) {
            if (mRodadas.get(position).getJogadores().get(i).getNome().equals(mRodadas.get(position).getNomeVencedor())) {
                btnPontuacao.setText(String.valueOf(mRodadas.get(position).getJogadores().get(i).getPontuacao()));
            }
        }

        return v;
    }
}
