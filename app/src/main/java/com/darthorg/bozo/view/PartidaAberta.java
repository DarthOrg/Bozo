package com.darthorg.bozo.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

import static android.support.design.widget.BottomSheetBehavior.from;


/**
 * Partida que Gerencia o Jogo
 */
public class PartidaAberta extends AppCompatActivity {

    //Objetos que vao ser Manipulados durante a rodada
    private Partida partida;
    private Rodada rodada;

    //Listas usadas no decorrer do jogo
    private ArrayList<Rodada> listRodadas = new ArrayList<>();
    private List<Jogador> jogadoresRodada = new ArrayList<>();
    private List<FragmentFilho> listaFragments;
    private List<FragmentFilho> listEmpatados;

    //Controllers
    private PartidaController partidaController;
    private RodadaController rodadaController;

    // Widgets
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;
    private TabsDinamicosAdapter adapter;
    // FloatButtons para o Menu
    private ImageButton BSplacar, BSaddJogador, BSremoverJogador;
    LinearLayout sairBS;
    ImageButton fabMais;

    private final int ProgressSalvar = 1000;

    //Responsaveis por trazer os dados da Activity anterior
    private Intent intent;
    private Bundle bundleParams;

    private TextView tituloGrupo;
    private BottomSheetBehavior mBottomSheetBehavior;

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

        tituloGrupo = (TextView) findViewById(R.id.TituloGrupo);


        //Recupera os valores das intents
        intent = getIntent();
        bundleParams = intent.getExtras();

        //Método que configura a partida
        configurarPartida();

