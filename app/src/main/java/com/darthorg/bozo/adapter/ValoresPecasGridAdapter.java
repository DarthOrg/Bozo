package com.darthorg.bozo.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.darthorg.bozo.R;

import java.util.ArrayList;

/**
 * Created by Gustavo on 12/09/2016.
 */
public class ValoresPecasGridAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Integer> valoresPossiveis;

    public ValoresPecasGridAdapter(Context mContext, ArrayList<Integer> valoresPossiveis) {
        this.mContext = mContext;
        this.valoresPossiveis = valoresPossiveis;
    }

    @Override
    public int getCount() {
        return valoresPossiveis.size();
    }

    @Override
    public Object getItem(int position) {
        return valoresPossiveis.get(position);
    }

    @Override
    public long getItemId(int position) {
        return valoresPossiveis.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView txtValores;

        if (convertView == null) {
            txtValores = new TextView(mContext);
            txtValores.setLayoutParams(new GridView.LayoutParams(60, 60));
            txtValores.setPadding(6, 6, 6, 6);
            txtValores.setBackground(mContext.getResources().getDrawable(R.drawable.circulo_accent));
            txtValores.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
            txtValores.setGravity(Gravity.CENTER);
            txtValores.setTextSize(20);
            if (position == 0) {
                txtValores.setBackground(mContext.getResources().getDrawable(R.drawable.circulo_black_transparente));
            }
        } else {
            txtValores = (TextView) convertView;
        }

        txtValores.setText(String.valueOf(valoresPossiveis.get(position)));

        return txtValores;
    }
}
