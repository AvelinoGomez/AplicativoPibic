package com.myscript.atk.sltw.sample.Activity;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.myscript.atk.sltw.sample.Adapter.UsuariosAdapter;
import com.myscript.atk.sltw.sample.DAO.ConfiguracaoFirebase;
import com.myscript.atk.sltw.sample.Entidades.Usuarios;
import com.myscript.atk.sltw.sample.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PrincipalAdm extends AppCompatActivity {

    private DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
    private FirebaseDatabase firebaseDatabase;


    private EditText edtPalavra;
    private ArrayList<Usuarios> listaPessoas = new ArrayList<>();
    private UsuariosAdapter usuariosArrayAdapter;
    private Button btnRefresh;


    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_adm);
        ListView list = (ListView)findViewById(R.id.apa_listUsuarios);

        btnRefresh = (Button)findViewById(R.id.apa_btnRefresh);

        list.deferNotifyDataSetChanged();
        refreshLista();
        context = this;


        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshTela(getIntent());
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });


    }

    public ArrayList<Usuarios> preencherListaUsuarios() {

            firebase.child("Usuarios").orderByChild("adm").equalTo("0").addValueEventListener(new ValueEventListener() {
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