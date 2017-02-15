package com.darthorg.bozo.view;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
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

    LinearLayout linearLayout, dadosEmbaixo,receberDados;
    RelativeLayout RLjogar, RLEmbaixo;
    ImageView copo,sombra;
    CardView caixaMenssagem;
    TextView menssagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copo_virtual);
        fullscreenTransparent();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_RS);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

//        final FloatingActionButton atualizar = (FloatingActionButton) findViewById(R.id.atualizar);
        final FloatingActionButton jogar = (FloatingActionButton) findViewById(R.id.jogarDados);
        final FloatingActionButton embaixo = (FloatingActionButton) findViewById(R.id.pedir_embaixo);

        receberDados = (LinearLayout) findViewById(R.id.recebeDados);
        caixaMenssagem = (CardView) findViewById(R.id.caixaMenssagem);
        menssagem = (TextView) findViewById(R.id.menssagem);
        RLjogar = (RelativeLayout) findViewById(R.id.rl_btnJogar);
        RLEmbaixo = (RelativeLayout) findViewById(R.id.rl_btnEmbaixo);

        copo = (ImageView) findViewById(R.id.copo);
        sombra = (ImageView) findViewById(R.id.sombra);

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
                menssagem.setText("\nCHACOALHE O CELULAR\n\nPara jogar os dados vire o celular de cabeça para baixo, caso queira 'EMBAIXO' clique na seta para baixo.\n");

            }
        });

        embaixo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Embaixo ATIVADO joque os dados",Toast.LENGTH_LONG).show();
            }
        });



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
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
                caixaMenssagem.setVisibility(View.GONE);
                receberDados.setVisibility(View.VISIBLE);
                RLjogar.setVisibility(View.VISIBLE);
                RLEmbaixo.setVisibility(View.GONE);
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
}
