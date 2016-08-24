package com.darthorg.bozo.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.darthorg.bozo.R;
import com.darthorg.bozo.adapter.TabsDinamicosAdapter;
import com.darthorg.bozo.controller.JogadorController;
import com.darthorg.bozo.controller.PartidaController;
import com.darthorg.bozo.controller.RodadaController;
import com.darthorg.bozo.fragment.FragmentFilho;
import com.darthorg.bozo.model.Jogador;
import com.darthorg.bozo.model.Partida;
import com.darthorg.bozo.model.Rodada;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Partida que Gerencia o Jogo
 */
public class PartidaAberta extends AppCompatActivity {

    //Objetos que vao ser Manipulados durante a rodada
    private Partida partida;
    private Rodada rodada;
    private List<Jogador> listJogadoresBanco;
    private List<Jogador> jogadoresRodada;
    private List<FragmentFilho> listaFragments = new ArrayList<>();


    //Controllers
    private PartidaController partidaController;
    private RodadaController rodadaController;
    private JogadorController jogadorController;

    // Widgets
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private TabsDinamicosAdapter adapter;
    // FloatButtons para o Menu
    private FloatingActionMenu fabMenu;
    private FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

    private final int ProgressSalvar = 1000;

    //Responsaveis por trazer os dados da Activity anterior
    private Intent intent;
    private Bundle bundleParams;

