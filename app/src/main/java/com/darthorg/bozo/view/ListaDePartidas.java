package com.darthorg.bozo.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
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
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_lista_de_partidas);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

        listViewPartidas = (ListView) findViewById(R.id.list_view_partidas);

        partidaDAO = new PartidaDAO(this);
        partidaList = partidaDAO.buscarPartidas();


        partidasListAdapter = new PartidasListaAdapter(getApplicationContext(), partidaList);
        listViewPartidas.setAdapter(partidasListAdapter);

        listViewPartidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListaDePartidas.this, PartidaAberta.class);
                intent.putExtra("partidaSalva", partidaList.get(position).getIdPartida());
                intent.putExtra("partidaNova", false);
                startActivity(intent);
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.partida_salvas_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_nova_partida) {
            Intent intent = new Intent(ListaDePartidas.this, NovaPartida.class);
            startActivity(intent);
        } else if (id == R.id.action_atualizar) {
            Intent intent = new Intent(ListaDePartidas.this, ListaDePartidas.class);
            startActivity(intent);
            finish();
        } else if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
