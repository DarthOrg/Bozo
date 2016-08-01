package com.darthorg.bozo.view;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
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
    TextView tvNomeGrupo;
    EditText etNovaPartida,editNomePartida,etNomeJogador;
    ImageButton btnConfirmar, btnEditar;
    FrameLayout flEditText, flTextView, flFundoBtn;
    Button btnAdicionar,btnAddJogador, contadorJogador, contadorJogadorMaximo, btnIniciar;
    MenuItem actionInicar;
    private List<Jogador> jogadorList;


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_nova_partida);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.TextoVazio);
        setSupportActionBar(toolbar);

        //Contador Jogador
        contadorJogador = (Button) findViewById(R.id.contagemJogadores);
        contadorJogadorMaximo = (Button) findViewById(R.id.contagemJogadoresMaximo);
        if (jogadores.size() == 0){
            contadorJogador.setText("Nenhum jogador");
        }else if (jogadores.size() == 1){
            contadorJogador.setText(jogadores.size()+" jogador");
        }else if (jogadores.size() >= 2){
            contadorJogador.setText(jogadores.size()+" jogadores");
        }


        ListView listView = (ListView) findViewById(R.id.list_view_jogadores);
        //todo: Criar um custon adapter com um botão remover os jogadores
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, jogadores);
        listView.setAdapter(adapter);


        //botão ficar precionado
        registerForContextMenu(listView);

        etNomeJogador = (EditText) findViewById(R.id.edit_nome_novo_jogador);

        tvNomeGrupo = (TextView) findViewById(R.id.tvNomeGrupo);
        editNomePartida = (EditText) findViewById(R.id.editText_nomePartida);

        btnConfirmar = (ImageButton) findViewById(R.id.btnConfirmar);
        btnIniciar = (Button) findViewById(R.id.btnIniciar);

        flTextView = (FrameLayout) findViewById(R.id.frameLayout_TextView);
        flEditText = (FrameLayout) findViewById(R.id.frameLayout_EditText);
        flFundoBtn = (FrameLayout) findViewById(R.id.frameLayout_fundoBtn);

        //Botão add jogador
        btnAddJogador = (Button) findViewById(R.id.novoJogador);
        btnAddJogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnAddJogador.setVisibility(View.GONE);
                btnAdicionar.setVisibility(View.VISIBLE);
                etNomeJogador.setVisibility(View.VISIBLE);

            }
        });

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etNovaPartida = (EditText) findViewById(R.id.editText_nomePartida);

                if (jogadores.size() < 2) {
                    Toast.makeText(NovaPartida.this, getString(R.string.TextoIniciarPrecisa), Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(etNovaPartida.getText().toString())) {
                    Toast.makeText(NovaPartida.this, R.string.NomeParaSuaPartida, Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(NovaPartida.this, PartidaAberta.class);
                    intent.putExtra("nomepartida", etNovaPartida.getText().toString());
                    intent.putStringArrayListExtra("jogadores", jogadores);
                    intent.putExtra("partidaNova", true);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnAdicionar = (Button) findViewById(R.id.btnAdicionarJogador);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnAddJogador.setVisibility(View.VISIBLE);
                btnAdicionar.setVisibility(View.GONE);
                etNomeJogador.setVisibility(View.GONE);

                if (jogadores.size() < 10) {
                    jogadores.add(etNomeJogador.getText().toString());
                    adapter.notifyDataSetChanged();
                    if (jogadores.size() == 0){
                        contadorJogador.setText("Nenhum jogador");
                    }else if (jogadores.size() == 1){
                        contadorJogador.setText(jogadores.size()+" jogador");
                    }else if (jogadores.size() >= 2 ){
                        contadorJogador.setText(jogadores.size()+" jogadores");
                    }

                } else {
                    contadorJogadorMaximo.setText("Maximo "+jogadores.size()+" jogadores");
                    contadorJogadorMaximo.setTextColor(Color.WHITE);
                    contadorJogador.setVisibility(View.GONE);
                    contadorJogadorMaximo.setVisibility(View.VISIBLE);
                    toolbar.setBackgroundColor(ContextCompat.getColor(NovaPartida.this,R.color.colorRed));
                    flTextView.setBackgroundColor(ContextCompat.getColor(NovaPartida.this,R.color.colorRed));
                    flEditText.setBackgroundColor(ContextCompat.getColor(NovaPartida.this,R.color.colorRed));
                    flFundoBtn.setBackgroundColor(ContextCompat.getColor(NovaPartida.this,R.color.colorRedDark));
                    btnAddJogador.setVisibility(View.GONE);
                }
            }
        });


        tvNomeGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flTextView.setVisibility(View.GONE);
                flEditText.setVisibility(View.VISIBLE);
                btnIniciar.setVisibility(View.GONE);
                btnConfirmar.setVisibility(View.VISIBLE);
                flEditText.setBackgroundColor(ContextCompat.getColor(NovaPartida.this,R.color.colorGreen));
                flFundoBtn.setBackgroundColor(ContextCompat.getColor(NovaPartida.this,R.color.colorGreenDark));
                toolbar.setBackgroundColor(ContextCompat.getColor(NovaPartida.this,R.color.colorGreen));
            }
        });

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editNomePartida.getText().toString();
                tvNomeGrupo.setText(name);
                flTextView.setVisibility(View.VISIBLE);
                flEditText.setVisibility(View.GONE);
                btnIniciar.setVisibility(View.VISIBLE);
                btnConfirmar.setVisibility(View.GONE);
                flTextView.setBackgroundColor(ContextCompat.getColor(NovaPartida.this,R.color.colorAccent));
                flFundoBtn.setBackgroundColor(ContextCompat.getColor(NovaPartida.this,R.color.colorFABPressedAccent));
                toolbar.setBackgroundColor(ContextCompat.getColor(NovaPartida.this,R.color.colorAccent));

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
