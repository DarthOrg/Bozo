package com.darthorg.bozo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
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

        View view = View.inflate(mContext, R.layout.adapter_valores_pecas, null);

        FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.bgValorPeca);
        TextView txtValores = (TextView) view.findViewById(R.id.tvValorPeca);

        txtValores.setText(String.valueOf(valoresPossiveis.get(position)));

        if (position == 0) {
            frameLayout.setBackground(mContext.getResources().getDrawable(R.color.colorBlackTransparente));
        }

        return view;
    }
}
