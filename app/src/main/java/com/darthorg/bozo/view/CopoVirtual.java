package com.darthorg.bozo.view;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.darthorg.bozo.R;

public class CopoVirtual extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copo_virtual);
        fullscreenTransparent();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_RS);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Button pedir_embaixo = (Button) findViewById(R.id.pedir_embaixo);
        final Button parar = (Button) findViewById(R.id.parar);
        final Button jogar = (Button) findViewById(R.id.jogar);

        jogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jogar.setVisibility(View.GONE);
                parar.setVisibility(View.VISIBLE);
                pedir_embaixo.setVisibility(View.VISIBLE);
            }
        });
        parar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jogar.setVisibility(View.VISIBLE);
                parar.setVisibility(View.GONE);
                pedir_embaixo.setVisibility(View.GONE);
            }
        });
        pedir_embaixo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jogar.setVisibility(View.VISIBLE);
                parar.setVisibility(View.GONE);
                pedir_embaixo.setVisibility(View.GONE);
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

    public void fullscreenTransparent(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorBlackTransparente25));
        }
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }
}
