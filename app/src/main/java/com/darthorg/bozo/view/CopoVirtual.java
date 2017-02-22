package com.darthorg.bozo.view;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.darthorg.bozo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CopoVirtual extends AppCompatActivity {

    SensorManager sensorManager;
    Sensor sensorEmAcao;

    LinearLayout llAreaPrincipal,llDadosEncima,llAreaInferiorReceberDados,llAreaInferiorCaixa;

    RelativeLayout mesa;

    ImageView copo,sombra;

    ImageButton btnCopo;

    Button btnAtualizar,btnAtivarCopo,btnEspiarEncima;

    FloatingActionButton btnPedirEmbaixo;

    TextView menssagem1,menssagem2,menssagem3,menssagem4;

    Toolbar toolbar;

    private int chances = 4;
    private int pedirEmBaixo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copo_virtual);
        fullscreenTransparent();
        IDs();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_sair));

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorEmAcao = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        findViewById(R.id.dado1).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.dado2).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.dado3).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.dado4).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.dado5).setOnTouchListener(new MyTouchListener());

        findViewById(R.id.lugar1).setOnDragListener(new MyOnDragListener(1));
        findViewById(R.id.lugar2).setOnDragListener(new MyOnDragListener(2));
        findViewById(R.id.lugar3).setOnDragListener(new MyOnDragListener(3));
        findViewById(R.id.lugar4).setOnDragListener(new MyOnDragListener(4));
        findViewById(R.id.lugar5).setOnDragListener(new MyOnDragListener(5));
        findViewById(R.id.llAreaPrincipal).setOnDragListener(new MyOnDragListener(6));

        btnAtivarCopo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chances();
                sensorManager.registerListener(new MySensorEvent(llAreaPrincipal), sensorEmAcao, SensorManager.SENSOR_DELAY_GAME);
                menssagem2.setVisibility(View.VISIBLE);
                btnAtivarCopo.setVisibility(View.GONE);
                btnPedirEmbaixo.setVisibility(View.VISIBLE);
                menssagem3.setVisibility(View.VISIBLE);
                menssagem1.setVisibility(View.GONE);



            }
        });

        btnCopo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorManager.registerListener(new MySensorEvent(llAreaPrincipal), sensorEmAcao, SensorManager.SENSOR_DELAY_GAME);
                copo.setVisibility(View.VISIBLE);
                sombra.setVisibility(View.VISIBLE);
                menssagem2.setVisibility(View.VISIBLE);
                llAreaPrincipal.setVisibility(View.GONE);
                llAreaInferiorCaixa.setVisibility(View.GONE);
                btnCopo.setVisibility(View.GONE);
                mesa.setBackgroundColor(getResources().getColor(R.color.colorWhiteFundo));
                llDadosEncima.setVisibility(View.GONE);
                menssagem4.setVisibility(View.GONE);
                btnPedirEmbaixo.setVisibility(View.VISIBLE);
                menssagem3.setVisibility(View.VISIBLE);
                pedirEmBaixo = 0;
                btnPedirEmbaixo.setImageDrawable(getResources().getDrawable(R.drawable.ic_embaixo));
                menssagem3.setText("Pedir Embaixo");
                btnEspiarEncima.setVisibility(View.GONE);
            }
        });

        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opcaoAtualizar();
            }
        });

        btnPedirEmbaixo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedirEmbaixo();
            }
        });

        btnEspiarEncima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEspiarEncima.setVisibility(View.GONE);
                llDadosEncima.setVisibility(View.VISIBLE);
            }
        });
    }

    public void IDs() {

        //Toobar
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Aria Principal
        llAreaPrincipal = (LinearLayout) findViewById(R.id.llAreaPrincipal);

        //Area DadosEncima
        llDadosEncima = (LinearLayout) findViewById(R.id.llDadosEncima);

        //Area Inferior Receber Dados
        llAreaInferiorReceberDados = (LinearLayout) findViewById(R.id.llAreaInferiorReceberDados);
        llAreaInferiorCaixa = (LinearLayout) findViewById(R.id.llAreaInferiorCaixa);

        //Fundo
        mesa = (RelativeLayout) findViewById(R.id.mesa);

        copo = (ImageView) findViewById(R.id.copo);
        sombra = (ImageView) findViewById(R.id.sombra);
        btnCopo = (ImageButton) findViewById(R.id.btnCopo);
        menssagem1 = (TextView) findViewById(R.id.menssagem1);
        menssagem2 = (TextView) findViewById(R.id.menssagem2);
        menssagem3 = (TextView) findViewById(R.id.menssagem3);
        menssagem4 = (TextView) findViewById(R.id.menssagem4);

        btnAtualizar = (Button) findViewById(R.id.btnAtualizar);
        btnAtivarCopo = (Button) findViewById(R.id.btnAtivarCopo);
        btnPedirEmbaixo = (FloatingActionButton) findViewById(R.id.btnPedirEmbaixo);
        btnEspiarEncima = (Button) findViewById(R.id.btnEspiarEncima);



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
        }else if (id == R.id.action_atualizar){
            opcaoAtualizar();
        }
        else if (id == R.id.action_tema_copo){

            AlertDialog.Builder builderRemoverJogador = new AlertDialog.Builder(CopoVirtual.this);

            LayoutInflater layoutInflater = getLayoutInflater();
            final View dialoglayout = layoutInflater.inflate(R.layout.dialog_tema_copo, null);

            builderRemoverJogador.setView(dialoglayout);

            ImageButton btnTemaNormal = (ImageButton) dialoglayout.findViewById(R.id.btnTemaNormal);
            ImageButton btnTemaRed = (ImageButton) dialoglayout.findViewById(R.id.btnTemaRed);
            ImageButton btnTemaPurple = (ImageButton) dialoglayout.findViewById(R.id.btnTemaPurple);
            ImageButton btnTemaPink = (ImageButton) dialoglayout.findViewById(R.id.btnTemaPink);
            ImageButton btnTemaBlue = (ImageButton) dialoglayout.findViewById(R.id.btnTemaBlue);
            ImageButton btnTemaGreen = (ImageButton) dialoglayout.findViewById(R.id.btnTemaGreen);
            ImageButton btnTemaOrange = (ImageButton) dialoglayout.findViewById(R.id.btnTemaOrange);
            ImageButton btnTemaDarth = (ImageButton) dialoglayout.findViewById(R.id.btnTemaDarth);
            ImageButton btnTemaFerro = (ImageButton) dialoglayout.findViewById(R.id.btnTemaFerro);
            ImageButton btnTemaStarWars = (ImageButton) dialoglayout.findViewById(R.id.btnTemaStarWars);
            ImageButton btnTemaFlor = (ImageButton) dialoglayout.findViewById(R.id.btnTemaFlor);
            ImageButton btnTemaFrozen = (ImageButton) dialoglayout.findViewById(R.id.btnTemaFrozen);
            ImageButton btnTemaGato = (ImageButton) dialoglayout.findViewById(R.id.btnTemaGato);

            builderRemoverJogador.setTitle("Tema do copo");
            builderRemoverJogador.setIcon(getResources().getDrawable(R.drawable.ic_tema));
            builderRemoverJogador.setMessage("Escolha o tema para seu copo");
            final AlertDialog dialog = builderRemoverJogador.create();


            btnTemaNormal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    copo.setImageDrawable(getResources().getDrawable(R.drawable.img_copo));
                    btnCopo.setImageDrawable(getResources().getDrawable(R.drawable.img_copo));
                    dialog.dismiss();
                }
            });
            btnTemaRed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    copo.setImageDrawable(getResources().getDrawable(R.drawable.copo_red));
                    btnCopo.setImageDrawable(getResources().getDrawable(R.drawable.copo_red));
                    dialog.dismiss();
                }
            });
            btnTemaPurple.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    copo.setImageDrawable(getResources().getDrawable(R.drawable.copo_purple));
                    btnCopo.setImageDrawable(getResources().getDrawable(R.drawable.copo_purple));
                    dialog.dismiss();
                }
            });
            btnTemaPink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    copo.setImageDrawable(getResources().getDrawable(R.drawable.copo_pink));
                    btnCopo.setImageDrawable(getResources().getDrawable(R.drawable.copo_pink));
                    dialog.dismiss();
                }
            });
            btnTemaBlue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    copo.setImageDrawable(getResources().getDrawable(R.drawable.copo_blue));
                    btnCopo.setImageDrawable(getResources().getDrawable(R.drawable.copo_blue));
                    dialog.dismiss();
                }
            });
            btnTemaGreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    copo.setImageDrawable(getResources().getDrawable(R.drawable.copo_green));
                    btnCopo.setImageDrawable(getResources().getDrawable(R.drawable.copo_green));
                    dialog.dismiss();
                }
            });
            btnTemaOrange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    copo.setImageDrawable(getResources().getDrawable(R.drawable.copo_orange));
                    btnCopo.setImageDrawable(getResources().getDrawable(R.drawable.copo_orange));
                    dialog.dismiss();
                }
            });
            btnTemaDarth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    copo.setImageDrawable(getResources().getDrawable(R.drawable.copo_darth));
                    btnCopo.setImageDrawable(getResources().getDrawable(R.drawable.copo_darth));
                    dialog.dismiss();
                }
            });
            btnTemaFerro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    copo.setImageDrawable(getResources().getDrawable(R.drawable.copo_ferro));
                    btnCopo.setImageDrawable(getResources().getDrawable(R.drawable.copo_ferro));
                    dialog.dismiss();
                }
            });
            btnTemaStarWars.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    copo.setImageDrawable(getResources().getDrawable(R.drawable.copo_star_wars));
                    btnCopo.setImageDrawable(getResources().getDrawable(R.drawable.copo_star_wars));
                    dialog.dismiss();
                }
            });
            btnTemaFlor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    copo.setImageDrawable(getResources().getDrawable(R.drawable.copo_flor));
                    btnCopo.setImageDrawable(getResources().getDrawable(R.drawable.copo_flor));
                    dialog.dismiss();
                }
            });
            btnTemaFrozen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    copo.setImageDrawable(getResources().getDrawable(R.drawable.copo_frozen));
                    btnCopo.setImageDrawable(getResources().getDrawable(R.drawable.copo_frozen));
                    dialog.dismiss();
                }
            });
            btnTemaGato.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    copo.setImageDrawable(getResources().getDrawable(R.drawable.copo_gato));
                    btnCopo.setImageDrawable(getResources().getDrawable(R.drawable.copo_gato));
                    dialog.dismiss();
                }
            });

            dialog.show();
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

    public List<ImageButton> embaralharDados(ViewGroup layoutContainerPrincipal) {

        LinearLayout containerPrincipal = (LinearLayout) layoutContainerPrincipal;
        List<ImageButton> dadosParaEmbaralhar = new ArrayList<>();


        //Recupera os ImageViews
        for (int i = 0; i < containerPrincipal.getChildCount(); i++) {
            dadosParaEmbaralhar.add((ImageButton) containerPrincipal.getChildAt(i));
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

    private void gerarImageViewsDadosEmcima(LinearLayout llDadosEmbaralhados) {

        List<ImageButton> ImageButtons = new ArrayList<>();
        llDadosEncima.removeAllViews();

        for (int i = 0; i < llDadosEmbaralhados.getChildCount(); i++) {
            int tag = (int) llDadosEmbaralhados.getChildAt(i).getTag();
            ImageButton img = new ImageButton(CopoVirtual.this);
            img.setTag(dadoEmbaixo(tag));
            llDadosEncima.addView(img);
            ImageButtons.add(img);
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


    class MySensorEvent implements SensorEventListener {

        LinearLayout llDadosAEmbaralhar;

        public MySensorEvent(LinearLayout llDadosAEmbaralhar) {
            this.llDadosAEmbaralhar = llDadosAEmbaralhar;
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            embaralharDados(llDadosAEmbaralhar);

            copo.setRotation((int) event.values[0] * -5);

            if (event.values[1] < -8 && (event.values[0] < 2 && event.values[0] > -2)) {

                sensorManager.unregisterListener(this);

                //Aparecer na Area principal
                llAreaPrincipal.setVisibility(View.VISIBLE);
                llAreaInferiorCaixa.setVisibility(View.VISIBLE);
                btnCopo.setVisibility(View.VISIBLE);
                menssagem4.setVisibility(View.VISIBLE);

                //Esconder na Area Principal
                copo.setVisibility(View.GONE);
                sombra.setVisibility(View.GONE);
                menssagem2.setVisibility(View.GONE);
                btnPedirEmbaixo.setVisibility(View.GONE);
                menssagem3.setVisibility(View.GONE);

                switch (pedirEmBaixo){
                    case 1:
                        menssagem4.setText("Valores Embaixo visivel");
                        btnEspiarEncima.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        menssagem4.setText("Valores Encima visivel");
                        btnEspiarEncima.setVisibility(View.GONE);
                        break;
                }


                String jogada = verificarJogada();
                if (jogada != null) {
                    menssagem4.setText(jogada);
                    menssagem4.setTextSize(20);
                    menssagem4.setTextColor(getResources().getColor(R.color.colorAccent));
                    btnAtualizar.setVisibility(View.VISIBLE);
                    btnCopo.setVisibility(View.GONE);
                }

                chances();


            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    public void pedirEmbaixo(){
        pedirEmBaixo++;
        switch (pedirEmBaixo){
            case 1:
                btnPedirEmbaixo.setImageDrawable(getResources().getDrawable(R.drawable.ic_confirmar_welcome));
                menssagem3.setText("Embaixo selecionado");
                break;
            case 2: case 0:
                btnPedirEmbaixo.setImageDrawable(getResources().getDrawable(R.drawable.ic_embaixo));
                menssagem3.setText("Pedir Embaixo");
                pedirEmBaixo = 0;
                break;
        }
    }

    public void chances(){
        chances--;
        switch (chances) {
            case 3:
                    menssagem2.setText("Para jogar os dados vire o copo"+"\n\n"+"Você tem " + chances + " jogadas");
                break;
            case 2:
                    menssagem2.setText("Para jogar os dados vire o copo"+"\n\n"+"Você tem mais " + chances + " jogadas");
                break;
            case 1:
                    menssagem2.setText("Para jogar os dados vire o copo"+"\n\n"+"Você tem só mais " + chances + " jogada");
                break;
            case 0:
                btnCopo.setVisibility(View.GONE);
                btnAtualizar.setVisibility(View.VISIBLE);
                break;
        }
    }

    private String verificarJogada() {

        List<Integer> valoresDados = new ArrayList<>();

        //pega os valores dos dados que estão na area principal
        for (int i = 0; i < llAreaPrincipal.getChildCount(); i++) {
            valoresDados.add((int) llAreaPrincipal.getChildAt(i).getTag());
        }

        //pega os valores dos dados que estão na area secundária
        for (int i = 0; i < llAreaInferiorReceberDados.getChildCount(); i++) {
            LinearLayout linearLayoutPosicoes = (LinearLayout) llAreaInferiorReceberDados.getChildAt(i);
            if (linearLayoutPosicoes.getChildCount() != 0) {
                valoresDados.add((int) linearLayoutPosicoes.getChildAt(0).getTag());
            }

        }

        return obterJogada(valoresDados);
    }

    private String obterJogada(List<Integer> valoresDados) {

        Collections.sort(valoresDados);

        //Seguida
        List<Integer> seguidaI = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> seguidaII = Arrays.asList(2, 3, 4, 5, 6);
        if (valoresDados.equals(seguidaI) || valoresDados.equals(seguidaII)) {
            // Seguida
            if (chances == 2)
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
                if (chances == 2)
                    return "Full de Boca";
                else
                    return "Full de " + countPar + " e " + countTrio;

            } else if (numQuadrada != 0) {
                //Quadrada
                if (chances == 2)
                    return "Quadrada de Boca ";
                else
                    return "Quadrada de " + numQuadrada;
            } else if (numGeneral != 0) {
                //General
                if (chances == 2)
                    return "General de Boca";
                else
                    return "General de " + numGeneral;
            }
        }
        return null;
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

        private int num;

        public MyOnDragListener(int num) {
            this.num = num;
        }

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
                    Log.i("DRAG AND DROP", num + "-" + "ACTION_DRAG_ENTERED");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.i("DRAG AND DROP", num + "-" + "ACTION_DRAG_EXITED");
                    break;

                case DragEvent.ACTION_DRAG_LOCATION:
                    Log.i("DRAG AND DROP", num + "-" + "ACTION_DRAG_LOCATION");
                    break;

                case DragEvent.ACTION_DROP:
                    Log.i("DRAG AND DROP", num + "-" + "ACTION_DROP");

                    LinearLayout container = (LinearLayout) v; // Recebe
                    ViewGroup owner = (ViewGroup) draggedView.getParent(); // Linear de quem esta vindo

                    if (owner != container) {
                        if (container.getId() == R.id.llAreaPrincipal || container.getChildAt(0) == null) {
                            owner.removeView(draggedView);
                            container.addView(draggedView);
                            draggedView.setVisibility(View.VISIBLE);

                        } else {
                            View filho = container.getChildAt(0);
                            container.removeView(filho);
                            owner.removeView(draggedView);

                            container.addView(draggedView);
                            draggedView.setVisibility(View.VISIBLE);
                            owner.addView(filho);
                            filho.setVisibility(View.VISIBLE);
                        }
                    }
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.i("DRAG AND DROP", num + "-" + "ACTION_DRAG_ENDED");
                    draggedView.setVisibility(View.VISIBLE);
                    gerarImageViewsDadosEmcima(llAreaPrincipal);
                    break;

            }
            return false;
        }

    }

    public void opcaoAtualizar() {
        switch (chances){
            case 4: case 3: case 0:
                finish();
                Intent intent = new Intent(CopoVirtual.this, CopoVirtual.class);
                startActivity(intent);
                break;
            case 2: case 1:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Atualizar");
                builder.setMessage("Você não terminou sua jogada, deseja atualizar assim mesmo?")
                        .setPositiveButton("Atualizar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                Intent intent = new Intent(CopoVirtual.this, CopoVirtual.class);
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
                break;

        }
    }

    public void opcaoSair() {
        switch (chances){
            case 4 : case 3:
                finish();
                break;
            case 2 : case 1 : case 0 :
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.sair));
                builder.setMessage("Você não terminou sua jogada, deseja sair assim mesmo?")
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

    @Override
    public void onBackPressed() {
        opcaoSair();
    }
}
