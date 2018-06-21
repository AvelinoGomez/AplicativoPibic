package com.myscript.atk.sltw.sample.CRUD;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myscript.atk.sltw.sample.Entidades.Usuarios;

/**
 * Created by Avelino on 18/04/2018.
 */

public class UpdateUsuario extends SQLiteOpenHelper {

    private static final String NOME_DB = "MEU_DB";
    private static final int VERSAO_DB = 1;
    private static final String TABELA_USUARIO = "TABELA_USUARIO";
    private static final String PATH_DB = "/data/com.myscript.atk.sltw.sample/database/MEU_DB";

    private Context mContext;
    private SQLiteDatabase db;

    public UpdateUsuario(Context context) {
        super(context, NOME_DB, null, VERSAO_DB);
        this.mContext = context;
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertUsuario(Usuarios usuario){
        openDB();
        try{
            ContentValues cv = new ContentValues();
            cv.put("ID",usuario.getId());
            cv.put("NOME",usuario.getNome());
            cv.put("SOBRENOME",usuario.getSobrenome());
            cv.put("DIANASC",usuario.getDiaNascimento());
            cv.put("MESNASC",usuario.getMesNascimento());
            cv.put("ANONASC",usuario.getAnoNascimento());
            cv.put("BACKGROUND",usuario.getBackGround());
            cv.put("ADM",usuario.getAdm());
            cv.put("PONTUACAO",usuario.getPontuacao());
            cv.put("EMAIL",usuario.getEmail());
            cv.put("JOGOS",usuario.getJogos());
            cv.put("MENSAGEM",usuario.getMensagem());
            cv.put("SENHA",usuario.getSenha());
            cv.put("CORLAPIS",usuario.getCorLapis());
            cv.put("AVATAR",usuario.getAvatar());
            cv.put("CORBACKGROUND",usuario.getCorBackGround());
            cv.put("FLAGBACKGROUND",usuario.getFlagFundo());

            db.insert(TABELA_USUARIO,null,cv);
            return true;



        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }

    public boolean updateUsuario(Usuarios usuario){
        openDB();
        try{
            String where = "NOME = '"+usuario.getNome() + "'";

            ContentValues cv = new ContentValues();
            cv.put("ID",usuario.getId());
            cv.put("NOME",usuario.getNome());
            cv.put("SOBRENOME",usuario.getSobrenome());
            cv.put("DIANASC",usuario.getDiaNascimento());
            cv.put("MESNASC",usuario.getMesNascimento());
            cv.put("ANONASC",usuario.getAnoNascimento());
            cv.put("BACKGROUND",usuario.getBackGround());
            cv.put("ADM",usuario.getAdm());
            cv.put("PONTUACAO",usuario.getPontuacao());
            cv.put("EMAIL",usuario.getEmail());
            cv.put("JOGOS",usuario.getJogos());
            cv.put("MENSAGEM",usuario.getMensagem());
            cv.put("SENHA",usuario.getSenha());
            cv.put("CORLAPIS",usuario.getCorLapis());
            cv.put("AVATAR",usuario.getAvatar());
            cv.put("CORBACKGROUND",usuario.getCorBackGround());
            cv.put("FLAGBACKGROUND",usuario.getFlagFundo());

            db.update(TABELA_USUARIO,cv,where,null);
            return true;


        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }

    public void openDB(){

        if(!db.isOpen()){
            db = mContext.openOrCreateDatabase(PATH_DB,SQLiteDatabase.OPEN_READWRITE,null);
        }
    }
}
