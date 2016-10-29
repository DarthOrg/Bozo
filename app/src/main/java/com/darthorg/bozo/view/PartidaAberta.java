package com.darthorg.bozo.view;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialog;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.darthorg.bozo.R;
import com.darthorg.bozo.adapter.TabsDinamicosAdapter;
import com.darthorg.bozo.controller.PartidaController;
import com.darthorg.bozo.controller.RodadaController;
import com.darthorg.bozo.fragment.FragmentFilho;
import com.darthorg.bozo.model.Jogador;
import com.darthorg.bozo.model.Partida;
import com.darthorg.bozo.model.Rodada;

import java.util.ArrayList;
import java.util.List;


/**
 * Partida que Gerencia o Jogo
 */
public class PartidaAberta extends AppCompatActivity {

    //Objetos que vao ser Manipulados durante a rodada
    private Partida partida;
    private Rodada rodada;

    //Listas usadas no decorrer do jogo
    private ArrayList<Rodada> listRodadas = new ArrayList<>();
    private static List<Jogador> jogadoresRodada = new ArrayList<>();
    public static List<FragmentFilho> listaFragments;
    private static List<FragmentFilho> listEmpatados;

    //Controllers
    private PartidaController partidaController;
    private RodadaController rodadaController;

    // Widgets
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private TabsDinamicosAdapter adapter;

    // BottonSheetDialog
    private BottomSheetDialog bottomSheetDialog;
    private View bottomSheetDialogView;

    // FloatButtons para o Menu
    private ImageButton BSplacar, BSaddJogador, BSremoverJogador;
    ImageButton fabMais;


    //Responsaveis por trazer os dados da Activity anterior
    private Intent intent;
    private Bundle bundleParams;

