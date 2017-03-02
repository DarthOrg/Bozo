package com.darthorg.bozo.view;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.darthorg.bozo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CopoVirtual extends AppCompatActivity {

    SensorManager sensorManager;
    Sensor sensorEmAcao;

    ImageView ivCopo;

    LinearLayout llAreaPrincipal, llAreaInferiorReceberDados,
            llAreaDadosCima, llAreaInferiorContainer, llPedirEmbaixo, llAreaJogo, llAcoes;

    Button btnInstrucaoJogar, btnJogarDados, btnAtualizar;
    FloatingActionButton btnPedirEmbaixo;
    ImageButton btnJogarDenovo;

    FrameLayout frameContainerCopo;

    SharedPreferences prefs;
    SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            verificarTema(sharedPreferences);
        }
    };

    MySensorEvent eventListener;

    private int chances = 3;
    private boolean pedirEmBaixo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copo_virtual);

        fullscreenTransparent();
        IDs();

        prefs = getSharedPreferences(Definicoes.PREF_CONFIG, MODE_PRIVATE);
        prefs.registerOnSharedPreferenceChangeListener(listener);
        verificarTema(prefs);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorEmAcao = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        findViewById(R.id.dado1).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.dado2).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.dado3).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.dado4).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.dado5).setOnTouchListener(new MyTouchListener());

        findViewById(R.id.lugar1).setOnDragListener(new MyOnDragListener());
        findViewById(R.id.lugar2).setOnDragListener(new MyOnDragListener());
        findViewById(R.id.lugar3).setOnDragListener(new MyOnDragListener());
        findViewById(R.id.lugar4).setOnDragListener(new MyOnDragListener());
        findViewById(R.id.lugar5).setOnDragListener(new MyOnDragListener());

        // Registra o DragListener nos 2 LinearLayouts
        for (int i = 0; i < llAreaPrincipal.getChildCount(); i++) {
            llAreaPrincipal.getChildAt(i).setOnDragListener(new MyOnDragListener());
        }

        ivCopo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                eventListener = new MySensorEvent(llAreaPrincipal);
                sensorManager.registerListener(eventListener, sensorEmAcao, SensorManager.SENSOR_DELAY_GAME);

                llPedirEmbaixo.setVisibility(View.VISIBLE);
                btnInstrucaoJogar.setVisibility(View.GONE);
                btnJogarDados.setVisibility(View.VISIBLE);
            }
        });

        btnJogarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jogarDados(eventListener);
            }
        });

        btnJogarDenovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llAreaJogo.setVisibility(View.GONE);
                llPedirEmbaixo.setVisibility(View.GONE);
                frameContainerCopo.setVisibility(View.VISIBLE);
                btnInstrucaoJogar.setVisibility(View.VISIBLE);
                btnJogarDados.setVisibility(View.GONE);
                ivCopo.setRotation(0);
            }
        });

        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 11) {
                    recreate();
                } else {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }

            }
        });

        btnPedirEmbaixo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pedirEmBaixo = !pedirEmBaixo;
            }
        });

    }

    public void IDs() {

        // ------- JOGO ----------------//
        // Area Jogo
        llAreaJogo = (LinearLayout) findViewById(R.id.llAreaJogo);

        //Area Principal
        llAreaPrincipal = (LinearLayout) findViewById(R.id.llAreaPrincipal);
        llPedirEmbaixo = (LinearLayout) findViewById(R.id.llPedirEmbaixo);

        //Area Inferior Receber Dados
        llAreaInferiorReceberDados = (LinearLayout) findViewById(R.id.llAreaInferiorReceberDados);
        llAreaInferiorContainer = (LinearLayout) findViewById(R.id.llAreaInferiorCaixa);

        //Area DadosEncima
        llAreaDadosCima = (LinearLayout) findViewById(R.id.llareaEmCima);

        //Acoes
        llAcoes = (LinearLayout) findViewById(R.id.llAcoes);
        btnJogarDenovo = (ImageButton) findViewById(R.id.btnJogarDenovo);
        btnAtualizar = (Button) findViewById(R.id.btnAtualizar);
        btnPedirEmbaixo = (FloatingActionButton) findViewById(R.id.btnPedirEmbaixo);


        // ------- COPO ----------------//
        //FrameLayoutCopo
        frameContainerCopo = (FrameLayout) findViewById(R.id.frameContainerCopo);

        //ImageView copo
        ivCopo = (ImageView) findViewById(R.id.ivCopo);
        btnInstrucaoJogar = (Button) findViewById(R.id.btnInstrucaoJogar);
        btnJogarDados = (Button) findViewById(R.id.btnJogarDados);


    }

    private void verificarTema(SharedPreferences sharedPreferences) {
        int tema = sharedPreferences.getInt("pref_tema", 0);
        if (tema == 0)
            ivCopo.setImageResource(R.drawable.img_copo);
        else
            ivCopo.setImageResource(tema);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_copo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            opcaoSair();
        } else if (id == R.id.action_atualizar) {
            opcaoAtualizar();
        } else if (id == R.id.action_tema_copo) {
            //TemaUtils.mudarTema(CopoVirtual.this, getLayoutInflater());
        }

        return super.onOptionsItemSelected(item);
    }

    public void fullscreenTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorBlack));
        }
    }

    public List<ImageButton> embaralharDados(LinearLayout containerMaster) {

        LinearLayout containerPrincipal = (LinearLayout) containerMaster;
        List<ImageButton> dadosParaEmbaralhar = new ArrayList<>();


        //Recupera os ImageViews
        for (int i = 0; i < containerPrincipal.getChildCount(); i++) {
            LinearLayout area = (LinearLayout) containerPrincipal.getChildAt(i);
            for (int j = 0; j < area.getChildCount(); j++) {
                dadosParaEmbaralhar.add((ImageButton) area.getChildAt(j));
            }
        }

        //Para cada imageView
        for (ImageButton img : dadosParaEmbaralhar) {
            int random = (int) (Math.random() * 6) + 1;
            img.setTag(random);
        }

        trocarImagens(dadosParaEmbaralhar);

        return dadosParaEmbaralhar;
    }

    private void trocarImagens(List<ImageButton> imageButtonsComTag) {

        for (ImageButton imgButton : imageButtonsComTag) {
            int tag = (int) imgButton.getTag();

            switch (tag) {
                case 1:
                    imgButton.setImageResource(R.drawable.ic_dado_um);
                    break;
                case 2:
                    imgButton.setImageResource(R.drawable.ic_dado_dois);
                    break;
                case 3:
                    imgButton.setImageResource(R.drawable.ic_dado_tres);
                    break;
                case 4:
                    imgButton.setImageResource(R.drawable.ic_dado_quatro);
                    break;
                case 5:
                    imgButton.setImageResource(R.drawable.ic_dado_cinco);
                    break;
                case 6:
                    imgButton.setImageResource(R.drawable.ic_dado_seis);
                    break;
            }
        }
    }

    private void gerarImageViewsDadosEmcima() {

        List<ImageButton> ImageButtons = new ArrayList<>();
        llAreaDadosCima.removeAllViews();


        for (int i = 0; i < llAreaPrincipal.getChildCount(); i++) {
            LinearLayout ll = (LinearLayout) llAreaPrincipal.getChildAt(i);
            for (int j = 0; j < ll.getChildCount(); j++) {
                int tag = (int) ll.getChildAt(j).getTag();
                ImageButton img = (ImageButton) getLayoutInflater().inflate(R.layout.img_dados_cima, llAreaDadosCima, false);
                img.setTag(dadoEmbaixo(tag));
                llAreaDadosCima.addView(img);
                ImageButtons.add(img);
            }
        }


        trocarImagens(ImageButtons);
    }

    private int dadoEmbaixo(int dado) {
        int dadoEmbaixo = 0;

        switch (dado) {
            case 1:
                dadoEmbaixo = 6;
                break;
            case 2:
                dadoEmbaixo = 5;
                break;
            case 3:
                dadoEmbaixo = 4;
                break;
            case 4:
                dadoEmbaixo = 3;
                break;
            case 5:
                dadoEmbaixo = 2;
                break;
            case 6:
                dadoEmbaixo = 1;
                break;
        }

        return dadoEmbaixo;
    }

    public void jogarDados(SensorEventListener listener) {
        sensorManager.unregisterListener(listener); //desabilita o sensor
        frameContainerCopo.setVisibility(View.GONE); //esconde o copo
        llAreaJogo.setVisibility(View.VISIBLE); //mostra os dados
        llAcoes.setVisibility(View.VISIBLE);
        chances--;

        int filhos = llAreaPrincipal.getChildCount();

        if (chances == 0) {
            btnJogarDenovo.setVisibility(View.GONE);
        }

        String jogada = verificarJogada();
        TextView txtJogada = (TextView) findViewById(R.id.txtEspecial);
        if (jogada != null) {
            txtJogada.setText(jogada);
            txtJogada.setVisibility(View.VISIBLE);
        } else {
            txtJogada.setVisibility(View.GONE);
        }

        Button btnEspiarCima = (Button) findViewById(R.id.btnEspiarEmCima);
        final LinearLayout containerAreaEmCima = (LinearLayout) findViewById(R.id.containerAreaEmCima);
        if (pedirEmBaixo) {
            gerarImageViewsDadosEmcima();
            btnEspiarCima.setVisibility(View.VISIBLE);
            btnEspiarCima.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    containerAreaEmCima.setVisibility(View.VISIBLE);
                }
            });
        } else {
            btnEspiarCima.setVisibility(View.GONE);
            containerAreaEmCima.setVisibility(View.GONE);
        }

        pedirEmBaixo = false;
    }

    private String verificarJogada() {

        List<Integer> valoresDados = new ArrayList<>();

        //pega os valores dos dados que estão na area principal
        for (int i = 0; i < llAreaPrincipal.getChildCount(); i++) {
            LinearLayout ll = (LinearLayout) llAreaPrincipal.getChildAt(i);
            for (int j = 0; j < ll.getChildCount(); j++) {
                valoresDados.add((Integer) ll.getChildAt(j).getTag());
            }
        }

        if (valoresDados.size() != 5) {
            //pega os valores dos dados que estão na area secundária
            for (int i = 0; i < llAreaInferiorReceberDados.getChildCount(); i++) {
                LinearLayout linearLayoutPosicoes = (LinearLayout) llAreaInferiorReceberDados.getChildAt(i);
                if (linearLayoutPosicoes.getChildCount() != 0) {
                    valoresDados.add((Integer) linearLayoutPosicoes.getChildAt(0).getTag());
                }
            }
            return obterJogada(valoresDados, false);
        }

        return obterJogada(valoresDados, true);
    }

    private String obterJogada(List<Integer> valoresDados, boolean deBoca) {

        Collections.sort(valoresDados);

        //Seguida
        List<Integer> seguidaI = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> seguidaII = Arrays.asList(2, 3, 4, 5, 6);
        if (valoresDados.equals(seguidaI) || valoresDados.equals(seguidaII)) {
            // Seguida
            if (deBoca)
                return "Seguida de Boca";
            else
                return "Seguida";
        }

        //Demais jogadas
        int countPar = 0;
        int countTrio = 0;
        int numQuadrada = 0;
        int numGeneral = 0;

        for (int i = 0; i < valoresDados.size(); i++) {

            int num = Collections.frequency(valoresDados, valoresDados.get(i));

            if (num == 2)
                countPar = valoresDados.get(i);
            if (num == 3)
                countTrio = valoresDados.get(i);
            if (num == 4)
                numQuadrada = valoresDados.get(i);
            if (num == 5)
                numGeneral = valoresDados.get(i);


            if (countPar != 0 && countTrio != 0) {
                //Full
                if (deBoca)
                    return "Full de Boca";
                else
                    return "Full de " + countPar + " e " + countTrio;

            } else if (numQuadrada != 0) {
                //Quadrada
                if (deBoca)
                    return "Quadrada de Boca ";
                else
                    return "Quadrada de " + numQuadrada;
            } else if (numGeneral != 0) {
                //General
                if (deBoca)
                    return "General de Boca";
                else
                    return "General de " + numGeneral;
            }
        }
        return null;
    }

    public void organizarDados() {

        // organizar dados
        LinearLayout areaDadosCima = (LinearLayout) llAreaPrincipal.getChildAt(0);
        LinearLayout areaDadosBaixo = (LinearLayout) llAreaPrincipal.getChildAt(1);
        View aux;

        // organizar
        if (areaDadosCima.getChildCount() == 4) {
            aux = areaDadosCima.getChildAt(areaDadosCima.getChildCount() - 1);
            View aux2 = areaDadosCima.getChildAt(areaDadosCima.getChildCount() - 2);
            areaDadosCima.removeView(aux);
            areaDadosCima.removeView(aux2);
            areaDadosBaixo.addView(aux);
            areaDadosBaixo.addView(aux2);
        } else if (areaDadosCima.getChildCount() == 3) {
            aux = areaDadosCima.getChildAt(areaDadosCima.getChildCount() - 1);
            areaDadosCima.removeView(aux);
            areaDadosBaixo.addView(aux);
        }
        if (areaDadosBaixo.getChildCount() == 4) {
            aux = areaDadosBaixo.getChildAt(areaDadosBaixo.getChildCount() - 1);
            View aux2 = areaDadosCima.getChildAt(areaDadosBaixo.getChildCount() - 2);
            areaDadosBaixo.removeView(aux);
            areaDadosBaixo.removeView(aux2);
            areaDadosCima.addView(aux);
            areaDadosCima.addView(aux2);
        } else if (areaDadosBaixo.getChildCount() == 3) {
            aux = areaDadosBaixo.getChildAt(areaDadosBaixo.getChildCount() - 1);
            areaDadosBaixo.removeView(aux);
            areaDadosCima.addView(aux);
        }

    }

    public void opcaoAtualizar() {
        switch (chances) {
            case 0:
                finish();
                Intent intent = new Intent(CopoVirtual.this, CopoVirtual.class);
                startActivity(intent);
                break;
            default:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Reiniciar");
                builder.setMessage("Você ainda tem " + chances + " chances , deseja reiniciar assim mesmo?")
                        .setPositiveButton(getString(R.string.sair), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
                break;
        }
    }

    public void opcaoSair() {

        switch (chances) {
            case 0:
                finish();
                break;
            default:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.sair));
                builder.setMessage("Você ainda tem " + chances + " chances , deseja sair assim mesmo?")
                        .setPositiveButton(getString(R.string.sair), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
                break;
        }
    }

    class MySensorEvent implements SensorEventListener {

        LinearLayout llDadosAEmbaralhar;

        public MySensorEvent(LinearLayout llDadosAEmbaralhar) {
            this.llDadosAEmbaralhar = llDadosAEmbaralhar;
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            embaralharDados(llDadosAEmbaralhar);

            ivCopo.setRotation((int) event.values[0] * -5);

            if (event.values[1] < -5 && (event.values[0] < 6 && event.values[0] > -6)) {
                jogarDados(this);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    class MyTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ClipData data = ClipData.newPlainText("simple_text", "text");
            View.DragShadowBuilder sb = new View.DragShadowBuilder(v);
            v.startDrag(data, sb, v, 0);
            return true;

        }
    }

    class MyOnDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            View draggedView = (View) event.getLocalState();

            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        draggedView.setVisibility(View.INVISIBLE);
                        return true;
                    }
                    return false;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //Log.i("DRAG AND DROP", num + "-" + "ACTION_DRAG_ENTERED");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //Log.i("DRAG AND DROP", num + "-" + "ACTION_DRAG_EXITED");
                    break;

                case DragEvent.ACTION_DRAG_LOCATION:
                    //Log.i("DRAG AND DROP", num + "-" + "ACTION_DRAG_LOCATION");
                    break;

                case DragEvent.ACTION_DROP:
                    //Log.i("DRAG AND DROP", num + "-" + "ACTION_DROP");

                    LinearLayout container = (LinearLayout) v; // Recebe
                    ViewGroup owner = (ViewGroup) draggedView.getParent(); // Linear de quem esta vindo

                    if (owner != container) {
                        if (container.getChildAt(0) == null && container != llAreaPrincipal.getChildAt(0) && container != llAreaPrincipal.getChildAt(1)) {
                            // Adiciona novo
                            owner.removeView(draggedView);
                            container.addView(draggedView);
                            draggedView.setVisibility(View.VISIBLE);

                        } else if (container == llAreaPrincipal.getChildAt(0) || container == llAreaPrincipal.getChildAt(1)) {

                            owner.removeView(draggedView);
                            container.addView(draggedView);
                            draggedView.setVisibility(View.VISIBLE);

                        } else {
                            //Troca
                            View filho = container.getChildAt(0);
                            container.removeView(filho);
                            owner.removeView(draggedView);

                            container.addView(draggedView);
                            draggedView.setVisibility(View.VISIBLE);
                            owner.addView(filho);
                            filho.setVisibility(View.VISIBLE);
                        }
                        organizarDados();
                    }
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    // Log.i("DRAG AND DROP", num + "-" + "ACTION_DRAG_ENDED");
                    draggedView.setVisibility(View.VISIBLE);
                    gerarImageViewsDadosEmcima();
                    break;

            }

            return false;
        }

    }


    @Override
    public void onBackPressed() {
        opcaoSair();
    }
}