package com.darthorg.bozo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.darthorg.bozo.R;
import com.darthorg.bozo.adapter.PartidasListaAdapter;
import com.darthorg.bozo.dao.PartidaDAO;
import com.darthorg.bozo.model.Partida;

import java.util.List;

public class ListaDePartidas extends AppCompatActivity {


    private Toolbar toolbar;
    private ListView listViewPartidas;
    private PartidasListaAdapter partidasListAdapter;
    private PartidaDAO partidaDAO;
    private List<Partida> partidaList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_partidas);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Suas partidas");
        setSupportActionBar(toolbar);

        listViewPartidas = (ListView) findViewById(R.id.list_view_partidas);

        partidaDAO = new PartidaDAO(this);
        partidaList = partidaDAO.buscarPartidas();

        partidasListAdapter = new PartidasListaAdapter(getApplicationContext(), partidaList);
        listViewPartidas.setAdapter(partidasListAdapter);

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
