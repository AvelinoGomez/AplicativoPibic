package com.myscript.atk.sltw.sample.Entidades;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Avelino on 10/03/2018.
 */

public class Historico {
    private String uid;
    private String pontuacao;
    private String palavraErro1;
    private String localErro1;
   // private Bitmap erro1;
    private String palavraErro2;
    private String localErro2;
   // private Bitmap erro2;
    private String palavraErro3;
    private String localErro3;
    private String data;
   // private Bitmap erro3;




    public Historico() {
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapHistorico = new HashMap<>();

        hashMapHistorico.put("uid",getUid());
        hashMapHistorico.put("pontuacao",getPontuacao());
        hashMapHistorico.put("palavraErro1",getPalavraErro1());
        hashMapHistorico.put("palavraErro2",getPalavraErro2());
        hashMapHistorico.put("palavraErro3",getPalavraErro3());
        hashMapHistorico.put("localErro1",getLocalErro1());
        hashMapHistorico.put("localErro2",getLocalErro2());
        hashMapHistorico.put("localErro3",getLocalErro3());
        hashMapHistorico.put("data",getData());

        return hashMapHistorico;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLocalErro1() {
        return localErro1;
    }

    public void setLocalErro1(String localErro1) {
        this.localErro1 = localErro1;
    }

    public String getLocalErro2() {
        return localErro2;
    }

    public void setLocalErro2(String localErro2) {
        this.localErro2 = localErro2;
    }

    public String getLocalErro3() {
        return localErro3;
    }

    public void setLocalErro3(String localErro3) {
        this.localErro3 = localErro3;
    }

    public String getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(String pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getPalavraErro1() {
        return palavraErro1;
    }

    public void setPalavraErro1(String palavraErro1) {
        this.palavraErro1 = palavraErro1;
    }

    /*public Bitmap getErro1() {
        return erro1;
    }

    public void setErro1(Bitmap erro1) {
        this.erro1 = erro1;
    }*/

    public String getPalavraErro2() {
        return palavraErro2;
    }

    public void setPalavraErro2(String palavraErro2) {
        this.palavraErro2 = palavraErro2;
    }

    /*public Bitmap getErro2() {
        return erro2;
    }

    public void setErro2(Bitmap erro2) {
        this.erro2 = erro2;
    }*/

    public String getPalavraErro3() {
        return palavraErro3;
    }

    public void setPalavraErro3(String PalavraErro3) {
        this.palavraErro3 = PalavraErro3;
    }

    /*public Bitmap getErro3() {
        return erro3;
    }

    public void setErro3(Bitmap erro3) {
        this.erro3 = erro3;
    }*/

    public String getUid(){ return uid; }

    public void setUid(String uid){ this.uid = uid; }

}
