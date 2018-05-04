package com.myscript.atk.sltw.sample.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.myscript.atk.sltw.sample.CRUD.DeleteUsuario;
import com.myscript.atk.sltw.sample.CRUD.ReadUsuario;
import com.myscript.atk.sltw.sample.DAO.ConfiguracaoFirebase;
import com.myscript.atk.sltw.sample.Entidades.Usuarios;
import com.myscript.atk.sltw.sample.R;

import java.util.ArrayList;

public class MenuAdm extends AppCompatActivity {

    DatabaseReference firebase;
    FirebaseAuth usuarioFirebase;

    Context context;

    String uid;
    Usuarios usuario;
    ArrayList<Usuarios> usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_adm);

        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();
        context = getApplication();

        Intent intent = getIntent();
        Bundle dados = intent.getExtras();
        uid = dados.getString("uid").toString();
        String nomeUsuario;

        final TextView ama_txtNome = (TextView) findViewById(R.id.ama_edtNomeMenu);
        Button ama_btnSair = (Button) findViewById(R.id.ama_btnSair);
        Button ama_btnListar = (Button) findViewById(R.id.ama_btnListarAlunos);
        Button ama_btnConfig = (Button) findViewById(R.id.ama_btnConfiguracoes);


        ReadUsuario r = new ReadUsuario(getApplicationContext());
        usuarios = r.getUsuarios();
        usuario = usuarios.get(0);


        ama_txtNome.setText(usuario.getNome() + " " + usuario.getSobrenome());


        ama_btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaTelaListar();
            }
        });

        ama_btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaTelaConfig(uid);
            }
        });
        ama_btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deslogarUsuario();
            }
        });


    }


    private void deslogarUsuario() {
        //DESLOGANDO//
        usuarioFirebase.signOut();

        DeleteUsuario d = new DeleteUsuario(context);
        d.deleteTable();
        //////////////

        //////////////

        Intent intent = new Intent(MenuAdm.this, login_activity.class);
        startActivity(intent);
        finish();
    }

    private void irParaTelaListar(){
        Intent intent = new Intent(MenuAdm.this, PrincipalAdm.class);
        startActivity(intent);
    }

    private void irParaTelaConfig(String uid){
            Bundle bundle = new Bundle();
            bundle.putString("uid",uid);
            Intent intentirParaTelaConfig = new Intent( MenuAdm.this,ConfigAdm.class);
            intentirParaTelaConfig.putExtras(bundle);
            startActivity(intentirParaTelaConfig);


    }
}
