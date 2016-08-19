package com.darthorg.bozo.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.darthorg.bozo.R;

public class Inicio extends AppCompatActivity {


    private Toolbar toolbar;
    Button btnGruposSalvos,btnConfiguracoes, btnInstrucoes, btnInstrucoesDois;
    ImageButton btnNovaPartida, menu,sair;
    ImageView imgTopo, imgBottom;
    TextView btnSobre,tituloInfo,textoInfo;
    RelativeLayout botaoAz,botaoDuque,botaoTerno,botaoQuadra,botaoQuina,botaoSena,conteudoInfo,textoBozo;
    LinearLayout qualquer;
    private int mId;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.TextoVazio);
        setSupportActionBar(toolbar);

        //Botao Novo Grupo
        btnNovaPartida = (ImageButton) findViewById(R.id.btnNovaPartida);
        btnNovaPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inicio.this, NovaPartida.class);
                    Inicio.this.startActivity(intent);



            }
        });

        btnSobre = (TextView) findViewById(R.id.btnSobre);
        btnSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inicio.this, Sobre.class);
                startActivity(intent);
            }
        });

        //Fab Grupos Salvos
        btnGruposSalvos = (Button) findViewById(R.id.btnGruposSalvos);
        btnGruposSalvos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inicio.this, ListaDePartidas.class);
                startActivity(intent);
            }
        });

        //Fab Configurações
        btnConfiguracoes = (Button) findViewById(R.id.btnConfiguracoes);
        btnConfiguracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return;
            }
        });


    }


    //Botão voltar
    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inicio_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_bloquear_som) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
