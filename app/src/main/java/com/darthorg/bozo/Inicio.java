package com.darthorg.bozo;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Inicio extends AppCompatActivity {


    private Toolbar toolbar;
    Button btnJogar;
    private int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);



        btnJogar = (Button) findViewById(R.id.btn_jogar);

        btnJogar.setOnClickListener(new View.OnClickListener() {


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
            //Intent intent = new Intent(this,Placar.class);
            //startActivity(intent);
            return true;
        } else if (id == R.id.action_sobre) {
            //Intent intent = new Intent(this,Placar.class);
            //startActivity(intent);
            return true;
        } else if (id == R.id.action_bloquear_som) {
            //Intent intent = new Intent(this,Placar.class);
            //startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
