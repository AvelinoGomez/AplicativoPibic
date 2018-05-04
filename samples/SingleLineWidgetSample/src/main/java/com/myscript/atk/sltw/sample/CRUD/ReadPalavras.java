package com.myscript.atk.sltw.sample.CRUD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myscript.atk.sltw.sample.Entidades.Palavra;
import com.myscript.atk.sltw.sample.Entidades.Usuarios;

import java.util.ArrayList;

/**
 * Created by Avelino on 18/04/2018.
 */

public class ReadPalavras extends SQLiteOpenHelper {

    private static final String NOME_DB = "MINHAS_PALAVRAS";
    private static final int VERSAO_DB = 1;
    private static final String TABELA_PALAVRAS = "TABELA_PALAVRAS";
    private static final String PATH_DB = "/data/data/com.myscript.atk.sltw.sample/databases/MINHAS_PALAVRAS";

    private Context mContext;
    private SQLiteDatabase db;

    public ReadPalavras(Context context) {
        super(context, NOME_DB, null, VERSAO_DB);
        this.mContext = context;
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Palavra> getPalavras(){
        openDB();
        ArrayList<Palavra> palavraArray = new ArrayList<>();
        String getPalavras = "SELECT * FROM " + TABELA_PALAVRAS;

        try{
            Cursor c = db.rawQuery(getPalavras,null);

            if(c.moveToFirst()){
                do{
                    Palavra palavra = new Palavra();
                    palavra.setPalavra(c.getString(0));
                    palavra.setDificuldade(c.getString(1));
                    palavra.setUidCriador(c.getString(2));
                    palavra.setNomeCriador(c.getString(3));
                    palavraArray.add(palavra);

                }while(c.moveToNext());
                c.close();
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            db.close();
        }
        return palavraArray;
    }

    public Palavra getPalavra(String palavra){
        openDB();
        String getPalavra = "SELECT * FROM " + TABELA_PALAVRAS + " WHERE "+TABELA_PALAVRAS+".PALAVRA = "+palavra;

        Palavra palavra1 = null;

        try{
            Cursor c = db.rawQuery(getPalavra,null);

            if(c.moveToFirst()) {
                do {


                    palavra1 = new Palavra();
                    palavra1.setPalavra(c.getString(0));
                    palavra1.setDificuldade(c.getString(1));
                    palavra1.setUidCriador(c.getString(2));
                    palavra1.setNomeCriador(c.getString(3));

                    return palavra1;

                } while (c.moveToNext());

            }c.close();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            db.close();
        }
        return palavra1;
    }

    public void openDB(){

        if(!db.isOpen()){
            db = mContext.openOrCreateDatabase(PATH_DB,SQLiteDatabase.OPEN_READWRITE,null);
        }
    }
}

