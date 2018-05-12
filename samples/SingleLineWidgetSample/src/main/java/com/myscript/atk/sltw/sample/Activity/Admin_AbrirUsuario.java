package com.myscript.atk.sltw.sample.Activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myscript.atk.sltw.sample.Adapter.HistoricoAdapter;
import com.myscript.atk.sltw.sample.DAO.ConfiguracaoFirebase;
import com.myscript.atk.sltw.sample.Entidades.Historico;
import com.myscript.atk.sltw.sample.Entidades.Usuarios;
import com.myscript.atk.sltw.sample.R;

import java.util.ArrayList;

public class Admin_AbrirUsuario extends AppCompatActivity {

    Button btnAtivarDescricao;
    EditText edtDescricaoAluno;
    TextView txtAluno;
    ListView listJogos;
    SwipeRefreshLayout swipeRefreshLayout;

    ArrayList<Historico> listaHistoricos = new ArrayList<Historico>();
    HistoricoAdapter historicoAdapter;

    Intent intent;

    Usuarios usuarios;
    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_abrir_usuario);

        intent = getIntent();

        usuarios = (Usuarios)getIntent().getSerializableExtra("usuario");

        InstanciarComponentes();
        refreshLista();


        edtDescricaoAluno.setText(usuarios.getMensagem());
        txtAluno.setText("");
        txtAluno.setText(txtAluno.getText()+usuarios.getNome()+" "+usuarios.getSobrenome());

        listJogos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                abrirActivityErros(listaHistoricos.get(position));
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshTela(intent);
            }
        });

        btnAtivarDescricao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carregarDescricao(usuarios,edtDescricaoAluno.getText().toString());
            }
        });


    }

    public void abrirActivityErros(Historico historico){
        Intent intentAbrirErro = new Intent(Admin_AbrirUsuario.this,Admin_ErrosDoUsuario.class);
        intentAbrirErro.putExtra("historico",historico);
        startActivity(intentAbrirErro);
    }

    public void carregarDescricao(Usuarios usuario,String msg){

        usuario.setMensagem(msg);

        firebase = ConfiguracaoFirebase.getFirebase();
        firebase.child("Usuarios").child(usuario.getId()).setValue(usuario);
    }


    public ArrayList<Historico> preencherListaHistorico() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("Historico").child(usuarios.getId());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    listaHistoricos.add(postSnapshot.getValue(Historico.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return listaHistoricos;
    }

    public void refreshLista(){
        listJogos = (ListView)findViewById(R.id.aaau_listJogos);
        listaHistoricos = preencherListaHistorico();
        historicoAdapter = new HistoricoAdapter(this,listaHistoricos);
        historicoAdapter.notifyDataSetChanged();
        listJogos.setAdapter(historicoAdapter);

    }

    public void InstanciarComponentes(){
        btnAtivarDescricao = (Button)findViewById(R.id.aaau_btnSalvar);
        edtDescricaoAluno = (EditText)findViewById(R.id.aaau_edtDescAluno);
        listJogos = (ListView)findViewById(R.id.aaau_listJogos);
        txtAluno = (TextView)findViewById(R.id.aaau_edtAluno);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.aaau_swipe);
    }

    public void refreshTela(Intent intent) {
        finish();
        startActivity(intent);
    }
}
