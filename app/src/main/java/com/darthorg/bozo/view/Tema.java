package com.darthorg.bozo.view;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darthorg.bozo.R;
import com.darthorg.bozo.adapter.TemasGridAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.darthorg.bozo.view.Definicoes.PREF_CONFIG;



public class Tema extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tema_copo);
        changeStatusBarColor();

        //toobar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        Mudar Tema acopo
            final GridView gridViewTemas = (GridView) findViewById(R.id.gvTemas);
            gridViewTemas.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            gridViewTemas.setAdapter(new TemasGridAdapter(this, getTemas()));

            final SharedPreferences preferencias = getSharedPreferences(PREF_CONFIG, MODE_PRIVATE);
            final SharedPreferences.Editor editor = preferencias.edit();

            gridViewTemas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    //Posição do item
                    final int temaEscolhido = (int) gridViewTemas.getItemAtPosition(i);

                    //Abre o alert
                    AlertDialog.Builder builder = new AlertDialog.Builder(Tema.this);
                    LayoutInflater layoutInflater = getLayoutInflater();
                    final View dialoglayout = layoutInflater.inflate(R.layout.dialog_tema_copo, null);
                    builder.setView(dialoglayout);

                    ImageView copoView = (ImageView) dialoglayout.findViewById(R.id.copoVeiw);

                    builder.setTitle("Pré vizualização");
                    builder.setIcon(R.drawable.ic_olhar);

                    //Coloca na ImageView o tema escolhido
                    copoView.setImageResource(temaEscolhido);

                            builder.setPositiveButton("Definir", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    //Altera o SharedPreferences
                                    editor.putInt("pref_tema", temaEscolhido);
                                    editor.commit();
                                    dialog.dismiss();
                                    finish();
                                }
                            })
                            .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                }
                            });
                    builder.show();
                }
            });

            FloatingActionButton imgRedefinir = (FloatingActionButton) findViewById(R.id.btnTemaPadrao);
            imgRedefinir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editor.putInt("pref_tema", 0);
                    editor.commit();
                    finish();
                    Toast.makeText(getApplicationContext(),"Tema restaurado",Toast.LENGTH_LONG).show();
                }
            });


    }




    private static List<com.darthorg.bozo.model.TemaCopo> getTemas() {

        List<com.darthorg.bozo.model.TemaCopo> temasCopo = new ArrayList<>();

        //Temas cores
        temasCopo.add(new com.darthorg.bozo.model.TemaCopo(R.drawable.ic_cores, R.color.colorRed, R.drawable.copo_red));
        temasCopo.add(new com.darthorg.bozo.model.TemaCopo(R.drawable.ic_cores, R.color.colorPink, R.drawable.copo_pink));
        temasCopo.add(new com.darthorg.bozo.model.TemaCopo(R.drawable.ic_cores, R.color.colorPurple, R.drawable.copo_purple));
        temasCopo.add(new com.darthorg.bozo.model.TemaCopo(R.drawable.ic_cores, R.color.colorBlue, R.drawable.copo_blue));
        temasCopo.add(new com.darthorg.bozo.model.TemaCopo(R.drawable.ic_cores, R.color.colorGreen, R.drawable.copo_green));
        temasCopo.add(new com.darthorg.bozo.model.TemaCopo(R.drawable.ic_cores, R.color.colorOrange, R.drawable.copo_orange));

        //Temas desenhos
        temasCopo.add(new com.darthorg.bozo.model.TemaCopo(R.drawable.ic_darth, R.color.colorDarh, R.drawable.copo_darth));
        temasCopo.add(new com.darthorg.bozo.model.TemaCopo(R.drawable.ic_ferro, R.color.colorFerro, R.drawable.copo_ferro));
        temasCopo.add(new com.darthorg.bozo.model.TemaCopo(R.drawable.ic_hulk, R.color.colorHulk, R.drawable.copo_hulk));
        temasCopo.add(new com.darthorg.bozo.model.TemaCopo(R.drawable.ic_star_wars, R.color.colorStarWars, R.drawable.copo_star_wars));
        temasCopo.add(new com.darthorg.bozo.model.TemaCopo(R.drawable.ic_flores, R.color.colorFlores, R.drawable.copo_flores));
        temasCopo.add(new com.darthorg.bozo.model.TemaCopo(R.drawable.ic_frozen, R.color.colorFrozen, R.drawable.copo_frozen));
        temasCopo.add(new com.darthorg.bozo.model.TemaCopo(R.drawable.ic_gato, R.color.colorGato, R.drawable.copo_gato));

        return temasCopo;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            menssagemSair();
        }

        return super.onOptionsItemSelected(item);
    }
    public void menssagemSair(){
        finish();
        Toast.makeText(getApplicationContext(),"Nenhum tema selecionado",Toast.LENGTH_LONG).show();
    }


    @Override
    public void onBackPressed() {
        menssagemSair();
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorBlack));
        }
    }
}
