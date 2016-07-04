package com.darthorg.bozo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaDePartidas extends AppCompatActivity {


    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_partidas);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Suas partidas");
        setSupportActionBar(toolbar);

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
