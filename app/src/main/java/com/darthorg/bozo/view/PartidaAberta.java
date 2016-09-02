package com.darthorg.bozo.view;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
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

import static android.support.design.widget.BottomSheetBehavior.STATE_EXPANDED;
import static android.support.design.widget.BottomSheetBehavior.from;


/**
 * Partida que Gerencia o Jogo
 */
public class PartidaAberta extends AppCompatActivity {

    //Objetos que vao ser Manipulados durante a rodada
    private Partida partida;
    private Rodada rodada;

    private List<Jogador> listJogadoresBanco;
    private List<Rodada> rodadasBD;
    private ArrayList<Rodada> listRodadas = new ArrayList<>();

    private List<Jogador> jogadoresRodada = new ArrayList<>();
    private List<FragmentFilho> listaFragments;


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
                final Dialog dialogAdicionarJogador = new Dialog(PartidaAberta.this);
                // Configura a view para o Dialog
                dialogAdicionarJogador.setContentView(R.layout.dialog_novo_jogador);

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
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        //Remover jogador
        BSremoverJogador.setOnClickListener(new View.OnClickListener() {
            @Override
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
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        // Placar
        BSplacar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PartidaAberta.this, ListaDePlacar.class);
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
     * Compara a pontuação dos fragments
     *
     * @return retorna um ganhador
     */
    public Jogador compararPontos() {

        jogadoresRodada.size();

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
            } else {
                ganhador = null;
            }
        }

        return ganhador;
    }

    /**
     * Verifica se todas as peças foram preenchidas
     * todo: Corrigir o bug de riscar a posição para sempre
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

        if (fragmentsCompletos == listaFragments.size()) {
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

        fabMais = (ImageButton) findViewById(R.id.mais);

        BSaddJogador = (ImageButton) findViewById(R.id.bottomSheet_add_jogador);
        BSremoverJogador = (ImageButton) findViewById(R.id.bottomSheet_remover_jogador);
        BSplacar = (ImageButton) findViewById(R.id.bottomSheet_placar);

        sairBS = (LinearLayout) findViewById(R.id.sairBottomSheet);



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
                    jogadoresRodada = new ArrayList<>();
                    listaFragments = new ArrayList<>();

                    //Esta partida sabe o seu ID , Nome , Rodadas e Jogadores
                    partida = partidaController.buscarPartida(bundleParams.getLong("partidaSalva"));
                    tituloGrupo.setText(partida.getNome());
                    Log.i("partidasalva", "id da partida :" + bundleParams.getLong("partidaSalva"));

                    rodadasBD = partidaController.buscarRodadasPartida(partida.getIdPartida());

                    if (rodadasBD.size() != 0) {
                        // Se der tudo certo ate aqui esta rodada é a ultima e ela tem um id .
                        rodada = rodadasBD.get(rodadasBD.size() - 1);
                    }

                    listJogadoresBanco = rodadaController.buscarJogadoresRodada(rodada.getIdRodada());

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

    public void configurarNovaRodada() {

        List<Jogador> auxJogadoresRodadas = new ArrayList<>();

        for (int i = 0; i < jogadoresRodada.size(); i++) {
            Jogador j = new Jogador();
            j.setNome(jogadoresRodada.get(i).getNome());
            j.setPontuacao(listaFragments.get(i).getContador());
            auxJogadoresRodadas.add(j);
        }

        rodada.setNomeVencedor(compararPontos().getNome());
        rodada.setJogadores(auxJogadoresRodadas);
        listRodadas.add(rodada);

        //Remove os fragments
        for (int i = 0; i < listaFragments.size(); i++) {
            adapter.removeFrag(viewPager.getCurrentItem());
        }
        configurarPartida();
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

    /**
     * Salva ou não as partidas
     */
    private void saveAndQuit() {
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
                            // Hmmm , Partida Salva então ?!

                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    //todo: Adaptar este método
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.partida_aberta_menu, menu);
        return true;
    }

    //Botão voltar
    @Override
    public void onBackPressed() {

        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_finalizar) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setCancelable(true);

            if (verificaSeRodadaAcabou()) {

                // Dialog Finalizar grupo de jogo
                final Dialog dialogFinalizar = new Dialog(PartidaAberta.this);

                // Configura a view para o Dialog
                dialogFinalizar.setContentView(R.layout.dialog_ganhou);

                Button btnFinalizar = (Button) dialogFinalizar.findViewById(R.id.btnFinalizar);
                Button btnCancelar = (Button) dialogFinalizar.findViewById(R.id.btnCancelar);
                Button btnJogar = (Button) dialogFinalizar.findViewById(R.id.btnJogar);
                TextView txtNomeJogadorExcluido = (TextView) dialogFinalizar.findViewById(R.id.txtGanhou);
                txtNomeJogadorExcluido.setText(compararPontos().getNome()+" Ganhou");

                // btn Jogar nova rodada
                btnJogar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        configurarNovaRodada();
                        dialogFinalizar.dismiss();
                    }
                });

                // btn Cancelar
                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogFinalizar.dismiss();
                        return;
                    }
                });

                // btn Finalizar Grupo
                btnFinalizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogFinalizar.dismiss();
                        saveAndQuit();
                    }
                });
                dialogFinalizar.show();

            } else {

                // Dialog Finalizar grupo de jogo
                final Dialog dialogFinalizar = new Dialog(PartidaAberta.this);

                // Configura a view para o Dialog
                dialogFinalizar.setContentView(R.layout.dialog_finalizar_grupo);

                Button btnCancelar = (Button) dialogFinalizar.findViewById(R.id.btnCancelar);
                Button btnFinalizar = (Button) dialogFinalizar.findViewById(R.id.btnFinalizar);
                TextView txtNomeJogadorExcluido = (TextView) dialogFinalizar.findViewById(R.id.txtNomeJogadorExcluido);
                txtNomeJogadorExcluido.setText(partida.getNome());

                // btn Finalizar Grupo
                btnFinalizar.setOnClickListener(new View.OnClickListener() {
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
                        return;
                    }
                });
                dialogFinalizar.show();

            }
        }

        return super.

                onOptionsItemSelected(item);
    }
}
