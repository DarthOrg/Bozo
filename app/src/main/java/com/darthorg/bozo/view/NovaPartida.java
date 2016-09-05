package com.darthorg.bozo.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.darthorg.bozo.R;

import java.util.ArrayList;

public class NovaPartida extends AppCompatActivity {

    private Toolbar toolbar;
    private ArrayList<String> jogadores = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    TextView Titulo,contagemJogadores,contagemJogadores2;
    EditText editNomeNovoJogador;
    FloatingActionButton btnAdicionarJogador;
    FrameLayout viewcontadorjogadores;
    ListView listView;


    private TextInputLayout tilJogador;
    private TextInputLayout tilNomeGrupo;
    private EditText campoJogador;
    private EditText campoNomeGrupo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_partida);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.TextoVazio);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        //Contador Jogador
        contagemJogadores2 = (TextView) findViewById(R.id.contagemJogadores2);
        if (jogadores.size() == 0){
            contagemJogadores2.setText("0");
        }else if (jogadores.size() == 1){
            contagemJogadores2.setText(jogadores.size()+"");
        }else if (jogadores.size() >= 2){
            contagemJogadores2.setText(jogadores.size()+"");
        }

        listView = (ListView) findViewById(R.id.list_view_jogadores);
        //todo: Criar um custon adapter com um botão remover os jogadores
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, jogadores);
        listView.setAdapter(adapter);

        //botão ficar precionado
        registerForContextMenu(listView);

        btnAdicionarJogador = (FloatingActionButton) findViewById(R.id.btnAddJogador);
        Titulo = (TextView) findViewById(R.id.Titulo);

        tilNomeGrupo = (TextInputLayout) findViewById(R.id.til_nome_grupo);
        campoNomeGrupo = (EditText) findViewById(R.id.campo_nome_grupo);

        tilJogador = (TextInputLayout) findViewById(R.id.til_jogador);
        campoJogador = (EditText) findViewById(R.id.campo_jogador);

        campoNomeGrupo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilNomeGrupo.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        campoJogador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnAdicionarJogador.setVisibility(View.VISIBLE);
                tilJogador.setError(null);

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        btnAdicionarJogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jogadores.size() < 10) {
                    jogadores.add(campoJogador.getText().toString());
                    adapter.notifyDataSetChanged();
                    campoJogador.setText(null);
                    btnAdicionarJogador.setVisibility(View.GONE);
                    if (jogadores.size() == 0){
                        contagemJogadores2.setText("0");
                    }else if (jogadores.size() == 1){
                        contagemJogadores2.setText(jogadores.size()+"");
                    }else if (jogadores.size() >= 2){
                        contagemJogadores2.setText(jogadores.size()+"");
                    }

                } else {
                    contagemJogadores2.setVisibility(View.GONE);
                    tilJogador.setVisibility(View.GONE);
                    btnAdicionarJogador.setVisibility(View.GONE);
                    Snackbar snackbar = Snackbar
                            .make(v, "Máximo 10 jogadores", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("Entendi", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            btnAdicionarJogador.setVisibility(View.VISIBLE);
                            contagemJogadores2.setVisibility(View.GONE);
                            contagemJogadores2.setTextColor(Color.RED);
                            tilJogador.setVisibility(View.VISIBLE);
                            campoJogador.setText(null);
                        }
                    });
                    snackbar.setActionTextColor(Color.RED);
                    snackbar.show();

                }

            }
        });


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.popup_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_apagar:

                //Todo:Implementar apagar jogadores

                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.nova_partida_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }else if (id == R.id.action_btn_criar) {

            if (TextUtils.isEmpty(campoNomeGrupo.getText().toString())) {
                tilNomeGrupo.setError(getString(R.string.NomeParaSuaPartida));
            }

            if (jogadores.size() == 0) {
                tilJogador.setError("Adicione 2 jogadores para criar o grupo");
                btnAdicionarJogador.setVisibility(View.GONE);
            }else if (jogadores.size() == 1) {
                tilJogador.setError("Adicione mais 1 jogador para criar o grupo");
                btnAdicionarJogador.setVisibility(View.GONE);
            }else {
                Intent intent = new Intent(NovaPartida.this, PartidaAberta.class);
                intent.putExtra("nomepartida", campoNomeGrupo.getText().toString());
                intent.putStringArrayListExtra("jogadores", jogadores);
                intent.putExtra("partidaNova", true);
                startActivity(intent);
                finish();
            }

        }

        return super.onOptionsItemSelected(item);
    }



}
