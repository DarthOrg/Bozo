package com.darthorg.bozo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.darthorg.bozo.R;
import com.darthorg.bozo.adapter.NovosJogadoresListAdapter;

import java.util.ArrayList;

public class NovaPartida extends AppCompatActivity {

    private Toolbar toolbar;
    private ArrayList<String> jogadores = new ArrayList<>();
    private NovosJogadoresListAdapter adapter;
    TextView titulo, contagemJogadores;
    FloatingActionButton btnAdicionarJogador;
    ListView listView;


    private TextInputLayout tilJogador;
    private TextInputLayout tilNomeGrupo;
    private EditText editJogador;
    private EditText editGrupo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_partida);
        getIDs();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Contador Jogadors
        contarJogadores();

        listView = (ListView) findViewById(R.id.list_view_jogadores);
        adapter = new NovosJogadoresListAdapter(jogadores, this);
        listView.setAdapter(adapter);


        //Listeners Paras os textos
        editGrupo.addTextChangedListener(new TextWatcher() {
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
        editJogador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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
                    if (!TextUtils.isEmpty(editJogador.getText().toString())) {
                        jogadores.add(editJogador.getText().toString());
                        adapter.notifyDataSetChanged();
                        editJogador.setText(null);
                        contarJogadores();
                    }
                } else {
                    tilJogador.setVisibility(View.GONE);
                    btnAdicionarJogador.setVisibility(View.GONE);
                    Snackbar.make(v, "Numero Maximo de jogadores atingido", Snackbar.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nova_partida_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_btn_criar) {

            if (TextUtils.isEmpty(editGrupo.getText().toString())) {
                tilNomeGrupo.setError(getString(R.string.NomeParaSuaPartida));
            } else if (jogadores.size() == 0) {
                tilJogador.setError("Adicione pelo menos 2 jogadores para criar o grupo");
            } else if (jogadores.size() == 1) {
                tilJogador.setError("Adicione um ou mais jogadores para iniciar");
            } else {
                Intent intent = new Intent(NovaPartida.this, PartidaAberta.class);
                intent.putExtra("nomepartida", editGrupo.getText().toString());
                intent.putStringArrayListExtra("jogadores", jogadores);
                intent.putExtra("partidaNova", true);
                startActivity(intent);
                finish();
            }

        }

        return super.onOptionsItemSelected(item);
    }

    private void getIDs() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnAdicionarJogador = (FloatingActionButton) findViewById(R.id.btnAddJogador);
        editGrupo = (EditText) findViewById(R.id.editNomeGrupo);
        tilNomeGrupo = (TextInputLayout) findViewById(R.id.til_nome_grupo);
        tilJogador = (TextInputLayout) findViewById(R.id.til_jogador);
        editJogador = (EditText) findViewById(R.id.editJogador);
    }

    private void contarJogadores() {

        if (jogadores.size() == 0) {
            //          contagemJogadores2.setText("0");
        } else if (jogadores.size() == 1) {
            //          contagemJogadores2.setText(jogadores.size() + "");
        } else if (jogadores.size() >= 2) {
            //        contagemJogadores2.setText(jogadores.size() + "");
        }
    }


}
