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
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darthorg.bozo.R;

import java.util.ArrayList;
import java.util.List;

public class CopoVirtual extends AppCompatActivity {

    SensorManager sensorManager;
    Sensor sensorEmAcao;

    ImageButton dadoEncima1,dadoEncima2,dadoEncima3,dadoEncima4,dadoEncima5;

    LinearLayout linearLayout, receberDados,dadosEncima,RLOlharEncima;
    RelativeLayout RLjogar, RLEmbaixo,RLAtualizar;
    ImageView copo,sombra;
    FloatingActionButton jogar,embaixo,atualizar,olhar;
    Toolbar toolbar;
    TextView txtMenssagem;
    private int chances = 3;
    private int btnEmbaixo = 0;
    private int btnolhar = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copo_virtual);
        fullscreenTransparent();
        IDs();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_sair_white));

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



        jogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout = (LinearLayout) findViewById(R.id.llAreaPrincipal);

                sensorManager.registerListener(new MySensorEvent(linearLayout), sensorEmAcao, SensorManager.SENSOR_DELAY_GAME);
                linearLayout.setVisibility(View.GONE);
                copo.setVisibility(View.VISIBLE);
                sombra.setVisibility(View.VISIBLE);
                RLjogar.setVisibility(View.GONE);
                RLEmbaixo.setVisibility(View.VISIBLE);
                RLOlharEncima.setVisibility(View.GONE);
                dadosEncima.setVisibility(View.GONE);
                txtMenssagem.setText("Chacoalhe o celular.");
                btnEmbaixo = 0;


            }
        });

        embaixo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEmbaixo ++;
                switch (btnEmbaixo){
                    case 1:
                        txtMenssagem.setText("Embaixo ATIVO");
                        break;
                    case 2:
                        txtMenssagem.setText("Embaixo DESATIVADO");
                        btnEmbaixo = 0;
                        break;
                }
            }
        });

        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(CopoVirtual.this,CopoVirtual.class);
                startActivity(intent);
            }
        });

        olhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnolhar ++;
                switch (btnolhar){
                    case 1:
                        dadosEncima.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        dadosEncima.setVisibility(View.GONE);
                        btnolhar = 0;
                        break;
                }
            }
        });



    }

    public void IDs(){
        //Toobar
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //FAB
        jogar = (FloatingActionButton) findViewById(R.id.jogarDados);
        embaixo = (FloatingActionButton) findViewById(R.id.pedir_embaixo);
        atualizar = (FloatingActionButton) findViewById(R.id.atualizar);
        olhar = (FloatingActionButton) findViewById(R.id.olhar);

        //LinearLayout
        receberDados = (LinearLayout) findViewById(R.id.recebeDados);
        dadosEncima = (LinearLayout) findViewById(R.id.llDadosEncima);
        RLOlharEncima = (LinearLayout) findViewById(R.id.rl_btnOlhar);

        //RelativeLayout
        RLjogar = (RelativeLayout) findViewById(R.id.rl_btnJogar);
        RLEmbaixo = (RelativeLayout) findViewById(R.id.rl_btnEmbaixo);
        RLAtualizar = (RelativeLayout) findViewById(R.id.rl_btnAtualizar);

        //ImageView
        copo = (ImageView) findViewById(R.id.copo);
        sombra = (ImageView) findViewById(R.id.sombra);

        //Texto
        txtMenssagem =(TextView) findViewById(R.id.txtMenssagem);

        //Dados Encima
        dadoEncima1 = (ImageButton) findViewById(R.id.dadoEncima1);
        dadoEncima2 = (ImageButton) findViewById(R.id.dadoEncima2);
        dadoEncima3 = (ImageButton) findViewById(R.id.dadoEncima3);
        dadoEncima4 = (ImageButton) findViewById(R.id.dadoEncima4);
        dadoEncima5 = (ImageButton) findViewById(R.id.dadoEncima5);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            opçãoSair();
        }

        return super.onOptionsItemSelected(item);
    }

    public void fullscreenTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorAccentDarkWhite2));
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

    class MySensorEvent implements SensorEventListener {

        LinearLayout linearLayout;

        public MySensorEvent(LinearLayout linearLayout) {
            this.linearLayout = linearLayout;
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            embaralharDados(linearLayout);

            copo.setRotation((int)event.values[0] * 5);

            if (event.values[1] < -8 && (event.values[0] < 2 && event.values[0] > -2)) {
                sensorManager.unregisterListener(this);
                linearLayout.setVisibility(View.VISIBLE);
                copo.setVisibility(View.GONE);
                sombra.setVisibility(View.GONE);
                receberDados.setVisibility(View.VISIBLE);
                RLjogar.setVisibility(View.VISIBLE);
                RLEmbaixo.setVisibility(View.GONE);

                chances --;
                switch (chances){
                    case 2:
                        txtMenssagem.setText("Você tem mais "+chances+" jogadas");
                        break;
                    case 1:
                        txtMenssagem.setText("Você tem só mais "+chances+" jogada");
                        break;
                    case 0:
                        txtMenssagem.setText("SEGUIDA DE BOCA");
                        RLjogar.setVisibility(View.GONE);
                        RLAtualizar.setVisibility(View.VISIBLE);
                        break;
                }

                switch (btnEmbaixo){
                    case 1:
                        RLOlharEncima.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        RLOlharEncima.setVisibility(View.GONE);
                        btnEmbaixo = 0;
                        break;
                }


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
                    break;

            }
            return false;
        }

    }
    public void AlertaSair(){
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
    }

    public void opçãoSair(){
        if (chances == 3){
            finish();
        }else if (chances  == 2){
            AlertaSair();
        }else if (chances  == 1){
            AlertaSair();
        }else if (chances  == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Lembrete");
            builder.setMessage("Antes de sair sua jogada foi\n"+txtMenssagem.getText().toString())
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
        }
    }

    @Override
    public void onBackPressed() {
        opçãoSair();
    }
}
