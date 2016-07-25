package com.darthorg.bozo.Update;

import android.app.Activity;
import android.view.MenuItem;

import com.darthorg.bozo.R;

/**
 * Created by wende on 24/07/2016.
 */
public class ListaGrupos {
    public static void atualizar(final Activity activity, final MenuItem mi){
        mi.setActionView(R.layout.progress_bar_menu);
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Todo:Implementar metodo atualizar
                        mi.setActionView(null);
                        mi.setIcon(R.drawable.ic_atualizar);

                    }
                });
            }
        }.start();

    }
}
