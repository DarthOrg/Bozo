package com.darthorg.bozo;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Inicio extends AppCompatActivity {


    private Toolbar toolbar;
    Button btnJogar, btnConfiguracoes, btnSobre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Marcador Bozó");
        setSupportActionBar(toolbar);

        btnJogar = (Button) findViewById(R.id.btn_jogar);
        btnConfiguracoes = (Button) findViewById(R.id.btn_configuracoes);
        btnSobre = (Button) findViewById(R.id.btn_sobre);

        btnJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inicio.this,NovaPartida.class);
                startActivity(intent);
            }
        });
        btnConfiguracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(Inicio.this,Configuracoes.class);
                //startActivity(intent);
            }
        });
        btnSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(Inicio.this,Sobre.class);
                //startActivity(intent);
            }
        });

        String[] partidas = {"Partida 01",
                "Partida 02",
                "Partida 03",
                "Partida 04",
                "Partida 05",
                "Partida 06",
                "Partida 07",
                "Partida 08",
                "Partida 09",
                "Partida 10",
                "Partida 11",
                "Partida 12",
                "Partida 13",
                "Partida 14",
                "Partida 15",
                "Partida 16",
                "Partida 17",
                "Partida 18",
                "Partida 19",
                "Partida 20",
        };

        ListView listView = (ListView) findViewById(R.id.list_view_partidas);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, partidas);
        listView.setAdapter(adapter);

    }



    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.inicio_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_nova_partida) {
            //Intent intent = new Intent(this,NovaPartida.class);
            //startActivity(intent);
            //return true;

        }

        return super.onOptionsItemSelected(item);
    }


}