        //FabBottomSheet
        View bottomSheetView = findViewById(R.id.bottomSheet);
        mBottomSheetBehavior = from(bottomSheetView);
        fabMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });


        //Adicionar jogador
        BSaddJogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Dialog para Adicionar Jogador
                final Dialog dialogAdicionarJogador = new Dialog(PartidaAberta.this, android.R.style.Theme_DeviceDefault_Dialog);
                // Configura a view para o Dialog
                dialogAdicionarJogador.setContentView(R.layout.dialog_novo_jogador);
                dialogAdicionarJogador.setTitle("Novo Jogador");

                //Recupera os componentes do layout do custondialog
                final EditText etNomeJogador = (EditText) dialogAdicionarJogador.findViewById(R.id.edit_nome_novo_jogador);
                Button btnAdicionarJogador = (Button) dialogAdicionarJogador.findViewById(R.id.btnAdicionarJogador);
                Button btnCancelar = (Button) dialogAdicionarJogador.findViewById(R.id.btnCancelar);

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

                        Toast.makeText(getApplicationContext(), getString(R.string.Jogador) + etNomeJogador.getText().toString() + getString(R.string.FoiAdicionado), Toast.LENGTH_LONG).show();
                        dialogAdicionarJogador.dismiss();
                    }
                });
                //Botão Cancelar
                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogAdicionarJogador.dismiss();
                    }
                });
                dialogAdicionarJogador.show();
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        //Remover jogador
        BSremoverJogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Dialog para Remover Jogador
                final Dialog dialogRemoveJogador = new Dialog(PartidaAberta.this, android.R.style.Theme_DeviceDefault_Dialog);

                // Configura a view para o Dialog
                dialogRemoveJogador.setContentView(R.layout.dialog_excluir);
                dialogRemoveJogador.setTitle("Remover Jogador");

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

                            dialogRemoveJogador.dismiss();

                        } else {
                            dialogRemoveJogador.dismiss();
                            Toast.makeText(getApplicationContext(), "Não pode excluir mais jogadores", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                //Botão Cancelar
                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogRemoveJogador.dismiss();
                    }
                });
                dialogRemoveJogador.show();
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        // Placar
        BSplacar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PartidaAberta.this, Placar.class);
                intent.putParcelableArrayListExtra("rodadasfinalizadas", listRodadas);
                startActivity(intent);
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        // Sair do BottomSheet
        sairBS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
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

        BSaddJogador = (ImageButton) findViewById(R.id.bottomSheet_add_jogador);
        BSremoverJogador = (ImageButton) findViewById(R.id.bottomSheet_remover_jogador);
        BSplacar = (ImageButton) findViewById(R.id.bottomSheet_placar);

        sairBS = (LinearLayout) findViewById(R.id.sairBottomSheet);


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

                            FragmentFilho fragmentFilho = new FragmentFilho();
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

                            FragmentFilho fragmentFilho = new FragmentFilho();
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
                            FragmentFilho fragmentFilho = new FragmentFilho();
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

                            FragmentFilho fragmentFilho = new FragmentFilho();
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
    public Jogador compararPontos() {

        FragmentFilho ganhando = listaFragments.get(0);
        Jogador ganhador = null;
        listEmpatados = new ArrayList<>();

        for (int i = 0; i < listaFragments.size(); i++) {
            // Ninguem ganha ou empata ate verificar
            listaFragments.get(i).setGanhando(false);
            listaFragments.get(i).setEmpatado(false);
            // Não compara ninguem igual e com pontuacao igual a zero
            if (!ganhando.equals(listaFragments.get(i)) && listaFragments.get(i).getContador() != 0) {
                if (ganhando.getContador() < listaFragments.get(i).getContador()) {
                    //Outra pessoa esta ganhando
                    ganhando.setGanhando(false);
                    ganhando = listaFragments.get(i);
                    ganhando.setGanhando(true);
                    ganhando.setEmpatado(false);
                    ganhador = jogadoresRodada.get(i);
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

                    ganhador = null;
                } else if (!ganhando.isEmpatado()) {
                    // permanece o vencedor
                    ganhando.setGanhando(true);
                    ganhador = jogadoresRodada.get(i);
                }
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
            if (listaFragments.get(i).isAcabouRodada()) {
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
            // Salva apenas se tiver alteraçao
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
                                partidaController = new PartidaController(PartidaAberta.this);


                                new Handler().postDelayed(new Runnable() {
                                    public void run() {

                                        partida.setRodadas(listRodadas);
                                        //Insere a partida no banco
                                        partidaController.inserirPartida(partida);

                                        Toast.makeText(getApplicationContext(), "Grupo " + partida.getNome() + " salvo com sucesso", Toast.LENGTH_LONG).show();
                                        builder.dismiss();
                                        finish();
                                    }
                                }, ProgressSalvar);

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
        } else {

            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);

            alertDialog.setCancelable(true);
            alertDialog.setTitle("Nenhuma Rodada");
            alertDialog.setMessage("Você não jogou nenhuma rodada .\n Deseja sair assim mesmo ?");
            alertDialog.setPositiveButton(" Sim ", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });

            alertDialog.setNegativeButton(" Cancelar ", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
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
        saveAndQuit();
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_finalizar) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setCancelable(true);

            if (verificaSeRodadaAcabou()) {

                if (compararPontos() != null) {

                    // Dialog Finalizar grupo de jogo
                    final Dialog dialogFinalizar = new Dialog(PartidaAberta.this, android.R.style.Theme_DeviceDefault_Dialog);
                    dialogFinalizar.setTitle("Rodada finalizada");

                    // Configura a view para o Dialog
                    dialogFinalizar.setContentView(R.layout.dialog_ganhou);

                    Button btnSair = (Button) dialogFinalizar.findViewById(R.id.btnSair);
                    Button btnCancelar = (Button) dialogFinalizar.findViewById(R.id.btnCancelar);
                    Button btnJogar = (Button) dialogFinalizar.findViewById(R.id.btnJogar);
                    TextView txtNomeJogadorExcluido = (TextView) dialogFinalizar.findViewById(R.id.txtGanhou);
                    txtNomeJogadorExcluido.setText(compararPontos().getNome() + " Ganhou");

                    // btn Jogar nova rodada
                    btnJogar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            configurarNovaRodada(compararPontos().getNome());
                            dialogFinalizar.dismiss();
                        }
                    });

                    // btn Cancelar
                    btnCancelar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogFinalizar.dismiss();
                        }
                    });

                    // btn Sair
                    btnSair.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //todo: uma hr isso vai dar bug nervoso
                            configurarNovaRodada(compararPontos().getNome());
                            dialogFinalizar.dismiss();
                            saveAndQuit();
                        }
                    });
                    dialogFinalizar.show();
                } else {
                    // EMPATE
                    final Dialog dialogEmpate = new Dialog(this, android.R.style.Theme_DeviceDefault_Dialog);
                    dialogEmpate.setContentView(R.layout.dialog_empate);
                    dialogEmpate.setTitle("Empate");
                    dialogEmpate.setCancelable(true);

                    final RadioGroup rgEmpate = (RadioGroup) dialogEmpate.findViewById(R.id.rgDialogEmpate);

                    // Gera radiobuttons e os adiciona ao RadioGroup
                    for (int i = 0; i < listEmpatados.size(); i++) {
                        RadioButton rdbtn = new RadioButton(this);
                        rdbtn.setId(i);
                        rdbtn.setText(listEmpatados.get(i).getNome());
                        rgEmpate.addView(rdbtn);
                    }

                    // Finaliza a partida configurando o vencedor de acordo com o radioButton selecionado
                    Button btnFinalizar = (Button) dialogEmpate.findViewById(R.id.btnFinlizarEmpate);
                    btnFinalizar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // captura o id do radiobutton e procura na lista e configura o vencedor
                            configurarNovaRodada(listEmpatados.get(rgEmpate.getCheckedRadioButtonId()).getNome());
                            dialogEmpate.dismiss();
                        }
                    });

                    dialogEmpate.show();
                }

            } else {

                // Dialog Finalizar grupo de jogo
                final Dialog dialogFinalizar = new Dialog(PartidaAberta.this, android.R.style.Theme_DeviceDefault_Dialog);

                // Configura a view para o Dialog
                dialogFinalizar.setContentView(R.layout.dialog_finalizar_grupo);
                dialogFinalizar.setTitle("Aviso!");

                Button btnCancelar = (Button) dialogFinalizar.findViewById(R.id.btnCancelar);
                Button btnSair = (Button) dialogFinalizar.findViewById(R.id.btnSair);

                // btn Finalizar Grupo
                btnSair.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogFinalizar.dismiss();
                        saveAndQuit();
                    }
                });

                // btn Cancelar
                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogFinalizar.dismiss();
                    }
                });
                dialogFinalizar.show();

            }
        }

        return super.

                onOptionsItemSelected(item);
    }
}
