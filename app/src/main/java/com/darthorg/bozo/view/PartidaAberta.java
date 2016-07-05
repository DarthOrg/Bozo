package com.darthorg.bozo.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import com.darthorg.bozo.R;

public class PartidaAberta extends AppCompatActivity {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private final int Progress = 1000;
    private final int ProgressSalvar = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida_aberta);



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Partida 01");
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


        if (id == R.id.action_salvar_partida) {

            //ProgressDialog Função carregar
            ProgressDialog builder = new ProgressDialog(PartidaAberta.this);
            builder.setMessage("Salvando só um momento...");
            builder.show();

            //Tepo que a barra vai demorar para carregar
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    //Activity que vai entrar e sair
                    startActivity(new Intent(PartidaAberta.this,PartidaAberta.class));
                    finish();
                    //Menssagem que vai aparecer após o salvamento
                    Toast alertaMenssagem = Toast.makeText(getApplicationContext(),"Partida salva com sucesso", Toast.LENGTH_LONG);
                    alertaMenssagem.show();
                }
            }, ProgressSalvar);
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
        }
        else if (id == R.id.action_placar) {
            Intent intent = new Intent(this,ListaDePlacar.class);
            startActivity(intent);
        }
        else if (id == R.id.action_excluir_este_jogador) {
        }
        else if (id == R.id.action_bloquear_som) {
        }
        else if (id == R.id.action_configuracoes) {
            return true;
        }
        else if (id == R.id.action_sair) {
            AlertDialog.Builder builder = new AlertDialog.Builder(PartidaAberta.this);
            builder.setMessage("Tem certeza que deseja salvar e sair ?");
            builder.setPositiveButton("Salvar e sair", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                    ProgressDialog builder = new ProgressDialog(PartidaAberta.this);
                    builder.setMessage("Salvando só um momento...");
                    builder.show();

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            Toast alertaMenssagem = Toast.makeText(getApplicationContext(),"Partida salva com sucesso", Toast.LENGTH_LONG);
                            alertaMenssagem.show();
                            finish();
                        }
                    }, Progress);
                }
            });
            builder.setNegativeButton("Não",null);
            builder.show();
        }

        else if (id == android.R.id.home) {
            AlertDialog.Builder builder = new AlertDialog.Builder(PartidaAberta.this);
            builder.setMessage("Tem certeza que deseja salvar e sair ?");
            builder.setPositiveButton("Salvar e sair", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


                    ProgressDialog builder = new ProgressDialog(PartidaAberta.this);
                    builder.setMessage("Salvando só um momento...");
                    builder.show();

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            Toast alertaMenssagem = Toast.makeText(getApplicationContext(),"Partida salva com sucesso", Toast.LENGTH_LONG);
                            alertaMenssagem.show();
                            finish();
                        }
                    }, Progress);
                }
            });
            builder.setNegativeButton("Não",null);
            builder.show();


        }

        return super.onOptionsItemSelected(item);
    }



}
