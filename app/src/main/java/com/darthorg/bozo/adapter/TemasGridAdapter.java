package com.darthorg.bozo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;

import com.darthorg.bozo.R;
import com.darthorg.bozo.model.TemaCopo;

import java.util.List;

/**
 * Created by gusta on 26/02/2017.
 */
public class TemasGridAdapter extends BaseAdapter {

    private Context mContext;

    // icone , backgroundIcone , copo
    private List<TemaCopo> temas;

    public TemasGridAdapter(Context mContext, List<TemaCopo> temas) {
        this.mContext = mContext;
        this.temas = temas;
    }

    @Override
    public int getCount() {
        return temas.size();
    }

    @Override
    public Object getItem(int i) {
        return temas.get(i).getImagemCopo();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = view.inflate(mContext, R.layout.adapter_tema, null);

        ImageButton imgButtonTema = (ImageButton) v.findViewById(R.id.imageButtonTema);
        imgButtonTema.setFocusable(false);
        imgButtonTema.setClickable(false);
        imgButtonTema.setFocusableInTouchMode(false);

        //Se o icone tiver bacground
        if (temas.get(i).getBackgroundIcone() != 0) {
            imgButtonTema.setImageResource(temas.get(i).getIcone());
            imgButtonTema.setBackgroundResource(temas.get(i).getBackgroundIcone());
        } else
            imgButtonTema.setBackgroundResource(temas.get(i).getIcone());

        return v;
    }
}
