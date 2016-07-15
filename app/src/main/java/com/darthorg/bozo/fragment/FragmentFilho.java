package com.darthorg.bozo.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.darthorg.bozo.R;

/**
 * Created by Gustavo on 11/07/2016.
 */
public class FragmentFilho extends Fragment {

    private String nomeFragmentFilho;
    private Button btnAs, btnDuque, btnTerno, btnQuadrada, btnSeguida, btnFull, btnQuina, btnSena, btnGeneral, btnQuadra;
    private String posicaoAs, posicaoDuque, posicaoTerno, posicaoQuadrada, posicaoSeguida, posicaoFull, posicaoQuina, posicaoSena, posicaoGeneral, posicaoQuadra;
    private TextView resultadoFinal;
    private int contador = 0;

    private Dialog dialog;
    private EditText et;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filho, container, false);
        getIDs(view);
        setEvents();

        btnAs.setText(getPosicaoAs());
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

        btnAs = (Button) view.findViewById(R.id.btnAs);
        btnDuque = (Button) view.findViewById(R.id.btnDuque);
        btnTerno = (Button) view.findViewById(R.id.btnTerno);
        btnQuadrada = (Button) view.findViewById(R.id.btnQuadrada);
        btnQuadra = (Button) view.findViewById(R.id.btnQuadra);
        btnSeguida = (Button) view.findViewById(R.id.btnSeguida);
        btnFull = (Button) view.findViewById(R.id.btnFull);
        btnQuina = (Button) view.findViewById(R.id.btnQuina);
        btnSena = (Button) view.findViewById(R.id.btnSena);
        btnGeneral = (Button) view.findViewById(R.id.btnGeneral);
    }


    private void setEvents() {

        cliquePeca(btnAs, "as");
        cliquePeca(btnDuque, "duque");
        cliquePeca(btnTerno, "terno");
        cliquePeca(btnQuadra, "quadra");
        cliquePeca(btnQuadrada, "quadrada");
        cliquePeca(btnQuina, "quina");
        cliquePeca(btnSena, "sena");
        cliquePeca(btnGeneral, "general");
        cliquePeca(btnSeguida, "seguida");
        cliquePeca(btnFull, "full");
    }

    public void cliquePeca(final Button button, final String posicao) {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_pontos);
                et = (EditText) dialog.findViewById(R.id.etPonto);
                Button btn = (Button) dialog.findViewById(R.id.btnOk);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (TextUtils.isEmpty(et.getText().toString())) {
                            dialog.dismiss();
                        } else {
                            if (posicao == "as") {
                                setPosicaoAs(et.getText().toString());
                                btnAs.setText(getPosicaoAs());
                                contador = contador + Integer.parseInt(getPosicaoAs());
                            } else if (posicao == "duque") {
                                setPosicaoDuque(et.getText().toString());
                                btnDuque.setText(getPosicaoDuque());
                                contador = contador + Integer.parseInt(getPosicaoDuque());
                            } else if (posicao == "terno") {
                                setPosicaoTerno(et.getText().toString());
                                btnTerno.setText(getPosicaoTerno());
                                contador = contador + Integer.parseInt(getPosicaoTerno());
                            } else if (posicao == "quadra") {
                                setPosicaoQuadra(et.getText().toString());
                                btnQuadra.setText(getPosicaoQuadra());
                                contador = contador + Integer.parseInt(getPosicaoQuadra());

                            } else if (posicao == "quadrada") {
                                setPosicaoQuadrada(et.getText().toString());
                                btnQuadrada.setText(getPosicaoQuadrada());
                                contador = contador + Integer.parseInt(getPosicaoQuadrada());

                            } else if (posicao == "quina") {
                                setPosicaoQuina(et.getText().toString());
                                btnQuina.setText(getPosicaoQuina());
                                contador = contador + Integer.parseInt(getPosicaoQuina());

                            } else if (posicao == "sena") {
                                setPosicaoSena(et.getText().toString());
                                btnSena.setText(getPosicaoSena());
                                contador = contador + Integer.parseInt(getPosicaoSena());

                            } else if (posicao == "general") {
                                setPosicaoGeneral(et.getText().toString());
                                btnGeneral.setText(getPosicaoGeneral());
                                contador = contador + Integer.parseInt(getPosicaoGeneral());

                            } else if (posicao == "seguida") {
                                setPosicaoSeguida(et.getText().toString());
                                btnSeguida.setText(getPosicaoSeguida());
                                contador = contador + Integer.parseInt(getPosicaoSeguida());

                            } else if (posicao == "full") {
                                setPosicaoFull(et.getText().toString());
                                btnFull.setText(getPosicaoFull());
                                contador = contador + Integer.parseInt(getPosicaoFull());
                            }
                            resultadoFinal.setText(contador + "");

                        }
                        dialog.dismiss();
                    }
                });// Fim evento de click no dialog
                dialog.show();
            }
        });
    }


    public String getPosicaoAs() {
        return posicaoAs;
    }

    public void setPosicaoAs(String posicaoAs) {
        this.posicaoAs = posicaoAs;
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
