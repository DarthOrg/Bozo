package com.darthorg.bozo.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.darthorg.bozo.R;
import com.darthorg.bozo.adapter.NovosJogadoresListAdapter;
import com.darthorg.bozo.fragment.FragmentFilho;
import com.darthorg.bozo.model.Jogador;

import java.util.ArrayList;

public class NovaPartida extends AppCompatActivity {

    private Toolbar toolbar;
    private ArrayList<String> jogadores = new ArrayList<>();
    private NovosJogadoresListAdapter adapter;
    ListView listView;

    Button btnCriarGrupo, btnAddJogador,btnEditarNomeGrupo;

    TextView txtErro, txtNomeGrupo, contagemJogadores;

    FrameLayout J1, J2, J3, J4, J5, J6, J7, J8, J9, J10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_partida);
        getIDs();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//Contador Jogadors
        contarJogadores();

        //Cor dos botões para versões anteriores da api 21
        corBotoes();

        listView = (ListView) findViewById(R.id.list_view_jogadores);
        adapter = new NovosJogadoresListAdapter(jogadores, this);
        listView.setAdapter(adapter);

        btnCriarGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(jogadores.size() == 0) {
                    txtErro.setVisibility(View.VISIBLE);
                    txtErro.setText("Adicione pelo menos 2 jogadores para criar o grupo");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SystemClock.sleep(5000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txtErro.setVisibility(View.GONE);
                                }
                            });
                        }
                    }).start();

                } else if (jogadores.size() == 1) {
                    txtErro.setVisibility(View.VISIBLE);
                    txtErro.setText("Adicione mais 1 ou mais jogadores para criar o grupo");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SystemClock.sleep(5000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txtErro.setVisibility(View.GONE);
                                }
                            });
                        }
                    }).start();

                } else {
                    Intent intent = new Intent(NovaPartida.this, PartidaAberta.class);
                    intent.putExtra("nomepartida", txtNomeGrupo.getText().toString());
                    intent.putStringArrayListExtra("jogadores", jogadores);
                    intent.putExtra("partidaNova", true);
                    startActivity(intent);
                    finish();
                }

            }
        });


        btnEditarNomeGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Dialog para Adicionar Jogador
                final Dialog dialogEditarGrupo = new Dialog(NovaPartida.this);
                // Configura a view para o Dialog
                dialogEditarGrupo.setContentView(R.layout.dialog_editar_grupo);
                dialogEditarGrupo.setTitle("Editar nome grupo");

                //Recupera os componentes do layout do custondialog
                final EditText etNomeGrupo = (EditText) dialogEditarGrupo.findViewById(R.id.edit_nome_grupo);
                Button btnSalvar = (Button) dialogEditarGrupo.findViewById(R.id.btnSalvar);
                Button btnCancelar = (Button) dialogEditarGrupo.findViewById(R.id.btnCancelar);

                etNomeGrupo.setText(txtNomeGrupo.getText().toString());
                //Add Jogador
                btnSalvar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        txtNomeGrupo.setText(etNomeGrupo.getText().toString());
                        dialogEditarGrupo.dismiss();
                    }
                });
                //Botão Cancelar
                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogEditarGrupo.dismiss();
                        return;
                    }
                });
                dialogEditarGrupo.show();

            }
        });

        btnAddJogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Dialog para Adicionar Jogador
                final Dialog dialogAdicionarJogador = new Dialog(NovaPartida.this);
                // Configura a view para o Dialog
                dialogAdicionarJogador.setContentView(R.layout.dialog_novo_jogador);
                dialogAdicionarJogador.setTitle("Adicionar Jogador");

                //Recupera os componentes do layout do custondialog
                final EditText etNomeJogador = (EditText) dialogAdicionarJogador.findViewById(R.id.edit_nome_novo_jogador);
                Button btnAdicionarJogador = (Button) dialogAdicionarJogador.findViewById(R.id.btnAdicionarJogador);
                Button btnCancelar = (Button) dialogAdicionarJogador.findViewById(R.id.btnCancelar);

                //Add Jogador
                btnAdicionarJogador.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (jogadores.size() < 10) {
                            if (!TextUtils.isEmpty(etNomeJogador.getText().toString())) {
                                jogadores.add(etNomeJogador.getText().toString());
                                adapter.notifyDataSetChanged();
                                etNomeJogador.setText(null);
                                contarJogadores();
                                dialogAdicionarJogador.dismiss();
                            }


                        } else {
                            Toast.makeText(getApplicationContext(), "Máximo 10 jogadores, você pode adicionar.", Toast.LENGTH_LONG).show();
                        }
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

            }
        });
    }

    private void corBotoes() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            btnCriarGrupo.setBackgroundColor(getResources().getColor(R.color.colorGreenA700));
            btnEditarNomeGrupo.setBackgroundColor(getResources().getColor(R.color.colorWhite));
            btnAddJogador.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getIDs() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        btnAddJogador = (Button) findViewById(R.id.btnAddJogador);
        btnEditarNomeGrupo = (Button) findViewById(R.id.btnEditarNomeGrupo);
        btnCriarGrupo = (Button) findViewById(R.id.btnCriarGrupo);
        txtErro = (TextView) findViewById(R.id.txtErro);
        txtNomeGrupo = (TextView) findViewById(R.id.txtNomeGrupo);

        contagemJogadores = (TextView) findViewById(R.id.contagemJogadores);

        J1 = (FrameLayout) findViewById(R.id.J1);
        J2 = (FrameLayout) findViewById(R.id.J2);
        J3 = (FrameLayout) findViewById(R.id.J3);
        J4 = (FrameLayout) findViewById(R.id.J4);
        J5 = (FrameLayout) findViewById(R.id.J5);
        J6 = (FrameLayout) findViewById(R.id.J6);
        J7 = (FrameLayout) findViewById(R.id.J7);
        J8 = (FrameLayout) findViewById(R.id.J8);
        J9 = (FrameLayout) findViewById(R.id.J9);
        J10 = (FrameLayout) findViewById(R.id.J10);
    }

    private void contarJogadores() {

        if (jogadores.size() == 0) {
                      contagemJogadores.setText("Jogadores");
        } else if (jogadores.size() == 1) {
            contagemJogadores.setText(jogadores.size() + " Jogador");
        } else if (jogadores.size() >= 2) {
                    contagemJogadores.setText(jogadores.size() + " Jogadores");
        }
    }

}
