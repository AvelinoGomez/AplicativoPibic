package com.myscript.atk.sltw.sample.CRUD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Avelino on 18/04/2018.
 */

public class CreateUsuario extends SQLiteOpenHelper {

    private static final String NOME_DB = "MEU_DB";
    private static final int VERSAO_DB = 1;
    private static final String TABELA_USUARIO = "TABELA_USUARIO";
    private static final String PATH_DB = "/data/com.myscript.atk.sltw.sample/database/MEU_DB";

    private Context mContext;
    private SQLiteDatabase db;

    public CreateUsuario(Context context) {
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

    public boolean createTable(){
        openDB();
        String createTable = "CREATE TABLE IF NOT EXISTS " + TABELA_USUARIO+ " ("+"ID TEXT, "+"NOME TEXT,"+
                "SOBRENOME TEXT,"+"DIANASC TEXT,"+"MESNASC TEXT,"+"ANONASC TEXT,"+"BACKGROUND TEXT,"+"ADM TEXT,"+
                "PONTUACAO TEXT,"+"EMAIL TEXT,"+"JOGOS TEXT,"+"MENSAGEM TEXT,"+"SENHA TEXT,"+"CORLAPIS TEXT"+")";
        try{
            db.execSQL(createTable);
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

