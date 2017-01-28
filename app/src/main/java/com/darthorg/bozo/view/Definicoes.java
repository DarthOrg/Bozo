package com.darthorg.bozo.view;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.darthorg.bozo.R;

public class Definicoes extends AppCompatActivity {

    public static final String PREF_CONFIG = "CONFIG_PONTUACAO";
    private SharedPreferences.Editor editor;
    private SharedPreferences preferencias;

    Button valoresPecas, btnEmail, btnTefefone, btnEstado, btnMunicipio, btnAlterarSenha, btnMS, btnRJ, btnPE,btnSobre;
    TextView txtValorespecas, txtSobrenome, txtEmail, txtTelefone, txtEstado, txtMunicipio, txtSenhaAlterada,titleBS,
            txtMS_title, txtMS_subtitle, txtRJ_title, txtRJ_subtitle, txtPE_title, txtPE_subtitle, txtLuzFundo;
    Switch lusFundo;
    ImageButton btnInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definicoes);
        changeStatusBarColor();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_RS);
        setSupportActionBar(toolbar);

//        btnInfo = (ImageButton) findViewById(R.id.btnInfo);
        btnSobre = (Button) findViewById(R.id.btnSobre);
        btnSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Definicoes.this);

                LayoutInflater layoutInflater = getLayoutInflater();
                final View dialoglayout = layoutInflater.inflate(R.layout.dialog_sobre, null);

                builder.setView(dialoglayout);

                builder.show();
            }
        });

        txtLuzFundo = (TextView) findViewById(R.id.txtLuzFundo);
        lusFundo = (Switch) findViewById(R.id.swirchLuzFundo);


        valoresPecas = (Button) findViewById(R.id.btnValoresPecas);
        txtValorespecas = (TextView) findViewById(R.id.txtValorespecas);

        preferencias = getSharedPreferences(PREF_CONFIG, MODE_PRIVATE);
        boolean displayLigado = preferencias.getBoolean("pref_display_ligado" , true);

        //força o Layout ficar de acordo com a preferencia que o usuario escolheu ligado ou desligado
        if (displayLigado == true){
            changeStatusBarColor();
            txtLuzFundo.setText(getString(R.string.ligado));
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccentDark));
        }else if (displayLigado == false){
            txtLuzFundo.setText(getString(R.string.desligado));
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccentDarkWhite));
            changeStatusBarColorDark();
            lusFundo.setChecked(false);
        }



        lusFundo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor = preferencias.edit();
                if (isChecked)
                    try {
                        txtLuzFundo.setText(R.string.ligado);
                        Toast.makeText(getApplicationContext(), R.string.textoLigado, Toast.LENGTH_LONG).show();
                        toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccentDark));
                        changeStatusBarColor();
                        editor.putBoolean("pref_display_ligado" , true);
                        editor.commit();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }

                else
                    try {
                        txtLuzFundo.setText(R.string.desligado);
                        Toast.makeText(getApplicationContext(), R.string.textoDesligado, Toast.LENGTH_LONG).show();
                        toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccentDarkWhite));
                        changeStatusBarColorDark();
                        editor.putBoolean("pref_display_ligado" , false);
                        editor.commit();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }

            }
        });

        int pontuacao = preferencias.getInt("pref_pontuacao" , 0);
        if(pontuacao == 1){
            txtValorespecas.setText(getString(R.string.valoresPecas1));
        }else if(pontuacao == 2){
            txtValorespecas.setText(getString(R.string.valoresPecas2));
        }

        valoresPecas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Definicoes.this);

                LayoutInflater layoutInflater = getLayoutInflater();
                final View dialoglayout = layoutInflater.inflate(R.layout.dialog_definicoes, null);

                builder.setView(dialoglayout);

                builder.setIcon(getResources().getDrawable(R.drawable.ic_marcador));
                builder.setTitle(getString(R.string.valoresTabela));
                builder.setMessage(getString(R.string.textoDialogValoresPecas)+"\n"+getString(R.string.nomePecasFSQG));

                final RadioGroup radioGroup = (RadioGroup) dialoglayout.findViewById(R.id.rgConfigPontuacao);
                final RadioButton radio1 = (RadioButton) dialoglayout.findViewById(R.id.opcao1);
                final RadioButton radio2 = (RadioButton) dialoglayout.findViewById(R.id.opcao2);

                preferencias = getSharedPreferences(PREF_CONFIG, MODE_PRIVATE);
                int pontuacao = preferencias.getInt("pref_pontuacao" , 0);
                if(pontuacao == 1){
                    radioGroup.check(R.id.opcao1);
                    radio1.setTextColor(getResources().getColor(R.color.colorAccent));
                    radio1.setText(R.string.valoresPecas1Selecionado);
                }else if(pontuacao == 2){
                    radioGroup.check(R.id.opcao2);
                    radio2.setTextColor(getResources().getColor(R.color.colorAccent));
                    radio2.setText(getString(R.string.valoresPecas2Selecionado));
                }

                radio1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            radio1.setTextColor(getResources().getColor(R.color.colorAccent));
                            radio1.setText(getString(R.string.valoresPecas1Selecionado));
                        }else {
                            radio1.setTextColor(getResources().getColor(R.color.colorBlack));
                            radio1.setText(getString(R.string.valoresPecas1));
                        }
                    }
                });

                radio2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked){
                            radio2.setTextColor(getResources().getColor(R.color.colorAccent));
                            radio2.setText(getString(R.string.valoresPecas2Selecionado));
                        }else {
                            radio2.setTextColor(getResources().getColor(R.color.colorBlack));
                            radio2.setText(getString(R.string.valoresPecas2));
                        }
                    }
                });




                builder.setPositiveButton(getString(R.string.salvar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor = preferencias.edit();
                        if(radioGroup.getCheckedRadioButtonId() == R.id.opcao1) {
                            editor.putInt("pref_pontuacao", 1);
                            txtValorespecas.setText(getString(R.string.valoresPecas1));
                            Toast.makeText(getApplicationContext(), getString(R.string.pecasTrocadas)+getString(R.string.valoresPecas1), Toast.LENGTH_LONG).show();
                        }else if(radioGroup.getCheckedRadioButtonId() == R.id.opcao2){
                            editor.putInt("pref_pontuacao", 2);
                            txtValorespecas.setText(getString(R.string.valoresPecas2));
                            Toast.makeText(getApplicationContext(), getString(R.string.pecasTrocadas)+getString(R.string.valoresPecas2), Toast.LENGTH_LONG).show();
                        }
                        editor.commit();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                }
                });



                builder.show();

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            if (lusFundo.isChecked() == true){
                changeStatusBarColor();
            }else if (lusFundo.isChecked() == false){
                changeStatusBarColorDark();
            }
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorAccentDark));
        }
    }
    private void changeStatusBarColorDark() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorAccentDarkWhite));
        }
    }
}
