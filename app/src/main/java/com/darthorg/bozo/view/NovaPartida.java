package com.darthorg.bozo.view;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.darthorg.bozo.R;
import com.darthorg.bozo.dao.JogadorDAO;
import com.darthorg.bozo.dao.PartidaDAO;
import com.darthorg.bozo.model.Jogador;
import com.darthorg.bozo.model.Partida;

import java.util.ArrayList;
import java.util.List;

public class NovaPartida extends AppCompatActivity {

    private Toolbar toolbar;
    private ArrayList<String> jogadores = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    TextView tvNomeGrupo,Titulo,TextoInfo;
    EditText editTextNomePartida,editNomeNovoJogador;
    ImageButton novoJogador, btnEditar,btnConfirmar,btnSair,btnAdicionarJogador;
    Button contagemJogadores,btnIniciar;
    MenuItem actionInicar;
    private List<Jogador> jogadorList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_partida);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.TextoVazio);
        setSupportActionBar(toolbar);

        novoJogador = (ImageButton) findViewById(R.id.novoJogador);
        btnEditar = (ImageButton) findViewById(R.id.btnEditar);
        btnConfirmar = (ImageButton) findViewById(R.id.btnConfirmar);
        btnSair = (ImageButton) findViewById(R.id.btnSair);
        btnAdicionarJogador = (ImageButton) findViewById(R.id.btnAdicionarJogador);
        contagemJogadores = (Button) findViewById(R.id.contagemJogadores);
        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        tvNomeGrupo = (TextView) findViewById(R.id.tvNomeGrupo);
        Titulo = (TextView) findViewById(R.id.Titulo);
        TextoInfo = (TextView) findViewById(R.id.TextoInfo);
        editTextNomePartida = (EditText) findViewById(R.id.editText_nomePartida);
        editTextNomePartida.setText(tvNomeGrupo.getText().toString());
        editNomeNovoJogador = (EditText) findViewById(R.id.edit_nome_novo_jogador);
        //Contador Jogador
        contagemJogadores = (Button) findViewById(R.id.contagemJogadores);
        if (jogadores.size() == 0){
            contagemJogadores.setText("0");
        }else if (jogadores.size() == 1){
            contagemJogadores.setText(jogadores.size()+"");
        }else if (jogadores.size() >= 2){
            contagemJogadores.setText(jogadores.size()+"");
        }

        ListView listView = (ListView) findViewById(R.id.list_view_jogadores);
        //todo: Criar um custon adapter com um botão remover os jogadores
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, jogadores);
        listView.setAdapter(adapter);

        //botão ficar precionado
        registerForContextMenu(listView);

        editNomeNovoJogador.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jogadores.size() < 2) {
                    Toast.makeText(NovaPartida.this, getString(R.string.TextoIniciarPrecisa), Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(editTextNomePartida.getText().toString())) {
                    Toast.makeText(NovaPartida.this, R.string.NomeParaSuaPartida, Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(NovaPartida.this, PartidaAberta.class);
                    intent.putExtra("nomepartida", editTextNomePartida.getText().toString());
                    intent.putStringArrayListExtra("jogadores", jogadores);
                    intent.putExtra("partidaNova", true);
                    startActivity(intent);
                    finish();
                }

            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEditar.setVisibility(View.GONE);
                tvNomeGrupo.setVisibility(View.GONE);
                btnConfirmar.setVisibility(View.VISIBLE);
                editTextNomePartida.setVisibility(View.VISIBLE);
                Titulo.setText("Nome grupo de jogo");
                TextoInfo.setText("Por favor, preencha um nome curto para seu grupo.");
            }
        });

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    btnEditar.setVisibility(View.VISIBLE);
                    tvNomeGrupo.setVisibility(View.VISIBLE);
                    btnConfirmar.setVisibility(View.GONE);
                    editTextNomePartida.setVisibility(View.GONE);
                    tvNomeGrupo.setText(editTextNomePartida.getText().toString());
                    Titulo.setText("Dados do grupo");
                    Titulo.setTextColor(ContextCompat.getColor(NovaPartida.this, R.color.colorAccent800));
                    TextoInfo.setText(R.string.InfoDadosGrupo);
                    TextoInfo.setTextColor(ContextCompat.getColor(NovaPartida.this, R.color.colorCinza));

            }
        });

        novoJogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnIniciar.setVisibility(View.GONE);
                btnEditar.setVisibility(View.GONE);
                tvNomeGrupo.setVisibility(View.GONE);
                btnConfirmar.setVisibility(View.GONE);
                editTextNomePartida.setVisibility(View.GONE);
                novoJogador.setVisibility(View.GONE);
                btnSair.setVisibility(View.VISIBLE);
                editNomeNovoJogador.setVisibility(View.VISIBLE);
                btnAdicionarJogador.setVisibility(View.VISIBLE);
                Titulo.setText("Novo jogador");
                TextoInfo.setText("Minimo 2 jogadores e maxímo 10.");
            }
        });

        btnAdicionarJogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editNomeNovoJogador.getText().length() == 0){
                    editNomeNovoJogador.setError("Campo vazio");
                }else if (jogadores.size() < 10) {
                    btnIniciar.setVisibility(View.VISIBLE);
                    btnEditar.setVisibility(View.VISIBLE);
                    tvNomeGrupo.setVisibility(View.VISIBLE);
                    btnConfirmar.setVisibility(View.GONE);
                    editTextNomePartida.setVisibility(View.GONE);
                    novoJogador.setVisibility(View.VISIBLE);
                    btnSair.setVisibility(View.GONE);
                    editNomeNovoJogador.setVisibility(View.GONE);
                    btnAdicionarJogador.setVisibility(View.GONE);
                    Titulo.setText("Dados do grupo");
                    Titulo.setTextColor(ContextCompat.getColor(NovaPartida.this, R.color.colorAccent800));
                    TextoInfo.setText(R.string.InfoDadosGrupo);
                    TextoInfo.setTextColor(ContextCompat.getColor(NovaPartida.this, R.color.colorCinza));

                    jogadores.add(editNomeNovoJogador.getText().toString());
                    adapter.notifyDataSetChanged();
                    editNomeNovoJogador.setText(null);
                    if (jogadores.size() == 0){
                        contagemJogadores.setText("0");
                    }else if (jogadores.size() == 1){
                        contagemJogadores.setText(jogadores.size()+"");
                    }else if (jogadores.size() >= 2){
                        contagemJogadores.setText(jogadores.size()+"");
                    }

                } else {
                    btnSair.setVisibility(View.VISIBLE);
                    Titulo.setText("MAXÍMO 10 JOGADORES");
                    Titulo.setTextColor(Color.RED);
                    TextoInfo.setText("Você adicionou o numero maximo de jogadores.");
                    TextoInfo.setTextColor(Color.RED);

                }



            }
        });
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnIniciar.setVisibility(View.VISIBLE);
                btnEditar.setVisibility(View.VISIBLE);
                tvNomeGrupo.setVisibility(View.VISIBLE);
                btnConfirmar.setVisibility(View.GONE);
                editTextNomePartida.setVisibility(View.GONE);
                novoJogador.setVisibility(View.VISIBLE);
                btnSair.setVisibility(View.GONE);
                editNomeNovoJogador.setVisibility(View.GONE);
                btnAdicionarJogador.setVisibility(View.GONE);
                Titulo.setText("Dados do grupo");
                Titulo.setTextColor(ContextCompat.getColor(NovaPartida.this, R.color.colorAccent800));
                TextoInfo.setText(R.string.InfoDadosGrupo);
                TextoInfo.setTextColor(ContextCompat.getColor(NovaPartida.this, R.color.colorCinza));
            }
        });



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
