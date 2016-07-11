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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.darthorg.bozo.R;
import com.darthorg.bozo.adapter.TabsDinamicosAdapter;
import com.darthorg.bozo.controller.JogadorController;
import com.darthorg.bozo.controller.PartidaController;
import com.darthorg.bozo.controller.RodadaController;
import com.darthorg.bozo.dao.JogadorDAO;
import com.darthorg.bozo.dao.PartidaDAO;
import com.darthorg.bozo.dao.RodadaDAO;
import com.darthorg.bozo.fragment.FragmentFilho;
import com.darthorg.bozo.model.Jogador;
import com.darthorg.bozo.model.Partida;
import com.darthorg.bozo.model.Rodada;

import java.util.ArrayList;
import java.util.List;

public class PartidaAberta extends AppCompatActivity {

    private Partida partida;
    private Rodada rodada = new Rodada();
    private Jogador jogador = new Jogador();
    private List<Partida> partidas = new ArrayList<Partida>();

    private PartidaController partidaController;
    private RodadaController rodadaController;
    private JogadorController jogadorController;
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

        //Busca a partida atual
        partida = buscarPartida();

        // teste para ver se cria as rodadas
        rodada.setIdPartida(partida.getIdPartida());
        RodadaDAO rodadaDAO = new RodadaDAO(this);
        rodadaDAO.novaRodada(rodada);

        Rodada rodadaAtual = buscarRodada(partida.getIdPartida());

        jogador.setNome("Jorgewal");
        jogador.setIdRodada(rodadaAtual.getIdRodada());

        JogadorDAO jogadorDAO = new JogadorDAO(this);
        jogadorDAO.novoJogador(jogador);

        //Busca os Ids nos Xml
        getIDs();

        toolbar.setTitle(partida.getNome());
        setSupportActionBar(toolbar);

        adapter = new TabsDinamicosAdapter(getSupportFragmentManager(), PartidaAberta.this, viewPager, tabLayout);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        int corOn = ContextCompat.getColor(this, R.color.colorBlack);
        int corOff = ContextCompat.getColor(this, R.color.colorBlackTransparente);
        int corBarra = ContextCompat.getColor(this, R.color.colorBlack);
        tabLayout.setTabTextColors(corOff, corOn);
        tabLayout.setSelectedTabIndicatorColor(corBarra);
        tabLayout.setSelectedTabIndicatorHeight(7);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void getIDs() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewPagerMarcadorJogador);
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutJogadores);
    }

    public Partida buscarPartida() {
        Intent intent = getIntent();
        bundle = intent.getExtras();
        Partida partidaAtual;

        PartidaDAO partidaDAO = new PartidaDAO(this);
        partidaAtual = partidaDAO.buscarPartidaPorNome(bundle.getString("nomepartida"));
        Log.i("bugsinistro", "nome : " + partidaAtual.getNome() + " " + " id : " + partidaAtual.getIdPartida());
        return partidaAtual;
    }

    public Rodada buscarRodada(long idRodada) {

        Rodada rodadaAtual;

        RodadaDAO rodadaDAO = new RodadaDAO(this);
        rodadaAtual = rodadaDAO.buscarRodadaPorPartida(idRodada);
        //Log.i("bugsinistro", "Rodada : " + rodadaAtual.getIdRodada() + "da partida : " + rodadaAtual.getIdPartida());
        return rodadaAtual;
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
                    //TODO: Implementar o metodo para salvar a partida
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
            if (adapter.getCount() > 0) {

                adapter.removeFrag(viewPager.getCurrentItem());
                adapter.notifyDataSetChanged();
                // vincula denovo o viewpager com o tablayout
                tabLayout.setupWithViewPager(viewPager);
                // coloca como o item atual o ultimo
                viewPager.setCurrentItem(adapter.getCount() - 1);

            } else {
                Toast.makeText(this, "Não tem mais jogadores para excluir", Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.action_bloquear_som) {
        } else if (id == R.id.action_configuracoes) {
            return true;
        } else if (id == R.id.action_sair) {
            AlertDialog.Builder builder = new AlertDialog.Builder(PartidaAberta.this);
            builder.setMessage("Deseja salvar antes de Sair ?");
            builder.setCancelable(true);
            builder.setPositiveButton("Sim ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //TODO: Implementar o metodo para salvar a partida
                    finish();
                }
            });

            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PartidaDAO partidaDAO = new PartidaDAO(PartidaAberta.this);
                    partidaDAO.deletarPartida(partida);
                    finish();
                }
            });

            builder.show();
        } else if (id == android.R.id.home) {
            AlertDialog.Builder builder = new AlertDialog.Builder(PartidaAberta.this);
            builder.setMessage("Deseja salvar antes de Sair ?");
            builder.setPositiveButton("Salvar e sair", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ProgressDialog builder = new ProgressDialog(PartidaAberta.this);
                    builder.setMessage("Salvando só um momento...");
                    builder.show();

                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //TODO: Implementar o metodo para salvar a partida
                            Toast alertaMenssagem = Toast.makeText(getApplicationContext(), "Partida salva com sucesso", Toast.LENGTH_LONG);
                            alertaMenssagem.show();
                            finish();
                        }
                    }, Progress);
                }
            });
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PartidaDAO partidaDAO = new PartidaDAO(PartidaAberta.this);
                    partidaDAO.deletarPartida(partida);
                    finish();
                }
            });
            builder.show();


        }

        return super.onOptionsItemSelected(item);
    }


}