    private TextView tituloGrupo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida_aberta);
        // Evita que a tela bloqueie sozinha
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //Busca os Ids nos Xml
        getIDs();

        toolbar.setTitle(R.string.TextoVazio);
        setSupportActionBar(toolbar);

        //Configura o Adapter junto com o ViewPager
        adapter = new TabsDinamicosAdapter(getSupportFragmentManager(), PartidaAberta.this, viewPager, tabLayout);
        viewPager.setAdapter(adapter);

        //Configura o TabLayout com o ViewPager
        tabLayout.setupWithViewPager(viewPager);
        tituloGrupo = (TextView) findViewById(R.id.TituloGrupo);

        //Recupera os valores das intents
        intent = getIntent();
        bundleParams = intent.getExtras();

        //Método que configura a partida
        configurarPartida();

        //Float action bar
        fabMenu.setClosedOnTouchOutside(true);

        //Adicionar jogador
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //Dialog para Adicionar Jogador
                final Dialog dialogAdicionarJogador = new Dialog(PartidaAberta.this);
                // Configura a view para o Dialog
                dialogAdicionarJogador.setContentView(R.layout.dialog_novo_jogador);

                //Recupera os componentes do layout do custondialog
                final EditText etNomeJogador = (EditText) dialogAdicionarJogador.findViewById(R.id.edit_nome_novo_jogador);
                Button btnAdicionarJogador = (Button) dialogAdicionarJogador.findViewById(R.id.btnExcluir);
                Button btnCancelar = (Button) dialogAdicionarJogador.findViewById(R.id.btnCancelar);

                //Titulo
                dialogAdicionarJogador.setTitle(R.string.AdicionarJogador);

                //Add Jogador
                btnAdicionarJogador.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        FragmentFilho fragmentFilho = new FragmentFilho();
                        Jogador jogador = new Jogador();
                        jogador.setNome(etNomeJogador.getText().toString());
                        fragmentFilho.setNome(etNomeJogador.getText().toString());

                        // Adiciona o jogador numa lista local de jogadores
                        jogadoresRodada.add(jogador);

                        listaFragments.add(fragmentFilho);

                        //Adiciona o Fragment nas tabs
                        adapter.addFrag(fragmentFilho, etNomeJogador.getText().toString());
                        // Notifica o Adapter que um novo fragment foi adicionado
                        adapter.notifyDataSetChanged();
                        if (adapter.getCount() > 0) {
                            tabLayout.setupWithViewPager(viewPager);
                            viewPager.setCurrentItem(adapter.getCount() - 1);
                        }

                        Snackbar.make(viewPager, getString(R.string.Jogador) + etNomeJogador.getText().toString() + getString(R.string.FoiAdicionado), Snackbar.LENGTH_SHORT).show();
                        dialogAdicionarJogador.dismiss();
                    }
                });
                //Botão Cancelar
                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogAdicionarJogador.dismiss();
                        return;
                    }
                });
                dialogAdicionarJogador.show();
                fabMenu.close(true);
            }
        });
        //Remover jogador
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Dialog para Remover Jogador
                final Dialog dialogRemoveJogador = new Dialog(PartidaAberta.this);

                // Configura a view para o Dialog
                dialogRemoveJogador.setContentView(R.layout.dialog_excluir);

                //Recupera os componentes do layout do custondialog
                Button btnExcluir = (Button) dialogRemoveJogador.findViewById(R.id.btnExcluir);
                Button btnCancelar = (Button) dialogRemoveJogador.findViewById(R.id.btnCancelar);
                TextView txtNomeJogadorExcluido = (TextView) dialogRemoveJogador.findViewById(R.id.txtNomeJogadorExcluido);
                txtNomeJogadorExcluido.setText(jogadoresRodada.get(viewPager.getCurrentItem()).getNome());

                //Botão Excluir
                btnExcluir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (adapter.getCount() > 2) {

                            // Remove o jogador da rodada
                            jogadoresRodada.remove(viewPager.getCurrentItem());
                            // Remove o Fragment
                            adapter.removeFrag(viewPager.getCurrentItem());
                            // Notifica o adapter que um fragment foi removido
                            adapter.notifyDataSetChanged();
                            // vincula denovo o viewpager com o tablayout
                            tabLayout.setupWithViewPager(viewPager);

                            dialogRemoveJogador.dismiss();

                        } else {
                            //todo:Criar um dialog aqui perguntando se deseja abandonar partida
                            Snackbar.make(view, R.string.TextoSemJogadores, Snackbar.LENGTH_LONG).show();
                        }
                    }
                });
                //Botão Cancelar
                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogRemoveJogador.dismiss();
                        return;
                    }
                });
                dialogRemoveJogador.show();
                fabMenu.close(true);
            }
        });
        // Placar
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(PartidaAberta.this, ListaDePlacar.class);
                startActivity(intent);
                fabMenu.close(true);
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                compararPontos();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    public Jogador compararPontos() {

        FragmentFilho ganhando = listaFragments.get(0);
        Jogador ganhador = jogadoresRodada.get(0);


        for (int i = 0; i < listaFragments.size(); i++) {
            listaFragments.get(i).setGanhando(false);
            if (ganhando.getContador() != 0) {
                if (ganhando.getContador() > listaFragments.get(i).getContador()) {
                    ganhando.setGanhando(true);


                } else {
                    ganhando.setGanhando(false);
                    ganhando = listaFragments.get(i);
                    ganhando.setGanhando(true);

                    ganhador = jogadoresRodada.get(i);
                }
            }
        }

        return ganhador;
    }

    public boolean verificaSeRodadaAcabou() {

        FragmentFilho ultimoJogador = listaFragments.get(listaFragments.size() - 1);

        if (ultimoJogador.isAcabouRodada()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Captura os Ids via classe R
     */
    public void getIDs() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewPagerMarcadorJogador);
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutJogadores);

        fabMenu = (FloatingActionMenu) findViewById(R.id.floating_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.floating_add_jogador);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.floating_excluir_jogador);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.floating_placar);
    }

    /**
     * Configura a partida de acordo com os jogadores
     */
    public void configurarPartida() {

        if (intent != null) {
            if (bundleParams != null) {
                //Verifica se é uma partida nova ou uma partida salva
                if (verificaPartidaNova()) {

                    partida = new Partida();
                    rodada = new Rodada();
                    jogadoresRodada = new ArrayList<>();

                    //Configura o nome da Partida
                    partida.setNome(bundleParams.getString("nomepartida"));
                    tituloGrupo.setText(partida.getNome());

                    //Adiciona os jogadores
                    ArrayList<String> jogadoresIniciais = bundleParams.getStringArrayList("jogadores");

                    for (int i = 0; i < jogadoresIniciais.size(); i++) {

                        FragmentFilho fragmentFilho = new FragmentFilho();
                        Jogador jogador = new Jogador();
                        jogador.setNome(jogadoresIniciais.get(i));
                        fragmentFilho.setNome(jogadoresIniciais.get(i));

                        // Adiciona o jogador numa lista local de jogadores
                        jogadoresRodada.add(jogador);

                        listaFragments.add(fragmentFilho);

                        // Cria uma nova tab para aquele jogador
                        adapter.addFrag(fragmentFilho, jogadoresIniciais.get(i));

                        // Notifica o adapter que os dados foram alterados
                        adapter.notifyDataSetChanged();

                        // Configura o tablayout novamente com as tabs novas
                        tabLayout.setupWithViewPager(viewPager);
                    }

                } else {

                    partidaController = new PartidaController(PartidaAberta.this);
                    jogadoresRodada = new ArrayList<>();

                    //Esta partida sabe o seu ID , Nome , Rodadas e Jogadores
                    partida = partidaController.buscarPartida(bundleParams.getLong("partidaSalva"));
                    tituloGrupo.setText(partida.getNome());

                    Log.i("partidasalva", "id da partida :" + bundleParams.getLong("partidaSalva"));

                    //todo:trocar este método por um que traz jogadores da ultima rodada
                    // Esta trazendo jogadores que estao naquela partida
                    listJogadoresBanco = partidaController.buscarJogadoresPartida(partida.getIdPartida());

                    for (int i = 0; i < listJogadoresBanco.size(); i++) {

                        // Adiciona um fragment para cada jogador
                        FragmentFilho fragmentFilho = new FragmentFilho();
                        adapter.addFrag(fragmentFilho, listJogadoresBanco.get(i).getNome());

                        // Adiciona os jogadores do banco tambem a uma lista local
                        jogadoresRodada.add(listJogadoresBanco.get(i));
                        fragmentFilho.setNome(listJogadoresBanco.get(i).getNome());

                        listaFragments.add(fragmentFilho);

                        // Notifica o adapter que fragments foram adicionados
                        adapter.notifyDataSetChanged();

                        //Configura o Tab e o ViewPager com os fragments novos
                        tabLayout.setupWithViewPager(viewPager);

                    }
                }
            }
        }

    }

    /**
     * Verifica se a partida é Nova ou Salva
     *
     * @return true caso a Partida for Nova
     */
    public boolean verificaPartidaNova() {
        if (bundleParams.getBoolean("partidaNova")) {
            return true;
        } else {
            return false;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.partida_aberta_menu, menu);
        return true;
    }

    //Botão voltar
    @Override
    public void onBackPressed() {

        if (fabMenu.isOpened()) {
            fabMenu.close(true);
        } else {

            LayoutInflater inflater = getLayoutInflater();
            View dialogLayout = inflater.inflate(R.layout.dialog_sair_grupo, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(PartidaAberta.this);

            //Botão Salvar grupo
            Button btnSalvarGrupo = (Button) dialogLayout.findViewById(R.id.btnExcluir);
            btnSalvarGrupo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    //ProgressDialog Função carregar
                    final ProgressDialog builder = new ProgressDialog(PartidaAberta.this);
                    builder.setMessage("Salvando só um momento...");
                    builder.show();

                    if (intent != null) {
                        if (bundleParams != null) {
                            //Verifica se é uma partida nova ou uma partida salva
                            if (verificaPartidaNova()) {
                                // OK, é nova

                                new Handler().postDelayed(new Runnable() {
                                    public void run() {

                                        partidaController = new PartidaController(PartidaAberta.this);
                                        rodadaController = new RodadaController(PartidaAberta.this);
                                        jogadorController = new JogadorController(PartidaAberta.this);

                                        //Insere a partida no banco
                                        long idDaPartida = partidaController.inserirPartida(partida);

                                        rodada.setIdPartida(idDaPartida);
                                        long idDaRodada = rodadaController.inserirRodada(rodada);

                                        for (int i = 0; i < jogadoresRodada.size(); i++) {
                                            jogadoresRodada.get(i).setIdRodada(idDaRodada);
                                            jogadorController.inserirJogador(jogadoresRodada.get(i));
                                        }

                                        Toast.makeText(getApplicationContext(), "Grupo " + partida.getNome() + " salvo com sucesso", Toast.LENGTH_LONG).show();
                                        builder.dismiss();
                                        finish();
                                    }
                                }, ProgressSalvar);

                            } else {
                                // Hmmm , Partida Salva então ?!

                                new Handler().postDelayed(new Runnable() {
                                    public void run() {

                                        rodadaController = new RodadaController(PartidaAberta.this);
                                        partidaController = new PartidaController(PartidaAberta.this);
                                        jogadorController = new JogadorController(PartidaAberta.this);

                                        rodada = rodadaController.buscarRodada(partida.getIdPartida());

                                        if (!jogadoresRodada.equals(listJogadoresBanco)) {
                                            //Deleta os jogadores do banco
                                            for (int i = 0; i < listJogadoresBanco.size(); i++) {
                                                jogadorController.deletarJogador(listJogadoresBanco.get(i));
                                            }

                                            // Adiciona os Jogadores novamente
                                            for (int i = 0; i < jogadoresRodada.size(); i++) {
                                                jogadoresRodada.get(i).setIdRodada(rodada.getIdRodada());
                                                jogadorController.inserirJogador(jogadoresRodada.get(i));
                                            }
                                        }
                                        finish();
                                        builder.dismiss();
                                    }
                                }, ProgressSalvar);
                            }
                        }
                    }
                }
            });
            //Botão Descartar grupo
            Button btnDescartarGrupo = (Button) dialogLayout.findViewById(R.id.btnDescartar);
            if (!verificaPartidaNova()) {
                btnDescartarGrupo.setText(R.string.DescartarAlteracoes);
            }
            btnDescartarGrupo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            builder.setView(dialogLayout);
            builder.show();

        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_bloquear_som) {
            return true;
        } else if (id == R.id.action_configuracoes) {
            return true;
        } else if (id == R.id.action_sair) {

            LayoutInflater inflater = getLayoutInflater();
            View dialogLayout = inflater.inflate(R.layout.dialog_sair_grupo, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(PartidaAberta.this);

            //Botão Salvar grupo
            Button btnSalvarGrupo = (Button) dialogLayout.findViewById(R.id.btnExcluir);
            btnSalvarGrupo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    //ProgressDialog Função carregar
                    final ProgressDialog builder = new ProgressDialog(PartidaAberta.this);
                    builder.setMessage("Salvando só um momento...");
                    builder.show();

                    if (intent != null) {
                        if (bundleParams != null) {
                            //Verifica se é uma partida nova ou uma partida salva
                            if (verificaPartidaNova()) {
                                // OK, é nova

                                new Handler().postDelayed(new Runnable() {
                                    public void run() {

                                        partidaController = new PartidaController(PartidaAberta.this);
                                        rodadaController = new RodadaController(PartidaAberta.this);
                                        jogadorController = new JogadorController(PartidaAberta.this);

                                        //Insere a partida no banco
                                        long idDaPartida = partidaController.inserirPartida(partida);

                                        rodada.setIdPartida(idDaPartida);
                                        long idDaRodada = rodadaController.inserirRodada(rodada);

                                        for (int i = 0; i < jogadoresRodada.size(); i++) {
                                            jogadoresRodada.get(i).setIdRodada(idDaRodada);
                                            jogadorController.inserirJogador(jogadoresRodada.get(i));
                                        }

                                        Toast.makeText(getApplicationContext(), "Grupo " + partida.getNome() + " salvo com sucesso", Toast.LENGTH_LONG).show();
                                        builder.dismiss();
                                        finish();
                                    }
                                }, ProgressSalvar);

                            } else {
                                // Hmmm , Partida Salva então ?!

                                new Handler().postDelayed(new Runnable() {
                                    public void run() {

                                        rodadaController = new RodadaController(PartidaAberta.this);
                                        partidaController = new PartidaController(PartidaAberta.this);
                                        jogadorController = new JogadorController(PartidaAberta.this);

                                        rodada = rodadaController.buscarRodada(partida.getIdPartida());

                                        if (!jogadoresRodada.equals(listJogadoresBanco)) {
                                            //Deleta os jogadores do banco
                                            for (int i = 0; i < listJogadoresBanco.size(); i++) {
                                                jogadorController.deletarJogador(listJogadoresBanco.get(i));
                                            }

                                            // Adiciona os Jogadores novamente
                                            for (int i = 0; i < jogadoresRodada.size(); i++) {
                                                jogadoresRodada.get(i).setIdRodada(rodada.getIdRodada());
                                                jogadorController.inserirJogador(jogadoresRodada.get(i));
                                            }
                                        }
                                        finish();
                                        builder.dismiss();
                                    }
                                }, ProgressSalvar);
                            }
                        }
                    }
                }
            });
            //Botão Descartar grupo
            Button btnDescartarGrupo = (Button) dialogLayout.findViewById(R.id.btnDescartar);
            if (!verificaPartidaNova()) {
                btnDescartarGrupo.setText(R.string.DescartarAlteracoes);
            }
            btnDescartarGrupo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            builder.setView(dialogLayout);
            builder.show();
            return true;

        } else if (id == R.id.action_finalizar) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setCancelable(true);

            if (verificaSeRodadaAcabou()) {

                alertDialogBuilder.setTitle("Nova Rodada");
                alertDialogBuilder.setMessage(compararPontos().getNome() + " Ganhou !!\nDeseja jogar uma nova rodada?");
                alertDialogBuilder.setPositiveButton(" Sim ", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertDialogBuilder.setNegativeButton(" Não ", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            } else {

                alertDialogBuilder.setTitle(" ;(");
                alertDialogBuilder.setMessage("Esta rodada ainda n terminou , Deseja sair assim mesmo ?");
                alertDialogBuilder.setPositiveButton(" Abandonar  ", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alertDialogBuilder.setNegativeButton(" Cancelar ", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
            }
            alertDialogBuilder.show();
        }

        fabMenu.close(true);
        return super.onOptionsItemSelected(item);
    }
}
