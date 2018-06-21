package com.myscript.atk.sltw.sample.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myscript.atk.sltw.sample.Adapter.UsuariosAdapter;
import com.myscript.atk.sltw.sample.Entidades.Usuarios;
import com.myscript.atk.sltw.sample.R;

import java.util.ArrayList;

public class Admin_ListarUsuarios extends AppCompatActivity {

    private DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
    private FirebaseDatabase firebaseDatabase;

    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText edtPalavra;
    private ArrayList<Usuarios> listaPessoas = new ArrayList<>();
    private UsuariosAdapter usuariosArrayAdapter;



    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_adm);
        ListView list = (ListView)findViewById(R.id.apa_listUsuarios);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.apa_swipe);

        list.deferNotifyDataSetChanged();
        refreshLista();

        context = this;

       swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               refreshTela(getIntent());
           }
       });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                abrirActivityUsuario(listaPessoas.get(position));
            }
        });

    }


    public void abrirActivityUsuario(Usuarios usuarios){
        Intent intentAbrirUsuario = new Intent(Admin_ListarUsuarios.this,Admin_AbrirUsuario.class);
        intentAbrirUsuario.putExtra("usuario",usuarios);
        startActivity(intentAbrirUsuario);
        finish();
    }

    public ArrayList<Usuarios> preencherListaUsuarios() {

        try {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference().child("Usuarios");
            ref.orderByChild("adm").equalTo("0").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        listaPessoas.add(postSnapshot.getValue(Usuarios.class));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch(Exception e){
            Toast.makeText(context, "Não foi possível se conectar ao servidor!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return listaPessoas;
    }

    public void refreshLista(){
        ListView list = (ListView)findViewById(R.id.apa_listUsuarios);
        listaPessoas = preencherListaUsuarios();
        usuariosArrayAdapter = new UsuariosAdapter(this,listaPessoas);
        usuariosArrayAdapter.notifyDataSetChanged();
        list.setAdapter(usuariosArrayAdapter);
    }

    public void refreshTela(Intent intent) {
        finish();
        startActivity(intent);
    }



}