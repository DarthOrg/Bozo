package com.darthorg.bozo.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.darthorg.bozo.R;
import com.darthorg.bozo.adapter.PartidasListAdapter;
import com.darthorg.bozo.adapter.UltimaPartidaAdapter;
import com.darthorg.bozo.controller.PartidaController;
import com.darthorg.bozo.model.Partida;

import java.util.List;

import static android.view.View.VISIBLE;

public class Inicio extends AppCompatActivity {

    public static final String PREF_CONFIG = "CONFIG_PONTUACAO";

    private Toolbar toolbar;

    private ListView listViewPartidas;
    private PartidasListAdapter partidasListAdapter;
    private List<Partida> partidaList;


    private ListView listViewUltimaPartida;
    private UltimaPartidaAdapter ultimaPartidaAdapter;
    private Partida ultimaPartida;


    FloatingActionButton fabCompartilhar, fabDefinicoes, novoMarcador, fabMSalvos, fabInstrucoes;
    LinearLayout ultimo_salvo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        changeStatusBarColor();

        ImageView copoVirtual = (ImageView) findViewById(R.id.copoVirtual);
        copoVirtual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this,CopoVirtual.class);
                startActivity(intent);
            }
        });

        //Botões FAB
        fabCompartilhar = (FloatingActionButton) findViewById(R.id.fabCompartilhar);
        fabDefinicoes = (FloatingActionButton) findViewById(R.id.fabDefinicoes);
        fabMSalvos = (FloatingActionButton) findViewById(R.id.fabMSalvos);
        fabInstrucoes = (FloatingActionButton) findViewById(R.id.fabInstrucoes);

        novoMarcador = (FloatingActionButton) findViewById(R.id.novo_marcador);
        ultimo_salvo = (LinearLayout) findViewById(R.id.ultimo_salvo);

        novoMarcador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inicio.this, NovaPartida.class);
                startActivity(intent);

            }
        });


        fabDefinicoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Definicoes.class);
                startActivity(intent);
            }
        });


        fabMSalvos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Implementar dialog

                final AlertDialog.Builder builder = new AlertDialog.Builder(Inicio.this);

                LayoutInflater layoutInflater = getLayoutInflater();
                final View dialoglayout = layoutInflater.inflate(R.layout.dialog_marcadores_salvos, null);

                builder.setView(dialoglayout);


                final AlertDialog dialog = builder.create();



                Button btnNovoMarcador = (Button) dialoglayout.findViewById(R.id.btnNovoMarcador);
                btnNovoMarcador.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Inicio.this, NovaPartida.class);
                        dialog.dismiss();
                        startActivity(intent);
                    }
                });

                listViewPartidas = (ListView) dialoglayout.findViewById(R.id.list_view_partidas);

                //Aparecer imagem quando a lista estiver vazia
                listViewPartidas.setEmptyView(dialoglayout.findViewById(R.id.listVazio));

                //Traz as partidas Salvas
                PartidaController partidaController = new PartidaController(Inicio.this);
                partidaList = partidaController.buscarPartidas();
                partidasListAdapter = new PartidasListAdapter(getApplicationContext(), partidaList, Inicio.this);
                listViewPartidas.setAdapter(partidasListAdapter);

                //Ao clicar em uma partida
                listViewPartidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(Inicio.this, PartidaAberta.class);
                        intent.putExtra("partidaSalva", partidaList.get(position).getIdPartida());
                        intent.putExtra("partidaNova", false);
                        dialog.dismiss();
                        startActivity(intent);
                    }
                });

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        atualizarTrazerUltimaPartida();
                    }
                });


                dialog.show();


            }
        });

        fabCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Compartilhar app
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, getString(R.string.textoCompartilharApp) + " " + getString(R.string.url_googleplay));
                startActivity(Intent.createChooser(share, getString(R.string.compartilharApp)));
            }
        });
        fabInstrucoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inicio.this, Instrucoes.class);
                startActivity(intent);
            }
        });


        listViewUltimaPartida = (ListView) findViewById(R.id.list_view_ultima_partida);
        atualizarTrazerUltimaPartida();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void atualizarTrazerUltimaPartida() {

        PartidaController partidaController = new PartidaController(Inicio.this);
        ultimaPartida = partidaController.buscarUltimaPartida();
        if (ultimaPartida != null) {
            ultimo_salvo.setVisibility(VISIBLE);

            ultimaPartidaAdapter = new UltimaPartidaAdapter(getApplicationContext(), ultimaPartida, this);
            listViewUltimaPartida.setAdapter(ultimaPartidaAdapter);

            listViewUltimaPartida.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Inicio.this, PartidaAberta.class);
                    intent.putExtra("partidaSalva", ultimaPartida.getIdPartida());
                    intent.putExtra("partidaNova", false);
                    startActivity(intent);
                }
            });
        } else {
            ultimo_salvo.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        atualizarTrazerUltimaPartida();
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorAccentDark));
        }
    }
}
