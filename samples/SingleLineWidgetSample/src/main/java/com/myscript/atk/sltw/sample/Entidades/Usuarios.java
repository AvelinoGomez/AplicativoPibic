package com.myscript.atk.sltw.sample.Entidades;

import android.provider.ContactsContract;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.myscript.atk.sltw.sample.DAO.ConfiguracaoFirebase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Avelino on 05/02/2018.
 */

public class Usuarios implements Serializable{

    private String id;
    private String adm;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private String diaNascimento;
    private String mesNascimento;
    private String anoNascimento;
    private String pontuacao;
    private String jogos;

    private String backGround;
    private String avatar;

    private String mensagem;

    private String corLapis;
    private String corBackGround;

    private String flagFundo;


    public Usuarios() {
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapUsuario = new HashMap<>();

        hashMapUsuario.put("id",getId());
        hashMapUsuario.put("adm",getAdm());
        hashMapUsuario.put("nome",getNome());
        hashMapUsuario.put("sobrenome",getSobrenome());
        hashMapUsuario.put("email",getEmail());
        hashMapUsuario.put("senha",getSenha());
        hashMapUsuario.put("diaNascimento",getDiaNascimento());
        hashMapUsuario.put("mesNascimento",getMesNascimento());
        hashMapUsuario.put("anoNascimento",getAnoNascimento());
        hashMapUsuario.put("pontuacao",getPontuacao());
        hashMapUsuario.put("jogos",getJogos());
        hashMapUsuario.put("background",getBackGround());
        hashMapUsuario.put("avatar",getAvatar());
        hashMapUsuario.put("mensagem",getMensagem());
        hashMapUsuario.put("corLapis",getCorLapis());
        hashMapUsuario.put("corBackground",getCorBackGround());
        hashMapUsuario.put("flagFundo",getFlagFundo());

        return hashMapUsuario;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCorBackGround() {
        return corBackGround;
    }

    public void setCorBackGround(String corBackGround) {
        this.corBackGround = corBackGround;
    }

    public String getFlagFundo() {
        return flagFundo;
    }

    public void setFlagFundo(String flagFundo) {
        this.flagFundo = flagFundo;
    }

    public String getCorLapis() {
        return corLapis;
    }

    public void setCorLapis(String corLapis) {
        this.corLapis = corLapis;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getBackGround() {
        return backGround;
    }

    public void setBackGround(String backGround) {
        this.backGround = backGround;
    }

    public String getJogos() {
        return jogos;
    }

    public void setJogos(String jogos) {
        this.jogos = jogos;
    }

    public String getAdm() {
        return adm;
    }

    public void setAdm(String adm) {
        this.adm = adm;
    }

    public String getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(String pontuacao) {
        this.pontuacao = pontuacao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDiaNascimento() {
        return diaNascimento;
    }

    public void setDiaNascimento(String diaNascimento) {
        this.diaNascimento = diaNascimento;
    }

    public String getMesNascimento() {
        return mesNascimento;
    }

    public void setMesNascimento(String mesNascimento) {
        this.mesNascimento = mesNascimento;
    }

    public String getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(String anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

}
