package com.darthorg.bozo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class PartidaAberta extends AppCompatActivity {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida_aberta);



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Nome da partida");
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewPagerMarcadorJogador);
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutJogadores);

        viewPager.setAdapter(new TabLayoutJogadores(this, getSupportFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);
        int corOn = ContextCompat.getColor(this, R.color.colorBlack);
        int corOff = ContextCompat.getColor(this, R.color.colorBlackTransparente);
        int corBarra = ContextCompat.getColor(this, R.color.colorBlack);
        tabLayout.setTabTextColors(corOff, corOn);
        tabLayout.setSelectedTabIndicatorColor(corBarra);
        tabLayout.setSelectedTabIndicatorHeight(7);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE); // Adiciona o scroll horizontal para as tabs

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.partida_aberta_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_placar) {
            Intent intent = new Intent(this,Placar.class);
            startActivity(intent);
            //return true;
        }
        else if (id == R.id.action_add_jogador) {

                    LayoutInflater inflater = getLayoutInflater();
                    //Recebe a activity para persolnalizar o dialog
                    View dialogLayout = inflater.inflate(R.layout.theme_dialog_novo_jogador, null);

                    AlertDialog.Builder builder = new AlertDialog.Builder(PartidaAberta.this);
                    builder.setTitle("Novo jogador");
                    builder.setPositiveButton("Ok",null);
                    builder.setNegativeButton("Cancelar",null);
                    builder.setView(dialogLayout);
                    builder.show();
            //return true;
        }
        else if (id == R.id.action_sair) {
            finish();
            //return true;
        }
        else if (id == android.R.id.home) {
            AlertDialog.Builder builder = new AlertDialog.Builder(PartidaAberta.this);
            builder.setTitle("Sair da partida");
            builder.setMessage("Gostaria mesmo de sair da partida ?");
            builder.setPositiveButton("Sim",null);
            builder.setNegativeButton("Não",null);
            builder.show();


        }

        return super.onOptionsItemSelected(item);
    }



}
