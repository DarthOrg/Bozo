package com.darthorg.bozo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import com.darthorg.bozo.R;
import com.darthorg.bozo.adapter.TemasGridAdapter;
import com.darthorg.bozo.model.TemaCopo;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.darthorg.bozo.view.Definicoes.PREF_CONFIG;

/**
 * Created by gusta on 26/02/2017.
 */

public class TemaUtils {

    public static void mudarTema(final Context context, LayoutInflater inflater) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = inflater;
        View dialoglayout = layoutInflater.inflate(R.layout.dialog_tema_copo, null);

        final GridView gridViewTemas = (GridView) dialoglayout.findViewById(R.id.gvTemas);
        gridViewTemas.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        gridViewTemas.setAdapter(new TemasGridAdapter(context, getTemas()));

        builder.setTitle("TemaCopo do copo ");
        builder.setIcon(context.getResources().getDrawable(R.drawable.ic_tema));
        builder.setMessage("Escolha o tema para seu copo");
        builder.setCancelable(true);

        builder.setView(dialoglayout);
        final AlertDialog dialog = builder.show();
        final SharedPreferences preferencias = context.getSharedPreferences(PREF_CONFIG, MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferencias.edit();

        gridViewTemas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int temaEscolhido = (int) gridViewTemas.getItemAtPosition(i);

                editor.putInt("pref_tema", temaEscolhido);
                editor.commit();

                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });

        ImageButton imgRedefinir = (ImageButton) dialoglayout.findViewById(R.id.btnTemaPadrao);
        imgRedefinir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("pref_tema", 0);
                editor.commit();

                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });


    }


    private static List<TemaCopo> getTemas() {

        List<TemaCopo> temasCopo = new ArrayList<>();

        temasCopo.add(new TemaCopo(R.drawable.ic_tema_white, R.drawable.circulo_red, R.drawable.copo_red));
        temasCopo.add(new TemaCopo(R.drawable.ic_tema_white, R.drawable.circulo_pink, R.drawable.copo_pink));
        temasCopo.add(new TemaCopo(R.drawable.ic_tema_white, R.drawable.circulo_purple, R.drawable.copo_purple));
        temasCopo.add(new TemaCopo(R.drawable.ic_tema_white, R.drawable.circulo_blue, R.drawable.copo_blue));
        temasCopo.add(new TemaCopo(R.drawable.ic_tema_white, R.drawable.circulo_green, R.drawable.copo_green));
        temasCopo.add(new TemaCopo(R.drawable.ic_tema_white, R.drawable.circulo_orange, R.drawable.copo_orange));
        temasCopo.add(new TemaCopo(R.drawable.circulo_darth, R.drawable.copo_darth));
        temasCopo.add(new TemaCopo(R.drawable.circulo_ferro, R.drawable.copo_homen_ferro));
        temasCopo.add(new TemaCopo(R.drawable.circulo_star_wars, R.drawable.copo_star_wars));
        temasCopo.add(new TemaCopo(R.drawable.circulo_flor, R.drawable.copo_flor));
        temasCopo.add(new TemaCopo(R.drawable.circulo_frozen, R.drawable.copo_frozen));
        temasCopo.add(new TemaCopo(R.drawable.circulo_gato, R.drawable.copo_gato));

        return temasCopo;
    }


}
