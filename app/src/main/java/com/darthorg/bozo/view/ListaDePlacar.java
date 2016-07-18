package com.darthorg.bozo.view;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ListView;

import com.darthorg.bozo.R;
import com.darthorg.bozo.adapter.JogadoresPlacarListaAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListaDePlacar extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listViewPlacar;
    private JogadoresPlacarListaAdapter adapter;
    private List<JogadoresPlacar> mPlacarLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_placar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);


        listViewPlacar = (ListView) findViewById(R.id.list_view_placar);

        mPlacarLista = new ArrayList<>();

        mPlacarLista.add(new JogadoresPlacar(1, "Wendell Ugalds","11"));
        mPlacarLista.add(new JogadoresPlacar(2, "Selma","9"));
        mPlacarLista.add(new JogadoresPlacar(3, "Gustavo candido","7"));
        mPlacarLista.add(new JogadoresPlacar(6, "Aryane","2"));

        adapter = new JogadoresPlacarListaAdapter(getApplicationContext(), mPlacarLista);
        listViewPlacar.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.placar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_zerar) {
            //Intent intent = new Intent(this,PartidaAberta.class);
            //startActivity(intent);
            //return true;
        }
        else if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
