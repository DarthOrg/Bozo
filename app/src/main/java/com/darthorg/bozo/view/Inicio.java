package com.darthorg.bozo.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
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

import com.darthorg.bozo.R;

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


        com.melnykov.fab.FloatingActionButton fab = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.btn_jogar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                //Recebe a activity para persolnalizar o dialog
                View dialogLayout = inflater.inflate(R.layout.theme_dialog_inicio, null);
                //Botão nova partida
                Button btnNovaPartida = (Button) dialogLayout.findViewById(R.id.btn_nova_partida);
                btnNovaPartida.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Inicio.this, NovaPartida.class);
                        startActivity(intent);
                    }
                });
                //Botão Suas partidas
                Button btnPartidasSalvas = (Button) dialogLayout.findViewById(R.id.btn_partidas_salvas);
                btnPartidasSalvas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Inicio.this, ListaDePartidas.class);
                        startActivity(intent);
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(Inicio.this);
                //builder.setNegativeButton("Cancelar",null);
                builder.setView(dialogLayout);
                builder.show();




            }
        });


        /*//Botão de informção do dado (Ás(01))
        btnInfoAs = (Button) findViewById(R.id.btn_info_as);
        btnInfoAs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Inicio.this);
                builder.setTitle("ÁZ ou ( Número 01 )");
                builder.setMessage("[ Áz ] é uma pedra de menor valor no Bozó. " +
                        " Sua posição fica no canto superior á esquerda," +
                        " soma-se os valores da pedra de áz." +
                        " sua pontuação varia de 1 à 5 pontos");
                builder.setIcon(R.drawable.ic_um);
                builder.setNegativeButton("instruções",null);
                builder.setPositiveButton("Entendi",null);
                builder.show();
            }
        });*/

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inicio_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_configuracoes) {
            //Intent intent = new Intent(this,ListaDePlacar.class);
            //startActivity(intent);
            return true;
        } else if (id == R.id.action_sobre) {
            //Intent intent = new Intent(this,ListaDePlacar.class);
            //startActivity(intent);
            return true;
        } else if (id == R.id.action_bloquear_som) {
            //Intent intent = new Intent(this,ListaDePlacar.class);
            //startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
