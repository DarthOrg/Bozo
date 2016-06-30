package com.darthorg.bozo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class NomeJogadores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nome_jogadores);

        setTitle("Jogadores");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.nome_jogadores_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_add_jogador) {
            //Intent intent = new Intent(this,NomeJogadores.class);
            //startActivity(intent);
            return true;
        }else if (id == R.id.action_iniciar_partida){
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
