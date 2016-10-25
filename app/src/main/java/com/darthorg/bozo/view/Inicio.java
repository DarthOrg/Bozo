package com.darthorg.bozo.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.darthorg.bozo.R;
import com.darthorg.bozo.adapter.PartidasListAdapter;
import com.darthorg.bozo.controller.PartidaController;
import com.darthorg.bozo.model.Partida;

import java.util.List;

public class Inicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ListView listViewPartidas;
    private PartidasListAdapter partidasListAdapter;
    private List<Partida> partidaList;
    private Toolbar toolbar;
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.grupos_de_jogo));
        setSupportActionBar(toolbar);

        listViewPartidas = (ListView) findViewById(R.id.list_view_partidas);

        //Aparecer imagem quando a lista estiver vazia
        listViewPartidas.setEmptyView(findViewById(R.id.listVazio));

        PartidaController partidaController = new PartidaController(Inicio.this);
        partidaList = partidaController.buscarPartidas();
        partidasListAdapter = new PartidasListAdapter(getApplicationContext(), partidaList, this);
        listViewPartidas.setAdapter(partidasListAdapter);

        listViewPartidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Inicio.this, PartidaAberta.class);
                intent.putExtra("partidaSalva", partidaList.get(position).getIdPartida());
                intent.putExtra("partidaNova", false);
                startActivity(intent);
            }
        });

        //atualizar
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.stl_swipe);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                atualizarLista();
                                mSwipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inicio.this, NovaPartida.class);
                Inicio.this.startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void instrucoesActivity() {
        Intent intent = new Intent(Inicio.this, Instrucoes.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_novo_grupo) {
            Intent intent = new Intent(Inicio.this, AdicionarJogadores.class);
            startActivity(intent);

        } else if (id == R.id.nav_instrucoes) {
            instrucoesActivity();
        } else if (id == R.id.nav_cor_destaque) {


        } else if (id == R.id.nav_sobre) {
            Intent intent = new Intent(Inicio.this, Sobre.class);
            startActivity(intent);

        } else if (id == R.id.nav_compartilhar) {
            // Compartilhar app
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, getString(R.string.compartilhar_app) + getString(R.string.url_googleplay));
            startActivity(Intent.createChooser(share, getString(R.string.titulo_compartilhar_app)));

        } else if (id == R.id.nav_configuracoes) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void atualizarLista() {
        PartidaController partidaController = new PartidaController(Inicio.this);
        partidaList = partidaController.buscarPartidas();
        partidasListAdapter = new PartidasListAdapter(getApplicationContext(), partidaList, this);
        listViewPartidas.setAdapter(partidasListAdapter);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        atualizarLista();
    }
}
