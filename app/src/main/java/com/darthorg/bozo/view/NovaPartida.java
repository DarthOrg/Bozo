package com.darthorg.bozo.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darthorg.bozo.R;

import java.util.ArrayList;

public class NovaPartida extends AppCompatActivity {

    private ArrayList<String> jogadores = new ArrayList<>();

    Button btnCancelar,btnProximo;

    EditText edit_nome_grupo;
    TextView txtAddJ;
    LinearLayout txtFAJ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_partida);
        getIDs();

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edit_nome_grupo.getText())) {
                Intent intent = new Intent(NovaPartida.this, AdicionarJogadores.class);
                intent.putExtra("nomepartida",edit_nome_grupo.getText().toString());
                startActivity(intent);
                finish();
            }else {
                    AlertDialog.Builder builderFimRodada = new AlertDialog.Builder(NovaPartida.this);

                    LayoutInflater layoutInflater = getLayoutInflater();
                    final View dialoglayout = layoutInflater.inflate(R.layout.dialog_partida_aberta, null);

                    builderFimRodada.setView(dialoglayout);

                    final Button btnOk = (Button) dialoglayout.findViewById(R.id.btnAcao);
                    final Button btnCancelar = (Button) dialoglayout.findViewById(R.id.btnCancelarPopup);
                    final  TextView txtTitulo = (TextView) dialoglayout.findViewById(R.id.tituloPopup);
                    final  TextView txtTexto = (TextView) dialoglayout.findViewById(R.id.txtPopup);
                    final LinearLayout fundo = (LinearLayout) dialoglayout.findViewById(R.id.fundoPopUp);


                    final AlertDialog dialog = builderFimRodada.create();


                    txtTitulo.setText(getString(R.string.ops));
                    txtTexto.setText(R.string.textoAdicionarNomeMarcador);
                    fundo.setBackgroundColor(getResources().getColor(R.color.colorLaranja));

                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                }


            }
        });




    }



    private void getIDs() {



        edit_nome_grupo = (EditText) findViewById(R.id.edit_nome_grupo);

        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnProximo = (Button) findViewById(R.id.btnProximo);


        txtAddJ = (TextView) findViewById(R.id.txtAdicionarJogadores);
        txtFAJ = (LinearLayout) findViewById(R.id.fundotxtAJ);


    }
}