    private TextView tituloGrupo;
    private final int PROGRESS_SAVE_TIME = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida_aberta);
        // Evita que a tela bloqueie sozinha
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //Bottom Sheet Dialog btn MAIS
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialogView = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        bottomSheetDialog.setContentView(bottomSheetDialogView);


        //Busca os Ids nos Xml
        getIDs();

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        tituloGrupo = (TextView) findViewById(R.id.TituloGrupo);


        //Recupera os valores das intents
        intent = getIntent();
        bundleParams = intent.getExtras();

        //Método que configura a partida
        configurarPartida();


        fabMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.show();
                return;
            }
        });


        //Adicionar jogador
        BSaddJogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LayoutInflater inflater = getLayoutInflater();

                View dialoglayout = inflater.inflate(R.layout.dialog_novo_jogador, null);

                final EditText etNomeJogador = (EditText) dialoglayout.findViewById(R.id.edit_nome_novo_jogador);

                AlertDialog.Builder builder = new AlertDialog.Builder(PartidaAberta.this);
                builder.setTitle(getString(R.string.adicionar_jogador));
                builder.setIcon(R.drawable.ic_add_jogador);

                builder.setPositiveButton(getString(R.string.adicionar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (jogadoresRodada.size() < 10) {

                            FragmentFilho fragmentFilho = new FragmentFilho(viewPager);
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

                            Toast.makeText(getApplicationContext(), getString(R.string.jogador) + etNomeJogador.getText().toString() + getString(R.string.toast_foi_adicionado), Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), getString(R.string.max_jogadores_permitidos), Toast.LENGTH_LONG).show();
                        }

                    }
                });

                builder.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setView(dialoglayout);
                builder.show();
                bottomSheetDialog.dismiss();
            }
        });

        //Remover jogador
        BSremoverJogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(PartidaAberta.this);
                builder.setTitle(getString(R.string.remover_jogador));
                builder.setIcon(R.drawable.ic_deletar_jogador);
                builder.setMessage(getString(R.string.pergunta_remover_jogador) + jogadoresRodada.get(viewPager.getCurrentItem()).getNome() + " ?")
                        .setPositiveButton(getString(R.string.remover), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                if (adapter.getCount() > 2) {

                                    //remove o fragment da lista
                                    listaFragments.remove(viewPager.getCurrentItem());

                                    // Remove o jogador da rodada
                                    jogadoresRodada.remove(viewPager.getCurrentItem());

                                    // Remove o Fragment
                                    adapter.removeFrag(viewPager.getCurrentItem());

                                    // Notifica o adapter que um fragment foi removido
                                    adapter.notifyDataSetChanged();
                                    // vincula denovo o viewpager com o tablayout
                                    tabLayout.setupWithViewPager(viewPager);

                                    dialog.dismiss();

                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), R.string.limite_exclusao_jogadores, Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.show();
                bottomSheetDialog.dismiss();
            }
        });

        // Placar
        BSplacar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PartidaAberta.this, Placar.class);
                intent.putParcelableArrayListExtra("rodadasfinalizadas", listRodadas);
                startActivity(intent);
                bottomSheetDialog.dismiss();
            }
        });

        //Compara os pontos e mostra quem esta ganhando
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

    /**
     * Captura os Ids via classe R
     */
    public void getIDs() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewPagerMarcadorJogador);
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutJogadores);

        fabMais = (ImageButton) findViewById(R.id.mais);

        BSaddJogador = (ImageButton) bottomSheetDialog.findViewById(R.id.bottomSheet_add_jogador);
        BSremoverJogador = (ImageButton) bottomSheetDialog.findViewById(R.id.bottomSheet_remover_jogador);
        BSplacar = (ImageButton) bottomSheetDialog.findViewById(R.id.bottomSheet_placar);

    }

    /**
     * Verifica se a partida é Nova ou Salva
     *
     * @return true caso a Partida for Nova
     */
    public boolean verificaPartidaNova() {
        return bundleParams.getBoolean("partidaNova");
    }

    /**
     * Configura a partida de acordo com os jogadores
     */
    public void configurarPartida() {

        //Configura o Adapter junto com o ViewPager
        adapter = new TabsDinamicosAdapter(getSupportFragmentManager(), PartidaAberta.this, viewPager, tabLayout);
        viewPager.setAdapter(adapter);

        //Configura o TabLayout com o ViewPager
        tabLayout.setupWithViewPager(viewPager);

        if (intent != null) {
            if (bundleParams != null) {
                //Verifica se é uma partida nova ou uma partida salva
                if (verificaPartidaNova()) {

                    partida = new Partida();
                    rodada = new Rodada();
                    listaFragments = new ArrayList<>();

                    //Configura o nome da Partida
                    partida.setNome(bundleParams.getString("nomepartida"));
                    tituloGrupo.setText(partida.getNome());

                    //Verifica se é a primeira rodada
                    if (listRodadas.size() == 0) {
                        //Adiciona os jogadores
                        ArrayList<String> jogadoresIniciais = bundleParams.getStringArrayList("jogadores");

                        for (int i = 0; i < jogadoresIniciais.size(); i++) {

                            FragmentFilho fragmentFilho = new FragmentFilho(viewPager);
                            Jogador jogador = new Jogador();
                            jogador.setNome(jogadoresIniciais.get(i));
                            fragmentFilho.setNome(jogadoresIniciais.get(i));

                            // Adiciona o jogador numa lista local de jogadores
                            jogadoresRodada.add(jogador);
                            listaFragments.add(fragmentFilho);

                            jogadoresRodada.size();
                            listaFragments.size();

                            // Cria uma nova tab para aquele jogador
                            adapter.addFrag(fragmentFilho, jogadoresIniciais.get(i));
                            // Notifica o adapter que os dados foram alterados
                            adapter.notifyDataSetChanged();
                            // Configura o tablayout novamente com as tabs novas
                            tabLayout.setupWithViewPager(viewPager);
                        }
                    } else {
                        //Não é a primeira rodada , entao pega os jogadores da rodada anterior
                        //todo:Gerar aqui de acordo com quem ganhou
                        for (int i = 0; i < jogadoresRodada.size(); i++) {

                            FragmentFilho fragmentFilho = new FragmentFilho(viewPager);
                            fragmentFilho.setNome(jogadoresRodada.get(i).getNome());
                            listaFragments.add(fragmentFilho);

                            // Cria uma nova tab para aquele jogador
                            adapter.addFrag(fragmentFilho, jogadoresRodada.get(i).getNome());
                            // Notifica o adapter que os dados foram alterados
                            adapter.notifyDataSetChanged();
                            // Configura o tablayout novamente com as tabs novas
                            tabLayout.setupWithViewPager(viewPager);
                        }
                    }// Fim Nova partida (trabalhando com dados locais)

                } else {

                    partidaController = new PartidaController(PartidaAberta.this);
                    rodadaController = new RodadaController(PartidaAberta.this);
                    listaFragments = new ArrayList<>();
                    rodada = new Rodada();

                    //Esta partida sabe o seu ID , Nome , Rodadas e Jogadores
                    partida = partidaController.buscarPartida(bundleParams.getLong("partidaSalva"));
                    tituloGrupo.setText(partida.getNome());
                    Log.i("partidasalva", "id da partida :" + bundleParams.getLong("partidaSalva"));

                    if (listRodadas.size() == 0) {

                        // Recupera os dados das rodadas salvas
                        for (int i = 0; i < partida.getRodadas().size(); i++) {
                            // Adiciona as Rodadas do Banco na lista de RodadasLocal ja com os jogadores
                            partida.getRodadas().get(i).setJogadores(rodadaController.buscarJogadoresRodada(partida.getRodadas().get(i).getIdRodada()));
                            listRodadas.add(partida.getRodadas().get(i));
                        }

                        //Configura o jogo a partir da ultima rodada
                        Rodada ultimaRodada = partida.getRodadas().get(partida.getRodadas().size() - 1);
                        for (int i = 0; i < ultimaRodada.getJogadores().size(); i++) {
                            // Adiciona um fragment para cada jogador
                            FragmentFilho fragmentFilho = new FragmentFilho(viewPager);
                            adapter.addFrag(fragmentFilho, ultimaRodada.getJogadores().get(i).getNome());

                            // Adiciona os jogadores do banco tambem a uma lista local
                            jogadoresRodada.add(ultimaRodada.getJogadores().get(i));
                            fragmentFilho.setNome(ultimaRodada.getJogadores().get(i).getNome());

                            listaFragments.add(fragmentFilho);

                            // Notifica o adapter que fragments foram adicionados
                            adapter.notifyDataSetChanged();

                            //Configura o Tab e o ViewPager com os fragments novos
                            tabLayout.setupWithViewPager(viewPager);
                        }
                    } else {

                        //todo:Gerar aqui de acordo com quem ganhou
                        for (int i = 0; i < jogadoresRodada.size(); i++) {

                            FragmentFilho fragmentFilho = new FragmentFilho(viewPager);
                            fragmentFilho.setNome(jogadoresRodada.get(i).getNome());
                            listaFragments.add(fragmentFilho);

                            // Cria uma nova tab para aquele jogador
                            adapter.addFrag(fragmentFilho, jogadoresRodada.get(i).getNome());
                            // Notifica o adapter que os dados foram alterados
                            adapter.notifyDataSetChanged();
                            // Configura o tablayout novamente com as tabs novas
                            tabLayout.setupWithViewPager(viewPager);
                        }
                    }


                }
            }
        }

    }

    /**
     * Compara a pontuação dos fragments
     *
     * @return retorna um ganhador
     */
    public static Jogador compararPontos() {

        FragmentFilho ganhando = listaFragments.get(0);
        Jogador ganhador = null;
        listEmpatados = new ArrayList<>();

        for (int i = 0; i < listaFragments.size(); i++) {
            // Ninguem ganha ou empata ate verificar
            listaFragments.get(i).setGanhando(false);
            listaFragments.get(i).setEmpatado(false);
            // Não compara ninguem igual e com pontuacao igual a zero
            if (!ganhando.equals(listaFragments.get(i)) ) {
                if (ganhando.getContador() < listaFragments.get(i).getContador()) {
                    //Outra pessoa esta ganhando
                    ganhando.setGanhando(false);
                    ganhando = listaFragments.get(i);
                    ganhando.setGanhando(true);
                    ganhando.setEmpatado(false);
                } else if (ganhando.getContador() == listaFragments.get(i).getContador()) {
                    //Empate
                    ganhando.setGanhando(false);
                    ganhando.setEmpatado(true);
                    listaFragments.get(i).setGanhando(false);
                    listaFragments.get(i).setEmpatado(true);

                    // Adiciona a uma lista os listEmpatados que estao com maior pontuacao
                    if (!listEmpatados.contains(ganhando)) {
                        listEmpatados.add(ganhando);
                    }
                    listEmpatados.add(listaFragments.get(i));

                } else if (!ganhando.isEmpatado()) {
                    // permanece o vencedor
                    ganhando.setGanhando(true);
                }
            }
        }


        for (int i = 0; i < listaFragments.size(); i++) {
            if (listaFragments.get(i).isGanhando()) {
                ganhador = jogadoresRodada.get(i);
            }
        }

        return ganhador;
    }

    /**
     * Verifica se todas as peças foram preenchidas
     *
     * @return
     */
    public boolean verificaSeRodadaAcabou() {

        int fragmentsCompletos = 0;

        for (int i = 0; i < listaFragments.size(); i++) {
            if (listaFragments.get(i).hasGeneralDeBoca()) {
                return true;
            } else if (listaFragments.get(i).isAcabouRodada()) {
                fragmentsCompletos++;
            }
        }

        return fragmentsCompletos == listaFragments.size();

    }

    public void configurarNovaRodada(String nomeGanhador) {

        List<Jogador> auxJogadoresRodadas = new ArrayList<>();

        for (int i = 0; i < jogadoresRodada.size(); i++) {
            Jogador j = new Jogador();
            j.setNome(jogadoresRodada.get(i).getNome());
            j.setPontuacao(listaFragments.get(i).getContador());
            auxJogadoresRodadas.add(j);
        }

        rodada.setNomeVencedor(nomeGanhador);
        rodada.setJogadores(auxJogadoresRodadas);
        listRodadas.add(rodada);

        //Remove os fragments
        for (int i = 0; i < listaFragments.size(); i++) {
            adapter.removeFrag(viewPager.getCurrentItem());
        }
        configurarPartida();
    }

    /**
     * Salva ou não as partidas
     */
    private void saveAndQuit() {

        if (listRodadas.size() != 0) {


            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(getString(R.string.titulo_salvar_e_sair));
            builder.setIcon(R.drawable.ic_salvar);
            builder.setMessage(getString(R.string.menssagem_salvar_grupo))
                    .setPositiveButton(getString(R.string.salvar), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //ProgressDialog Função carregar
                            final ProgressDialog builder = new ProgressDialog(PartidaAberta.this);
                            builder.setMessage(getString(R.string.salvando));
                            builder.show();

                            if (intent != null) {
                                if (bundleParams != null) {
                                    //Verifica se é uma partida nova ou uma partida salva
                                    if (verificaPartidaNova()) {
                                        // OK, é nova
                                        partidaController = new PartidaController(PartidaAberta.this);


                                        new Handler().postDelayed(new Runnable() {
                                            public void run() {

                                                partida.setRodadas(listRodadas);
                                                //Insere a partida no banco
                                                partidaController.inserirPartida(partida);

                                                Toast.makeText(getApplicationContext(), getString(R.string.grupo) + partida.getNome() + getString(R.string.salvo_com_sucesso), Toast.LENGTH_LONG).show();
                                                builder.dismiss();
                                                finish();
                                            }
                                        }, PROGRESS_SAVE_TIME);

                                    } else {
                                        // Hmmm , Partida Salva então ?! entao ferro mané kkk

                                        new Handler().postDelayed(new Runnable() {
                                            public void run() {
                                                rodadaController = new RodadaController(PartidaAberta.this);

                                                if (!listRodadas.equals(partida.getRodadas())) {
                                                    // Adiciona as Novas Rodadas
                                                    for (int i = 0; i < listRodadas.size(); i++) {
                                                        if (i >= partida.getRodadas().size()) {
                                                            listRodadas.get(i).setIdPartida(partida.getIdPartida());
                                                            rodadaController.inserirRodada(listRodadas.get(i));
                                                        }
                                                    }
                                                }
                                                finish();
                                                builder.dismiss();
                                            }
                                        }, PROGRESS_SAVE_TIME);
                                    }
                                }
                            }
                        }
                    })
                    .setNegativeButton(getString(R.string.descartar), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            if (!verificaPartidaNova()) {
                                builder.setNegativeButton(getString(R.string.descartar_alteracoes), null);
                            }
                            finish();
                            dialog.dismiss();
                        }
                    });
            builder.show();

        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.sair));
            builder.setMessage(getString(R.string.sair_sem_jogar))
                    .setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
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

        if (id == R.id.action_finalizar) {
            finalizarRodada();
        }

        return super.

                onOptionsItemSelected(item);
    }

    public void finalizarRodada() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(true);

        if (verificaSeRodadaAcabou()) {

            if (compararPontos() != null) {


                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(compararPontos().getNome() + getString(R.string.ganhou));
                builder.setIcon(R.drawable.ic_jogador);
                builder.setMessage(getString(R.string.pergunta_nova_rodada))
                        .setPositiveButton(getString(R.string.jogar), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                configurarNovaRodada(compararPontos().getNome());
                            }
                        })
                        .setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNeutralButton(getString(R.string.sair), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //todo: uma hr isso vai dar bug nervoso
                                configurarNovaRodada(compararPontos().getNome());
                                saveAndQuit();
                                dialog.dismiss();
                            }
                        });

                builder.show();

            } else {

                LayoutInflater inflater = getLayoutInflater();

                View dialoglayout = inflater.inflate(R.layout.dialog_empate, null);

                final RadioGroup rgEmpate = (RadioGroup) dialoglayout.findViewById(R.id.rgDialogEmpate);

                for (int i = 0; i < listEmpatados.size(); i++) {
                    RadioButton rdbtn = new RadioButton(this);
                    rdbtn.setId(i);
                    rdbtn.setText(listEmpatados.get(i).getNome());
                    rgEmpate.addView(rdbtn);
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(PartidaAberta.this);
                builder.setTitle(getString(R.string.empate));
                builder.setIcon(R.drawable.ic_groupo);
                builder.setMessage(getString(R.string.decisao_empate));

                builder.setPositiveButton(getString(R.string.finalizar_rodada), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        configurarNovaRodada(listEmpatados.get(rgEmpate.getCheckedRadioButtonId()).getNome());
                    }
                });

                builder.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setView(dialoglayout);
                builder.show();

            }

        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.aviso));
            builder.setIcon(R.drawable.ic_aviso);
            builder.setMessage(getString(R.string.rodada_incompleta))
                    .setPositiveButton(getString(R.string.sair), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            saveAndQuit();
                        }
                    })
                    .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            builder.show();

        }
    }

    @Override
    public void onBackPressed() {
        saveAndQuit();
    }
}
