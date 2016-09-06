package com.darthorg.bozo.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.darthorg.bozo.R;
import com.darthorg.bozo.model.PecaBozo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gustavo on 11/07/2016.
 * Um fragmentFilho é responsavel por saber quantos pontos o jogador esta
 * Versao 2
 */
public class FragmentFilho extends Fragment {

    private String nome;

    private Button btnAz, btnDuque, btnTerno, btnQuadrada, btnSeguida, btnFull, btnQuina, btnSena, btnGeneral, btnQuadra;

    // Nomes das peças
    private final String
            nomeAz = "Az", nomeDuque = "Duque",
            nomeTerno = "Terno", nomeQuadra = "Quadra",
            nomeQuina = "Quina", nomeSena = "Sena",
            nomeFull = "Full", nomeSeguida = "Seguida",
            nomeQuadrada = "Quadrada", nomeGeneral = "General";

    //Objetos do tipo PecaBozo que seram manipulados durante a partida
    private PecaBozo pecaBozoAz = new PecaBozo(nomeAz, null, false);
    private PecaBozo pecaBozoDuque = new PecaBozo(nomeDuque, null, false);
    private PecaBozo pecaBozoTerno = new PecaBozo(nomeTerno, null, false);
    private PecaBozo pecaBozoQuadra = new PecaBozo(nomeQuadra, null, false);
    private PecaBozo pecaBozoQuina = new PecaBozo(nomeQuina, null, false);
    private PecaBozo pecaBozoSena = new PecaBozo(nomeSena, null, false);
    private PecaBozo pecaBozoFull = new PecaBozo(nomeFull, null, false);
    private PecaBozo pecaBozoSeguida = new PecaBozo(nomeSeguida, null, false);
    private PecaBozo pecaBozoQuadrada = new PecaBozo(nomeQuadrada, null, false);
    private PecaBozo pecaBozoGeneral = new PecaBozo(nomeGeneral, null, false);

    public List<PecaBozo> pecasBozo;
    private List<Button> buttons;

    //variaveis que controlam o jogo
    private TextView resultadoFinal;
    private Button txtGanhando;
    private int contador = 0;
    private boolean ganhando;
    private boolean acabouRodada;

    // variavel necessaria para inflar o layout do AlertDialog
    private LayoutInflater dialogInflater;

