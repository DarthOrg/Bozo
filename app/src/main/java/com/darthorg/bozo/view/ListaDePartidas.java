package com.darthorg.bozo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.darthorg.bozo.R;
import com.darthorg.bozo.adapter.PartidasListaAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListaDePartidas extends AppCompatActivity {


    private Toolbar toolbar;
    private ListView listViewPartidas;
    private PartidasListaAdapter adapter;
    private List<Partidas> mPartidasLista;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_partidas);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Suas partidas");
        setSupportActionBar(toolbar);

        listViewPartidas = (ListView) findViewById(R.id.list_view_partidas);

        mPartidasLista = new ArrayList<>();

        mPartidasLista.add(new Partidas(1, "01", "Partida 01","Jogador 01, Jogador 02, Jogador 03"));
        mPartidasLista.add(new Partidas(2, "02", "Partida 02","Jogador 01, Jogador 02"));
        mPartidasLista.add(new Partidas(3, "03", "Partida 03","Jogador 01, Jogador 02, Jogador 03"));
        mPartidasLista.add(new Partidas(4, "04", "Partida 04","Jogador 01, Jogador 02, Jogador 03"));
        mPartidasLista.add(new Partidas(5, "05", "Partida 05","Jogador 01, Jogador 02, Jogador 03"));
        mPartidasLista.add(new Partidas(6, "06", "Partida 06","Jogador 01, Jogador 02"));
        mPartidasLista.add(new Partidas(7, "07", "Partida 07","Jogador 01, Jogador 02, Jogador 03"));
        mPartidasLista.add(new Partidas(8, "08", "Partida 08","Jogador 01, Jogador 02, "));
        mPartidasLista.add(new Partidas(9, "09", "Partida 09","Jogador 01, Jogador 02, Jogador 03"));
        mPartidasLista.add(new Partidas(10, "10", "Partida 10","Jogador 01, Jogador 02, Jogador 03"));
        mPartidasLista.add(new Partidas(11, "11", "Partida 11","Jogador 01, Jogador 02"));
        mPartidasLista.add(new Partidas(12, "12", "Partida 12","Jogador 01, Jogador 02, Jogador 03"));
        mPartidasLista.add(new Partidas(13, "13", "Partida 13","Jogador 01, Jogador 02, Jogador 03"));
        mPartidasLista.add(new Partidas(14, "14", "Partida 14","Jogador 01, Jogador 02, Jogador 03"));
        mPartidasLista.add(new Partidas(15, "15", "Partida 15","Jogador 01, Jogador 02"));
        mPartidasLista.add(new Partidas(16, "16", "Partida 16","Jogador 01, Jogador 02, Jogador 03"));
        mPartidasLista.add(new Partidas(17, "17", "Partida 17","Jogador 01, Jogador 02, "));
        mPartidasLista.add(new Partidas(18, "18", "Partida 18","Jogador 01, Jogador 02, Jogador 03"));
        mPartidasLista.add(new Partidas(19, "19", "Partida 19","Jogador 01, Jogador 02, Jogador 03"));
        mPartidasLista.add(new Partidas(20, "20", "Partida 20","Jogador 01, Jogador 02, Jogador 03"));

        adapter = new PartidasListaAdapter(getApplicationContext(),mPartidasLista);
        listViewPartidas.setAdapter(adapter);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
