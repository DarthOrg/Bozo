package com.darthorg.bozo.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.darthorg.bozo.R;
import com.darthorg.bozo.controller.JogadorController;
import com.darthorg.bozo.fragment.FragmentFilho;
import com.darthorg.bozo.model.Jogador;
import com.github.clans.fab.FloatingActionMenu;

public class Inicio extends AppCompatActivity {


    private Toolbar toolbar;
    Button btnJogar, btnInfoAs, btnInfoDuque, btnInfoTerno, btnInfoQuadra, btnInfoQuina, btnInfoSena;
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
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);


        ImageButton btnJogar = (ImageButton) findViewById(R.id.btn_jogar);
        btnJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                //Recebe a activity para persolnalizar o dialog
                View dialogLayout = inflater.inflate(R.layout.dialog_inicio, null);
                //Botão nova partida
                Button btnNovaPartida = (Button) dialogLayout.findViewById(R.id.btn_nova_partida);
                btnNovaPartida.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Inicio.this, NovaPartida.class);
                        startActivity(intent);
                    }
                });
                //Botão grupo salvos
                Button btnPartidasSalvas = (Button) dialogLayout.findViewById(R.id.btn_partidas_salvas);
                btnPartidasSalvas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Inicio.this, ListaDePartidas.class);
                        startActivity(intent);
                    }
                });
                //Botão Configurações
                Button btnConfiguracoes = (Button) dialogLayout.findViewById(R.id.btn_configuracoes);
                btnConfiguracoes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        return;
                    }
                });
                //Botão Instruções
                Button btnInstrucoes = (Button) dialogLayout.findViewById(R.id.btn_instrucoes);
                btnInstrucoes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        return;
                    }
                });
                //Botão Sobre
                Button btnSobre = (Button) dialogLayout.findViewById(R.id.btn_sobre);
                btnSobre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        return;
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(Inicio.this);
                //builder.setNegativeButton("Cancelar",null);
                builder.setView(dialogLayout);
                builder.show();
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
            //Intent intent = new Intent(this,ListaDePlacar.class);
            //startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
