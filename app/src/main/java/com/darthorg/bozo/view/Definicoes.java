package com.darthorg.bozo.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.darthorg.bozo.R;
import com.darthorg.bozo.util.Util;

public class Definicoes extends AppCompatActivity {

    public static final String PREF_CONFIG = "CONFIG_GERAL";
    private SharedPreferences.Editor editor;
    private SharedPreferences preferencias;

    TextView txtValorespecas, txtLuzFundo;
    Switch swichtLuzFundo;
    ImageView copoView;


    SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            verificarTema(sharedPreferences);
        }
    };

    private void verificarTema(SharedPreferences sharedPreferences) {
        int tema = sharedPreferences.getInt("pref_tema", 0);
        if (tema == 0)
            copoView.setImageResource(R.drawable.img_copo);
        else
            copoView.setImageResource(tema);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definicoes);
        changeStatusBarColor();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_RS);
        setSupportActionBar(toolbar);

        txtLuzFundo = (TextView) findViewById(R.id.txtLuzFundo);
        swichtLuzFundo = (Switch) findViewById(R.id.swirchLuzFundo);
        txtValorespecas = (TextView) findViewById(R.id.txtValorespecas);

        //Visualizar o copo Atual
        findViewById(R.id.btnViewCopo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abre o alert
                AlertDialog.Builder builder = new AlertDialog.Builder(Definicoes.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                final View dialoglayout = layoutInflater.inflate(R.layout.dialog_tema_copo, null);
                builder.setView(dialoglayout);

                copoView = (ImageView) dialoglayout.findViewById(R.id.copoVeiw);


                builder.setTitle(getString(R.string.olhando_tema));
                builder.setIcon(R.drawable.ic_olhar);

                preferencias = getSharedPreferences(Definicoes.PREF_CONFIG, MODE_PRIVATE);
                preferencias.registerOnSharedPreferenceChangeListener(listener);
                verificarTema(preferencias);


                builder.setPositiveButton(getString(R.string.alterar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(Definicoes.this, Tema.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                        .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.show();

            }
        });

        preferencias = getSharedPreferences(PREF_CONFIG, MODE_PRIVATE);
        boolean displayLigado = preferencias.getBoolean("pref_display_ligado", true);

        //força o Layout ficar de acordo com a preferencia que o usuario escolheu ligado ou desligado
        if (displayLigado == true) {
            changeStatusBarColor();
            txtLuzFundo.setText(getString(R.string.ligado));
            swichtLuzFundo.setChecked(true);
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccentDark));
        } else if (displayLigado == false) {
            txtLuzFundo.setText(getString(R.string.desligado));
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccentDarkWhite));
            changeStatusBarColorDark();
            swichtLuzFundo.setChecked(false);
        }

        //Instruções
        findViewById(R.id.llInstrucoes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Definicoes.this, Instrucoes.class);
                startActivity(intent);
            }
        });

        //Tema copo
        findViewById(R.id.llTemaCopo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//            TemaUtils.mudarTema(CopoVirtual.this, getLayoutInflater());
                Intent intent = new Intent(Definicoes.this, Tema.class);
                startActivity(intent);
            }
        });

        //Sobre
        findViewById(R.id.llSobre).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Definicoes.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                View dialoglayout = layoutInflater.inflate(R.layout.dialog_sobre, null);

                TextView txtVersão = (TextView) dialoglayout.findViewById(R.id.txtVersao);

                try {
                    txtVersão.setText(getString(R.string.versao) + Util.getPackageInfo(getApplicationContext()).versionName);
                } catch (Exception e) {
                    txtVersão.setText(R.string.versao_padrao);
                }

                builder.setCancelable(true);
                builder.setView(dialoglayout);
                builder.show();
            }
        });

        swichtLuzFundo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor = preferencias.edit();
                if (isChecked) {
                    txtLuzFundo.setText(R.string.ligado);
                    Toast.makeText(getApplicationContext(), R.string.textoLigado, Toast.LENGTH_LONG).show();
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccentDark));
                    changeStatusBarColor();
                    editor.putBoolean("pref_display_ligado", true);
                    editor.commit();
                } else {
                    txtLuzFundo.setText(R.string.desligado);
                    Toast.makeText(getApplicationContext(), R.string.textoDesligado, Toast.LENGTH_LONG).show();
                    toolbar.setBackgroundColor(getResources().getColor(R.color.colorAccentDarkWhite));
                    changeStatusBarColorDark();
                    editor.putBoolean("pref_display_ligado", false);
                    editor.commit();
                }

            }
        });

        int pontuacao = preferencias.getInt("pref_pontuacao", 1);
        if (pontuacao == 1) {
            txtValorespecas.setText(getString(R.string.valoresPecas1));
        } else if (pontuacao == 2) {
            txtValorespecas.setText(getString(R.string.valoresPecas2));
        }

        //Valores peças
        findViewById(R.id.llValoresPecas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder builder = new AlertDialog.Builder(Definicoes.this);

                LayoutInflater layoutInflater = getLayoutInflater();
                final View dialoglayout = layoutInflater.inflate(R.layout.dialog_definicoes, null);

                builder.setView(dialoglayout);

                builder.setIcon(getResources().getDrawable(R.drawable.ic_marcador));
                builder.setTitle(getString(R.string.valoresTabela));
                builder.setMessage(getString(R.string.textoDialogValoresPecas) + "\n" + getString(R.string.nomePecasFSQG));

                final RadioGroup radioGroup = (RadioGroup) dialoglayout.findViewById(R.id.rgConfigPontuacao);
                final RadioButton radio1 = (RadioButton) dialoglayout.findViewById(R.id.opcao1);
                final RadioButton radio2 = (RadioButton) dialoglayout.findViewById(R.id.opcao2);


                int pontuacao = preferencias.getInt("pref_pontuacao", 1);
                if (pontuacao == 1) {
                    radioGroup.check(R.id.opcao1);
                    radio1.setTextColor(getResources().getColor(R.color.colorAccent));
                } else if (pontuacao == 2) {
                    radioGroup.check(R.id.opcao2);
                    radio2.setTextColor(getResources().getColor(R.color.colorAccent));
                }

                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (radioGroup.getCheckedRadioButtonId() == R.id.opcao1) {
                            radio1.setTextColor(getResources().getColor(R.color.colorAccent));
                            radio2.setTextColor(getResources().getColor(R.color.colorBlack));
                        } else {
                            radio2.setTextColor(getResources().getColor(R.color.colorAccent));
                            radio1.setTextColor(getResources().getColor(R.color.colorBlack));
                        }

                    }
                });

                builder.setPositiveButton(getString(R.string.salvar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor = preferencias.edit();
                        if (radioGroup.getCheckedRadioButtonId() == R.id.opcao1) {
                            editor.putInt("pref_pontuacao", 1);
                            txtValorespecas.setText(getString(R.string.valoresPecas1));
                            Toast.makeText(getApplicationContext(), getString(R.string.pecasTrocadas) + getString(R.string.valoresPecas1), Toast.LENGTH_LONG).show();
                        } else if (radioGroup.getCheckedRadioButtonId() == R.id.opcao2) {
                            editor.putInt("pref_pontuacao", 2);
                            txtValorespecas.setText(getString(R.string.valoresPecas2));
                            Toast.makeText(getApplicationContext(), getString(R.string.pecasTrocadas) + getString(R.string.valoresPecas2), Toast.LENGTH_LONG).show();
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


        //Feed Back
        findViewById(R.id.llFeedBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedback();
            }
        });

    }

    //Feed Back
    private void sendFeedback() {
        /* final Intent _Intent = new Intent(android.content.Intent.ACTION_SEND);
        _Intent.setType("text/html");
        _Intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ getString(R.string.mail_feedback_email) });
        _Intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.mail_feedback_subject));
        _Intent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.mail_feedback_message));
        startActivity(Intent.createChooser(_Intent, getString(R.string.title_send_feedback))); */


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, getString(R.string.mail_feedback_email));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_feedback_subject));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            if (swichtLuzFundo.isChecked() == true) {
                changeStatusBarColor();
            } else if (swichtLuzFundo.isChecked() == false) {
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
