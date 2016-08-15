package com.darthorg.bozo.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darthorg.bozo.R;
import com.darthorg.bozo.model.Jogador;

import java.util.ArrayList;
import java.util.List;

public class NovaPartida extends AppCompatActivity {

    private Toolbar toolbar;
    private ArrayList<String> jogadores = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    TextView tvNomeGrupo,Titulo,TextoInfo;
    EditText editTextNomePartida,editNomeNovoJogador;
    ImageButton novoJogador,btnSair,btnVoltar,btnAdicionarJogador;
    Button contagemJogadores,contagemJogadores2,btnIniciar,btnOk, btnSalvar,btnEditar;
    RelativeLayout paginaDadosGrupo;
    ListView listView;
    MenuItem actionInicar;
    private List<Jogador> jogadorList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_partida);

        novoJogador = (ImageButton) findViewById(R.id.novoJogador);
        btnEditar = (Button) findViewById(R.id.btnEditar);
        btnSalvar = (Button) findViewById(R.id.btnExcluir);
        btnSair = (ImageButton) findViewById(R.id.btnSair);
        btnVoltar = (ImageButton) findViewById(R.id.btnVoltar);
        btnAdicionarJogador = (ImageButton) findViewById(R.id.btnAdicionarJogador);
        contagemJogadores = (Button) findViewById(R.id.contagemJogadores);
        contagemJogadores2 = (Button) findViewById(R.id.contagemJogadores2);
        btnIniciar = (Button) findViewById(R.id.btnIniciar);
        tvNomeGrupo = (TextView) findViewById(R.id.tvNomeGrupo);
        Titulo = (TextView) findViewById(R.id.Titulo);
        TextoInfo = (TextView) findViewById(R.id.TextoInfo);
        editTextNomePartida = (EditText) findViewById(R.id.editText_nomePartida);
        editTextNomePartida.setText(tvNomeGrupo.getText().toString());
        editNomeNovoJogador = (EditText) findViewById(R.id.edit_nome_novo_jogador);
        btnOk = (Button) findViewById(R.id.btnOk);
        paginaDadosGrupo = (RelativeLayout) findViewById(R.id.paginaDadosGrupo);


        //Contador Jogador
        contagemJogadores = (Button) findViewById(R.id.contagemJogadores);
        if (jogadores.size() == 0){
            contagemJogadores.setText("0");
            contagemJogadores2.setText("0");
        }else if (jogadores.size() == 1){
            contagemJogadores.setText(jogadores.size()+"");
            contagemJogadores2.setText(jogadores.size()+"");
        }else if (jogadores.size() >= 2){
            contagemJogadores.setText(jogadores.size()+"");
            contagemJogadores2.setText(jogadores.size()+"");
        }

        listView = (ListView) findViewById(R.id.list_view_jogadores);
        //todo: Criar um custon adapter com um botão remover os jogadores
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, jogadores);
        listView.setAdapter(adapter);

        //botão ficar precionado
        registerForContextMenu(listView);

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
                btnVoltar.setVisibility(View.GONE);
                btnSair.setVisibility(View.VISIBLE);
                novoJogador.setVisibility(View.GONE);
                btnIniciar.setVisibility(View.GONE);
                contagemJogadores.setVisibility(View.GONE);
                tvNomeGrupo.setVisibility(View.GONE);
                btnSalvar.setVisibility(View.VISIBLE);
                editTextNomePartida.setVisibility(View.VISIBLE);
                Titulo.setText("Nome grupo de jogo");
                TextoInfo.setText("Por favor, preencha um nome curto para seu grupo.");
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    btnEditar.setVisibility(View.VISIBLE);
                    tvNomeGrupo.setVisibility(View.VISIBLE);
                    btnSalvar.setVisibility(View.GONE);
                    btnSair.setVisibility(View.GONE);
                    btnVoltar.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.VISIBLE);
                    novoJogador.setVisibility(View.VISIBLE);
                    contagemJogadores.setVisibility(View.VISIBLE);
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
                btnVoltar.setVisibility(View.GONE);
                btnSair.setVisibility(View.VISIBLE);
                btnIniciar.setVisibility(View.GONE);
                btnEditar.setVisibility(View.GONE);
                tvNomeGrupo.setVisibility(View.GONE);
                btnSalvar.setVisibility(View.GONE);
                contagemJogadores.setVisibility(View.GONE);
                contagemJogadores2.setVisibility(View.VISIBLE);
                editTextNomePartida.setVisibility(View.GONE);
                novoJogador.setVisibility(View.GONE);
                editNomeNovoJogador.setVisibility(View.VISIBLE);
                btnAdicionarJogador.setVisibility(View.VISIBLE);
                Titulo.setText("Novo jogador");
                TextoInfo.setText("Minimo 2 jogadores e maxímo 10.");
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTheme(R.style.AppTheme);
                btnIniciar.setVisibility(View.VISIBLE);
                btnEditar.setVisibility(View.VISIBLE);
                tvNomeGrupo.setVisibility(View.VISIBLE);
                btnSalvar.setVisibility(View.GONE);
                btnOk.setVisibility(View.GONE);
                editTextNomePartida.setVisibility(View.GONE);
                contagemJogadores.setVisibility(View.VISIBLE);
                contagemJogadores2.setVisibility(View.GONE);
                novoJogador.setVisibility(View.VISIBLE);
                btnSair.setVisibility(View.GONE);
                btnVoltar.setVisibility(View.VISIBLE);
                editNomeNovoJogador.setVisibility(View.GONE);
                btnAdicionarJogador.setVisibility(View.GONE);
                Titulo.setText("Dados do grupo");
                Titulo.setTextColor(ContextCompat.getColor(NovaPartida.this, R.color.colorAccent800));
                TextoInfo.setText(R.string.InfoDadosGrupo);
                TextoInfo.setTextColor(ContextCompat.getColor(NovaPartida.this, R.color.colorCinza));
            }
        });

        btnAdicionarJogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editNomeNovoJogador.getText().length() == 0){
                    editNomeNovoJogador.setError("Campo vazio");
                }else if (jogadores.size() < 10) {
                    jogadores.add(editNomeNovoJogador.getText().toString());
                    adapter.notifyDataSetChanged();
                    editNomeNovoJogador.setText(null);
                    if (jogadores.size() == 0){
                        contagemJogadores.setText("0");
                        contagemJogadores2.setText("0");
                    }else if (jogadores.size() == 1){
                        contagemJogadores.setText(jogadores.size()+"");
                        contagemJogadores2.setText(jogadores.size()+"");
                    }else if (jogadores.size() >= 2){
                        contagemJogadores.setText(jogadores.size()+"");
                        contagemJogadores2.setText(jogadores.size()+"");
                    }

                } else {
                    btnOk.setVisibility(View.VISIBLE);
                    btnSair.setVisibility(View.GONE);
                    editNomeNovoJogador.setVisibility(View.GONE);
                    btnAdicionarJogador.setVisibility(View.GONE);
                    contagemJogadores2.setVisibility(View.GONE);
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
                btnSalvar.setVisibility(View.GONE);
                editTextNomePartida.setVisibility(View.GONE);
                contagemJogadores.setVisibility(View.VISIBLE);
                contagemJogadores2.setVisibility(View.GONE);
                novoJogador.setVisibility(View.VISIBLE);
                btnSair.setVisibility(View.GONE);
                btnVoltar.setVisibility(View.VISIBLE);
                editNomeNovoJogador.setVisibility(View.GONE);
                btnAdicionarJogador.setVisibility(View.GONE);
                Titulo.setText("Dados do grupo");
                Titulo.setTextColor(ContextCompat.getColor(NovaPartida.this, R.color.colorAccent800));
                TextoInfo.setText(R.string.InfoDadosGrupo);
                TextoInfo.setTextColor(ContextCompat.getColor(NovaPartida.this, R.color.colorCinza));
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
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