    RelativeLayout fundoResultado;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filho, container, false);
        getIDs(view);
        obterPecasBozo();
        setEvents();

        dialogInflater = inflater;

        for (int i = 0; i < pecasBozo.size(); i++) {
            buttons.get(i).setText(pecasBozo.get(i).getPontuacao());
        }

        resultadoFinal.setText(contador + "");

        return view;
    }


    private void getIDs(View view) {
        //Exemplo :
        // TextView textview = (TextView) view.findViewById(R.id.meuTextView);

        resultadoFinal = (TextView) view.findViewById(R.id.txtResultadoJogador);
        fundoResultado = (RelativeLayout) view.findViewById(R.id.fundoresultado);

        btnAz = (Button) view.findViewById(R.id.btnAz);
        btnDuque = (Button) view.findViewById(R.id.btnDuque);
        btnTerno = (Button) view.findViewById(R.id.btnTerno);
        btnQuadrada = (Button) view.findViewById(R.id.btnQuadrada);
        btnQuadra = (Button) view.findViewById(R.id.btnQuadra);
        btnSeguida = (Button) view.findViewById(R.id.btnSeguida);
        btnFull = (Button) view.findViewById(R.id.btnFull);
        btnQuina = (Button) view.findViewById(R.id.btnQuina);
        btnSena = (Button) view.findViewById(R.id.btnSena);
        btnGeneral = (Button) view.findViewById(R.id.btnGeneral);


        txtGanhando = (Button) view.findViewById(R.id.txtGanhando);

    }

    private void setEvents() {

        for (int i = 0; i < pecasBozo.size(); i++) {
            if (!pecasBozo.get(i).isRiscado()) {
                cliquePeca(pecasBozo.get(i), buttons.get(i));
            }
        }


    }

    public void cliquePeca(final PecaBozo pecaBozo, final Button button) {

        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                          //Dialog para Adicionar Jogador
                                          final Dialog dialogMarcarBozo = new Dialog(getContext());
                                          // Configura a view para o Dialog
                                          dialogMarcarBozo.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                                          dialogMarcarBozo.setContentView(R.layout.dialog_pontos);

                                          //Recupera os componentes do layout do custondialog
                                          final EditText et = (EditText) dialogMarcarBozo.findViewById(R.id.etPonto);
                                          TextView tituloPonto = (TextView) dialogMarcarBozo.findViewById(R.id.tituloPonto);
                                          Button btnMarcar = (Button) dialogMarcarBozo.findViewById(R.id.btnMarcar);
                                          Button btnRiscar = (Button) dialogMarcarBozo.findViewById(R.id.btnRiscar);
                                          ImageButton btnCancelar = (ImageButton) dialogMarcarBozo.findViewById(R.id.btnCancelar);


                                          switch (pecaBozo.getNome()) {
                                              case nomeAz:
                                                  tituloPonto.setText(getString(R.string.Az) + " ( 1 á 5 )");
                                                  et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
                                                  btnRiscar.setText("Riscar Áz");
                                                  break;
                                              case nomeDuque:
                                                  tituloPonto.setText(getString(R.string.Duque) + " ( 2 á 10 )");
                                                  et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
                                                  btnRiscar.setText("Riscar Duque");
                                                  break;
                                              case nomeTerno:
                                                  tituloPonto.setText(getString(R.string.Terno) + " ( 3 á 15 )");
                                                  et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
                                                  btnRiscar.setText("Riscar Terno");
                                                  break;
                                              case nomeQuadra:
                                                  tituloPonto.setText(getString(R.string.Quadra) + " ( 4 á 20 )");
                                                  et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
                                                  btnRiscar.setText("Riscar Quadra");
                                                  break;
                                              case nomeQuina:
                                                  tituloPonto.setText(getString(R.string.Quina) + " ( 5 á 25 )");
                                                  et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
                                                  btnRiscar.setText("Riscar Quina");
                                                  break;
                                              case nomeSena:
                                                  tituloPonto.setText(getString(R.string.Sena) + " ( 6 á 30 )");
                                                  et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
                                                  btnRiscar.setText("Riscar Sena");
                                                  break;
                                              case nomeFull:
                                                  tituloPonto.setText(getString(R.string.Full) + " ( 10 ou 15 )");
                                                  et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
                                                  btnRiscar.setText("Riscar Full");
                                                  break;
                                              case nomeSeguida:
                                                  tituloPonto.setText(getString(R.string.Seguida) + " ( 20 ou 25)");
                                                  et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
                                                  btnRiscar.setText("Riscar Seguida");
                                                  break;
                                              case nomeQuadrada:
                                                  tituloPonto.setText(getString(R.string.Quadrada) + " ( 30 ou 35 )");
                                                  et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});
                                                  btnRiscar.setText("Riscar Quadrada");
                                                  break;
                                              case nomeGeneral:
                                                  tituloPonto.setText(getString(R.string.General) + " ( 40 ou 100)");
                                                  et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
                                                  btnRiscar.setText("Riscar General");
                                                  break;
                                              default:
                                                  break;
                                          }

                                          //Botão Marcar
                                          btnMarcar.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {

                                                  if (TextUtils.isEmpty(et.getText().toString())) {
                                                      dialogMarcarBozo.dismiss();
                                                  } else {
                                                      pecaBozo.setPontuacao(et.getText().toString());
                                                      button.setText(et.getText().toString());
                                                      button.setTextColor(getResources().getColor(R.color.colorCinza));
                                                      pecaBozo.setRiscado(false);
                                                  }
                                                  contador = contarPontos();
                                                  resultadoFinal.setText(contador + "");

                                                  contarPecasUsadas();
                                                  dialogMarcarBozo.dismiss();

                                              }
                                          });
                                          //Botão Riscar
                                          btnRiscar.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {

                                                  button.setText("X");
                                                  button.setTextColor(Color.RED);

                                                  // Necessario para fazer a verificação
                                                  pecaBozo.setRiscado(true);
                                                  pecaBozo.setPontuacao("x");

                                                  contador = contarPontos();
                                                  resultadoFinal.setText(contador + "");

                                                  contarPecasUsadas();
                                                  dialogMarcarBozo.dismiss();
                                              }
                                          });
                                          //Botão Cancelar
                                          btnCancelar.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {
                                                  dialogMarcarBozo.dismiss();
                                                  return;
                                              }
                                          });
                                          dialogMarcarBozo.setCancelable(true);
                                          dialogMarcarBozo.show();

                                      }
                                  }
        );
    }

    public int contarPontos() {

        int pontos = 0;

        for (int i = 0; i < pecasBozo.size(); i++) {
            if (pecasBozo.get(i).isRiscado() == false && pecasBozo.get(i).getPontuacao() != null) {
                pontos = pontos + Integer.parseInt(pecasBozo.get(i).getPontuacao());
            }
        }
        return pontos;
    }

    public void obterPecasBozo() {

        pecasBozo = new ArrayList<>();
        //Adiciona a lista as peças
        pecasBozo.add(pecaBozoAz);
        pecasBozo.add(pecaBozoDuque);
        pecasBozo.add(pecaBozoTerno);
        pecasBozo.add(pecaBozoQuadra);
        pecasBozo.add(pecaBozoQuina);
        pecasBozo.add(pecaBozoSena);
        pecasBozo.add(pecaBozoFull);
        pecasBozo.add(pecaBozoSeguida);
        pecasBozo.add(pecaBozoQuadrada);
        pecasBozo.add(pecaBozoGeneral);


        buttons = new ArrayList<>();
        // Adiciona a lista os botoes utilizados
        buttons.add(btnAz);
        buttons.add(btnDuque);
        buttons.add(btnTerno);
        buttons.add(btnQuadra);
        buttons.add(btnQuina);
        buttons.add(btnSena);
        buttons.add(btnFull);
        buttons.add(btnSeguida);
        buttons.add(btnQuadrada);
        buttons.add(btnGeneral);

    }


    public int getContador() {
        return contador;
    }

    public boolean isGanhando() {
        return ganhando;
    }

    public void setGanhando(boolean ganhando) {
        this.ganhando = ganhando;
        if (ganhando) {
            txtGanhando.setText("Ganhando");
            fundoResultado.setVisibility(View.VISIBLE);
        } else {
            fundoResultado.setVisibility(View.INVISIBLE);
        }
    }

    public void contarPecasUsadas() {

        int contadorPecas = 0;

        for (int i = 0; i < pecasBozo.size(); i++) {
            if (pecasBozo.get(i).getPontuacao() != null) {
                contadorPecas++;
            }
        }

        if (contadorPecas >= 10) {
            Log.i("pecasUsadas", "ACABOUUUUU");
            acabouRodada = true;
        } else {
            Log.i("pecasUsadas", "A PARTIDA AINDA ESTA ROLANDO");
            acabouRodada = false;
        }
    }

    public boolean isAcabouRodada() {
        return acabouRodada;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
