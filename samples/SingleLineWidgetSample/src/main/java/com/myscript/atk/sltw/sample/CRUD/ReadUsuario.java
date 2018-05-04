package com.myscript.atk.sltw.sample.CRUD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myscript.atk.sltw.sample.Entidades.Usuarios;

import java.util.ArrayList;

/**
 * Created by Avelino on 18/04/2018.
 */

public class ReadUsuario extends SQLiteOpenHelper {

    private static final String NOME_DB = "MEU_DB";
    private static final int VERSAO_DB = 1;
    private static final String TABELA_USUARIO = "TABELA_USUARIO";
    private static final String PATH_DB = "/data/com.myscript.atk.sltw.sample/database/MEU_DB";

    private Context mContext;
    private SQLiteDatabase db;

    public ReadUsuario(Context context) {
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

    public ArrayList<Usuarios> getUsuarios(){
        openDB();
        ArrayList<Usuarios> usuariosArray = new ArrayList<>();
        String getPessoas = "SELECT * FROM " + TABELA_USUARIO;

        try{
            Cursor c = db.rawQuery(getPessoas,null);

            if(c.moveToFirst()){
                do{
                    Usuarios usuario = new Usuarios();
                    usuario.setId(c.getString(0));
                    usuario.setAdm(c.getString(7));
                    usuario.setNome(c.getString(1));
                    usuario.setSobrenome(c.getString(2));
                    usuario.setEmail(c.getString(9));
                    usuario.setDiaNascimento(c.getString(3));
                    usuario.setMesNascimento(c.getString(4));
                    usuario.setAnoNascimento(c.getString(5));
                    usuario.setPontuacao(c.getString(8));
                    usuario.setJogos(c.getString(10));
                    usuario.setBackGround(c.getString(6));
                    usuario.setMensagem(c.getString(11));
                    usuario.setSenha(c.getString(12));
                    usuario.setCorLapis(c.getString(13));

                    usuariosArray.add(usuario);

                }while(c.moveToNext());
                c.close();
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            db.close();
        }
        return usuariosArray;
    }

    public Usuarios getUsuario(String UID){
        openDB();
        String getPessoas = "SELECT * FROM " + TABELA_USUARIO;

        Usuarios usuario = null;

        try{
            Cursor c = db.rawQuery(getPessoas,null);

            if(c.moveToFirst()){
                do{
                    if(c.getString(0) == UID){ //SE FOR O USUARIO REALMENTE//

                        usuario = new Usuarios();
                        usuario.setId(c.getString(0));
                        usuario.setAdm(c.getString(7));
                        usuario.setNome(c.getString(1));
                        usuario.setSobrenome(c.getString(2));
                        usuario.setEmail(c.getString(9));
                        usuario.setDiaNascimento(c.getString(3));
                        usuario.setMesNascimento(c.getString(4));
                        usuario.setAnoNascimento(c.getString(5));
                        usuario.setPontuacao(c.getString(8));
                        usuario.setJogos(c.getString(10));
                        usuario.setBackGround(c.getString(6));
                        usuario.setMensagem(c.getString(11));
                        usuario.setSenha(c.getString(12));

                        return usuario;
                    }

                }while(c.moveToNext());
                c.close();
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            db.close();
        }
        return usuario;
    }

    public void openDB(){

        if(!db.isOpen()){
            db = mContext.openOrCreateDatabase(PATH_DB,SQLiteDatabase.OPEN_READWRITE,null);
        }
    }
}

