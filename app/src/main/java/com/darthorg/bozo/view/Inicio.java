package com.darthorg.bozo.view;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.darthorg.bozo.R;
import com.darthorg.bozo.Splash;
import com.darthorg.bozo.Welcome;
import com.darthorg.bozo.adapter.PartidasListAdapter;
import com.darthorg.bozo.controller.PartidaController;
import com.darthorg.bozo.model.Partida;

import java.util.EmptyStackException;
import java.util.List;

import static android.view.View.VISIBLE;

public class Inicio extends AppCompatActivity {


    private Toolbar toolbar;

    private ListView listViewPartidas;
    private PartidasListAdapter partidasListAdapter;
    private List<Partida> partidaList;


    private ListView listViewPartidasInicio;
    private PartidasListAdapter partidasListAdapterInicio;
    private List<Partida> partidaListInicio;


    FloatingActionButton fabCompartilhar, fabDefinicoes, novoMarcador, fabMSalvos, fabInstrucoes;
    CardView cardMSalvos;
    Button cardNovoMarcador;
    LinearLayout ultimo_salvo;


//    private final int SPLASH = 10000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        changeStatusBarColor();

        //Toobar do include marcadores salvos
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_marcador));
        setSupportActionBar(toolbar);

        //Botões FAB
        fabCompartilhar = (FloatingActionButton) findViewById(R.id.fabCompartilhar);
        fabDefinicoes = (FloatingActionButton) findViewById(R.id.fabDefinicoes);
        fabMSalvos = (FloatingActionButton) findViewById(R.id.fabMSalvos);
        fabInstrucoes = (FloatingActionButton) findViewById(R.id.fabInstrucoes);

        //PopUp marcadores salvos
        novoMarcador = (FloatingActionButton) findViewById(R.id.novo_marcador);
        cardMSalvos = (CardView) findViewById(R.id.CardMSalvos);
        cardNovoMarcador = (Button) findViewById(R.id.cardBtnNovoMarcador);
        ultimo_salvo = (LinearLayout) findViewById(R.id.ultimo_salvo);

        novoMarcador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ultimo_salvo.getVisibility() == VISIBLE){
                    ultimo_salvo.setVisibility(View.GONE);
                }else {
                    Intent intent = new Intent(Inicio.this, NovaPartida.class);
                    startActivity(intent);
                }
            }
        });

        novoMarcador.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                    ultimo_salvo.setVisibility(VISIBLE);
                return true;
            }
        });


        cardNovoMarcador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inicio.this, NovaPartida.class);
                startActivity(intent);
            }
        });


        fabMSalvos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ultimo_salvo.getVisibility() == VISIBLE){
                    ultimo_salvo.setVisibility(View.GONE);
                    cardMSalvos.setVisibility(VISIBLE);
                }else{
                    cardMSalvos.setVisibility(VISIBLE);
                }

            }
        });

        fabCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Compartilhar app
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.putExtra(Intent.EXTRA_TEXT, getString(R.string.compartilhar_app) + getString(R.string.url_googleplay));
            startActivity(Intent.createChooser(share, getString(R.string.titulo_compartilhar_app)));
            }
        });
        fabInstrucoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inicio.this, Instrucoes.class);
                startActivity(intent);
            }
        });




        listViewPartidas = (ListView) findViewById(R.id.list_view_partidas);
        listViewPartidasInicio = (ListView) findViewById(R.id.list_view_partidas_inicio);

        //Aparecer imagem quando a lista estiver vazia
        listViewPartidas.setEmptyView(findViewById(R.id.listVazio));

        PartidaController partidaController = new PartidaController(Inicio.this);
        partidaList = partidaController.buscarPartidas();
        partidasListAdapter = new PartidasListAdapter(getApplicationContext(), partidaList, this);
        listViewPartidas.setAdapter(partidasListAdapter);
        listViewPartidasInicio.setAdapter(partidasListAdapter);

        listViewPartidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Inicio.this, PartidaAberta.class);
                intent.putExtra("partidaSalva", partidaList.get(position).getIdPartida());
                intent.putExtra("partidaNova", false);
                startActivity(intent);
            }
        });

        listViewPartidasInicio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Inicio.this, PartidaAberta.class);
                intent.putExtra("partidaSalva", partidaList.get(position).getIdPartida());
                intent.putExtra("partidaNova", false);
                startActivity(intent);
            }
        });



    }
    @Override
    public void onBackPressed() {

        if (cardMSalvos.getVisibility() == VISIBLE) {
            cardMSalvos.setVisibility(View.GONE);
        } else if (ultimo_salvo.getVisibility() == VISIBLE){
            ultimo_salvo.setVisibility(View.GONE);
        } else{
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inicio_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_sair) {
            cardMSalvos.setVisibility(View.GONE);
        }

        return super.

                onOptionsItemSelected(item);
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

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorAccentDark));
        }
    }
}
