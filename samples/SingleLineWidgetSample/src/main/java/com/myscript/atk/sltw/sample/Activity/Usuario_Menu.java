package com.myscript.atk.sltw.sample.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.myscript.atk.sltw.sample.CRUD.DeleteUsuario;
import com.myscript.atk.sltw.sample.CRUD.ReadPalavras;
import com.myscript.atk.sltw.sample.CRUD.ReadUsuario;
import com.myscript.atk.sltw.sample.DAO.ConfiguracaoFirebase;
import com.myscript.atk.sltw.sample.Entidades.Palavra;
import com.myscript.atk.sltw.sample.Entidades.Usuarios;
import com.myscript.atk.sltw.sample.R;

import java.util.ArrayList;


public class Usuario_Menu extends AppCompatActivity {

    DatabaseReference firebase;
    FirebaseAuth usuarioFirebase;
    private ArrayList<String> listaPalavras = new ArrayList<>();
    Context context;
    ArrayList<Usuarios> usuarios;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telamenu);

        view = (View)findViewById(R.id.layout_telamenu);

        context = getApplicationContext();

        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();

        Intent intent = getIntent();
        Bundle dados = intent.getExtras();
        final String uid = dados.getString("uid").toString();
        String nomeUsuario;

        //Button Layout/
        Button botaojogar = (Button)findViewById(R.id.buttonJogar);
        Button botaoconfig = (Button)findViewById(R.id.buttonConfig);
        Button btnSair = (Button)findViewById(R.id.btnSair);
        Button btnLoja = (Button)findViewById(R.id.buttonLoja);

        //EditText Layout/
        final EditText edtNomeMenu = (EditText)findViewById(R.id.edtNomeMenu);
        final EditText edtPontuacao = (EditText)findViewById(R.id.edtPontGeral);


        ReadUsuario r = new ReadUsuario(getApplicationContext());
        usuarios = r.getUsuarios();
        Usuarios usuario = usuarios.get(0);

        final ReadPalavras rp = new ReadPalavras(getApplicationContext());

        firebase = ConfiguracaoFirebase.getFirebase().child("Usuarios").child(uid);
        firebase.setValue(usuario);

        edtNomeMenu.setText(usuarios.get(0).getNome());
        edtPontuacao.setText(usuarios.get(0).getPontuacao());

        btnSair.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View view){
            deslogarUsuario();
            Toast.makeText(Usuario_Menu.this, "Você se desconectou!", Toast.LENGTH_SHORT).show();
        }
        });

        botaojogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Palavra> palavras = rp.getPalavras();
                if(palavras.size()==0){
                    Toast.makeText(context, "Não existem palavras cadastradas!", Toast.LENGTH_SHORT).show();
                }else
                IrParaTelaJogar(uid);
            }
        });

        botaoconfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IrParaTelaConfig(uid);
            }
        });

        btnLoja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IrParaTelaLoja(uid);
            }
        });

        //BACKGROUND ESCOLHIDO//
        if(usuario.getBackGround().equals("0")){
            view.setBackgroundResource(R.drawable.background1);
        }else if(usuario.getBackGround().equals("1")){
            view.setBackgroundResource(R.drawable.background2);
        }else{
            view.setBackgroundResource(R.drawable.background3);
        }
        //

    }



    void IrParaTelaJogar(String uid){
        Bundle bundle = new Bundle();
        bundle.putString("uid",uid);
        Intent intent = new Intent(this, Usuario_Jogando.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void deslogarUsuario() {
        usuarioFirebase.signOut();

        //DESLOGANDO//
        DeleteUsuario d = new DeleteUsuario(context);
        d.deleteTable();
        //////////////

        Intent intent = new Intent(Usuario_Menu.this, Login_activity.class);
        startActivity(intent);
        finish();
    }

   void IrParaTelaConfig(String uid){
       Bundle bundle = new Bundle();
       bundle.putString("uid",uid);
        Intent intent = new Intent(this, Usuario_Config.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    void IrParaTelaLoja(String uid){
        Bundle bundle = new Bundle();
        bundle.putString("uid",uid);
        Intent intent = new Intent(this, Usuario_Loja.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }



}
