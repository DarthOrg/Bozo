package com.darthorg.bozo.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
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

    //Partida Atual
    private Partida partida;

    //Objetos que vao ser Manipulados durante a rodada
    private Rodada rodada = new Rodada();
    private List<Jogador> jogadoresRodada = new ArrayList<Jogador>();

    //Controllers
    private PartidaController partidaController;
    private RodadaController rodadaController;
    private JogadorController jogadorController;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    private TabsDinamicosAdapter adapter;

    private final int Progress = 1000;
    private final int ProgressSalvar = 1000;

    //Responsaveis por trazer os dados da Activity anterior
    private Intent intent;
    private Bundle bundleParams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_partida_aberta);

        //Busca os parametros passados por intent
        intent = getIntent();
        bundleParams = intent.getExtras();

        //Busca a partida atual
        partida = buscarPartida();

        //Busca os Ids nos Xml
        getIDs();

        //Configura o Toolbar
        toolbar.setTitle(partida.getNome());
        toolbar.setSubtitle("Marcador do bozó");
        toolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        //Configura o Adapter junto com o ViewPager
        adapter = new TabsDinamicosAdapter(getSupportFragmentManager(), PartidaAberta.this, viewPager, tabLayout);
        viewPager.setAdapter(adapter);

        //Configura o TabLayout com o ViewPager
        tabLayout.setupWithViewPager(viewPager);

        // Configura as Cores no TabLayout
        int corOn = ContextCompat.getColor(this, R.color.colorAccent);
        int corOff = ContextCompat.getColor(this, R.color.colorWhiteTransparente);
        int corBarra = ContextCompat.getColor(this, R.color.colorAccent);
        int corFundoTabLayoyt = ContextCompat.getColor(this, R.color.colorFundo);
        tabLayout.setBackgroundColor(corFundoTabLayoyt);
        tabLayout.setTabTextColors(corOff, corOn);
        tabLayout.setSelectedTabIndicatorColor(corBarra);
        tabLayout.setSelectedTabIndicatorHeight(7);

        //Configura a partida
        configurarPartida();

    }

    /**
     * Captura os Ids via classe R
     */
    public void getIDs() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewPagerMarcadorJogador);
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutJogadores);
    }

    /**
     * Busca pela partida por Nome
     *
     * @return Partida Atual
     */
    public Partida buscarPartida() {
        Partida partidaAtual;

        PartidaDAO partidaDAO = new PartidaDAO(this);
        partidaAtual = partidaDAO.buscarPartidaPorNome(bundleParams.getString("nomepartida"));
        Log.i("partidaAtual", "nome : " + partidaAtual.getNome() + " " + " id : " + partidaAtual.getIdPartida());
        return partidaAtual;
    }

    /**
     * Busca Rodada por Partida
     *
     * @param idPartida
     * @return Objeto Rodada
     */
    public Rodada buscarRodada(long idPartida) {
        Rodada rodada;

        RodadaDAO rodadaDAO = new RodadaDAO(this);
        rodada = rodadaDAO.buscarRodadaPorPartida(idPartida);
        return rodada;
    }

    /**
     * Busca jogador por nome no banco
     *
     * @param nome
     * @return
     */
    public Jogador buscarJogador(String nome) {
        Jogador jogador;
        JogadorDAO jogadorDAO = new JogadorDAO(this);
        jogador = jogadorDAO.buscarJogadorPorNome(nome);

        return jogador;
    }

    /**
     * Configura a partida de acordo com os jogadores
     */
    public void configurarPartida() {

        rodada.setIdPartida(partida.getIdPartida());
        rodadaController = new RodadaController(this);
        jogadorController = new JogadorController(this);
        rodadaController.inserirRodada(rodada);

        if (intent != null) {
            if (bundleParams != null) {
                ArrayList<String> jogadoresIniciais = bundleParams.getStringArrayList("jogadores");
                rodada = buscarRodada(partida.getIdPartida());


                //Todo: Criar aqui os jogadores da partida
                for (int i = 0; i < jogadoresIniciais.size(); i++) {
                    FragmentFilho fragmentFilho = new FragmentFilho();

                    Jogador jogador = new Jogador();
                    jogador.setNome(jogadoresIniciais.get(i));
                    jogador.setIdRodada(rodada.getIdRodada());
                    // Adiciona o jogador numa lista local de jogadores
                    jogadoresRodada.add(jogador);
                    // Insere o jogador no banco
                    jogadorController.inserirJogador(jogadoresRodada.get(i));

                    // Cria uma nova tab para aquele jogador
                    adapter.addFrag(fragmentFilho, jogadoresIniciais.get(i));
                    // Notifica o adapter que os dados foram alterados
                    adapter.notifyDataSetChanged();
                    // Configura o tablayout novamente com as tabs novas
                    tabLayout.setupWithViewPager(viewPager);

                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.partida_aberta_menu, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

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
                    Toast alertaMenssagem = Toast.makeText(getApplicationContext(), "Partida salva com sucesso", Toast.LENGTH_LONG);
                    alertaMenssagem.show();
                    builder.dismiss();
                }
            }, ProgressSalvar);
        } else if (id == R.id.action_add_jogador) {

            jogadorController = new JogadorController(this);
            LayoutInflater inflater = getLayoutInflater();

            //Recebe a activity para persolnalizar o dialog
            View dialogLayout = inflater.inflate(R.layout.dialog_novo_jogador, null);
            final EditText etNomeJogador = (EditText) dialogLayout.findViewById(R.id.edit_nome_novo_jogador);

            AlertDialog.Builder builder = new AlertDialog.Builder(PartidaAberta.this);
            builder.setTitle("Novo jogador");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FragmentFilho fragmentFilho = new FragmentFilho();

                    Jogador jogador = new Jogador();
                    jogador.setNome(etNomeJogador.getText().toString());
                    jogador.setIdRodada(rodada.getIdRodada());
                    // Adiciona o jogador numa lista local de jogadores
                    jogadoresRodada.add(jogador);
                    // Adiciona no banco o jogador
                    jogadorController.inserirJogador(jogador);


                    adapter.addFrag(fragmentFilho, etNomeJogador.getText().toString());
                    adapter.notifyDataSetChanged();
                    Snackbar.make(viewPager, "Jogador "+etNomeJogador.getText().toString()+" foi adicionado!", Snackbar.LENGTH_LONG).show();

                    if (adapter.getCount() > 0) {
                        tabLayout.setupWithViewPager(viewPager);
                        viewPager.setCurrentItem(adapter.getCount() - 1);

                    }
                }
            });
            builder.setNegativeButton("Cancelar", null);
            builder.setView(dialogLayout);
            builder.setCancelable(false);
            builder.show();


        } else if (id == R.id.action_placar) {
            Intent intent = new Intent(this, ListaDePlacar.class);
            startActivity(intent);
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
                    Toast alertaMenssagem = Toast.makeText(getApplicationContext(), "Partida salva com sucesso", Toast.LENGTH_LONG);
                    alertaMenssagem.show();
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
        } else if (id == R.id.action_excluir_este_jogador){

                Snackbar.make(viewPager, "Excluir jogador selecionado", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED)
                        .setAction("Excluir "+jogadoresRodada.get(viewPager.getCurrentItem()).getNome(), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (adapter.getCount() > 0) {
                                    jogadorController = new JogadorController(PartidaAberta.this);
                                    String nomeJogadorTabAtual = jogadoresRodada.get(viewPager.getCurrentItem()).getNome();
                                    Jogador jogadorBuscado = buscarJogador(nomeJogadorTabAtual);
                                    jogadorController.deletarJogador(jogadorBuscado);
                                    jogadoresRodada.remove(viewPager.getCurrentItem());
                                    adapter.removeFrag(viewPager.getCurrentItem());
                                    adapter.notifyDataSetChanged();
                                    // vincula denovo o viewpager com o tablayout
                                    tabLayout.setupWithViewPager(viewPager);

                                } else {
                                    Snackbar.make(view, "Não tem mais jogadores para excluir", Snackbar.LENGTH_LONG).show();
                                }

                            }
                        })
                        .show();
            }
        return super.onOptionsItemSelected(item);
    }

        /*

        // teste para ver se cria as rodadas

        rodada.setIdPartida(partida.getIdPartida());
        RodadaDAO rodadaDAO = new RodadaDAO(this);
        rodadaDAO.novaRodada(rodada);

        Rodada rodadaAtual = buscarRodada(partida.getIdPartida());

        jogador.setNome("Jorgewal");
        jogador.setIdRodada(rodadaAtual.getIdRodada());

        JogadorDAO jogadorDAO = new JogadorDAO(this);
        jogadorDAO.novoJogador(jogador);

        */


}
