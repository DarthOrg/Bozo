package com.darthorg.bozo.view;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
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
        contadorJogador.setText("Jogadores "+jogadores.size()+"/10");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

        ListView listView = (ListView) findViewById(R.id.list_view_jogadores);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, jogadores);
        listView.setAdapter(adapter);


        com.melnykov.fab.FloatingActionButton fab = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                LayoutInflater inflater = getLayoutInflater();
                //Recebe a activity para persolnalizar o dialog
                View dialogLayout = inflater.inflate(R.layout.dialog_novo_jogador, null);
                final EditText etNomeJogador = (EditText) dialogLayout.findViewById(R.id.edit_nome_novo_jogador);

                AlertDialog.Builder builder = new AlertDialog.Builder(NovaPartida.this);
                builder.setTitle("Novo jogador");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (jogadores.size() < 10) {
                            jogadores.add(etNomeJogador.getText().toString());
                            adapter.notifyDataSetChanged();
                            contadorJogador.setText("Jogadores: "+jogadores.size()+"/10");
                            Snackbar.make(view, "Jogador "+etNomeJogador.getText().toString()+" foi adicionado!", Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(view, "Numero máximo de jogadores é 10", Snackbar.LENGTH_INDEFINITE)
                                    .setActionTextColor(Color.CYAN)
                                    .setAction("Ok", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            return;

                                        }
                                    })
                                    .show();
                        }
                    }
                });
                builder.setNegativeButton("Cancelar", null);
                builder.setView(dialogLayout);
                builder.setCancelable(false);
                builder.show();
            }
        });


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

            }else if (TextUtils.isEmpty(etNovaPartida.getText().toString())) {
                Toast.makeText(this, "Por favor insira um nome para sua partida!", Toast.LENGTH_SHORT).show();
            }else {
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
