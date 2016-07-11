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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.darthorg.bozo.R;
import com.darthorg.bozo.adapter.TabsDinamicosAdapter;
import com.darthorg.bozo.controller.PartidaController;
import com.darthorg.bozo.fragment.FragmentFilho;
import com.darthorg.bozo.model.Partida;

public class PartidaAberta extends AppCompatActivity {

    private PartidaController partidaController;
    private Partida partida = new Partida();
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    private Bundle bundle;
    private TabsDinamicosAdapter adapter;

    private final int Progress = 1000;
    private final int ProgressSalvar = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida_aberta);

        Intent intent = getIntent();
        bundle = intent.getExtras();
        partida.setNome(bundle.getString("nomepartida"));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(partida.getNome());

        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewPagerMarcadorJogador);
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutJogadores);

        adapter = new TabsDinamicosAdapter(getSupportFragmentManager(), PartidaAberta.this, viewPager, tabLayout);
        viewPager.setAdapter(adapter);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.partida_aberta_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_salvar_partida) {

            //ProgressDialog Função carregar
            final ProgressDialog builder = new ProgressDialog(PartidaAberta.this);
            builder.setMessage("Salvando só um momento...");
            builder.show();

            //Tepo que a barra vai demorar para carregar
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    partidaController = new PartidaController(PartidaAberta.this);
                    if (partidaController.inserirPartida(partida)) {
                        //Menssagem que vai aparecer após o salvamento
                        Toast.makeText(PartidaAberta.this, "Partida salva com sucesso", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(PartidaAberta.this, "Ocorreu um erro", Toast.LENGTH_LONG).show();
                    }
                    builder.dismiss();
                }
            }, ProgressSalvar);
        } else if (id == R.id.action_add_jogador) {

            FragmentFilho fragmentFilho = new FragmentFilho();
            adapter.addFrag(fragmentFilho, "teste");
            adapter.notifyDataSetChanged();
            if (adapter.getCount() > 0) {
                tabLayout.setupWithViewPager(viewPager);
                viewPager.setCurrentItem(adapter.getCount() - 1);
            }


        } else if (id == R.id.action_placar) {
            Intent intent = new Intent(this, ListaDePlacar.class);
            startActivity(intent);
        } else if (id == R.id.action_excluir_este_jogador) {
            adapter.removeFrag(viewPager.getCurrentItem());
            adapter.notifyDataSetChanged();

            if (adapter.getCount() > 0) {
                tabLayout.setupWithViewPager(viewPager);
                viewPager.setCurrentItem(adapter.getCount() - 1);
            }

        } else if (id == R.id.action_bloquear_som) {
        } else if (id == R.id.action_configuracoes) {
            return true;
        } else if (id == R.id.action_sair) {
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
                            Toast alertaMenssagem = Toast.makeText(getApplicationContext(), "Partida salva com sucesso", Toast.LENGTH_LONG);
                            alertaMenssagem.show();
                            finish();
                        }
                    }, Progress);
                }
            });
            builder.setNegativeButton("Não", null);
            builder.show();
        } else if (id == android.R.id.home) {
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
                            Toast alertaMenssagem = Toast.makeText(getApplicationContext(), "Partida salva com sucesso", Toast.LENGTH_LONG);
                            alertaMenssagem.show();
                            finish();
                        }
                    }, Progress);
                }
            });
            builder.setNegativeButton("Não", null);
            builder.show();


        }

        return super.onOptionsItemSelected(item);
    }


}
