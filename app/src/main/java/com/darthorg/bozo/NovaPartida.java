package com.darthorg.bozo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.melnykov.fab.ScrollDirectionListener;

public class NovaPartida extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_partida);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Nova partida");
        setSupportActionBar(toolbar);



        String[] jogadores = {"Jogador 01","Jogador 02"};

        ListView listView = (ListView) findViewById(R.id.list_view_jogadores);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, jogadores);
        listView.setAdapter(adapter);

        com.melnykov.fab.FloatingActionButton fab = (com.melnykov.fab.FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                //Recebe a activity para persolnalizar o dialog
                View dialogLayout = inflater.inflate(R.layout.theme_dialog_novo_jogador, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(NovaPartida.this);
                builder.setTitle("Novo jogador");
                builder.setPositiveButton("Ok",null);
                builder.setNegativeButton("Cancelar",null);
                builder.setView(dialogLayout);
                builder.show();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.nova_partida_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_inciar_partida) {
            Intent intent = new Intent(this,PartidaAberta.class);
            startActivity(intent);
            //return true;
        }
        else if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
