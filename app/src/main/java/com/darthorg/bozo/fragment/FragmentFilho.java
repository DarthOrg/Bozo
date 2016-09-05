package com.darthorg.bozo.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darthorg.bozo.R;
import com.darthorg.bozo.model.PecaBozo;
import com.nispok.snackbar.SnackbarManager;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.getColor;
import static com.darthorg.bozo.R.color.colorAccent700;
import static com.darthorg.bozo.R.color.colorGreen;

/**
 * Created by Gustavo on 11/07/2016.
 * Um fragmentFilho é responsavel por saber quantos pontos o jogador esta
 * Versao 2
 */
public class FragmentFilho extends Fragment {

    private String nome;

    private Button btnAz, btnDuque, btnTerno, btnQuadrada, btnSeguida, btnFull, btnQuina, btnSena, btnGeneral, btnQuadra;
    private ImageView riscarAz, riscarDuque, riscarTerno, riscarQuadrada, riscarSeguida, riscarFull, riscarQuina, riscarSena, riscarGeneral, riscarQuadra;

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
    private List<ImageView> riscos;

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
            if (pecasBozo.get(i).isRiscado()) {
                buttons.get(i).setVisibility(View.GONE);
                riscos.get(i).setVisibility(View.VISIBLE);
            } else {
                cliquePeca(pecasBozo.get(i), buttons.get(i), riscos.get(i));
            }
        }


    }

    public void cliquePeca(final PecaBozo pecaBozo, final Button button, final ImageView risco) {

        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                          if (button.getText().toString() == "X") {

                                              Toast.makeText(getActivity(), "Peça já RISCADA (X), não pode ser usada novamente.", Toast.LENGTH_LONG).show();

                                          } else {

                                              //Dialog para Adicionar Jogador
                                              final Dialog dialogMarcarBozó = new Dialog(getContext());
                                              // Configura a view para o Dialog
                                              dialogMarcarBozó.setContentView(R.layout.dialog_pontos);

                                              //Recupera os componentes do layout do custondialog
                                              final EditText et = (EditText) dialogMarcarBozó.findViewById(R.id.etPonto);
                                              TextView tituloPonto = (TextView) dialogMarcarBozó.findViewById(R.id.tituloPonto);
                                              Button btnMarcar = (Button) dialogMarcarBozó.findViewById(R.id.btnMarcar);
                                              Button btnRiscar = (Button) dialogMarcarBozó.findViewById(R.id.btnRiscar);
                                              ImageButton btnCancelar = (ImageButton) dialogMarcarBozó.findViewById(R.id.btnCancelar);


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
                                                      dialogMarcarBozó.dismiss();
                                                      if (TextUtils.isEmpty(et.getText().toString())) {
                                                          dialogMarcarBozó.dismiss();
                                                      } else {
                                                          pecaBozo.setPontuacao(et.getText().toString());
                                                          button.setText(et.getText().toString());
                                                      }
                                                      contador = contarPontos();
                                                      resultadoFinal.setText(contador + "");

                                                      contarPecasUsadas();

                                                  }
                                              });
                                              //Botão Riscar
                                              btnRiscar.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View view) {

                                                      if (button.getText().toString().isEmpty()) {
                                                          button.setText("X");
                                                          button.setTextColor(Color.RED);
                                                          dialogMarcarBozó.dismiss();
                                                      } else {
                                                          dialogMarcarBozó.dismiss();
                                                          Toast.makeText(getActivity(), "Peça já usada, não pode ser riscada.", Toast.LENGTH_LONG).show();
                                                      }

                                                  }
                                              });
                                              //Botão Cancelar
                                              btnCancelar.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View view) {
                                                      dialogMarcarBozó.dismiss();
                                                      return;
                                                  }
                                              });
                                              dialogMarcarBozó.setCancelable(true);
                                              dialogMarcarBozó.show();
                                          }
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

        riscos = new ArrayList<>();
        // Adiciona a lista os ImgViews
        riscos.add(riscarAz);
        riscos.add(riscarDuque);
        riscos.add(riscarTerno);
        riscos.add(riscarQuadra);
        riscos.add(riscarQuina);
        riscos.add(riscarSena);
        riscos.add(riscarFull);
        riscos.add(riscarSeguida);
        riscos.add(riscarQuadrada);
        riscos.add(riscarGeneral);

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
