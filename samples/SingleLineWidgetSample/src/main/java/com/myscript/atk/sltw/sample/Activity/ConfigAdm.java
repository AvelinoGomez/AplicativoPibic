package com.myscript.atk.sltw.sample.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myscript.atk.sltw.sample.Adapter.PalavrasAdapter;
import com.myscript.atk.sltw.sample.Adapter.UsuariosAdapter;
import com.myscript.atk.sltw.sample.CRUD.ReadUsuario;
import com.myscript.atk.sltw.sample.CRUD.UpdatePalavras;
import com.myscript.atk.sltw.sample.DAO.ConfiguracaoFirebase;
import com.myscript.atk.sltw.sample.Entidades.Palavra;
import com.myscript.atk.sltw.sample.Entidades.Usuarios;
import com.myscript.atk.sltw.sample.R;

import java.util.ArrayList;
import java.util.List;

public class ConfigAdm extends AppCompatActivity {

    Boolean retorno;

    private DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
    private FirebaseDatabase firebaseDatabase;

    private ListView aca_listPalavras;
    private ArrayList<Palavra> listaPalavras = new ArrayList<>();
    EditText aca_edtAddPalavra;
    Button aca_btnAddPalavra;
    Button aca_btnRefresh;
    private PalavrasAdapter palavrasArrayAdapter;
    Context context;

    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_adm);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent intent = getIntent();
        Bundle dados = intent.getExtras();
        uid = dados.getString("uid").toString();

        context=this;
        inicializarComponentes();

        aca_btnAddPalavra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aca_edtAddPalavra.getText().toString().equals("")){
                    Toast.makeText(context,"Digite uma palavra",Toast.LENGTH_SHORT);
                }else{
                    String palavraSalvar = aca_edtAddPalavra.getText().toString();
                    palavraSalvar.toLowerCase();

                    ReadUsuario r = new ReadUsuario(context);
                    Usuarios usuarios = r.getUsuarios().get(0);

                    Palavra palavra = new Palavra();
                    palavra.setPalavra(palavraSalvar);
                    palavra.setUidCriador(uid);
                    palavra.setNomeCriador(usuarios.getNome());
                    UpdatePalavras u = new UpdatePalavras(context);
                    u.insertPalavra(palavra);
                    salvarPalavra(palavra);
                }
            }
        });

        aca_btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshTela(getIntent());
            }
        });

        //RESGATANDO PALAVRA DO FIREBASE
        firebase = FirebaseDatabase.getInstance().getReference();
        firebase.child("Palavras").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    listaPalavras.add(postSnapshot.getValue(Palavra.class));
                    palavrasArrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        palavrasArrayAdapter = new PalavrasAdapter(this,listaPalavras);
        aca_listPalavras.setAdapter(palavrasArrayAdapter);


    }


    public void resgatarPalavrasLista(){

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
                aca_edtAddPalavra.setText("");
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

    public ArrayList<Palavra> resgatarPalavrasBD(){

        firebase = FirebaseDatabase.getInstance().getReference();
        firebase.child("Palavras").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    listaPalavras.add(postSnapshot.getValue(Palavra.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return listaPalavras;
    }

    public void inicializarComponentes(){
        aca_btnAddPalavra = (Button)findViewById(R.id.aca_btnAddPalavra);
        aca_edtAddPalavra = (EditText)findViewById(R.id.aca_edtAddPalavra);
        aca_listPalavras = (ListView)findViewById(R.id.aca_listaPalavras);
        aca_btnRefresh = (Button)findViewById(R.id.aca_btnRefresh);
    }

    public void refreshTela(Intent intent) {
        finish();
        startActivity(intent);
    }

}
