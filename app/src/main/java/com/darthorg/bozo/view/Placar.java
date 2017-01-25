package com.darthorg.bozo.view;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.darthorg.bozo.R;
import com.darthorg.bozo.adapter.PlacarAdapter;
import com.darthorg.bozo.model.Rodada;

import java.util.ArrayList;

public class Placar extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listViewPlacar;
    private PlacarAdapter adapter;
    private ArrayList<Rodada> rodadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placar);
        changeStatusBarColor();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.placar);
        setSupportActionBar(toolbar);

        // Recupera as rodadas vindas por intent
        rodadas = getIntent().getParcelableArrayListExtra("rodadasfinalizadas");

        // Configura o txt com a quantidade de rodadas
        TextView tvQuantRodadas = (TextView) findViewById(R.id.btnQuantRodadas);
        tvQuantRodadas.setText(String.valueOf(rodadas.size()));

        // Recupera o listView e configura com a lista de rodadas
        listViewPlacar = (ListView) findViewById(R.id.list_view_placar);
        adapter = new PlacarAdapter(getApplicationContext(), rodadas);
        listViewPlacar.setAdapter(adapter);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorLaranja));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
