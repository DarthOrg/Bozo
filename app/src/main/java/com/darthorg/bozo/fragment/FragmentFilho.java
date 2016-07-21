package com.darthorg.bozo.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

    private String nomeFragmentFilho;

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

    private List<PecaBozo> pecasBozo = new ArrayList<PecaBozo>();
    private List<Button> buttons = new ArrayList<Button>();
    private List<ImageView> riscos = new ArrayList<ImageView>();

    //variaveis que controlam o jogo
    private TextView resultadoFinal;
    private int contador = 0;

    // variavel necessaria para inflar o layout do AlertDialog
    private LayoutInflater dialogInflater;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filho, container, false);
        getIDs(view);
        obterPecasBozo();
        setEvents();

        dialogInflater = inflater;

        for (int i = 0; i < pecasBozo.size(); i++) {
            buttons.get(i).setText(pecaBozoAz.getPontuacao());
        }

        resultadoFinal.setText(contador + "");
        return view;
    }

    private void getIDs(View view) {
        //Exemplo :
        // TextView textview = (TextView) view.findViewById(R.id.meuTextView);

        resultadoFinal = (TextView) view.findViewById(R.id.txtResultadoJogador);

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


        riscarAz = (ImageView) view.findViewById(R.id.riscarAz);
        riscarDuque = (ImageView) view.findViewById(R.id.riscarDuque);
        riscarTerno = (ImageView) view.findViewById(R.id.riscarTerno);
        riscarQuadra = (ImageView) view.findViewById(R.id.riscarQuadra);
        riscarQuina = (ImageView) view.findViewById(R.id.riscarQuina);
        riscarSena = (ImageView) view.findViewById(R.id.riscarSena);
        riscarFull = (ImageView) view.findViewById(R.id.riscarFull);
        riscarSeguida = (ImageView) view.findViewById(R.id.riscarSeguida);
        riscarQuadrada = (ImageView) view.findViewById(R.id.riscarQuadrada);
        riscarGeneral = (ImageView) view.findViewById(R.id.riscarGeneral);

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

                                          View dialogLayout = dialogInflater.inflate(R.layout.dialog_pontos, null);
                                          final EditText et = (EditText) dialogLayout.findViewById(R.id.etPonto);

                                          final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                          switch (pecaBozo.getNome()) {
                                              case nomeAz:
                                                  builder.setTitle(getString(R.string.nameAz) + " ( 1 á 5 )");
                                                  et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
                                                  break;
                                              case nomeDuque:
                                                  builder.setTitle(getString(R.string.nameDuque) + " ( 2 á 10 )");
                                                  break;
                                              case nomeTerno:
                                                  builder.setTitle(getString(R.string.nameTerno) + " ( 3 á 15 )");
                                                  break;
                                              case nomeQuadra:
                                                  builder.setTitle(getString(R.string.nameQuadra) + " ( 4 á 20 )");
                                                  break;
                                              case nomeQuina:
                                                  builder.setTitle(getString(R.string.nameQuina) + " ( 5 á 25 )");
                                                  break;
                                              case nomeSena:
                                                  builder.setTitle(getString(R.string.nameSena) + " ( 6 á 30 )");
                                                  break;
                                              case nomeFull:
                                                  builder.setTitle(getString(R.string.nameFull) + " ( 10 ou 15 )");
                                                  break;
                                              case nomeSeguida:
                                                  builder.setTitle(getString(R.string.nameSeguida) + " ( 20 ou 25)");
                                                  break;
                                              case nomeQuadrada:
                                                  builder.setTitle(getString(R.string.nameQuadrada) + " ( 30 ou 35 )");
                                                  break;
                                              case nomeGeneral:
                                                  builder.setTitle(getString(R.string.nameQuadrada) + " ( 40 ou 100)");
                                                  et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(3)});
                                                  break;
                                              default:
                                                  break;
                                          }

                                          builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                      @Override
                                                      public void onClick(DialogInterface dialog, int which) {
                                                          if (TextUtils.isEmpty(et.getText().toString())) {
                                                              dialog.dismiss();
                                                          } else {
                                                              pecaBozo.setPontuacao(et.getText().toString());
                                                              button.setText(et.getText().toString());
                                                          }
                                                          contador = contarPontos();
                                                          resultadoFinal.setText(contador + "");
                                                      }
                                                  }

                                          );
                                          builder.setNegativeButton("Cancelar", null);
                                          builder.setNeutralButton("[ X ] Riscar", new DialogInterface.OnClickListener() {
                                              @Override
                                              public void onClick(DialogInterface dialogInterface, int i) {
                                                  if (pecaBozo.getPontuacao() == null) {
                                                      pecaBozo.setRiscado(true);
                                                      button.setVisibility(View.GONE);
                                                      risco.setVisibility(View.VISIBLE);
                                                  }
                                                  contador = contarPontos();
                                                  resultadoFinal.setText(contador + "");
                                              }
                                          });
                                          builder.setView(dialogLayout);
                                          builder.setCancelable(false);
                                          builder.show();
                                      }
                                  }
        );
    }

    public int contarPontos() {

        int pontuacao = 0;

        for (int i = 0; i < pecasBozo.size(); i++) {
            if (!pecasBozo.get(i).isRiscado() && pecasBozo.get(i).getPontuacao() != null) {
                pontuacao = pontuacao + Integer.parseInt(pecasBozo.get(i).getPontuacao());
            }
        }

        return pontuacao;
    }

    public void obterPecasBozo() {

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

    public void setContador(int contador) {
        this.contador = contador;
    }
}
