package com.darthorg.bozo.model;

/**
 * Created by gusta on 26/02/2017.
 */

public class TemaCopo {
    private int icone;
    private int backgroundIcone;
    private int imagemCopo;

    public TemaCopo(int icone, int backgroundIcone, int imagemCopo) {
        this.icone = icone;
        this.backgroundIcone = backgroundIcone;
        this.imagemCopo = imagemCopo;
    }

    public TemaCopo(int icone, int imagemCopo) {
        this.icone = icone;
        this.imagemCopo = imagemCopo;
    }

    public int getIcone() {
        return icone;
    }

    public void setIcone(int icone) {
        this.icone = icone;
    }

    public int getBackgroundIcone() {
        return backgroundIcone;
    }

    public void setBackgroundIcone(int backgroundIcone) {
        this.backgroundIcone = backgroundIcone;
    }

    public int getImagemCopo() {
        return imagemCopo;
    }

    public void setImagemCopo(int imagemCopo) {
        this.imagemCopo = imagemCopo;
    }

}
