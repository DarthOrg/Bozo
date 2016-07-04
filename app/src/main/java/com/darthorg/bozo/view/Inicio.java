package com.darthorg.bozo.view;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.darthorg.bozo.R;

public class Inicio extends AppCompatActivity {


    private Toolbar toolbar;
    Button btnJogar, btnInfoAs, btnInfoDuque, btnInfoTerno, btnInfoQuadra, btnInfoQuina, btnInfoSena;
    private int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);



        //Botão inicar partida
        btnJogar = (Button) findViewById(R.id.btn_jogar);
        btnJogar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                LayoutInflater inflater = getLayoutInflater();
                //Recebe a activity para persolnalizar o dialog
                View dialogLayout = inflater.inflate(R.layout.theme_dialog_inicio, null);
                //Botão nova partida
                Button btnNovaPartida = (Button) dialogLayout.findViewById(R.id.btn_nova_partida);
                btnNovaPartida.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Inicio.this, NovaPartida.class);
                        startActivity(intent);
                    }
                });
                //Botão Suas partidas
                Button btnPartidasSalvas = (Button) dialogLayout.findViewById(R.id.btn_partidas_salvas);
                btnPartidasSalvas.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Inicio.this, ListaDePartidas.class);
                        startActivity(intent);
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(Inicio.this);
                //builder.setNegativeButton("Cancelar",null);
                builder.setView(dialogLayout);
                builder.show();
            }
        });

        //Botão de informção do dado (Ás(01))
        btnInfoAs = (Button) findViewById(R.id.btn_info_as);
        btnInfoAs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Inicio.this);
                builder.setTitle("ÁZ ou ( Número 01 )");
                builder.setMessage("[ Áz ] é uma pedra de menor valor no Bozó. " +
                        " Sua posição fica no canto superior á esquerda," +
                        " soma-se os valores da pedra de áz." +
                        " sua pontuação varia de 1 à 5 pontos");
                builder.setIcon(R.drawable.ic_um);
                builder.setNegativeButton("instruções",null);
                builder.setPositiveButton("Entendi",null);
                builder.show();
            }
        });
        //Botão de informção do dado (Duque(02))
        btnInfoDuque = (Button) findViewById(R.id.btn_info_duque);
        btnInfoDuque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Inicio.this);
                builder.setTitle("Duque ou ( Número 02 )");
                builder.setMessage("[ Duque ] é uma pedra com o valor maior do que a de ás. " +
                        " Sua posição fica no centro á esquerda," +
                        " soma-se os valores da pedra de Duque." +
                        " Sua pontuação varia de 2 à 10 pontos");
                builder.setIcon(R.drawable.ic_duque);
                builder.setNegativeButton("instruções",null);
                builder.setPositiveButton("Entendi",null);
                builder.show();
            }
        });
        //Botão de informção do dado (Terno(03))
        btnInfoTerno = (Button) findViewById(R.id.btn_info_terno);
        btnInfoTerno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Inicio.this);
                builder.setTitle("Terno ou ( Número 03 )");
                builder.setMessage("[ Terno ] é uma pedra com o valor maior do que a de Duque. " +
                        " Sua posição fica no canto inferior á esquerda," +
                        " soma-se os valores da pedra de Terno, " +
                        " sua pontuação varia de 3 à 15 pontos");
                builder.setIcon(R.drawable.ic_terno);
                builder.setNegativeButton("instruções",null);
                builder.setPositiveButton("Entendi",null);
                builder.show();
            }
        });
        //Botão de informção do dado (Quadra(04))
        btnInfoQuadra = (Button) findViewById(R.id.btn_info_quadra);
        btnInfoQuadra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Inicio.this);
                builder.setTitle("Quadra ou ( Número 04 )");
                builder.setMessage("[ Quadra ] é uma pedra com o valor maior do que a de Terno. " +
                        " Sua posição fica no canto superior á direita," +
                        " soma-se os valores da pedra de Quadra, " +
                        " sua pontuação varia de 4 à 20 pontos");
                builder.setIcon(R.drawable.ic_quadra);
                builder.setNegativeButton("instruções",null);
                builder.setPositiveButton("Entendi",null);
                builder.show();
            }
        });

        //Botão de informção do dado (Quina(05))
        btnInfoQuina = (Button) findViewById(R.id.btn_info_quina);
        btnInfoQuina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Inicio.this);
                builder.setTitle("Quina ou ( Número 05 )");
                builder.setMessage("[ Quina ] é uma pedra com o valor maior do que a de Quadra. " +
                        " Sua posição fica no centro á direita," +
                        " soma-se os valores da pedra de Quina, " +
                        " sua pontuação varia de 5 à 25 pontos");
                builder.setIcon(R.drawable.ic_quina);
                builder.setNegativeButton("instruções",null);
                builder.setPositiveButton("Entendi",null);
                builder.show();
            }
        });
        //Botão de informção do dado (Sena(06))
        btnInfoSena = (Button) findViewById(R.id.btn_info_sena);
        btnInfoSena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Inicio.this);
                builder.setTitle("Sena ou ( Número 06 )");
                builder.setMessage("[ Sena ] é uma pedra com o valor maior do que a de Quina. " +
                        " Sua posição fica no canto inferior á direita," +
                        " soma dos valores da pedra de Sena, " +
                        " sua pontuação varia de 6 à 30 pontos");
                builder.setIcon(R.drawable.ic_sena);
                builder.setNegativeButton("instruções",null);
                builder.setPositiveButton("Entendi",null);
                builder.show();
            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inicio_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_configuracoes) {
            //Intent intent = new Intent(this,ListaDePlacar.class);
            //startActivity(intent);
            return true;
        } else if (id == R.id.action_sobre) {
            //Intent intent = new Intent(this,ListaDePlacar.class);
            //startActivity(intent);
            return true;
        } else if (id == R.id.action_bloquear_som) {
            //Intent intent = new Intent(this,ListaDePlacar.class);
            //startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
