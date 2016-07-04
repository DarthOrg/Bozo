package com.darthorg.bozo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    private final int SPLASH = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



    new Handler().postDelayed(new Runnable() {
        public void run() {
            startActivity(new Intent(Splash.this,Inicio.class));
            finish();
        }
    }, SPLASH);

    }
}
