package com.darthorg.bozo.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.darthorg.bozo.R;
import com.darthorg.bozo.dao.PartidaDAO;
import com.darthorg.bozo.fragment.FragmentFilho;
import com.darthorg.bozo.model.Jogador;
import com.darthorg.bozo.model.Partida;
import com.darthorg.bozo.view.Inicio;
import com.darthorg.bozo.view.ListaDePartidas;
import com.darthorg.bozo.view.NovaPartida;

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
        final View v = View.inflate(mContext, R.layout.theme_listview_partidas, null);
        TextView tvNumero = (TextView) v.findViewById(R.id.txt_lista_numero);
        TextView tvNomePartida = (TextView) v.findViewById(R.id.txt_lista_nome_partida);





        //Todo:Corrigir erro não está aparecendo AlertDialog
        //Botão de Excluir
        btnDeletar = (Button) v.findViewById(R.id.btnDeletarPartida);
        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Excluir");
                builder.setMessage("Tem certeza que deseja excluir a partida?");
                builder.setIcon(R.drawable.ic_delete);
                builder.setNegativeButton("Não",null);
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(mContext, "Partida deletada", Toast.LENGTH_SHORT).show();
                        PartidaDAO partidaDAO = new PartidaDAO(mContext);
                        partidaDAO.deletarPartida(mPartidasLista.get(position));
                        mPartidasLista.remove(position);
                        notifyDataSetChanged();
                    }
                });
                builder.show();
            }
        });

        tvNumero.setText(String.valueOf(position + 1));
        tvNomePartida.setText(mPartidasLista.get(position).getNome());

        v.setTag(mPartidasLista.get(position).getIdPartida());

        return v;
    }
}
