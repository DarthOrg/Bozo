package com.darthorg.bozo.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.darthorg.bozo.R;
import com.darthorg.bozo.adapter.NovosJogadoresListAdapter;
import com.darthorg.bozo.model.Partida;

import java.util.ArrayList;

public class AdicionarJogadores extends AppCompatActivity {

    private ArrayList<String> jogadores = new ArrayList<>();
    private NovosJogadoresListAdapter adapter;
    private Partida partida;
    ListView listView;

    Button btnAddJogador, btnAnterior;
    Button btnCriar;
    TextView txtAddJ;
    LinearLayout txtFAJ;

    private TextView tituloGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_jogadores);
        getIDs();


        String nomepartida = getIntent().getStringExtra("nomepartida");
        tituloGrupo.setText(nomepartida);


        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdicionarJogadores.this, NovaPartida.class);
                startActivity(intent);
                finish();
            }
        });

        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jogadores.size() == 0) {
                    txtFAJ.setBackgroundColor(getResources().getColor(R.color.colorLaranjaFlat));
                    txtAddJ.setText(R.string.requer_jogadores);
                    btnCriar.setVisibility(View.GONE);
                    btnAnterior.setVisibility(View.GONE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(getResources().getColor(R.color.colorLaranjaFlat));
                    }


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SystemClock.sleep(5000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    txtAddJ.setText(R.string.textoAdicionarJogadores);
                                    txtFAJ.setBackgroundColor(getResources().getColor(R.color.colorAccentDark));
                                    btnCriar.setVisibility(View.VISIBLE);
                                    btnAnterior.setVisibility(View.VISIBLE);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        Window window = getWindow();
                                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                        window.setStatusBarColor(getResources().getColor(R.color.colorAccent700));
                                    }
                                }
                            });
                        }
                    }).start();

                } else if (jogadores.size() == 1) {

                    txtFAJ.setBackgroundColor(getResources().getColor(R.color.colorLaranjaFlat));
                    txtAddJ.setText(R.string.requer_jogador);
                    btnCriar.setVisibility(View.GONE);
                    btnAnterior.setVisibility(View.GONE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(getResources().getColor(R.color.colorLaranjaFlat));
                    }

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SystemClock.sleep(5000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    txtAddJ.setText(R.string.textoAdicionarJogadores);
                                    txtFAJ.setBackgroundColor(getResources().getColor(R.color.colorAccentDark));
                                    btnCriar.setVisibility(View.VISIBLE);
                                    btnAnterior.setVisibility(View.VISIBLE);
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        Window window = getWindow();
                                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                        window.setStatusBarColor(getResources().getColor(R.color.colorAccent700));
                                    }
                                }
                            });
                        }
                    }).start();
                } else {
                    Intent intent = new Intent(AdicionarJogadores.this, PartidaAberta.class);
                    intent.putExtra("nomepartida", tituloGrupo.getText().toString());
                    intent.putStringArrayListExtra("jogadores", jogadores);
                    intent.putExtra("partidaNova", true);
                    startActivity(intent);
                    finish();
                }

            }
        });

        listView = (ListView) findViewById(R.id.list_view_jogadores);
        adapter = new NovosJogadoresListAdapter(jogadores, this);
        listView.setAdapter(adapter);


        // tituloGrupo.setText(partida.getNome());

        btnAddJogador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (jogadores.size() == 10){

                    txtFAJ.setBackgroundColor(getResources().getColor(R.color.colorRedFlat));
                    txtAddJ.setText(R.string.textoLimeiteJogadores);
                    btnAddJogador.setVisibility(View.GONE);
                    btnCriar.setTextColor(getResources().getColor(R.color.colorWhite));
                    btnAnterior.setTextColor(getResources().getColor(R.color.colorWhite));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        Window window = getWindow();
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(getResources().getColor(R.color.colorRedFlat));
                    }

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SystemClock.sleep(10000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    txtAddJ.setText(R.string.textoAdicionarJogadores);
                                    btnAddJogador.setVisibility(View.VISIBLE);
                                    txtFAJ.setBackgroundColor(getResources().getColor(R.color.colorAccentDark));
                                    btnCriar.setTextColor(getResources().getColor(R.color.colorAccentA100));
                                    btnAnterior.setTextColor(getResources().getColor(R.color.colorAccentA100));
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        Window window = getWindow();
                                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                                        window.setStatusBarColor(getResources().getColor(R.color.colorAccent700));
                                    }
                                }
                            });
                        }
                    }).start();
                }else {

                    LayoutInflater inflater = getLayoutInflater();

                    View dialoglayout = inflater.inflate(R.layout.dialog_novo_jogador, null);

                    final EditText etNomeJogador = (EditText) dialoglayout.findViewById(R.id.edit_nome_novo_jogador);

                    AlertDialog.Builder builder = new AlertDialog.Builder(AdicionarJogadores.this);
                    builder.setTitle(getString(R.string.adicionarJogador));
                    builder.setIcon(R.drawable.ic_add_jogador);

                    builder.setPositiveButton(getString(R.string.adicionar), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (!TextUtils.isEmpty(etNomeJogador.getText().toString())) {
                                jogadores.add(etNomeJogador.getText().toString());
                                adapter.notifyDataSetChanged();
                                etNomeJogador.setText(null);
                                dialog.dismiss();
                            }else {
                                Toast.makeText(getApplicationContext(), "Nenhum jogador foi adicionado", Toast.LENGTH_LONG).show();
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

                }
            }
        });


    }

    private void getIDs() {

        btnAnterior = (Button) findViewById(R.id.btnAnterior);
        btnCriar = (Button) findViewById(R.id.btnCriar);
        btnAddJogador = (Button) findViewById(R.id.btnAddJogador);

        txtAddJ = (TextView) findViewById(R.id.txtAdicionarJogadores);
        txtFAJ = (LinearLayout) findViewById(R.id.fundotxtAJ);

        tituloGrupo = (TextView) findViewById(R.id.titulodogrupo);



    }

}
