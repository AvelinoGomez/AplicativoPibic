package com.myscript.atk.sltw.sample.DAO;

import android.provider.ContactsContract;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Avelino on 05/02/2018.
 */

public class ConfiguracaoFirebase {

    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth autenticacao;

    public static DatabaseReference getFirebase(){
        if(referenciaFirebase==null){
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFirebase;
    }

    public static FirebaseAuth getFirebaseAutenticacao(){
        if(autenticacao==null){
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }
}
