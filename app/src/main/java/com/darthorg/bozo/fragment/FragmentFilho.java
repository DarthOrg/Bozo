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

/**
 * Created by Gustavo on 11/07/2016.
 * Um fragmentFilho é responsavel por saber quantos pontos o jogador esta
 */
public class FragmentFilho extends Fragment {

    private String nomeFragmentFilho;
    private Button btnAz, btnDuque, btnTerno, btnQuadrada, btnSeguida, btnFull, btnQuina, btnSena, btnGeneral, btnQuadra;
    private ImageView riscarAz, riscarDuque, riscarTerno, riscarQuadrada, riscarSeguida, riscarFull, riscarQuina, riscarSena, riscarGeneral, riscarQuadra;

    private final String nomeAz = "Az",
            nomeDuque = "Duque",
            nomeTerno = "Terno",
            nomeQuadra = "Quadra",
            nomeQuina = "Quina",
            nomeSena = "Sena",
            nomeFull = "Full",
            nomeSeguida = "Seguida",
            nomeQuadrada = "Quadrada",
            nomeGeneral = "General";

    //Valores das posiçoes
    private String posicaoAz, posicaoDuque, posicaoTerno, posicaoQuadrada, posicaoSeguida, posicaoFull, posicaoQuina, posicaoSena, posicaoGeneral, posicaoQuadra;


    private TextView resultadoFinal;
    private int contador = 0;

