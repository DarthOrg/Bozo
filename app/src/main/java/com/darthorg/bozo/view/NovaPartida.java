package com.darthorg.bozo.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.darthorg.bozo.R;
import com.darthorg.bozo.adapter.NovosJogadoresListAdapter;

import java.util.ArrayList;

public class NovaPartida extends AppCompatActivity {

    private Toolbar toolbar;
    private ArrayList<String> jogadores = new ArrayList<>();
    private NovosJogadoresListAdapter adapter;
    ListView listView;

    Button btnAddJogador;

    FrameLayout btnEditarNomeGrupo;

    LinearLayout campoEditarGrupo;

    EditText edit_nome_grupo;

    Button btnEdtSalvar, btnEdtCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_partida);
        getIDs();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//Contador Jogadors
        contarJogadores();

        listView = (ListView) findViewById(R.id.list_view_jogadores);
        adapter = new NovosJogadoresListAdapter(jogadores, this);
        listView.setAdapter(adapter);

        btnEditarNomeGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campoEditarGrupo.setVisibility(View.VISIBLE);

            }
        });

        btnEdtSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(edit_nome_grupo.getText().toString())) {
                    campoEditarGrupo.setVisibility(View.GONE);
                    toolbar.setTitle(edit_nome_grupo.getText().toString());
                    edit_nome_grupo.setText(null);
                }

            }
        });
        btnEdtCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campoEditarGrupo.setVisibility(View.GONE);
                edit_nome_grupo.setText(null);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nova_partida_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_criar_grupo) {

            if (jogadores.size() == 0) {
                Toast.makeText(getApplicationContext(), "Adicione pelo menos 2 jogadores para criar o grupo", Toast.LENGTH_LONG).show();
            } else if (jogadores.size() == 1) {
                Toast.makeText(getApplicationContext(), "Adicione mais 1 ou mais jogadores para criar o grupo", Toast.LENGTH_LONG).show();
            }else {
                Intent intent = new Intent(NovaPartida.this, PartidaAberta.class);
                intent.putExtra("nomepartida", toolbar.getTitle().toString());
                intent.putStringArrayListExtra("jogadores", jogadores);
                intent.putExtra("partidaNova", true);
                startActivity(intent);
                finish();
            }

        }

        return super.onOptionsItemSelected(item);
    }

    private void getIDs() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        btnAddJogador = (Button) findViewById(R.id.btnAddJogador);

        btnEditarNomeGrupo = (FrameLayout) findViewById(R.id.btnEditarNomeGrupo);

        campoEditarGrupo = (LinearLayout) findViewById(R.id.campoEditarNomegrupo);

        edit_nome_grupo = (EditText) findViewById(R.id.edit_nome_grupo);

        btnEdtSalvar = (Button) findViewById(R.id.btnEdtSalvar);
        btnEdtCancelar = (Button) findViewById(R.id.btnEdtCancelar);


    }

    private void contarJogadores() {

        if (jogadores.size() == 0) {
//                      contagemJogadores.setText("Jogadores");
        } else if (jogadores.size() == 1) {
//            contagemJogadores.setText(jogadores.size() + " Jogador");
        } else if (jogadores.size() >= 2) {
//                    contagemJogadores.setText(jogadores.size() + " Jogadores");
        }
    }

}
