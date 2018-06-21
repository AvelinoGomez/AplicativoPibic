/*package com.myscript.atk.sltw.sample.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import com.myscript.atk.sltw.sample.Adapter.PalavrasAdapterUsuario;
import com.myscript.atk.sltw.sample.CRUD.ReadUsuario;
import com.myscript.atk.sltw.sample.CRUD.UpdatePalavras;
import com.myscript.atk.sltw.sample.DAO.ConfiguracaoFirebase;
import com.myscript.atk.sltw.sample.Entidades.Palavra;
import com.myscript.atk.sltw.sample.Entidades.Usuarios;
import com.myscript.atk.sltw.sample.R;

import java.util.ArrayList;

public class Usuario_AdicionarPalavra extends AppCompatActivity {

    Boolean retorno;

    private DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
    private FirebaseDatabase firebaseDatabase;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView aap_listPalavras;
    private ArrayList<Palavra> listaPalavras = new ArrayList<>();
    EditText aap_edtAddPalavra;
    Button aap_btnAddPalavra;
    private PalavrasAdapterUsuario palavrasArrayAdapter;
    Context context;

    String uid;
    Usuarios u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_palavra);

        Intent intent = getIntent();
        Bundle dados = intent.getExtras();
        uid = dados.getString("uid").toString();

        context=this;
        inicializarComponentes();



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshTela(getIntent());
            }
        });

        aap_btnAddPalavra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aap_edtAddPalavra.getText().toString().equals("")){
                    Toast.makeText(context,"Digite uma palavra",Toast.LENGTH_SHORT);
                }else{
                    String palavraSalvar = aap_edtAddPalavra.getText().toString();

                    ReadUsuario r = new ReadUsuario(context);
                    u = r.getUsuarios().get(0);

                    palavraSalvar.toLowerCase();
                    Palavra palavra = new Palavra();
                    palavra.setPalavra(palavraSalvar);
                    palavra.setNomeCriador(u.getNome()+" "+u.getSobrenome());
                    palavra.setUidCriador(uid);
                    UpdatePalavras up = new UpdatePalavras(context);
                    up.insertPalavra(palavra);
                    salvarPalavra(palavra);
                }
            }
        });

        //RESGATANDO PALAVRA DO FIREBASE
        firebase = FirebaseDatabase.getInstance().getReference();
        firebase.child("Palavras").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    listaPalavras.add(postSnapshot.getValue(Palavra.class));
                }
                palavrasArrayAdapter = new PalavrasAdapterUsuario(context,listaPalavras);
                aap_listPalavras.setAdapter(palavrasArrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
                aap_edtAddPalavra.setText("");
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

    public void refreshTela(Intent intent){
            finish();
            startActivity(intent);
    }


    public void inicializarComponentes(){
        aap_btnAddPalavra = (Button)findViewById(R.id.aap_btnAddPalavra);
        aap_edtAddPalavra = (EditText)findViewById(R.id.aap_edtAddPalavra);
        aap_listPalavras = (ListView)findViewById(R.id.aap_listaPalavras);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.aap_swipe);
    }
    }


*/