    private LayoutInflater dialogInflater;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filho, container, false);
        getIDs(view);
        setEvents();

        dialogInflater = inflater;

        btnAz.setText(getPosicaoAz());
        btnDuque.setText(getPosicaoDuque());
        btnTerno.setText(getPosicaoTerno());
        btnQuadrada.setText(getPosicaoQuadrada());
        btnQuadra.setText(getPosicaoQuadra());
        btnSeguida.setText(getPosicaoSeguida());
        btnFull.setText(getPosicaoFull());
        btnQuina.setText(getPosicaoQuina());
        btnSena.setText(getPosicaoSena());
        btnGeneral.setText(getPosicaoGeneral());

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

        cliquePeca(btnAz, nomeAz);
        cliquePeca(btnDuque, nomeDuque);
        cliquePeca(btnTerno, nomeTerno);
        cliquePeca(btnQuadra, nomeQuadra);
        cliquePeca(btnQuina, nomeQuina);
        cliquePeca(btnSena, nomeSena);
        cliquePeca(btnFull, nomeFull);
        cliquePeca(btnSeguida, nomeSeguida);
        cliquePeca(btnQuadrada, nomeQuadrada);
        cliquePeca(btnGeneral, nomeGeneral);
    }

    public void cliquePeca(final Button button, final String posicao) {

        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {

                                          View dialogLayout = dialogInflater.inflate(R.layout.dialog_pontos, null);
                                          final EditText et = (EditText) dialogLayout.findViewById(R.id.etPonto);

                                          final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                          switch (posicao) {
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
                                                              switch (posicao) {
                                                                  case nomeAz:
                                                                      setPosicaoAz(et.getText().toString());
                                                                      btnAz.setText(getPosicaoAz());
                                                                      break;
                                                                  case nomeDuque:
                                                                      setPosicaoDuque(et.getText().toString());
                                                                      btnDuque.setText(getPosicaoDuque());
                                                                      break;
                                                                  case nomeTerno:
                                                                      setPosicaoTerno(et.getText().toString());
                                                                      btnTerno.setText(getPosicaoTerno());
                                                                      break;
                                                                  case nomeQuadra:
                                                                      setPosicaoQuadra(et.getText().toString());
                                                                      btnQuadra.setText(getPosicaoQuadra());
                                                                      break;
                                                                  case nomeQuina:
                                                                      setPosicaoQuina(et.getText().toString());
                                                                      btnQuina.setText(getPosicaoQuina());
                                                                      break;
                                                                  case nomeSena:
                                                                      setPosicaoSena(et.getText().toString());
                                                                      btnSena.setText(getPosicaoSena());
                                                                      break;
                                                                  case nomeFull:
                                                                      setPosicaoFull(et.getText().toString());
                                                                      btnFull.setText(getPosicaoFull());
                                                                      break;
                                                                  case nomeSeguida:
                                                                      setPosicaoSeguida(et.getText().toString());
                                                                      btnSeguida.setText(getPosicaoSeguida());
                                                                      break;
                                                                  case nomeQuadrada:
                                                                      setPosicaoQuadrada(et.getText().toString());
                                                                      btnQuadrada.setText(getPosicaoQuadrada());
                                                                      break;
                                                                  case nomeGeneral:
                                                                      setPosicaoGeneral(et.getText().toString());
                                                                      btnGeneral.setText(getPosicaoGeneral());
                                                                      break;
                                                                  default:
                                                                      break;
                                                              }
                                                              contador = contarPontos();
                                                              resultadoFinal.setText(contador + "");
                                                          }
                                                      }
                                                  }
                                          );
                                          builder.setNegativeButton("Cancelar", null);
                                          builder.setNeutralButton("[ X ] Riscar", new DialogInterface.OnClickListener() {
                                              @Override
                                              public void onClick(DialogInterface dialogInterface, int i) {
                                                  //Todo: Validar para n riscar uma peça que ja foi usada
                                                  switch (posicao) {
                                                      case nomeAz:
                                                          if (getPosicaoAz() == null) {
                                                              btnAz.setVisibility(View.GONE);
                                                              riscarAz.setVisibility(View.VISIBLE);
                                                          }
                                                          break;
                                                      case nomeDuque:
                                                          if (getPosicaoDuque() == null) {
                                                              btnDuque.setVisibility(View.GONE);
                                                              riscarDuque.setVisibility(View.VISIBLE);
                                                          }
                                                          break;
                                                      case nomeTerno:
                                                          if (getPosicaoTerno() == null) {
                                                              btnTerno.setVisibility(View.GONE);
                                                              riscarTerno.setVisibility(View.VISIBLE);
                                                          }
                                                          break;
                                                      case nomeQuadra:
                                                          if (getPosicaoQuadra() == null) {
                                                              btnQuadra.setVisibility(View.GONE);
                                                              riscarQuadra.setVisibility(View.VISIBLE);
                                                          }
                                                          break;
                                                      case nomeQuina:
                                                          if (getPosicaoQuina() == null) {
                                                              btnQuina.setVisibility(View.GONE);
                                                              riscarQuina.setVisibility(View.VISIBLE);
                                                          }
                                                          break;
                                                      case nomeSena:
                                                          if (getPosicaoSena() == null) {
                                                              btnSena.setVisibility(View.GONE);
                                                              riscarSena.setVisibility(View.VISIBLE);
                                                          }
                                                          break;
                                                      case nomeFull:
                                                          if (getPosicaoFull() == null) {
                                                              btnFull.setVisibility(View.GONE);
                                                              riscarFull.setVisibility(View.VISIBLE);
                                                          }
                                                          break;
                                                      case nomeSeguida:
                                                          if (getPosicaoSeguida() == null) {
                                                              btnSeguida.setVisibility(View.GONE);
                                                              riscarSeguida.setVisibility(View.VISIBLE);
                                                          }
                                                          break;
                                                      case nomeQuadrada:
                                                          if (getPosicaoQuadrada() == null) {
                                                              btnQuadrada.setVisibility(View.GONE);
                                                              riscarQuadrada.setVisibility(View.VISIBLE);
                                                          }
                                                          break;
                                                      case nomeGeneral:
                                                          if (getPosicaoGeneral() == null) {
                                                              btnGeneral.setVisibility(View.GONE);
                                                              riscarGeneral.setVisibility(View.VISIBLE);
                                                          }
                                                          break;
                                                      default:
                                                          break;
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

        if (getPosicaoAz() != null) {
            pontuacao = pontuacao + Integer.parseInt(getPosicaoAz());
        }
        if (getPosicaoDuque() != null) {
            pontuacao = pontuacao + Integer.parseInt(getPosicaoDuque());
        }
        if (getPosicaoTerno() != null) {
            pontuacao = pontuacao + Integer.parseInt(getPosicaoTerno());
        }
        if (getPosicaoQuadra() != null) {
            pontuacao = pontuacao + Integer.parseInt(getPosicaoQuadra());
        }
        if (getPosicaoQuina() != null) {
            pontuacao = pontuacao + Integer.parseInt(getPosicaoQuina());
        }
        if (getPosicaoSena() != null) {
            pontuacao = pontuacao + Integer.parseInt(getPosicaoSena());
        }
        if (getPosicaoFull() != null) {
            pontuacao = pontuacao + Integer.parseInt(getPosicaoFull());
        }
        if (getPosicaoSeguida() != null) {
            pontuacao = pontuacao + Integer.parseInt(getPosicaoSeguida());
        }
        if (getPosicaoQuadrada() != null) {
            pontuacao = pontuacao + Integer.parseInt(getPosicaoQuadrada());
        }
        if (getPosicaoGeneral() != null) {
            pontuacao = pontuacao + Integer.parseInt(getPosicaoGeneral());
        }

        return pontuacao;

    }

    public String getPosicaoAz() {
        return posicaoAz;
    }

    public void setPosicaoAz(String posicaoAz) {
        this.posicaoAz = posicaoAz;
    }

    public String getPosicaoDuque() {
        return posicaoDuque;
    }

    public void setPosicaoDuque(String posicaoDuque) {
        this.posicaoDuque = posicaoDuque;
    }

    public String getPosicaoTerno() {
        return posicaoTerno;
    }

    public void setPosicaoTerno(String posicaoTerno) {
        this.posicaoTerno = posicaoTerno;
    }

    public String getPosicaoQuadrada() {
        return posicaoQuadrada;
    }

    public void setPosicaoQuadrada(String posicaoQuadrada) {
        this.posicaoQuadrada = posicaoQuadrada;
    }

    public String getPosicaoQuadra() {
        return posicaoQuadra;
    }

    public void setPosicaoQuadra(String posicaoQuadra) {
        this.posicaoQuadra = posicaoQuadra;
    }

    public String getPosicaoSeguida() {
        return posicaoSeguida;
    }

    public void setPosicaoSeguida(String posicaoSeguida) {
        this.posicaoSeguida = posicaoSeguida;
    }

    public String getPosicaoFull() {
        return posicaoFull;
    }

    public void setPosicaoFull(String posicaoFull) {
        this.posicaoFull = posicaoFull;
    }

    public String getPosicaoQuina() {
        return posicaoQuina;
    }

    public void setPosicaoQuina(String posicaoQuina) {
        this.posicaoQuina = posicaoQuina;
    }

    public String getPosicaoSena() {
        return posicaoSena;
    }

    public void setPosicaoSena(String posicaoSena) {
        this.posicaoSena = posicaoSena;
    }

    public String getPosicaoGeneral() {
        return posicaoGeneral;
    }

    public void setPosicaoGeneral(String posicaoGeneral) {
        this.posicaoGeneral = posicaoGeneral;
    }

    public int getContador() {
        return contador;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }
}
