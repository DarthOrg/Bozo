package com.darthorg.bozo.view;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.darthorg.bozo.R;
import com.darthorg.bozo.controller.PartidaController;
import com.darthorg.bozo.model.Partida;

import java.util.ArrayList;

public class NovaPartida extends AppCompatActivity {

    private Partida partida = new Partida();
    private PartidaController partidaController;

    private Toolbar toolbar;
    private ArrayList<String> jogadores = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    TextView contadorJogador;


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_nova_partida);

        contadorJogador = (TextView) findViewById(R.id.contagemJogadores);
        contadorJogador.setText("Jogadores " + jogadores.size() + "/10");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

        final ListView listView = (ListView) findViewById(R.id.list_view_jogadores);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, jogadores);
        listView.setAdapter(adapter);


        com.melnykov.fab.FloatingActionButton fab = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    final Dialog dialogNovoJogador = new Dialog(NovaPartida.this);

                    // Configura a view para o layout
                    dialogNovoJogador.setContentView(R.layout.dialog_novo_jogador);

                    //Recupera os componentes do layout
                    final EditText etNomeJogador = (EditText) dialogNovoJogador.findViewById(R.id.edit_nome_novo_jogador);
                    Button btnAdicionarJogador = (Button) dialogNovoJogador.findViewById(R.id.btnAdicionar);

                    //Adicionar novo jogador
                    btnAdicionarJogador.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Todo: ERRO não fecha o AlertDialog quando clica adicionar ou cancelar
                            if (jogadores.size() < 10) {
                                jogadores.add(etNomeJogador.getText().toString());
                                adapter.notifyDataSetChanged();
                                contadorJogador.setText("Jogadores: " + jogadores.size() + "/10");

                            } else {
                                Toast.makeText(NovaPartida.this, "Numero máximo de jogadores é 10", Toast.LENGTH_SHORT).show();
                            }
                            dialogNovoJogador.dismiss();
                        }
                    });
                    //Botão Cancelar
                    Button btnCancelar = (Button) dialogNovoJogador.findViewById(R.id.btnCancelar);
                    btnCancelar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogNovoJogador.dismiss();
                            return;
                        }
                    });
                    dialogNovoJogador.show();
                }
            });
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nova_partida_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_inciar_partida) {

            EditText etNovaPartida = (EditText) findViewById(R.id.editText_nomePartida);
            partida.setNome(etNovaPartida.getText().toString());

            if (jogadores.size() < 2) {

                Toast.makeText(NovaPartida.this, "Para iniciar precisa de 2 Jogadores", Toast.LENGTH_SHORT).show();

            } else if (TextUtils.isEmpty(etNovaPartida.getText().toString())) {
                Toast.makeText(this, "Por favor insira um nome para sua partida!", Toast.LENGTH_SHORT).show();
            } else {
                partidaController = new PartidaController(this);
                if (partidaController.inserirPartida(partida)) {
                    Intent intent = new Intent(this, PartidaAberta.class);
                    intent.putExtra("nomepartida", etNovaPartida.getText().toString());
                    intent.putStringArrayListExtra("jogadores", jogadores);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(this, "Ocorreu um erro ao dar um nome a partida", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
