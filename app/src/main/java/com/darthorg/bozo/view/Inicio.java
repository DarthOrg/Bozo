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
        btnInstrucoesDois = (Button) findViewById(R.id.btnInstrucoesDois);
        btnInstrucoesDois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inicio.this, NovaPartida.class);
                Inicio.this.startActivity(intent);
                menu.setVisibility(View.VISIBLE);
                sair.setVisibility(View.GONE);
                btnInstrucoesDois.setVisibility(View.GONE);
                botaoAz.setVisibility(View.GONE);
                botaoDuque.setVisibility(View.GONE);
                botaoTerno.setVisibility(View.GONE);
                botaoQuadra.setVisibility(View.GONE);
                botaoQuina.setVisibility(View.GONE);
                botaoSena.setVisibility(View.GONE);
                imgTopo.setVisibility(View.VISIBLE);
                conteudoInfo.setVisibility(View.GONE);
                btnNovaPartida.setVisibility(View.VISIBLE);
                textoBozo.setVisibility(View.VISIBLE);
                qualquer.setVisibility(View.VISIBLE);
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

        //Fab Instruções
        btnInstrucoes = (Button) findViewById(R.id.btnInstrucoes);
        btnInstrucoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                return;
            }
        });

        menu = (ImageButton) findViewById(R.id.menu);
        sair = (ImageButton) findViewById(R.id.sair);
        qualquer = (LinearLayout) findViewById(R.id.qualquer);
        botaoAz = (RelativeLayout) findViewById(R.id.botaoAz);
        botaoDuque = (RelativeLayout) findViewById(R.id.botaoDuque);
        botaoTerno = (RelativeLayout) findViewById(R.id.botaoTerno);
        botaoQuadra = (RelativeLayout) findViewById(R.id.botaoQuadra);
        botaoQuina = (RelativeLayout) findViewById(R.id.botaoQuina);
        botaoSena = (RelativeLayout) findViewById(R.id.botaoSena);
        conteudoInfo = (RelativeLayout) findViewById(R.id.conteudoInfo);
        textoBozo = (RelativeLayout) findViewById(R.id.textoBozo);
        tituloInfo = (TextView) findViewById(R.id.tituloInfo);
        textoInfo = (TextView) findViewById(R.id.textoInfo);
        imgTopo = (ImageView) findViewById(R.id.imgTopo);
        imgBottom = (ImageView) findViewById(R.id.imgBottom);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.setVisibility(View.GONE);
                sair.setVisibility(View.VISIBLE);
                btnInstrucoesDois.setVisibility(View.VISIBLE);
                botaoAz.setVisibility(View.VISIBLE);
                botaoDuque.setVisibility(View.VISIBLE);
                botaoTerno.setVisibility(View.VISIBLE);
                botaoQuadra.setVisibility(View.VISIBLE);
                botaoQuina.setVisibility(View.VISIBLE);
                botaoSena.setVisibility(View.VISIBLE);
                btnNovaPartida.setVisibility(View.GONE);
                textoBozo.setVisibility(View.GONE);
                qualquer.setVisibility(View.GONE);


            }
        });

        botaoAz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conteudoInfo.setVisibility(View.VISIBLE);
                tituloInfo.setText(R.string.Az);
                textoInfo.setText(R.string.TextoDadoUm);
                imgTopo.setVisibility(View.GONE);

            }
        });
        botaoDuque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conteudoInfo.setVisibility(View.VISIBLE);
                tituloInfo.setText(R.string.Duque);
                textoInfo.setText(R.string.TextoDadoDois);
                imgTopo.setVisibility(View.GONE);

            }
        });
        botaoTerno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conteudoInfo.setVisibility(View.VISIBLE);
                tituloInfo.setText(R.string.Terno);
                textoInfo.setText(R.string.TextoDadoTres);
                imgTopo.setVisibility(View.GONE);

            }
        });
        botaoQuadra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conteudoInfo.setVisibility(View.VISIBLE);
                tituloInfo.setText(R.string.Quadra);
                textoInfo.setText(R.string.TextoDadoQuatro);
                imgTopo.setVisibility(View.GONE);

            }
        });
        botaoQuina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conteudoInfo.setVisibility(View.VISIBLE);
                tituloInfo.setText(R.string.Quina);
                textoInfo.setText(R.string.TextoDadoCinco);
                imgTopo.setVisibility(View.GONE);

            }
        });
        botaoSena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conteudoInfo.setVisibility(View.VISIBLE);
                tituloInfo.setText(R.string.Sena);
                textoInfo.setText(R.string.TextoDadoSeis);
                imgTopo.setVisibility(View.GONE);

            }
        });

        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.setVisibility(View.VISIBLE);
                sair.setVisibility(View.GONE);
                btnInstrucoesDois.setVisibility(View.GONE);
                botaoAz.setVisibility(View.GONE);
                botaoDuque.setVisibility(View.GONE);
                botaoTerno.setVisibility(View.GONE);
                botaoQuadra.setVisibility(View.GONE);
                botaoQuina.setVisibility(View.GONE);
                botaoSena.setVisibility(View.GONE);
                imgTopo.setVisibility(View.VISIBLE);
                conteudoInfo.setVisibility(View.GONE);
                btnNovaPartida.setVisibility(View.VISIBLE);
                textoBozo.setVisibility(View.VISIBLE);
                qualquer.setVisibility(View.VISIBLE);

            }
        });
    }


    //Botão voltar
    @Override
    public void onBackPressed() {
        menu.setVisibility(View.VISIBLE);
        sair.setVisibility(View.GONE);
        btnInstrucoesDois.setVisibility(View.GONE);
        botaoAz.setVisibility(View.GONE);
        botaoDuque.setVisibility(View.GONE);
        botaoTerno.setVisibility(View.GONE);
        botaoQuadra.setVisibility(View.GONE);
        botaoQuina.setVisibility(View.GONE);
        botaoSena.setVisibility(View.GONE);
        imgTopo.setVisibility(View.VISIBLE);
        conteudoInfo.setVisibility(View.GONE);
        btnNovaPartida.setVisibility(View.VISIBLE);
        textoBozo.setVisibility(View.VISIBLE);
        qualquer.setVisibility(View.VISIBLE);

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
