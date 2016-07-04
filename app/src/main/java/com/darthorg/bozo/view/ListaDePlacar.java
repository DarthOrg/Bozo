package com.darthorg.bozo.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.darthorg.bozo.R;

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
        setContentView(R.layout.activity_placar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("ListaDePlacar");
        setSupportActionBar(toolbar);

        listViewPlacar = (ListView) findViewById(R.id.list_view_placar);

        mPlacarLista = new ArrayList<>();

        mPlacarLista.add(new JogadoresPlacar(1, "Wendell Ugalds", "Rodadas ganhadas","11"));
        mPlacarLista.add(new JogadoresPlacar(2, "Selma", "Rodadas ganhadas","9"));
        mPlacarLista.add(new JogadoresPlacar(3, "Aryane", "Rodadas ganhadas","7"));
        mPlacarLista.add(new JogadoresPlacar(4, "Regiane", "Rodadas ganhadas","5"));
        mPlacarLista.add(new JogadoresPlacar(5, "Yasmin", "Rodadas ganhadas","4"));
        mPlacarLista.add(new JogadoresPlacar(6, "Gustavo candido", "Rodadas ganhadas","2"));
        mPlacarLista.add(new JogadoresPlacar(7, "Outra pessoa", "Rodadas ganhadas","0"));
        mPlacarLista.add(new JogadoresPlacar(8, "Outra pessoa", "Rodadas ganhadas","0"));
        mPlacarLista.add(new JogadoresPlacar(9, "Outra pessoa", "Rodadas ganhadas","0"));
        mPlacarLista.add(new JogadoresPlacar(10, "Outra pessoa", "Rodadas ganhadas","0"));

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
