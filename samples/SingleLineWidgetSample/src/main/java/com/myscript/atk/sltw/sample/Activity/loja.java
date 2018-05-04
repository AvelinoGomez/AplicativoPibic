package com.myscript.atk.sltw.sample.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.myscript.atk.sltw.sample.CRUD.ReadUsuario;
import com.myscript.atk.sltw.sample.CRUD.UpdateUsuario;
import com.myscript.atk.sltw.sample.DAO.ConfiguracaoFirebase;
import com.myscript.atk.sltw.sample.Entidades.Usuarios;
import com.myscript.atk.sltw.sample.R;

import java.util.ArrayList;

/*
Adicionar FUNDOS COMPRADOS E NÃO COMPRADOS
Adicionar TABELA DE PREÇOS
Adicionar CRUD
 */
public class loja extends AppCompatActivity {

    Button btnPassarEsqFundo;
    Button btnPassarDirFundo;
    Button btnPassarEsqMsg;
    Button btnPassarDirMsg;
    Button btnSalvar;
    ImageView imgFundo;
    ImageView imgMsg;
    TextView txtPrecoFundo;
    TextView txtPrecoMsg;

    View layoutInteiro;
    String uid;

    Usuarios usuario;


    int contadorFundo;
    int contadorMsg;

    int qtdBackgroundsCadastrados=3; // 0 - 2//

    DatabaseReference firebase;
    FirebaseAuth usuarioFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loja);

        Intent intent = getIntent();
        Bundle dados = intent.getExtras();
        uid = dados.getString("uid").toString();
        final TextView edtNomeLoja = (TextView)findViewById(R.id.edtNomeLoja);
        final TextView edtPontuacao = (TextView)findViewById(R.id.edtPontGeral);

        /*//Resgatando Usuario//
        firebase = ConfiguracaoFirebase.getFirebase().child("Usuarios").child(uid);

        usuarioFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();

        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuario = dataSnapshot.getValue(Usuarios.class);
                edtNomeLoja.setText(usuario.getNome()+" "+usuario.getSobrenome());
                edtPontuacao.setText(usuario.getPontuacao());
                contadorFundo = Integer.valueOf(usuario.getBackGround());
                contadorMsg = Integer.valueOf(usuario.getMensagem());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Intent intent = new Intent(loja.this,login_activity.class);
                startActivity(intent);
            }
        });
        //RESGATANDO USUARIO//*/

        ReadUsuario r = new ReadUsuario(getApplicationContext());
        ArrayList<Usuarios> usuariosLista = r.getUsuarios();
        usuario = usuariosLista.get(0);

        edtNomeLoja.setText(usuario.getNome()+" "+usuario.getSobrenome());
        edtPontuacao.setText(usuario.getPontuacao());
        contadorFundo = Integer.valueOf(usuario.getBackGround());
        contadorMsg = Integer.valueOf(usuario.getMensagem());


        instanciarComponentes();

        //SETANDO BACKGROUND//
        if(contadorFundo==0){
            layoutInteiro.setBackgroundResource(R.drawable.background1);
        }else if(contadorFundo==1){
            layoutInteiro.setBackgroundResource(R.drawable.background2);
        }else{
            layoutInteiro.setBackgroundResource(R.drawable.background3);
        }
        //////////////////////

        //SETANDO MENSAGEM//
        if(contadorMsg==0){
            imgMsg.setBackgroundResource(R.drawable.barco);
        }
        //////////////////////

        btnPassarEsqFundo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contadorFundo==0){
                    contadorFundo=qtdBackgroundsCadastrados-1;
                    setBackgroundTela(contadorFundo);
                }else{
                    contadorFundo--;
                    setBackgroundTela(contadorFundo);
                }
            }
        });
        btnPassarDirFundo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contadorFundo==qtdBackgroundsCadastrados-1){
                    contadorFundo=0;
                    setBackgroundTela(contadorFundo);
                }else{
                    contadorFundo++;
                    setBackgroundTela(contadorFundo);
                }
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebase = ConfiguracaoFirebase.getFirebase().child("Usuarios").child(uid);

                usuario.setBackGround(contadorFundo+"");
                usuario.setMensagem(contadorMsg+"");

                firebase.setValue(usuario);

                UpdateUsuario u = new UpdateUsuario(getApplicationContext());
                u.updateUsuario(usuario);

                IrParaTelaInicial(uid);

            }
        });
    }

    private void setBackgroundTela(int contadorFundo) {

        if(contadorFundo==0){
            imgFundo.setBackgroundResource(R.drawable.background1);
            //layoutInteiro.setBackgroundResource(R.drawable.background1);
            txtPrecoFundo.setText("50");
        }
        else if(contadorFundo==1){
            imgFundo.setBackgroundResource(R.drawable.background2);
            //layoutInteiro.setBackgroundResource(R.drawable.background2);
            txtPrecoFundo.setText("50");
        }else if(contadorFundo==2){
            imgFundo.setBackgroundResource(R.drawable.background3);
            //layoutInteiro.setBackgroundResource(R.drawable.background3);
            txtPrecoFundo.setText("50");
        }else /*if(contadorFundo<0)*/{
            contadorFundo=1;
            imgFundo.setBackgroundResource(R.drawable.background2);
            //layoutInteiro.setBackgroundResource(R.drawable.background2);
            txtPrecoFundo.setText("50");
        }
        /*else if(contadorFundo>qtdBackgroundsCadastrados-1){
            contadorFundo=1;
            imgFundo.setBackgroundResource(R.drawable.background1);
            layoutInteiro.setBackgroundResource(R.drawable.background1);
        }*/

    }

    private void setMensagemTela(int contadorMsg) {

        if(contadorMsg==0){
            imgMsg.setBackgroundResource(R.drawable.barco);
        }

    }

    int comprarFundo(){
        return 0;
    }


    void instanciarComponentes(){
        btnPassarDirFundo = (Button)findViewById(R.id.al_btnDirFundo);
        btnPassarEsqFundo = (Button)findViewById(R.id.al_btnEsqFundo);
        btnPassarDirMsg = (Button)findViewById(R.id.al_btnDirMsg);
        btnPassarEsqMsg = (Button)findViewById(R.id.al_btnEsqMsg);
        imgFundo = (ImageView)findViewById(R.id.al_imgFundo);
        imgMsg = (ImageView)findViewById(R.id.al_imgMsg);
        txtPrecoFundo = (TextView) findViewById(R.id.al_txtPrecoFundo);
        txtPrecoMsg = (TextView)findViewById(R.id.al_txtPrecoMsg);
        layoutInteiro = (View)findViewById(R.id.layoutLoja);
        btnSalvar = (Button)findViewById(R.id.al_btnSalvar);
    }

    private void deslogarUsuario() {
        usuarioFirebase.signOut();
        Intent intent = new Intent(loja.this, login_activity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        IrParaTelaInicial(uid);
    }

    void IrParaTelaInicial(String uid){
        Bundle bundle = new Bundle();
        bundle.putString("uid",uid);
        Intent intent = new Intent(this, telamenu.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}
