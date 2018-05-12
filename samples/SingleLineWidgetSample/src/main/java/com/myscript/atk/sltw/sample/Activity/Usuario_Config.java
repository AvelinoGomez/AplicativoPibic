package com.myscript.atk.sltw.sample.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.myscript.atk.sltw.sample.CRUD.ReadUsuario;
import com.myscript.atk.sltw.sample.DAO.ConfiguracaoFirebase;
import com.myscript.atk.sltw.sample.Entidades.Palavra;
import com.myscript.atk.sltw.sample.Entidades.Usuarios;
import com.myscript.atk.sltw.sample.R;

import java.util.ArrayList;
/*Tela configuração de Usuarios*/

public class Usuario_Config extends AppCompatActivity {

    DatabaseReference firebase;
    FirebaseAuth usuarioFirebase;
    Boolean retorno;
    View view;
    ArrayList<Usuarios> usuarios;
    String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telaconfig);
        Button btnGoToADD = (Button)findViewById(R.id.tc_btnGoToADD);
        Button tc_paletaCores = (Button)findViewById(R.id.tc_paletaCores);
        view = (View)findViewById(R.id.layout_telaconfig);

        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();

        Intent intent = getIntent();
        Bundle dados = intent.getExtras();
        uid = dados.getString("uid").toString();
        final EditText edtNomeConf = (EditText)findViewById(R.id.edtNomeConf);
        final EditText edtPontuacaoConf = (EditText)findViewById(R.id.edtPontGeralConf);

        /*firebase = ConfiguracaoFirebase.getFirebase().child("Usuarios").child(uid);

        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuarios usuario = dataSnapshot.getValue(Usuarios.class);
                edtNomeConf.setText(usuario.getNome()+" "+usuario.getSobrenome());
                edtPontuacaoConf.setText(usuario.getPontuacao());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Intent intent = new Intent(Usuario_Config.this,Login_activity.class);
                startActivity(intent);
            }
        });*/

        //RESGATANDO USUARIO//
        ReadUsuario r = new ReadUsuario(getApplicationContext());
        usuarios = r.getUsuarios();
        Usuarios usuarioLogado = usuarios.get(0);
        edtNomeConf.setText(usuarioLogado.getNome()+" "+usuarioLogado.getSobrenome());
        edtPontuacaoConf.setText(usuarioLogado.getPontuacao());
        //RESGATANDO USUARIO//

        //BACKGROUND ESCOLHIDO//
        if(usuarioLogado.getBackGround().equals("0")){
            view.setBackgroundResource(R.drawable.background1);
        }else if(usuarioLogado.getBackGround().equals("1")){
            view.setBackgroundResource(R.drawable.background2);
        }else{
            view.setBackgroundResource(R.drawable.background3);
        }
        //

        btnGoToADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaAdicionarPalavra(uid);
            }
        });

        tc_paletaCores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaPaletaLapis();
            }
        });

    }

    public void irParaPaletaLapis(){
        Intent intent = new Intent(this,Usuario_CoresLapis.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        IrParaTelaInicial(uid);
    }

    void IrParaTelaInicial(String uid){
        Bundle bundle = new Bundle();
        bundle.putString("uid",uid);
        Intent intent = new Intent(this, Usuario_Menu.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    public void irParaAdicionarPalavra(String uid){
        Bundle bundle = new Bundle();
        bundle.putString("uid",uid);
        Intent intent = new Intent(this, Usuario_AdicionarPalavra.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void salvarPalavra(Palavra palavra) {

        if (verificarPalavra(palavra)) {
            Toast.makeText(this,"Esta palavra já foi cadastrada!",Toast.LENGTH_SHORT);
        } else {
            try {
                DatabaseReference firebase = ConfiguracaoFirebase.getFirebase().child("Palavras");
                String p = palavra.getPalavra();
                firebase.child(p).setValue(palavra);
                Toast.makeText(this, "Palavra cadastrada com Sucesso!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Falha na conexão", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    public Boolean verificarPalavra(Palavra palavra) {
        String p = palavra.getPalavra().toString();
        retorno = false;
        firebase.getRoot();
        firebase.child("Palavras").orderByChild("palavra").equalTo(p).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                retorno = true;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return retorno;
    }


}
