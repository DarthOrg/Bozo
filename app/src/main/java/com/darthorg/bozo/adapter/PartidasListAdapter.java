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

        btnDelPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(parentActivity);

                alertDialog.setCancelable(true);
                alertDialog.setTitle("Deletar Partida");
                alertDialog.setMessage("Você deseja realmente deletar partida " + mPartidaList.get(position).getNome() + " ? ");
                alertDialog.setPositiveButton(" Deletar ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        PartidaController partidaController = new PartidaController(mContext);
                        if (partidaController.deletarPartida(mPartidaList.get(position))) {
                            mPartidaList.remove(mPartidaList.get(position));
                            notifyDataSetChanged();
                            Toast.makeText(mContext, "Partida deletada!", Toast.LENGTH_SHORT);
                        } else {
                            Toast.makeText(mContext, "Ocorreu um erro ao deletar a partida", Toast.LENGTH_SHORT);
                        }
                        dialog.dismiss();

                    }
                });

                alertDialog.setNegativeButton(" Cancelar ", new DialogInterface.OnClickListener() {
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
