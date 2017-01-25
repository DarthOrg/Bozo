package com.darthorg.bozo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.darthorg.bozo.R;
import com.darthorg.bozo.controller.PartidaController;
import com.darthorg.bozo.model.Partida;
import com.darthorg.bozo.model.Rodada;

import java.util.Collections;
import java.util.List;

/**
 * Created by wende on 03/07/2016.
 */
public class PartidasListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Partida> mPartidaList;
    private Activity parentActivity;

    //Construtor


    public PartidasListAdapter(Context mContext, List<Partida> mPartidaList, Activity parentActivity) {
        this.mContext = mContext;
        this.mPartidaList = mPartidaList;
        this.parentActivity = parentActivity;
    }

    @Override
    public int getCount() {
        return mPartidaList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPartidaList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        View v = View.inflate(mContext, R.layout.adapter_listview_partidas, null);
        TextView tvNumero = (TextView) v.findViewById(R.id.txt_lista_numero);
        TextView tvNomePartida = (TextView) v.findViewById(R.id.txt_lista_nome_partida);
        Button btnDelPartida = (Button) v.findViewById(R.id.btnDelPartida);
        TextView tvJogadoresUltimaRodada = (TextView) v.findViewById(R.id.tvJogadoresUltimaRodada);

        //Busca a ultima rodada
        Rodada ultimaRodada = mPartidaList.get(position).getRodadas().get(mPartidaList.get(position).getRodadas().size() - 1);

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


        btnDelPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(parentActivity);

                alertDialog.setCancelable(true);
                alertDialog.setTitle(mContext.getString(R.string.apagarMarcador));
                alertDialog.setMessage(mContext.getString(R.string.textoApagarMarcador) +" "+ mPartidaList.get(position).getNome() + " ? ");
                alertDialog.setPositiveButton(mContext.getString(R.string.apagar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        PartidaController partidaController = new PartidaController(mContext);

                        if (partidaController.deletarPartida(mPartidaList.get(position))) {
                            mPartidaList.remove(mPartidaList.get(position));
                            notifyDataSetChanged();
                            Toast.makeText(mContext, mContext.getString(R.string.marcadorApagado), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, mContext.getString(R.string.erroApagarMarcador), Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();

                    }
                });

                alertDialog.setNegativeButton(mContext.getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });


        tvNumero.setText(String.valueOf(position + 1));
        tvNomePartida.setText(mPartidaList.get(position).getNome());

        v.setTag(mPartidaList.get(position).getIdPartida());

        return v;
    }

}
