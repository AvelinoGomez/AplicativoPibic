package com.myscript.atk.sltw.sample.Entidades;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Avelino on 10/03/2018.
 */

public class HistoricoErro {
    private String uid;
    private Drawable erro1;
    private Drawable erro2;
    private Drawable erro3;

    public HistoricoErro(String uid) {
        this.uid = uid;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapHistorico = new HashMap<>();

        hashMapHistorico.put("usuario",getClass());
        hashMapHistorico.put("erro1",getErro1());
        hashMapHistorico.put("erro2",getErro2());
        hashMapHistorico.put("erro3",getErro3());

        return hashMapHistorico;
    }
    public String getUid(){ return uid; }

    public void setUid(String uid){ this.uid = uid; }

    public Drawable getErro1() {
        return erro1;
    }

    public void setErro1(Drawable erro1) {
        this.erro1 = erro1;
    }

    public Drawable getErro2() {
        return erro2;
    }

    public void setErro2(Drawable erro2) {
        this.erro2 = erro2;
    }

    public Drawable getErro3() {
        return erro3;
    }

    public void setErro3(Drawable erro3) {
        this.erro3 = erro3;
    }
}
