package com.myscript.atk.sltw.sample.Activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.myscript.atk.sltw.sample.CRUD.ReadUsuario;
import com.myscript.atk.sltw.sample.CRUD.UpdateUsuario;
import com.myscript.atk.sltw.sample.DAO.ConfiguracaoFirebase;
import com.myscript.atk.sltw.sample.Entidades.Usuarios;
import com.myscript.atk.sltw.sample.R;

import java.util.ArrayList;

public class Usuario_CoresBackground extends AppCompatActivity {

    Button paleta_Verde;
    Button paleta_Amarelo;
    Button paleta_Azul;
    Button paleta_Cinza;
    Button paleta_Esmeralda;
    Button paleta_Laranja;
    Button paleta_Marrom;
    Button paleta_Preto;
    Button paleta_Rosa;
    Button paleta_Roxo;
    Button paleta_Vermelho;
    Button paleta_Violeta;

    Usuarios usuario;

    Context context;

    private DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paleta_cores_papel);

        ReadUsuario r = new ReadUsuario(getApplicationContext());
        final ArrayList<Usuarios> usuarios = r.getUsuarios();
        usuario = usuarios.get(0);

        context=getApplicationContext();

        Toast.makeText(context, "Escolha uma cor para seu Fundo!", Toast.LENGTH_LONG).show();

        Button paleta_Verde = (Button)findViewById(R.id.paleta_background_btnVerde);
        Button paleta_Amarelo = (Button)findViewById(R.id.paleta_background_btnAmarelo);
        Button paleta_Azul = (Button)findViewById(R.id.paleta_background_btnAzul);
        Button paleta_Cinza = (Button)findViewById(R.id.paleta_background_btnCinza);
        Button paleta_Esmeralda = (Button)findViewById(R.id.paleta_background_btnEsmeralda);
        Button paleta_Laranja = (Button)findViewById(R.id.paleta_background_btnLaranja);
        Button paleta_Marrom = (Button)findViewById(R.id.paleta_background_btnMarrom);
        Button paleta_Preto = (Button)findViewById(R.id.paleta_background_btnPreto);
        Button paleta_Rosa = (Button)findViewById(R.id.paleta_background_btnRosa);
        Button paleta_Roxo = (Button)findViewById(R.id.paleta_background_btnRoxo);
        Button paleta_Vermelho = (Button)findViewById(R.id.paleta_background_btnVermelho);
        Button paleta_Violeta = (Button)findViewById(R.id.paleta_background_btnVioleta);

        paleta_Amarelo.setBackgroundColor(Color.rgb(255,255,0));
        paleta_Amarelo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetarCorUsuario(255,255,0,v);
            }
        });
        paleta_Azul.setBackgroundColor(Color.rgb(32,112,160));
        paleta_Azul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetarCorUsuario(32,112,160,v);
            }
        });
        paleta_Cinza.setBackgroundColor(Color.rgb(196,196,196));
        paleta_Cinza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetarCorUsuario(196,196,196,v);
            }
        });
        paleta_Esmeralda.setBackgroundColor(Color.rgb(80,200,120));
        paleta_Esmeralda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetarCorUsuario(80,200,120,v	);
            }
        });
        paleta_Laranja.setBackgroundColor(Color.rgb(255,165,0));
        paleta_Laranja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetarCorUsuario(255,165,0,v);
            }
        });
        paleta_Marrom.setBackgroundColor(Color.rgb(150,75,0));
        paleta_Marrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetarCorUsuario(150,75,0,v);
            }
        });
        paleta_Preto.setBackgroundColor(Color.rgb(255,255,255));
        paleta_Preto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetarCorUsuario(255,255,255,v);
            }
        });
        paleta_Rosa.setBackgroundColor(Color.rgb(255,0,127));
        paleta_Rosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetarCorUsuario(255,0,127,v);
            }
        });
        paleta_Roxo.setBackgroundColor(Color.rgb(200,25,180));
        paleta_Roxo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetarCorUsuario(200,25,180,v);
            }
        });
        paleta_Verde.setBackgroundColor(Color.rgb(0,128,0));
        paleta_Verde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetarCorUsuario(0,128,0,v);
            }
        });
        paleta_Vermelho.setBackgroundColor(Color.rgb(255,0,0));
        paleta_Vermelho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetarCorUsuario(255,0,0,v);
            }
        });
        paleta_Violeta.setBackgroundColor(Color.rgb(238,130,238));
        paleta_Violeta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetarCorUsuario(238,130,238,v);
            }
        });

    }

    void SetarCorUsuario(int r,int g,int b,View v){
        usuario.setCorBackGround(""+Color.rgb(r,g,b));
        usuario.setFlagFundo("0");
        UpdateUsuario u = new UpdateUsuario(context);
        u.updateUsuario(usuario);
        salvarHistorico(usuario);


        finish();
    }

    public void salvarHistorico(Usuarios usuario){

        try{
            firebase.getRoot();
            firebase = ConfiguracaoFirebase.getFirebase().child("Usuarios");
            firebase.child(usuario.getId()).setValue(usuario);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(context,"Erro na conexão!",Toast.LENGTH_SHORT).show();
        }
    }


}

