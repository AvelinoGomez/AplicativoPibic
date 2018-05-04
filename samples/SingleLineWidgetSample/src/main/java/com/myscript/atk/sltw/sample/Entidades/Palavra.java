package com.myscript.atk.sltw.sample.Entidades;

/**
 * Created by Avelino on 15/03/2018.
 */

public class Palavra {

    private String palavra;
    private String dificuldade;
    private String uidCriador;
    private String nomeCriador;



    public Palavra() {
        this.palavra = palavra;
        dificuldade = "0";
        this.uidCriador = uidCriador;
    }

    public String getNomeCriador() {
        return nomeCriador;
    }

    public void setNomeCriador(String nomeCriador) {
        this.nomeCriador = nomeCriador;
    }

    public String getUidCriador() {
        return uidCriador;
    }

    public void setUidCriador(String uidCriador) {
        this.uidCriador = uidCriador;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public String getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(String dificuldade) {
        this.dificuldade = dificuldade;
    }
}
