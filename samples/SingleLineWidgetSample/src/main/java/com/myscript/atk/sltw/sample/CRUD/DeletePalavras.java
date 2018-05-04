package com.myscript.atk.sltw.sample.CRUD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myscript.atk.sltw.sample.Entidades.Palavra;
import com.myscript.atk.sltw.sample.Entidades.Usuarios;

/**
 * Created by Avelino on 18/04/2018.
 */

public class DeletePalavras extends SQLiteOpenHelper {

    private static final String NOME_DB = "MINHAS_PALAVRAS";
    private static final int VERSAO_DB = 1;
    private static final String TABELA_PALAVRAS = "TABELA_PALAVRAS";
    private static final String PATH_DB = "/data/data/com.myscript.atk.sltw.sample/databases/MINHAS_PALAVRAS";

    private Context mContext;
    private SQLiteDatabase db;

    public DeletePalavras(Context context) {
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

    public boolean deleteTable(){
        openDB();
        String deleteTable = "DROP TABLE IF EXISTS "+TABELA_PALAVRAS;
        try{
            db.execSQL(deleteTable);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            db.close();
        }
    }

    public boolean deletePalavras(Palavra palavra){
        openDB();

        String deletePalavra = "PALAVRA = '"+palavra.getPalavra()+"'";

        try{
            db.delete(TABELA_PALAVRAS,deletePalavra,null);
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

