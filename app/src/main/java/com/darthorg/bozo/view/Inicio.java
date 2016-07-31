package com.darthorg.bozo.view;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.darthorg.bozo.R;

public class Inicio extends AppCompatActivity {


    private Toolbar toolbar;
    Button btnGruposSalvos,btnConfiguracoes, btnInstrucoes;
    ImageButton btnNovaPartida;
    private int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_inicio);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.TextoVazio);
        setSupportActionBar(toolbar);

        //Fab Novo Grupo
        btnNovaPartida = (ImageButton) findViewById(R.id.btnNovaPartida);
        btnNovaPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inicio.this, NovaPartida.class);
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
