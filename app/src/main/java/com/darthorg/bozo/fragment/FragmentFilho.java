package com.darthorg.bozo.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.darthorg.bozo.R;
import com.darthorg.bozo.adapter.ValoresPecasGridAdapter;
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
    private boolean empatado;
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
        fundoResultado.setVisibility(View.INVISIBLE);
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
                buttons.get(i).setTextColor(Color.RED);
            }
            cliquePeca(pecasBozo.get(i), buttons.get(i));
        }


    }

    /**
     * Evento de clique na peça do Bozó
     *
     * @param pecaBozo
     * @param button
     */
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
                                          //   final EditText et = (EditText) dialogMarcarBozo.findViewById(R.id.etPonto);
                                          TextView tituloPonto = (TextView) dialogMarcarBozo.findViewById(R.id.tituloPonto);
                                          Button btnRiscar = (Button) dialogMarcarBozo.findViewById(R.id.btnRiscar);
                                          GridView gridValoresPecas = (GridView) dialogMarcarBozo.findViewById(R.id.gvValoresPecas);
                                          ValoresPecasGridAdapter valores;
                                          ImageButton btnCancelar = (ImageButton) dialogMarcarBozo.findViewById(R.id.btnCancelar);


                                          switch (pecaBozo.getNome()) {
                                              case nomeAz:
                                                  tituloPonto.setText(getString(R.string.az));

                                                  //Configura os valores possiveis em cada posição
                                                  valores = new ValoresPecasGridAdapter(getContext(), pecaBozoAz.getValoresPossiveis());
                                                  gridValoresPecas.setAdapter(valores);

                                                  cliqueValor(gridValoresPecas, pecaBozoAz, button, dialogMarcarBozo);

                                                  btnRiscar.setText(getString(R.string.riscar) + getString(R.string.az));
                                                  break;
                                              case nomeDuque:
                                                  tituloPonto.setText(getString(R.string.duque));

                                                  //Configura os valores possiveis em cada posição
                                                  valores = new ValoresPecasGridAdapter(getContext(), pecaBozoDuque.getValoresPossiveis());
                                                  gridValoresPecas.setAdapter(valores);

                                                  cliqueValor(gridValoresPecas, pecaBozoDuque, button, dialogMarcarBozo);

                                                  btnRiscar.setText(getString(R.string.riscar) + getString(R.string.duque));
                                                  break;
                                              case nomeTerno:
                                                  tituloPonto.setText(getString(R.string.terno));

                                                  //Configura os valores possiveis em cada posição
                                                  valores = new ValoresPecasGridAdapter(getContext(), pecaBozoTerno.getValoresPossiveis());
                                                  gridValoresPecas.setAdapter(valores);

                                                  cliqueValor(gridValoresPecas, pecaBozoTerno, button, dialogMarcarBozo);

                                                  btnRiscar.setText(getString(R.string.riscar) + getString(R.string.terno));
                                                  break;
                                              case nomeQuadra:
                                                  tituloPonto.setText(getString(R.string.quadra));

                                                  //Configura os valores possiveis em cada posição
                                                  valores = new ValoresPecasGridAdapter(getContext(), pecaBozoQuadra.getValoresPossiveis());
                                                  gridValoresPecas.setAdapter(valores);

                                                  cliqueValor(gridValoresPecas, pecaBozoQuadra, button, dialogMarcarBozo);

                                                  btnRiscar.setText(getString(R.string.riscar) + getString(R.string.quadra));
                                                  break;
                                              case nomeQuina:
                                                  tituloPonto.setText(getString(R.string.quina));

                                                  //Configura os valores possiveis em cada posição
                                                  valores = new ValoresPecasGridAdapter(getContext(), pecaBozoQuina.getValoresPossiveis());
                                                  gridValoresPecas.setAdapter(valores);

                                                  cliqueValor(gridValoresPecas, pecaBozoQuina, button, dialogMarcarBozo);

                                                  btnRiscar.setText(getString(R.string.riscar) + getString(R.string.quina));
                                                  break;
                                              case nomeSena:
                                                  tituloPonto.setText(getString(R.string.sena));

                                                  //Configura os valores possiveis em cada posição
                                                  valores = new ValoresPecasGridAdapter(getContext(), pecaBozoSena.getValoresPossiveis());
                                                  gridValoresPecas.setAdapter(valores);

                                                  cliqueValor(gridValoresPecas, pecaBozoSena, button, dialogMarcarBozo);

                                                  btnRiscar.setText(getString(R.string.riscar) + getString(R.string.sena));
                                                  break;
                                              case nomeFull:
                                                  tituloPonto.setText(getString(R.string.full));

                                                  //Configura os valores possiveis em cada posição
                                                  valores = new ValoresPecasGridAdapter(getContext(), pecaBozoFull.getValoresPossiveis());
                                                  gridValoresPecas.setAdapter(valores);

                                                  cliqueValor(gridValoresPecas, pecaBozoFull, button, dialogMarcarBozo);

                                                  btnRiscar.setText(getString(R.string.riscar) + getString(R.string.full));
                                                  break;
                                              case nomeSeguida:
                                                  tituloPonto.setText(getString(R.string.seguida));

                                                  valores = new ValoresPecasGridAdapter(getContext(), pecaBozoSeguida.getValoresPossiveis());
                                                  gridValoresPecas.setAdapter(valores);

                                                  cliqueValor(gridValoresPecas, pecaBozoSeguida, button, dialogMarcarBozo);

                                                  btnRiscar.setText(getString(R.string.riscar) + getString(R.string.seguida));
                                                  break;
                                              case nomeQuadrada:
                                                  tituloPonto.setText(getString(R.string.quadrada));

                                                  //Configura os valores possiveis em cada posição
                                                  valores = new ValoresPecasGridAdapter(getContext(), pecaBozoQuadrada.getValoresPossiveis());
                                                  gridValoresPecas.setAdapter(valores);

                                                  cliqueValor(gridValoresPecas, pecaBozoQuadrada, button, dialogMarcarBozo);

                                                  btnRiscar.setText(getString(R.string.riscar) + getString(R.string.quadrada));
                                                  break;
                                              case nomeGeneral:
                                                  tituloPonto.setText(getString(R.string.general));

                                                  //Configura os valores possiveis em cada posição
                                                  valores = new ValoresPecasGridAdapter(getContext(), pecaBozoGeneral.getValoresPossiveis());
                                                  gridValoresPecas.setAdapter(valores);

                                                  cliqueValor(gridValoresPecas, pecaBozoGeneral, button, dialogMarcarBozo);

                                                  btnRiscar.setText(getString(R.string.riscar) + getString(R.string.general));
                                                  break;
                                              default:
                                                  break;
                                          }

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

    /**
     * Evento de clique no valor escolhido para a peça do Bozó
     *
     * @param gridView gridview onde o evento sera chamado
     * @param peca     A peça que foi clicada
     * @param btn      O Botao que representa a peça
     * @param dialog   Dialog em que o gridview esta
     */
    private void cliqueValor(GridView gridView, final PecaBozo peca, final Button btn, final Dialog dialog) {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                peca.setPontuacao(peca.getValoresPossiveis().get(position).toString());
                btn.setText(peca.getValoresPossiveis().get(position).toString());
                btn.setTextColor(getResources().getColor(R.color.colorCinza));
                peca.setRiscado(false);

                contador = contarPontos();
                resultadoFinal.setText(contador + "");

                contarPecasUsadas();

                dialog.dismiss();
            }
        });
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

    private void buttomColor() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            txtGanhando.setBackgroundColor(getResources().getColor(R.color.colorGreenA400));
        }
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

    public void setGanhando(boolean ganhando) {
        this.ganhando = ganhando;

        if (getView() != null && isAdded()) {
            if (fundoResultado != null) {
                if (ganhando) {
                    txtGanhando.setText(getString(R.string.ganhando));
                    fundoResultado.setVisibility(View.VISIBLE);
                    buttomColor();
                } else {
                    fundoResultado.setVisibility(View.INVISIBLE);
                }
            }
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

    public int getContador() {
        return contador;
    }

    public boolean isGanhando() {
        return ganhando;
    }

    public boolean isEmpatado() {
        return empatado;
    }

    public void setEmpatado(boolean empatado) {
        this.empatado = empatado;
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
