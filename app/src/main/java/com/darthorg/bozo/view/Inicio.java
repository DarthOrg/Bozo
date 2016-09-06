package com.darthorg.bozo.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ContextMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.darthorg.bozo.R;
import com.darthorg.bozo.adapter.PartidasListaAdapter;
import com.darthorg.bozo.dao.PartidaDAO;
import com.darthorg.bozo.model.Partida;

import java.util.List;

public class Inicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ListView listViewPartidas;
    private PartidasListaAdapter partidasListAdapter;
    private PartidaDAO partidaDAO;
    private List<Partida> partidaList;
    Toolbar toolbar;
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(R.string.TextoVazio);
        setSupportActionBar(toolbar);

        listViewPartidas = (ListView) findViewById(R.id.list_view_partidas);

        //Aparecer imagem quando a lista estiver vazia
        listViewPartidas.setEmptyView(findViewById(R.id.list_vazio));

        //botão ficar precionado
        registerForContextMenu(listViewPartidas);


        partidaDAO = new PartidaDAO(this);
        partidaList = partidaDAO.buscarPartidas();


        partidasListAdapter = new PartidasListaAdapter(getApplicationContext(), partidaList);
        listViewPartidas.setAdapter(partidasListAdapter);

//botão ficar precionado
        registerForContextMenu(listViewPartidas);

        listViewPartidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Inicio.this, PartidaAberta.class);
                intent.putExtra("partidaSalva", partidaList.get(position).getIdPartida());
                intent.putExtra("partidaNova", false);
                startActivity(intent);

                finish();
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
                                partidaDAO = new PartidaDAO(Inicio.this);
                                partidaList = partidaDAO.buscarPartidas();
                                partidasListAdapter = new PartidasListaAdapter(getApplicationContext(), partidaList);
                                listViewPartidas.setAdapter(partidasListAdapter);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.popup_menu, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.action_apagar:

                int position = 0;
                Toast.makeText(Inicio.this, partidaList.get(position).getNome() + " - foi excluido da sua lista", Toast.LENGTH_SHORT).show();
                PartidaDAO partidaDAO = new PartidaDAO(Inicio.this);
                partidaDAO.deletarPartida(partidaList.get(position));
                partidaList.remove(position);
                partidasListAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);

        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inicio_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_instrucoes) {
            instrucoesActivity();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_novo_grupo) {
            Intent intent = new Intent(Inicio.this, NovaPartida.class);
            startActivity(intent);

        } else if (id == R.id.nav_instrucoes) {
            instrucoesActivity();
        } else if (id == R.id.nav_cor_destaque) {


        } else if (id == R.id.nav_sobre) {
            Intent intent = new Intent(Inicio.this, Sobre.class);
            startActivity(intent);

        } else if (id == R.id.nav_compartilhar) {
            // Compartilhar app
            // Algumas aplicações ainda bugam
            // Whatsapp, Email, Mensagem, OK
            // todo:  rever "facebook","messeger" , trocar url do app
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, "Baixe marcador de Bozó na GooglePlay " + "https://play.google.com/store/apps/details?id=com.adobe.psmobile");
            startActivity(Intent.createChooser(share, "Compartilhar App"));

        } else if (id == R.id.nav_configuracoes) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
