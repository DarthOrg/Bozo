package com.darthorg.bozo.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    EditText edit_nome_grupo;

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

        btnAddJogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = getLayoutInflater();

                View dialoglayout = inflater.inflate(R.layout.dialog_novo_jogador, null);

                final EditText etNomeJogador = (EditText) dialoglayout.findViewById(R.id.edit_nome_novo_jogador);

                AlertDialog.Builder builder = new AlertDialog.Builder(NovaPartida.this);
                builder.setTitle("Adicionar jogador");
                builder.setIcon(R.drawable.ic_add_jogador);

                builder.setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (jogadores.size() < 10) {
                            if (!TextUtils.isEmpty(etNomeJogador.getText().toString())) {
                                jogadores.add(etNomeJogador.getText().toString());
                                adapter.notifyDataSetChanged();
                                etNomeJogador.setText(null);
                                contarJogadores();
                                dialog.dismiss();
                            }


                        } else {
                            Toast.makeText(getApplicationContext(), "Máximo 10 jogadores, você pode adicionar.", Toast.LENGTH_LONG).show();
                        }

                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setView(dialoglayout);
                builder.show();

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
            } else if (!TextUtils.isEmpty(edit_nome_grupo.getText())) {
                Intent intent = new Intent(NovaPartida.this, PartidaAberta.class);
                intent.putExtra("nomepartida", edit_nome_grupo.getText().toString());
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

        edit_nome_grupo = (EditText) findViewById(R.id.edit_nome_grupo);


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
