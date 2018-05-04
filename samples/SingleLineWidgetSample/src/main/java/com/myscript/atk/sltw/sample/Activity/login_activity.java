package com.myscript.atk.sltw.sample.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.myscript.atk.sltw.sample.CRUD.CreatePalavras;
import com.myscript.atk.sltw.sample.CRUD.CreateUsuario;
import com.myscript.atk.sltw.sample.CRUD.DeletePalavras;
import com.myscript.atk.sltw.sample.CRUD.ReadPalavras;
import com.myscript.atk.sltw.sample.CRUD.ReadUsuario;
import com.myscript.atk.sltw.sample.CRUD.UpdatePalavras;
import com.myscript.atk.sltw.sample.CRUD.UpdateUsuario;
import com.myscript.atk.sltw.sample.DAO.ConfiguracaoFirebase;
import com.myscript.atk.sltw.sample.Entidades.Palavra;
import com.myscript.atk.sltw.sample.Entidades.Usuarios;
import com.myscript.atk.sltw.sample.Helper.Preferencias;
import com.myscript.atk.sltw.sample.R;

import java.util.ArrayList;

public class login_activity extends AppCompatActivity {

    TextView txtCadastro;
    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnLogar;
    FirebaseAuth autenticacao;

    private Usuarios usuarios;
    private DatabaseReference firebase;
    private FirebaseDatabase firebaseDatabase;
    Preferencias preferencias;

    ArrayList<Palavra> palavraLista;

    Palavra p;
    UpdatePalavras up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        palavraLista = new ArrayList<>();

        CreateUsuario c = new CreateUsuario(getApplicationContext());
        c.createTable();
        CreatePalavras cp = new CreatePalavras(getApplicationContext());
        cp.createTable();
        up = new UpdatePalavras(getApplicationContext());

        ReadUsuario r = new ReadUsuario(getApplicationContext());


        ArrayList<Usuarios> usuariosLista = r.getUsuarios();

        //Resgate palavra
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        //firebase = FirebaseDatabase.getInstance().getReference().child("Palavras");
        DatabaseReference ref = database.getReference().child("Palavras");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    palavraLista.add(postSnapshot.getValue(Palavra.class));
                    up.insertPalavra(postSnapshot.getValue(Palavra.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ReadPalavras rp = new ReadPalavras(getApplicationContext());
        ArrayList<Palavra> palavraListaBD = rp.getPalavras();

        if(palavraLista.size()!=palavraListaBD.size()){
            DeletePalavras del = new DeletePalavras(getApplicationContext());
            del.deleteTable();

            for (int i = 0; i <palavraLista.size() ; i++) {
                up.insertPalavra(palavraLista.get(i));
            }

        }


        if(usuariosLista.size()!=0){
            //Toast.makeText(getApplicationContext(),"EXISTE UM USARIO: "+usuariosLista.get(0).getNome(),Toast.LENGTH_SHORT).show();
            if(usuariosLista.get(0).getAdm().equals("0")){
                abrirTelaPrincipalComum(usuariosLista.get(0).getId());
            }else{
                abrirTelaPrincipalAdm(usuariosLista.get(0).getId());
            }
        }


        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtSenha = (EditText)findViewById(R.id.edtSenha);
        btnLogar = (Button)findViewById(R.id.btnLogar);
        txtCadastro = (TextView)findViewById(R.id.txtCadastro);

        //verifaUsuarioLogado();


        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtEmail.getText().toString().equals("") && !edtSenha.getText().toString().equals("")){
                    usuarios = new Usuarios();
                    usuarios.setEmail(edtEmail.getText().toString());
                    usuarios.setSenha(edtSenha.getText().toString());

                    validarLogin();

                }else{
                    Toast.makeText(login_activity.this,"Preencha os campos de Email e Senha",Toast.LENGTH_SHORT);
                }
            }
        });

        txtCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaCadastro();
            }
        });



    }

    public ArrayList<Palavra> onDataChange(DataSnapshot dataSnapshot) {
        ArrayList<Palavra> p  = new ArrayList<Palavra>();
        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
            p.add(postSnapshot.getValue(Palavra.class));
        }

        return p;
    }

     /*public ArrayList<Palavra> resgatarPalavrasBD(){



    return listaPalavras;
    }*/

    private void validarLogin(){

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuarios.getEmail(),usuarios.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    DatabaseReference firebase;
                    firebase = ConfiguracaoFirebase.getFirebase().child("Usuarios").child(autenticacao.getUid());
                    firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Usuarios usuario;
                            usuario= dataSnapshot.getValue(Usuarios.class);

                            //CRUD
                            UpdateUsuario u = new UpdateUsuario(getApplicationContext());
                            if(u.insertUsuario(usuario)){
                                Toast.makeText(login_activity.this,"SALVO",Toast.LENGTH_SHORT);

                            }else{
                                Toast.makeText(login_activity.this,"Erro ao salvar usuario no Armazenamento local!",Toast.LENGTH_SHORT);
                            }
                            //CRUD

                            String adm = usuario.getAdm();
                            if(adm.equals("0")){
                                abrirTelaPrincipalComum(autenticacao.getUid());
                            }
                            else{
                                abrirTelaPrincipalAdm(autenticacao.getUid());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("Erro na leitura do arquivo: " + databaseError.getCode());
                        }
                    });




                    //Toast.makeText(login_activity.this, "Login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(login_activity.this, "Login ou Senha invalidos!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void abrirTelaPrincipalAdm(String uid){
        Bundle bundle = new Bundle();
        bundle.putString("uid",uid);
        Intent intentAbrirTelaPrincipal = new Intent(login_activity.this,MenuAdm.class);
        intentAbrirTelaPrincipal.putExtras(bundle);
        startActivity(intentAbrirTelaPrincipal);
        finish();

    }

    public String resgatarModoUsuarioFirebase(String uid){
        DatabaseReference firebase;
        firebase = ConfiguracaoFirebase.getFirebase().child("Usuarios").child(uid);
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuarios usuario;
                usuario= dataSnapshot.getValue(Usuarios.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Erro na leitura do arquivo: " + databaseError.getCode());
            }
        });
        return usuarios.getAdm();
    }

    public void abrirTelaPrincipalComum(String uid){
        Bundle bundle = new Bundle();
        bundle.putString("uid",uid);
        Intent intentAbrirTelaPrincipal = new Intent(login_activity.this,telamenu.class);
        intentAbrirTelaPrincipal.putExtras(bundle);
        startActivity(intentAbrirTelaPrincipal);
        finish();
    }

    public void irParaCadastro(){
        Intent intent = new Intent(this,CadastroActivity.class);
            startActivity(intent);
            finish();
    }

    private void verifaUsuarioLogado() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if (autenticacao.getCurrentUser() != null) {
            Intent intent = new Intent(login_activity.this, telamenu.class);
            startActivity(intent);
            finish();
        }
    }

}
