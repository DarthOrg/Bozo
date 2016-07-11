package com.darthorg.bozo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
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
        View v = View.inflate(mContext, R.layout.theme_listview_partidas, null);
        TextView tvNumero = (TextView) v.findViewById(R.id.txt_lista_numero);
        TextView tvNomePartida = (TextView) v.findViewById(R.id.txt_lista_nome_partida);

        Button btn = (Button) v.findViewById(R.id.btnDeletarPartida);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Deletou", Toast.LENGTH_SHORT).show();
                PartidaDAO partidaDAO = new PartidaDAO(mContext);
                partidaDAO.deletarPartida(mPartidasLista.get(position));
                mPartidasLista.remove(position);
                notifyDataSetChanged();
            }
        });

        tvNumero.setText(String.valueOf(position + 1));
        tvNomePartida.setText(mPartidasLista.get(position).getNome());

        v.setTag(mPartidasLista.get(position).getIdPartida());

        return v;
    }
}
